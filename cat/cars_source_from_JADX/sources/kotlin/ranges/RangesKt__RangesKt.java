package kotlin.ranges;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0000\u001a@\u0010\u0006\u001a\u00020\u0003\"\b\b\u0000\u0010\u0007*\u00020\b\"\u0018\b\u0001\u0010\t*\b\u0012\u0004\u0012\u0002H\u00070\n*\b\u0012\u0004\u0012\u0002H\u00070\u000b*\u0002H\t2\b\u0010\f\u001a\u0004\u0018\u0001H\u0007H\n¢\u0006\u0002\u0010\r\u001a0\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00070\u000b\"\u000e\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u000f*\u0002H\u00072\u0006\u0010\u0010\u001a\u0002H\u0007H\u0002¢\u0006\u0002\u0010\u0011\u001a\u001b\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012*\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0013H\u0002¨\u0006\u0014"}, mo6929d2 = {"checkStepIsPositive", "", "isPositive", "", "step", "", "contains", "T", "", "R", "", "Lkotlin/ranges/ClosedRange;", "element", "(Ljava/lang/Iterable;Ljava/lang/Object;)Z", "rangeTo", "", "that", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Lkotlin/ranges/ClosedRange;", "Lkotlin/ranges/ClosedFloatingPointRange;", "", "kotlin-stdlib"}, mo6930k = 5, mo6931mv = {1, 1, 15}, mo6933xi = 1, mo6934xs = "kotlin/ranges/RangesKt")
/* compiled from: Ranges.kt */
class RangesKt__RangesKt extends RangesKt__RangesJVMKt {
    public static final <T extends Comparable<? super T>> ClosedRange<T> rangeTo(T $this$rangeTo, T that) {
        Intrinsics.checkParameterIsNotNull($this$rangeTo, "$this$rangeTo");
        Intrinsics.checkParameterIsNotNull(that, "that");
        return new ComparableRange<>($this$rangeTo, that);
    }

    public static final ClosedFloatingPointRange<Double> rangeTo(double $this$rangeTo, double that) {
        return new ClosedDoubleRange<>($this$rangeTo, that);
    }

    private static final <T, R extends Iterable<? extends T> & ClosedRange<T>> boolean contains(R $this$contains, T element) {
        Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
        return element != null && ((ClosedRange) $this$contains).contains((Comparable) element);
    }

    public static final void checkStepIsPositive(boolean isPositive, Number step) {
        Intrinsics.checkParameterIsNotNull(step, "step");
        if (!isPositive) {
            StringBuilder sb = new StringBuilder();
            sb.append("Step must be positive, was: ");
            sb.append(step);
            sb.append('.');
            throw new IllegalArgumentException(sb.toString());
        }
    }
}