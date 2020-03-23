package okhttp3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import kotlin.text.Typography;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;

public final class MultipartBody extends RequestBody {
    public static final MediaType ALTERNATIVE = MediaType.get("multipart/alternative");
    private static final byte[] COLONSPACE = {58, 32};
    private static final byte[] CRLF = {13, 10};
    private static final byte[] DASHDASH = {45, 45};
    public static final MediaType DIGEST = MediaType.get("multipart/digest");
    public static final MediaType FORM = MediaType.get("multipart/form-data");
    public static final MediaType MIXED = MediaType.get("multipart/mixed");
    public static final MediaType PARALLEL = MediaType.get("multipart/parallel");
    private final ByteString boundary;
    private long contentLength = -1;
    private final MediaType contentType;
    private final MediaType originalType;
    private final List<Part> parts;

    public static final class Builder {
        private final ByteString boundary;
        private final List<Part> parts;
        private MediaType type;

        public Builder() {
            this(UUID.randomUUID().toString());
        }

        public Builder(String boundary2) {
            this.type = MultipartBody.MIXED;
            this.parts = new ArrayList();
            this.boundary = ByteString.encodeUtf8(boundary2);
        }

        public Builder setType(MediaType type2) {
            if (type2 == null) {
                throw new NullPointerException("type == null");
            } else if (type2.type().equals("multipart")) {
                this.type = type2;
                return this;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("multipart != ");
                sb.append(type2);
                throw new IllegalArgumentException(sb.toString());
            }
        }

        public Builder addPart(RequestBody body) {
            return addPart(Part.create(body));
        }

        public Builder addPart(@Nullable Headers headers, RequestBody body) {
            return addPart(Part.create(headers, body));
        }

        public Builder addFormDataPart(String name, String value) {
            return addPart(Part.createFormData(name, value));
        }

        public Builder addFormDataPart(String name, @Nullable String filename, RequestBody body) {
            return addPart(Part.createFormData(name, filename, body));
        }

        public Builder addPart(Part part) {
            if (part != null) {
                this.parts.add(part);
                return this;
            }
            throw new NullPointerException("part == null");
        }

        public MultipartBody build() {
            if (!this.parts.isEmpty()) {
                return new MultipartBody(this.boundary, this.type, this.parts);
            }
            throw new IllegalStateException("Multipart body must have at least one part.");
        }
    }

    public static final class Part {
        final RequestBody body;
        @Nullable
        final Headers headers;

        public static Part create(RequestBody body2) {
            return create(null, body2);
        }

        public static Part create(@Nullable Headers headers2, RequestBody body2) {
            if (body2 == null) {
                throw new NullPointerException("body == null");
            } else if (headers2 != null && headers2.get("Content-Type") != null) {
                throw new IllegalArgumentException("Unexpected header: Content-Type");
            } else if (headers2 == null || headers2.get("Content-Length") == null) {
                return new Part(headers2, body2);
            } else {
                throw new IllegalArgumentException("Unexpected header: Content-Length");
            }
        }

        public static Part createFormData(String name, String value) {
            return createFormData(name, null, RequestBody.create((MediaType) null, value));
        }

        public static Part createFormData(String name, @Nullable String filename, RequestBody body2) {
            if (name != null) {
                StringBuilder disposition = new StringBuilder("form-data; name=");
                MultipartBody.appendQuotedString(disposition, name);
                if (filename != null) {
                    disposition.append("; filename=");
                    MultipartBody.appendQuotedString(disposition, filename);
                }
                return create(new okhttp3.Headers.Builder().addUnsafeNonAscii("Content-Disposition", disposition.toString()).build(), body2);
            }
            throw new NullPointerException("name == null");
        }

        private Part(@Nullable Headers headers2, RequestBody body2) {
            this.headers = headers2;
            this.body = body2;
        }

        @Nullable
        public Headers headers() {
            return this.headers;
        }

        public RequestBody body() {
            return this.body;
        }
    }

    MultipartBody(ByteString boundary2, MediaType type, List<Part> parts2) {
        this.boundary = boundary2;
        this.originalType = type;
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        sb.append("; boundary=");
        sb.append(boundary2.utf8());
        this.contentType = MediaType.get(sb.toString());
        this.parts = Util.immutableList(parts2);
    }

    public MediaType type() {
        return this.originalType;
    }

    public String boundary() {
        return this.boundary.utf8();
    }

    public int size() {
        return this.parts.size();
    }

    public List<Part> parts() {
        return this.parts;
    }

    public Part part(int index) {
        return (Part) this.parts.get(index);
    }

    public MediaType contentType() {
        return this.contentType;
    }

