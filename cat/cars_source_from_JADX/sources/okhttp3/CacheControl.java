package okhttp3;

import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public final class CacheControl {
    public static final CacheControl FORCE_CACHE = new Builder().onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
    public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();
    @Nullable
    String headerValue;
    private final boolean immutable;
    private final boolean isPrivate;
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean noTransform;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;

    public static final class Builder {
        boolean immutable;
        int maxAgeSeconds = -1;
        int maxStaleSeconds = -1;
        int minFreshSeconds = -1;
        boolean noCache;
        boolean noStore;
        boolean noTransform;
        boolean onlyIfCached;

        public Builder noCache() {
            this.noCache = true;
            return this;
        }

        public Builder noStore() {
            this.noStore = true;
            return this;
        }

        public Builder maxAge(int maxAge, TimeUnit timeUnit) {
            int i;
            if (maxAge >= 0) {
                long maxAgeSecondsLong = timeUnit.toSeconds((long) maxAge);
                if (maxAgeSecondsLong > 2147483647L) {
                    i = Integer.MAX_VALUE;
                } else {
                    i = (int) maxAgeSecondsLong;
                }
                this.maxAgeSeconds = i;
                return this;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("maxAge < 0: ");
            sb.append(maxAge);
            throw new IllegalArgumentException(sb.toString());
        }

        public Builder maxStale(int maxStale, TimeUnit timeUnit) {
            int i;
            if (maxStale >= 0) {
                long maxStaleSecondsLong = timeUnit.toSeconds((long) maxStale);
                if (maxStaleSecondsLong > 2147483647L) {
                    i = Integer.MAX_VALUE;
                } else {
                    i = (int) maxStaleSecondsLong;
                }
                this.maxStaleSeconds = i;
                return this;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("maxStale < 0: ");
            sb.append(maxStale);
            throw new IllegalArgumentException(sb.toString());
        }

        public Builder minFresh(int minFresh, TimeUnit timeUnit) {
            int i;
            if (minFresh >= 0) {
                long minFreshSecondsLong = timeUnit.toSeconds((long) minFresh);
                if (minFreshSecondsLong > 2147483647L) {
                    i = Integer.MAX_VALUE;
                } else {
                    i = (int) minFreshSecondsLong;
                }
                this.minFreshSeconds = i;
                return this;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("minFresh < 0: ");
            sb.append(minFresh);
            throw new IllegalArgumentException(sb.toString());
        }

        public Builder onlyIfCached() {
            this.onlyIfCached = true;
            return this;
        }

        public Builder noTransform() {
            this.noTransform = true;
            return this;
        }

        public Builder immutable() {
            this.immutable = true;
            return this;
        }

        public CacheControl build() {
            return new CacheControl(this);
        }
    }

    private CacheControl(boolean noCache2, boolean noStore2, int maxAgeSeconds2, int sMaxAgeSeconds2, boolean isPrivate2, boolean isPublic2, boolean mustRevalidate2, int maxStaleSeconds2, int minFreshSeconds2, boolean onlyIfCached2, boolean noTransform2, boolean immutable2, @Nullable String headerValue2) {
        this.noCache = noCache2;
        this.noStore = noStore2;
        this.maxAgeSeconds = maxAgeSeconds2;
        this.sMaxAgeSeconds = sMaxAgeSeconds2;
        this.isPrivate = isPrivate2;
        this.isPublic = isPublic2;
        this.mustRevalidate = mustRevalidate2;
        this.maxStaleSeconds = maxStaleSeconds2;
        this.minFreshSeconds = minFreshSeconds2;
        this.onlyIfCached = onlyIfCached2;
        this.noTransform = noTransform2;
        this.immutable = immutable2;
        this.headerValue = headerValue2;
    }

    CacheControl(Builder builder) {
        this.noCache = builder.noCache;
        this.noStore = builder.noStore;
        this.maxAgeSeconds = builder.maxAgeSeconds;
        this.sMaxAgeSeconds = -1;
        this.isPrivate = false;
        this.isPublic = false;
        this.mustRevalidate = false;
        this.maxStaleSeconds = builder.maxStaleSeconds;
        this.minFreshSeconds = builder.minFreshSeconds;
        this.onlyIfCached = builder.onlyIfCached;
        this.noTransform = builder.noTransform;
        this.immutable = builder.immutable;
    }

    public boolean noCache() {
        return this.noCache;
    }

    public boolean noStore() {
        return this.noStore;
    }

    public int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public int sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    public int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    public boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    public boolean noTransform() {
        return this.noTransform;
    }

    public boolean immutable() {
        return this.immutable;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00c4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static okhttp3.CacheControl parse(okhttp3.Headers r32) {
        /*
            r0 = r32
            r1 = 0
            r2 = 0
            r3 = -1
            r4 = -1
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = -1
            r9 = -1
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 1
            r14 = 0
            r15 = 0
            r16 = r1
            int r1 = r32.size()
            r30 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r16
        L_0x0020:
            if (r15 >= r1) goto L_0x01a0
            r16 = r1
            java.lang.String r1 = r0.name(r15)
            r31 = r12
            java.lang.String r12 = r0.value(r15)
            java.lang.String r0 = "Cache-Control"
            boolean r0 = r1.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x003c
            if (r14 == 0) goto L_0x003a
            r13 = 0
            goto L_0x0045
        L_0x003a:
            r14 = r12
            goto L_0x0045
        L_0x003c:
            java.lang.String r0 = "Pragma"
            boolean r0 = r1.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x0194
            r13 = 0
        L_0x0045:
            r0 = 0
        L_0x0046:
            r17 = r1
            int r1 = r12.length()
            if (r0 >= r1) goto L_0x018d
            r1 = r0
            r18 = r2
            java.lang.String r2 = "=,;"
            int r0 = okhttp3.internal.http.HttpHeaders.skipUntil(r12, r0, r2)
            java.lang.String r2 = r12.substring(r1, r0)
            java.lang.String r2 = r2.trim()
            r19 = r1
            int r1 = r12.length()
            if (r0 == r1) goto L_0x00af
            char r1 = r12.charAt(r0)
            r20 = r3
            r3 = 44
            if (r1 == r3) goto L_0x00b1
            char r1 = r12.charAt(r0)
            r3 = 59
            if (r1 != r3) goto L_0x007a
            goto L_0x00b1
        L_0x007a:
            int r0 = r0 + 1
            int r0 = okhttp3.internal.http.HttpHeaders.skipWhitespace(r12, r0)
            int r1 = r12.length()
            if (r0 >= r1) goto L_0x009f
            char r1 = r12.charAt(r0)
            r3 = 34
            if (r1 != r3) goto L_0x009f
            int r0 = r0 + 1
            r1 = r0
            java.lang.String r3 = "\""
            int r0 = okhttp3.internal.http.HttpHeaders.skipUntil(r12, r0, r3)
            java.lang.String r3 = r12.substring(r1, r0)
            int r0 = r0 + 1
            goto L_0x00b4
        L_0x009f:
            r1 = r0
            java.lang.String r3 = ",;"
            int r0 = okhttp3.internal.http.HttpHeaders.skipUntil(r12, r0, r3)
            java.lang.String r3 = r12.substring(r1, r0)
            java.lang.String r3 = r3.trim()
            goto L_0x00b4
        L_0x00af:
            r20 = r3
        L_0x00b1:
            int r0 = r0 + 1
            r3 = 0
        L_0x00b4:
            java.lang.String r1 = "no-cache"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x00c4
            r1 = 1
            r21 = r0
            r2 = r1
            r3 = r20
            goto L_0x0187
        L_0x00c4:
            java.lang.String r1 = "no-store"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x00d4
            r1 = 1
            r21 = r0
            r3 = r1
            r2 = r18
            goto L_0x0187
        L_0x00d4:
            java.lang.String r1 = "max-age"
            boolean r1 = r1.equalsIgnoreCase(r2)
            r21 = r0
            r0 = -1
            if (r1 == 0) goto L_0x00ea
            int r0 = okhttp3.internal.http.HttpHeaders.parseSeconds(r3, r0)
            r4 = r0
            r2 = r18
            r3 = r20
            goto L_0x0187
        L_0x00ea:
            java.lang.String r1 = "s-maxage"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x00fd
            int r0 = okhttp3.internal.http.HttpHeaders.parseSeconds(r3, r0)
            r5 = r0
            r2 = r18
            r3 = r20
            goto L_0x0187
        L_0x00fd:
            java.lang.String r1 = "private"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x010d
            r0 = 1
            r6 = r0
            r2 = r18
            r3 = r20
            goto L_0x0187
        L_0x010d:
            java.lang.String r1 = "public"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x011d
            r0 = 1
            r7 = r0
            r2 = r18
            r3 = r20
            goto L_0x0187
        L_0x011d:
            java.lang.String r1 = "must-revalidate"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x012e
            r0 = 1
            r30 = r0
            r2 = r18
            r3 = r20
            goto L_0x0187
        L_0x012e:
            java.lang.String r1 = "max-stale"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x0143
            r0 = 2147483647(0x7fffffff, float:NaN)
            int r0 = okhttp3.internal.http.HttpHeaders.parseSeconds(r3, r0)
            r8 = r0
            r2 = r18
            r3 = r20
            goto L_0x0187
        L_0x0143:
            java.lang.String r1 = "min-fresh"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x0155
            int r0 = okhttp3.internal.http.HttpHeaders.parseSeconds(r3, r0)
            r9 = r0
            r2 = r18
            r3 = r20
            goto L_0x0187
        L_0x0155:
            java.lang.String r0 = "only-if-cached"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0164
            r0 = 1
            r10 = r0
            r2 = r18
            r3 = r20
            goto L_0x0187
        L_0x0164:
            java.lang.String r0 = "no-transform"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0173
            r0 = 1
            r11 = r0
            r2 = r18
            r3 = r20
            goto L_0x0187
        L_0x0173:
            java.lang.String r0 = "immutable"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0183
            r0 = 1
            r31 = r0
            r2 = r18
            r3 = r20
            goto L_0x0187
        L_0x0183:
            r2 = r18
            r3 = r20
        L_0x0187:
            r1 = r17
            r0 = r21
            goto L_0x0046
        L_0x018d:
            r18 = r2
            r20 = r3
            r12 = r31
            goto L_0x0198
        L_0x0194:
            r17 = r1
            r12 = r31
        L_0x0198:
            int r15 = r15 + 1
            r0 = r32
            r1 = r16
            goto L_0x0020
        L_0x01a0:
            r16 = r1
            r31 = r12
            if (r13 != 0) goto L_0x01a7
            r14 = 0
        L_0x01a7:
            okhttp3.CacheControl r0 = new okhttp3.CacheControl
            r16 = r0
            r17 = r2
            r18 = r3
            r19 = r4
            r20 = r5
            r21 = r6
            r22 = r7
            r23 = r30
            r24 = r8
            r25 = r9
            r26 = r10
            r27 = r11
            r28 = r31
            r29 = r14
            r16.<init>(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.CacheControl.parse(okhttp3.Headers):okhttp3.CacheControl");
    }

    public String toString() {
        String result = this.headerValue;
        if (result != null) {
            return result;
        }
        String headerValue2 = headerValue();
        this.headerValue = headerValue2;
        return headerValue2;
    }

    private String headerValue() {
        StringBuilder result = new StringBuilder();
        if (this.noCache) {
            result.append("no-cache, ");
        }
        if (this.noStore) {
            result.append("no-store, ");
        }
        String str = ", ";
        if (this.maxAgeSeconds != -1) {
            result.append("max-age=");
            result.append(this.maxAgeSeconds);
            result.append(str);
        }
        if (this.sMaxAgeSeconds != -1) {
            result.append("s-maxage=");
            result.append(this.sMaxAgeSeconds);
            result.append(str);
        }
        if (this.isPrivate) {
            result.append("private, ");
        }
        if (this.isPublic) {
            result.append("public, ");
        }
        if (this.mustRevalidate) {
            result.append("must-revalidate, ");
        }
        if (this.maxStaleSeconds != -1) {
            result.append("max-stale=");
            result.append(this.maxStaleSeconds);
            result.append(str);
        }
        if (this.minFreshSeconds != -1) {
            result.append("min-fresh=");
            result.append(this.minFreshSeconds);
            result.append(str);
        }
        if (this.onlyIfCached) {
            result.append("only-if-cached, ");
        }
        if (this.noTransform) {
            result.append("no-transform, ");
        }
        if (this.immutable) {
            result.append("immutable, ");
        }
        if (result.length() == 0) {
            return "";
        }
        result.delete(result.length() - 2, result.length());
        return result.toString();
    }
}
