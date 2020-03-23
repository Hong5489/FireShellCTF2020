package kotlin.p004io;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, mo6929d2 = {"<anonymous>", "", "it", "", "invoke"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* renamed from: kotlin.io.FilesKt__FileReadWriteKt$readLines$1 */
/* compiled from: FileReadWrite.kt */
final class FilesKt__FileReadWriteKt$readLines$1 extends Lambda implements Function1<String, Unit> {
    final /* synthetic */ ArrayList $result;

    FilesKt__FileReadWriteKt$readLines$1(ArrayList arrayList) {
        this.$result = arrayList;
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((String) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(String it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        this.$result.add(it);
    }
}
