package okhttp3.internal.p007ws;

import java.io.IOException;
import java.util.Random;
import okio.Buffer;
import okio.Buffer.UnsafeCursor;
import okio.BufferedSink;
import okio.ByteString;
import okio.Sink;
import okio.Timeout;

/* renamed from: okhttp3.internal.ws.WebSocketWriter */
final class WebSocketWriter {
    boolean activeWriter;
    final Buffer buffer = new Buffer();
    final FrameSink frameSink = new FrameSink();
    final boolean isClient;
    private final UnsafeCursor maskCursor;
    private final byte[] maskKey;
    final Random random;
    final BufferedSink sink;
    final Buffer sinkBuffer;
    boolean writerClosed;

    /* renamed from: okhttp3.internal.ws.WebSocketWriter$FrameSink */
    final class FrameSink implements Sink {
        boolean closed;
        long contentLength;
        int formatOpcode;
        boolean isFirstFrame;

        FrameSink() {
        }

        public void write(Buffer source, long byteCount) throws IOException {
            if (!this.closed) {
                WebSocketWriter.this.buffer.write(source, byteCount);
                boolean deferWrite = this.isFirstFrame && this.contentLength != -1 && WebSocketWriter.this.buffer.size() > this.contentLength - 8192;
                long emitCount = WebSocketWriter.this.buffer.completeSegmentByteCount();
                if (emitCount > 0 && !deferWrite) {
                    WebSocketWriter.this.writeMessageFrame(this.formatOpcode, emitCount, this.isFirstFrame, false);
                    this.isFirstFrame = false;
                    return;
                }
                return;
            }
            throw new IOException("closed");
        }

        public void flush() throws IOException {
            if (!this.closed) {
                WebSocketWriter webSocketWriter = WebSocketWriter.this;
                webSocketWriter.writeMessageFrame(this.formatOpcode, webSocketWriter.buffer.size(), this.isFirstFrame, false);
                this.isFirstFrame = false;
                return;
            }
            throw new IOException("closed");
        }

        public Timeout timeout() {
            return WebSocketWriter.this.sink.timeout();
        }

        public void close() throws IOException {
            if (!this.closed) {
                WebSocketWriter webSocketWriter = WebSocketWriter.this;
                webSocketWriter.writeMessageFrame(this.formatOpcode, webSocketWriter.buffer.size(), this.isFirstFrame, true);
                this.closed = true;
                WebSocketWriter.this.activeWriter = false;
                return;
            }
            throw new IOException("closed");
        }
    }

    WebSocketWriter(boolean isClient2, BufferedSink sink2, Random random2) {
        if (sink2 == null) {
            throw new NullPointerException("sink == null");
        } else if (random2 != null) {
            this.isClient = isClient2;
            this.sink = sink2;
            this.sinkBuffer = sink2.buffer();
            this.random = random2;
            UnsafeCursor unsafeCursor = null;
            this.maskKey = isClient2 ? new byte[4] : null;
            if (isClient2) {
                unsafeCursor = new UnsafeCursor();
            }
            this.maskCursor = unsafeCursor;
        } else {
            throw new NullPointerException("random == null");
        }
    }

    /* access modifiers changed from: 0000 */
    public void writePing(ByteString payload) throws IOException {
        writeControlFrame(9, payload);
    }

    /* access modifiers changed from: 0000 */
    public void writePong(ByteString payload) throws IOException {
        writeControlFrame(10, payload);
    }

    /* access modifiers changed from: 0000 */
    public void writeClose(int code, ByteString reason) throws IOException {
        ByteString payload = ByteString.EMPTY;
        if (!(code == 0 && reason == null)) {
            if (code != 0) {
                WebSocketProtocol.validateCloseCode(code);
            }
            Buffer buffer2 = new Buffer();
            buffer2.writeShort(code);
            if (reason != null) {
                buffer2.write(reason);
            }
            payload = buffer2.readByteString();
        }
        try {
            writeControlFrame(8, payload);
        } finally {
            this.writerClosed = true;
        }
    }

    private void writeControlFrame(int opcode, ByteString payload) throws IOException {
        if (!this.writerClosed) {
            int length = payload.size();
            if (((long) length) <= 125) {
                this.sinkBuffer.writeByte(opcode | 128);
                int b1 = length;
                if (this.isClient) {
                    this.sinkBuffer.writeByte(b1 | 128);
                    this.random.nextBytes(this.maskKey);
                    this.sinkBuffer.write(this.maskKey);
                    if (length > 0) {
                        long payloadStart = this.sinkBuffer.size();
                        this.sinkBuffer.write(payload);
                        this.sinkBuffer.readAndWriteUnsafe(this.maskCursor);
                        this.maskCursor.seek(payloadStart);
                        WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                        this.maskCursor.close();
                    }
                } else {
                    this.sinkBuffer.writeByte(b1);
                    this.sinkBuffer.write(payload);
                }
                this.sink.flush();
                return;
            }
            throw new IllegalArgumentException("Payload size must be less than or equal to 125");
        }
        throw new IOException("closed");
    }

    /* access modifiers changed from: 0000 */
    public Sink newMessageSink(int formatOpcode, long contentLength) {
        if (!this.activeWriter) {
            this.activeWriter = true;
            FrameSink frameSink2 = this.frameSink;
            frameSink2.formatOpcode = formatOpcode;
            frameSink2.contentLength = contentLength;
            frameSink2.isFirstFrame = true;
            frameSink2.closed = false;
            return frameSink2;
        }
        throw new IllegalStateException("Another message writer is active. Did you call close()?");
    }

    /* access modifiers changed from: 0000 */
    public void writeMessageFrame(int formatOpcode, long byteCount, boolean isFirstFrame, boolean isFinal) throws IOException {
        if (!this.writerClosed) {
            int b0 = isFirstFrame ? formatOpcode : 0;
            if (isFinal) {
                b0 |= 128;
            }
            this.sinkBuffer.writeByte(b0);
            int b1 = 0;
            if (this.isClient) {
                b1 = 0 | 128;
            }
            if (byteCount <= 125) {
                this.sinkBuffer.writeByte(b1 | ((int) byteCount));
            } else if (byteCount <= 65535) {
                this.sinkBuffer.writeByte(b1 | 126);
                this.sinkBuffer.writeShort((int) byteCount);
            } else {
                this.sinkBuffer.writeByte(b1 | 127);
                this.sinkBuffer.writeLong(byteCount);
            }
            if (this.isClient) {
                this.random.nextBytes(this.maskKey);
                this.sinkBuffer.write(this.maskKey);
                if (byteCount > 0) {
                    long bufferStart = this.sinkBuffer.size();
                    this.sinkBuffer.write(this.buffer, byteCount);
                    this.sinkBuffer.readAndWriteUnsafe(this.maskCursor);
                    this.maskCursor.seek(bufferStart);
                    WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                    this.maskCursor.close();
                }
            } else {
                this.sinkBuffer.write(this.buffer, byteCount);
            }
            this.sink.emit();
            return;
        }
        throw new IOException("closed");
    }
}
