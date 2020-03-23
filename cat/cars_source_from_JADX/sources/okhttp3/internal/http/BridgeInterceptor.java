package okhttp3.internal.http;

import java.io.IOException;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

public final class BridgeInterceptor implements Interceptor {
    private final CookieJar cookieJar;

    public BridgeInterceptor(CookieJar cookieJar2) {
        this.cookieJar = cookieJar2;
    }

    public Response intercept(Chain chain) throws IOException {
        Request userRequest = chain.request();
        Builder requestBuilder = userRequest.newBuilder();
        RequestBody body = userRequest.body();
        String str = "Content-Type";
        String str2 = "Content-Length";
        if (body != null) {
            MediaType contentType = body.contentType();
            if (contentType != null) {
                requestBuilder.header(str, contentType.toString());
            }
            long contentLength = body.contentLength();
            String str3 = "Transfer-Encoding";
            if (contentLength != -1) {
                requestBuilder.header(str2, Long.toString(contentLength));
                requestBuilder.removeHeader(str3);
            } else {
                requestBuilder.header(str3, "chunked");
                requestBuilder.removeHeader(str2);
            }
        }
        String str4 = "Host";
        if (userRequest.header(str4) == null) {
            requestBuilder.header(str4, Util.hostHeader(userRequest.url(), false));
        }
        String str5 = "Connection";
        if (userRequest.header(str5) == null) {
            requestBuilder.header(str5, "Keep-Alive");
        }
        boolean transparentGzip = false;
        String str6 = "Accept-Encoding";
        String str7 = "gzip";
        if (userRequest.header(str6) == null && userRequest.header("Range") == null) {
            transparentGzip = true;
            requestBuilder.header(str6, str7);
        }
        List<Cookie> cookies = this.cookieJar.loadForRequest(userRequest.url());
        if (!cookies.isEmpty()) {
            requestBuilder.header("Cookie", cookieHeader(cookies));
        }
        String str8 = "User-Agent";
        if (userRequest.header(str8) == null) {
            requestBuilder.header(str8, Version.userAgent());
        }
        Response networkResponse = chain.proceed(requestBuilder.build());
        HttpHeaders.receiveHeaders(this.cookieJar, userRequest.url(), networkResponse.headers());
        Response.Builder responseBuilder = networkResponse.newBuilder().request(userRequest);
        if (transparentGzip) {
            String str9 = "Content-Encoding";
            if (str7.equalsIgnoreCase(networkResponse.header(str9)) && HttpHeaders.hasBody(networkResponse)) {
                GzipSource responseBody = new GzipSource(networkResponse.body().source());
                responseBuilder.headers(networkResponse.headers().newBuilder().removeAll(str9).removeAll(str2).build());
                responseBuilder.body(new RealResponseBody(networkResponse.header(str), -1, Okio.buffer((Source) responseBody)));
            }
        }
        return responseBuilder.build();
    }

    private String cookieHeader(List<Cookie> cookies) {
        StringBuilder cookieHeader = new StringBuilder();
        int size = cookies.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                cookieHeader.append("; ");
            }
            Cookie cookie = (Cookie) cookies.get(i);
            cookieHeader.append(cookie.name());
            cookieHeader.append('=');
            cookieHeader.append(cookie.value());
        }
        return cookieHeader.toString();
    }
}
