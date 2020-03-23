package okhttp3.internal.http;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import javax.annotation.Nullable;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RouteException;
import okhttp3.internal.connection.Transmitter;
import okhttp3.internal.http2.ConnectionShutdownException;

public final class RetryAndFollowUpInterceptor implements Interceptor {
    private static final int MAX_FOLLOW_UPS = 20;
    private final OkHttpClient client;

    public RetryAndFollowUpInterceptor(OkHttpClient client2) {
        this.client = client2;
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RealInterceptorChain realChain = (RealInterceptorChain) chain;
        Transmitter transmitter = realChain.transmitter();
        int followUpCount = 0;
        Response priorResponse = null;
        while (true) {
            transmitter.prepareToConnect(request);
            if (!transmitter.isCanceled()) {
                boolean requestSendStarted = false;
                Route route = null;
                try {
                    Response response = realChain.proceed(request, transmitter, null);
                    if (1 == 0) {
                        transmitter.exchangeDoneDueToException();
                    }
                    if (priorResponse != null) {
                        response = response.newBuilder().priorResponse(priorResponse.newBuilder().body(null).build()).build();
                    }
                    Exchange exchange = Internal.instance.exchange(response);
                    if (exchange != null) {
                        route = exchange.connection().route();
                    }
                    Request followUp = followUpRequest(response, route);
                    if (followUp == null) {
                        if (exchange != null && exchange.isDuplex()) {
                            transmitter.timeoutEarlyExit();
                        }
                        return response;
                    }
                    RequestBody followUpBody = followUp.body();
                    if (followUpBody != null && followUpBody.isOneShot()) {
                        return response;
                    }
                    Util.closeQuietly((Closeable) response.body());
                    if (transmitter.hasExchange()) {
                        exchange.detachWithViolence();
                    }
                    followUpCount++;
                    if (followUpCount <= 20) {
                        request = followUp;
                        priorResponse = response;
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Too many follow-up requests: ");
                        sb.append(followUpCount);
                        throw new ProtocolException(sb.toString());
                    }
                } catch (RouteException e) {
                    if (!recover(e.getLastConnectException(), transmitter, false, request)) {
                        throw e.getFirstConnectException();
                    } else if (0 == 0) {
                        transmitter.exchangeDoneDueToException();
                    }
                } catch (IOException e2) {
                    if (!(e2 instanceof ConnectionShutdownException)) {
                        requestSendStarted = true;
                    }
                    if (!recover(e2, transmitter, requestSendStarted, request)) {
                        throw e2;
                    } else if (0 == 0) {
                        transmitter.exchangeDoneDueToException();
                    }
                } catch (Throwable th) {
                    if (0 == 0) {
                        transmitter.exchangeDoneDueToException();
                    }
                    throw th;
                }
            } else {
                throw new IOException("Canceled");
            }
        }
    }

    private boolean recover(IOException e, Transmitter transmitter, boolean requestSendStarted, Request userRequest) {
        if (!this.client.retryOnConnectionFailure()) {
            return false;
        }
        if ((!requestSendStarted || !requestIsOneShot(e, userRequest)) && isRecoverable(e, requestSendStarted) && transmitter.canRetry()) {
            return true;
        }
        return false;
    }

    private boolean requestIsOneShot(IOException e, Request userRequest) {
        RequestBody requestBody = userRequest.body();
        return (requestBody != null && requestBody.isOneShot()) || (e instanceof FileNotFoundException);
    }

    private boolean isRecoverable(IOException e, boolean requestSendStarted) {
        boolean z = false;
        if (e instanceof ProtocolException) {
            return false;
        }
        if (e instanceof InterruptedIOException) {
            if ((e instanceof SocketTimeoutException) && !requestSendStarted) {
                z = true;
            }
            return z;
        } else if ((!(e instanceof SSLHandshakeException) || !(e.getCause() instanceof CertificateException)) && !(e instanceof SSLPeerUnverifiedException)) {
            return true;
        } else {
            return false;
        }
    }

    private Request followUpRequest(Response userResponse, @Nullable Route route) throws IOException {
        Proxy selectedProxy;
        if (userResponse != null) {
            int responseCode = userResponse.code();
            String method = userResponse.request().method();
            String str = "GET";
            RequestBody requestBody = null;
            if (responseCode == 307 || responseCode == 308) {
                if (!method.equals(str) && !method.equals("HEAD")) {
                    return null;
                }
            } else if (responseCode == 401) {
                return this.client.authenticator().authenticate(route, userResponse);
            } else {
                if (responseCode != 503) {
                    if (responseCode == 407) {
                        if (route != null) {
                            selectedProxy = route.proxy();
                        } else {
                            selectedProxy = this.client.proxy();
                        }
                        if (selectedProxy.type() == Type.HTTP) {
                            return this.client.proxyAuthenticator().authenticate(route, userResponse);
                        }
                        throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
                    } else if (responseCode != 408) {
                        switch (responseCode) {
                            case 300:
                            case 301:
                            case 302:
                            case 303:
                                break;
                            default:
                                return null;
                        }
                    } else if (!this.client.retryOnConnectionFailure()) {
                        return null;
                    } else {
                        RequestBody requestBody2 = userResponse.request().body();
                        if (requestBody2 != null && requestBody2.isOneShot()) {
                            return null;
                        }
                        if ((userResponse.priorResponse() == null || userResponse.priorResponse().code() != 408) && retryAfter(userResponse, 0) <= 0) {
                            return userResponse.request();
                        }
                        return null;
                    }
                } else if ((userResponse.priorResponse() == null || userResponse.priorResponse().code() != 503) && retryAfter(userResponse, Integer.MAX_VALUE) == 0) {
                    return userResponse.request();
                } else {
                    return null;
                }
            }
            if (!this.client.followRedirects()) {
                return null;
            }
            String location = userResponse.header("Location");
            if (location == null) {
                return null;
            }
            HttpUrl url = userResponse.request().url().resolve(location);
            if (url == null) {
                return null;
            }
            if (!url.scheme().equals(userResponse.request().url().scheme()) && !this.client.followSslRedirects()) {
                return null;
            }
            Builder requestBuilder = userResponse.request().newBuilder();
            if (HttpMethod.permitsRequestBody(method)) {
                boolean maintainBody = HttpMethod.redirectsWithBody(method);
                if (HttpMethod.redirectsToGet(method)) {
                    requestBuilder.method(str, null);
                } else {
                    if (maintainBody) {
                        requestBody = userResponse.request().body();
                    }
                    requestBuilder.method(method, requestBody);
                }
                if (!maintainBody) {
                    requestBuilder.removeHeader("Transfer-Encoding");
                    requestBuilder.removeHeader("Content-Length");
                    requestBuilder.removeHeader("Content-Type");
                }
            }
            if (!Util.sameConnection(userResponse.request().url(), url)) {
                requestBuilder.removeHeader("Authorization");
            }
            return requestBuilder.url(url).build();
        }
        throw new IllegalStateException();
    }

    private int retryAfter(Response userResponse, int defaultDelay) {
        String header = userResponse.header("Retry-After");
        if (header == null) {
            return defaultDelay;
        }
        if (header.matches("\\d+")) {
            return Integer.valueOf(header).intValue();
        }
        return Integer.MAX_VALUE;
    }
}
