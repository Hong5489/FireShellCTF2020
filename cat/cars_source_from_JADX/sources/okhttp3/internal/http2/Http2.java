package okhttp3.internal.http2;

import java.io.IOException;
import okhttp3.internal.Util;
import okio.ByteString;

public final class Http2 {
    static final String[] BINARY = new String[256];
    static final ByteString CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    static final String[] FLAGS = new String[64];
    static final byte FLAG_ACK = 1;
    static final byte FLAG_COMPRESSED = 32;
    static final byte FLAG_END_HEADERS = 4;
    static final byte FLAG_END_PUSH_PROMISE = 4;
    static final byte FLAG_END_STREAM = 1;
    static final byte FLAG_NONE = 0;
    static final byte FLAG_PADDED = 8;
    static final byte FLAG_PRIORITY = 32;
    private static final String[] FRAME_NAMES = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
    static final int INITIAL_MAX_FRAME_SIZE = 16384;
    static final byte TYPE_CONTINUATION = 9;
    static final byte TYPE_DATA = 0;
    static final byte TYPE_GOAWAY = 7;
    static final byte TYPE_HEADERS = 1;
    static final byte TYPE_PING = 6;
    static final byte TYPE_PRIORITY = 2;
    static final byte TYPE_PUSH_PROMISE = 5;
    static final byte TYPE_RST_STREAM = 3;
    static final byte TYPE_SETTINGS = 4;
    static final byte TYPE_WINDOW_UPDATE = 8;

    static {
        String str;
        int[] frameFlags;
        int i = 0;
        while (true) {
            String[] strArr = BINARY;
            if (i >= strArr.length) {
                break;
            }
            strArr[i] = Util.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
            i++;
        }
        String[] strArr2 = FLAGS;
        strArr2[0] = "";
        strArr2[1] = "END_STREAM";
        int[] prefixFlags = {1};
        strArr2[8] = "PADDED";
        int length = prefixFlags.length;
        int i2 = 0;
        while (true) {
            str = "|PADDED";
            if (i2 >= length) {
                break;
            }
            int prefixFlag = prefixFlags[i2];
            String[] strArr3 = FLAGS;
            int i3 = prefixFlag | 8;
            StringBuilder sb = new StringBuilder();
            sb.append(FLAGS[prefixFlag]);
            sb.append(str);
            strArr3[i3] = sb.toString();
            i2++;
        }
        String[] strArr4 = FLAGS;
        strArr4[4] = "END_HEADERS";
        strArr4[32] = "PRIORITY";
        strArr4[36] = "END_HEADERS|PRIORITY";
        for (int frameFlag : new int[]{4, 32, 36}) {
            for (int prefixFlag2 : prefixFlags) {
                String[] strArr5 = FLAGS;
                int i4 = prefixFlag2 | frameFlag;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(FLAGS[prefixFlag2]);
                sb2.append('|');
                sb2.append(FLAGS[frameFlag]);
                strArr5[i4] = sb2.toString();
                String[] strArr6 = FLAGS;
                int i5 = prefixFlag2 | frameFlag | 8;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(FLAGS[prefixFlag2]);
                sb3.append('|');
                sb3.append(FLAGS[frameFlag]);
                sb3.append(str);
                strArr6[i5] = sb3.toString();
            }
        }
        int i6 = 0;
        while (true) {
            String[] strArr7 = FLAGS;
            if (i6 < strArr7.length) {
                if (strArr7[i6] == null) {
                    strArr7[i6] = BINARY[i6];
                }
                i6++;
            } else {
                return;
            }
        }
    }

    private Http2() {
    }

    static IllegalArgumentException illegalArgument(String message, Object... args) {
        throw new IllegalArgumentException(Util.format(message, args));
    }

    static IOException ioException(String message, Object... args) throws IOException {
        throw new IOException(Util.format(message, args));
    }

    static String frameLog(boolean inbound, int streamId, int length, byte type, byte flags) {
        String[] strArr = FRAME_NAMES;
        String formattedType = type < strArr.length ? strArr[type] : Util.format("0x%02x", Byte.valueOf(type));
        String formattedFlags = formatFlags(type, flags);
        Object[] objArr = new Object[5];
        objArr[0] = inbound ? "<<" : ">>";
        objArr[1] = Integer.valueOf(streamId);
        objArr[2] = Integer.valueOf(length);
        objArr[3] = formattedType;
        objArr[4] = formattedFlags;
        return Util.format("%s 0x%08x %5d %-13s %s", objArr);
    }

    static String formatFlags(byte type, byte flags) {
        if (flags == 0) {
            return "";
        }
        if (!(type == 2 || type == 3)) {
            if (type == 4 || type == 6) {
                return flags == 1 ? "ACK" : BINARY[flags];
            } else if (!(type == 7 || type == 8)) {
                String[] strArr = FLAGS;
                String result = flags < strArr.length ? strArr[flags] : BINARY[flags];
                if (type == 5 && (flags & 4) != 0) {
                    return result.replace("HEADERS", "PUSH_PROMISE");
                }
                if (type != 0 || (flags & 32) == 0) {
                    return result;
                }
                return result.replace("PRIORITY", "COMPRESSED");
            }
        }
        return BINARY[flags];
    }
}
