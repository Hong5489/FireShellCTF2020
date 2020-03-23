package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Picasso.Priority;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

abstract class Action<T> {
    boolean cancelled;
    final Drawable errorDrawable;
    final int errorResId;
    final String key;
    final int memoryPolicy;
    final int networkPolicy;
    final boolean noFade;
    final Picasso picasso;
    final Request request;
    final Object tag;
    final WeakReference<T> target;
    boolean willReplay;

    static class RequestWeakReference<M> extends WeakReference<M> {
        final Action action;

        RequestWeakReference(Action action2, M referent, ReferenceQueue<? super M> q) {
            super(referent, q);
            this.action = action2;
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void complete(Bitmap bitmap, LoadedFrom loadedFrom);

    /* access modifiers changed from: 0000 */
    public abstract void error(Exception exc);

    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    Action(com.squareup.picasso.Picasso r3, T r4, com.squareup.picasso.Request r5, int r6, int r7, int r8, android.graphics.drawable.Drawable r9, java.lang.String r10, java.lang.Object r11, boolean r12) {
        /*
            r2 = this;
            r2.<init>()
            r2.picasso = r3
            r2.request = r5
            if (r4 != 0) goto L_0x000b
            r0 = 0
            goto L_0x0012
        L_0x000b:
            com.squareup.picasso.Action$RequestWeakReference r0 = new com.squareup.picasso.Action$RequestWeakReference
            java.lang.ref.ReferenceQueue<java.lang.Object> r1 = r3.referenceQueue
            r0.<init>(r2, r4, r1)
        L_0x0012:
            r2.target = r0
            r2.memoryPolicy = r6
            r2.networkPolicy = r7
            r2.noFade = r12
            r2.errorResId = r8
            r2.errorDrawable = r9
            r2.key = r10
            if (r11 == 0) goto L_0x0024
            r0 = r11
            goto L_0x0025
        L_0x0024:
            r0 = r2
        L_0x0025:
            r2.tag = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.picasso.Action.<init>(com.squareup.picasso.Picasso, java.lang.Object, com.squareup.picasso.Request, int, int, int, android.graphics.drawable.Drawable, java.lang.String, java.lang.Object, boolean):void");
    }

    /* access modifiers changed from: 0000 */
    public void cancel() {
        this.cancelled = true;
    }

    /* access modifiers changed from: 0000 */
    public Request getRequest() {
        return this.request;
    }

    /* access modifiers changed from: 0000 */
    public T getTarget() {
        WeakReference<T> weakReference = this.target;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    /* access modifiers changed from: 0000 */
    public String getKey() {
        return this.key;
    }

    /* access modifiers changed from: 0000 */
    public boolean isCancelled() {
        return this.cancelled;
    }

    /* access modifiers changed from: 0000 */
    public boolean willReplay() {
        return this.willReplay;
    }

    /* access modifiers changed from: 0000 */
    public int getMemoryPolicy() {
        return this.memoryPolicy;
    }

    /* access modifiers changed from: 0000 */
    public int getNetworkPolicy() {
        return this.networkPolicy;
    }

    /* access modifiers changed from: 0000 */
    public Picasso getPicasso() {
        return this.picasso;
    }

    /* access modifiers changed from: 0000 */
    public Priority getPriority() {
        return this.request.priority;
    }

    /* access modifiers changed from: 0000 */
    public Object getTag() {
        return this.tag;
    }
}
