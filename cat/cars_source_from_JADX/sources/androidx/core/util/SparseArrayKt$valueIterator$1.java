package androidx.core.util;

import android.util.SparseArray;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u0000\u001d\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J\t\u0010\t\u001a\u00020\nH\u0002J\u0016\u0010\u000b\u001a\n \f*\u0004\u0018\u00018\u00008\u0000H\u0002¢\u0006\u0002\u0010\rR\u001a\u0010\u0003\u001a\u00020\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u000e"}, mo6929d2 = {"androidx/core/util/SparseArrayKt$valueIterator$1", "", "(Landroid/util/SparseArray;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "hasNext", "", "next", "kotlin.jvm.PlatformType", "()Ljava/lang/Object;", "core-ktx_release"}, mo6930k = 1, mo6931mv = {1, 1, 10})
/* compiled from: SparseArray.kt */
public final class SparseArrayKt$valueIterator$1 implements Iterator<T>, KMappedMarker {
    private int index;
    final /* synthetic */ SparseArray receiver$0;

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    SparseArrayKt$valueIterator$1(SparseArray<T> $receiver) {
        this.receiver$0 = $receiver;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int i) {
        this.index = i;
    }

    public boolean hasNext() {
        return this.index < this.receiver$0.size();
    }

    public T next() {
        SparseArray sparseArray = this.receiver$0;
        int i = this.index;
        this.index = i + 1;
        return sparseArray.valueAt(i);
    }
}
