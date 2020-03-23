package okio;

import java.io.IOException;
import javax.annotation.Nullable;

public final class Pipe {
    final Buffer buffer = new Buffer();
    /* access modifiers changed from: private */
    @Nullable
    public Sink foldedSink;
    final long maxBufferSize;
    private final Sink sink = new PipeSink();
    boolean sinkClosed;
    private final Source source = new PipeSource();
    boolean sourceClosed;

    final class PipeSink implements Sink {
        final PushableTimeout timeout = new PushableTimeout();

        PipeSink() {
        }

        public void write(Buffer source, long byteCount) throws IOException {
            Sink delegate = null;
            synchronized (Pipe.this.buffer) {
                if (!Pipe.this.sinkClosed) {
                    while (true) {
                        if (byteCount <= 0) {
                            break;
                        } else if (Pipe.this.foldedSink != null) {
                            delegate = Pipe.this.foldedSink;
                            break;
                        } else if (!Pipe.this.sourceClosed) {
                            long bufferSpaceAvailable = Pipe.this.maxBufferSize - Pipe.this.buffer.size();
                            if (bufferSpaceAvailable == 0) {
                                this.timeout.waitUntilNotified(Pipe.this.buffer);
                            } else {
                                long bytesToWrite = Math.min(bufferSpaceAvailable, byteCount);
                                Pipe.this.buffer.write(source, bytesToWrite);
                                byteCount -= bytesToWrite;
                                Pipe.this.buffer.notifyAll();
                            }
                        } else {
                            throw new IOException("source is closed");
                        }
                    }
                } else {
                    throw new IllegalStateException("closed");
                }
            }
            if (delegate != null) {
                this.timeout.push(delegate.timeout());
                try {
                    delegate.write(source, byteCount);
                } finally {
                    this.timeout.pop();
                }
            }
        }

