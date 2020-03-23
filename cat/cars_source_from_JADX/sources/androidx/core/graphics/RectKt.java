package androidx.core.graphics;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Region.Op;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\f\u001a\u0015\u0010\u0000\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\f\u001a\r\u0010\u0004\u001a\u00020\u0005*\u00020\u0001H\n\u001a\r\u0010\u0004\u001a\u00020\u0006*\u00020\u0003H\n\u001a\r\u0010\u0007\u001a\u00020\u0005*\u00020\u0001H\n\u001a\r\u0010\u0007\u001a\u00020\u0006*\u00020\u0003H\n\u001a\r\u0010\b\u001a\u00020\u0005*\u00020\u0001H\n\u001a\r\u0010\b\u001a\u00020\u0006*\u00020\u0003H\n\u001a\r\u0010\t\u001a\u00020\u0005*\u00020\u0001H\n\u001a\r\u0010\t\u001a\u00020\u0006*\u00020\u0003H\n\u001a\u0015\u0010\n\u001a\u00020\u000b*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\n\u001a\u0015\u0010\n\u001a\u00020\u000b*\u00020\u00032\u0006\u0010\f\u001a\u00020\u000eH\n\u001a\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\rH\n\u001a\u0015\u0010\u000f\u001a\u00020\u0011*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n\u001a\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0005H\n\u001a\u0015\u0010\u000f\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000eH\n\u001a\u0015\u0010\u000f\u001a\u00020\u0011*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\n\u001a\u0015\u0010\u000f\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0006H\n\u001a\u0015\u0010\u0012\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\f\u001a\u0015\u0010\u0012\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\f\u001a\u0015\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\rH\n\u001a\u0015\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n\u001a\u0015\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0005H\n\u001a\u0015\u0010\u0013\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000eH\n\u001a\u0015\u0010\u0013\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\n\u001a\u0015\u0010\u0013\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0006H\n\u001a\r\u0010\u0014\u001a\u00020\u0001*\u00020\u0003H\b\u001a\r\u0010\u0015\u001a\u00020\u0003*\u00020\u0001H\b\u001a\r\u0010\u0016\u001a\u00020\u0011*\u00020\u0001H\b\u001a\r\u0010\u0016\u001a\u00020\u0011*\u00020\u0003H\b\u001a\u0015\u0010\u0017\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0019H\b\u001a\u0015\u0010\u001a\u001a\u00020\u0011*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\f\u001a\u0015\u0010\u001a\u001a\u00020\u0011*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\f¨\u0006\u001b"}, mo6929d2 = {"and", "Landroid/graphics/Rect;", "r", "Landroid/graphics/RectF;", "component1", "", "", "component2", "component3", "component4", "contains", "", "p", "Landroid/graphics/Point;", "Landroid/graphics/PointF;", "minus", "xy", "Landroid/graphics/Region;", "or", "plus", "toRect", "toRectF", "toRegion", "transform", "m", "Landroid/graphics/Matrix;", "xor", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: Rect.kt */
public final class RectKt {
    public static final int component1(Rect $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.left;
    }

    public static final int component2(Rect $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.top;
    }

    public static final int component3(Rect $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.right;
    }

    public static final int component4(Rect $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.bottom;
    }

    public static final float component1(RectF $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.left;
    }

    public static final float component2(RectF $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.top;
    }

    public static final float component3(RectF $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.right;
    }

    public static final float component4(RectF $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.bottom;
    }

