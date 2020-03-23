package retrofit2;

import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;

public final class Response<T> {
    @Nullable
    private final T body;
    @Nullable
    private final ResponseBody errorBody;
    private final okhttp3.Response rawResponse;

    public static <T> Response<T> success(@Nullable T body2) {
        return success(body2, new Builder().code(200).message("OK").protocol(Protocol.HTTP_1_1).request(new Request.Builder().url("http://localhost/").build()).build());
    }

    public static <T> Response<T> success(int code, @Nullable T body2) {
        if (code >= 200 && code < 300) {
            return success(body2, new Builder().code(code).message("Response.success()").protocol(Protocol.HTTP_1_1).request(new Request.Builder().url("http://localhost/").build()).build());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("code < 200 or >= 300: ");
        sb.append(code);
        throw new IllegalArgumentException(sb.toString());
    }

    public static <T> Response<T> success(@Nullable T body2, Headers headers) {
        Objects.requireNonNull(headers, "headers == null");
        return success(body2, new Builder().code(200).message("OK").protocol(Protocol.HTTP_1_1).headers(headers).request(new Request.Builder().url("http://localhost/").build()).build());
    }

    public static <T> Response<T> success(@Nullable T body2, okhttp3.Response rawResponse2) {
        Objects.requireNonNull(rawResponse2, "rawResponse == null");
        if (rawResponse2.isSuccessful()) {
            return new Response<>(rawResponse2, body2, null);
        }
        throw new IllegalArgumentException("rawResponse must be successful response");
    }

    public static <T> Response<T> error(int code, ResponseBody body2) {
        Objects.requireNonNull(body2, "body == null");
        if (code >= 400) {
            return error(body2, new Builder().body(new NoContentResponseBody(body2.contentType(), body2.contentLength())).code(code).message("Response.error()").protocol(Protocol.HTTP_1_1).request(new Request.Builder().url("http://localhost/").build()).build());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("code < 400: ");
        sb.append(code);
        throw new IllegalArgumentException(sb.toString());
    }

    public static <T> Response<T> error(ResponseBody body2, okhttp3.Response rawResponse2) {
        Objects.requireNonNull(body2, "body == null");
        Objects.requireNonNull(rawResponse2, "rawResponse == null");
        if (!rawResponse2.isSuccessful()) {
            return new Response<>(rawResponse2, null, body2);
        }
        throw new IllegalArgumentException("rawResponse should not be successful response");
    }

    private Response(okhttp3.Response rawResponse2, @Nullable T body2, @Nullable ResponseBody errorBody2) {
        this.rawResponse = rawResponse2;
        this.body = body2;
        this.errorBody = errorBody2;
    }

    public okhttp3.Response raw() {
        return this.rawResponse;
    }

    public int code() {
        return this.rawResponse.code();
    }

    public String message() {
        return this.rawResponse.message();
    }

    public Headers headers() {
        return this.rawResponse.headers();
    }

    public boolean isSuccessful() {
        return this.rawResponse.isSuccessful();
    }

    @Nullable
    public T body() {
        return this.body;
    }

    @Nullable
    public ResponseBody errorBody() {
        return this.errorBody;
    }

    public String toString() {
        return this.rawResponse.toString();
    }
}
