package androidx.core.util;

import android.util.SparseBooleanArray;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.BooleanIterator;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000>\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\n\u001a\u0015\u0010\b\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\b\u001a\u0015\u0010\t\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\n\u001a\u00020\u0006H\b\u001aE\u0010\u000b\u001a\u00020\f*\u00020\u000226\u0010\r\u001a2\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\f0\u000eH\b\u001a\u001d\u0010\u0011\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0006H\b\u001a#\u0010\u0013\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\u0014H\b\u001a\r\u0010\u0015\u001a\u00020\u0006*\u00020\u0002H\b\u001a\r\u0010\u0016\u001a\u00020\u0006*\u00020\u0002H\b\u001a\n\u0010\u0017\u001a\u00020\u0018*\u00020\u0002\u001a\u0015\u0010\u0019\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002H\u0002\u001a\u0012\u0010\u001b\u001a\u00020\f*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u001a\u0010\u001c\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0006\u001a\u001d\u0010\u001d\u001a\u00020\f*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0006H\n\u001a\n\u0010\u001e\u001a\u00020\u001f*\u00020\u0002\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006 "}, mo6929d2 = {"size", "", "Landroid/util/SparseBooleanArray;", "getSize", "(Landroid/util/SparseBooleanArray;)I", "contains", "", "key", "containsKey", "containsValue", "value", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "getOrElse", "Lkotlin/Function0;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", "set", "valueIterator", "Lkotlin/collections/BooleanIterator;", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: SparseBooleanArray.kt */
public final class SparseBooleanArrayKt {
    public static final int getSize(SparseBooleanArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size();
    }

    public static final boolean contains(SparseBooleanArray $receiver, int key) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfKey(key) >= 0;
    }

    public static final void set(SparseBooleanArray $receiver, int key, boolean value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.put(key, value);
    }

    public static final SparseBooleanArray plus(SparseBooleanArray $receiver, SparseBooleanArray other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray($receiver.size() + other.size());
        putAll(sparseBooleanArray, $receiver);
        putAll(sparseBooleanArray, other);
        return sparseBooleanArray;
    }

    public static final boolean containsKey(SparseBooleanArray $receiver, int key) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfKey(key) >= 0;
    }

    public static final boolean containsValue(SparseBooleanArray $receiver, boolean value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfValue(value) != -1;
    }

    public static final boolean getOrDefault(SparseBooleanArray $receiver, int key, boolean defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.get(key, defaultValue);
    }

    public static final boolean getOrElse(SparseBooleanArray $receiver, int key, Function0<Boolean> defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        int it = $receiver.indexOfKey(key);
        return it != -1 ? $receiver.valueAt(it) : ((Boolean) defaultValue.invoke()).booleanValue();
    }

    public static final boolean isEmpty(SparseBooleanArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size() == 0;
    }

    public static final boolean isNotEmpty(SparseBooleanArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size() != 0;
    }

    public static final boolean remove(SparseBooleanArray $receiver, int key, boolean value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.indexOfKey(key);
        if (index == -1 || value != $receiver.valueAt(index)) {
            return false;
        }
        $receiver.delete(key);
        return true;
    }

    public static final void putAll(SparseBooleanArray $receiver, SparseBooleanArray other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        SparseBooleanArray $receiver$iv = other;
        int size = $receiver$iv.size();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            $receiver.put($receiver$iv.keyAt(index$iv), $receiver$iv.valueAt(index$iv));
        }
    }

    public static final void forEach(SparseBooleanArray $receiver, Function2<? super Integer, ? super Boolean, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int size = $receiver.size();
        for (int index = 0; index < size; index++) {
            action.invoke(Integer.valueOf($receiver.keyAt(index)), Boolean.valueOf($receiver.valueAt(index)));
        }
    }

    public static final IntIterator keyIterator(SparseBooleanArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new SparseBooleanArrayKt$keyIterator$1($receiver);
    }

    public static final BooleanIterator valueIterator(SparseBooleanArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new SparseBooleanArrayKt$valueIterator$1($receiver);
    }
}
