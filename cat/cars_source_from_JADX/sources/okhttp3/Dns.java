package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public interface Dns {
    public static final Dns SYSTEM = $$Lambda$Dns$mTkNcZf2K4euny3_jks6Cac6Az0.INSTANCE;

    List<InetAddress> lookup(String str) throws UnknownHostException;

    static /* synthetic */ List lambda$static$0(String hostname) throws UnknownHostException {
        if (hostname != null) {
            try {
                return Arrays.asList(InetAddress.getAllByName(hostname));
            } catch (NullPointerException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Broken system behaviour for dns lookup of ");
                sb.append(hostname);
                UnknownHostException unknownHostException = new UnknownHostException(sb.toString());
                unknownHostException.initCause(e);
                throw unknownHostException;
            }
        } else {
            throw new UnknownHostException("hostname == null");
        }
    }
}
