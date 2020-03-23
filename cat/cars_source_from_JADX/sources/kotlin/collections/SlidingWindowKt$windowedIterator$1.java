package kotlin.collections;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceScope;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo6929d2 = {"<anonymous>", "", "T", "Lkotlin/sequences/SequenceScope;", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo6930k = 3, mo6931mv = {1, 1, 15})
@DebugMetadata(mo7536c = "kotlin.collections.SlidingWindowKt$windowedIterator$1", mo7537f = "SlidingWindow.kt", mo7538i = {0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 4, 4, 4}, mo7539l = {33, 39, 46, 52, 55}, mo7540m = "invokeSuspend", mo7541n = {"$this$iterator", "gap", "buffer", "skip", "e", "$this$iterator", "gap", "buffer", "skip", "$this$iterator", "gap", "buffer", "e", "$this$iterator", "gap", "buffer", "$this$iterator", "gap", "buffer"}, mo7542s = {"L$0", "I$0", "L$1", "I$1", "L$2", "L$0", "I$0", "L$1", "I$1", "L$0", "I$0", "L$1", "L$2", "L$0", "I$0", "L$1", "L$0", "I$0", "L$1"})
/* compiled from: SlidingWindow.kt */
final class SlidingWindowKt$windowedIterator$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super List<? extends T>>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Iterator $iterator;
    final /* synthetic */ boolean $partialWindows;
    final /* synthetic */ boolean $reuseBuffer;
    final /* synthetic */ int $size;
    final /* synthetic */ int $step;
    int I$0;
    int I$1;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* renamed from: p$ */
    private SequenceScope f34p$;

    SlidingWindowKt$windowedIterator$1(int i, int i2, Iterator it, boolean z, boolean z2, Continuation continuation) {
        this.$step = i;
        this.$size = i2;
        this.$iterator = it;
        this.$reuseBuffer = z;
        this.$partialWindows = z2;
        super(2, continuation);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        SlidingWindowKt$windowedIterator$1 slidingWindowKt$windowedIterator$1 = new SlidingWindowKt$windowedIterator$1(this.$step, this.$size, this.$iterator, this.$reuseBuffer, this.$partialWindows, continuation);
        SequenceScope sequenceScope = (SequenceScope) obj;
        slidingWindowKt$windowedIterator$1.f34p$ = (SequenceScope) obj;
        return slidingWindowKt$windowedIterator$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SlidingWindowKt$windowedIterator$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Regions count limit reached
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
        */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00fc  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x012c A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0140  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0180  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x018b  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01d0  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0106 A[SYNTHETIC] */
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            r14 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r14.label
            r2 = 5
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            if (r1 == 0) goto L_0x00aa
            r7 = 0
            r8 = 0
            if (r1 == r6) goto L_0x0086
            if (r1 == r5) goto L_0x006d
            if (r1 == r4) goto L_0x0052
            if (r1 == r3) goto L_0x0037
            if (r1 != r2) goto L_0x002f
            r0 = r8
            r1 = r8
            r2 = r7
            java.lang.Object r3 = r14.L$1
            r1 = r3
            kotlin.collections.RingBuffer r1 = (kotlin.collections.RingBuffer) r1
            int r2 = r14.I$0
            java.lang.Object r3 = r14.L$0
            r0 = r3
            kotlin.sequences.SequenceScope r0 = (kotlin.sequences.SequenceScope) r0
            kotlin.ResultKt.throwOnFailure(r15)
            r7 = r15
            r15 = r14
            goto L_0x01cd
        L_0x002f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0037:
            r1 = r8
            r4 = r8
            r5 = r7
            java.lang.Object r7 = r14.L$1
            r4 = r7
            kotlin.collections.RingBuffer r4 = (kotlin.collections.RingBuffer) r4
            int r5 = r14.I$0
            java.lang.Object r7 = r14.L$0
            r1 = r7
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r15)
            r7 = r5
            r5 = r15
            r15 = r14
            r13 = r4
            r4 = r0
            r0 = r1
            r1 = r13
            goto L_0x01ac
        L_0x0052:
            r1 = r8
            r5 = r8
            java.lang.Object r9 = r14.L$3
            java.util.Iterator r9 = (java.util.Iterator) r9
            java.lang.Object r5 = r14.L$2
            java.lang.Object r10 = r14.L$1
            r8 = r10
            kotlin.collections.RingBuffer r8 = (kotlin.collections.RingBuffer) r8
            int r7 = r14.I$0
            java.lang.Object r10 = r14.L$0
            r1 = r10
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r15)
            r10 = r15
            r15 = r14
            goto L_0x0175
        L_0x006d:
            r0 = r8
            r1 = r7
            r2 = r8
            r3 = r7
            int r1 = r14.I$1
            java.lang.Object r4 = r14.L$1
            r2 = r4
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            int r3 = r14.I$0
            java.lang.Object r4 = r14.L$0
            r0 = r4
            kotlin.sequences.SequenceScope r0 = (kotlin.sequences.SequenceScope) r0
            kotlin.ResultKt.throwOnFailure(r15)
            r7 = r15
            r15 = r14
            goto L_0x012d
        L_0x0086:
            r1 = r8
            r2 = r8
            r3 = r7
            r4 = r8
            java.lang.Object r8 = r14.L$3
            java.util.Iterator r8 = (java.util.Iterator) r8
            java.lang.Object r2 = r14.L$2
            int r3 = r14.I$1
            java.lang.Object r9 = r14.L$1
            r4 = r9
            java.util.ArrayList r4 = (java.util.ArrayList) r4
            int r7 = r14.I$0
            java.lang.Object r9 = r14.L$0
            r1 = r9
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r15)
            r9 = r2
            r2 = r4
            r4 = r0
            r0 = r1
            r1 = r3
            r3 = r7
            r7 = r15
            r15 = r14
            goto L_0x00f4
        L_0x00aa:
            kotlin.ResultKt.throwOnFailure(r15)
            kotlin.sequences.SequenceScope r1 = r14.f34p$
            int r7 = r14.$step
            int r8 = r14.$size
            int r7 = r7 - r8
            if (r7 < 0) goto L_0x012f
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>(r8)
            r3 = 0
            java.util.Iterator r4 = r14.$iterator
            r8 = r4
            r4 = r0
            r0 = r1
            r1 = r3
            r3 = r7
            r7 = r15
            r15 = r14
        L_0x00c5:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x0106
            java.lang.Object r9 = r8.next()
            if (r1 <= 0) goto L_0x00d4
            int r1 = r1 + -1
            goto L_0x0105
        L_0x00d4:
            r2.add(r9)
            int r10 = r2.size()
            int r11 = r15.$size
            if (r10 != r11) goto L_0x0105
            r15.L$0 = r0
            r15.I$0 = r3
            r15.L$1 = r2
            r15.I$1 = r1
            r15.L$2 = r9
            r15.L$3 = r8
            r15.label = r6
            java.lang.Object r10 = r0.yield(r2, r15)
            if (r10 != r4) goto L_0x00f4
            return r4
        L_0x00f4:
            boolean r10 = r15.$reuseBuffer
            if (r10 == 0) goto L_0x00fc
            r2.clear()
            goto L_0x0104
        L_0x00fc:
            java.util.ArrayList r10 = new java.util.ArrayList
            int r11 = r15.$size
            r10.<init>(r11)
            r2 = r10
        L_0x0104:
            r1 = r3
        L_0x0105:
            goto L_0x00c5
        L_0x0106:
            r8 = r2
            java.util.Collection r8 = (java.util.Collection) r8
            boolean r8 = r8.isEmpty()
            r6 = r6 ^ r8
            if (r6 == 0) goto L_0x01d2
            boolean r6 = r15.$partialWindows
            if (r6 != 0) goto L_0x011c
            int r6 = r2.size()
            int r8 = r15.$size
            if (r6 != r8) goto L_0x01d2
        L_0x011c:
            r15.L$0 = r0
            r15.I$0 = r3
            r15.L$1 = r2
            r15.I$1 = r1
            r15.label = r5
            java.lang.Object r5 = r0.yield(r2, r15)
            if (r5 != r4) goto L_0x012d
            return r4
        L_0x012d:
            goto L_0x01d2
        L_0x012f:
            kotlin.collections.RingBuffer r5 = new kotlin.collections.RingBuffer
            r5.<init>(r8)
            java.util.Iterator r8 = r14.$iterator
            r9 = r8
            r8 = r5
            r5 = r15
            r15 = r14
        L_0x013a:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x017c
            java.lang.Object r10 = r9.next()
            r8.add(r10)
            boolean r11 = r8.isFull()
            if (r11 == 0) goto L_0x017b
            boolean r11 = r15.$reuseBuffer
            if (r11 == 0) goto L_0x0155
            r11 = r8
            java.util.List r11 = (java.util.List) r11
            goto L_0x015f
        L_0x0155:
            java.util.ArrayList r11 = new java.util.ArrayList
            r12 = r8
            java.util.Collection r12 = (java.util.Collection) r12
            r11.<init>(r12)
            java.util.List r11 = (java.util.List) r11
        L_0x015f:
            r15.L$0 = r1
            r15.I$0 = r7
            r15.L$1 = r8
            r15.L$2 = r10
            r15.L$3 = r9
            r15.label = r4
            java.lang.Object r11 = r1.yield(r11, r15)
            if (r11 != r0) goto L_0x0172
            return r0
        L_0x0172:
            r13 = r10
            r10 = r5
            r5 = r13
        L_0x0175:
            int r11 = r15.$step
            r8.removeFirst(r11)
            r5 = r10
        L_0x017b:
            goto L_0x013a
        L_0x017c:
            boolean r4 = r15.$partialWindows
            if (r4 == 0) goto L_0x01d0
            r4 = r0
            r0 = r1
            r1 = r8
        L_0x0183:
            int r8 = r1.size()
            int r9 = r15.$step
            if (r8 <= r9) goto L_0x01b2
            boolean r8 = r15.$reuseBuffer
            if (r8 == 0) goto L_0x0193
            r8 = r1
            java.util.List r8 = (java.util.List) r8
            goto L_0x019d
        L_0x0193:
            java.util.ArrayList r8 = new java.util.ArrayList
            r9 = r1
            java.util.Collection r9 = (java.util.Collection) r9
            r8.<init>(r9)
            java.util.List r8 = (java.util.List) r8
        L_0x019d:
            r15.L$0 = r0
            r15.I$0 = r7
            r15.L$1 = r1
            r15.label = r3
            java.lang.Object r8 = r0.yield(r8, r15)
            if (r8 != r4) goto L_0x01ac
            return r4
        L_0x01ac:
            int r8 = r15.$step
            r1.removeFirst(r8)
            goto L_0x0183
        L_0x01b2:
            r3 = r1
            java.util.Collection r3 = (java.util.Collection) r3
            boolean r3 = r3.isEmpty()
            r3 = r3 ^ r6
            if (r3 == 0) goto L_0x01ce
            r15.L$0 = r0
            r15.I$0 = r7
            r15.L$1 = r1
            r15.label = r2
            java.lang.Object r2 = r0.yield(r1, r15)
            if (r2 != r4) goto L_0x01cb
            return r4
        L_0x01cb:
            r2 = r7
            r7 = r5
        L_0x01cd:
            goto L_0x01d2
        L_0x01ce:
            r7 = r5
            goto L_0x01d2
        L_0x01d0:
            r0 = r1
            r7 = r5
        L_0x01d2:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.collections.SlidingWindowKt$windowedIterator$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
