package androidx.core.view;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000V\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u001a2\u0010\u0019\u001a\u00020\u001a*\u00020\u00032#\b\u0004\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u001a0\u001cH\b\u001a2\u0010 \u001a\u00020\u001a*\u00020\u00032#\b\u0004\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u001a0\u001cH\b\u001a2\u0010!\u001a\u00020\u001a*\u00020\u00032#\b\u0004\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u001a0\u001cH\b\u001a\u0014\u0010\"\u001a\u00020#*\u00020\u00032\b\b\u0002\u0010$\u001a\u00020%\u001a%\u0010&\u001a\u00020'*\u00020\u00032\u0006\u0010(\u001a\u00020)2\u000e\b\u0004\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0*H\b\u001a%\u0010+\u001a\u00020'*\u00020\u00032\u0006\u0010(\u001a\u00020)2\u000e\b\u0004\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0*H\b\u001a\u0017\u0010,\u001a\u00020\u001a*\u00020\u00032\b\b\u0001\u0010-\u001a\u00020\fH\b\u001a7\u0010.\u001a\u00020\u001a\"\n\b\u0000\u0010/\u0018\u0001*\u000200*\u00020\u00032\u0017\u00101\u001a\u0013\u0012\u0004\u0012\u0002H/\u0012\u0004\u0012\u00020\u001a0\u001c¢\u0006\u0002\b2H\b¢\u0006\u0002\b3\u001a&\u0010.\u001a\u00020\u001a*\u00020\u00032\u0017\u00101\u001a\u0013\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020\u001a0\u001c¢\u0006\u0002\b2H\b\u001a5\u00104\u001a\u00020\u001a*\u00020\u00032\b\b\u0003\u00105\u001a\u00020\f2\b\b\u0003\u00106\u001a\u00020\f2\b\b\u0003\u00107\u001a\u00020\f2\b\b\u0003\u00108\u001a\u00020\fH\b\u001a5\u00109\u001a\u00020\u001a*\u00020\u00032\b\b\u0003\u0010:\u001a\u00020\f2\b\b\u0003\u00106\u001a\u00020\f2\b\b\u0003\u0010;\u001a\u00020\f2\b\b\u0003\u00108\u001a\u00020\fH\b\"*\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u000e¢\u0006\f\u001a\u0004\b\u0002\u0010\u0004\"\u0004\b\u0005\u0010\u0006\"*\u0010\u0007\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u000e¢\u0006\f\u001a\u0004\b\u0007\u0010\u0004\"\u0004\b\b\u0010\u0006\"*\u0010\t\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0004\"\u0004\b\n\u0010\u0006\"\u0016\u0010\u000b\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\"\u0016\u0010\u000f\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000e\"\u0016\u0010\u0011\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000e\"\u0016\u0010\u0013\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000e\"\u0016\u0010\u0015\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u000e\"\u0016\u0010\u0017\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u000e¨\u0006<"}, mo6929d2 = {"value", "", "isGone", "Landroid/view/View;", "(Landroid/view/View;)Z", "setGone", "(Landroid/view/View;Z)V", "isInvisible", "setInvisible", "isVisible", "setVisible", "marginBottom", "", "getMarginBottom", "(Landroid/view/View;)I", "marginEnd", "getMarginEnd", "marginLeft", "getMarginLeft", "marginRight", "getMarginRight", "marginStart", "getMarginStart", "marginTop", "getMarginTop", "doOnLayout", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "view", "doOnNextLayout", "doOnPreDraw", "drawToBitmap", "Landroid/graphics/Bitmap;", "config", "Landroid/graphics/Bitmap$Config;", "postDelayed", "Ljava/lang/Runnable;", "delayInMillis", "", "Lkotlin/Function0;", "postOnAnimationDelayed", "setPadding", "size", "updateLayoutParams", "T", "Landroid/view/ViewGroup$LayoutParams;", "block", "Lkotlin/ExtensionFunctionType;", "updateLayoutParamsTyped", "updatePadding", "left", "top", "right", "bottom", "updatePaddingRelative", "start", "end", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: View.kt */
public final class ViewKt {
    public static final void doOnNextLayout(View $receiver, Function1<? super View, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        $receiver.addOnLayoutChangeListener(new ViewKt$doOnNextLayout$1(action));
    }

    public static final void doOnLayout(View $receiver, Function1<? super View, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        if (!ViewCompat.isLaidOut($receiver) || $receiver.isLayoutRequested()) {
            $receiver.addOnLayoutChangeListener(new ViewKt$doOnLayout$$inlined$doOnNextLayout$1(action));
        } else {
            action.invoke($receiver);
        }
    }

