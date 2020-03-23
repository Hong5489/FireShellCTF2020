package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.jvm.functions.Function1;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001e\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0016ø\u0001\u0000¢\u0006\u0002\u0010\nR\u0014\u0010\u0002\u001a\u00020\u00038VX\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u000b"}, mo6929d2 = {"kotlin/coroutines/ContinuationKt$Continuation$1", "Lkotlin/coroutines/Continuation;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: Continuation.kt */
public final class ContinuationKt$Continuation$1 implements Continuation<T> {
    final /* synthetic */ CoroutineContext $context;
    final /* synthetic */ Function1 $resumeWith;

    public ContinuationKt$Continuation$1(CoroutineContext $captured_local_variable$0, Function1 $captured_local_variable$1) {
        this.$context = $captured_local_variable$0;
        this.$resumeWith = $captured_local_variable$1;
    }

    public CoroutineContext getContext() {
        return this.$context;
    }

    public void resumeWith(Object result) {
        this.$resumeWith.invoke(Result.m46boximpl(result));
    }
}
