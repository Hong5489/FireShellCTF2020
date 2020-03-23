package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import kotlin.coroutines.Continuation;
import okhttp3.Call.Factory;
import okhttp3.Response;
import okhttp3.ResponseBody;

abstract class HttpServiceMethod<ResponseT, ReturnT> extends ServiceMethod<ReturnT> {
    private final Factory callFactory;
    private final RequestFactory requestFactory;
    private final Converter<ResponseBody, ResponseT> responseConverter;

    static final class CallAdapted<ResponseT, ReturnT> extends HttpServiceMethod<ResponseT, ReturnT> {
        private final CallAdapter<ResponseT, ReturnT> callAdapter;

        CallAdapted(RequestFactory requestFactory, Factory callFactory, Converter<ResponseBody, ResponseT> responseConverter, CallAdapter<ResponseT, ReturnT> callAdapter2) {
            super(requestFactory, callFactory, responseConverter);
            this.callAdapter = callAdapter2;
        }

        /* access modifiers changed from: protected */
        public ReturnT adapt(Call<ResponseT> call, Object[] args) {
            return this.callAdapter.adapt(call);
        }
    }

    static final class SuspendForBody<ResponseT> extends HttpServiceMethod<ResponseT, Object> {
        private final CallAdapter<ResponseT, Call<ResponseT>> callAdapter;
        private final boolean isNullable;

        SuspendForBody(RequestFactory requestFactory, Factory callFactory, Converter<ResponseBody, ResponseT> responseConverter, CallAdapter<ResponseT, Call<ResponseT>> callAdapter2, boolean isNullable2) {
            super(requestFactory, callFactory, responseConverter);
            this.callAdapter = callAdapter2;
            this.isNullable = isNullable2;
        }

        /* access modifiers changed from: protected */
        public Object adapt(Call<ResponseT> call, Object[] args) {
            Object obj;
            Call<ResponseT> call2 = (Call) this.callAdapter.adapt(call);
            Continuation<ResponseT> continuation = args[args.length - 1];
            try {
                if (this.isNullable) {
                    obj = KotlinExtensions.awaitNullable(call2, continuation);
                } else {
                    obj = KotlinExtensions.await(call2, continuation);
                }
                return obj;
            } catch (Exception e) {
                return KotlinExtensions.suspendAndThrow(e, continuation);
            }
        }
    }

    static final class SuspendForResponse<ResponseT> extends HttpServiceMethod<ResponseT, Object> {
        private final CallAdapter<ResponseT, Call<ResponseT>> callAdapter;

        SuspendForResponse(RequestFactory requestFactory, Factory callFactory, Converter<ResponseBody, ResponseT> responseConverter, CallAdapter<ResponseT, Call<ResponseT>> callAdapter2) {
            super(requestFactory, callFactory, responseConverter);
            this.callAdapter = callAdapter2;
        }

        /* access modifiers changed from: protected */
        public Object adapt(Call<ResponseT> call, Object[] args) {
            Call<ResponseT> call2 = (Call) this.callAdapter.adapt(call);
            Continuation<Response<ResponseT>> continuation = args[args.length - 1];
            try {
                return KotlinExtensions.awaitResponse(call2, continuation);
            } catch (Exception e) {
                return KotlinExtensions.suspendAndThrow(e, continuation);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public abstract ReturnT adapt(Call<ResponseT> call, Object[] objArr);

    static <ResponseT, ReturnT> HttpServiceMethod<ResponseT, ReturnT> parseAnnotations(Retrofit retrofit, Method method, RequestFactory requestFactory2) {
        boolean continuationWantsResponse;
        Annotation[] annotations;
        Type adapterType;
        Retrofit retrofit3 = retrofit;
        Method method2 = method;
        RequestFactory requestFactory3 = requestFactory2;
        boolean isKotlinSuspendFunction = requestFactory3.isKotlinSuspendFunction;
        boolean continuationWantsResponse2 = false;
        Annotation[] annotations2 = method.getAnnotations();
        if (isKotlinSuspendFunction) {
            Type[] parameterTypes = method.getGenericParameterTypes();
            Type responseType = Utils.getParameterLowerBound(0, (ParameterizedType) parameterTypes[parameterTypes.length - 1]);
            if (Utils.getRawType(responseType) == Response.class && (responseType instanceof ParameterizedType)) {
                responseType = Utils.getParameterUpperBound(0, (ParameterizedType) responseType);
                continuationWantsResponse2 = true;
            }
            adapterType = new ParameterizedTypeImpl(null, Call.class, responseType);
            Type type = adapterType;
            continuationWantsResponse = continuationWantsResponse2;
            annotations = SkipCallbackExecutorImpl.ensurePresent(annotations2);
        } else {
            continuationWantsResponse = false;
            annotations = annotations2;
            adapterType = method.getGenericReturnType();
        }
        CallAdapter<ResponseT, ReturnT> callAdapter = createCallAdapter(retrofit3, method2, adapterType, annotations);
        Type responseType2 = callAdapter.responseType();
        if (responseType2 == Response.class) {
            StringBuilder sb = new StringBuilder();
            sb.append("'");
            sb.append(Utils.getRawType(responseType2).getName());
            sb.append("' is not a valid response body type. Did you mean ResponseBody?");
            throw Utils.methodError(method2, sb.toString(), new Object[0]);
        } else if (responseType2 == Response.class) {
            throw Utils.methodError(method2, "Response must include generic type (e.g., Response<String>)", new Object[0]);
        } else if (!requestFactory3.httpMethod.equals("HEAD") || Void.class.equals(responseType2)) {
            Converter<ResponseBody, ResponseT> responseConverter2 = createResponseConverter(retrofit3, method2, responseType2);
            Factory callFactory2 = retrofit3.callFactory;
            if (!isKotlinSuspendFunction) {
                return new CallAdapted(requestFactory3, callFactory2, responseConverter2, callAdapter);
            }
            if (continuationWantsResponse) {
                return new SuspendForResponse(requestFactory3, callFactory2, responseConverter2, callAdapter);
            }
            Factory factory = callFactory2;
            Converter converter = responseConverter2;
            SuspendForBody suspendForBody = new SuspendForBody(requestFactory2, callFactory2, responseConverter2, callAdapter, false);
            return suspendForBody;
        } else {
            throw Utils.methodError(method2, "HEAD method must use Void as response type.", new Object[0]);
        }
    }

    private static <ResponseT, ReturnT> CallAdapter<ResponseT, ReturnT> createCallAdapter(Retrofit retrofit, Method method, Type returnType, Annotation[] annotations) {
        try {
            return retrofit.callAdapter(returnType, annotations);
        } catch (RuntimeException e) {
            throw Utils.methodError(method, e, "Unable to create call adapter for %s", returnType);
        }
    }

    private static <ResponseT> Converter<ResponseBody, ResponseT> createResponseConverter(Retrofit retrofit, Method method, Type responseType) {
        try {
            return retrofit.responseBodyConverter(responseType, method.getAnnotations());
        } catch (RuntimeException e) {
            throw Utils.methodError(method, e, "Unable to create converter for %s", responseType);
        }
    }

    HttpServiceMethod(RequestFactory requestFactory2, Factory callFactory2, Converter<ResponseBody, ResponseT> responseConverter2) {
        this.requestFactory = requestFactory2;
        this.callFactory = callFactory2;
        this.responseConverter = responseConverter2;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public final ReturnT invoke(Object[] args) {
        return adapt(new OkHttpCall<>(this.requestFactory, args, this.callFactory, this.responseConverter), args);
    }
}
