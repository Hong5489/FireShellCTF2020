package kotlin.sequences;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00020\u0002B\u0007\b\u0000¢\u0006\u0002\u0010\u0003J\u0019\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u0000H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u001f\u0010\b\u001a\u00020\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000bJ\u001f\u0010\b\u001a\u00020\u00052\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\rH¦@ø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u001f\u0010\b\u001a\u00020\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010H@ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, mo6929d2 = {"Lkotlin/sequences/SequenceScope;", "T", "", "()V", "yield", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "yieldAll", "elements", "", "(Ljava/lang/Iterable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "iterator", "", "(Ljava/util/Iterator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sequence", "Lkotlin/sequences/Sequence;", "(Lkotlin/sequences/Sequence;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: SequenceBuilder.kt */
public abstract class SequenceScope<T> {
    public abstract Object yield(T t, Continuation<? super Unit> continuation);

    public abstract Object yieldAll(Iterator<? extends T> it, Continuation<? super Unit> continuation);

    public final Object yieldAll(Iterable<? extends T> elements, Continuation<? super Unit> $completion) {
        if (!(elements instanceof Collection) || !((Collection) elements).isEmpty()) {
            return yieldAll(elements.iterator(), $completion);
        }
        return Unit.INSTANCE;
    }

    public final Object yieldAll(Sequence<? extends T> sequence, Continuation<? super Unit> $completion) {
        return yieldAll(sequence.iterator(), $completion);
    }
}