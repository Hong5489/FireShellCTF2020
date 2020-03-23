package okhttp3;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import okio.ByteString;

public final class Credentials {
    private Credentials() {
    }

    public static String basic(String username, String password) {
        return basic(username, password, StandardCharsets.ISO_8859_1);
    }

    public static String basic(String username, String password, Charset charset) {
        StringBuilder sb = new StringBuilder();
        sb.append(username);
        sb.append(":");
        sb.append(password);
        String encoded = ByteString.encodeString(sb.toString(), charset).base64();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Basic ");
        sb2.append(encoded);
        return sb2.toString();
    }
}
