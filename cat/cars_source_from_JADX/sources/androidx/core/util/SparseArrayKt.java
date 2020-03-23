package androidx.core.util;

import android.util.SparseArray;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000@\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010(\n\u0000\u001a!\u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u0001H\n\u001a!\u0010\t\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u0001H\b\u001a&\u0010\n\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u000b\u001a\u0002H\u0002H\b¢\u0006\u0002\u0010\f\u001aQ\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000326\u0010\u000f\u001a2\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\b\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u000e0\u0010H\b\u001a.\u0010\u0013\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u0002H\u0002H\b¢\u0006\u0002\u0010\u0015\u001a4\u0010\u0016\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0017H\b¢\u0006\u0002\u0010\u0018\u001a\u0019\u0010\u0019\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\b\u001a\u0019\u0010\u001a\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\b\u001a\u0016\u0010\u001b\u001a\u00020\u001c\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a-\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0002\u001a$\u0010\u001f\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a+\u0010 \u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u0002H\u0002¢\u0006\u0002\u0010!\u001a.\u0010\"\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u0002H\u0002H\n¢\u0006\u0002\u0010#\u001a\u001c\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00020%\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\"\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006&"}, mo6929d2 = {"size", "", "T", "Landroid/util/SparseArray;", "getSize", "(Landroid/util/SparseArray;)I", "contains", "", "key", "containsKey", "containsValue", "value", "(Landroid/util/SparseArray;Ljava/lang/Object;)Z", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "(Landroid/util/SparseArray;ILjava/lang/Object;)Ljava/lang/Object;", "getOrElse", "Lkotlin/Function0;", "(Landroid/util/SparseArray;ILkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", "(Landroid/util/SparseArray;ILjava/lang/Object;)Z", "set", "(Landroid/util/SparseArray;ILjava/lang/Object;)V", "valueIterator", "", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: SparseArray.kt */
public final class SparseArrayKt {
    public static final <T> int getSize(SparseArray<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size();
    }

    public static final <T> boolean contains(SparseArray<T> $receiver, int key) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfKey(key) >= 0;
    }

    public static final <T> void set(SparseArray<T> $receiver, int key, T value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.put(key, value);
    }

    public static final <T> SparseArray<T> plus(SparseArray<T> $receiver, SparseArray<T> other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        SparseArray sparseArray = new SparseArray($receiver.size() + other.size());
        putAll(sparseArray, $receiver);
        putAll(sparseArray, other);
        return sparseArray;
    }

    public static final <T> boolean containsKey(SparseArray<T> $receiver, int key) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfKey(key) >= 0;
    }

    public static final <T> boolean containsValue(SparseArray<T> $receiver, T value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.indexOfValue(value) != -1;
    }

    public static final <T> T getOrDefault(SparseArray<T> $receiver, int key, T defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        T t = $receiver.get(key);
        return t != null ? t : defaultValue;
    }

    public static final <T> T getOrElse(SparseArray<T> $receiver, int key, Function0<? extends T> defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        T t = $receiver.get(key);
        return t != null ? t : defaultValue.invoke();
    }

    public static final <T> boolean isEmpty(SparseArray<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size() == 0;
    }

    public static final <T> boolean isNotEmpty(SparseArray<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.size() != 0;
    }

    public static final <T> boolean remove(SparseArray<T> $receiver, int key, T value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int index = $receiver.indexOfKey(key);
        if (index == -1 || !Intrinsics.areEqual((Object) value, $receiver.valueAt(index))) {
            return false;
        }
        $receiver.removeAt(index);
        return true;
    }

    public static final <T> void putAll(SparseArray<T> $receiver, SparseArray<T> other) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(other, "other");
        SparseArray $receiver$iv = other;
        int size = $receiver$iv.size();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            $receiver.put($receiver$iv.keyAt(index$iv), $receiver$iv.valueAt(index$iv));
        }
    }

    public static final <T> void forEach(SparseArray<T> $receiver, Function2<? super Integer, ? super T, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int size = $receiver.size();
        for (int index = 0; index < size; index++) {
            action.invoke(Integer.valueOf($receiver.keyAt(index)), $receiver.valueAt(index));
        }
    }

    public static final <T> IntIterator keyIterator(SparseArray<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new SparseArrayKt$keyIterator$1($receiver);
    }

    public static final <T> Iterator<T> valueIterator(SparseArray<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new SparseArrayKt$valueIterator$1<>($receiver);
    }
}
