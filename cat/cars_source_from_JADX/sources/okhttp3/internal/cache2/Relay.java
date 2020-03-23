package okhttp3.internal.cache2;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

final class Relay {
    private static final long FILE_HEADER_SIZE = 32;
    static final ByteString PREFIX_CLEAN = ByteString.encodeUtf8("OkHttp cache v1\n");
    static final ByteString PREFIX_DIRTY = ByteString.encodeUtf8("OkHttp DIRTY :(\n");
    private static final int SOURCE_FILE = 2;
    private static final int SOURCE_UPSTREAM = 1;
    final Buffer buffer = new Buffer();
    final long bufferMaxSize;
    boolean complete;
    RandomAccessFile file;
    private final ByteString metadata;
    int sourceCount;
    Source upstream;
    final Buffer upstreamBuffer = new Buffer();
    long upstreamPos;
    Thread upstreamReader;

    class RelaySource implements Source {
        private FileOperator fileOperator = new FileOperator(Relay.this.file.getChannel());
        private long sourcePos;
        private final Timeout timeout = new Timeout();

        RelaySource() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0038, code lost:
            r7 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x003a, code lost:
            r7 = r9 - r1.this$0.buffer.size();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0048, code lost:
            if (r1.sourcePos >= r7) goto L_0x012b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x004c, code lost:
            r7 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:80:?, code lost:
            r5 = java.lang.Math.min(r2, r9 - r1.sourcePos);
            r1.this$0.buffer.copyTo(r22, r1.sourcePos - r7, r5);
            r1.sourcePos += r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:82:0x0147, code lost:
            return r5;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long read(okio.Buffer r22, long r23) throws java.io.IOException {
            /*
                r21 = this;
                r1 = r21
                r2 = r23
                okhttp3.internal.cache2.FileOperator r0 = r1.fileOperator
                if (r0 == 0) goto L_0x014b
                okhttp3.internal.cache2.Relay r4 = okhttp3.internal.cache2.Relay.this
                monitor-enter(r4)
            L_0x000b:
                long r5 = r1.sourcePos     // Catch:{ all -> 0x0148 }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0148 }
                long r7 = r0.upstreamPos     // Catch:{ all -> 0x0148 }
                r9 = r7
                int r0 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                r5 = -1
                if (r0 != 0) goto L_0x003a
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0148 }
                boolean r0 = r0.complete     // Catch:{ all -> 0x0148 }
                if (r0 == 0) goto L_0x0020
                monitor-exit(r4)     // Catch:{ all -> 0x0148 }
                return r5
            L_0x0020:
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0148 }
                java.lang.Thread r0 = r0.upstreamReader     // Catch:{ all -> 0x0148 }
                if (r0 == 0) goto L_0x002e
                okio.Timeout r0 = r1.timeout     // Catch:{ all -> 0x0148 }
                okhttp3.internal.cache2.Relay r5 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0148 }
                r0.waitUntilNotified(r5)     // Catch:{ all -> 0x0148 }
                goto L_0x000b
            L_0x002e:
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0148 }
                java.lang.Thread r7 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0148 }
                r0.upstreamReader = r7     // Catch:{ all -> 0x0148 }
                r0 = 1
                monitor-exit(r4)     // Catch:{ all -> 0x0148 }
                r7 = r0
                goto L_0x004d
            L_0x003a:
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0148 }
                okio.Buffer r0 = r0.buffer     // Catch:{ all -> 0x0148 }
                long r7 = r0.size()     // Catch:{ all -> 0x0148 }
                long r7 = r9 - r7
                long r11 = r1.sourcePos     // Catch:{ all -> 0x0148 }
                int r0 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
                if (r0 >= 0) goto L_0x012b
                r0 = 2
                monitor-exit(r4)     // Catch:{ all -> 0x0148 }
                r7 = r0
            L_0x004d:
                r0 = 2
                r11 = 32
                if (r7 != r0) goto L_0x006c
                long r4 = r1.sourcePos
                long r4 = r9 - r4
                long r4 = java.lang.Math.min(r2, r4)
                okhttp3.internal.cache2.FileOperator r13 = r1.fileOperator
                long r14 = r1.sourcePos
                long r14 = r14 + r11
                r16 = r22
                r17 = r4
                r13.read(r14, r16, r17)
                long r11 = r1.sourcePos
                long r11 = r11 + r4
                r1.sourcePos = r11
                return r4
            L_0x006c:
                r4 = 0
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0118 }
                okio.Source r0 = r0.upstream     // Catch:{ all -> 0x0118 }
                okhttp3.internal.cache2.Relay r8 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0118 }
                okio.Buffer r8 = r8.upstreamBuffer     // Catch:{ all -> 0x0118 }
                okhttp3.internal.cache2.Relay r13 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0118 }
                long r13 = r13.bufferMaxSize     // Catch:{ all -> 0x0118 }
                long r13 = r0.read(r8, r13)     // Catch:{ all -> 0x0118 }
                int r0 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
                if (r0 != 0) goto L_0x0098
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0118 }
                r0.commit(r9)     // Catch:{ all -> 0x0118 }
                okhttp3.internal.cache2.Relay r8 = okhttp3.internal.cache2.Relay.this
                monitor-enter(r8)
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0095 }
                r0.upstreamReader = r4     // Catch:{ all -> 0x0095 }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0095 }
                r0.notifyAll()     // Catch:{ all -> 0x0095 }
                monitor-exit(r8)     // Catch:{ all -> 0x0095 }
                return r5
            L_0x0095:
                r0 = move-exception
                monitor-exit(r8)     // Catch:{ all -> 0x0095 }
                throw r0
            L_0x0098:
                long r5 = java.lang.Math.min(r13, r2)     // Catch:{ all -> 0x0118 }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0118 }
                okio.Buffer r15 = r0.upstreamBuffer     // Catch:{ all -> 0x0118 }
                r17 = 0
                r16 = r22
                r19 = r5
                r15.copyTo(r16, r17, r19)     // Catch:{ all -> 0x0118 }
                long r11 = r1.sourcePos     // Catch:{ all -> 0x0118 }
                long r11 = r11 + r5
                r1.sourcePos = r11     // Catch:{ all -> 0x0118 }
                okhttp3.internal.cache2.FileOperator r0 = r1.fileOperator     // Catch:{ all -> 0x0118 }
                r11 = 32
                long r16 = r9 + r11
                okhttp3.internal.cache2.Relay r8 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0118 }
                okio.Buffer r8 = r8.upstreamBuffer     // Catch:{ all -> 0x0118 }
                okio.Buffer r18 = r8.clone()     // Catch:{ all -> 0x0118 }
                r15 = r0
                r19 = r13
                r15.write(r16, r18, r19)     // Catch:{ all -> 0x0118 }
                okhttp3.internal.cache2.Relay r8 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0118 }
                monitor-enter(r8)     // Catch:{ all -> 0x0118 }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0111 }
                okio.Buffer r0 = r0.buffer     // Catch:{ all -> 0x0111 }
                okhttp3.internal.cache2.Relay r11 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0111 }
                okio.Buffer r11 = r11.upstreamBuffer     // Catch:{ all -> 0x0111 }
                r0.write(r11, r13)     // Catch:{ all -> 0x0111 }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0111 }
                okio.Buffer r0 = r0.buffer     // Catch:{ all -> 0x0111 }
                long r11 = r0.size()     // Catch:{ all -> 0x0111 }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0111 }
                r16 = r5
                long r4 = r0.bufferMaxSize     // Catch:{ all -> 0x0116 }
                int r0 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
                if (r0 <= 0) goto L_0x00f6
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0116 }
                okio.Buffer r0 = r0.buffer     // Catch:{ all -> 0x0116 }
                okhttp3.internal.cache2.Relay r4 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0116 }
                okio.Buffer r4 = r4.buffer     // Catch:{ all -> 0x0116 }
                long r4 = r4.size()     // Catch:{ all -> 0x0116 }
                okhttp3.internal.cache2.Relay r6 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0116 }
                long r11 = r6.bufferMaxSize     // Catch:{ all -> 0x0116 }
                long r4 = r4 - r11
                r0.skip(r4)     // Catch:{ all -> 0x0116 }
            L_0x00f6:
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0116 }
                long r4 = r0.upstreamPos     // Catch:{ all -> 0x0116 }
                long r4 = r4 + r13
                r0.upstreamPos = r4     // Catch:{ all -> 0x0116 }
                monitor-exit(r8)     // Catch:{ all -> 0x0116 }
                okhttp3.internal.cache2.Relay r4 = okhttp3.internal.cache2.Relay.this
                monitor-enter(r4)
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x010e }
                r5 = 0
                r0.upstreamReader = r5     // Catch:{ all -> 0x010e }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x010e }
                r0.notifyAll()     // Catch:{ all -> 0x010e }
                monitor-exit(r4)     // Catch:{ all -> 0x010e }
                return r16
            L_0x010e:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x010e }
                throw r0
            L_0x0111:
                r0 = move-exception
                r16 = r5
            L_0x0114:
                monitor-exit(r8)     // Catch:{ all -> 0x0116 }
                throw r0     // Catch:{ all -> 0x0118 }
            L_0x0116:
                r0 = move-exception
                goto L_0x0114
            L_0x0118:
                r0 = move-exception
                okhttp3.internal.cache2.Relay r5 = okhttp3.internal.cache2.Relay.this
                monitor-enter(r5)
                okhttp3.internal.cache2.Relay r4 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0128 }
                r6 = 0
                r4.upstreamReader = r6     // Catch:{ all -> 0x0128 }
                okhttp3.internal.cache2.Relay r4 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0128 }
                r4.notifyAll()     // Catch:{ all -> 0x0128 }
                monitor-exit(r5)     // Catch:{ all -> 0x0128 }
                throw r0
            L_0x0128:
                r0 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x0128 }
                throw r0
            L_0x012b:
                long r5 = r1.sourcePos     // Catch:{ all -> 0x0148 }
                long r5 = r9 - r5
                long r5 = java.lang.Math.min(r2, r5)     // Catch:{ all -> 0x0148 }
                okhttp3.internal.cache2.Relay r0 = okhttp3.internal.cache2.Relay.this     // Catch:{ all -> 0x0148 }
                okio.Buffer r11 = r0.buffer     // Catch:{ all -> 0x0148 }
                long r12 = r1.sourcePos     // Catch:{ all -> 0x0148 }
                long r13 = r12 - r7
                r12 = r22
                r15 = r5
                r11.copyTo(r12, r13, r15)     // Catch:{ all -> 0x0148 }
                long r11 = r1.sourcePos     // Catch:{ all -> 0x0148 }
                long r11 = r11 + r5
                r1.sourcePos = r11     // Catch:{ all -> 0x0148 }
                monitor-exit(r4)     // Catch:{ all -> 0x0148 }
                return r5
            L_0x0148:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0148 }
                throw r0
            L_0x014b:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r4 = "closed"
                r0.<init>(r4)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.cache2.Relay.RelaySource.read(okio.Buffer, long):long");
        }

        public Timeout timeout() {
            return this.timeout;
        }

        public void close() throws IOException {
            if (this.fileOperator != null) {
                this.fileOperator = null;
                RandomAccessFile fileToClose = null;
                synchronized (Relay.this) {
                    Relay.this.sourceCount--;
                    if (Relay.this.sourceCount == 0) {
                        fileToClose = Relay.this.file;
                        Relay.this.file = null;
                    }
                }
                if (fileToClose != null) {
                    Util.closeQuietly((Closeable) fileToClose);
                }
            }
        }
    }

    private Relay(RandomAccessFile file2, Source upstream2, long upstreamPos2, ByteString metadata2, long bufferMaxSize2) {
        this.file = file2;
        this.upstream = upstream2;
        this.complete = upstream2 == null;
        this.upstreamPos = upstreamPos2;
        this.metadata = metadata2;
        this.bufferMaxSize = bufferMaxSize2;
    }

    public static Relay edit(File file2, Source upstream2, ByteString metadata2, long bufferMaxSize2) throws IOException {
        File file3 = file2;
        RandomAccessFile randomAccessFile = new RandomAccessFile(file2, "rw");
        Relay relay = new Relay(randomAccessFile, upstream2, 0, metadata2, bufferMaxSize2);
        randomAccessFile.setLength(0);
        relay.writeHeader(PREFIX_DIRTY, -1, -1);
        return relay;
    }

    public static Relay read(File file2) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file2, "rw");
        FileOperator fileOperator = new FileOperator(randomAccessFile.getChannel());
        Buffer header = new Buffer();
        fileOperator.read(0, header, FILE_HEADER_SIZE);
        if (header.readByteString((long) PREFIX_CLEAN.size()).equals(PREFIX_CLEAN)) {
            long upstreamSize = header.readLong();
            long metadataSize = header.readLong();
            Buffer metadataBuffer = new Buffer();
            fileOperator.read(upstreamSize + FILE_HEADER_SIZE, metadataBuffer, metadataSize);
            Relay relay = new Relay(randomAccessFile, null, upstreamSize, metadataBuffer.readByteString(), 0);
            return relay;
        }
        throw new IOException("unreadable cache file");
    }

    private void writeHeader(ByteString prefix, long upstreamSize, long metadataSize) throws IOException {
        Buffer header = new Buffer();
        header.write(prefix);
        header.writeLong(upstreamSize);
        header.writeLong(metadataSize);
        if (header.size() == FILE_HEADER_SIZE) {
            new FileOperator(this.file.getChannel()).write(0, header, FILE_HEADER_SIZE);
            return;
        }
        throw new IllegalArgumentException();
    }

    private void writeMetadata(long upstreamSize) throws IOException {
        Buffer metadataBuffer = new Buffer();
        metadataBuffer.write(this.metadata);
        new FileOperator(this.file.getChannel()).write(FILE_HEADER_SIZE + upstreamSize, metadataBuffer, (long) this.metadata.size());
    }

    /* access modifiers changed from: 0000 */
    public void commit(long upstreamSize) throws IOException {
        writeMetadata(upstreamSize);
        this.file.getChannel().force(false);
        writeHeader(PREFIX_CLEAN, upstreamSize, (long) this.metadata.size());
        this.file.getChannel().force(false);
        synchronized (this) {
            this.complete = true;
        }
        Util.closeQuietly((Closeable) this.upstream);
        this.upstream = null;
    }

    /* access modifiers changed from: 0000 */
    public boolean isClosed() {
        return this.file == null;
    }

    public ByteString metadata() {
        return this.metadata;
    }

    public Source newSource() {
        synchronized (this) {
            if (this.file == null) {
                return null;
            }
            this.sourceCount++;
            return new RelaySource();
        }
    }
}
