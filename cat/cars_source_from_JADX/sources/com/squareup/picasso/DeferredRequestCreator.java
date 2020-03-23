package com.squareup.picasso;

import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

class DeferredRequestCreator implements OnPreDrawListener, OnAttachStateChangeListener {
    Callback callback;
    private final RequestCreator creator;
    final WeakReference<ImageView> target;

    DeferredRequestCreator(RequestCreator creator2, ImageView target2, Callback callback2) {
        this.creator = creator2;
        this.target = new WeakReference<>(target2);
        this.callback = callback2;
        target2.addOnAttachStateChangeListener(this);
        if (target2.getWindowToken() != null) {
            onViewAttachedToWindow(target2);
        }
    }

    public void onViewAttachedToWindow(View view) {
        view.getViewTreeObserver().addOnPreDrawListener(this);
    }

    public void onViewDetachedFromWindow(View view) {
        view.getViewTreeObserver().removeOnPreDrawListener(this);
    }

    public boolean onPreDraw() {
        ImageView target2 = (ImageView) this.target.get();
        if (target2 == null) {
            return true;
        }
        ViewTreeObserver vto = target2.getViewTreeObserver();
        if (!vto.isAlive()) {
            return true;
        }
        int width = target2.getWidth();
        int height = target2.getHeight();
        if (width <= 0 || height <= 0) {
            return true;
        }
        target2.removeOnAttachStateChangeListener(this);
        vto.removeOnPreDrawListener(this);
        this.target.clear();
        this.creator.unfit().resize(width, height).into(target2, this.callback);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void cancel() {
        this.creator.clearTag();
        this.callback = null;
        ImageView target2 = (ImageView) this.target.get();
        if (target2 != null) {
            this.target.clear();
            target2.removeOnAttachStateChangeListener(this);
            ViewTreeObserver vto = target2.getViewTreeObserver();
            if (vto.isAlive()) {
                vto.removeOnPreDrawListener(this);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Object getTag() {
        return this.creator.getTag();
    }
}
