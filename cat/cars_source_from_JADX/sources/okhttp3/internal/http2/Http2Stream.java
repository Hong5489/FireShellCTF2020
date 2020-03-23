package okhttp3.internal.http2;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http2Stream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    long bytesLeftInWriteWindow;
    final Http2Connection connection;
    @Nullable
    ErrorCode errorCode;
    @Nullable
    IOException errorException;
    private boolean hasResponseHeaders;
    private final Deque<Headers> headersQueue = new ArrayDeque();

    /* renamed from: id */
    final int f47id;
    final StreamTimeout readTimeout = new StreamTimeout();
    final FramingSink sink;
    private final FramingSource source;
    long unacknowledgedBytesRead = 0;
    final StreamTimeout writeTimeout = new StreamTimeout();

    final class FramingSink implements Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long EMIT_BUFFER_SIZE = 16384;
        boolean closed;
        boolean finished;
        private final Buffer sendBuffer = new Buffer();
        /* access modifiers changed from: private */
        public Headers trailers;

        static {
            Class<Http2Stream> cls = Http2Stream.class;
        }

        FramingSink() {
        }

        public void write(Buffer source, long byteCount) throws IOException {
            this.sendBuffer.write(source, byteCount);
            while (this.sendBuffer.size() >= EMIT_BUFFER_SIZE) {
                emitFrame(false);
            }
        }

        /* JADX INFO: finally extract failed */
        private void emitFrame(boolean outFinishedOnLastFrame) throws IOException {
            long toWrite;
            boolean z;
            synchronized (Http2Stream.this) {
                Http2Stream.this.writeTimeout.enter();
                while (Http2Stream.this.bytesLeftInWriteWindow <= 0 && !this.finished && !this.closed && Http2Stream.this.errorCode == null) {
                    try {
                        Http2Stream.this.waitForIo();
                    } catch (Throwable th) {
                        Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                        throw th;
                    }
                }
                Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                Http2Stream.this.checkOutNotClosed();
                toWrite = Math.min(Http2Stream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
                Http2Stream.this.bytesLeftInWriteWindow -= toWrite;
            }
            Http2Stream.this.writeTimeout.enter();
            if (outFinishedOnLastFrame) {
                try {
                    if (toWrite == this.sendBuffer.size()) {
                        z = true;
                        Http2Stream.this.connection.writeData(Http2Stream.this.f47id, z, this.sendBuffer, toWrite);
                        Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                    }
                } catch (Throwable th2) {
                    Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                    throw th2;
                }
            }
            z = false;
            Http2Stream.this.connection.writeData(Http2Stream.this.f47id, z, this.sendBuffer, toWrite);
            Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
        }

        public void flush() throws IOException {
            synchronized (Http2Stream.this) {
                Http2Stream.this.checkOutNotClosed();
            }
            while (this.sendBuffer.size() > 0) {
                emitFrame(false);
                Http2Stream.this.connection.flush();
            }
        }

        public Timeout timeout() {
            return Http2Stream.this.writeTimeout;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001f, code lost:
            if (r10.sendBuffer.size() <= 0) goto L_0x0023;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
            r0 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0023, code lost:
            r0 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0026, code lost:
            if (r10.trailers == null) goto L_0x002a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0028, code lost:
            r3 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x002a, code lost:
            r3 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
            if (r3 == false) goto L_0x004d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0035, code lost:
            if (r10.sendBuffer.size() <= 0) goto L_0x003b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0037, code lost:
            emitFrame(false);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x003b, code lost:
            r10.this$0.connection.writeHeaders(r10.this$0.f47id, true, okhttp3.internal.Util.toHeaderBlock(r10.trailers));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004d, code lost:
            if (r0 == false) goto L_0x005d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0057, code lost:
            if (r10.sendBuffer.size() <= 0) goto L_0x006c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0059, code lost:
            emitFrame(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x005d, code lost:
            r10.this$0.connection.writeData(r10.this$0.f47id, true, null, 0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x006c, code lost:
            r2 = r10.this$0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x006e, code lost:
            monitor-enter(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            r10.closed = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0071, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0072, code lost:
            r10.this$0.connection.flush();
            r10.this$0.cancelStreamIfNecessary();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x007e, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
            if (r10.this$0.sink.finished != false) goto L_0x006c;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() throws java.io.IOException {
            /*
                r10 = this;
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                monitor-enter(r0)
                boolean r1 = r10.closed     // Catch:{ all -> 0x0082 }
                if (r1 == 0) goto L_0x000a
                monitor-exit(r0)     // Catch:{ all -> 0x0082 }
                return
            L_0x000a:
                monitor-exit(r0)     // Catch:{ all -> 0x0082 }
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Stream$FramingSink r0 = r0.sink
                boolean r0 = r0.finished
                r1 = 1
                if (r0 != 0) goto L_0x006c
                okio.Buffer r0 = r10.sendBuffer
                long r2 = r0.size()
                r4 = 0
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                r2 = 0
                if (r0 <= 0) goto L_0x0023
                r0 = r1
                goto L_0x0024
            L_0x0023:
                r0 = r2
            L_0x0024:
                okhttp3.Headers r3 = r10.trailers
                if (r3 == 0) goto L_0x002a
                r3 = r1
                goto L_0x002b
            L_0x002a:
                r3 = r2
            L_0x002b:
                if (r3 == 0) goto L_0x004d
            L_0x002d:
                okio.Buffer r6 = r10.sendBuffer
                long r6 = r6.size()
                int r6 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r6 <= 0) goto L_0x003b
                r10.emitFrame(r2)
                goto L_0x002d
            L_0x003b:
                okhttp3.internal.http2.Http2Stream r2 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Connection r2 = r2.connection
                okhttp3.internal.http2.Http2Stream r4 = okhttp3.internal.http2.Http2Stream.this
                int r4 = r4.f47id
                okhttp3.Headers r5 = r10.trailers
                java.util.List r5 = okhttp3.internal.Util.toHeaderBlock(r5)
                r2.writeHeaders(r4, r1, r5)
                goto L_0x006c
            L_0x004d:
                if (r0 == 0) goto L_0x005d
            L_0x004f:
                okio.Buffer r2 = r10.sendBuffer
                long r6 = r2.size()
                int r2 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r2 <= 0) goto L_0x006c
                r10.emitFrame(r1)
                goto L_0x004f
            L_0x005d:
                okhttp3.internal.http2.Http2Stream r2 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Connection r4 = r2.connection
                okhttp3.internal.http2.Http2Stream r2 = okhttp3.internal.http2.Http2Stream.this
                int r5 = r2.f47id
                r6 = 1
                r7 = 0
                r8 = 0
                r4.writeData(r5, r6, r7, r8)
            L_0x006c:
                okhttp3.internal.http2.Http2Stream r2 = okhttp3.internal.http2.Http2Stream.this
                monitor-enter(r2)
                r10.closed = r1     // Catch:{ all -> 0x007f }
                monitor-exit(r2)     // Catch:{ all -> 0x007f }
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Connection r0 = r0.connection
                r0.flush()
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                r0.cancelStreamIfNecessary()
                return
            L_0x007f:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x007f }
                throw r0
            L_0x0082:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0082 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Stream.FramingSink.close():void");
        }
    }

    private final class FramingSource implements Source {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean closed;
        boolean finished;
        private final long maxByteCount;
        /* access modifiers changed from: private */
        public final Buffer readBuffer = new Buffer();
        /* access modifiers changed from: private */
        public final Buffer receiveBuffer = new Buffer();
        /* access modifiers changed from: private */
        public Headers trailers;

        static {
            Class<Http2Stream> cls = Http2Stream.class;
        }

        FramingSource(long maxByteCount2) {
            this.maxByteCount = maxByteCount2;
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            long readBytesDelivered;
            Throwable th;
            Throwable th2;
            if (byteCount >= 0) {
                while (true) {
                    readBytesDelivered = -1;
                    th = null;
                    synchronized (Http2Stream.this) {
                        Http2Stream.this.readTimeout.enter();
                        try {
                            if (Http2Stream.this.errorCode != null) {
                                if (Http2Stream.this.errorException != null) {
                                    th2 = Http2Stream.this.errorException;
                                } else {
                                    th2 = new StreamResetException(Http2Stream.this.errorCode);
                                }
                                th = th2;
                            }
                            if (!this.closed) {
                                if (this.readBuffer.size() <= 0) {
                                    if (this.finished || th != null) {
                                        break;
                                    }
                                    Http2Stream.this.waitForIo();
                                } else {
                                    readBytesDelivered = this.readBuffer.read(sink, Math.min(byteCount, this.readBuffer.size()));
                                    Http2Stream.this.unacknowledgedBytesRead += readBytesDelivered;
                                    if (th == null && Http2Stream.this.unacknowledgedBytesRead >= ((long) (Http2Stream.this.connection.okHttpSettings.getInitialWindowSize() / 2))) {
                                        Http2Stream.this.connection.writeWindowUpdateLater(Http2Stream.this.f47id, Http2Stream.this.unacknowledgedBytesRead);
                                        Http2Stream.this.unacknowledgedBytesRead = 0;
                                    }
                                }
                            } else {
                                throw new IOException("stream closed");
                            }
                        } finally {
                            Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
                        }
                    }
                }
                Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
                if (readBytesDelivered != -1) {
                    updateConnectionFlowControl(readBytesDelivered);
                    return readBytesDelivered;
                } else if (th == null) {
                    return -1;
                } else {
                    throw th;
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("byteCount < 0: ");
                sb.append(byteCount);
                throw new IllegalArgumentException(sb.toString());
            }
        }

        private void updateConnectionFlowControl(long read) {
            Http2Stream.this.connection.updateConnectionFlowControl(read);
        }

        /* access modifiers changed from: 0000 */
        public void receive(BufferedSource in, long byteCount) throws IOException {
            boolean finished2;
            boolean z;
            boolean flowControlError;
            BufferedSource bufferedSource = in;
            long bytesDiscarded = byteCount;
            while (bytesDiscarded > 0) {
                synchronized (Http2Stream.this) {
                    finished2 = this.finished;
                    z = true;
                    flowControlError = this.readBuffer.size() + bytesDiscarded > this.maxByteCount;
                }
                if (flowControlError) {
                    bufferedSource.skip(bytesDiscarded);
                    Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                } else if (finished2) {
                    bufferedSource.skip(bytesDiscarded);
                    return;
                } else {
                    long read = bufferedSource.read(this.receiveBuffer, bytesDiscarded);
                    if (read != -1) {
                        long byteCount2 = bytesDiscarded - read;
                        long bytesDiscarded2 = 0;
                        synchronized (Http2Stream.this) {
                            if (this.closed) {
                                bytesDiscarded2 = this.receiveBuffer.size();
                                this.receiveBuffer.clear();
                            } else {
                                if (this.readBuffer.size() != 0) {
                                    z = false;
                                }
                                boolean wasEmpty = z;
                                this.readBuffer.writeAll(this.receiveBuffer);
                                if (wasEmpty) {
                                    Http2Stream.this.notifyAll();
                                }
                            }
                        }
                        if (bytesDiscarded2 > 0) {
                            updateConnectionFlowControl(bytesDiscarded2);
                        }
                        bytesDiscarded = byteCount2;
                    } else {
                        throw new EOFException();
                    }
                }
            }
        }

        public Timeout timeout() {
            return Http2Stream.this.readTimeout;
        }

        public void close() throws IOException {
            long bytesDiscarded;
            synchronized (Http2Stream.this) {
                this.closed = true;
                bytesDiscarded = this.readBuffer.size();
                this.readBuffer.clear();
                Http2Stream.this.notifyAll();
            }
            if (bytesDiscarded > 0) {
                updateConnectionFlowControl(bytesDiscarded);
            }
            Http2Stream.this.cancelStreamIfNecessary();
        }
    }

    class StreamTimeout extends AsyncTimeout {
        StreamTimeout() {
        }

        /* access modifiers changed from: protected */
        public void timedOut() {
            Http2Stream.this.closeLater(ErrorCode.CANCEL);
            Http2Stream.this.connection.sendDegradedPingLater();
        }

        /* access modifiers changed from: protected */
        public IOException newTimeoutException(IOException cause) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (cause != null) {
                socketTimeoutException.initCause(cause);
            }
            return socketTimeoutException;
        }

        public void exitAndThrowIfTimedOut() throws IOException {
            if (exit()) {
                throw newTimeoutException(null);
            }
        }
    }

    Http2Stream(int id, Http2Connection connection2, boolean outFinished, boolean inFinished, @Nullable Headers headers) {
        if (connection2 != null) {
            this.f47id = id;
            this.connection = connection2;
            this.bytesLeftInWriteWindow = (long) connection2.peerSettings.getInitialWindowSize();
            this.source = new FramingSource((long) connection2.okHttpSettings.getInitialWindowSize());
            this.sink = new FramingSink();
            this.source.finished = inFinished;
            this.sink.finished = outFinished;
            if (headers != null) {
                this.headersQueue.add(headers);
            }
            if (isLocallyInitiated() && headers != null) {
                throw new IllegalStateException("locally-initiated streams shouldn't have headers yet");
            } else if (!isLocallyInitiated() && headers == null) {
                throw new IllegalStateException("remotely-initiated streams should have headers");
            }
        } else {
            throw new NullPointerException("connection == null");
        }
    }

    public int getId() {
        return this.f47id;
    }

    public synchronized boolean isOpen() {
        if (this.errorCode != null) {
            return false;
        }
        if ((this.source.finished || this.source.closed) && ((this.sink.finished || this.sink.closed) && this.hasResponseHeaders)) {
            return false;
        }
        return true;
    }

    public boolean isLocallyInitiated() {
        if (this.connection.client == ((this.f47id & 1) == 1)) {
            return true;
        }
        return false;
    }

    public Http2Connection getConnection() {
        return this.connection;
    }

    public synchronized Headers takeHeaders() throws IOException {
        this.readTimeout.enter();
        while (this.headersQueue.isEmpty()) {
            try {
                try {
                    if (this.errorCode == null) {
                        waitForIo();
                    }
                } catch (Throwable th) {
                    th = th;
                    this.readTimeout.exitAndThrowIfTimedOut();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                this.readTimeout.exitAndThrowIfTimedOut();
                throw th;
            }
        }
        this.readTimeout.exitAndThrowIfTimedOut();
        if (this.headersQueue.isEmpty()) {
            throw (this.errorException != null ? this.errorException : new StreamResetException(this.errorCode));
        }
        return (Headers) this.headersQueue.removeFirst();
    }

    public synchronized Headers trailers() throws IOException {
        if (this.errorCode != null) {
            throw (this.errorException != null ? this.errorException : new StreamResetException(this.errorCode));
        } else if (!this.source.finished || !this.source.receiveBuffer.exhausted() || !this.source.readBuffer.exhausted()) {
            throw new IllegalStateException("too early; can't read the trailers yet");
        }
        return this.source.trailers != null ? this.source.trailers : Util.EMPTY_HEADERS;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public void writeHeaders(List<Header> responseHeaders, boolean outFinished, boolean flushHeaders) throws IOException {
        boolean z;
        if (responseHeaders != null) {
            synchronized (this) {
                z = true;
                this.hasResponseHeaders = true;
                if (outFinished) {
                    this.sink.finished = true;
                }
            }
            if (!flushHeaders) {
                synchronized (this.connection) {
                    if (this.connection.bytesLeftInWriteWindow != 0) {
                        z = false;
                    }
                    flushHeaders = z;
                }
            }
            this.connection.writeHeaders(this.f47id, outFinished, responseHeaders);
            if (flushHeaders) {
                this.connection.flush();
                return;
            }
            return;
        }
        throw new NullPointerException("headers == null");
    }

    public void enqueueTrailers(Headers trailers) {
        synchronized (this) {
            if (this.sink.finished) {
                throw new IllegalStateException("already finished");
            } else if (trailers.size() != 0) {
                this.sink.trailers = trailers;
            } else {
                throw new IllegalArgumentException("trailers.size() == 0");
            }
        }
    }

    public Timeout readTimeout() {
        return this.readTimeout;
    }

    public Timeout writeTimeout() {
        return this.writeTimeout;
    }

    public Source getSource() {
        return this.source;
    }

    public Sink getSink() {
        synchronized (this) {
            if (!this.hasResponseHeaders) {
                if (!isLocallyInitiated()) {
                    throw new IllegalStateException("reply before requesting the sink");
                }
            }
        }
        return this.sink;
    }

    public void close(ErrorCode rstStatusCode, @Nullable IOException errorException2) throws IOException {
        if (closeInternal(rstStatusCode, errorException2)) {
            this.connection.writeSynReset(this.f47id, rstStatusCode);
        }
    }

    public void closeLater(ErrorCode errorCode2) {
        if (closeInternal(errorCode2, null)) {
            this.connection.writeSynResetLater(this.f47id, errorCode2);
        }
    }

    private boolean closeInternal(ErrorCode errorCode2, @Nullable IOException errorException2) {
        synchronized (this) {
            if (this.errorCode != null) {
                return false;
            }
            if (this.source.finished && this.sink.finished) {
                return false;
            }
            this.errorCode = errorCode2;
            this.errorException = errorException2;
            notifyAll();
            this.connection.removeStream(this.f47id);
            return true;
        }
    }

    /* access modifiers changed from: 0000 */
    public void receiveData(BufferedSource in, int length) throws IOException {
        this.source.receive(in, (long) length);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0019  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void receiveHeaders(okhttp3.Headers r4, boolean r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.hasResponseHeaders     // Catch:{ all -> 0x002f }
            r1 = 1
            if (r0 == 0) goto L_0x0010
            if (r5 != 0) goto L_0x000a
            goto L_0x0010
        L_0x000a:
            okhttp3.internal.http2.Http2Stream$FramingSource r0 = r3.source     // Catch:{ all -> 0x002f }
            r0.trailers = r4     // Catch:{ all -> 0x002f }
            goto L_0x0017
        L_0x0010:
            r3.hasResponseHeaders = r1     // Catch:{ all -> 0x002f }
            java.util.Deque<okhttp3.Headers> r0 = r3.headersQueue     // Catch:{ all -> 0x002f }
            r0.add(r4)     // Catch:{ all -> 0x002f }
        L_0x0017:
            if (r5 == 0) goto L_0x001d
            okhttp3.internal.http2.Http2Stream$FramingSource r0 = r3.source     // Catch:{ all -> 0x002f }
            r0.finished = r1     // Catch:{ all -> 0x002f }
        L_0x001d:
            boolean r0 = r3.isOpen()     // Catch:{ all -> 0x002f }
            r3.notifyAll()     // Catch:{ all -> 0x002f }
            monitor-exit(r3)     // Catch:{ all -> 0x002f }
            if (r0 != 0) goto L_0x002e
            okhttp3.internal.http2.Http2Connection r1 = r3.connection
            int r2 = r3.f47id
            r1.removeStream(r2)
        L_0x002e:
            return
        L_0x002f:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002f }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Stream.receiveHeaders(okhttp3.Headers, boolean):void");
    }

    /* access modifiers changed from: 0000 */
    public synchronized void receiveRstStream(ErrorCode errorCode2) {
        if (this.errorCode == null) {
            this.errorCode = errorCode2;
            notifyAll();
        }
    }

    /* access modifiers changed from: 0000 */
    public void cancelStreamIfNecessary() throws IOException {
        boolean cancel;
        boolean open;
        synchronized (this) {
            cancel = !this.source.finished && this.source.closed && (this.sink.finished || this.sink.closed);
            open = isOpen();
        }
        if (cancel) {
            close(ErrorCode.CANCEL, null);
        } else if (!open) {
            this.connection.removeStream(this.f47id);
        }
    }

    /* access modifiers changed from: 0000 */
    public void addBytesToWriteWindow(long delta) {
        this.bytesLeftInWriteWindow += delta;
        if (delta > 0) {
            notifyAll();
        }
    }

    /* access modifiers changed from: 0000 */
    public void checkOutNotClosed() throws IOException {
        if (this.sink.closed) {
            throw new IOException("stream closed");
        } else if (!this.sink.finished) {
            ErrorCode errorCode2 = this.errorCode;
            if (errorCode2 != null) {
                Throwable th = this.errorException;
                if (th == null) {
                    th = new StreamResetException(errorCode2);
                }
                throw th;
            }
        } else {
            throw new IOException("stream finished");
        }
    }

    /* access modifiers changed from: 0000 */
    public void waitForIo() throws InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }
}
