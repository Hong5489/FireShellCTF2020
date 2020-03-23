package okio;

public final class Utf8 {
    private Utf8() {
    }

    public static long size(String string) {
        return size(string, 0, string.length());
    }

    public static long size(String string, int beginIndex, int endIndex) {
        if (string == null) {
            throw new IllegalArgumentException("string == null");
        } else if (beginIndex < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("beginIndex < 0: ");
            sb.append(beginIndex);
            throw new IllegalArgumentException(sb.toString());
        } else if (endIndex < beginIndex) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("endIndex < beginIndex: ");
            sb2.append(endIndex);
            sb2.append(" < ");
            sb2.append(beginIndex);
            throw new IllegalArgumentException(sb2.toString());
        } else if (endIndex <= string.length()) {
            long result = 0;
            int i = beginIndex;
            while (i < endIndex) {
                int c = string.charAt(i);
                if (c < 128) {
                    result++;
                    i++;
                } else if (c < 2048) {
                    result += 2;
                    i++;
                } else if (c < 55296 || c > 57343) {
                    result += 3;
                    i++;
                } else {
                    int low = i + 1 < endIndex ? string.charAt(i + 1) : 0;
                    if (c > 56319 || low < 56320 || low > 57343) {
                        result++;
                        i++;
                    } else {
                        result += 4;
                        i += 2;
                    }
                }
            }
            return result;
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("endIndex > string.length: ");
            sb3.append(endIndex);
            sb3.append(" > ");
            sb3.append(string.length());
            throw new IllegalArgumentException(sb3.toString());
        }
    }
}
