package androidx.core.view;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo6929d2 = {"<anonymous>", "", "run"}, mo6930k = 3, mo6931mv = {1, 1, 10})
/* compiled from: View.kt */
public final class ViewKt$postOnAnimationDelayed$runnable$1 implements Runnable {
    final /* synthetic */ Function0 $action;

    public ViewKt$postOnAnimationDelayed$runnable$1(Function0 function0) {
        this.$action = function0;
    }

    public final void run() {
        this.$action.invoke();
    }
}
