package androidx.core.graphics;

import android.graphics.Canvas;
import android.graphics.Matrix;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\t\u001a0\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\u0002\b\u0007H\b\u001aD\u0010\b\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\u0002\b\u0007H\b\u001a&\u0010\r\u001a\u00020\u0001*\u00020\u00022\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\u0002\b\u0007H\b\u001aN\u0010\u000e\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\u0002\b\u0007H\b\u001a:\u0010\u0011\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\n2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\u0002\b\u0007H\b\u001a:\u0010\u0012\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\n2\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\u0002\b\u0007H\b¨\u0006\u0013"}, mo6929d2 = {"withMatrix", "", "Landroid/graphics/Canvas;", "matrix", "Landroid/graphics/Matrix;", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "withRotation", "degrees", "", "pivotX", "pivotY", "withSave", "withScale", "x", "y", "withSkew", "withTranslation", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: Canvas.kt */
public final class CanvasKt {
    public static final void withSave(Canvas $receiver, Function1<? super Canvas, Unit> block) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(block, "block");
        int checkpoint = $receiver.save();
        try {
            block.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            $receiver.restoreToCount(checkpoint);
            InlineMarker.finallyEnd(1);
        }
    }

    public static /* bridge */ /* synthetic */ void withTranslation$default(Canvas $receiver, float x, float y, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            x = 0.0f;
        }
        if ((i & 2) != 0) {
            y = 0.0f;
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(block, "block");
        int checkpoint = $receiver.save();
        $receiver.translate(x, y);
        try {
            block.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            $receiver.restoreToCount(checkpoint);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final void withTranslation(Canvas $receiver, float x, float y, Function1<? super Canvas, Unit> block) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(block, "block");
        int checkpoint = $receiver.save();
        $receiver.translate(x, y);
        try {
            block.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            $receiver.restoreToCount(checkpoint);
            InlineMarker.finallyEnd(1);
        }
    }

    public static /* bridge */ /* synthetic */ void withRotation$default(Canvas $receiver, float degrees, float pivotX, float pivotY, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            degrees = 0.0f;
        }
        if ((i & 2) != 0) {
            pivotX = 0.0f;
        }
        if ((i & 4) != 0) {
            pivotY = 0.0f;
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(block, "block");
        int checkpoint = $receiver.save();
        $receiver.rotate(degrees, pivotX, pivotY);
        try {
            block.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            $receiver.restoreToCount(checkpoint);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final void withRotation(Canvas $receiver, float degrees, float pivotX, float pivotY, Function1<? super Canvas, Unit> block) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(block, "block");
        int checkpoint = $receiver.save();
        $receiver.rotate(degrees, pivotX, pivotY);
        try {
            block.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            $receiver.restoreToCount(checkpoint);
            InlineMarker.finallyEnd(1);
        }
    }

    public static /* bridge */ /* synthetic */ void withScale$default(Canvas $receiver, float x, float y, float pivotX, float pivotY, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            x = 1.0f;
        }
        if ((i & 2) != 0) {
            y = 1.0f;
        }
        if ((i & 4) != 0) {
            pivotX = 0.0f;
        }
        if ((i & 8) != 0) {
            pivotY = 0.0f;
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(block, "block");
        int checkpoint = $receiver.save();
        $receiver.scale(x, y, pivotX, pivotY);
        try {
            block.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            $receiver.restoreToCount(checkpoint);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final void withScale(Canvas $receiver, float x, float y, float pivotX, float pivotY, Function1<? super Canvas, Unit> block) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(block, "block");
        int checkpoint = $receiver.save();
        $receiver.scale(x, y, pivotX, pivotY);
        try {
            block.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            $receiver.restoreToCount(checkpoint);
            InlineMarker.finallyEnd(1);
        }
    }

    public static /* bridge */ /* synthetic */ void withSkew$default(Canvas $receiver, float x, float y, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            x = 0.0f;
        }
        if ((i & 2) != 0) {
            y = 0.0f;
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(block, "block");
        int checkpoint = $receiver.save();
        $receiver.skew(x, y);
        try {
            block.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            $receiver.restoreToCount(checkpoint);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final void withSkew(Canvas $receiver, float x, float y, Function1<? super Canvas, Unit> block) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(block, "block");
        int checkpoint = $receiver.save();
        $receiver.skew(x, y);
        try {
            block.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            $receiver.restoreToCount(checkpoint);
            InlineMarker.finallyEnd(1);
        }
    }

    public static /* bridge */ /* synthetic */ void withMatrix$default(Canvas $receiver, Matrix matrix, Function1 block, int i, Object obj) {
        if ((i & 1) != 0) {
            matrix = new Matrix();
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(matrix, "matrix");
        Intrinsics.checkParameterIsNotNull(block, "block");
        int checkpoint = $receiver.save();
        $receiver.concat(matrix);
        try {
            block.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            $receiver.restoreToCount(checkpoint);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final void withMatrix(Canvas $receiver, Matrix matrix, Function1<? super Canvas, Unit> block) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(matrix, "matrix");
        Intrinsics.checkParameterIsNotNull(block, "block");
        int checkpoint = $receiver.save();
        $receiver.concat(matrix);
        try {
            block.invoke($receiver);
        } finally {
            InlineMarker.finallyStart(1);
            $receiver.restoreToCount(checkpoint);
            InlineMarker.finallyEnd(1);
        }
    }
}
