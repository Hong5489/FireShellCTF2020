package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.Protocol;
import okhttp3.Response;

public final class StatusLine {
    public static final int HTTP_CONTINUE = 100;
    public static final int HTTP_PERM_REDIRECT = 308;
    public static final int HTTP_TEMP_REDIRECT = 307;
    public final int code;
    public final String message;
    public final Protocol protocol;

    public StatusLine(Protocol protocol2, int code2, String message2) {
        this.protocol = protocol2;
        this.code = code2;
        this.message = message2;
    }

    public static StatusLine get(Response response) {
        return new StatusLine(response.protocol(), response.code(), response.message());
    }

    public static StatusLine parse(String statusLine) throws IOException {
        Protocol protocol2;
        int codeStart;
        String str = "Unexpected status line: ";
        if (statusLine.startsWith("HTTP/1.")) {
            if (statusLine.length() < 9 || statusLine.charAt(8) != ' ') {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(statusLine);
                throw new ProtocolException(sb.toString());
            }
            int httpMinorVersion = statusLine.charAt(7) - '0';
            codeStart = 9;
            if (httpMinorVersion == 0) {
                protocol2 = Protocol.HTTP_1_0;
            } else if (httpMinorVersion == 1) {
                protocol2 = Protocol.HTTP_1_1;
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append(statusLine);
                throw new ProtocolException(sb2.toString());
            }
        } else if (statusLine.startsWith("ICY ")) {
            protocol2 = Protocol.HTTP_1_0;
            codeStart = 4;
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(statusLine);
            throw new ProtocolException(sb3.toString());
        }
        if (statusLine.length() >= codeStart + 3) {
            try {
                int code2 = Integer.parseInt(statusLine.substring(codeStart, codeStart + 3));
                String message2 = "";
                if (statusLine.length() > codeStart + 3) {
                    if (statusLine.charAt(codeStart + 3) == ' ') {
                        message2 = statusLine.substring(codeStart + 4);
                    } else {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str);
                        sb4.append(statusLine);
                        throw new ProtocolException(sb4.toString());
                    }
                }
                return new StatusLine(protocol2, code2, message2);
            } catch (NumberFormatException e) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(str);
                sb5.append(statusLine);
                throw new ProtocolException(sb5.toString());
            }
        } else {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(str);
            sb6.append(statusLine);
            throw new ProtocolException(sb6.toString());
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1");
        result.append(' ');
        result.append(this.code);
        if (this.message != null) {
            result.append(' ');
            result.append(this.message);
        }
        return result.toString();
    }
}
