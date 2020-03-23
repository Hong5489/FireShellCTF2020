package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010)\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u001a\u0015\u0010\n\u001a\u00020\u000b*\u00020\u00032\u0006\u0010\f\u001a\u00020\u0002H\n\u001a0\u0010\r\u001a\u00020\u000e*\u00020\u00032!\u0010\u000f\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u000e0\u0010H\b\u001aE\u0010\u0013\u001a\u00020\u000e*\u00020\u000326\u0010\u000f\u001a2\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u000e0\u0014H\b\u001a\u0015\u0010\u0016\u001a\u00020\u0002*\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0007H\u0002\u001a\r\u0010\u0017\u001a\u00020\u000b*\u00020\u0003H\b\u001a\r\u0010\u0018\u001a\u00020\u000b*\u00020\u0003H\b\u001a\u0013\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00020\u001a*\u00020\u0003H\u0002\u001a\u0015\u0010\u001b\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\f\u001a\u00020\u0002H\n\u001a\u0015\u0010\u001c\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\f\u001a\u00020\u0002H\n\u001a\u0017\u0010\u001d\u001a\u00020\u000e*\u00020\u001e2\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\b\u001a5\u0010\u001f\u001a\u00020\u000e*\u00020\u001e2\b\b\u0003\u0010 \u001a\u00020\u00072\b\b\u0003\u0010!\u001a\u00020\u00072\b\b\u0003\u0010\"\u001a\u00020\u00072\b\b\u0003\u0010#\u001a\u00020\u0007H\b\u001a5\u0010$\u001a\u00020\u000e*\u00020\u001e2\b\b\u0003\u0010%\u001a\u00020\u00072\b\b\u0003\u0010!\u001a\u00020\u00072\b\b\u0003\u0010&\u001a\u00020\u00072\b\b\u0003\u0010#\u001a\u00020\u0007H\b\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0016\u0010\u0006\u001a\u00020\u0007*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006'"}, mo6929d2 = {"children", "Lkotlin/sequences/Sequence;", "Landroid/view/View;", "Landroid/view/ViewGroup;", "getChildren", "(Landroid/view/ViewGroup;)Lkotlin/sequences/Sequence;", "size", "", "getSize", "(Landroid/view/ViewGroup;)I", "contains", "", "view", "forEach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "forEachIndexed", "Lkotlin/Function2;", "index", "get", "isEmpty", "isNotEmpty", "iterator", "", "minusAssign", "plusAssign", "setMargins", "Landroid/view/ViewGroup$MarginLayoutParams;", "updateMargins", "left", "top", "right", "bottom", "updateMarginsRelative", "start", "end", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: ViewGroup.kt */
public final class ViewGroupKt {
    public static final View get(ViewGroup $receiver, int index) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        View childAt = $receiver.getChildAt(index);
        if (childAt != null) {
            return childAt;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Index: ");
        sb.append(index);
        sb.append(", Size: ");
        sb.append($receiver.getChildCount());
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public static final boolean contains(ViewGroup $receiver, View view) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(view, "view");
        return $receiver.indexOfChild(view) != -1;
    }

    public static final void plusAssign(ViewGroup $receiver, View view) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(view, "view");
        $receiver.addView(view);
    }

    public static final void minusAssign(ViewGroup $receiver, View view) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(view, "view");
        $receiver.removeView(view);
    }

    public static final int getSize(ViewGroup $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.getChildCount();
    }

    public static final boolean isEmpty(ViewGroup $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.getChildCount() == 0;
    }

    public static final boolean isNotEmpty(ViewGroup $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.getChildCount() != 0;
    }

    public static final void forEach(ViewGroup $receiver, Function1<? super View, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int childCount = $receiver.getChildCount();
        for (int index = 0; index < childCount; index++) {
            View childAt = $receiver.getChildAt(index);
            Intrinsics.checkExpressionValueIsNotNull(childAt, "getChildAt(index)");
            action.invoke(childAt);
        }
    }

    public static final void forEachIndexed(ViewGroup $receiver, Function2<? super Integer, ? super View, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int childCount = $receiver.getChildCount();
        for (int index = 0; index < childCount; index++) {
            Integer valueOf = Integer.valueOf(index);
            View childAt = $receiver.getChildAt(index);
            Intrinsics.checkExpressionValueIsNotNull(childAt, "getChildAt(index)");
            action.invoke(valueOf, childAt);
        }
    }

    public static final Iterator<View> iterator(ViewGroup $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new ViewGroupKt$iterator$1<>($receiver);
    }

    public static final Sequence<View> getChildren(ViewGroup $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new ViewGroupKt$children$1<>($receiver);
    }

    public static final void setMargins(MarginLayoutParams $receiver, int size) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setMargins(size, size, size, size);
    }

    public static /* bridge */ /* synthetic */ void updateMargins$default(MarginLayoutParams $receiver, int left, int top, int right, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            left = $receiver.leftMargin;
        }
        if ((i & 2) != 0) {
            top = $receiver.topMargin;
        }
        if ((i & 4) != 0) {
            right = $receiver.rightMargin;
        }
        if ((i & 8) != 0) {
            bottom = $receiver.bottomMargin;
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setMargins(left, top, right, bottom);
    }

    public static final void updateMargins(MarginLayoutParams $receiver, int left, int top, int right, int bottom) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setMargins(left, top, right, bottom);
    }

    public static /* bridge */ /* synthetic */ void updateMarginsRelative$default(MarginLayoutParams $receiver, int start, int top, int end, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            start = $receiver.getMarginStart();
        }
        if ((i & 2) != 0) {
            top = $receiver.topMargin;
        }
        if ((i & 4) != 0) {
            end = $receiver.getMarginEnd();
        }
        if ((i & 8) != 0) {
            bottom = $receiver.bottomMargin;
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setMarginStart(start);
        $receiver.topMargin = top;
        $receiver.setMarginEnd(end);
        $receiver.bottomMargin = bottom;
    }

    public static final void updateMarginsRelative(MarginLayoutParams $receiver, int start, int top, int end, int bottom) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setMarginStart(start);
        $receiver.topMargin = top;
        $receiver.setMarginEnd(end);
        $receiver.bottomMargin = bottom;
    }
}
