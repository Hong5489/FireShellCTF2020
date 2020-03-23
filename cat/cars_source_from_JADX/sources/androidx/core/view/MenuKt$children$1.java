package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.sequences.Sequence;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010)\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u000f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005H\u0002¨\u0006\u0006"}, mo6929d2 = {"androidx/core/view/MenuKt$children$1", "Lkotlin/sequences/Sequence;", "Landroid/view/MenuItem;", "(Landroid/view/Menu;)V", "iterator", "", "core-ktx_release"}, mo6930k = 1, mo6931mv = {1, 1, 10})
/* compiled from: Menu.kt */
public final class MenuKt$children$1 implements Sequence<MenuItem> {
    final /* synthetic */ Menu receiver$0;

    MenuKt$children$1(Menu $receiver) {
        this.receiver$0 = $receiver;
    }

    public Iterator<MenuItem> iterator() {
        return MenuKt.iterator(this.receiver$0);
    }
}
