package kotlin.text;

import kotlin.Metadata;
import kotlin.collections.CharIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo6929d2 = {"<anonymous>", "Lkotlin/collections/CharIterator;", "invoke"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* compiled from: _Strings.kt */
final class StringsKt___StringsKt$withIndex$1 extends Lambda implements Function0<CharIterator> {
    final /* synthetic */ CharSequence $this_withIndex;

    StringsKt___StringsKt$withIndex$1(CharSequence charSequence) {
        this.$this_withIndex = charSequence;
        super(0);
    }

    public final CharIterator invoke() {
        return StringsKt.iterator(this.$this_withIndex);
    }
}
