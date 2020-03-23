package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.ColorSpace.Named;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001a#\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\b\u001a7\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\b\u001a&\u0010\u000b\u001a\u00020\u0001*\u00020\u00012\u0017\u0010\f\u001a\u0013\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r¢\u0006\u0002\b\u0010H\b\u001a\u001d\u0010\u0011\u001a\u00020\u0003*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0003H\n\u001a'\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\bH\b\u001a'\u0010\u0016\u001a\u00020\u000f*\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00032\b\b\u0001\u0010\u0017\u001a\u00020\u0003H\n¨\u0006\u0018"}, mo6929d2 = {"createBitmap", "Landroid/graphics/Bitmap;", "width", "", "height", "config", "Landroid/graphics/Bitmap$Config;", "hasAlpha", "", "colorSpace", "Landroid/graphics/ColorSpace;", "applyCanvas", "block", "Lkotlin/Function1;", "Landroid/graphics/Canvas;", "", "Lkotlin/ExtensionFunctionType;", "get", "x", "y", "scale", "filter", "set", "color", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: Bitmap.kt */
public final class BitmapKt {
    public static final Bitmap applyCanvas(Bitmap $receiver, Function1<? super Canvas, Unit> block) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(block, "block");
        block.invoke(new Canvas($receiver));
        return $receiver;
    }

    public static final int get(Bitmap $receiver, int x, int y) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.getPixel(x, y);
    }

    public static final void set(Bitmap $receiver, int x, int y, int color) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        $receiver.setPixel(x, y, color);
    }

    public static /* bridge */ /* synthetic */ Bitmap scale$default(Bitmap $receiver, int width, int height, boolean filter, int i, Object obj) {
        if ((i & 4) != 0) {
            filter = true;
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap($receiver, width, height, filter);
        Intrinsics.checkExpressionValueIsNotNull(createScaledBitmap, "Bitmap.createScaledBitma…s, width, height, filter)");
        return createScaledBitmap;
    }

    public static final Bitmap scale(Bitmap $receiver, int width, int height, boolean filter) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap($receiver, width, height, filter);
        Intrinsics.checkExpressionValueIsNotNull(createScaledBitmap, "Bitmap.createScaledBitma…s, width, height, filter)");
        return createScaledBitmap;
    }

    public static /* bridge */ /* synthetic */ Bitmap createBitmap$default(int width, int height, Config config, int i, Object obj) {
        if ((i & 4) != 0) {
            config = Config.ARGB_8888;
        }
        Intrinsics.checkParameterIsNotNull(config, "config");
        Bitmap createBitmap = Bitmap.createBitmap(width, height, config);
        Intrinsics.checkExpressionValueIsNotNull(createBitmap, "Bitmap.createBitmap(width, height, config)");
        return createBitmap;
    }

    public static final Bitmap createBitmap(int width, int height, Config config) {
        Intrinsics.checkParameterIsNotNull(config, "config");
        Bitmap createBitmap = Bitmap.createBitmap(width, height, config);
        Intrinsics.checkExpressionValueIsNotNull(createBitmap, "Bitmap.createBitmap(width, height, config)");
        return createBitmap;
    }

    public static /* bridge */ /* synthetic */ Bitmap createBitmap$default(int width, int height, Config config, boolean hasAlpha, ColorSpace colorSpace, int i, Object obj) {
        if ((i & 4) != 0) {
            config = Config.ARGB_8888;
        }
        if ((i & 8) != 0) {
            hasAlpha = true;
        }
        if ((i & 16) != 0) {
            ColorSpace colorSpace2 = ColorSpace.get(Named.SRGB);
            Intrinsics.checkExpressionValueIsNotNull(colorSpace2, "ColorSpace.get(ColorSpace.Named.SRGB)");
            colorSpace = colorSpace2;
        }
        Intrinsics.checkParameterIsNotNull(config, "config");
        Intrinsics.checkParameterIsNotNull(colorSpace, "colorSpace");
        Bitmap createBitmap = Bitmap.createBitmap(width, height, config, hasAlpha, colorSpace);
        Intrinsics.checkExpressionValueIsNotNull(createBitmap, "Bitmap.createBitmap(widt…ig, hasAlpha, colorSpace)");
        return createBitmap;
    }

    public static final Bitmap createBitmap(int width, int height, Config config, boolean hasAlpha, ColorSpace colorSpace) {
        Intrinsics.checkParameterIsNotNull(config, "config");
        Intrinsics.checkParameterIsNotNull(colorSpace, "colorSpace");
        Bitmap createBitmap = Bitmap.createBitmap(width, height, config, hasAlpha, colorSpace);
        Intrinsics.checkExpressionValueIsNotNull(createBitmap, "Bitmap.createBitmap(widt…ig, hasAlpha, colorSpace)");
        return createBitmap;
    }
}
