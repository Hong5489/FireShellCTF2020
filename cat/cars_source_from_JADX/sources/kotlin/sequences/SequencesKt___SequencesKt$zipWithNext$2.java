package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo6929d2 = {"<anonymous>", "", "T", "R", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo6930k = 3, mo6931mv = {1, 1, 15})
@DebugMetadata(mo7536c = "kotlin.sequences.SequencesKt___SequencesKt$zipWithNext$2", mo7537f = "_Sequences.kt", mo7538i = {0, 0, 0, 0}, mo7539l = {1702}, mo7540m = "invokeSuspend", mo7541n = {"$this$result", "iterator", "current", "next"}, mo7542s = {"L$0", "L$1", "L$2", "L$3"})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$zipWithNext$2 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Sequence $this_zipWithNext;
    final /* synthetic */ Function2 $transform;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* renamed from: p$ */
    private SequenceScope f44p$;

    SequencesKt___SequencesKt$zipWithNext$2(Sequence sequence, Function2 function2, Continuation continuation) {
        this.$this_zipWithNext = sequence;
        this.$transform = function2;
        super(2, continuation);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        SequencesKt___SequencesKt$zipWithNext$2 sequencesKt___SequencesKt$zipWithNext$2 = new SequencesKt___SequencesKt$zipWithNext$2(this.$this_zipWithNext, this.$transform, continuation);
        SequenceScope sequenceScope = (SequenceScope) obj;
        sequencesKt___SequencesKt$zipWithNext$2.f44p$ = (SequenceScope) obj;
        return sequencesKt___SequencesKt$zipWithNext$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt___SequencesKt$zipWithNext$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 1
            if (r1 == 0) goto L_0x002c
            if (r1 != r2) goto L_0x0024
            r1 = 0
            r3 = r1
            r4 = r1
            r5 = r1
            java.lang.Object r4 = r9.L$3
            java.lang.Object r5 = r9.L$2
            java.lang.Object r6 = r9.L$1
            r1 = r6
            java.util.Iterator r1 = (java.util.Iterator) r1
            java.lang.Object r6 = r9.L$0
            r3 = r6
            kotlin.sequences.SequenceScope r3 = (kotlin.sequences.SequenceScope) r3
            kotlin.ResultKt.throwOnFailure(r10)
            r6 = r0
            r0 = r10
            r10 = r9
            goto L_0x006f
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r10)
            kotlin.sequences.SequenceScope r1 = r9.f44p$
            kotlin.sequences.Sequence r3 = r9.$this_zipWithNext
            java.util.Iterator r3 = r3.iterator()
            boolean r4 = r3.hasNext()
            if (r4 != 0) goto L_0x0040
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0040:
            java.lang.Object r4 = r3.next()
            r5 = r4
            r4 = r0
            r0 = r10
            r10 = r9
            r8 = r3
            r3 = r1
            r1 = r8
        L_0x004b:
            boolean r6 = r1.hasNext()
            if (r6 == 0) goto L_0x0072
            java.lang.Object r6 = r1.next()
            kotlin.jvm.functions.Function2 r7 = r10.$transform
            java.lang.Object r7 = r7.invoke(r5, r6)
            r10.L$0 = r3
            r10.L$1 = r1
            r10.L$2 = r5
            r10.L$3 = r6
            r10.label = r2
            java.lang.Object r7 = r3.yield(r7, r10)
            if (r7 != r4) goto L_0x006c
            return r4
        L_0x006c:
            r8 = r6
            r6 = r4
            r4 = r8
        L_0x006f:
            r5 = r4
            r4 = r6
            goto L_0x004b
        L_0x0072:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$zipWithNext$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
