package kotlin.collections.unsigned;

import kotlin.Metadata;
import kotlin.ULongArray;
import kotlin.collections.ULongIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo6929d2 = {"<anonymous>", "Lkotlin/collections/ULongIterator;", "invoke"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* compiled from: _UArrays.kt */
final class UArraysKt___UArraysKt$withIndex$2 extends Lambda implements Function0<ULongIterator> {
    final /* synthetic */ long[] $this_withIndex;

    UArraysKt___UArraysKt$withIndex$2(long[] jArr) {
        this.$this_withIndex = jArr;
        super(0);
    }

    public final ULongIterator invoke() {
        return ULongArray.m256iteratorimpl(this.$this_withIndex);
    }
}
