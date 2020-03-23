package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\"\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0007H\u0014ø\u0001\u0000¢\u0006\u0002\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u000e¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\t¸\u0006\u0000"}, mo6929d2 = {"kotlin/coroutines/intrinsics/IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$2", "Lkotlin/coroutines/jvm/internal/ContinuationImpl;", "label", "", "invokeSuspend", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* renamed from: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4 */
/* compiled from: IntrinsicsJvm.kt */
public final class C0328xa50de663 extends ContinuationImpl {
    final /* synthetic */ Continuation $completion;
    final /* synthetic */ CoroutineContext $context;
    final /* synthetic */ Object $receiver$inlined;
    final /* synthetic */ Function2 $this_createCoroutineUnintercepted$inlined;
    private int label;

    public C0328xa50de663(Continuation $captured_local_variable$1, CoroutineContext $captured_local_variable$2, Continuation $super_call_param$3, CoroutineContext $super_call_param$4, Function2 function2, Object obj) {
        this.$completion = $captured_local_variable$1;
        this.$context = $captured_local_variable$2;
        this.$this_createCoroutineUnintercepted$inlined = function2;
        this.$receiver$inlined = obj;
        super($super_call_param$3, $super_call_param$4);
    }

    /* access modifiers changed from: protected */
    public Object invokeSuspend(Object result) {
        int i = this.label;
        if (i == 0) {
            this.label = 1;
            ResultKt.throwOnFailure(result);
            Continuation it = this;
            Function2 function2 = this.$this_createCoroutineUnintercepted$inlined;
            if (function2 != null) {
                return ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2)).invoke(this.$receiver$inlined, it);
            }
            throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
        } else if (i == 1) {
            this.label = 2;
            ResultKt.throwOnFailure(result);
            return result;
        } else {
            throw new IllegalStateException("This coroutine had already completed".toString());
        }
    }
}
