package okhttp3;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public final class Challenge {
    private final Map<String, String> authParams;
    private final String scheme;

    public Challenge(String scheme2, Map<String, String> authParams2) {
        if (scheme2 == null) {
            throw new NullPointerException("scheme == null");
        } else if (authParams2 != null) {
            this.scheme = scheme2;
            Map<String, String> newAuthParams = new LinkedHashMap<>();
            for (Entry<String, String> authParam : authParams2.entrySet()) {
                newAuthParams.put(authParam.getKey() == null ? null : ((String) authParam.getKey()).toLowerCase(Locale.US), (String) authParam.getValue());
            }
            this.authParams = Collections.unmodifiableMap(newAuthParams);
        } else {
            throw new NullPointerException("authParams == null");
        }
    }

    public Challenge(String scheme2, String realm) {
        if (scheme2 == null) {
            throw new NullPointerException("scheme == null");
        } else if (realm != null) {
            this.scheme = scheme2;
            this.authParams = Collections.singletonMap("realm", realm);
        } else {
            throw new NullPointerException("realm == null");
        }
    }

    public Challenge withCharset(Charset charset) {
        if (charset != null) {
            Map<String, String> authParams2 = new LinkedHashMap<>(this.authParams);
            authParams2.put("charset", charset.name());
            return new Challenge(this.scheme, authParams2);
        }
        throw new NullPointerException("charset == null");
    }

    public String scheme() {
        return this.scheme;
    }

    public Map<String, String> authParams() {
        return this.authParams;
    }

    public String realm() {
        return (String) this.authParams.get("realm");
    }

    public Charset charset() {
        String charset = (String) this.authParams.get("charset");
        if (charset != null) {
            try {
                return Charset.forName(charset);
            } catch (Exception e) {
            }
        }
        return StandardCharsets.ISO_8859_1;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof Challenge) && ((Challenge) other).scheme.equals(this.scheme) && ((Challenge) other).authParams.equals(this.authParams);
    }

    public int hashCode() {
        return (((29 * 31) + this.scheme.hashCode()) * 31) + this.authParams.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.scheme);
        sb.append(" authParams=");
        sb.append(this.authParams);
        return sb.toString();
    }
}