    public static final void doOnPreDraw(View $receiver, Function1<? super View, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        ViewTreeObserver vto = $receiver.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewKt$doOnPreDraw$1($receiver, action, vto));
    }

    public static /* bridge */ /* synthetic */ void updatePaddingRelative$default(View $receiver, int start, int top, int end, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            start = $receiver.getPaddingStart();
        }
        if ((i & 2) != 0) {
            top = $receiver.getPaddingTop();
        }
        if ((i & 4) != 0) {
            end = $receiver.getPaddingEnd();
        }
        if ((i & 8) != 0) {
            bottom = $receiver.getPaddingBottom();
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setPaddingRelative(start, top, end, bottom);
    }

    public static final void updatePaddingRelative(View $receiver, int start, int top, int end, int bottom) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setPaddingRelative(start, top, end, bottom);
    }

    public static /* bridge */ /* synthetic */ void updatePadding$default(View $receiver, int left, int top, int right, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            left = $receiver.getPaddingLeft();
        }
        if ((i & 2) != 0) {
            top = $receiver.getPaddingTop();
        }
        if ((i & 4) != 0) {
            right = $receiver.getPaddingRight();
        }
        if ((i & 8) != 0) {
            bottom = $receiver.getPaddingBottom();
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setPadding(left, top, right, bottom);
    }

    public static final void updatePadding(View $receiver, int left, int top, int right, int bottom) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setPadding(left, top, right, bottom);
    }

    public static final void setPadding(View $receiver, int size) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setPadding(size, size, size, size);
    }

    public static final Runnable postDelayed(View $receiver, long delayInMillis, Function0<Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        Runnable runnable = new ViewKt$postDelayed$runnable$1(action);
        $receiver.postDelayed(runnable, delayInMillis);
        return runnable;
    }

    public static final Runnable postOnAnimationDelayed(View $receiver, long delayInMillis, Function0<Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        Runnable runnable = new ViewKt$postOnAnimationDelayed$runnable$1(action);
        $receiver.postOnAnimationDelayed(runnable, delayInMillis);
        return runnable;
    }

    public static /* bridge */ /* synthetic */ Bitmap drawToBitmap$default(View view, Config config, int i, Object obj) {
        if ((i & 1) != 0) {
            config = Config.ARGB_8888;
        }
        return drawToBitmap(view, config);
    }

    public static final Bitmap drawToBitmap(View $receiver, Config config) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(config, "config");
        if (ViewCompat.isLaidOut($receiver)) {
            Bitmap $receiver$iv = Bitmap.createBitmap($receiver.getWidth(), $receiver.getHeight(), config);
            Intrinsics.checkExpressionValueIsNotNull($receiver$iv, "Bitmap.createBitmap(width, height, config)");
            Canvas $receiver2 = new Canvas($receiver$iv);
            $receiver2.translate(-((float) $receiver.getScrollX()), -((float) $receiver.getScrollY()));
            $receiver.draw($receiver2);
            return $receiver$iv;
        }
        throw new IllegalStateException("View needs to be laid out before calling drawToBitmap()");
    }

    public static final boolean isVisible(View $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.getVisibility() == 0;
    }

    public static final void setVisible(View $receiver, boolean value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setVisibility(value ? 0 : 8);
    }

    public static final boolean isInvisible(View $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.getVisibility() == 4;
    }

    public static final void setInvisible(View $receiver, boolean value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setVisibility(value ? 4 : 0);
    }

    public static final boolean isGone(View $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.getVisibility() == 8;
    }

    public static final void setGone(View $receiver, boolean value) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setVisibility(value ? 8 : 0);
    }

    public static final void updateLayoutParams(View $receiver, Function1<? super LayoutParams, Unit> block) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(block, "block");
        View $receiver$iv = $receiver;
        LayoutParams params$iv = $receiver$iv.getLayoutParams();
        if (params$iv != null) {
            block.invoke(params$iv);
            $receiver$iv.setLayoutParams(params$iv);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
    }

    private static final <T extends LayoutParams> void updateLayoutParamsTyped(View $receiver, Function1<? super T, Unit> block) {
        LayoutParams layoutParams = $receiver.getLayoutParams();
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        LayoutParams params = layoutParams;
        block.invoke(params);
        $receiver.setLayoutParams(params);
    }

    public static final int getMarginLeft(View $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        LayoutParams layoutParams = $receiver.getLayoutParams();
        if (!(layoutParams instanceof MarginLayoutParams)) {
            layoutParams = null;
        }
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
        if (marginLayoutParams != null) {
            return marginLayoutParams.leftMargin;
        }
        return 0;
    }

    public static final int getMarginTop(View $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        LayoutParams layoutParams = $receiver.getLayoutParams();
        if (!(layoutParams instanceof MarginLayoutParams)) {
            layoutParams = null;
        }
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
        if (marginLayoutParams != null) {
            return marginLayoutParams.topMargin;
        }
        return 0;
    }

    public static final int getMarginRight(View $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        LayoutParams layoutParams = $receiver.getLayoutParams();
        if (!(layoutParams instanceof MarginLayoutParams)) {
            layoutParams = null;
        }
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
        if (marginLayoutParams != null) {
            return marginLayoutParams.rightMargin;
        }
        return 0;
    }

    public static final int getMarginBottom(View $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        LayoutParams layoutParams = $receiver.getLayoutParams();
        if (!(layoutParams instanceof MarginLayoutParams)) {
            layoutParams = null;
        }
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
        if (marginLayoutParams != null) {
            return marginLayoutParams.bottomMargin;
        }
        return 0;
    }

    public static final int getMarginStart(View $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        LayoutParams lp = $receiver.getLayoutParams();
        if (lp instanceof MarginLayoutParams) {
            return MarginLayoutParamsCompat.getMarginStart((MarginLayoutParams) lp);
        }
        return 0;
    }

    public static final int getMarginEnd(View $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        LayoutParams lp = $receiver.getLayoutParams();
        if (lp instanceof MarginLayoutParams) {
            return MarginLayoutParamsCompat.getMarginEnd((MarginLayoutParams) lp);
        }
        return 0;
    }
}
