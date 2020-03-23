package okhttp3;

import androidx.core.app.NotificationCompat;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.Transmitter;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;
import okio.Timeout;

final class RealCall implements Call {
    final OkHttpClient client;
    private boolean executed;
    final boolean forWebSocket;
    final Request originalRequest;
    /* access modifiers changed from: private */
    public Transmitter transmitter;

    final class AsyncCall extends NamedRunnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private volatile AtomicInteger callsPerHost = new AtomicInteger(0);
        private final Callback responseCallback;

        static {
            Class<RealCall> cls = RealCall.class;
        }

        AsyncCall(Callback responseCallback2) {
            super("OkHttp %s", RealCall.this.redactedUrl());
            this.responseCallback = responseCallback2;
        }

        /* access modifiers changed from: 0000 */
        public AtomicInteger callsPerHost() {
            return this.callsPerHost;
        }

        /* access modifiers changed from: 0000 */
        public void reuseCallsPerHostFrom(AsyncCall other) {
            this.callsPerHost = other.callsPerHost;
        }

        /* access modifiers changed from: 0000 */
        public String host() {
            return RealCall.this.originalRequest.url().host();
        }

        /* access modifiers changed from: 0000 */
        public Request request() {
            return RealCall.this.originalRequest;
        }

        /* access modifiers changed from: 0000 */
        public RealCall get() {
            return RealCall.this;
        }

        /* access modifiers changed from: 0000 */
        public void executeOn(ExecutorService executorService) {
            try {
                executorService.execute(this);
                if (1 != 0) {
                    return;
                }
            } catch (RejectedExecutionException e) {
                InterruptedIOException ioException = new InterruptedIOException("executor rejected");
                ioException.initCause(e);
                RealCall.this.transmitter.noMoreExchanges(ioException);
                this.responseCallback.onFailure(RealCall.this, ioException);
                if (0 != 0) {
                    return;
                }
            } catch (Throwable th) {
                if (0 == 0) {
                    RealCall.this.client.dispatcher().finished(this);
                }
                throw th;
            }
            RealCall.this.client.dispatcher().finished(this);
        }

        /* access modifiers changed from: protected */
        public void execute() {
            boolean signalledCallback = false;
            RealCall.this.transmitter.timeoutEnter();
            try {
                signalledCallback = true;
                this.responseCallback.onResponse(RealCall.this, RealCall.this.getResponseWithInterceptorChain());
            } catch (IOException e) {
                if (signalledCallback) {
                    Platform platform = Platform.get();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Callback failure for ");
                    sb.append(RealCall.this.toLoggableString());
                    platform.log(4, sb.toString(), e);
                } else {
                    this.responseCallback.onFailure(RealCall.this, e);
                }
            } catch (Throwable t) {
                RealCall.this.client.dispatcher().finished(this);
                throw t;
            }
            RealCall.this.client.dispatcher().finished(this);
        }
    }

    private RealCall(OkHttpClient client2, Request originalRequest2, boolean forWebSocket2) {
        this.client = client2;
        this.originalRequest = originalRequest2;
        this.forWebSocket = forWebSocket2;
    }

    static RealCall newRealCall(OkHttpClient client2, Request originalRequest2, boolean forWebSocket2) {
        RealCall call = new RealCall(client2, originalRequest2, forWebSocket2);
        call.transmitter = new Transmitter(client2, call);
        return call;
    }

    public Request request() {
        return this.originalRequest;
    }

    public Response execute() throws IOException {
        synchronized (this) {
            if (!this.executed) {
                this.executed = true;
            } else {
                throw new IllegalStateException("Already Executed");
            }
        }
        this.transmitter.timeoutEnter();
        this.transmitter.callStart();
        try {
            this.client.dispatcher().executed(this);
            return getResponseWithInterceptorChain();
        } finally {
            this.client.dispatcher().finished(this);
        }
    }

    public void enqueue(Callback responseCallback) {
        synchronized (this) {
            if (!this.executed) {
                this.executed = true;
            } else {
                throw new IllegalStateException("Already Executed");
            }
        }
        this.transmitter.callStart();
        this.client.dispatcher().enqueue(new AsyncCall(responseCallback));
    }

    public void cancel() {
        this.transmitter.cancel();
    }

    public Timeout timeout() {
        return this.transmitter.timeout();
    }

    public synchronized boolean isExecuted() {
        return this.executed;
    }

    public boolean isCanceled() {
        return this.transmitter.isCanceled();
    }

    public RealCall clone() {
        return newRealCall(this.client, this.originalRequest, this.forWebSocket);
    }

    /* access modifiers changed from: 0000 */
    public String toLoggableString() {
        StringBuilder sb = new StringBuilder();
        sb.append(isCanceled() ? "canceled " : "");
        sb.append(this.forWebSocket ? "web socket" : NotificationCompat.CATEGORY_CALL);
        sb.append(" to ");
        sb.append(redactedUrl());
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String redactedUrl() {
        return this.originalRequest.url().redact();
    }

    /* access modifiers changed from: 0000 */
    public Response getResponseWithInterceptorChain() throws IOException {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.client.interceptors());
        arrayList.add(new RetryAndFollowUpInterceptor(this.client));
        arrayList.add(new BridgeInterceptor(this.client.cookieJar()));
        arrayList.add(new CacheInterceptor(this.client.internalCache()));
        arrayList.add(new ConnectInterceptor(this.client));
        if (!this.forWebSocket) {
            arrayList.addAll(this.client.networkInterceptors());
        }
        arrayList.add(new CallServerInterceptor(this.forWebSocket));
        RealInterceptorChain realInterceptorChain = new RealInterceptorChain(arrayList, this.transmitter, null, 0, this.originalRequest, this, this.client.connectTimeoutMillis(), this.client.readTimeoutMillis(), this.client.writeTimeoutMillis());
        try {
            Response response = realInterceptorChain.proceed(this.originalRequest);
            if (!this.transmitter.isCanceled()) {
                if (0 == 0) {
                    this.transmitter.noMoreExchanges(null);
                }
                return response;
            }
            Util.closeQuietly((Closeable) response);
            throw new IOException("Canceled");
        } catch (IOException e) {
            throw this.transmitter.noMoreExchanges(e);
        } catch (Throwable th) {
            if (1 == 0) {
                this.transmitter.noMoreExchanges(null);
            }
            throw th;
        }
    }
}
