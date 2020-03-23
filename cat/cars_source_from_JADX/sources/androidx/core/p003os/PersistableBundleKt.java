package androidx.core.p003os;

import android.os.Build.VERSION;
import android.os.PersistableBundle;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a=\u0010\u0000\u001a\u00020\u00012.\u0010\u0002\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00040\u0003\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004H\u0007¢\u0006\u0002\u0010\u0007¨\u0006\b"}, mo6929d2 = {"persistableBundleOf", "Landroid/os/PersistableBundle;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/os/PersistableBundle;", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* renamed from: androidx.core.os.PersistableBundleKt */
/* compiled from: PersistableBundle.kt */
public final class PersistableBundleKt {
    public static final PersistableBundle persistableBundleOf(Pair<String, ? extends Object>... pairs) {
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        PersistableBundle persistableBundle = new PersistableBundle(pairs.length);
        PersistableBundle $receiver = persistableBundle;
        for (Pair<String, ? extends Object> pair : pairs) {
            String key = (String) pair.component1();
            Object value = pair.component2();
            if (value == null) {
                $receiver.putString(key, null);
            } else if (value instanceof Boolean) {
                if (VERSION.SDK_INT >= 22) {
                    $receiver.putBoolean(key, ((Boolean) value).booleanValue());
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Illegal value type boolean for key \"");
                    sb.append(key);
                    sb.append(Typography.quote);
                    throw new IllegalArgumentException(sb.toString());
                }
            } else if (value instanceof Double) {
                $receiver.putDouble(key, ((Number) value).doubleValue());
            } else if (value instanceof Integer) {
                $receiver.putInt(key, ((Number) value).intValue());
            } else if (value instanceof Long) {
                $receiver.putLong(key, ((Number) value).longValue());
            } else if (value instanceof String) {
                $receiver.putString(key, (String) value);
            } else if (value instanceof boolean[]) {
                if (VERSION.SDK_INT >= 22) {
                    $receiver.putBooleanArray(key, (boolean[]) value);
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Illegal value type boolean[] for key \"");
                    sb2.append(key);
                    sb2.append(Typography.quote);
                    throw new IllegalArgumentException(sb2.toString());
                }
            } else if (value instanceof double[]) {
                $receiver.putDoubleArray(key, (double[]) value);
            } else if (value instanceof int[]) {
                $receiver.putIntArray(key, (int[]) value);
            } else if (value instanceof long[]) {
                $receiver.putLongArray(key, (long[]) value);
            } else {
                String str = " for key \"";
                if (value instanceof Object[]) {
                    Class componentType = value.getClass().getComponentType();
                    if (!String.class.isAssignableFrom(componentType)) {
                        Intrinsics.checkExpressionValueIsNotNull(componentType, "componentType");
                        String valueType = componentType.getCanonicalName();
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Illegal value array type ");
                        sb3.append(valueType);
                        sb3.append(str);
                        sb3.append(key);
                        sb3.append(Typography.quote);
                        throw new IllegalArgumentException(sb3.toString());
                    } else if (value != null) {
                        $receiver.putStringArray(key, (String[]) value);
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
                    }
                } else {
                    String valueType2 = value.getClass().getCanonicalName();
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Illegal value type ");
                    sb4.append(valueType2);
                    sb4.append(str);
                    sb4.append(key);
                    sb4.append(Typography.quote);
                    throw new IllegalArgumentException(sb4.toString());
                }
            }
        }
        return persistableBundle;
    }
}
