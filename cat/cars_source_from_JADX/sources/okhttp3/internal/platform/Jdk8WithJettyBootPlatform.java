package okhttp3.internal.platform;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocket;
import okhttp3.Protocol;
import okhttp3.internal.Util;

class Jdk8WithJettyBootPlatform extends Platform {
    private final Class<?> clientProviderClass;
    private final Method getMethod;
    private final Method putMethod;
    private final Method removeMethod;
    private final Class<?> serverProviderClass;

    private static class AlpnProvider implements InvocationHandler {
        private final List<String> protocols;
        String selected;
        boolean unsupported;

        AlpnProvider(List<String> protocols2) {
            this.protocols = protocols2;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            Class<?> returnType = method.getReturnType();
            if (args == null) {
                args = Util.EMPTY_STRING_ARRAY;
            }
            if (methodName.equals("supports") && Boolean.TYPE == returnType) {
                return Boolean.valueOf(true);
            }
            if (methodName.equals("unsupported") && Void.TYPE == returnType) {
                this.unsupported = true;
                return null;
            } else if (methodName.equals("protocols") && args.length == 0) {
                return this.protocols;
            } else {
                if ((methodName.equals("selectProtocol") || methodName.equals("select")) && String.class == returnType && args.length == 1 && (args[0] instanceof List)) {
                    List<?> peerProtocols = (List) args[0];
                    int size = peerProtocols.size();
                    for (int i = 0; i < size; i++) {
                        String protocol = (String) peerProtocols.get(i);
                        if (this.protocols.contains(protocol)) {
                            this.selected = protocol;
                            return protocol;
                        }
                    }
                    String str = (String) this.protocols.get(0);
                    this.selected = str;
                    return str;
                } else if ((!methodName.equals("protocolSelected") && !methodName.equals("selected")) || args.length != 1) {
                    return method.invoke(this, args);
                } else {
                    this.selected = (String) args[0];
                    return null;
                }
            }
        }
    }

    Jdk8WithJettyBootPlatform(Method putMethod2, Method getMethod2, Method removeMethod2, Class<?> clientProviderClass2, Class<?> serverProviderClass2) {
        this.putMethod = putMethod2;
        this.getMethod = getMethod2;
        this.removeMethod = removeMethod2;
        this.clientProviderClass = clientProviderClass2;
        this.serverProviderClass = serverProviderClass2;
    }

    public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
        List<String> names = alpnProtocolNames(protocols);
        try {
            Object alpnProvider = Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new AlpnProvider(names));
            this.putMethod.invoke(null, new Object[]{sslSocket, alpnProvider});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("failed to set ALPN", e);
        }
    }

    public void afterHandshake(SSLSocket sslSocket) {
        try {
            this.removeMethod.invoke(null, new Object[]{sslSocket});
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("failed to remove ALPN", e);
        }
    }

    @Nullable
    public String getSelectedProtocol(SSLSocket socket) {
        try {
            String str = null;
            AlpnProvider provider = (AlpnProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, new Object[]{socket}));
            if (provider.unsupported || provider.selected != null) {
                if (!provider.unsupported) {
                    str = provider.selected;
                }
                return str;
            }
            Platform.get().log(4, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", null);
            return null;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AssertionError("failed to get ALPN selected protocol", e);
        }
    }

    public static Platform buildIfSupported() {
        String alpnClassName = "org.eclipse.jetty.alpn.ALPN";
        try {
            Class<?> alpnClass = Class.forName(alpnClassName, true, null);
            StringBuilder sb = new StringBuilder();
            sb.append(alpnClassName);
            sb.append("$Provider");
            Class<?> providerClass = Class.forName(sb.toString(), true, null);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(alpnClassName);
            sb2.append("$ClientProvider");
            Class<?> clientProviderClass2 = Class.forName(sb2.toString(), true, null);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(alpnClassName);
            sb3.append("$ServerProvider");
            Class<?> serverProviderClass2 = Class.forName(sb3.toString(), true, null);
            Jdk8WithJettyBootPlatform jdk8WithJettyBootPlatform = new Jdk8WithJettyBootPlatform(alpnClass.getMethod("put", new Class[]{SSLSocket.class, providerClass}), alpnClass.getMethod("get", new Class[]{SSLSocket.class}), alpnClass.getMethod("remove", new Class[]{SSLSocket.class}), clientProviderClass2, serverProviderClass2);
            return jdk8WithJettyBootPlatform;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            return null;
        }
    }
}
