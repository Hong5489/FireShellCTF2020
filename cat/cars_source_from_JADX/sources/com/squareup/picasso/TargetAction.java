package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.squareup.picasso.Picasso.LoadedFrom;

final class TargetAction extends Action<Target> {
    TargetAction(Picasso picasso, Target target, Request data, int memoryPolicy, int networkPolicy, Drawable errorDrawable, String key, Object tag, int errorResId) {
        super(picasso, target, data, memoryPolicy, networkPolicy, errorResId, errorDrawable, key, tag, false);
    }

    /* access modifiers changed from: 0000 */
    public void complete(Bitmap result, LoadedFrom from) {
        if (result != null) {
            Target target = (Target) getTarget();
            if (target != null) {
                target.onBitmapLoaded(result, from);
                if (result.isRecycled()) {
                    throw new IllegalStateException("Target callback must not recycle bitmap!");
                }
                return;
            }
            return;
        }
        throw new AssertionError(String.format("Attempted to complete action with no result!\n%s", new Object[]{this}));
    }

    /* access modifiers changed from: 0000 */
    public void error(Exception e) {
        Target target = (Target) getTarget();
        if (target == null) {
            return;
        }
        if (this.errorResId != 0) {
            target.onBitmapFailed(e, this.picasso.context.getResources().getDrawable(this.errorResId));
        } else {
            target.onBitmapFailed(e, this.errorDrawable);
        }
    }
}
