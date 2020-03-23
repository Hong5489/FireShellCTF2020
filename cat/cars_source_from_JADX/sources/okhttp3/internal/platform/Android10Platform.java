package okhttp3.internal.platform;

import android.net.ssl.SSLSockets;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import okhttp3.Protocol;

class Android10Platform extends AndroidPlatform {
    Android10Platform(Class<?> sslParametersClass) {
        super(sslParametersClass, null, null, null, null, null);
    }

    public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
        enableSessionTickets(sslSocket);
        SSLParameters sslParameters = sslSocket.getSSLParameters();
        sslParameters.setApplicationProtocols((String[]) Platform.alpnProtocolNames(protocols).toArray(new String[0]));
        sslSocket.setSSLParameters(sslParameters);
    }

    private void enableSessionTickets(SSLSocket sslSocket) {
        if (SSLSockets.isSupportedSocket(sslSocket)) {
            SSLSockets.setUseSessionTickets(sslSocket, true);
        }
    }

    @Nullable
    public String getSelectedProtocol(SSLSocket socket) {
        String alpnResult = socket.getApplicationProtocol();
        if (alpnResult == null || alpnResult.isEmpty()) {
            return null;
        }
        return alpnResult;
    }

    @Nullable
    public static Platform buildIfSupported() {
        try {
            if (getSdkInt() >= 29) {
                return new Android10Platform(Class.forName("com.android.org.conscrypt.SSLParametersImpl"));
            }
        } catch (ReflectiveOperationException e) {
        }
        return null;
    }
}
