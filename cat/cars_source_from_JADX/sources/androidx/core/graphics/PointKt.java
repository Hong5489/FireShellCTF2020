package androidx.core.graphics;

import android.graphics.Point;
import android.graphics.PointF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\t\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n\u001a\r\u0010\u0000\u001a\u00020\u0003*\u00020\u0004H\n\u001a\r\u0010\u0005\u001a\u00020\u0001*\u00020\u0002H\n\u001a\r\u0010\u0005\u001a\u00020\u0003*\u00020\u0004H\n\u001a\u0015\u0010\u0006\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\n\u001a\u0015\u0010\u0006\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\b\u001a\u00020\u0001H\n\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004H\n\u001a\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\b\u001a\u00020\u0003H\n\u001a\u0015\u0010\t\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\n\u001a\u0015\u0010\t\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\b\u001a\u00020\u0001H\n\u001a\u0015\u0010\t\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004H\n\u001a\u0015\u0010\t\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\b\u001a\u00020\u0003H\n\u001a\r\u0010\n\u001a\u00020\u0002*\u00020\u0004H\b\u001a\r\u0010\u000b\u001a\u00020\u0004*\u00020\u0002H\b\u001a\r\u0010\f\u001a\u00020\u0002*\u00020\u0002H\n\u001a\r\u0010\f\u001a\u00020\u0004*\u00020\u0004H\n¨\u0006\r"}, mo6929d2 = {"component1", "", "Landroid/graphics/Point;", "", "Landroid/graphics/PointF;", "component2", "minus", "p", "xy", "plus", "toPoint", "toPointF", "unaryMinus", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: Point.kt */
public final class PointKt {
    public static final int component1(Point $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.x;
    }

    public static final int component2(Point $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.y;
    }

    public static final float component1(PointF $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.x;
    }

    public static final float component2(PointF $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.y;
    }

    public static final Point plus(Point $receiver, Point p) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(p, "p");
        Point point = new Point($receiver.x, $receiver.y);
        point.offset(p.x, p.y);
        return point;
    }

    public static final PointF plus(PointF $receiver, PointF p) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(p, "p");
        PointF pointF = new PointF($receiver.x, $receiver.y);
        pointF.offset(p.x, p.y);
        return pointF;
    }

    public static final Point plus(Point $receiver, int xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Point point = new Point($receiver.x, $receiver.y);
        point.offset(xy, xy);
        return point;
    }

    public static final PointF plus(PointF $receiver, float xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        PointF pointF = new PointF($receiver.x, $receiver.y);
        pointF.offset(xy, xy);
        return pointF;
    }

    public static final Point minus(Point $receiver, Point p) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(p, "p");
        Point point = new Point($receiver.x, $receiver.y);
        point.offset(-p.x, -p.y);
        return point;
    }

    public static final PointF minus(PointF $receiver, PointF p) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(p, "p");
        PointF pointF = new PointF($receiver.x, $receiver.y);
        pointF.offset(-p.x, -p.y);
        return pointF;
    }

    public static final Point minus(Point $receiver, int xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Point point = new Point($receiver.x, $receiver.y);
        point.offset(-xy, -xy);
        return point;
    }

    public static final PointF minus(PointF $receiver, float xy) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        PointF pointF = new PointF($receiver.x, $receiver.y);
        pointF.offset(-xy, -xy);
        return pointF;
    }

    public static final Point unaryMinus(Point $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new Point(-$receiver.x, -$receiver.y);
    }

    public static final PointF unaryMinus(PointF $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new PointF(-$receiver.x, -$receiver.y);
    }

    public static final PointF toPointF(Point $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new PointF($receiver);
    }

    public static final Point toPoint(PointF $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return new Point((int) $receiver.x, (int) $receiver.y);
    }
}
