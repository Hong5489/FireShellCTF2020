package com.squareup.picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

final class MarkableInputStream extends InputStream {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int DEFAULT_LIMIT_INCREMENT = 1024;
    private boolean allowExpire;
    private long defaultMark;

    /* renamed from: in */
    private final InputStream f32in;
    private long limit;
    private int limitIncrement;
    private long offset;
    private long reset;

    MarkableInputStream(InputStream in) {
        this(in, 4096);
    }

    MarkableInputStream(InputStream in, int size) {
        this(in, size, 1024);
    }

    private MarkableInputStream(InputStream in, int size, int limitIncrement2) {
        this.defaultMark = -1;
        this.allowExpire = true;
        this.limitIncrement = -1;
        if (!in.markSupported()) {
            in = new BufferedInputStream(in, size);
        }
        this.f32in = in;
        this.limitIncrement = limitIncrement2;
    }

    public void mark(int readLimit) {
        this.defaultMark = savePosition(readLimit);
    }

    public long savePosition(int readLimit) {
        long offsetLimit = this.offset + ((long) readLimit);
        if (this.limit < offsetLimit) {
            setLimit(offsetLimit);
        }
        return this.offset;
    }

    public void allowMarksToExpire(boolean allowExpire2) {
        this.allowExpire = allowExpire2;
    }

    private void setLimit(long limit2) {
        try {
            if (this.reset >= this.offset || this.offset > this.limit) {
                this.reset = this.offset;
                this.f32in.mark((int) (limit2 - this.offset));
            } else {
                this.f32in.reset();
                this.f32in.mark((int) (limit2 - this.reset));
                skip(this.reset, this.offset);
            }
            this.limit = limit2;
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to mark: ");
            sb.append(e);
            throw new IllegalStateException(sb.toString());
        }
    }

    public void reset() throws IOException {
        reset(this.defaultMark);
    }

    public void reset(long token) throws IOException {
        if (this.offset > this.limit || token < this.reset) {
            throw new IOException("Cannot reset");
        }
        this.f32in.reset();
        skip(this.reset, token);
        this.offset = token;
    }

    private void skip(long current, long target) throws IOException {
        while (current < target) {
            long skipped = this.f32in.skip(target - current);
            if (skipped == 0) {
                if (read() != -1) {
                    skipped = 1;
                } else {
                    return;
                }
            }
            current += skipped;
        }
    }

    public int read() throws IOException {
        if (!this.allowExpire) {
            long j = this.offset + 1;
            long j2 = this.limit;
            if (j > j2) {
                setLimit(j2 + ((long) this.limitIncrement));
            }
        }
        int result = this.f32in.read();
        if (result != -1) {
            this.offset++;
        }
        return result;
    }

    public int read(byte[] buffer) throws IOException {
        if (!this.allowExpire) {
            long j = this.offset;
            if (((long) buffer.length) + j > this.limit) {
                setLimit(j + ((long) buffer.length) + ((long) this.limitIncrement));
            }
        }
        int count = this.f32in.read(buffer);
        if (count != -1) {
            this.offset += (long) count;
        }
        return count;
    }

    public int read(byte[] buffer, int offset2, int length) throws IOException {
        if (!this.allowExpire) {
            long j = this.offset;
            if (((long) length) + j > this.limit) {
                setLimit(j + ((long) length) + ((long) this.limitIncrement));
            }
        }
        int count = this.f32in.read(buffer, offset2, length);
        if (count != -1) {
            this.offset += (long) count;
        }
        return count;
    }

    public long skip(long byteCount) throws IOException {
        if (!this.allowExpire) {
            long j = this.offset;
            if (j + byteCount > this.limit) {
                setLimit(j + byteCount + ((long) this.limitIncrement));
            }
        }
        long skipped = this.f32in.skip(byteCount);
        this.offset += skipped;
        return skipped;
    }

    public int available() throws IOException {
        return this.f32in.available();
    }

    public void close() throws IOException {
        this.f32in.close();
    }

    public boolean markSupported() {
        return this.f32in.markSupported();
    }
}
