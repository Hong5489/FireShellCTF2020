package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import kotlin.coroutines.Continuation;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.OPTIONS;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;

final class RequestFactory {
    private final HttpUrl baseUrl;
    @Nullable
    private final MediaType contentType;
    private final boolean hasBody;
    @Nullable
    private final Headers headers;
    final String httpMethod;
    private final boolean isFormEncoded;
    final boolean isKotlinSuspendFunction;
    private final boolean isMultipart;
    private final Method method;
    private final ParameterHandler<?>[] parameterHandlers;
    @Nullable
    private final String relativeUrl;

    static final class Builder {
        private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
        private static final Pattern PARAM_NAME_REGEX = Pattern.compile(PARAM);
        private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9_-]*)\\}");
        @Nullable
        MediaType contentType;
        boolean gotBody;
        boolean gotField;
        boolean gotPart;
        boolean gotPath;
        boolean gotQuery;
        boolean gotQueryMap;
        boolean gotQueryName;
        boolean gotUrl;
        boolean hasBody;
        @Nullable
        Headers headers;
        @Nullable
        String httpMethod;
        boolean isFormEncoded;
        boolean isKotlinSuspendFunction;
        boolean isMultipart;
        final Method method;
        final Annotation[] methodAnnotations;
        final Annotation[][] parameterAnnotationsArray;
        @Nullable
        ParameterHandler<?>[] parameterHandlers;
        final Type[] parameterTypes;
        @Nullable
        String relativeUrl;
        @Nullable
        Set<String> relativeUrlParamNames;
        final Retrofit retrofit;

        Builder(Retrofit retrofit3, Method method2) {
            this.retrofit = retrofit3;
            this.method = method2;
            this.methodAnnotations = method2.getAnnotations();
            this.parameterTypes = method2.getGenericParameterTypes();
            this.parameterAnnotationsArray = method2.getParameterAnnotations();
        }

        /* access modifiers changed from: 0000 */
        public RequestFactory build() {
            for (Annotation annotation : this.methodAnnotations) {
                parseMethodAnnotation(annotation);
            }
            if (this.httpMethod != null) {
                if (!this.hasBody) {
                    if (this.isMultipart) {
                        throw Utils.methodError(this.method, "Multipart can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                    } else if (this.isFormEncoded) {
                        throw Utils.methodError(this.method, "FormUrlEncoded can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                    }
                }
                int parameterCount = this.parameterAnnotationsArray.length;
                this.parameterHandlers = new ParameterHandler[parameterCount];
                int p = 0;
                int lastParameter = parameterCount - 1;
                while (true) {
                    boolean z = true;
                    if (p >= parameterCount) {
                        break;
                    }
                    ParameterHandler<?>[] parameterHandlerArr = this.parameterHandlers;
                    Type type = this.parameterTypes[p];
                    Annotation[] annotationArr = this.parameterAnnotationsArray[p];
                    if (p != lastParameter) {
                        z = false;
                    }
                    parameterHandlerArr[p] = parseParameter(p, type, annotationArr, z);
                    p++;
                }
                if (this.relativeUrl == null && !this.gotUrl) {
                    throw Utils.methodError(this.method, "Missing either @%s URL or @Url parameter.", this.httpMethod);
                } else if (!this.isFormEncoded && !this.isMultipart && !this.hasBody && this.gotBody) {
                    throw Utils.methodError(this.method, "Non-body HTTP method cannot contain @Body.", new Object[0]);
                } else if (this.isFormEncoded && !this.gotField) {
                    throw Utils.methodError(this.method, "Form-encoded method must contain at least one @Field.", new Object[0]);
                } else if (!this.isMultipart || this.gotPart) {
                    return new RequestFactory(this);
                } else {
                    throw Utils.methodError(this.method, "Multipart method must contain at least one @Part.", new Object[0]);
                }
            } else {
                throw Utils.methodError(this.method, "HTTP method annotation is required (e.g., @GET, @POST, etc.).", new Object[0]);
            }
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof DELETE) {
                parseHttpMethodAndPath("DELETE", ((DELETE) annotation).value(), false);
            } else if (annotation instanceof GET) {
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            } else if (annotation instanceof HEAD) {
                parseHttpMethodAndPath("HEAD", ((HEAD) annotation).value(), false);
            } else if (annotation instanceof PATCH) {
                parseHttpMethodAndPath("PATCH", ((PATCH) annotation).value(), true);
            } else if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
            } else if (annotation instanceof PUT) {
                parseHttpMethodAndPath("PUT", ((PUT) annotation).value(), true);
            } else if (annotation instanceof OPTIONS) {
                parseHttpMethodAndPath("OPTIONS", ((OPTIONS) annotation).value(), false);
            } else if (annotation instanceof HTTP) {
                HTTP http = (HTTP) annotation;
                parseHttpMethodAndPath(http.method(), http.path(), http.hasBody());
            } else if (annotation instanceof retrofit2.http.Headers) {
                String[] headersToParse = ((retrofit2.http.Headers) annotation).value();
                if (headersToParse.length != 0) {
                    this.headers = parseHeaders(headersToParse);
                } else {
                    throw Utils.methodError(this.method, "@Headers annotation is empty.", new Object[0]);
                }
            } else {
                String str = "Only one encoding annotation is allowed.";
                if (annotation instanceof Multipart) {
                    if (!this.isFormEncoded) {
                        this.isMultipart = true;
                        return;
                    }
                    throw Utils.methodError(this.method, str, new Object[0]);
                } else if (!(annotation instanceof FormUrlEncoded)) {
                } else {
                    if (!this.isMultipart) {
                        this.isFormEncoded = true;
                        return;
                    }
                    throw Utils.methodError(this.method, str, new Object[0]);
                }
            }
        }

        private void parseHttpMethodAndPath(String httpMethod2, String value, boolean hasBody2) {
            String str = this.httpMethod;
            if (str == null) {
                this.httpMethod = httpMethod2;
                this.hasBody = hasBody2;
                if (!value.isEmpty()) {
                    int question = value.indexOf(63);
                    if (question != -1 && question < value.length() - 1) {
                        String queryParams = value.substring(question + 1);
                        if (PARAM_URL_REGEX.matcher(queryParams).find()) {
                            throw Utils.methodError(this.method, "URL query string \"%s\" must not have replace block. For dynamic query parameters use @Query.", queryParams);
                        }
                    }
                    this.relativeUrl = value;
                    this.relativeUrlParamNames = parsePathParameters(value);
                    return;
                }
                return;
            }
            throw Utils.methodError(this.method, "Only one HTTP method is allowed. Found: %s and %s.", str, httpMethod2);
        }

        private Headers parseHeaders(String[] headers2) {
            okhttp3.Headers.Builder builder = new okhttp3.Headers.Builder();
            for (String header : headers2) {
                int colon = header.indexOf(58);
                if (colon == -1 || colon == 0 || colon == header.length() - 1) {
                    throw Utils.methodError(this.method, "@Headers value must be in the form \"Name: Value\". Found: \"%s\"", header);
                }
                String headerName = header.substring(0, colon);
                String headerValue = header.substring(colon + 1).trim();
                if ("Content-Type".equalsIgnoreCase(headerName)) {
                    try {
                        this.contentType = MediaType.get(headerValue);
                    } catch (IllegalArgumentException e) {
                        throw Utils.methodError(this.method, e, "Malformed content type: %s", headerValue);
                    }
                } else {
                    builder.add(headerName, headerValue);
                }
            }
            return builder.build();
        }

        @Nullable
        private ParameterHandler<?> parseParameter(int p, Type parameterType, @Nullable Annotation[] annotations, boolean allowContinuation) {
            ParameterHandler<?> result = null;
            if (annotations != null) {
                ParameterHandler<?> result2 = null;
                for (Annotation annotation : annotations) {
                    ParameterHandler<?> annotationAction = parseParameterAnnotation(p, parameterType, annotations, annotation);
                    if (annotationAction != null) {
                        if (result2 == null) {
                            result2 = annotationAction;
                        } else {
                            throw Utils.parameterError(this.method, p, "Multiple Retrofit annotations found, only one allowed.", new Object[0]);
                        }
                    }
                }
                result = result2;
            }
            if (result != null) {
                return result;
            }
            if (allowContinuation) {
                try {
                    if (Utils.getRawType(parameterType) == Continuation.class) {
                        this.isKotlinSuspendFunction = true;
                        return null;
                    }
                } catch (NoClassDefFoundError e) {
                }
            }
            throw Utils.parameterError(this.method, p, "No Retrofit annotation found.", new Object[0]);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0042, code lost:
            if ("android.net.Uri".equals(((java.lang.Class) r12).getName()) != false) goto L_0x0050;
         */
        @javax.annotation.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private retrofit2.ParameterHandler<?> parseParameterAnnotation(int r11, java.lang.reflect.Type r12, java.lang.annotation.Annotation[] r13, java.lang.annotation.Annotation r14) {
            /*
                r10 = this;
                boolean r0 = r14 instanceof retrofit2.http.Url
                java.lang.String r1 = "@Path parameters may not be used with @Url."
                r2 = 1
                r3 = 0
                if (r0 == 0) goto L_0x009c
                r10.validateResolvableType(r11, r12)
                boolean r0 = r10.gotUrl
                if (r0 != 0) goto L_0x0091
                boolean r0 = r10.gotPath
                if (r0 != 0) goto L_0x0088
                boolean r0 = r10.gotQuery
                if (r0 != 0) goto L_0x007d
                boolean r0 = r10.gotQueryName
                if (r0 != 0) goto L_0x0072
                boolean r0 = r10.gotQueryMap
                if (r0 != 0) goto L_0x0067
                java.lang.String r0 = r10.relativeUrl
                if (r0 != 0) goto L_0x0058
                r10.gotUrl = r2
                java.lang.Class<okhttp3.HttpUrl> r0 = okhttp3.HttpUrl.class
                if (r12 == r0) goto L_0x0050
                java.lang.Class<java.lang.String> r0 = java.lang.String.class
                if (r12 == r0) goto L_0x0050
                java.lang.Class<java.net.URI> r0 = java.net.URI.class
                if (r12 == r0) goto L_0x0050
                boolean r0 = r12 instanceof java.lang.Class
                if (r0 == 0) goto L_0x0045
                r0 = r12
                java.lang.Class r0 = (java.lang.Class) r0
                java.lang.String r0 = r0.getName()
                java.lang.String r1 = "android.net.Uri"
                boolean r0 = r1.equals(r0)
                if (r0 == 0) goto L_0x0045
                goto L_0x0050
            L_0x0045:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "@Url must be okhttp3.HttpUrl, String, java.net.URI, or android.net.Uri type."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x0050:
                retrofit2.ParameterHandler$RelativeUrl r0 = new retrofit2.ParameterHandler$RelativeUrl
                java.lang.reflect.Method r1 = r10.method
                r0.<init>(r1, r11)
                return r0
            L_0x0058:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r2]
                java.lang.String r2 = r10.httpMethod
                r1[r3] = r2
                java.lang.String r2 = "@Url cannot be used with @%s URL"
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x0067:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "A @Url parameter must not come after a @QueryMap."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x0072:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "A @Url parameter must not come after a @QueryName."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x007d:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "A @Url parameter must not come after a @Query."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x0088:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r2 = new java.lang.Object[r3]
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r1, r2)
                throw r0
            L_0x0091:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "Multiple @Url method annotations found."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x009c:
                boolean r0 = r14 instanceof retrofit2.http.Path
                if (r0 == 0) goto L_0x0112
                r10.validateResolvableType(r11, r12)
                boolean r0 = r10.gotQuery
                if (r0 != 0) goto L_0x0107
                boolean r0 = r10.gotQueryName
                if (r0 != 0) goto L_0x00fc
                boolean r0 = r10.gotQueryMap
                if (r0 != 0) goto L_0x00f1
                boolean r0 = r10.gotUrl
                if (r0 != 0) goto L_0x00e8
                java.lang.String r0 = r10.relativeUrl
                if (r0 == 0) goto L_0x00d9
                r10.gotPath = r2
                r0 = r14
                retrofit2.http.Path r0 = (retrofit2.http.Path) r0
                java.lang.String r7 = r0.value()
                r10.validatePathName(r11, r7)
                retrofit2.Retrofit r1 = r10.retrofit
                retrofit2.Converter r8 = r1.stringConverter(r12, r13)
                retrofit2.ParameterHandler$Path r9 = new retrofit2.ParameterHandler$Path
                java.lang.reflect.Method r2 = r10.method
                boolean r6 = r0.encoded()
                r1 = r9
                r3 = r11
                r4 = r7
                r5 = r8
                r1.<init>(r2, r3, r4, r5, r6)
                return r9
            L_0x00d9:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r2]
                java.lang.String r2 = r10.httpMethod
                r1[r3] = r2
                java.lang.String r2 = "@Path can only be used with relative url on @%s"
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x00e8:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r2 = new java.lang.Object[r3]
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r1, r2)
                throw r0
            L_0x00f1:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "A @Path parameter must not come after a @QueryMap."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x00fc:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "A @Path parameter must not come after a @QueryName."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x0107:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "A @Path parameter must not come after a @Query."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x0112:
                boolean r0 = r14 instanceof retrofit2.http.Query
                java.lang.String r1 = "<String>)"
                java.lang.String r4 = " must include generic type (e.g., "
                if (r0 == 0) goto L_0x01a1
                r10.validateResolvableType(r11, r12)
                r0 = r14
                retrofit2.http.Query r0 = (retrofit2.http.Query) r0
                java.lang.String r5 = r0.value()
                boolean r6 = r0.encoded()
                java.lang.Class r7 = retrofit2.Utils.getRawType(r12)
                r10.gotQuery = r2
                java.lang.Class<java.lang.Iterable> r2 = java.lang.Iterable.class
                boolean r2 = r2.isAssignableFrom(r7)
                if (r2 == 0) goto L_0x0177
                boolean r2 = r12 instanceof java.lang.reflect.ParameterizedType
                if (r2 == 0) goto L_0x0151
                r1 = r12
                java.lang.reflect.ParameterizedType r1 = (java.lang.reflect.ParameterizedType) r1
                java.lang.reflect.Type r2 = retrofit2.Utils.getParameterUpperBound(r3, r1)
                retrofit2.Retrofit r3 = r10.retrofit
                retrofit2.Converter r3 = r3.stringConverter(r2, r13)
                retrofit2.ParameterHandler$Query r4 = new retrofit2.ParameterHandler$Query
                r4.<init>(r5, r3, r6)
                retrofit2.ParameterHandler r4 = r4.iterable()
                return r4
            L_0x0151:
                java.lang.reflect.Method r2 = r10.method
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = r7.getSimpleName()
                r8.append(r9)
                r8.append(r4)
                java.lang.String r4 = r7.getSimpleName()
                r8.append(r4)
                r8.append(r1)
                java.lang.String r1 = r8.toString()
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r2, r11, r1, r3)
                throw r1
            L_0x0177:
                boolean r1 = r7.isArray()
                if (r1 == 0) goto L_0x0195
                java.lang.Class r1 = r7.getComponentType()
                java.lang.Class r1 = boxIfPrimitive(r1)
                retrofit2.Retrofit r2 = r10.retrofit
                retrofit2.Converter r2 = r2.stringConverter(r1, r13)
                retrofit2.ParameterHandler$Query r3 = new retrofit2.ParameterHandler$Query
                r3.<init>(r5, r2, r6)
                retrofit2.ParameterHandler r3 = r3.array()
                return r3
            L_0x0195:
                retrofit2.Retrofit r1 = r10.retrofit
                retrofit2.Converter r1 = r1.stringConverter(r12, r13)
                retrofit2.ParameterHandler$Query r2 = new retrofit2.ParameterHandler$Query
                r2.<init>(r5, r1, r6)
                return r2
            L_0x01a1:
                boolean r0 = r14 instanceof retrofit2.http.QueryName
                if (r0 == 0) goto L_0x0228
                r10.validateResolvableType(r11, r12)
                r0 = r14
                retrofit2.http.QueryName r0 = (retrofit2.http.QueryName) r0
                boolean r5 = r0.encoded()
                java.lang.Class r6 = retrofit2.Utils.getRawType(r12)
                r10.gotQueryName = r2
                java.lang.Class<java.lang.Iterable> r2 = java.lang.Iterable.class
                boolean r2 = r2.isAssignableFrom(r6)
                if (r2 == 0) goto L_0x01fe
                boolean r2 = r12 instanceof java.lang.reflect.ParameterizedType
                if (r2 == 0) goto L_0x01d8
                r1 = r12
                java.lang.reflect.ParameterizedType r1 = (java.lang.reflect.ParameterizedType) r1
                java.lang.reflect.Type r2 = retrofit2.Utils.getParameterUpperBound(r3, r1)
                retrofit2.Retrofit r3 = r10.retrofit
                retrofit2.Converter r3 = r3.stringConverter(r2, r13)
                retrofit2.ParameterHandler$QueryName r4 = new retrofit2.ParameterHandler$QueryName
                r4.<init>(r3, r5)
                retrofit2.ParameterHandler r4 = r4.iterable()
                return r4
            L_0x01d8:
                java.lang.reflect.Method r2 = r10.method
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                java.lang.String r8 = r6.getSimpleName()
                r7.append(r8)
                r7.append(r4)
                java.lang.String r4 = r6.getSimpleName()
                r7.append(r4)
                r7.append(r1)
                java.lang.String r1 = r7.toString()
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r2, r11, r1, r3)
                throw r1
            L_0x01fe:
                boolean r1 = r6.isArray()
                if (r1 == 0) goto L_0x021c
                java.lang.Class r1 = r6.getComponentType()
                java.lang.Class r1 = boxIfPrimitive(r1)
                retrofit2.Retrofit r2 = r10.retrofit
                retrofit2.Converter r2 = r2.stringConverter(r1, r13)
                retrofit2.ParameterHandler$QueryName r3 = new retrofit2.ParameterHandler$QueryName
                r3.<init>(r2, r5)
                retrofit2.ParameterHandler r3 = r3.array()
                return r3
            L_0x021c:
                retrofit2.Retrofit r1 = r10.retrofit
                retrofit2.Converter r1 = r1.stringConverter(r12, r13)
                retrofit2.ParameterHandler$QueryName r2 = new retrofit2.ParameterHandler$QueryName
                r2.<init>(r1, r5)
                return r2
            L_0x0228:
                boolean r0 = r14 instanceof retrofit2.http.QueryMap
                java.lang.String r5 = "Map must include generic types (e.g., Map<String, String>)"
                if (r0 == 0) goto L_0x029b
                r10.validateResolvableType(r11, r12)
                java.lang.Class r0 = retrofit2.Utils.getRawType(r12)
                r10.gotQueryMap = r2
                java.lang.Class<java.util.Map> r1 = java.util.Map.class
                boolean r1 = r1.isAssignableFrom(r0)
                if (r1 == 0) goto L_0x0290
                java.lang.Class<java.util.Map> r1 = java.util.Map.class
                java.lang.reflect.Type r1 = retrofit2.Utils.getSupertype(r12, r0, r1)
                boolean r4 = r1 instanceof java.lang.reflect.ParameterizedType
                if (r4 == 0) goto L_0x0287
                r4 = r1
                java.lang.reflect.ParameterizedType r4 = (java.lang.reflect.ParameterizedType) r4
                java.lang.reflect.Type r5 = retrofit2.Utils.getParameterUpperBound(r3, r4)
                java.lang.Class<java.lang.String> r6 = java.lang.String.class
                if (r6 != r5) goto L_0x026d
                java.lang.reflect.Type r2 = retrofit2.Utils.getParameterUpperBound(r2, r4)
                retrofit2.Retrofit r3 = r10.retrofit
                retrofit2.Converter r3 = r3.stringConverter(r2, r13)
                retrofit2.ParameterHandler$QueryMap r6 = new retrofit2.ParameterHandler$QueryMap
                java.lang.reflect.Method r7 = r10.method
                r8 = r14
                retrofit2.http.QueryMap r8 = (retrofit2.http.QueryMap) r8
                boolean r8 = r8.encoded()
                r6.<init>(r7, r11, r3, r8)
                return r6
            L_0x026d:
                java.lang.reflect.Method r2 = r10.method
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "@QueryMap keys must be of type String: "
                r6.append(r7)
                r6.append(r5)
                java.lang.String r6 = r6.toString()
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r2 = retrofit2.Utils.parameterError(r2, r11, r6, r3)
                throw r2
            L_0x0287:
                java.lang.reflect.Method r2 = r10.method
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r2 = retrofit2.Utils.parameterError(r2, r11, r5, r3)
                throw r2
            L_0x0290:
                java.lang.reflect.Method r1 = r10.method
                java.lang.Object[] r2 = new java.lang.Object[r3]
                java.lang.String r3 = "@QueryMap parameter type must be Map."
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r1, r11, r3, r2)
                throw r1
            L_0x029b:
                boolean r0 = r14 instanceof retrofit2.http.Header
                if (r0 == 0) goto L_0x0320
                r10.validateResolvableType(r11, r12)
                r0 = r14
                retrofit2.http.Header r0 = (retrofit2.http.Header) r0
                java.lang.String r2 = r0.value()
                java.lang.Class r5 = retrofit2.Utils.getRawType(r12)
                java.lang.Class<java.lang.Iterable> r6 = java.lang.Iterable.class
                boolean r6 = r6.isAssignableFrom(r5)
                if (r6 == 0) goto L_0x02f6
                boolean r6 = r12 instanceof java.lang.reflect.ParameterizedType
                if (r6 == 0) goto L_0x02d0
                r1 = r12
                java.lang.reflect.ParameterizedType r1 = (java.lang.reflect.ParameterizedType) r1
                java.lang.reflect.Type r3 = retrofit2.Utils.getParameterUpperBound(r3, r1)
                retrofit2.Retrofit r4 = r10.retrofit
                retrofit2.Converter r4 = r4.stringConverter(r3, r13)
                retrofit2.ParameterHandler$Header r6 = new retrofit2.ParameterHandler$Header
                r6.<init>(r2, r4)
                retrofit2.ParameterHandler r6 = r6.iterable()
                return r6
            L_0x02d0:
                java.lang.reflect.Method r6 = r10.method
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                java.lang.String r8 = r5.getSimpleName()
                r7.append(r8)
                r7.append(r4)
                java.lang.String r4 = r5.getSimpleName()
                r7.append(r4)
                r7.append(r1)
                java.lang.String r1 = r7.toString()
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r6, r11, r1, r3)
                throw r1
            L_0x02f6:
                boolean r1 = r5.isArray()
                if (r1 == 0) goto L_0x0314
                java.lang.Class r1 = r5.getComponentType()
                java.lang.Class r1 = boxIfPrimitive(r1)
                retrofit2.Retrofit r3 = r10.retrofit
                retrofit2.Converter r3 = r3.stringConverter(r1, r13)
                retrofit2.ParameterHandler$Header r4 = new retrofit2.ParameterHandler$Header
                r4.<init>(r2, r3)
                retrofit2.ParameterHandler r4 = r4.array()
                return r4
            L_0x0314:
                retrofit2.Retrofit r1 = r10.retrofit
                retrofit2.Converter r1 = r1.stringConverter(r12, r13)
                retrofit2.ParameterHandler$Header r3 = new retrofit2.ParameterHandler$Header
                r3.<init>(r2, r1)
                return r3
            L_0x0320:
                boolean r0 = r14 instanceof retrofit2.http.HeaderMap
                if (r0 == 0) goto L_0x0394
                java.lang.Class<okhttp3.Headers> r0 = okhttp3.Headers.class
                if (r12 != r0) goto L_0x0330
                retrofit2.ParameterHandler$Headers r0 = new retrofit2.ParameterHandler$Headers
                java.lang.reflect.Method r1 = r10.method
                r0.<init>(r1, r11)
                return r0
            L_0x0330:
                r10.validateResolvableType(r11, r12)
                java.lang.Class r0 = retrofit2.Utils.getRawType(r12)
                java.lang.Class<java.util.Map> r1 = java.util.Map.class
                boolean r1 = r1.isAssignableFrom(r0)
                if (r1 == 0) goto L_0x0389
                java.lang.Class<java.util.Map> r1 = java.util.Map.class
                java.lang.reflect.Type r1 = retrofit2.Utils.getSupertype(r12, r0, r1)
                boolean r4 = r1 instanceof java.lang.reflect.ParameterizedType
                if (r4 == 0) goto L_0x0380
                r4 = r1
                java.lang.reflect.ParameterizedType r4 = (java.lang.reflect.ParameterizedType) r4
                java.lang.reflect.Type r5 = retrofit2.Utils.getParameterUpperBound(r3, r4)
                java.lang.Class<java.lang.String> r6 = java.lang.String.class
                if (r6 != r5) goto L_0x0366
                java.lang.reflect.Type r2 = retrofit2.Utils.getParameterUpperBound(r2, r4)
                retrofit2.Retrofit r3 = r10.retrofit
                retrofit2.Converter r3 = r3.stringConverter(r2, r13)
                retrofit2.ParameterHandler$HeaderMap r6 = new retrofit2.ParameterHandler$HeaderMap
                java.lang.reflect.Method r7 = r10.method
                r6.<init>(r7, r11, r3)
                return r6
            L_0x0366:
                java.lang.reflect.Method r2 = r10.method
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "@HeaderMap keys must be of type String: "
                r6.append(r7)
                r6.append(r5)
                java.lang.String r6 = r6.toString()
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r2 = retrofit2.Utils.parameterError(r2, r11, r6, r3)
                throw r2
            L_0x0380:
                java.lang.reflect.Method r2 = r10.method
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r2 = retrofit2.Utils.parameterError(r2, r11, r5, r3)
                throw r2
            L_0x0389:
                java.lang.reflect.Method r1 = r10.method
                java.lang.Object[] r2 = new java.lang.Object[r3]
                java.lang.String r3 = "@HeaderMap parameter type must be Map."
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r1, r11, r3, r2)
                throw r1
            L_0x0394:
                boolean r0 = r14 instanceof retrofit2.http.Field
                if (r0 == 0) goto L_0x042e
                r10.validateResolvableType(r11, r12)
                boolean r0 = r10.isFormEncoded
                if (r0 == 0) goto L_0x0423
                r0 = r14
                retrofit2.http.Field r0 = (retrofit2.http.Field) r0
                java.lang.String r5 = r0.value()
                boolean r6 = r0.encoded()
                r10.gotField = r2
                java.lang.Class r2 = retrofit2.Utils.getRawType(r12)
                java.lang.Class<java.lang.Iterable> r7 = java.lang.Iterable.class
                boolean r7 = r7.isAssignableFrom(r2)
                if (r7 == 0) goto L_0x03f9
                boolean r7 = r12 instanceof java.lang.reflect.ParameterizedType
                if (r7 == 0) goto L_0x03d3
                r1 = r12
                java.lang.reflect.ParameterizedType r1 = (java.lang.reflect.ParameterizedType) r1
                java.lang.reflect.Type r3 = retrofit2.Utils.getParameterUpperBound(r3, r1)
                retrofit2.Retrofit r4 = r10.retrofit
                retrofit2.Converter r4 = r4.stringConverter(r3, r13)
                retrofit2.ParameterHandler$Field r7 = new retrofit2.ParameterHandler$Field
                r7.<init>(r5, r4, r6)
                retrofit2.ParameterHandler r7 = r7.iterable()
                return r7
            L_0x03d3:
                java.lang.reflect.Method r7 = r10.method
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = r2.getSimpleName()
                r8.append(r9)
                r8.append(r4)
                java.lang.String r4 = r2.getSimpleName()
                r8.append(r4)
                r8.append(r1)
                java.lang.String r1 = r8.toString()
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r7, r11, r1, r3)
                throw r1
            L_0x03f9:
                boolean r1 = r2.isArray()
                if (r1 == 0) goto L_0x0417
                java.lang.Class r1 = r2.getComponentType()
                java.lang.Class r1 = boxIfPrimitive(r1)
                retrofit2.Retrofit r3 = r10.retrofit
                retrofit2.Converter r3 = r3.stringConverter(r1, r13)
                retrofit2.ParameterHandler$Field r4 = new retrofit2.ParameterHandler$Field
                r4.<init>(r5, r3, r6)
                retrofit2.ParameterHandler r4 = r4.array()
                return r4
            L_0x0417:
                retrofit2.Retrofit r1 = r10.retrofit
                retrofit2.Converter r1 = r1.stringConverter(r12, r13)
                retrofit2.ParameterHandler$Field r3 = new retrofit2.ParameterHandler$Field
                r3.<init>(r5, r1, r6)
                return r3
            L_0x0423:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "@Field parameters can only be used with form encoding."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x042e:
                boolean r0 = r14 instanceof retrofit2.http.FieldMap
                if (r0 == 0) goto L_0x04ae
                r10.validateResolvableType(r11, r12)
                boolean r0 = r10.isFormEncoded
                if (r0 == 0) goto L_0x04a3
                java.lang.Class r0 = retrofit2.Utils.getRawType(r12)
                java.lang.Class<java.util.Map> r1 = java.util.Map.class
                boolean r1 = r1.isAssignableFrom(r0)
                if (r1 == 0) goto L_0x0498
                java.lang.Class<java.util.Map> r1 = java.util.Map.class
                java.lang.reflect.Type r1 = retrofit2.Utils.getSupertype(r12, r0, r1)
                boolean r4 = r1 instanceof java.lang.reflect.ParameterizedType
                if (r4 == 0) goto L_0x048f
                r4 = r1
                java.lang.reflect.ParameterizedType r4 = (java.lang.reflect.ParameterizedType) r4
                java.lang.reflect.Type r5 = retrofit2.Utils.getParameterUpperBound(r3, r4)
                java.lang.Class<java.lang.String> r6 = java.lang.String.class
                if (r6 != r5) goto L_0x0475
                java.lang.reflect.Type r3 = retrofit2.Utils.getParameterUpperBound(r2, r4)
                retrofit2.Retrofit r6 = r10.retrofit
                retrofit2.Converter r6 = r6.stringConverter(r3, r13)
                r10.gotField = r2
                retrofit2.ParameterHandler$FieldMap r2 = new retrofit2.ParameterHandler$FieldMap
                java.lang.reflect.Method r7 = r10.method
                r8 = r14
                retrofit2.http.FieldMap r8 = (retrofit2.http.FieldMap) r8
                boolean r8 = r8.encoded()
                r2.<init>(r7, r11, r6, r8)
                return r2
            L_0x0475:
                java.lang.reflect.Method r2 = r10.method
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "@FieldMap keys must be of type String: "
                r6.append(r7)
                r6.append(r5)
                java.lang.String r6 = r6.toString()
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r2 = retrofit2.Utils.parameterError(r2, r11, r6, r3)
                throw r2
            L_0x048f:
                java.lang.reflect.Method r2 = r10.method
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r2 = retrofit2.Utils.parameterError(r2, r11, r5, r3)
                throw r2
            L_0x0498:
                java.lang.reflect.Method r1 = r10.method
                java.lang.Object[] r2 = new java.lang.Object[r3]
                java.lang.String r3 = "@FieldMap parameter type must be Map."
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r1, r11, r3, r2)
                throw r1
            L_0x04a3:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "@FieldMap parameters can only be used with form encoding."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x04ae:
                boolean r0 = r14 instanceof retrofit2.http.Part
                if (r0 == 0) goto L_0x064b
                r10.validateResolvableType(r11, r12)
                boolean r0 = r10.isMultipart
                if (r0 == 0) goto L_0x0640
                r0 = r14
                retrofit2.http.Part r0 = (retrofit2.http.Part) r0
                r10.gotPart = r2
                java.lang.String r5 = r0.value()
                java.lang.Class r6 = retrofit2.Utils.getRawType(r12)
                boolean r7 = r5.isEmpty()
                if (r7 == 0) goto L_0x0559
                java.lang.Class<java.lang.Iterable> r2 = java.lang.Iterable.class
                boolean r2 = r2.isAssignableFrom(r6)
                java.lang.String r7 = "@Part annotation must supply a name or use MultipartBody.Part parameter type."
                if (r2 == 0) goto L_0x0523
                boolean r2 = r12 instanceof java.lang.reflect.ParameterizedType
                if (r2 == 0) goto L_0x04fd
                r1 = r12
                java.lang.reflect.ParameterizedType r1 = (java.lang.reflect.ParameterizedType) r1
                java.lang.reflect.Type r2 = retrofit2.Utils.getParameterUpperBound(r3, r1)
                java.lang.Class<okhttp3.MultipartBody$Part> r4 = okhttp3.MultipartBody.Part.class
                java.lang.Class r8 = retrofit2.Utils.getRawType(r2)
                boolean r4 = r4.isAssignableFrom(r8)
                if (r4 == 0) goto L_0x04f4
                retrofit2.ParameterHandler$RawPart r3 = retrofit2.ParameterHandler.RawPart.INSTANCE
                retrofit2.ParameterHandler r3 = r3.iterable()
                return r3
            L_0x04f4:
                java.lang.reflect.Method r4 = r10.method
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r3 = retrofit2.Utils.parameterError(r4, r11, r7, r3)
                throw r3
            L_0x04fd:
                java.lang.reflect.Method r2 = r10.method
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                java.lang.String r8 = r6.getSimpleName()
                r7.append(r8)
                r7.append(r4)
                java.lang.String r4 = r6.getSimpleName()
                r7.append(r4)
                r7.append(r1)
                java.lang.String r1 = r7.toString()
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r2, r11, r1, r3)
                throw r1
            L_0x0523:
                boolean r1 = r6.isArray()
                if (r1 == 0) goto L_0x0545
                java.lang.Class r1 = r6.getComponentType()
                java.lang.Class<okhttp3.MultipartBody$Part> r2 = okhttp3.MultipartBody.Part.class
                boolean r2 = r2.isAssignableFrom(r1)
                if (r2 == 0) goto L_0x053c
                retrofit2.ParameterHandler$RawPart r2 = retrofit2.ParameterHandler.RawPart.INSTANCE
                retrofit2.ParameterHandler r2 = r2.array()
                return r2
            L_0x053c:
                java.lang.reflect.Method r2 = r10.method
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r2 = retrofit2.Utils.parameterError(r2, r11, r7, r3)
                throw r2
            L_0x0545:
                java.lang.Class<okhttp3.MultipartBody$Part> r1 = okhttp3.MultipartBody.Part.class
                boolean r1 = r1.isAssignableFrom(r6)
                if (r1 == 0) goto L_0x0550
                retrofit2.ParameterHandler$RawPart r1 = retrofit2.ParameterHandler.RawPart.INSTANCE
                return r1
            L_0x0550:
                java.lang.reflect.Method r1 = r10.method
                java.lang.Object[] r2 = new java.lang.Object[r3]
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r1, r11, r7, r2)
                throw r1
            L_0x0559:
                r7 = 4
                java.lang.String[] r7 = new java.lang.String[r7]
                java.lang.String r8 = "Content-Disposition"
                r7[r3] = r8
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = "form-data; name=\""
                r8.append(r9)
                r8.append(r5)
                java.lang.String r9 = "\""
                r8.append(r9)
                java.lang.String r8 = r8.toString()
                r7[r2] = r8
                r2 = 2
                java.lang.String r8 = "Content-Transfer-Encoding"
                r7[r2] = r8
                r2 = 3
                java.lang.String r8 = r0.encoding()
                r7[r2] = r8
                okhttp3.Headers r2 = okhttp3.Headers.m36of(r7)
                java.lang.Class<java.lang.Iterable> r7 = java.lang.Iterable.class
                boolean r7 = r7.isAssignableFrom(r6)
                java.lang.String r8 = "@Part parameters using the MultipartBody.Part must not include a part name in the annotation."
                if (r7 == 0) goto L_0x05ec
                boolean r7 = r12 instanceof java.lang.reflect.ParameterizedType
                if (r7 == 0) goto L_0x05c6
                r1 = r12
                java.lang.reflect.ParameterizedType r1 = (java.lang.reflect.ParameterizedType) r1
                java.lang.reflect.Type r4 = retrofit2.Utils.getParameterUpperBound(r3, r1)
                java.lang.Class<okhttp3.MultipartBody$Part> r7 = okhttp3.MultipartBody.Part.class
                java.lang.Class r9 = retrofit2.Utils.getRawType(r4)
                boolean r7 = r7.isAssignableFrom(r9)
                if (r7 != 0) goto L_0x05bd
                retrofit2.Retrofit r3 = r10.retrofit
                java.lang.annotation.Annotation[] r7 = r10.methodAnnotations
                retrofit2.Converter r3 = r3.requestBodyConverter(r4, r13, r7)
                retrofit2.ParameterHandler$Part r7 = new retrofit2.ParameterHandler$Part
                java.lang.reflect.Method r8 = r10.method
                r7.<init>(r8, r11, r2, r3)
                retrofit2.ParameterHandler r7 = r7.iterable()
                return r7
            L_0x05bd:
                java.lang.reflect.Method r7 = r10.method
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r3 = retrofit2.Utils.parameterError(r7, r11, r8, r3)
                throw r3
            L_0x05c6:
                java.lang.reflect.Method r7 = r10.method
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>()
                java.lang.String r9 = r6.getSimpleName()
                r8.append(r9)
                r8.append(r4)
                java.lang.String r4 = r6.getSimpleName()
                r8.append(r4)
                r8.append(r1)
                java.lang.String r1 = r8.toString()
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r7, r11, r1, r3)
                throw r1
            L_0x05ec:
                boolean r1 = r6.isArray()
                if (r1 == 0) goto L_0x061f
                java.lang.Class r1 = r6.getComponentType()
                java.lang.Class r1 = boxIfPrimitive(r1)
                java.lang.Class<okhttp3.MultipartBody$Part> r4 = okhttp3.MultipartBody.Part.class
                boolean r4 = r4.isAssignableFrom(r1)
                if (r4 != 0) goto L_0x0616
                retrofit2.Retrofit r3 = r10.retrofit
                java.lang.annotation.Annotation[] r4 = r10.methodAnnotations
                retrofit2.Converter r3 = r3.requestBodyConverter(r1, r13, r4)
                retrofit2.ParameterHandler$Part r4 = new retrofit2.ParameterHandler$Part
                java.lang.reflect.Method r7 = r10.method
                r4.<init>(r7, r11, r2, r3)
                retrofit2.ParameterHandler r4 = r4.array()
                return r4
            L_0x0616:
                java.lang.reflect.Method r4 = r10.method
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r3 = retrofit2.Utils.parameterError(r4, r11, r8, r3)
                throw r3
            L_0x061f:
                java.lang.Class<okhttp3.MultipartBody$Part> r1 = okhttp3.MultipartBody.Part.class
                boolean r1 = r1.isAssignableFrom(r6)
                if (r1 != 0) goto L_0x0637
                retrofit2.Retrofit r1 = r10.retrofit
                java.lang.annotation.Annotation[] r3 = r10.methodAnnotations
                retrofit2.Converter r1 = r1.requestBodyConverter(r12, r13, r3)
                retrofit2.ParameterHandler$Part r3 = new retrofit2.ParameterHandler$Part
                java.lang.reflect.Method r4 = r10.method
                r3.<init>(r4, r11, r2, r1)
                return r3
            L_0x0637:
                java.lang.reflect.Method r1 = r10.method
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r1, r11, r8, r3)
                throw r1
            L_0x0640:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "@Part parameters can only be used with multipart encoding."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x064b:
                boolean r0 = r14 instanceof retrofit2.http.PartMap
                if (r0 == 0) goto L_0x06e4
                r10.validateResolvableType(r11, r12)
                boolean r0 = r10.isMultipart
                if (r0 == 0) goto L_0x06d9
                r10.gotPart = r2
                java.lang.Class r0 = retrofit2.Utils.getRawType(r12)
                java.lang.Class<java.util.Map> r1 = java.util.Map.class
                boolean r1 = r1.isAssignableFrom(r0)
                if (r1 == 0) goto L_0x06ce
                java.lang.Class<java.util.Map> r1 = java.util.Map.class
                java.lang.reflect.Type r1 = retrofit2.Utils.getSupertype(r12, r0, r1)
                boolean r4 = r1 instanceof java.lang.reflect.ParameterizedType
                if (r4 == 0) goto L_0x06c5
                r4 = r1
                java.lang.reflect.ParameterizedType r4 = (java.lang.reflect.ParameterizedType) r4
                java.lang.reflect.Type r5 = retrofit2.Utils.getParameterUpperBound(r3, r4)
                java.lang.Class<java.lang.String> r6 = java.lang.String.class
                if (r6 != r5) goto L_0x06ab
                java.lang.reflect.Type r2 = retrofit2.Utils.getParameterUpperBound(r2, r4)
                java.lang.Class<okhttp3.MultipartBody$Part> r6 = okhttp3.MultipartBody.Part.class
                java.lang.Class r7 = retrofit2.Utils.getRawType(r2)
                boolean r6 = r6.isAssignableFrom(r7)
                if (r6 != 0) goto L_0x06a0
                retrofit2.Retrofit r3 = r10.retrofit
                java.lang.annotation.Annotation[] r6 = r10.methodAnnotations
                retrofit2.Converter r3 = r3.requestBodyConverter(r2, r13, r6)
                r6 = r14
                retrofit2.http.PartMap r6 = (retrofit2.http.PartMap) r6
                retrofit2.ParameterHandler$PartMap r7 = new retrofit2.ParameterHandler$PartMap
                java.lang.reflect.Method r8 = r10.method
                java.lang.String r9 = r6.encoding()
                r7.<init>(r8, r11, r3, r9)
                return r7
            L_0x06a0:
                java.lang.reflect.Method r6 = r10.method
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.String r7 = "@PartMap values cannot be MultipartBody.Part. Use @Part List<Part> or a different value type instead."
                java.lang.RuntimeException r3 = retrofit2.Utils.parameterError(r6, r11, r7, r3)
                throw r3
            L_0x06ab:
                java.lang.reflect.Method r2 = r10.method
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "@PartMap keys must be of type String: "
                r6.append(r7)
                r6.append(r5)
                java.lang.String r6 = r6.toString()
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r2 = retrofit2.Utils.parameterError(r2, r11, r6, r3)
                throw r2
            L_0x06c5:
                java.lang.reflect.Method r2 = r10.method
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r2 = retrofit2.Utils.parameterError(r2, r11, r5, r3)
                throw r2
            L_0x06ce:
                java.lang.reflect.Method r1 = r10.method
                java.lang.Object[] r2 = new java.lang.Object[r3]
                java.lang.String r3 = "@PartMap parameter type must be Map."
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r1, r11, r3, r2)
                throw r1
            L_0x06d9:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "@PartMap parameters can only be used with multipart encoding."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x06e4:
                boolean r0 = r14 instanceof retrofit2.http.Body
                if (r0 == 0) goto L_0x072e
                r10.validateResolvableType(r11, r12)
                boolean r0 = r10.isFormEncoded
                if (r0 != 0) goto L_0x0723
                boolean r0 = r10.isMultipart
                if (r0 != 0) goto L_0x0723
                boolean r0 = r10.gotBody
                if (r0 != 0) goto L_0x0718
                retrofit2.Retrofit r0 = r10.retrofit     // Catch:{ RuntimeException -> 0x070a }
                java.lang.annotation.Annotation[] r1 = r10.methodAnnotations     // Catch:{ RuntimeException -> 0x070a }
                retrofit2.Converter r0 = r0.requestBodyConverter(r12, r13, r1)     // Catch:{ RuntimeException -> 0x070a }
                r10.gotBody = r2
                retrofit2.ParameterHandler$Body r1 = new retrofit2.ParameterHandler$Body
                java.lang.reflect.Method r2 = r10.method
                r1.<init>(r2, r11, r0)
                return r1
            L_0x070a:
                r0 = move-exception
                java.lang.reflect.Method r1 = r10.method
                java.lang.Object[] r2 = new java.lang.Object[r2]
                r2[r3] = r12
                java.lang.String r3 = "Unable to create @Body converter for %s"
                java.lang.RuntimeException r1 = retrofit2.Utils.parameterError(r1, r0, r11, r3, r2)
                throw r1
            L_0x0718:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "Multiple @Body method annotations found."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x0723:
                java.lang.reflect.Method r0 = r10.method
                java.lang.Object[] r1 = new java.lang.Object[r3]
                java.lang.String r2 = "@Body parameters cannot be used with form or multi-part encoding."
                java.lang.RuntimeException r0 = retrofit2.Utils.parameterError(r0, r11, r2, r1)
                throw r0
            L_0x072e:
                boolean r0 = r14 instanceof retrofit2.http.Tag
                if (r0 == 0) goto L_0x0787
                r10.validateResolvableType(r11, r12)
                java.lang.Class r0 = retrofit2.Utils.getRawType(r12)
                int r1 = r11 + -1
            L_0x073b:
                if (r1 < 0) goto L_0x0781
                retrofit2.ParameterHandler<?>[] r2 = r10.parameterHandlers
                r2 = r2[r1]
                boolean r4 = r2 instanceof retrofit2.ParameterHandler.Tag
                if (r4 == 0) goto L_0x077e
                r4 = r2
                retrofit2.ParameterHandler$Tag r4 = (retrofit2.ParameterHandler.Tag) r4
                java.lang.Class<T> r4 = r4.cls
                boolean r4 = r4.equals(r0)
                if (r4 != 0) goto L_0x0751
                goto L_0x077e
            L_0x0751:
                java.lang.reflect.Method r4 = r10.method
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "@Tag type "
                r5.append(r6)
                java.lang.String r6 = r0.getName()
                r5.append(r6)
                java.lang.String r6 = " is duplicate of parameter #"
                r5.append(r6)
                int r6 = r1 + 1
                r5.append(r6)
                java.lang.String r6 = " and would always overwrite its value."
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.RuntimeException r3 = retrofit2.Utils.parameterError(r4, r11, r5, r3)
                throw r3
            L_0x077e:
                int r1 = r1 + -1
                goto L_0x073b
            L_0x0781:
                retrofit2.ParameterHandler$Tag r1 = new retrofit2.ParameterHandler$Tag
                r1.<init>(r0)
                return r1
            L_0x0787:
                r0 = 0
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: retrofit2.RequestFactory.Builder.parseParameterAnnotation(int, java.lang.reflect.Type, java.lang.annotation.Annotation[], java.lang.annotation.Annotation):retrofit2.ParameterHandler");
        }

        private void validateResolvableType(int p, Type type) {
            if (Utils.hasUnresolvableType(type)) {
                throw Utils.parameterError(this.method, p, "Parameter type must not include a type variable or wildcard: %s", type);
            }
        }

        private void validatePathName(int p, String name) {
            if (!PARAM_NAME_REGEX.matcher(name).matches()) {
                throw Utils.parameterError(this.method, p, "@Path parameter name must match %s. Found: %s", PARAM_URL_REGEX.pattern(), name);
            } else if (!this.relativeUrlParamNames.contains(name)) {
                throw Utils.parameterError(this.method, p, "URL \"%s\" does not contain \"{%s}\".", this.relativeUrl, name);
            }
        }

        static Set<String> parsePathParameters(String path) {
            Matcher m = PARAM_URL_REGEX.matcher(path);
            Set<String> patterns = new LinkedHashSet<>();
            while (m.find()) {
                patterns.add(m.group(1));
            }
            return patterns;
        }

        private static Class<?> boxIfPrimitive(Class<?> type) {
            if (Boolean.TYPE == type) {
                return Boolean.class;
            }
            if (Byte.TYPE == type) {
                return Byte.class;
            }
            if (Character.TYPE == type) {
                return Character.class;
            }
            if (Double.TYPE == type) {
                return Double.class;
            }
            if (Float.TYPE == type) {
                return Float.class;
            }
            if (Integer.TYPE == type) {
                return Integer.class;
            }
            if (Long.TYPE == type) {
                return Long.class;
            }
            if (Short.TYPE == type) {
                return Short.class;
            }
            return type;
        }
    }

    static RequestFactory parseAnnotations(Retrofit retrofit, Method method2) {
        return new Builder(retrofit, method2).build();
    }

    RequestFactory(Builder builder) {
        this.method = builder.method;
        this.baseUrl = builder.retrofit.baseUrl;
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.headers = builder.headers;
        this.contentType = builder.contentType;
        this.hasBody = builder.hasBody;
        this.isFormEncoded = builder.isFormEncoded;
        this.isMultipart = builder.isMultipart;
        this.parameterHandlers = builder.parameterHandlers;
        this.isKotlinSuspendFunction = builder.isKotlinSuspendFunction;
    }

    /* access modifiers changed from: 0000 */
    public Request create(Object[] args) throws IOException {
        ParameterHandler<?>[] parameterHandlerArr = this.parameterHandlers;
        int argumentCount = args.length;
        if (argumentCount == parameterHandlerArr.length) {
            RequestBuilder requestBuilder = new RequestBuilder(this.httpMethod, this.baseUrl, this.relativeUrl, this.headers, this.contentType, this.hasBody, this.isFormEncoded, this.isMultipart);
            if (this.isKotlinSuspendFunction) {
                argumentCount--;
            }
            List<Object> argumentList = new ArrayList<>(argumentCount);
            for (int p = 0; p < argumentCount; p++) {
                argumentList.add(args[p]);
                parameterHandlerArr[p].apply(requestBuilder, args[p]);
            }
            return requestBuilder.get().tag(Invocation.class, new Invocation(this.method, argumentList)).build();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Argument count (");
        sb.append(argumentCount);
        sb.append(") doesn't match expected count (");
        sb.append(parameterHandlerArr.length);
        sb.append(")");
        throw new IllegalArgumentException(sb.toString());
    }
}
