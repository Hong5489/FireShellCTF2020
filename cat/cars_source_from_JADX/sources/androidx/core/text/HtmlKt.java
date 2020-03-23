package androidx.core.text;

import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.text.Spanned;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a/\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH\b\u001a\u0017\u0010\t\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u0004H\b¨\u0006\u000b"}, mo6929d2 = {"parseAsHtml", "Landroid/text/Spanned;", "", "flags", "", "imageGetter", "Landroid/text/Html$ImageGetter;", "tagHandler", "Landroid/text/Html$TagHandler;", "toHtml", "option", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: Html.kt */
public final class HtmlKt {
    public static /* bridge */ /* synthetic */ Spanned parseAsHtml$default(String $receiver, int flags, ImageGetter imageGetter, TagHandler tagHandler, int i, Object obj) {
        if ((i & 1) != 0) {
            flags = 0;
        }
        if ((i & 2) != 0) {
            imageGetter = null;
        }
        if ((i & 4) != 0) {
            tagHandler = null;
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Spanned fromHtml = HtmlCompat.fromHtml($receiver, flags, imageGetter, tagHandler);
        Intrinsics.checkExpressionValueIsNotNull(fromHtml, "HtmlCompat.fromHtml(this… imageGetter, tagHandler)");
        return fromHtml;
    }

    public static final Spanned parseAsHtml(String $receiver, int flags, ImageGetter imageGetter, TagHandler tagHandler) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Spanned fromHtml = HtmlCompat.fromHtml($receiver, flags, imageGetter, tagHandler);
        Intrinsics.checkExpressionValueIsNotNull(fromHtml, "HtmlCompat.fromHtml(this… imageGetter, tagHandler)");
        return fromHtml;
    }

    public static /* bridge */ /* synthetic */ String toHtml$default(Spanned $receiver, int option, int i, Object obj) {
        if ((i & 1) != 0) {
            option = 0;
        }
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        String html = HtmlCompat.toHtml($receiver, option);
        Intrinsics.checkExpressionValueIsNotNull(html, "HtmlCompat.toHtml(this, option)");
        return html;
    }

    public static final String toHtml(Spanned $receiver, int option) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        String html = HtmlCompat.toHtml($receiver, option);
        Intrinsics.checkExpressionValueIsNotNull(html, "HtmlCompat.toHtml(this, option)");
        return html;
    }
}