    public long contentLength() throws IOException {
        long result = this.contentLength;
        if (result != -1) {
            return result;
        }
        long writeOrCountBytes = writeOrCountBytes(null, true);
        this.contentLength = writeOrCountBytes;
        return writeOrCountBytes;
    }

    public void writeTo(BufferedSink sink) throws IOException {
        writeOrCountBytes(sink, false);
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r4v0, types: [okio.BufferedSink] */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r4v2, types: [okio.Buffer] */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long writeOrCountBytes(@javax.annotation.Nullable okio.BufferedSink r17, boolean r18) throws java.io.IOException {
        /*
            r16 = this;
            r0 = r16
            r1 = 0
            r3 = 0
            if (r18 == 0) goto L_0x000e
            okio.Buffer r4 = new okio.Buffer
            r4.<init>()
            r3 = r4
            goto L_0x0010
        L_0x000e:
            r4 = r17
        L_0x0010:
            r5 = 0
            java.util.List<okhttp3.MultipartBody$Part> r6 = r0.parts
            int r6 = r6.size()
        L_0x0017:
            if (r5 >= r6) goto L_0x00a9
            java.util.List<okhttp3.MultipartBody$Part> r7 = r0.parts
            java.lang.Object r7 = r7.get(r5)
            okhttp3.MultipartBody$Part r7 = (okhttp3.MultipartBody.Part) r7
            okhttp3.Headers r8 = r7.headers
            okhttp3.RequestBody r9 = r7.body
            byte[] r10 = DASHDASH
            r4.write(r10)
            okio.ByteString r10 = r0.boundary
            r4.write(r10)
            byte[] r10 = CRLF
            r4.write(r10)
            if (r8 == 0) goto L_0x005b
            r10 = 0
            int r11 = r8.size()
        L_0x003b:
            if (r10 >= r11) goto L_0x005b
            java.lang.String r12 = r8.name(r10)
            okio.BufferedSink r12 = r4.writeUtf8(r12)
            byte[] r13 = COLONSPACE
            okio.BufferedSink r12 = r12.write(r13)
            java.lang.String r13 = r8.value(r10)
            okio.BufferedSink r12 = r12.writeUtf8(r13)
            byte[] r13 = CRLF
            r12.write(r13)
            int r10 = r10 + 1
            goto L_0x003b
        L_0x005b:
            okhttp3.MediaType r10 = r9.contentType()
            if (r10 == 0) goto L_0x0074
            java.lang.String r11 = "Content-Type: "
            okio.BufferedSink r11 = r4.writeUtf8(r11)
            java.lang.String r12 = r10.toString()
            okio.BufferedSink r11 = r11.writeUtf8(r12)
            byte[] r12 = CRLF
            r11.write(r12)
        L_0x0074:
            long r11 = r9.contentLength()
            r13 = -1
            int r15 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r15 == 0) goto L_0x008e
            java.lang.String r13 = "Content-Length: "
            okio.BufferedSink r13 = r4.writeUtf8(r13)
            okio.BufferedSink r13 = r13.writeDecimalLong(r11)
            byte[] r14 = CRLF
            r13.write(r14)
            goto L_0x0094
        L_0x008e:
            if (r18 == 0) goto L_0x0094
            r3.clear()
            return r13
        L_0x0094:
            byte[] r13 = CRLF
            r4.write(r13)
            if (r18 == 0) goto L_0x009d
            long r1 = r1 + r11
            goto L_0x00a0
        L_0x009d:
            r9.writeTo(r4)
        L_0x00a0:
            byte[] r13 = CRLF
            r4.write(r13)
            int r5 = r5 + 1
            goto L_0x0017
        L_0x00a9:
            byte[] r5 = DASHDASH
            r4.write(r5)
            okio.ByteString r5 = r0.boundary
            r4.write(r5)
            byte[] r5 = DASHDASH
            r4.write(r5)
            byte[] r5 = CRLF
            r4.write(r5)
            if (r18 == 0) goto L_0x00c7
            long r5 = r3.size()
            long r1 = r1 + r5
            r3.clear()
        L_0x00c7:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.MultipartBody.writeOrCountBytes(okio.BufferedSink, boolean):long");
    }

    static void appendQuotedString(StringBuilder target, String key) {
        target.append(Typography.quote);
        int len = key.length();
        for (int i = 0; i < len; i++) {
            char ch = key.charAt(i);
            if (ch == 10) {
                target.append("%0A");
            } else if (ch == 13) {
                target.append("%0D");
            } else if (ch != '\"') {
                target.append(ch);
            } else {
                target.append("%22");
            }
        }
        target.append(Typography.quote);
    }
}
