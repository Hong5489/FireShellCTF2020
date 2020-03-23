package androidx.core.view;

import android.view.View;
import android.view.View.OnLayoutChangeListener;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000A\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\b\b\n\u0002\b\b\n\u0002\b\b\n\u0002\b\b\n\u0002\b\b\n\u0002\b\b\n\u0002\b\b*\u0001\u0000\b\n\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JP\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0016¨\u0006\u0010¸\u0006\u0000"}, mo6929d2 = {"androidx/core/view/ViewKt$doOnNextLayout$1", "Landroid/view/View$OnLayoutChangeListener;", "(Lkotlin/jvm/functions/Function1;)V", "onLayoutChange", "", "view", "Landroid/view/View;", "left", "", "top", "right", "bottom", "oldLeft", "oldTop", "oldRight", "oldBottom", "core-ktx_release"}, mo6930k = 1, mo6931mv = {1, 1, 10})
/* compiled from: View.kt */
public final class ViewKt$doOnLayout$$inlined$doOnNextLayout$1 implements OnLayoutChangeListener {
    final /* synthetic */ Function1 $action$inlined;

    public ViewKt$doOnLayout$$inlined$doOnNextLayout$1(Function1 function1) {
        this.$action$inlined = function1;
    }

    public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        view.removeOnLayoutChangeListener(this);
        this.$action$inlined.invoke(view);
    }
}
