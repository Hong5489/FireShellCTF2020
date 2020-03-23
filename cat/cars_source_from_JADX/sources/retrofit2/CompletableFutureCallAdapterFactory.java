package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import retrofit2.CallAdapter.Factory;

final class CompletableFutureCallAdapterFactory extends Factory {
    static final Factory INSTANCE = new CompletableFutureCallAdapterFactory();

    private static final class BodyCallAdapter<R> implements CallAdapter<R, CompletableFuture<R>> {
        private final Type responseType;

        BodyCallAdapter(Type responseType2) {
            this.responseType = responseType2;
        }

        public Type responseType() {
            return this.responseType;
        }

        public CompletableFuture<R> adapt(final Call<R> call) {
            final CompletableFuture<R> future = new CompletableFuture<R>() {
                public boolean cancel(boolean mayInterruptIfRunning) {
                    if (mayInterruptIfRunning) {
                        call.cancel();
                    }
                    return super.cancel(mayInterruptIfRunning);
                }
            };
            call.enqueue(new Callback<R>() {
                public void onResponse(Call<R> call, Response<R> response) {
                    if (response.isSuccessful()) {
                        future.complete(response.body());
                    } else {
                        future.completeExceptionally(new HttpException(response));
                    }
                }

                public void onFailure(Call<R> call, Throwable t) {
                    future.completeExceptionally(t);
                }
            });
            return future;
        }
    }

    private static final class ResponseCallAdapter<R> implements CallAdapter<R, CompletableFuture<Response<R>>> {
        private final Type responseType;

        ResponseCallAdapter(Type responseType2) {
            this.responseType = responseType2;
        }

        public Type responseType() {
            return this.responseType;
        }

        public CompletableFuture<Response<R>> adapt(final Call<R> call) {
            final CompletableFuture<Response<R>> future = new CompletableFuture<Response<R>>() {
                public boolean cancel(boolean mayInterruptIfRunning) {
                    if (mayInterruptIfRunning) {
                        call.cancel();
                    }
                    return super.cancel(mayInterruptIfRunning);
                }
            };
            call.enqueue(new Callback<R>() {
                public void onResponse(Call<R> call, Response<R> response) {
                    future.complete(response);
                }

                public void onFailure(Call<R> call, Throwable t) {
                    future.completeExceptionally(t);
                }
            });
            return future;
        }
    }

    CompletableFutureCallAdapterFactory() {
    }

    @Nullable
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != CompletableFuture.class) {
            return null;
        }
        if (returnType instanceof ParameterizedType) {
            Type innerType = getParameterUpperBound(0, (ParameterizedType) returnType);
            if (getRawType(innerType) != Response.class) {
                return new BodyCallAdapter(innerType);
            }
            if (innerType instanceof ParameterizedType) {
                return new ResponseCallAdapter(getParameterUpperBound(0, (ParameterizedType) innerType));
            }
            throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>");
        }
        throw new IllegalStateException("CompletableFuture return type must be parameterized as CompletableFuture<Foo> or CompletableFuture<? extends Foo>");
    }
}
