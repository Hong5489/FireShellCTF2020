package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, mo6929d2 = {"<anonymous>", "", "T", "it", "", "invoke"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$elementAt$1 extends Lambda implements Function1 {
    final /* synthetic */ int $index;

    SequencesKt___SequencesKt$elementAt$1(int i) {
        this.$index = i;
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return invoke(((Number) obj).intValue());
    }

    public final Void invoke(int it) {
        StringBuilder sb = new StringBuilder();
        sb.append("Sequence doesn't contain element at index ");
        sb.append(this.$index);
        sb.append('.');
        throw new IndexOutOfBoundsException(sb.toString());
    }
}
