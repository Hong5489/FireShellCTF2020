package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, mo6929d2 = {"<anonymous>", "", "it", "invoke"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* compiled from: Indent.kt */
final class StringsKt__IndentKt$prependIndent$1 extends Lambda implements Function1<String, String> {
    final /* synthetic */ String $indent;

    StringsKt__IndentKt$prependIndent$1(String str) {
        this.$indent = str;
        super(1);
    }

    public final String invoke(String it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        if (!StringsKt.isBlank(it)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.$indent);
            sb.append(it);
            return sb.toString();
        } else if (it.length() < this.$indent.length()) {
            return this.$indent;
        } else {
            return it;
        }
    }
}
