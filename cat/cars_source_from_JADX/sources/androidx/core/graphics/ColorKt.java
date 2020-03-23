package androidx.core.graphics;

import android.graphics.Color;
import android.graphics.ColorSpace;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u00006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\r\u0010\u0018\u001a\u00020\u0001*\u00020\u0001H\n\u001a\r\u0010\u0018\u001a\u00020\u0004*\u00020\u0005H\n\u001a\r\u0010\u0018\u001a\u00020\u0004*\u00020\u0019H\n\u001a\r\u0010\u001a\u001a\u00020\u0001*\u00020\u0001H\n\u001a\r\u0010\u001a\u001a\u00020\u0004*\u00020\u0005H\n\u001a\r\u0010\u001a\u001a\u00020\u0004*\u00020\u0019H\n\u001a\r\u0010\u001b\u001a\u00020\u0001*\u00020\u0001H\n\u001a\r\u0010\u001b\u001a\u00020\u0004*\u00020\u0005H\n\u001a\r\u0010\u001b\u001a\u00020\u0004*\u00020\u0019H\n\u001a\r\u0010\u001c\u001a\u00020\u0001*\u00020\u0001H\n\u001a\r\u0010\u001c\u001a\u00020\u0004*\u00020\u0005H\n\u001a\r\u0010\u001c\u001a\u00020\u0004*\u00020\u0019H\n\u001a\u0015\u0010\u001d\u001a\u00020\u0019*\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u0019H\u0002\u001a\r\u0010\u001f\u001a\u00020\u0019*\u00020\u0001H\b\u001a\r\u0010\u001f\u001a\u00020\u0019*\u00020\u0005H\b\u001a\r\u0010 \u001a\u00020\u0001*\u00020\u0005H\b\u001a\r\u0010 \u001a\u00020\u0001*\u00020!H\b\u001a\r\u0010\"\u001a\u00020\u0005*\u00020\u0001H\b\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0003\"\u0016\u0010\u0000\u001a\u00020\u0004*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0006\"\u0016\u0010\u0007\u001a\u00020\u0001*\u00020\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\b\b\u0010\u0003\"\u0016\u0010\u0007\u001a\u00020\u0004*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006\"\u0016\u0010\t\u001a\u00020\n*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u0016\u0010\r\u001a\u00020\u0001*\u00020\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0003\"\u0016\u0010\r\u001a\u00020\u0004*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0006\"\u0016\u0010\u000f\u001a\u00020\u0010*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011\"\u0016\u0010\u0012\u001a\u00020\u0010*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0011\"\u0016\u0010\u0013\u001a\u00020\u0004*\u00020\u00018Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\"\u0016\u0010\u0013\u001a\u00020\u0004*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0006\"\u0016\u0010\u0016\u001a\u00020\u0001*\u00020\u00018Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0003\"\u0016\u0010\u0016\u001a\u00020\u0004*\u00020\u00058Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0006¨\u0006#"}, mo6929d2 = {"alpha", "", "getAlpha", "(I)I", "", "", "(J)F", "blue", "getBlue", "colorSpace", "Landroid/graphics/ColorSpace;", "getColorSpace", "(J)Landroid/graphics/ColorSpace;", "green", "getGreen", "isSrgb", "", "(J)Z", "isWideGamut", "luminance", "getLuminance", "(I)F", "red", "getRed", "component1", "Landroid/graphics/Color;", "component2", "component3", "component4", "plus", "c", "toColor", "toColorInt", "", "toColorLong", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: Color.kt */
public final class ColorKt {
    public static final float component1(Color $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.getComponent(0);
    }

    public static final float component2(Color $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.getComponent(1);
    }

    public static final float component3(Color $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.getComponent(2);
    }

    public static final float component4(Color $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return $receiver.getComponent(3);
    }

    public static final Color plus(Color $receiver, Color c) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(c, "c");
        Color compositeColors = ColorUtils.compositeColors(c, $receiver);
        Intrinsics.checkExpressionValueIsNotNull(compositeColors, "ColorUtils.compositeColors(c, this)");
        return compositeColors;
    }

    public static final int getAlpha(int $receiver) {
        return ($receiver >> 24) & 255;
    }

    public static final int getRed(int $receiver) {
        return ($receiver >> 16) & 255;
    }

    public static final int getGreen(int $receiver) {
        return ($receiver >> 8) & 255;
    }

    public static final int getBlue(int $receiver) {
        return $receiver & 255;
    }

    public static final int component1(int $receiver) {
        return ($receiver >> 24) & 255;
    }

    public static final int component2(int $receiver) {
        return ($receiver >> 16) & 255;
    }

    public static final int component3(int $receiver) {
        return ($receiver >> 8) & 255;
    }

    public static final int component4(int $receiver) {
        return $receiver & 255;
    }

    public static final float getLuminance(int $receiver) {
        return Color.luminance($receiver);
    }

    public static final Color toColor(int $receiver) {
        Color valueOf = Color.valueOf($receiver);
        Intrinsics.checkExpressionValueIsNotNull(valueOf, "Color.valueOf(this)");
        return valueOf;
    }

    public static final long toColorLong(int $receiver) {
        return Color.pack($receiver);
    }

    public static final float component1(long $receiver) {
        return Color.red($receiver);
    }

    public static final float component2(long $receiver) {
        return Color.green($receiver);
    }

    public static final float component3(long $receiver) {
        return Color.blue($receiver);
    }

    public static final float component4(long $receiver) {
        return Color.alpha($receiver);
    }

    public static final float getAlpha(long $receiver) {
        return Color.alpha($receiver);
    }

    public static final float getRed(long $receiver) {
        return Color.red($receiver);
    }

    public static final float getGreen(long $receiver) {
        return Color.green($receiver);
    }

    public static final float getBlue(long $receiver) {
        return Color.blue($receiver);
    }

    public static final float getLuminance(long $receiver) {
        return Color.luminance($receiver);
    }

    public static final Color toColor(long $receiver) {
        Color valueOf = Color.valueOf($receiver);
        Intrinsics.checkExpressionValueIsNotNull(valueOf, "Color.valueOf(this)");
        return valueOf;
    }

    public static final int toColorInt(long $receiver) {
        return Color.toArgb($receiver);
    }

    public static final boolean isSrgb(long $receiver) {
        return Color.isSrgb($receiver);
    }

    public static final boolean isWideGamut(long $receiver) {
        return Color.isWideGamut($receiver);
    }

    public static final ColorSpace getColorSpace(long $receiver) {
        ColorSpace colorSpace = Color.colorSpace($receiver);
        Intrinsics.checkExpressionValueIsNotNull(colorSpace, "Color.colorSpace(this)");
        return colorSpace;
    }

    public static final int toColorInt(String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        return Color.parseColor($receiver);
    }
}
