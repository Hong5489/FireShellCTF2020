package kotlin.collections;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007¢\u0006\u0002\b\u0004\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0002\b\b\u001a\u001d\u0010\t\u001a\u00020\u0006*\u0006\u0012\u0002\b\u00030\u00012\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0002\b\n¨\u0006\u000b"}, mo6929d2 = {"asReversed", "", "T", "", "asReversedMutable", "reverseElementIndex", "", "index", "reverseElementIndex$CollectionsKt__ReversedViewsKt", "reversePositionIndex", "reversePositionIndex$CollectionsKt__ReversedViewsKt", "kotlin-stdlib"}, mo6930k = 5, mo6931mv = {1, 1, 15}, mo6933xi = 1, mo6934xs = "kotlin/collections/CollectionsKt")
/* compiled from: ReversedViews.kt */
class CollectionsKt__ReversedViewsKt extends CollectionsKt__MutableCollectionsKt {
    /* access modifiers changed from: private */
    public static final int reverseElementIndex$CollectionsKt__ReversedViewsKt(List<?> $this$reverseElementIndex, int index) {
        int lastIndex = CollectionsKt.getLastIndex($this$reverseElementIndex);
        if (index >= 0 && lastIndex >= index) {
            return CollectionsKt.getLastIndex($this$reverseElementIndex) - index;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Element index ");
        sb.append(index);
        sb.append(" must be in range [");
        sb.append(new IntRange(0, CollectionsKt.getLastIndex($this$reverseElementIndex)));
        sb.append("].");
        throw new IndexOutOfBoundsException(sb.toString());
    }

    /* access modifiers changed from: private */
    public static final int reversePositionIndex$CollectionsKt__ReversedViewsKt(List<?> $this$reversePositionIndex, int index) {
        int size = $this$reversePositionIndex.size();
        if (index >= 0 && size >= index) {
            return $this$reversePositionIndex.size() - index;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Position index ");
        sb.append(index);
        sb.append(" must be in range [");
        sb.append(new IntRange(0, $this$reversePositionIndex.size()));
        sb.append("].");
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public static final <T> List<T> asReversed(List<? extends T> $this$asReversed) {
        Intrinsics.checkParameterIsNotNull($this$asReversed, "$this$asReversed");
        return new ReversedListReadOnly<>($this$asReversed);
    }

    public static final <T> List<T> asReversedMutable(List<T> $this$asReversed) {
        Intrinsics.checkParameterIsNotNull($this$asReversed, "$this$asReversed");
        return new ReversedList<>($this$asReversed);
    }
}