    public static final Rect plus(Rect $receiver, Rect r) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Rect rect = new Rect($receiver);
        rect.union(r);
        return rect;
    }

    public static final RectF plus(RectF $receiver, RectF r) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(r, "r");
        RectF rectF = new RectF($receiver);
        rectF.union(r);
        return rectF;
    }

    public static final Rect plus(Rect $receiver, int xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Rect rect = new Rect($receiver);
        rect.offset(xy, xy);
        return rect;
    }

    public static final RectF plus(RectF $receiver, float xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        RectF rectF = new RectF($receiver);
        rectF.offset(xy, xy);
        return rectF;
    }

    public static final Rect plus(Rect $receiver, Point xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(xy, "xy");
        Rect rect = new Rect($receiver);
        rect.offset(xy.x, xy.y);
        return rect;
    }

    public static final RectF plus(RectF $receiver, PointF xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(xy, "xy");
        RectF rectF = new RectF($receiver);
        rectF.offset(xy.x, xy.y);
        return rectF;
    }

    public static final Region minus(Rect $receiver, Rect r) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region region = new Region($receiver);
        region.op(r, Op.DIFFERENCE);
        return region;
    }

    public static final Region minus(RectF $receiver, RectF r) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(r, "r");
        RectF $receiver$iv = $receiver;
        Rect r$iv = new Rect();
        $receiver$iv.roundOut(r$iv);
        Region region = new Region(r$iv);
        Region $receiver2 = region;
        RectF $receiver$iv2 = r;
        Rect r$iv2 = new Rect();
        $receiver$iv2.roundOut(r$iv2);
        $receiver2.op(r$iv2, Op.DIFFERENCE);
        return region;
    }

    public static final Rect minus(Rect $receiver, int xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Rect rect = new Rect($receiver);
        rect.offset(-xy, -xy);
        return rect;
    }

    public static final RectF minus(RectF $receiver, float xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        RectF rectF = new RectF($receiver);
        rectF.offset(-xy, -xy);
        return rectF;
    }

    public static final Rect minus(Rect $receiver, Point xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(xy, "xy");
        Rect rect = new Rect($receiver);
        rect.offset(-xy.x, -xy.y);
        return rect;
    }

    public static final RectF minus(RectF $receiver, PointF xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(xy, "xy");
        RectF rectF = new RectF($receiver);
        rectF.offset(-xy.x, -xy.y);
        return rectF;
    }

    /* renamed from: or */
    public static final Rect m5or(Rect $receiver, Rect r) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Rect rect = new Rect($receiver);
        rect.union(r);
        return rect;
    }

    /* renamed from: or */
    public static final RectF m6or(RectF $receiver, RectF r) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(r, "r");
        RectF rectF = new RectF($receiver);
        rectF.union(r);
        return rectF;
    }

    public static final Rect and(Rect $receiver, Rect r) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Rect rect = new Rect($receiver);
        rect.intersect(r);
        return rect;
    }

    public static final RectF and(RectF $receiver, RectF r) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(r, "r");
        RectF rectF = new RectF($receiver);
        rectF.intersect(r);
        return rectF;
    }

    public static final Region xor(Rect $receiver, Rect r) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region region = new Region($receiver);
        region.op(r, Op.XOR);
        return region;
    }

    public static final Region xor(RectF $receiver, RectF r) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(r, "r");
        RectF $receiver$iv = $receiver;
        Rect r$iv = new Rect();
        $receiver$iv.roundOut(r$iv);
        Region region = new Region(r$iv);
        Region $receiver2 = region;
        RectF $receiver$iv2 = r;
        Rect r$iv2 = new Rect();
        $receiver$iv2.roundOut(r$iv2);
        $receiver2.op(r$iv2, Op.XOR);
        return region;
    }

    public static final boolean contains(Rect $receiver, Point p) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(p, "p");
        return $receiver.contains(p.x, p.y);
    }

    public static final boolean contains(RectF $receiver, PointF p) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(p, "p");
        return $receiver.contains(p.x, p.y);
    }

    public static final RectF toRectF(Rect $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new RectF($receiver);
    }

    public static final Rect toRect(RectF $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Rect r = new Rect();
        $receiver.roundOut(r);
        return r;
    }

    public static final Region toRegion(Rect $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new Region($receiver);
    }

    public static final Region toRegion(RectF $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        RectF $receiver$iv = $receiver;
        Rect r$iv = new Rect();
        $receiver$iv.roundOut(r$iv);
        return new Region(r$iv);
    }

    public static final RectF transform(RectF $receiver, Matrix m) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(m, "m");
        RectF rectF = $receiver;
        m.mapRect($receiver);
        return $receiver;
    }
}
