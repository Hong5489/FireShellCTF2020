package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.coroutines.experimental.CoroutineContext.Element;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\n¢\u0006\u0002\b\u0005"}, mo6929d2 = {"<anonymous>", "", "acc", "element", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "invoke"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* compiled from: CoroutineContextImpl.kt */
final class CombinedContext$toString$1 extends Lambda implements Function2<String, Element, String> {
    public static final CombinedContext$toString$1 INSTANCE = new CombinedContext$toString$1();

    CombinedContext$toString$1() {
        super(2);
    }

    public final String invoke(String acc, Element element) {
        Intrinsics.checkParameterIsNotNull(acc, "acc");
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (acc.length() == 0) {
            return element.toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(acc);
        sb.append(", ");
        sb.append(element);
        return sb.toString();
    }
}
