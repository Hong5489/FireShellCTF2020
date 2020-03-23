package androidx.core.graphics;

import android.graphics.ImageDecoder;
import android.graphics.ImageDecoder.ImageInfo;
import android.graphics.ImageDecoder.OnHeaderDecodedListener;
import android.graphics.ImageDecoder.Source;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u00062\u000e\u0010\u0007\u001a\n \u0004*\u0004\u0018\u00010\b0\bH\n¢\u0006\u0002\b\t"}, mo6929d2 = {"<anonymous>", "", "decoder", "Landroid/graphics/ImageDecoder;", "kotlin.jvm.PlatformType", "info", "Landroid/graphics/ImageDecoder$ImageInfo;", "source", "Landroid/graphics/ImageDecoder$Source;", "onHeaderDecoded"}, mo6930k = 3, mo6931mv = {1, 1, 10})
/* compiled from: ImageDecoder.kt */
public final class ImageDecoderKt$decodeDrawable$1 implements OnHeaderDecodedListener {
    final /* synthetic */ Function3 $action;

    public ImageDecoderKt$decodeDrawable$1(Function3 function3) {
        this.$action = function3;
    }

    public final void onHeaderDecoded(ImageDecoder decoder, ImageInfo info, Source source) {
        Function3 function3 = this.$action;
        Intrinsics.checkExpressionValueIsNotNull(decoder, "decoder");
        Intrinsics.checkExpressionValueIsNotNull(info, "info");
        Intrinsics.checkExpressionValueIsNotNull(source, "source");
        function3.invoke(decoder, info, source);
    }
}