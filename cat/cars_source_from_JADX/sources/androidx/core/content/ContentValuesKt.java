package androidx.core.content;

import android.content.ContentValues;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a;\u0010\u0000\u001a\u00020\u00012.\u0010\u0002\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00040\u0003\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004¢\u0006\u0002\u0010\u0007¨\u0006\b"}, mo6929d2 = {"contentValuesOf", "Landroid/content/ContentValues;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/content/ContentValues;", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: ContentValues.kt */
public final class ContentValuesKt {
    public static final ContentValues contentValuesOf(Pair<String, ? extends Object>... pairs) {
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        ContentValues contentValues = new ContentValues(pairs.length);
        ContentValues $receiver = contentValues;
        for (Pair<String, ? extends Object> pair : pairs) {
            String key = (String) pair.component1();
            Object value = pair.component2();
            if (value == null) {
                $receiver.putNull(key);
            } else if (value instanceof String) {
                $receiver.put(key, (String) value);
            } else if (value instanceof Integer) {
                $receiver.put(key, (Integer) value);
            } else if (value instanceof Long) {
                $receiver.put(key, (Long) value);
            } else if (value instanceof Boolean) {
                $receiver.put(key, (Boolean) value);
            } else if (value instanceof Float) {
                $receiver.put(key, (Float) value);
            } else if (value instanceof Double) {
                $receiver.put(key, (Double) value);
            } else if (value instanceof byte[]) {
                $receiver.put(key, (byte[]) value);
            } else if (value instanceof Byte) {
                $receiver.put(key, (Byte) value);
            } else if (value instanceof Short) {
                $receiver.put(key, (Short) value);
            } else {
                String valueType = value.getClass().getCanonicalName();
                StringBuilder sb = new StringBuilder();
                sb.append("Illegal value type ");
                sb.append(valueType);
                sb.append(" for key \"");
                sb.append(key);
                sb.append(Typography.quote);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        return contentValues;
    }
}
