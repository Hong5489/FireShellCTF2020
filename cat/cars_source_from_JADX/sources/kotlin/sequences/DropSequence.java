package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH\u0002J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo6929d2 = {"Lkotlin/sequences/DropSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/sequences/DropTakeSequence;", "sequence", "count", "", "(Lkotlin/sequences/Sequence;I)V", "drop", "n", "iterator", "", "take", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: Sequences.kt */
public final class DropSequence<T> implements Sequence<T>, DropTakeSequence<T> {
    /* access modifiers changed from: private */
    public final int count;
    /* access modifiers changed from: private */
    public final Sequence<T> sequence;

    public DropSequence(Sequence<? extends T> sequence2, int count2) {
        Intrinsics.checkParameterIsNotNull(sequence2, "sequence");
        this.sequence = sequence2;
        this.count = count2;
        if (!(this.count >= 0)) {
            StringBuilder sb = new StringBuilder();
            sb.append("count must be non-negative, but was ");
            sb.append(this.count);
            sb.append('.');
            throw new IllegalArgumentException(sb.toString().toString());
        }
    }

    public Sequence<T> drop(int n) {
        DropSequence dropSequence;
        int n1 = this.count + n;
        if (n1 < 0) {
            dropSequence = new DropSequence(this, n);
        } else {
            dropSequence = new DropSequence(this.sequence, n1);
        }
        return dropSequence;
    }

    public Sequence<T> take(int n) {
        int i = this.count;
        int n1 = i + n;
        return n1 < 0 ? new TakeSequence<>(this, n) : new SubSequence<>(this.sequence, i, n1);
    }

    public Iterator<T> iterator() {
        return new DropSequence$iterator$1<>(this);
    }
}