        public void flush() throws IOException {
            Sink delegate = null;
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sinkClosed) {
                    throw new IllegalStateException("closed");
                } else if (Pipe.this.foldedSink != null) {
                    delegate = Pipe.this.foldedSink;
                } else if (Pipe.this.sourceClosed) {
                    if (Pipe.this.buffer.size() > 0) {
                        throw new IOException("source is closed");
                    }
                }
            }
            if (delegate != null) {
                this.timeout.push(delegate.timeout());
                try {
                    delegate.flush();
                } finally {
                    this.timeout.pop();
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0048, code lost:
            if (r0 == null) goto L_0x0063;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x004a, code lost:
            r6.timeout.push(r0.timeout());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
            r0.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x005c, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x005d, code lost:
            r6.timeout.pop();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0062, code lost:
            throw r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0063, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() throws java.io.IOException {
            /*
                r6 = this;
                r0 = 0
                okio.Pipe r1 = okio.Pipe.this
                okio.Buffer r1 = r1.buffer
                monitor-enter(r1)
                okio.Pipe r2 = okio.Pipe.this     // Catch:{ all -> 0x0064 }
                boolean r2 = r2.sinkClosed     // Catch:{ all -> 0x0064 }
                if (r2 == 0) goto L_0x000e
                monitor-exit(r1)     // Catch:{ all -> 0x0064 }
                return
            L_0x000e:
                okio.Pipe r2 = okio.Pipe.this     // Catch:{ all -> 0x0064 }
                okio.Sink r2 = r2.foldedSink     // Catch:{ all -> 0x0064 }
                if (r2 == 0) goto L_0x001e
                okio.Pipe r2 = okio.Pipe.this     // Catch:{ all -> 0x0064 }
                okio.Sink r2 = r2.foldedSink     // Catch:{ all -> 0x0064 }
                r0 = r2
                goto L_0x0047
            L_0x001e:
                okio.Pipe r2 = okio.Pipe.this     // Catch:{ all -> 0x0064 }
                boolean r2 = r2.sourceClosed     // Catch:{ all -> 0x0064 }
                if (r2 == 0) goto L_0x003b
                okio.Pipe r2 = okio.Pipe.this     // Catch:{ all -> 0x0064 }
                okio.Buffer r2 = r2.buffer     // Catch:{ all -> 0x0064 }
                long r2 = r2.size()     // Catch:{ all -> 0x0064 }
                r4 = 0
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 > 0) goto L_0x0033
                goto L_0x003b
            L_0x0033:
                java.io.IOException r2 = new java.io.IOException     // Catch:{ all -> 0x0064 }
                java.lang.String r3 = "source is closed"
                r2.<init>(r3)     // Catch:{ all -> 0x0064 }
                throw r2     // Catch:{ all -> 0x0064 }
            L_0x003b:
                okio.Pipe r2 = okio.Pipe.this     // Catch:{ all -> 0x0064 }
                r3 = 1
                r2.sinkClosed = r3     // Catch:{ all -> 0x0064 }
                okio.Pipe r2 = okio.Pipe.this     // Catch:{ all -> 0x0064 }
                okio.Buffer r2 = r2.buffer     // Catch:{ all -> 0x0064 }
                r2.notifyAll()     // Catch:{ all -> 0x0064 }
            L_0x0047:
                monitor-exit(r1)     // Catch:{ all -> 0x0064 }
                if (r0 == 0) goto L_0x0063
                okio.PushableTimeout r1 = r6.timeout
                okio.Timeout r2 = r0.timeout()
                r1.push(r2)
                r0.close()     // Catch:{ all -> 0x005c }
                okio.PushableTimeout r1 = r6.timeout
                r1.pop()
                goto L_0x0063
            L_0x005c:
                r1 = move-exception
                okio.PushableTimeout r2 = r6.timeout
                r2.pop()
                throw r1
            L_0x0063:
                return
            L_0x0064:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0064 }
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.Pipe.PipeSink.close():void");
        }

        public Timeout timeout() {
            return this.timeout;
        }
    }

    final class PipeSource implements Source {
        final Timeout timeout = new Timeout();

        PipeSource() {
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            synchronized (Pipe.this.buffer) {
                if (!Pipe.this.sourceClosed) {
                    while (Pipe.this.buffer.size() == 0) {
                        if (Pipe.this.sinkClosed) {
                            return -1;
                        }
                        this.timeout.waitUntilNotified(Pipe.this.buffer);
                    }
                    long result = Pipe.this.buffer.read(sink, byteCount);
                    Pipe.this.buffer.notifyAll();
                    return result;
                }
                throw new IllegalStateException("closed");
            }
        }

        public void close() throws IOException {
            synchronized (Pipe.this.buffer) {
                Pipe.this.sourceClosed = true;
                Pipe.this.buffer.notifyAll();
            }
        }

        public Timeout timeout() {
            return this.timeout;
        }
    }

    public Pipe(long maxBufferSize2) {
        if (maxBufferSize2 >= 1) {
            this.maxBufferSize = maxBufferSize2;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("maxBufferSize < 1: ");
        sb.append(maxBufferSize2);
        throw new IllegalArgumentException(sb.toString());
    }

    public final Source source() {
        return this.source;
    }

    public final Sink sink() {
        return this.sink;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r7.write(r1, r1.size);
        r7.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0034, code lost:
        if (1 != 0) goto L_0x0000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0036, code lost:
        r3 = r6.buffer;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0038, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r6.sourceClosed = true;
        r6.buffer.notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0040, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0046, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0047, code lost:
        if (0 == 0) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x004b, code lost:
        monitor-enter(r6.buffer);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r6.sourceClosed = true;
        r6.buffer.notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0058, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void fold(okio.Sink r7) throws java.io.IOException {
        /*
            r6 = this;
        L_0x0000:
            okio.Buffer r0 = r6.buffer
            monitor-enter(r0)
            okio.Sink r1 = r6.foldedSink     // Catch:{ all -> 0x0061 }
            if (r1 != 0) goto L_0x0059
            okio.Buffer r1 = r6.buffer     // Catch:{ all -> 0x0061 }
            boolean r1 = r1.exhausted()     // Catch:{ all -> 0x0061 }
            r2 = 1
            if (r1 == 0) goto L_0x0016
            r6.sourceClosed = r2     // Catch:{ all -> 0x0061 }
            r6.foldedSink = r7     // Catch:{ all -> 0x0061 }
            monitor-exit(r0)     // Catch:{ all -> 0x0061 }
            return
        L_0x0016:
            okio.Buffer r1 = new okio.Buffer     // Catch:{ all -> 0x0061 }
            r1.<init>()     // Catch:{ all -> 0x0061 }
            okio.Buffer r3 = r6.buffer     // Catch:{ all -> 0x0061 }
            okio.Buffer r4 = r6.buffer     // Catch:{ all -> 0x0061 }
            long r4 = r4.size     // Catch:{ all -> 0x0061 }
            r1.write(r3, r4)     // Catch:{ all -> 0x0061 }
            okio.Buffer r3 = r6.buffer     // Catch:{ all -> 0x0061 }
            r3.notifyAll()     // Catch:{ all -> 0x0061 }
            monitor-exit(r0)     // Catch:{ all -> 0x0061 }
            r0 = 0
            long r3 = r1.size     // Catch:{ all -> 0x0046 }
            r7.write(r1, r3)     // Catch:{ all -> 0x0046 }
            r7.flush()     // Catch:{ all -> 0x0046 }
            r0 = 1
            if (r0 != 0) goto L_0x0045
            okio.Buffer r3 = r6.buffer
            monitor-enter(r3)
            r6.sourceClosed = r2     // Catch:{ all -> 0x0042 }
            okio.Buffer r2 = r6.buffer     // Catch:{ all -> 0x0042 }
            r2.notifyAll()     // Catch:{ all -> 0x0042 }
            monitor-exit(r3)     // Catch:{ all -> 0x0042 }
            goto L_0x0045
        L_0x0042:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0042 }
            throw r2
        L_0x0045:
            goto L_0x0000
        L_0x0046:
            r3 = move-exception
            if (r0 != 0) goto L_0x0058
            okio.Buffer r4 = r6.buffer
            monitor-enter(r4)
            r6.sourceClosed = r2     // Catch:{ all -> 0x0055 }
            okio.Buffer r2 = r6.buffer     // Catch:{ all -> 0x0055 }
            r2.notifyAll()     // Catch:{ all -> 0x0055 }
            monitor-exit(r4)     // Catch:{ all -> 0x0055 }
            goto L_0x0058
        L_0x0055:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0055 }
            throw r2
        L_0x0058:
            throw r3
        L_0x0059:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0061 }
            java.lang.String r2 = "sink already folded"
            r1.<init>(r2)     // Catch:{ all -> 0x0061 }
            throw r1     // Catch:{ all -> 0x0061 }
        L_0x0061:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0061 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Pipe.fold(okio.Sink):void");
    }
}
