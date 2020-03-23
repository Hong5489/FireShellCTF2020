package okhttp3;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.internal.tls.CertificateChainCleaner;
import okio.ByteString;

public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();
    @Nullable
    private final CertificateChainCleaner certificateChainCleaner;
    private final Set<Pin> pins;

    public static final class Builder {
        private final List<Pin> pins = new ArrayList();

        public Builder add(String pattern, String... pins2) {
            if (pattern != null) {
                for (String pin : pins2) {
                    this.pins.add(new Pin(pattern, pin));
                }
                return this;
            }
            throw new NullPointerException("pattern == null");
        }

        public CertificatePinner build() {
            return new CertificatePinner(new LinkedHashSet(this.pins), null);
        }
    }

    static final class Pin {
        private static final String WILDCARD = "*.";
        final String canonicalHostname;
        final ByteString hash;
        final String hashAlgorithm;
        final String pattern;

        Pin(String pattern2, String pin) {
            String str;
            this.pattern = pattern2;
            String str2 = WILDCARD;
            String str3 = "http://";
            if (pattern2.startsWith(str2)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str3);
                sb.append(pattern2.substring(str2.length()));
                str = HttpUrl.get(sb.toString()).host();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str3);
                sb2.append(pattern2);
                str = HttpUrl.get(sb2.toString()).host();
            }
            this.canonicalHostname = str;
            String str4 = "sha1/";
            if (pin.startsWith(str4)) {
                this.hashAlgorithm = str4;
                this.hash = ByteString.decodeBase64(pin.substring(str4.length()));
            } else {
                String str5 = "sha256/";
                if (pin.startsWith(str5)) {
                    this.hashAlgorithm = str5;
                    this.hash = ByteString.decodeBase64(pin.substring(str5.length()));
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("pins must start with 'sha256/' or 'sha1/': ");
                    sb3.append(pin);
                    throw new IllegalArgumentException(sb3.toString());
                }
            }
            if (this.hash == null) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("pins must be base64: ");
                sb4.append(pin);
                throw new IllegalArgumentException(sb4.toString());
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:5:0x002e, code lost:
            if (r11.regionMatches(false, r6, r7, 0, r7.length()) != false) goto L_0x0032;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean matches(java.lang.String r11) {
            /*
                r10 = this;
                java.lang.String r0 = r10.pattern
                java.lang.String r1 = "*."
                boolean r0 = r0.startsWith(r1)
                if (r0 == 0) goto L_0x0033
                r0 = 46
                int r0 = r11.indexOf(r0)
                int r1 = r11.length()
                int r1 = r1 - r0
                r2 = 1
                int r1 = r1 - r2
                java.lang.String r3 = r10.canonicalHostname
                int r3 = r3.length()
                if (r1 != r3) goto L_0x0031
                r5 = 0
                int r6 = r0 + 1
                java.lang.String r7 = r10.canonicalHostname
                r8 = 0
                int r9 = r7.length()
                r4 = r11
                boolean r1 = r4.regionMatches(r5, r6, r7, r8, r9)
                if (r1 == 0) goto L_0x0031
                goto L_0x0032
            L_0x0031:
                r2 = 0
            L_0x0032:
                return r2
            L_0x0033:
                java.lang.String r0 = r10.canonicalHostname
                boolean r0 = r11.equals(r0)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.CertificatePinner.Pin.matches(java.lang.String):boolean");
        }

        public boolean equals(Object other) {
            return (other instanceof Pin) && this.pattern.equals(((Pin) other).pattern) && this.hashAlgorithm.equals(((Pin) other).hashAlgorithm) && this.hash.equals(((Pin) other).hash);
        }

        public int hashCode() {
            return (((((17 * 31) + this.pattern.hashCode()) * 31) + this.hashAlgorithm.hashCode()) * 31) + this.hash.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.hashAlgorithm);
            sb.append(this.hash.base64());
            return sb.toString();
        }
    }

    CertificatePinner(Set<Pin> pins2, @Nullable CertificateChainCleaner certificateChainCleaner2) {
        this.pins = pins2;
        this.certificateChainCleaner = certificateChainCleaner2;
    }

    public boolean equals(@Nullable Object other) {
        boolean z = true;
        if (other == this) {
            return true;
        }
        if (!(other instanceof CertificatePinner) || !Objects.equals(this.certificateChainCleaner, ((CertificatePinner) other).certificateChainCleaner) || !this.pins.equals(((CertificatePinner) other).pins)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (Objects.hashCode(this.certificateChainCleaner) * 31) + this.pins.hashCode();
    }

    public void check(String hostname, List<Certificate> peerCertificates) throws SSLPeerUnverifiedException {
        String str;
        List<Pin> pins2 = findMatchingPins(hostname);
        if (!pins2.isEmpty()) {
            CertificateChainCleaner certificateChainCleaner2 = this.certificateChainCleaner;
            if (certificateChainCleaner2 != null) {
                peerCertificates = certificateChainCleaner2.clean(peerCertificates, hostname);
            }
            int certsSize = peerCertificates.size();
            for (int c = 0; c < certsSize; c++) {
                X509Certificate x509Certificate = (X509Certificate) peerCertificates.get(c);
                ByteString sha1 = null;
                ByteString sha256 = null;
                int pinsSize = pins2.size();
                for (int p = 0; p < pinsSize; p++) {
                    Pin pin = (Pin) pins2.get(p);
                    if (pin.hashAlgorithm.equals("sha256/")) {
                        if (sha256 == null) {
                            sha256 = sha256(x509Certificate);
                        }
                        if (pin.hash.equals(sha256)) {
                            return;
                        }
                    } else if (pin.hashAlgorithm.equals("sha1/")) {
                        if (sha1 == null) {
                            sha1 = sha1(x509Certificate);
                        }
                        if (pin.hash.equals(sha1)) {
                            return;
                        }
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("unsupported hashAlgorithm: ");
                        sb.append(pin.hashAlgorithm);
                        throw new AssertionError(sb.toString());
                    }
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Certificate pinning failure!");
            StringBuilder message = sb2.append("\n  Peer certificate chain:");
            int c2 = 0;
            int certsSize2 = peerCertificates.size();
            while (true) {
                str = "\n    ";
                if (c2 >= certsSize2) {
                    break;
                }
                X509Certificate x509Certificate2 = (X509Certificate) peerCertificates.get(c2);
                message.append(str);
                message.append(pin(x509Certificate2));
                message.append(": ");
                message.append(x509Certificate2.getSubjectDN().getName());
                c2++;
            }
            message.append("\n  Pinned certificates for ");
            message.append(hostname);
            message.append(":");
            int pinsSize2 = pins2.size();
            for (int p2 = 0; p2 < pinsSize2; p2++) {
                Pin pin2 = (Pin) pins2.get(p2);
                message.append(str);
                message.append(pin2);
            }
            throw new SSLPeerUnverifiedException(message.toString());
        }
    }

    public void check(String hostname, Certificate... peerCertificates) throws SSLPeerUnverifiedException {
        check(hostname, Arrays.asList(peerCertificates));
    }

    /* access modifiers changed from: 0000 */
    public List<Pin> findMatchingPins(String hostname) {
        List<Pin> result = Collections.emptyList();
        for (Pin pin : this.pins) {
            if (pin.matches(hostname)) {
                if (result.isEmpty()) {
                    result = new ArrayList<>();
                }
                result.add(pin);
            }
        }
        return result;
    }

    /* access modifiers changed from: 0000 */
    public CertificatePinner withCertificateChainCleaner(@Nullable CertificateChainCleaner certificateChainCleaner2) {
        if (Objects.equals(this.certificateChainCleaner, certificateChainCleaner2)) {
            return this;
        }
        return new CertificatePinner(this.pins, certificateChainCleaner2);
    }

    public static String pin(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            StringBuilder sb = new StringBuilder();
            sb.append("sha256/");
            sb.append(sha256((X509Certificate) certificate).base64());
            return sb.toString();
        }
        throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    }

    static ByteString sha1(X509Certificate x509Certificate) {
        return ByteString.m38of(x509Certificate.getPublicKey().getEncoded()).sha1();
    }

    static ByteString sha256(X509Certificate x509Certificate) {
        return ByteString.m38of(x509Certificate.getPublicKey().getEncoded()).sha256();
    }
}
