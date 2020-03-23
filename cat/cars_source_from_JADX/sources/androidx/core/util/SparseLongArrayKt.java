package androidx.core.util;

import android.util.SparseLongArray;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.collections.LongIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000D\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\n\u001a\u0015\u0010\b\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\b\u001a\u0015\u0010\t\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\b\u001aE\u0010\f\u001a\u00020\r*\u00020\u000226\u0010\u000e\u001a2\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\r0\u000fH\b\u001a\u001d\u0010\u0012\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u000bH\b\u001a#\u0010\u0014\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0015H\b\u001a\r\u0010\u0016\u001a\u00020\u0006*\u00020\u0002H\b\u001a\r\u0010\u0017\u001a\u00020\u0006*\u00020\u0002H\b\u001a\f\u0010\u0018\u001a\u00020\u0019*\u00020\u0002H\u0007\u001a\u0015\u0010\u001a\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002H\u0002\u001a\u0014\u0010\u001c\u001a\u00020\r*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002H\u0007\u001a\u001c\u0010\u001d\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\u001d\u0010\u001e\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\n\u001a\f\u0010\u001f\u001a\u00020 *\u00020\u0002H\u0007\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006!"}, mo6929d2 = {"size", "", "Landroid/util/SparseLongArray;", "getSize", "(Landroid/util/SparseLongArray;)I", "contains", "", "key", "containsKey", "containsValue", "value", "", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "getOrElse", "Lkotlin/Function0;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", "set", "valueIterator", "Lkotlin/collections/LongIterator;", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: SparseLongArray.kt */
public final class SparseLongArrayKt {
    public static final int getSize(SparseLongArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size();
    }

    public static final boolean contains(SparseLongArray $receiver, int key) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfKey(key) >= 0;
    }

    public static final void set(SparseLongArray $receiver, int key, long value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.put(key, value);
    }

    public static final SparseLongArray plus(SparseLongArray $receiver, SparseLongArray other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        SparseLongArray sparseLongArray = new SparseLongArray($receiver.size() + other.size());
        putAll(sparseLongArray, $receiver);
        putAll(sparseLongArray, other);
        return sparseLongArray;
    }

    public static final boolean containsKey(SparseLongArray $receiver, int key) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfKey(key) >= 0;
    }

    public static final boolean containsValue(SparseLongArray $receiver, long value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfValue(value) != -1;
    }

    public static final long getOrDefault(SparseLongArray $receiver, int key, long defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.get(key, defaultValue);
    }

    public static final long getOrElse(SparseLongArray $receiver, int key, Function0<Long> defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        int it = $receiver.indexOfKey(key);
        return it != -1 ? $receiver.valueAt(it) : ((Number) defaultValue.invoke()).longValue();
    }

    public static final boolean isEmpty(SparseLongArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size() == 0;
    }

    public static final boolean isNotEmpty(SparseLongArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size() != 0;
    }

    public static final boolean remove(SparseLongArray $receiver, int key, long value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.indexOfKey(key);
        if (index == -1 || value != $receiver.valueAt(index)) {
            return false;
        }
        $receiver.removeAt(index);
        return true;
    }

    public static final void putAll(SparseLongArray $receiver, SparseLongArray other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        SparseLongArray $receiver$iv = other;
        int size = $receiver$iv.size();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            $receiver.put($receiver$iv.keyAt(index$iv), $receiver$iv.valueAt(index$iv));
        }
    }

    public static final void forEach(SparseLongArray $receiver, Function2<? super Integer, ? super Long, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int size = $receiver.size();
        for (int index = 0; index < size; index++) {
            action.invoke(Integer.valueOf($receiver.keyAt(index)), Long.valueOf($receiver.valueAt(index)));
        }
    }

    public static final IntIterator keyIterator(SparseLongArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new SparseLongArrayKt$keyIterator$1($receiver);
    }

    public static final LongIterator valueIterator(SparseLongArray $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new SparseLongArrayKt$valueIterator$1($receiver);
    }
}
