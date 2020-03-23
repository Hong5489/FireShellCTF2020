package androidx.core.view;

import android.view.Menu;
import android.view.MenuItem;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMutableIterator;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000%\n\u0000\n\u0002\u0010)\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\t\u0010\u0006\u001a\u00020\u0007H\u0002J\t\u0010\b\u001a\u00020\u0002H\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"}, mo6929d2 = {"androidx/core/view/MenuKt$iterator$1", "", "Landroid/view/MenuItem;", "(Landroid/view/Menu;)V", "index", "", "hasNext", "", "next", "remove", "", "core-ktx_release"}, mo6930k = 1, mo6931mv = {1, 1, 10})
/* compiled from: Menu.kt */
public final class MenuKt$iterator$1 implements Iterator<MenuItem>, KMutableIterator {
    private int index;
    final /* synthetic */ Menu receiver$0;

    MenuKt$iterator$1(Menu $receiver) {
        this.receiver$0 = $receiver;
    }

    public boolean hasNext() {
        return this.index < this.receiver$0.size();
    }

    public MenuItem next() {
        Menu menu = this.receiver$0;
        int i = this.index;
        this.index = i + 1;
        MenuItem item = menu.getItem(i);
        if (item != null) {
            return item;
        }
        throw new IndexOutOfBoundsException();
    }

    public void remove() {
        Menu menu = this.receiver$0;
        this.index--;
        menu.removeItem(this.index);
    }
}
