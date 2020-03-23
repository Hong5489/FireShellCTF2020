package androidx.core.util;

import android.util.SparseIntArray;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u00008\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\n\u001a\u0015\u0010\b\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\b\u001a\u0015\u0010\t\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\n\u001a\u00020\u0001H\b\u001aE\u0010\u000b\u001a\u00020\f*\u00020\u000226\u0010\r\u001a2\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\f0\u000eH\b\u001a\u001d\u0010\u0011\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0001H\b\u001a#\u0010\u0013\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0014H\b\u001a\r\u0010\u0015\u001a\u00020\u0006*\u00020\u0002H\b\u001a\r\u0010\u0016\u001a\u00020\u0006*\u00020\u0002H\b\u001a\n\u0010\u0017\u001a\u00020\u0018*\u00020\u0002\u001a\u0015\u0010\u0019\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002H\u0002\u001a\u0012\u0010\u001b\u001a\u00020\f*\u00020\u00022\u0006\u0010\u001a\u001a\u00020\u0002\u001a\u001a\u0010\u001c\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0001\u001a\u001d\u0010\u001d\u001a\u00020\f*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0001H\n\u001a\n\u0010\u001e\u001a\u00020\u0018*\u00020\u0002\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u001f"}, mo6929d2 = {"size", "", "Landroid/util/SparseIntArray;", "getSize", "(Landroid/util/SparseIntArray;)I", "contains", "", "key", "containsKey", "containsValue", "value", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "getOrElse", "Lkotlin/Function0;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", "set", "valueIterator", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: SparseIntArray.kt */
public final class SparseIntArrayKt {
    public static final int getSize(SparseIntArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size();
    }

    public static final boolean contains(SparseIntArray $receiver, int key) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfKey(key) >= 0;
    }

    public static final void set(SparseIntArray $receiver, int key, int value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.put(key, value);
    }

    public static final SparseIntArray plus(SparseIntArray $receiver, SparseIntArray other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        SparseIntArray sparseIntArray = new SparseIntArray($receiver.size() + other.size());
        putAll(sparseIntArray, $receiver);
        putAll(sparseIntArray, other);
        return sparseIntArray;
    }

    public static final boolean containsKey(SparseIntArray $receiver, int key) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfKey(key) >= 0;
    }

    public static final boolean containsValue(SparseIntArray $receiver, int value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfValue(value) != -1;
    }

    public static final int getOrDefault(SparseIntArray $receiver, int key, int defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.get(key, defaultValue);
    }

    public static final int getOrElse(SparseIntArray $receiver, int key, Function0<Integer> defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        int it = $receiver.indexOfKey(key);
        return it != -1 ? $receiver.valueAt(it) : ((Number) defaultValue.invoke()).intValue();
    }

    public static final boolean isEmpty(SparseIntArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size() == 0;
    }

    public static final boolean isNotEmpty(SparseIntArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size() != 0;
    }

    public static final boolean remove(SparseIntArray $receiver, int key, int value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.indexOfKey(key);
        if (index == -1 || value != $receiver.valueAt(index)) {
            return false;
        }
        $receiver.removeAt(index);
        return true;
    }

    public static final void putAll(SparseIntArray $receiver, SparseIntArray other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        SparseIntArray $receiver$iv = other;
        int size = $receiver$iv.size();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            $receiver.put($receiver$iv.keyAt(index$iv), $receiver$iv.valueAt(index$iv));
        }
    }

    public static final void forEach(SparseIntArray $receiver, Function2<? super Integer, ? super Integer, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int size = $receiver.size();
        for (int index = 0; index < size; index++) {
            action.invoke(Integer.valueOf($receiver.keyAt(index)), Integer.valueOf($receiver.valueAt(index)));
        }
    }

    public static final IntIterator keyIterator(SparseIntArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new SparseIntArrayKt$keyIterator$1($receiver);
    }

    public static final IntIterator valueIterator(SparseIntArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new SparseIntArrayKt$valueIterator$1($receiver);
    }
}
