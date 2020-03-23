package kotlin.sequences;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\n¢\u0006\u0002\b\u0005"}, mo6929d2 = {"<anonymous>", "", "R", "it", "", "invoke"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* compiled from: _SequencesJvm.kt */
final class SequencesKt___SequencesJvmKt$filterIsInstance$1 extends Lambda implements Function1<Object, Boolean> {
    final /* synthetic */ Class $klass;

    SequencesKt___SequencesJvmKt$filterIsInstance$1(Class cls) {
        this.$klass = cls;
        super(1);
    }

    public final boolean invoke(Object it) {
        return this.$klass.isInstance(it);
    }
}
