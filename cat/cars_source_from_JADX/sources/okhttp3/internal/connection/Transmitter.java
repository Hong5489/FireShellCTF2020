package okhttp3.internal.connection;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.AsyncTimeout;
import okio.Timeout;

public final class Transmitter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Call call;
    @Nullable
    private Object callStackTrace;
    private boolean canceled;
    private final OkHttpClient client;
    public RealConnection connection;
    private final RealConnectionPool connectionPool;
    private final EventListener eventListener;
    @Nullable
    private Exchange exchange;
    private ExchangeFinder exchangeFinder;
    private boolean exchangeRequestDone;
    private boolean exchangeResponseDone;
    private boolean noMoreExchanges;
    private Request request;
    private final AsyncTimeout timeout = new AsyncTimeout() {
        /* access modifiers changed from: protected */
        public void timedOut() {
            Transmitter.this.cancel();
        }
    };
    private boolean timeoutEarlyExit;

    static final class TransmitterReference extends WeakReference<Transmitter> {
        final Object callStackTrace;

        TransmitterReference(Transmitter referent, Object callStackTrace2) {
            super(referent);
            this.callStackTrace = callStackTrace2;
        }
    }

    public Transmitter(OkHttpClient client2, Call call2) {
        this.client = client2;
        this.connectionPool = Internal.instance.realConnectionPool(client2.connectionPool());
        this.call = call2;
        this.eventListener = client2.eventListenerFactory().create(call2);
        this.timeout.timeout((long) client2.callTimeoutMillis(), TimeUnit.MILLISECONDS);
    }

    public Timeout timeout() {
        return this.timeout;
    }

    public void timeoutEnter() {
        this.timeout.enter();
    }

    public void timeoutEarlyExit() {
        if (!this.timeoutEarlyExit) {
            this.timeoutEarlyExit = true;
            this.timeout.exit();
            return;
        }
        throw new IllegalStateException();
    }

    @Nullable
    private IOException timeoutExit(@Nullable IOException cause) {
        if (this.timeoutEarlyExit || !this.timeout.exit()) {
            return cause;
        }
        InterruptedIOException e = new InterruptedIOException("timeout");
        if (cause != null) {
            e.initCause(cause);
        }
        return e;
    }

    public void callStart() {
        this.callStackTrace = Platform.get().getStackTraceForCloseable("response.body().close()");
        this.eventListener.callStart(this.call);
    }

    public void prepareToConnect(Request request2) {
        Request request3 = this.request;
        if (request3 != null) {
            if (Util.sameConnection(request3.url(), request2.url()) && this.exchangeFinder.hasRouteToTry()) {
                return;
            }
            if (this.exchange != null) {
                throw new IllegalStateException();
            } else if (this.exchangeFinder != null) {
                maybeReleaseConnection(null, true);
                this.exchangeFinder = null;
            }
        }
        this.request = request2;
        ExchangeFinder exchangeFinder2 = new ExchangeFinder(this, this.connectionPool, createAddress(request2.url()), this.call, this.eventListener);
        this.exchangeFinder = exchangeFinder2;
    }

    private Address createAddress(HttpUrl url) {
        SSLSocketFactory sslSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        CertificatePinner certificatePinner = null;
        if (url.isHttps()) {
            sslSocketFactory = this.client.sslSocketFactory();
            hostnameVerifier = this.client.hostnameVerifier();
            certificatePinner = this.client.certificatePinner();
        }
        Address address = new Address(url.host(), url.port(), this.client.dns(), this.client.socketFactory(), sslSocketFactory, hostnameVerifier, certificatePinner, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
        return address;
    }

    /* access modifiers changed from: 0000 */
    public Exchange newExchange(Chain chain, boolean doExtensiveHealthChecks) {
        synchronized (this.connectionPool) {
            if (this.noMoreExchanges) {
                throw new IllegalStateException("released");
            } else if (this.exchange != null) {
                throw new IllegalStateException("cannot make a new request because the previous response is still open: please call response.close()");
            }
        }
        Exchange result = new Exchange(this, this.call, this.eventListener, this.exchangeFinder, this.exchangeFinder.find(this.client, chain, doExtensiveHealthChecks));
        synchronized (this.connectionPool) {
            this.exchange = result;
            this.exchangeRequestDone = false;
            this.exchangeResponseDone = false;
        }
        return result;
    }

    /* access modifiers changed from: 0000 */
    public void acquireConnectionNoEvents(RealConnection connection2) {
        if (this.connection == null) {
            this.connection = connection2;
            connection2.transmitters.add(new TransmitterReference(this, this.callStackTrace));
            return;
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public Socket releaseConnectionNoEvents() {
        int index = -1;
        int i = 0;
        int size = this.connection.transmitters.size();
        while (true) {
            if (i >= size) {
                break;
            } else if (((Reference) this.connection.transmitters.get(i)).get() == this) {
                index = i;
                break;
            } else {
                i++;
            }
        }
        if (index != -1) {
            RealConnection released = this.connection;
            released.transmitters.remove(index);
            this.connection = null;
            if (released.transmitters.isEmpty()) {
                released.idleAtNanos = System.nanoTime();
                if (this.connectionPool.connectionBecameIdle(released)) {
                    return released.socket();
                }
            }
            return null;
        }
        throw new IllegalStateException();
    }

    public void exchangeDoneDueToException() {
        synchronized (this.connectionPool) {
            if (!this.noMoreExchanges) {
                this.exchange = null;
            } else {
                throw new IllegalStateException();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0038, code lost:
        if (r0 == false) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003a, code lost:
        r10 = maybeReleaseConnection(r10, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x003f, code lost:
        return r10;
     */
    @javax.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.io.IOException exchangeMessageDone(okhttp3.internal.connection.Exchange r7, boolean r8, boolean r9, @javax.annotation.Nullable java.io.IOException r10) {
        /*
            r6 = this;
            r0 = 0
            okhttp3.internal.connection.RealConnectionPool r1 = r6.connectionPool
            monitor-enter(r1)
            okhttp3.internal.connection.Exchange r2 = r6.exchange     // Catch:{ all -> 0x0040 }
            if (r7 == r2) goto L_0x000a
            monitor-exit(r1)     // Catch:{ all -> 0x0040 }
            return r10
        L_0x000a:
            r2 = 0
            r3 = 1
            if (r8 == 0) goto L_0x0015
            boolean r4 = r6.exchangeRequestDone     // Catch:{ all -> 0x0040 }
            if (r4 != 0) goto L_0x0013
            r2 = 1
        L_0x0013:
            r6.exchangeRequestDone = r3     // Catch:{ all -> 0x0040 }
        L_0x0015:
            if (r9 == 0) goto L_0x001e
            boolean r4 = r6.exchangeResponseDone     // Catch:{ all -> 0x0040 }
            if (r4 != 0) goto L_0x001c
            r2 = 1
        L_0x001c:
            r6.exchangeResponseDone = r3     // Catch:{ all -> 0x0040 }
        L_0x001e:
            boolean r4 = r6.exchangeRequestDone     // Catch:{ all -> 0x0040 }
            if (r4 == 0) goto L_0x0037
            boolean r4 = r6.exchangeResponseDone     // Catch:{ all -> 0x0040 }
            if (r4 == 0) goto L_0x0037
            if (r2 == 0) goto L_0x0037
            r0 = 1
            okhttp3.internal.connection.Exchange r4 = r6.exchange     // Catch:{ all -> 0x0040 }
            okhttp3.internal.connection.RealConnection r4 = r4.connection()     // Catch:{ all -> 0x0040 }
            int r5 = r4.successCount     // Catch:{ all -> 0x0040 }
            int r5 = r5 + r3
            r4.successCount = r5     // Catch:{ all -> 0x0040 }
            r3 = 0
            r6.exchange = r3     // Catch:{ all -> 0x0040 }
        L_0x0037:
            monitor-exit(r1)     // Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x003f
            r1 = 0
            java.io.IOException r10 = r6.maybeReleaseConnection(r10, r1)
        L_0x003f:
            return r10
        L_0x0040:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0040 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.Transmitter.exchangeMessageDone(okhttp3.internal.connection.Exchange, boolean, boolean, java.io.IOException):java.io.IOException");
    }

    @Nullable
    public IOException noMoreExchanges(@Nullable IOException e) {
        synchronized (this.connectionPool) {
            this.noMoreExchanges = true;
        }
        return maybeReleaseConnection(e, false);
    }

    @Nullable
    private IOException maybeReleaseConnection(@Nullable IOException e, boolean force) {
        Connection releasedConnection;
        Socket socket;
        boolean z;
        boolean callEnd;
        synchronized (this.connectionPool) {
            if (force) {
                if (this.exchange != null) {
                    throw new IllegalStateException("cannot release connection while it is in use");
                }
            }
            releasedConnection = this.connection;
            if (this.connection == null || this.exchange != null || (!force && !this.noMoreExchanges)) {
                socket = null;
            } else {
                socket = releaseConnectionNoEvents();
            }
            if (this.connection != null) {
                releasedConnection = null;
            }
            z = true;
            callEnd = this.noMoreExchanges && this.exchange == null;
        }
        Util.closeQuietly(socket);
        if (releasedConnection != null) {
            this.eventListener.connectionReleased(this.call, releasedConnection);
        }
        if (callEnd) {
            if (e == null) {
                z = false;
            }
            boolean callFailed = z;
            e = timeoutExit(e);
            if (callFailed) {
                this.eventListener.callFailed(this.call, e);
            } else {
                this.eventListener.callEnd(this.call);
            }
        }
        return e;
    }

    public boolean canRetry() {
        return this.exchangeFinder.hasStreamFailure() && this.exchangeFinder.hasRouteToTry();
    }

    public boolean hasExchange() {
        boolean z;
        synchronized (this.connectionPool) {
            z = this.exchange != null;
        }
        return z;
    }

    public void cancel() {
        Exchange exchangeToCancel;
        RealConnection connectionToCancel;
        synchronized (this.connectionPool) {
            this.canceled = true;
            exchangeToCancel = this.exchange;
            if (this.exchangeFinder == null || this.exchangeFinder.connectingConnection() == null) {
                connectionToCancel = this.connection;
            } else {
                connectionToCancel = this.exchangeFinder.connectingConnection();
            }
        }
        if (exchangeToCancel != null) {
            exchangeToCancel.cancel();
        } else if (connectionToCancel != null) {
            connectionToCancel.cancel();
        }
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.connectionPool) {
            z = this.canceled;
        }
        return z;
    }
}
