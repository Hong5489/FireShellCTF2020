package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public final class Http2Connection implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int AWAIT_PING = 3;
    static final int DEGRADED_PING = 2;
    static final long DEGRADED_PONG_TIMEOUT_NS = 1000000000;
    static final int INTERVAL_PING = 1;
    static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    /* access modifiers changed from: private */
    public static final ExecutorService listenerExecutor;
    private long awaitPingsSent = 0;
    /* access modifiers changed from: private */
    public long awaitPongsReceived = 0;
    long bytesLeftInWriteWindow;
    final boolean client;
    final String connectionName;
    final Set<Integer> currentPushRequests = new LinkedHashSet();
    private long degradedPingsSent = 0;
    private long degradedPongDeadlineNs = 0;
    /* access modifiers changed from: private */
    public long degradedPongsReceived = 0;
    /* access modifiers changed from: private */
    public long intervalPingsSent = 0;
    /* access modifiers changed from: private */
    public long intervalPongsReceived = 0;
    int lastGoodStreamId;
    final Listener listener;
    int nextStreamId;
    Settings okHttpSettings = new Settings();
    final Settings peerSettings = new Settings();
    private final ExecutorService pushExecutor;
    final PushObserver pushObserver;
    final ReaderRunnable readerRunnable;
    /* access modifiers changed from: private */
    public boolean shutdown;
    final Socket socket;
    final Map<Integer, Http2Stream> streams = new LinkedHashMap();
    long unacknowledgedBytesRead = 0;
    final Http2Writer writer;
    /* access modifiers changed from: private */
    public final ScheduledExecutorService writerExecutor;

    public static class Builder {
        boolean client;
        String connectionName;
        Listener listener = Listener.REFUSE_INCOMING_STREAMS;
        int pingIntervalMillis;
        PushObserver pushObserver = PushObserver.CANCEL;
        BufferedSink sink;
        Socket socket;
        BufferedSource source;

        public Builder(boolean client2) {
            this.client = client2;
        }

        public Builder socket(Socket socket2) throws IOException {
            String connectionName2;
            SocketAddress remoteSocketAddress = socket2.getRemoteSocketAddress();
            if (remoteSocketAddress instanceof InetSocketAddress) {
                connectionName2 = ((InetSocketAddress) remoteSocketAddress).getHostName();
            } else {
                connectionName2 = remoteSocketAddress.toString();
            }
            return socket(socket2, connectionName2, Okio.buffer(Okio.source(socket2)), Okio.buffer(Okio.sink(socket2)));
        }

        public Builder socket(Socket socket2, String connectionName2, BufferedSource source2, BufferedSink sink2) {
            this.socket = socket2;
            this.connectionName = connectionName2;
            this.source = source2;
            this.sink = sink2;
            return this;
        }

        public Builder listener(Listener listener2) {
            this.listener = listener2;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver2) {
            this.pushObserver = pushObserver2;
            return this;
        }

        public Builder pingIntervalMillis(int pingIntervalMillis2) {
            this.pingIntervalMillis = pingIntervalMillis2;
            return this;
        }

        public Http2Connection build() {
            return new Http2Connection(this);
        }
    }

    final class IntervalPingRunnable extends NamedRunnable {
        IntervalPingRunnable() {
            super("OkHttp %s ping", Http2Connection.this.connectionName);
        }

        public void execute() {
            boolean failDueToMissingPong;
            synchronized (Http2Connection.this) {
                if (Http2Connection.this.intervalPongsReceived < Http2Connection.this.intervalPingsSent) {
                    failDueToMissingPong = true;
                } else {
                    Http2Connection.this.intervalPingsSent = 1 + Http2Connection.this.intervalPingsSent;
                    failDueToMissingPong = false;
                }
            }
            if (failDueToMissingPong) {
                Http2Connection.this.failConnection(null);
            } else {
                Http2Connection.this.writePing(false, 1, 0);
            }
        }
    }

    public static abstract class Listener {
        public static final Listener REFUSE_INCOMING_STREAMS = new Listener() {
            public void onStream(Http2Stream stream) throws IOException {
                stream.close(ErrorCode.REFUSED_STREAM, null);
            }
        };

        public abstract void onStream(Http2Stream http2Stream) throws IOException;

        public void onSettings(Http2Connection connection) {
        }
    }

    final class PingRunnable extends NamedRunnable {
        final int payload1;
        final int payload2;
        final boolean reply;

        PingRunnable(boolean reply2, int payload12, int payload22) {
            super("OkHttp %s ping %08x%08x", Http2Connection.this.connectionName, Integer.valueOf(payload12), Integer.valueOf(payload22));
            this.reply = reply2;
            this.payload1 = payload12;
            this.payload2 = payload22;
        }

        public void execute() {
            Http2Connection.this.writePing(this.reply, this.payload1, this.payload2);
        }
    }

    class ReaderRunnable extends NamedRunnable implements Handler {
        final Http2Reader reader;

        ReaderRunnable(Http2Reader reader2) {
            super("OkHttp %s", Http2Connection.this.connectionName);
            this.reader = reader2;
        }

        /* access modifiers changed from: protected */
        public void execute() {
            ErrorCode streamErrorCode;
            ErrorCode connectionErrorCode = ErrorCode.INTERNAL_ERROR;
            ErrorCode streamErrorCode2 = ErrorCode.INTERNAL_ERROR;
            IOException errorException = null;
            try {
                this.reader.readConnectionPreface(this);
                while (this.reader.nextFrame(false, this)) {
                }
                connectionErrorCode = ErrorCode.NO_ERROR;
                streamErrorCode = ErrorCode.CANCEL;
            } catch (IOException e) {
                errorException = e;
                connectionErrorCode = ErrorCode.PROTOCOL_ERROR;
                streamErrorCode = ErrorCode.PROTOCOL_ERROR;
            } catch (Throwable th) {
                Http2Connection.this.close(connectionErrorCode, streamErrorCode2, errorException);
                Util.closeQuietly((Closeable) this.reader);
                throw th;
            }
            Http2Connection.this.close(connectionErrorCode, streamErrorCode, errorException);
            Util.closeQuietly((Closeable) this.reader);
        }

        public void data(boolean inFinished, int streamId, BufferedSource source, int length) throws IOException {
            if (Http2Connection.this.pushedStream(streamId)) {
                Http2Connection.this.pushDataLater(streamId, source, length, inFinished);
                return;
            }
            Http2Stream dataStream = Http2Connection.this.getStream(streamId);
            if (dataStream == null) {
                Http2Connection.this.writeSynResetLater(streamId, ErrorCode.PROTOCOL_ERROR);
                Http2Connection.this.updateConnectionFlowControl((long) length);
                source.skip((long) length);
                return;
            }
            dataStream.receiveData(source, length);
            if (inFinished) {
                dataStream.receiveHeaders(Util.EMPTY_HEADERS, true);
            }
        }

        public void headers(boolean inFinished, int streamId, int associatedStreamId, List<Header> headerBlock) {
            if (Http2Connection.this.pushedStream(streamId)) {
                Http2Connection.this.pushHeadersLater(streamId, headerBlock, inFinished);
                return;
            }
            synchronized (Http2Connection.this) {
                Http2Stream stream = Http2Connection.this.getStream(streamId);
                if (stream != null) {
                    stream.receiveHeaders(Util.toHeaders(headerBlock), inFinished);
                } else if (!Http2Connection.this.shutdown) {
                    if (streamId > Http2Connection.this.lastGoodStreamId) {
                        if (streamId % 2 != Http2Connection.this.nextStreamId % 2) {
                            int i = streamId;
                            final Http2Stream http2Stream = new Http2Stream(i, Http2Connection.this, false, inFinished, Util.toHeaders(headerBlock));
                            Http2Connection.this.lastGoodStreamId = streamId;
                            Http2Connection.this.streams.put(Integer.valueOf(streamId), http2Stream);
                            Http2Connection.listenerExecutor.execute(new NamedRunnable("OkHttp %s stream %d", new Object[]{Http2Connection.this.connectionName, Integer.valueOf(streamId)}) {
                                public void execute() {
                                    try {
                                        Http2Connection.this.listener.onStream(http2Stream);
                                    } catch (IOException e) {
                                        Platform platform = Platform.get();
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("Http2Connection.Listener failure for ");
                                        sb.append(Http2Connection.this.connectionName);
                                        platform.log(4, sb.toString(), e);
                                        try {
                                            http2Stream.close(ErrorCode.PROTOCOL_ERROR, e);
                                        } catch (IOException e2) {
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }

        public void rstStream(int streamId, ErrorCode errorCode) {
            if (Http2Connection.this.pushedStream(streamId)) {
                Http2Connection.this.pushResetLater(streamId, errorCode);
                return;
            }
            Http2Stream rstStream = Http2Connection.this.removeStream(streamId);
            if (rstStream != null) {
                rstStream.receiveRstStream(errorCode);
            }
        }

        public void settings(boolean clearPrevious, Settings settings) {
            try {
                ScheduledExecutorService access$500 = Http2Connection.this.writerExecutor;
                final boolean z = clearPrevious;
                final Settings settings2 = settings;
                C03602 r1 = new NamedRunnable("OkHttp %s ACK Settings", new Object[]{Http2Connection.this.connectionName}) {
                    public void execute() {
                        ReaderRunnable.this.applyAndAckSettings(z, settings2);
                    }
                };
                access$500.execute(r1);
            } catch (RejectedExecutionException e) {
            }
        }

        /* access modifiers changed from: 0000 */
        public void applyAndAckSettings(boolean clearPrevious, Settings settings) {
            Http2Stream[] http2StreamArr;
            long delta = 0;
            Http2Stream[] streamsToNotify = null;
            synchronized (Http2Connection.this.writer) {
                synchronized (Http2Connection.this) {
                    int priorWriteWindowSize = Http2Connection.this.peerSettings.getInitialWindowSize();
                    if (clearPrevious) {
                        Http2Connection.this.peerSettings.clear();
                    }
                    Http2Connection.this.peerSettings.merge(settings);
                    int peerInitialWindowSize = Http2Connection.this.peerSettings.getInitialWindowSize();
                    if (!(peerInitialWindowSize == -1 || peerInitialWindowSize == priorWriteWindowSize)) {
                        delta = (long) (peerInitialWindowSize - priorWriteWindowSize);
                        if (!Http2Connection.this.streams.isEmpty()) {
                            http2StreamArr = (Http2Stream[]) Http2Connection.this.streams.values().toArray(new Http2Stream[Http2Connection.this.streams.size()]);
                        } else {
                            http2StreamArr = null;
                        }
                        streamsToNotify = http2StreamArr;
                    }
                }
                try {
                    Http2Connection.this.writer.applyAndAckSettings(Http2Connection.this.peerSettings);
                } catch (IOException e) {
                    Http2Connection.this.failConnection(e);
                }
            }
            if (streamsToNotify != null) {
                for (Http2Stream stream : streamsToNotify) {
                    synchronized (stream) {
                        stream.addBytesToWriteWindow(delta);
                    }
                }
            }
            Http2Connection.listenerExecutor.execute(new NamedRunnable("OkHttp %s settings", Http2Connection.this.connectionName) {
                public void execute() {
                    Http2Connection.this.listener.onSettings(Http2Connection.this);
                }
            });
        }

        public void ackSettings() {
        }

        public void ping(boolean reply, int payload1, int payload2) {
            if (reply) {
                synchronized (Http2Connection.this) {
                    if (payload1 == 1) {
                        try {
                            Http2Connection.this.intervalPongsReceived = 1 + Http2Connection.this.intervalPongsReceived;
                        } catch (Throwable th) {
                            throw th;
                        }
                    } else if (payload1 == 2) {
                        Http2Connection.this.degradedPongsReceived = 1 + Http2Connection.this.degradedPongsReceived;
                    } else if (payload1 == 3) {
                        Http2Connection.this.awaitPongsReceived = 1 + Http2Connection.this.awaitPongsReceived;
                        Http2Connection.this.notifyAll();
                    }
                }
                return;
            }
            try {
                Http2Connection.this.writerExecutor.execute(new PingRunnable(true, payload1, payload2));
            } catch (RejectedExecutionException e) {
            }
        }

        public void goAway(int lastGoodStreamId, ErrorCode errorCode, ByteString debugData) {
            Http2Stream[] streamsCopy;
            debugData.size();
            synchronized (Http2Connection.this) {
                streamsCopy = (Http2Stream[]) Http2Connection.this.streams.values().toArray(new Http2Stream[Http2Connection.this.streams.size()]);
                Http2Connection.this.shutdown = true;
            }
            for (Http2Stream http2Stream : streamsCopy) {
                if (http2Stream.getId() > lastGoodStreamId && http2Stream.isLocallyInitiated()) {
                    http2Stream.receiveRstStream(ErrorCode.REFUSED_STREAM);
                    Http2Connection.this.removeStream(http2Stream.getId());
                }
            }
        }

        public void windowUpdate(int streamId, long windowSizeIncrement) {
            if (streamId == 0) {
                synchronized (Http2Connection.this) {
                    Http2Connection.this.bytesLeftInWriteWindow += windowSizeIncrement;
                    Http2Connection.this.notifyAll();
                }
                return;
            }
            Http2Stream stream = Http2Connection.this.getStream(streamId);
            if (stream != null) {
                synchronized (stream) {
                    stream.addBytesToWriteWindow(windowSizeIncrement);
                }
            }
        }

        public void priority(int streamId, int streamDependency, int weight, boolean exclusive) {
        }

        public void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) {
            Http2Connection.this.pushRequestLater(promisedStreamId, requestHeaders);
        }

        public void alternateService(int streamId, String origin, ByteString protocol, String host, int port, long maxAge) {
        }
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Http2Connection", true));
        listenerExecutor = threadPoolExecutor;
    }

    Http2Connection(Builder builder) {
        Builder builder2 = builder;
        this.pushObserver = builder2.pushObserver;
        this.client = builder2.client;
        this.listener = builder2.listener;
        this.nextStreamId = builder2.client ? 1 : 2;
        if (builder2.client) {
            this.nextStreamId += 2;
        }
        if (builder2.client) {
            this.okHttpSettings.set(7, 16777216);
        }
        this.connectionName = builder2.connectionName;
        this.writerExecutor = new ScheduledThreadPoolExecutor(1, Util.threadFactory(Util.format("OkHttp %s Writer", this.connectionName), false));
        if (builder2.pingIntervalMillis != 0) {
            this.writerExecutor.scheduleAtFixedRate(new IntervalPingRunnable(), (long) builder2.pingIntervalMillis, (long) builder2.pingIntervalMillis, TimeUnit.MILLISECONDS);
        }
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(Util.format("OkHttp %s Push Observer", this.connectionName), true));
        this.pushExecutor = threadPoolExecutor;
        this.peerSettings.set(7, 65535);
        this.peerSettings.set(5, 16384);
        this.bytesLeftInWriteWindow = (long) this.peerSettings.getInitialWindowSize();
        this.socket = builder2.socket;
        this.writer = new Http2Writer(builder2.sink, this.client);
        this.readerRunnable = new ReaderRunnable(new Http2Reader(builder2.source, this.client));
    }

    public synchronized int openStreamCount() {
        return this.streams.size();
    }

    /* access modifiers changed from: 0000 */
    public synchronized Http2Stream getStream(int id) {
        return (Http2Stream) this.streams.get(Integer.valueOf(id));
    }

    /* access modifiers changed from: 0000 */
    public synchronized Http2Stream removeStream(int streamId) {
        Http2Stream stream;
        stream = (Http2Stream) this.streams.remove(Integer.valueOf(streamId));
        notifyAll();
        return stream;
    }

    public synchronized int maxConcurrentStreams() {
        return this.peerSettings.getMaxConcurrentStreams(Integer.MAX_VALUE);
    }

    /* access modifiers changed from: 0000 */
    public synchronized void updateConnectionFlowControl(long read) {
        this.unacknowledgedBytesRead += read;
        if (this.unacknowledgedBytesRead >= ((long) (this.okHttpSettings.getInitialWindowSize() / 2))) {
            writeWindowUpdateLater(0, this.unacknowledgedBytesRead);
            this.unacknowledgedBytesRead = 0;
        }
    }

    public Http2Stream pushStream(int associatedStreamId, List<Header> requestHeaders, boolean out) throws IOException {
        if (!this.client) {
            return newStream(associatedStreamId, requestHeaders, out);
        }
        throw new IllegalStateException("Client cannot push requests.");
    }

    public Http2Stream newStream(List<Header> requestHeaders, boolean out) throws IOException {
        return newStream(0, requestHeaders, out);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0046  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private okhttp3.internal.http2.Http2Stream newStream(int r12, java.util.List<okhttp3.internal.http2.Header> r13, boolean r14) throws java.io.IOException {
        /*
            r11 = this;
            r0 = r14 ^ 1
            r7 = 0
            okhttp3.internal.http2.Http2Writer r8 = r11.writer
            monitor-enter(r8)
            monitor-enter(r11)     // Catch:{ all -> 0x007b }
            int r1 = r11.nextStreamId     // Catch:{ all -> 0x0078 }
            r2 = 1073741823(0x3fffffff, float:1.9999999)
            if (r1 <= r2) goto L_0x0013
            okhttp3.internal.http2.ErrorCode r1 = okhttp3.internal.http2.ErrorCode.REFUSED_STREAM     // Catch:{ all -> 0x0078 }
            r11.shutdown(r1)     // Catch:{ all -> 0x0078 }
        L_0x0013:
            boolean r1 = r11.shutdown     // Catch:{ all -> 0x0078 }
            if (r1 != 0) goto L_0x0072
            int r1 = r11.nextStreamId     // Catch:{ all -> 0x0078 }
            r9 = r1
            int r1 = r11.nextStreamId     // Catch:{ all -> 0x0078 }
            int r1 = r1 + 2
            r11.nextStreamId = r1     // Catch:{ all -> 0x0078 }
            okhttp3.internal.http2.Http2Stream r10 = new okhttp3.internal.http2.Http2Stream     // Catch:{ all -> 0x0078 }
            r6 = 0
            r1 = r10
            r2 = r9
            r3 = r11
            r4 = r0
            r5 = r7
            r1.<init>(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0078 }
            r1 = r10
            if (r14 == 0) goto L_0x003f
            long r2 = r11.bytesLeftInWriteWindow     // Catch:{ all -> 0x0078 }
            r4 = 0
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 == 0) goto L_0x003f
            long r2 = r1.bytesLeftInWriteWindow     // Catch:{ all -> 0x0078 }
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 != 0) goto L_0x003d
            goto L_0x003f
        L_0x003d:
            r2 = 0
            goto L_0x0040
        L_0x003f:
            r2 = 1
        L_0x0040:
            boolean r3 = r1.isOpen()     // Catch:{ all -> 0x0078 }
            if (r3 == 0) goto L_0x004f
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.Http2Stream> r3 = r11.streams     // Catch:{ all -> 0x0078 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x0078 }
            r3.put(r4, r1)     // Catch:{ all -> 0x0078 }
        L_0x004f:
            monitor-exit(r11)     // Catch:{ all -> 0x0078 }
            if (r12 != 0) goto L_0x0058
            okhttp3.internal.http2.Http2Writer r3 = r11.writer     // Catch:{ all -> 0x007b }
            r3.headers(r0, r9, r13)     // Catch:{ all -> 0x007b }
            goto L_0x0061
        L_0x0058:
            boolean r3 = r11.client     // Catch:{ all -> 0x007b }
            if (r3 != 0) goto L_0x006a
            okhttp3.internal.http2.Http2Writer r3 = r11.writer     // Catch:{ all -> 0x007b }
            r3.pushPromise(r12, r9, r13)     // Catch:{ all -> 0x007b }
        L_0x0061:
            monitor-exit(r8)     // Catch:{ all -> 0x007b }
            if (r2 == 0) goto L_0x0069
            okhttp3.internal.http2.Http2Writer r3 = r11.writer
            r3.flush()
        L_0x0069:
            return r1
        L_0x006a:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x007b }
            java.lang.String r4 = "client streams shouldn't have associated stream IDs"
            r3.<init>(r4)     // Catch:{ all -> 0x007b }
            throw r3     // Catch:{ all -> 0x007b }
        L_0x0072:
            okhttp3.internal.http2.ConnectionShutdownException r1 = new okhttp3.internal.http2.ConnectionShutdownException     // Catch:{ all -> 0x0078 }
            r1.<init>()     // Catch:{ all -> 0x0078 }
            throw r1     // Catch:{ all -> 0x0078 }
        L_0x0078:
            r1 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0078 }
            throw r1     // Catch:{ all -> 0x007b }
        L_0x007b:
            r1 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x007b }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Connection.newStream(int, java.util.List, boolean):okhttp3.internal.http2.Http2Stream");
    }

    /* access modifiers changed from: 0000 */
    public void writeHeaders(int streamId, boolean outFinished, List<Header> alternating) throws IOException {
        this.writer.headers(outFinished, streamId, alternating);
    }

    public void writeData(int streamId, boolean outFinished, Buffer buffer, long byteCount) throws IOException {
        int toWrite;
        if (byteCount == 0) {
            this.writer.data(outFinished, streamId, buffer, 0);
            return;
        }
        while (byteCount > 0) {
            synchronized (this) {
                while (this.bytesLeftInWriteWindow <= 0) {
                    try {
                        if (this.streams.containsKey(Integer.valueOf(streamId))) {
                            wait();
                        } else {
                            throw new IOException("stream closed");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new InterruptedIOException();
                    }
                }
                toWrite = Math.min((int) Math.min(byteCount, this.bytesLeftInWriteWindow), this.writer.maxDataLength());
                this.bytesLeftInWriteWindow -= (long) toWrite;
            }
            byteCount -= (long) toWrite;
            this.writer.data(outFinished && byteCount == 0, streamId, buffer, toWrite);
        }
    }

    /* access modifiers changed from: 0000 */
    public void writeSynResetLater(int streamId, ErrorCode errorCode) {
        try {
            ScheduledExecutorService scheduledExecutorService = this.writerExecutor;
            final int i = streamId;
            final ErrorCode errorCode2 = errorCode;
            C03511 r1 = new NamedRunnable("OkHttp %s stream %d", new Object[]{this.connectionName, Integer.valueOf(streamId)}) {
                public void execute() {
                    try {
                        Http2Connection.this.writeSynReset(i, errorCode2);
                    } catch (IOException e) {
                        Http2Connection.this.failConnection(e);
                    }
                }
            };
            scheduledExecutorService.execute(r1);
        } catch (RejectedExecutionException e) {
        }
    }

    /* access modifiers changed from: 0000 */
    public void writeSynReset(int streamId, ErrorCode statusCode) throws IOException {
        this.writer.rstStream(streamId, statusCode);
    }

    /* access modifiers changed from: 0000 */
    public void writeWindowUpdateLater(int streamId, long unacknowledgedBytesRead2) {
        try {
            ScheduledExecutorService scheduledExecutorService = this.writerExecutor;
            final int i = streamId;
            final long j = unacknowledgedBytesRead2;
            C03522 r1 = new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.connectionName, Integer.valueOf(streamId)}) {
                public void execute() {
                    try {
                        Http2Connection.this.writer.windowUpdate(i, j);
                    } catch (IOException e) {
                        Http2Connection.this.failConnection(e);
                    }
                }
            };
            scheduledExecutorService.execute(r1);
        } catch (RejectedExecutionException e) {
        }
    }

    /* access modifiers changed from: 0000 */
    public void writePing(boolean reply, int payload1, int payload2) {
        try {
            this.writer.ping(reply, payload1, payload2);
        } catch (IOException e) {
            failConnection(e);
        }
    }

    /* access modifiers changed from: 0000 */
    public void writePingAndAwaitPong() throws InterruptedException {
        writePing();
        awaitPong();
    }

    /* access modifiers changed from: 0000 */
    public void writePing() {
        synchronized (this) {
            this.awaitPingsSent++;
        }
        writePing(false, 3, 1330343787);
    }

    /* access modifiers changed from: 0000 */
    public synchronized void awaitPong() throws InterruptedException {
        while (this.awaitPongsReceived < this.awaitPingsSent) {
            wait();
        }
    }

    public void flush() throws IOException {
        this.writer.flush();
    }

    public void shutdown(ErrorCode statusCode) throws IOException {
        synchronized (this.writer) {
            synchronized (this) {
                if (!this.shutdown) {
                    this.shutdown = true;
                    int lastGoodStreamId2 = this.lastGoodStreamId;
                    this.writer.goAway(lastGoodStreamId2, statusCode, Util.EMPTY_BYTE_ARRAY);
                }
            }
        }
    }

    public void close() {
        close(ErrorCode.NO_ERROR, ErrorCode.CANCEL, null);
    }

    /* access modifiers changed from: 0000 */
    public void close(ErrorCode connectionCode, ErrorCode streamCode, @Nullable IOException cause) {
        try {
            shutdown(connectionCode);
        } catch (IOException e) {
        }
        Http2Stream[] streamsToClose = null;
        synchronized (this) {
            if (!this.streams.isEmpty()) {
                streamsToClose = (Http2Stream[]) this.streams.values().toArray(new Http2Stream[this.streams.size()]);
                this.streams.clear();
            }
        }
        if (streamsToClose != null) {
            for (Http2Stream stream : streamsToClose) {
                try {
                    stream.close(streamCode, cause);
                } catch (IOException e2) {
                }
            }
        }
        try {
            this.writer.close();
        } catch (IOException e3) {
        }
        try {
            this.socket.close();
        } catch (IOException e4) {
        }
        this.writerExecutor.shutdown();
        this.pushExecutor.shutdown();
    }

    /* access modifiers changed from: private */
    public void failConnection(@Nullable IOException e) {
        close(ErrorCode.PROTOCOL_ERROR, ErrorCode.PROTOCOL_ERROR, e);
    }

    public void start() throws IOException {
        start(true);
    }

    /* access modifiers changed from: 0000 */
    public void start(boolean sendConnectionPreface) throws IOException {
        if (sendConnectionPreface) {
            this.writer.connectionPreface();
            this.writer.settings(this.okHttpSettings);
            int windowSize = this.okHttpSettings.getInitialWindowSize();
            if (windowSize != 65535) {
                this.writer.windowUpdate(0, (long) (windowSize - 65535));
            }
        }
        new Thread(this.readerRunnable).start();
    }

    public void setSettings(Settings settings) throws IOException {
        synchronized (this.writer) {
            synchronized (this) {
                if (!this.shutdown) {
                    this.okHttpSettings.merge(settings);
                } else {
                    throw new ConnectionShutdownException();
                }
            }
            this.writer.settings(settings);
        }
    }

    public synchronized boolean isHealthy(long nowNs) {
        if (this.shutdown) {
            return false;
        }
        if (this.degradedPongsReceived >= this.degradedPingsSent || nowNs < this.degradedPongDeadlineNs) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:9:?, code lost:
        r6.writerExecutor.execute(new okhttp3.internal.http2.Http2Connection.C03533(r6, "OkHttp %s ping", r6.connectionName));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendDegradedPingLater() {
        /*
            r6 = this;
            monitor-enter(r6)
            long r0 = r6.degradedPongsReceived     // Catch:{ all -> 0x0034 }
            long r2 = r6.degradedPingsSent     // Catch:{ all -> 0x0034 }
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x000b
            monitor-exit(r6)     // Catch:{ all -> 0x0034 }
            return
        L_0x000b:
            long r0 = r6.degradedPingsSent     // Catch:{ all -> 0x0034 }
            r2 = 1
            long r0 = r0 + r2
            r6.degradedPingsSent = r0     // Catch:{ all -> 0x0034 }
            long r0 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0034 }
            r2 = 1000000000(0x3b9aca00, double:4.94065646E-315)
            long r0 = r0 + r2
            r6.degradedPongDeadlineNs = r0     // Catch:{ all -> 0x0034 }
            monitor-exit(r6)     // Catch:{ all -> 0x0034 }
            java.util.concurrent.ScheduledExecutorService r0 = r6.writerExecutor     // Catch:{ RejectedExecutionException -> 0x0032 }
            okhttp3.internal.http2.Http2Connection$3 r1 = new okhttp3.internal.http2.Http2Connection$3     // Catch:{ RejectedExecutionException -> 0x0032 }
            java.lang.String r2 = "OkHttp %s ping"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ RejectedExecutionException -> 0x0032 }
            r4 = 0
            java.lang.String r5 = r6.connectionName     // Catch:{ RejectedExecutionException -> 0x0032 }
            r3[r4] = r5     // Catch:{ RejectedExecutionException -> 0x0032 }
            r1.<init>(r2, r3)     // Catch:{ RejectedExecutionException -> 0x0032 }
            r0.execute(r1)     // Catch:{ RejectedExecutionException -> 0x0032 }
            goto L_0x0033
        L_0x0032:
            r0 = move-exception
        L_0x0033:
            return
        L_0x0034:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0034 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Connection.sendDegradedPingLater():void");
    }

    /* access modifiers changed from: 0000 */
    public boolean pushedStream(int streamId) {
        return streamId != 0 && (streamId & 1) == 0;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r3 = r8;
        r6 = r9;
        r7 = r10;
        r2 = new okhttp3.internal.http2.Http2Connection.C03544(r3, "OkHttp %s Push Request[%s]", new java.lang.Object[]{r8.connectionName, java.lang.Integer.valueOf(r9)});
        pushExecutorExecute(r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void pushRequestLater(int r9, java.util.List<okhttp3.internal.http2.Header> r10) {
        /*
            r8 = this;
            monitor-enter(r8)
            java.util.Set<java.lang.Integer> r0 = r8.currentPushRequests     // Catch:{ all -> 0x003e }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x003e }
            boolean r0 = r0.contains(r1)     // Catch:{ all -> 0x003e }
            if (r0 == 0) goto L_0x0014
            okhttp3.internal.http2.ErrorCode r0 = okhttp3.internal.http2.ErrorCode.PROTOCOL_ERROR     // Catch:{ all -> 0x003e }
            r8.writeSynResetLater(r9, r0)     // Catch:{ all -> 0x003e }
            monitor-exit(r8)     // Catch:{ all -> 0x003e }
            return
        L_0x0014:
            java.util.Set<java.lang.Integer> r0 = r8.currentPushRequests     // Catch:{ all -> 0x003e }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x003e }
            r0.add(r1)     // Catch:{ all -> 0x003e }
            monitor-exit(r8)     // Catch:{ all -> 0x003e }
            okhttp3.internal.http2.Http2Connection$4 r0 = new okhttp3.internal.http2.Http2Connection$4     // Catch:{ RejectedExecutionException -> 0x003c }
            java.lang.String r4 = "OkHttp %s Push Request[%s]"
            r1 = 2
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ RejectedExecutionException -> 0x003c }
            r1 = 0
            java.lang.String r2 = r8.connectionName     // Catch:{ RejectedExecutionException -> 0x003c }
            r5[r1] = r2     // Catch:{ RejectedExecutionException -> 0x003c }
            r1 = 1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r9)     // Catch:{ RejectedExecutionException -> 0x003c }
            r5[r1] = r2     // Catch:{ RejectedExecutionException -> 0x003c }
            r2 = r0
            r3 = r8
            r6 = r9
            r7 = r10
            r2.<init>(r4, r5, r6, r7)     // Catch:{ RejectedExecutionException -> 0x003c }
            r8.pushExecutorExecute(r0)     // Catch:{ RejectedExecutionException -> 0x003c }
            goto L_0x003d
        L_0x003c:
            r0 = move-exception
        L_0x003d:
            return
        L_0x003e:
            r0 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x003e }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Connection.pushRequestLater(int, java.util.List):void");
    }

    /* access modifiers changed from: 0000 */
    public void pushHeadersLater(int streamId, List<Header> requestHeaders, boolean inFinished) {
        try {
            final int i = streamId;
            final List<Header> list = requestHeaders;
            final boolean z = inFinished;
            C03555 r0 = new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.connectionName, Integer.valueOf(streamId)}) {
                public void execute() {
                    boolean cancel = Http2Connection.this.pushObserver.onHeaders(i, list, z);
                    if (cancel) {
                        try {
                            Http2Connection.this.writer.rstStream(i, ErrorCode.CANCEL);
                        } catch (IOException e) {
                            return;
                        }
                    }
                    if (cancel || z) {
                        synchronized (Http2Connection.this) {
                            Http2Connection.this.currentPushRequests.remove(Integer.valueOf(i));
                        }
                    }
                }
            };
            pushExecutorExecute(r0);
        } catch (RejectedExecutionException e) {
        }
    }

    /* access modifiers changed from: 0000 */
    public void pushDataLater(int streamId, BufferedSource source, int byteCount, boolean inFinished) throws IOException {
        Buffer buffer = new Buffer();
        source.require((long) byteCount);
        source.read(buffer, (long) byteCount);
        if (buffer.size() == ((long) byteCount)) {
            final int i = streamId;
            final Buffer buffer2 = buffer;
            final int i2 = byteCount;
            final boolean z = inFinished;
            C03566 r1 = new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.connectionName, Integer.valueOf(streamId)}) {
                public void execute() {
                    try {
                        boolean cancel = Http2Connection.this.pushObserver.onData(i, buffer2, i2, z);
                        if (cancel) {
                            Http2Connection.this.writer.rstStream(i, ErrorCode.CANCEL);
                        }
                        if (cancel || z) {
                            synchronized (Http2Connection.this) {
                                Http2Connection.this.currentPushRequests.remove(Integer.valueOf(i));
                            }
                        }
                    } catch (IOException e) {
                    }
                }
            };
            pushExecutorExecute(r1);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(buffer.size());
        sb.append(" != ");
        sb.append(byteCount);
        throw new IOException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public void pushResetLater(int streamId, ErrorCode errorCode) {
        final int i = streamId;
        final ErrorCode errorCode2 = errorCode;
        C03577 r0 = new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.connectionName, Integer.valueOf(streamId)}) {
            public void execute() {
                Http2Connection.this.pushObserver.onReset(i, errorCode2);
                synchronized (Http2Connection.this) {
                    Http2Connection.this.currentPushRequests.remove(Integer.valueOf(i));
                }
            }
        };
        pushExecutorExecute(r0);
    }

    private synchronized void pushExecutorExecute(NamedRunnable namedRunnable) {
        if (!this.shutdown) {
            this.pushExecutor.execute(namedRunnable);
        }
    }
}
