package okhttp3.internal.tls;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import okhttp3.internal.Util;

public final class OkHostnameVerifier implements HostnameVerifier {
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;
    public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();

    private OkHostnameVerifier() {
    }

    public boolean verify(String host, SSLSession session) {
        try {
            return verify(host, (X509Certificate) session.getPeerCertificates()[0]);
        } catch (SSLException e) {
            return false;
        }
    }

    public boolean verify(String host, X509Certificate certificate) {
        if (Util.verifyAsIpAddress(host)) {
            return verifyIpAddress(host, certificate);
        }
        return verifyHostname(host, certificate);
    }

    private boolean verifyIpAddress(String ipAddress, X509Certificate certificate) {
        List<String> altNames = getSubjectAltNames(certificate, 7);
        int size = altNames.size();
        for (int i = 0; i < size; i++) {
            if (ipAddress.equalsIgnoreCase((String) altNames.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean verifyHostname(String hostname, X509Certificate certificate) {
        String hostname2 = hostname.toLowerCase(Locale.US);
        for (String altName : getSubjectAltNames(certificate, 2)) {
            if (verifyHostname(hostname2, altName)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> allSubjectAltNames(X509Certificate certificate) {
        List<String> altIpaNames = getSubjectAltNames(certificate, 7);
        List<String> altDnsNames = getSubjectAltNames(certificate, 2);
        List<String> result = new ArrayList<>(altIpaNames.size() + altDnsNames.size());
        result.addAll(altIpaNames);
        result.addAll(altDnsNames);
        return result;
    }

    private static List<String> getSubjectAltNames(X509Certificate certificate, int type) {
        List<String> result = new ArrayList<>();
        try {
            Collection<?> subjectAltNames = certificate.getSubjectAlternativeNames();
            if (subjectAltNames == null) {
                return Collections.emptyList();
            }
            Iterator it = subjectAltNames.iterator();
            while (it.hasNext()) {
                List<?> entry = (List) it.next();
                if (entry != null) {
                    if (entry.size() >= 2) {
                        Integer altNameType = (Integer) entry.get(0);
                        if (altNameType != null) {
                            if (altNameType.intValue() == type) {
                                String altName = (String) entry.get(1);
                                if (altName != null) {
                                    result.add(altName);
                                }
                            }
                        }
                    }
                }
            }
            return result;
        } catch (CertificateParsingException e) {
            return Collections.emptyList();
        }
    }

    public boolean verifyHostname(String hostname, String pattern) {
        if (!(hostname == null || hostname.length() == 0)) {
            String str = ".";
            if (!hostname.startsWith(str)) {
                String str2 = "..";
                if (hostname.endsWith(str2) || pattern == null || pattern.length() == 0 || pattern.startsWith(str) || pattern.endsWith(str2)) {
                    return false;
                }
                if (!hostname.endsWith(str)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(hostname);
                    sb.append('.');
                    hostname = sb.toString();
                }
                if (!pattern.endsWith(str)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(pattern);
                    sb2.append('.');
                    pattern = sb2.toString();
                }
                String pattern2 = pattern.toLowerCase(Locale.US);
                if (!pattern2.contains("*")) {
                    return hostname.equals(pattern2);
                }
                String str3 = "*.";
                if (!pattern2.startsWith(str3) || pattern2.indexOf(42, 1) != -1 || hostname.length() < pattern2.length() || str3.equals(pattern2)) {
                    return false;
                }
                String suffix = pattern2.substring(1);
                if (!hostname.endsWith(suffix)) {
                    return false;
                }
                int suffixStartIndexInHostname = hostname.length() - suffix.length();
                if (suffixStartIndexInHostname <= 0 || hostname.lastIndexOf(46, suffixStartIndexInHostname - 1) == -1) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
