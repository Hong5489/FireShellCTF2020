package androidx.core.net;

import android.net.Uri;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\r\u0010\u0003\u001a\u00020\u0002*\u00020\u0001H\b\u001a\r\u0010\u0003\u001a\u00020\u0002*\u00020\u0004H\b¨\u0006\u0005"}, mo6929d2 = {"toFile", "Ljava/io/File;", "Landroid/net/Uri;", "toUri", "", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: Uri.kt */
public final class UriKt {
    public static final Uri toUri(String $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Uri parse = Uri.parse($receiver);
        Intrinsics.checkExpressionValueIsNotNull(parse, "Uri.parse(this)");
        return parse;
    }

    public static final Uri toUri(File $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        Uri fromFile = Uri.fromFile($receiver);
        Intrinsics.checkExpressionValueIsNotNull(fromFile, "Uri.fromFile(this)");
        return fromFile;
    }

    public static final File toFile(Uri $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if (Intrinsics.areEqual((Object) $receiver.getScheme(), (Object) "file")) {
            return new File($receiver.getPath());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Uri lacks 'file' scheme: ");
        sb.append($receiver);
        throw new IllegalArgumentException(sb.toString().toString());
    }
}
