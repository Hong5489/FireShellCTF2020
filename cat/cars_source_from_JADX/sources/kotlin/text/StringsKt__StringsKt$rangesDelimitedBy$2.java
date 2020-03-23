package kotlin.text;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\r\n\u0002\b\u0002\u0010\u0000\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0002H\n¢\u0006\u0002\b\u0005"}, mo6929d2 = {"<anonymous>", "Lkotlin/Pair;", "", "", "currentIndex", "invoke"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* compiled from: Strings.kt */
final class StringsKt__StringsKt$rangesDelimitedBy$2 extends Lambda implements Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>> {
    final /* synthetic */ char[] $delimiters;
    final /* synthetic */ boolean $ignoreCase;

    StringsKt__StringsKt$rangesDelimitedBy$2(char[] cArr, boolean z) {
        this.$delimiters = cArr;
        this.$ignoreCase = z;
        super(2);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke((CharSequence) obj, ((Number) obj2).intValue());
    }

    public final Pair<Integer, Integer> invoke(CharSequence $receiver, int currentIndex) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        int it = StringsKt.indexOfAny($receiver, this.$delimiters, currentIndex, this.$ignoreCase);
        if (it < 0) {
            return null;
        }
        return TuplesKt.m22to(Integer.valueOf(it), Integer.valueOf(1));
    }
}
