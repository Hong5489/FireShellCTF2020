package okhttp3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;

public abstract class RequestBody {
    @Nullable
    public abstract MediaType contentType();

    public abstract void writeTo(BufferedSink bufferedSink) throws IOException;

    public long contentLength() throws IOException {
        return -1;
    }

    public boolean isDuplex() {
        return false;
    }

    public boolean isOneShot() {
        return false;
    }

    public static RequestBody create(@Nullable MediaType contentType, String content) {
        Charset charset = StandardCharsets.UTF_8;
        if (contentType != null) {
            charset = contentType.charset();
            if (charset == null) {
                charset = StandardCharsets.UTF_8;
                StringBuilder sb = new StringBuilder();
                sb.append(contentType);
                sb.append("; charset=utf-8");
                contentType = MediaType.parse(sb.toString());
            }
        }
        return create(contentType, content.getBytes(charset));
    }

    public static RequestBody create(@Nullable final MediaType contentType, final ByteString content) {
        return new RequestBody() {
            @Nullable
            public MediaType contentType() {
                return MediaType.this;
            }

            public long contentLength() throws IOException {
                return (long) content.size();
            }

            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content);
            }
        };
    }

    public static RequestBody create(@Nullable MediaType contentType, byte[] content) {
        return create(contentType, content, 0, content.length);
    }

    public static RequestBody create(@Nullable final MediaType contentType, final byte[] content, final int offset, final int byteCount) {
        if (content != null) {
            Util.checkOffsetAndCount((long) content.length, (long) offset, (long) byteCount);
            return new RequestBody() {
                @Nullable
                public MediaType contentType() {
                    return MediaType.this;
                }

                public long contentLength() {
                    return (long) byteCount;
                }

                public void writeTo(BufferedSink sink) throws IOException {
                    sink.write(content, offset, byteCount);
                }
            };
        }
        throw new NullPointerException("content == null");
    }

    public static RequestBody create(@Nullable final MediaType contentType, final File file) {
        if (file != null) {
            return new RequestBody() {
                @Nullable
                public MediaType contentType() {
                    return MediaType.this;
                }

                public long contentLength() {
                    return file.length();
                }

                /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
                    r0.close();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
                    r3 = move-exception;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:13:0x0019, code lost:
                    r1.addSuppressed(r3);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:14:0x001c, code lost:
                    throw r2;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:8:0x0011, code lost:
                    r2 = move-exception;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
                    if (r0 != null) goto L_0x0014;
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void writeTo(okio.BufferedSink r5) throws java.io.IOException {
                    /*
                        r4 = this;
                        java.io.File r0 = r3
                        okio.Source r0 = okio.Okio.source(r0)
                        r5.writeAll(r0)     // Catch:{ all -> 0x000f }
                        if (r0 == 0) goto L_0x000e
                        r0.close()
                    L_0x000e:
                        return
                    L_0x000f:
                        r1 = move-exception
                        throw r1     // Catch:{ all -> 0x0011 }
                    L_0x0011:
                        r2 = move-exception
                        if (r0 == 0) goto L_0x001c
                        r0.close()     // Catch:{ all -> 0x0018 }
                        goto L_0x001c
                    L_0x0018:
                        r3 = move-exception
                        r1.addSuppressed(r3)
                    L_0x001c:
                        throw r2
                    */
                    throw new UnsupportedOperationException("Method not decompiled: okhttp3.RequestBody.C03403.writeTo(okio.BufferedSink):void");
                }
            };
        }
        throw new NullPointerException("file == null");
    }
}
