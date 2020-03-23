package okhttp3.internal.connection;

import java.io.IOException;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RouteSelector.Selection;
import okhttp3.internal.http.ExchangeCodec;

final class ExchangeFinder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Address address;
    private final Call call;
    private RealConnection connectingConnection;
    private final RealConnectionPool connectionPool;
    private final EventListener eventListener;
    private boolean hasStreamFailure;
    private Route nextRouteToTry;
    private Selection routeSelection;
    private final RouteSelector routeSelector;
    private final Transmitter transmitter;

    ExchangeFinder(Transmitter transmitter2, RealConnectionPool connectionPool2, Address address2, Call call2, EventListener eventListener2) {
        this.transmitter = transmitter2;
        this.connectionPool = connectionPool2;
        this.address = address2;
        this.call = call2;
        this.eventListener = eventListener2;
        this.routeSelector = new RouteSelector(address2, connectionPool2.routeDatabase, call2, eventListener2);
    }

    public ExchangeCodec find(OkHttpClient client, Chain chain, boolean doExtensiveHealthChecks) {
        try {
            return findHealthyConnection(chain.connectTimeoutMillis(), chain.readTimeoutMillis(), chain.writeTimeoutMillis(), client.pingIntervalMillis(), client.retryOnConnectionFailure(), doExtensiveHealthChecks).newCodec(client, chain);
        } catch (RouteException e) {
            trackFailure();
            throw e;
        } catch (IOException e2) {
            trackFailure();
            throw new RouteException(e2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0018, code lost:
        if (r0.isHealthy(r9) != false) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private okhttp3.internal.connection.RealConnection findHealthyConnection(int r4, int r5, int r6, int r7, boolean r8, boolean r9) throws java.io.IOException {
        /*
            r3 = this;
        L_0x0000:
            okhttp3.internal.connection.RealConnection r0 = r3.findConnection(r4, r5, r6, r7, r8)
            okhttp3.internal.connection.RealConnectionPool r1 = r3.connectionPool
            monitor-enter(r1)
            int r2 = r0.successCount     // Catch:{ all -> 0x001f }
            if (r2 != 0) goto L_0x0013
            boolean r2 = r0.isMultiplexed()     // Catch:{ all -> 0x001f }
            if (r2 != 0) goto L_0x0013
            monitor-exit(r1)     // Catch:{ all -> 0x001f }
            return r0
        L_0x0013:
            monitor-exit(r1)     // Catch:{ all -> 0x001f }
            boolean r1 = r0.isHealthy(r9)
            if (r1 != 0) goto L_0x001e
            r0.noNewExchanges()
            goto L_0x0000
        L_0x001e:
            return r0
        L_0x001f:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001f }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.ExchangeFinder.findHealthyConnection(int, int, int, int, boolean, boolean):okhttp3.internal.connection.RealConnection");
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x009f A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00e2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private okhttp3.internal.connection.RealConnection findConnection(int r20, int r21, int r22, int r23, boolean r24) throws java.io.IOException {
        /*
            r19 = this;
            r1 = r19
            r2 = 0
            r3 = 0
            r4 = 0
            okhttp3.internal.connection.RealConnectionPool r5 = r1.connectionPool
            monitor-enter(r5)
            okhttp3.internal.connection.Transmitter r0 = r1.transmitter     // Catch:{ all -> 0x0151 }
            boolean r0 = r0.isCanceled()     // Catch:{ all -> 0x0151 }
            if (r0 != 0) goto L_0x0149
            r0 = 0
            r1.hasStreamFailure = r0     // Catch:{ all -> 0x0151 }
            okhttp3.internal.connection.Transmitter r6 = r1.transmitter     // Catch:{ all -> 0x0151 }
            okhttp3.internal.connection.RealConnection r6 = r6.connection     // Catch:{ all -> 0x0151 }
            okhttp3.internal.connection.Transmitter r7 = r1.transmitter     // Catch:{ all -> 0x0151 }
            okhttp3.internal.connection.RealConnection r7 = r7.connection     // Catch:{ all -> 0x0151 }
            r8 = 0
            if (r7 == 0) goto L_0x002d
            okhttp3.internal.connection.Transmitter r7 = r1.transmitter     // Catch:{ all -> 0x0151 }
            okhttp3.internal.connection.RealConnection r7 = r7.connection     // Catch:{ all -> 0x0151 }
            boolean r7 = r7.noNewExchanges     // Catch:{ all -> 0x0151 }
            if (r7 == 0) goto L_0x002d
            okhttp3.internal.connection.Transmitter r7 = r1.transmitter     // Catch:{ all -> 0x0151 }
            java.net.Socket r7 = r7.releaseConnectionNoEvents()     // Catch:{ all -> 0x0151 }
            goto L_0x002e
        L_0x002d:
            r7 = r8
        L_0x002e:
            okhttp3.internal.connection.Transmitter r9 = r1.transmitter     // Catch:{ all -> 0x0151 }
            okhttp3.internal.connection.RealConnection r9 = r9.connection     // Catch:{ all -> 0x0151 }
            if (r9 == 0) goto L_0x003b
            okhttp3.internal.connection.Transmitter r9 = r1.transmitter     // Catch:{ all -> 0x0151 }
            okhttp3.internal.connection.RealConnection r9 = r9.connection     // Catch:{ all -> 0x0151 }
            r3 = r9
            r6 = 0
        L_0x003b:
            if (r3 != 0) goto L_0x0069
            okhttp3.internal.connection.RealConnectionPool r9 = r1.connectionPool     // Catch:{ all -> 0x0151 }
            okhttp3.Address r10 = r1.address     // Catch:{ all -> 0x0151 }
            okhttp3.internal.connection.Transmitter r11 = r1.transmitter     // Catch:{ all -> 0x0151 }
            boolean r9 = r9.transmitterAcquirePooledConnection(r10, r11, r8, r0)     // Catch:{ all -> 0x0151 }
            if (r9 == 0) goto L_0x0050
            r2 = 1
            okhttp3.internal.connection.Transmitter r9 = r1.transmitter     // Catch:{ all -> 0x0151 }
            okhttp3.internal.connection.RealConnection r9 = r9.connection     // Catch:{ all -> 0x0151 }
            r3 = r9
            goto L_0x0069
        L_0x0050:
            okhttp3.Route r9 = r1.nextRouteToTry     // Catch:{ all -> 0x0151 }
            if (r9 == 0) goto L_0x005a
            okhttp3.Route r9 = r1.nextRouteToTry     // Catch:{ all -> 0x0151 }
            r4 = r9
            r1.nextRouteToTry = r8     // Catch:{ all -> 0x0151 }
            goto L_0x0069
        L_0x005a:
            boolean r9 = r19.retryCurrentRoute()     // Catch:{ all -> 0x0151 }
            if (r9 == 0) goto L_0x0069
            okhttp3.internal.connection.Transmitter r9 = r1.transmitter     // Catch:{ all -> 0x0151 }
            okhttp3.internal.connection.RealConnection r9 = r9.connection     // Catch:{ all -> 0x0151 }
            okhttp3.Route r9 = r9.route()     // Catch:{ all -> 0x0151 }
            r4 = r9
        L_0x0069:
            monitor-exit(r5)     // Catch:{ all -> 0x0151 }
            okhttp3.internal.Util.closeQuietly(r7)
            if (r6 == 0) goto L_0x0076
            okhttp3.EventListener r5 = r1.eventListener
            okhttp3.Call r9 = r1.call
            r5.connectionReleased(r9, r6)
        L_0x0076:
            if (r2 == 0) goto L_0x007f
            okhttp3.EventListener r5 = r1.eventListener
            okhttp3.Call r9 = r1.call
            r5.connectionAcquired(r9, r3)
        L_0x007f:
            if (r3 == 0) goto L_0x0082
            return r3
        L_0x0082:
            r5 = 0
            if (r4 != 0) goto L_0x009a
            okhttp3.internal.connection.RouteSelector$Selection r9 = r1.routeSelection
            if (r9 == 0) goto L_0x008f
            boolean r9 = r9.hasNext()
            if (r9 != 0) goto L_0x009a
        L_0x008f:
            r5 = 1
            okhttp3.internal.connection.RouteSelector r9 = r1.routeSelector
            okhttp3.internal.connection.RouteSelector$Selection r9 = r9.next()
            r1.routeSelection = r9
            r9 = r5
            goto L_0x009b
        L_0x009a:
            r9 = r5
        L_0x009b:
            r5 = 0
            okhttp3.internal.connection.RealConnectionPool r10 = r1.connectionPool
            monitor-enter(r10)
            okhttp3.internal.connection.Transmitter r11 = r1.transmitter     // Catch:{ all -> 0x0146 }
            boolean r11 = r11.isCanceled()     // Catch:{ all -> 0x0146 }
            if (r11 != 0) goto L_0x013e
            if (r9 == 0) goto L_0x00c2
            okhttp3.internal.connection.RouteSelector$Selection r11 = r1.routeSelection     // Catch:{ all -> 0x0146 }
            java.util.List r11 = r11.getAll()     // Catch:{ all -> 0x0146 }
            r5 = r11
            okhttp3.internal.connection.RealConnectionPool r11 = r1.connectionPool     // Catch:{ all -> 0x0146 }
            okhttp3.Address r12 = r1.address     // Catch:{ all -> 0x0146 }
            okhttp3.internal.connection.Transmitter r13 = r1.transmitter     // Catch:{ all -> 0x0146 }
            boolean r0 = r11.transmitterAcquirePooledConnection(r12, r13, r5, r0)     // Catch:{ all -> 0x0146 }
            if (r0 == 0) goto L_0x00c2
            r2 = 1
            okhttp3.internal.connection.Transmitter r0 = r1.transmitter     // Catch:{ all -> 0x0146 }
            okhttp3.internal.connection.RealConnection r0 = r0.connection     // Catch:{ all -> 0x0146 }
            r3 = r0
        L_0x00c2:
            if (r2 != 0) goto L_0x00d7
            if (r4 != 0) goto L_0x00cd
            okhttp3.internal.connection.RouteSelector$Selection r0 = r1.routeSelection     // Catch:{ all -> 0x0146 }
            okhttp3.Route r0 = r0.next()     // Catch:{ all -> 0x0146 }
            r4 = r0
        L_0x00cd:
            okhttp3.internal.connection.RealConnection r0 = new okhttp3.internal.connection.RealConnection     // Catch:{ all -> 0x0146 }
            okhttp3.internal.connection.RealConnectionPool r11 = r1.connectionPool     // Catch:{ all -> 0x0146 }
            r0.<init>(r11, r4)     // Catch:{ all -> 0x0146 }
            r3 = r0
            r1.connectingConnection = r3     // Catch:{ all -> 0x0146 }
        L_0x00d7:
            monitor-exit(r10)     // Catch:{ all -> 0x0146 }
            if (r2 == 0) goto L_0x00e2
            okhttp3.EventListener r0 = r1.eventListener
            okhttp3.Call r8 = r1.call
            r0.connectionAcquired(r8, r3)
            return r3
        L_0x00e2:
            okhttp3.Call r0 = r1.call
            okhttp3.EventListener r10 = r1.eventListener
            r11 = r3
            r12 = r20
            r13 = r21
            r14 = r22
            r15 = r23
            r16 = r24
            r17 = r0
            r18 = r10
            r11.connect(r12, r13, r14, r15, r16, r17, r18)
            okhttp3.internal.connection.RealConnectionPool r0 = r1.connectionPool
            okhttp3.internal.connection.RouteDatabase r0 = r0.routeDatabase
            okhttp3.Route r10 = r3.route()
            r0.connected(r10)
            r10 = 0
            okhttp3.internal.connection.RealConnectionPool r11 = r1.connectionPool
            monitor-enter(r11)
            r1.connectingConnection = r8     // Catch:{ all -> 0x013b }
            okhttp3.internal.connection.RealConnectionPool r0 = r1.connectionPool     // Catch:{ all -> 0x013b }
            okhttp3.Address r8 = r1.address     // Catch:{ all -> 0x013b }
            okhttp3.internal.connection.Transmitter r12 = r1.transmitter     // Catch:{ all -> 0x013b }
            r13 = 1
            boolean r0 = r0.transmitterAcquirePooledConnection(r8, r12, r5, r13)     // Catch:{ all -> 0x013b }
            if (r0 == 0) goto L_0x0125
            r3.noNewExchanges = r13     // Catch:{ all -> 0x013b }
            java.net.Socket r0 = r3.socket()     // Catch:{ all -> 0x013b }
            r10 = r0
            okhttp3.internal.connection.Transmitter r0 = r1.transmitter     // Catch:{ all -> 0x013b }
            okhttp3.internal.connection.RealConnection r0 = r0.connection     // Catch:{ all -> 0x013b }
            r3 = r0
            r1.nextRouteToTry = r4     // Catch:{ all -> 0x013b }
            goto L_0x012f
        L_0x0125:
            okhttp3.internal.connection.RealConnectionPool r0 = r1.connectionPool     // Catch:{ all -> 0x013b }
            r0.put(r3)     // Catch:{ all -> 0x013b }
            okhttp3.internal.connection.Transmitter r0 = r1.transmitter     // Catch:{ all -> 0x013b }
            r0.acquireConnectionNoEvents(r3)     // Catch:{ all -> 0x013b }
        L_0x012f:
            monitor-exit(r11)     // Catch:{ all -> 0x013b }
            okhttp3.internal.Util.closeQuietly(r10)
            okhttp3.EventListener r0 = r1.eventListener
            okhttp3.Call r8 = r1.call
            r0.connectionAcquired(r8, r3)
            return r3
        L_0x013b:
            r0 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x013b }
            throw r0
        L_0x013e:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x0146 }
            java.lang.String r8 = "Canceled"
            r0.<init>(r8)     // Catch:{ all -> 0x0146 }
            throw r0     // Catch:{ all -> 0x0146 }
        L_0x0146:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x0146 }
            throw r0
        L_0x0149:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x0151 }
            java.lang.String r6 = "Canceled"
            r0.<init>(r6)     // Catch:{ all -> 0x0151 }
            throw r0     // Catch:{ all -> 0x0151 }
        L_0x0151:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0151 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.ExchangeFinder.findConnection(int, int, int, int, boolean):okhttp3.internal.connection.RealConnection");
    }

    /* access modifiers changed from: 0000 */
    public RealConnection connectingConnection() {
        return this.connectingConnection;
    }

    /* access modifiers changed from: 0000 */
    public void trackFailure() {
        synchronized (this.connectionPool) {
            this.hasStreamFailure = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean hasStreamFailure() {
        boolean z;
        synchronized (this.connectionPool) {
            z = this.hasStreamFailure;
        }
        return z;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasRouteToTry() {
        synchronized (this.connectionPool) {
            boolean z = true;
            if (this.nextRouteToTry != null) {
                return true;
            }
            if (retryCurrentRoute()) {
                this.nextRouteToTry = this.transmitter.connection.route();
                return true;
            }
            if ((this.routeSelection == null || !this.routeSelection.hasNext()) && !this.routeSelector.hasNext()) {
                z = false;
            }
            return z;
        }
    }

    private boolean retryCurrentRoute() {
        return this.transmitter.connection != null && this.transmitter.connection.routeFailureCount == 0 && Util.sameConnection(this.transmitter.connection.route().address().url(), this.address.url());
    }
}
