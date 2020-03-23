package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo6929d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo6930k = 3, mo6931mv = {1, 1, 15})
@DebugMetadata(mo7536c = "kotlin.sequences.SequencesKt__SequencesKt$ifEmpty$1", mo7537f = "Sequences.kt", mo7538i = {0, 0, 1, 1}, mo7539l = {67, 69}, mo7540m = "invokeSuspend", mo7541n = {"$this$sequence", "iterator", "$this$sequence", "iterator"}, mo7542s = {"L$0", "L$1", "L$0", "L$1"})
/* compiled from: Sequences.kt */
final class SequencesKt__SequencesKt$ifEmpty$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function0 $defaultValue;
    final /* synthetic */ Sequence $this_ifEmpty;
    Object L$0;
    Object L$1;
    int label;

    /* renamed from: p$ */
    private SequenceScope f43p$;

    SequencesKt__SequencesKt$ifEmpty$1(Sequence sequence, Function0 function0, Continuation continuation) {
        this.$this_ifEmpty = sequence;
        this.$defaultValue = function0;
        super(2, continuation);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        SequencesKt__SequencesKt$ifEmpty$1 sequencesKt__SequencesKt$ifEmpty$1 = new SequencesKt__SequencesKt$ifEmpty$1(this.$this_ifEmpty, this.$defaultValue, continuation);
        SequenceScope sequenceScope = (SequenceScope) obj;
        sequencesKt__SequencesKt$ifEmpty$1.f43p$ = (SequenceScope) obj;
        return sequencesKt__SequencesKt$ifEmpty$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt__SequencesKt$ifEmpty$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            SequenceScope $this$sequence = this.f43p$;
            Iterator iterator = this.$this_ifEmpty.iterator();
            if (iterator.hasNext()) {
                this.L$0 = $this$sequence;
                this.L$1 = iterator;
                this.label = 1;
                if ($this$sequence.yieldAll(iterator, (Continuation<? super Unit>) this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                Iterator it = iterator;
            } else {
                Sequence sequence = (Sequence) this.$defaultValue.invoke();
                this.L$0 = $this$sequence;
                this.L$1 = iterator;
                this.label = 2;
                if ($this$sequence.yieldAll(sequence, (Continuation<? super Unit>) this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                Iterator it2 = iterator;
            }
        } else if (i == 1) {
            Iterator iterator2 = (Iterator) this.L$1;
            SequenceScope $this$sequence2 = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure($result);
        } else if (i == 2) {
            Iterator iterator3 = (Iterator) this.L$1;
            SequenceScope $this$sequence3 = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
