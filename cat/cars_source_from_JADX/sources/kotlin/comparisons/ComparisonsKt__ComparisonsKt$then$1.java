package kotlin.comparisons;

import java.util.Comparator;
import kotlin.Metadata;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, mo6929d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* compiled from: Comparisons.kt */
final class ComparisonsKt__ComparisonsKt$then$1<T> implements Comparator<T> {
    final /* synthetic */ Comparator $comparator;
    final /* synthetic */ Comparator $this_then;

    ComparisonsKt__ComparisonsKt$then$1(Comparator comparator, Comparator comparator2) {
        this.$this_then = comparator;
        this.$comparator = comparator2;
    }

    public final int compare(T a, T b) {
        int previousCompare = this.$this_then.compare(a, b);
        return previousCompare != 0 ? previousCompare : this.$comparator.compare(a, b);
    }
}
