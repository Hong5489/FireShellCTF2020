package kotlin.collections;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.ArrayIteratorsKt;
import kotlin.jvm.internal.Lambda;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo6929d2 = {"<anonymous>", "Lkotlin/collections/BooleanIterator;", "invoke"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* compiled from: _Arrays.kt */
final class ArraysKt___ArraysKt$withIndex$8 extends Lambda implements Function0<BooleanIterator> {
    final /* synthetic */ boolean[] $this_withIndex;

    ArraysKt___ArraysKt$withIndex$8(boolean[] zArr) {
        this.$this_withIndex = zArr;
        super(0);
    }

    public final BooleanIterator invoke() {
        return ArrayIteratorsKt.iterator(this.$this_withIndex);
    }
}
