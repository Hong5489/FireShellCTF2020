package com.squareup.picasso;

import android.app.Notification;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Picasso.Priority;
import com.squareup.picasso.Request.Builder;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestCreator {
    private static final AtomicInteger nextId = new AtomicInteger();
    private final Builder data;
    private boolean deferred;
    private Drawable errorDrawable;
    private int errorResId;
    private int memoryPolicy;
    private int networkPolicy;
    private boolean noFade;
    private final Picasso picasso;
    private Drawable placeholderDrawable;
    private int placeholderResId;
    private boolean setPlaceholder;
    private Object tag;

    RequestCreator(Picasso picasso2, Uri uri, int resourceId) {
        this.setPlaceholder = true;
        if (!picasso2.shutdown) {
            this.picasso = picasso2;
            this.data = new Builder(uri, resourceId, picasso2.defaultBitmapConfig);
            return;
        }
        throw new IllegalStateException("Picasso instance already shut down. Cannot submit new requests.");
    }

    RequestCreator() {
        this.setPlaceholder = true;
        this.picasso = null;
        this.data = new Builder(null, 0, null);
    }

    public RequestCreator noPlaceholder() {
        if (this.placeholderResId != 0) {
            throw new IllegalStateException("Placeholder resource already set.");
        } else if (this.placeholderDrawable == null) {
            this.setPlaceholder = false;
            return this;
        } else {
            throw new IllegalStateException("Placeholder image already set.");
        }
    }

    public RequestCreator placeholder(int placeholderResId2) {
        if (!this.setPlaceholder) {
            throw new IllegalStateException("Already explicitly declared as no placeholder.");
        } else if (placeholderResId2 == 0) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        } else if (this.placeholderDrawable == null) {
            this.placeholderResId = placeholderResId2;
            return this;
        } else {
            throw new IllegalStateException("Placeholder image already set.");
        }
    }

    public RequestCreator placeholder(Drawable placeholderDrawable2) {
        if (!this.setPlaceholder) {
            throw new IllegalStateException("Already explicitly declared as no placeholder.");
        } else if (this.placeholderResId == 0) {
            this.placeholderDrawable = placeholderDrawable2;
            return this;
        } else {
            throw new IllegalStateException("Placeholder image already set.");
        }
    }

    public RequestCreator error(int errorResId2) {
        if (errorResId2 == 0) {
            throw new IllegalArgumentException("Error image resource invalid.");
        } else if (this.errorDrawable == null) {
            this.errorResId = errorResId2;
            return this;
        } else {
            throw new IllegalStateException("Error image already set.");
        }
    }

    public RequestCreator error(Drawable errorDrawable2) {
        if (errorDrawable2 == null) {
            throw new IllegalArgumentException("Error image may not be null.");
        } else if (this.errorResId == 0) {
            this.errorDrawable = errorDrawable2;
            return this;
        } else {
            throw new IllegalStateException("Error image already set.");
        }
    }

    public RequestCreator tag(Object tag2) {
        if (tag2 == null) {
            throw new IllegalArgumentException("Tag invalid.");
        } else if (this.tag == null) {
            this.tag = tag2;
            return this;
        } else {
            throw new IllegalStateException("Tag already set.");
        }
    }

    public RequestCreator fit() {
        this.deferred = true;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public RequestCreator unfit() {
        this.deferred = false;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public RequestCreator clearTag() {
        this.tag = null;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public Object getTag() {
        return this.tag;
    }

    public RequestCreator resizeDimen(int targetWidthResId, int targetHeightResId) {
        Resources resources = this.picasso.context.getResources();
        return resize(resources.getDimensionPixelSize(targetWidthResId), resources.getDimensionPixelSize(targetHeightResId));
    }

    public RequestCreator resize(int targetWidth, int targetHeight) {
        this.data.resize(targetWidth, targetHeight);
        return this;
    }

    public RequestCreator centerCrop() {
        this.data.centerCrop(17);
        return this;
    }

    public RequestCreator centerCrop(int alignGravity) {
        this.data.centerCrop(alignGravity);
        return this;
    }

    public RequestCreator centerInside() {
        this.data.centerInside();
        return this;
    }

    public RequestCreator onlyScaleDown() {
        this.data.onlyScaleDown();
        return this;
    }

    public RequestCreator rotate(float degrees) {
        this.data.rotate(degrees);
        return this;
    }

    public RequestCreator rotate(float degrees, float pivotX, float pivotY) {
        this.data.rotate(degrees, pivotX, pivotY);
        return this;
    }

    public RequestCreator config(Config config) {
        this.data.config(config);
        return this;
    }

    public RequestCreator stableKey(String stableKey) {
        this.data.stableKey(stableKey);
        return this;
    }

    public RequestCreator priority(Priority priority) {
        this.data.priority(priority);
        return this;
    }

    public RequestCreator transform(Transformation transformation) {
        this.data.transform(transformation);
        return this;
    }

    public RequestCreator transform(List<? extends Transformation> transformations) {
        this.data.transform(transformations);
        return this;
    }

    public RequestCreator memoryPolicy(MemoryPolicy policy, MemoryPolicy... additional) {
        String str = "Memory policy cannot be null.";
        if (policy != null) {
            this.memoryPolicy |= policy.index;
            if (additional != null) {
                if (additional.length > 0) {
                    int length = additional.length;
                    int i = 0;
                    while (i < length) {
                        MemoryPolicy memoryPolicy2 = additional[i];
                        if (memoryPolicy2 != null) {
                            this.memoryPolicy |= memoryPolicy2.index;
                            i++;
                        } else {
                            throw new IllegalArgumentException(str);
                        }
                    }
                }
                return this;
            }
            throw new IllegalArgumentException(str);
        }
        throw new IllegalArgumentException(str);
    }

    public RequestCreator networkPolicy(NetworkPolicy policy, NetworkPolicy... additional) {
        String str = "Network policy cannot be null.";
        if (policy != null) {
            this.networkPolicy |= policy.index;
            if (additional != null) {
                if (additional.length > 0) {
                    int length = additional.length;
                    int i = 0;
                    while (i < length) {
                        NetworkPolicy networkPolicy2 = additional[i];
                        if (networkPolicy2 != null) {
                            this.networkPolicy |= networkPolicy2.index;
                            i++;
                        } else {
                            throw new IllegalArgumentException(str);
                        }
                    }
                }
                return this;
            }
            throw new IllegalArgumentException(str);
        }
        throw new IllegalArgumentException(str);
    }

    public RequestCreator purgeable() {
        this.data.purgeable();
        return this;
    }

    public RequestCreator noFade() {
        this.noFade = true;
        return this;
    }

    public Bitmap get() throws IOException {
        long started = System.nanoTime();
        Utils.checkNotMain();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with get.");
        } else if (!this.data.hasImage()) {
            return null;
        } else {
            Request finalData = createRequest(started);
            Request request = finalData;
            GetAction getAction = new GetAction(this.picasso, request, this.memoryPolicy, this.networkPolicy, this.tag, Utils.createKey(finalData, new StringBuilder()));
            Picasso picasso2 = this.picasso;
            return BitmapHunter.forRequest(picasso2, picasso2.dispatcher, this.picasso.cache, this.picasso.stats, getAction).hunt();
        }
    }

    public void fetch() {
        fetch(null);
    }

    public void fetch(Callback callback) {
        long started = System.nanoTime();
        if (!this.deferred) {
            if (this.data.hasImage()) {
                if (!this.data.hasPriority()) {
                    this.data.priority(Priority.LOW);
                }
                Request request = createRequest(started);
                String key = Utils.createKey(request, new StringBuilder());
                if (!MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy) || this.picasso.quickMemoryCacheCheck(key) == null) {
                    FetchAction fetchAction = new FetchAction(this.picasso, request, this.memoryPolicy, this.networkPolicy, this.tag, key, callback);
                    this.picasso.submit(fetchAction);
                } else {
                    if (this.picasso.loggingEnabled) {
                        String plainId = request.plainId();
                        StringBuilder sb = new StringBuilder();
                        sb.append("from ");
                        sb.append(LoadedFrom.MEMORY);
                        Utils.log("Main", "completed", plainId, sb.toString());
                    }
                    if (callback != null) {
                        callback.onSuccess();
                    }
                    return;
                }
            }
            return;
        }
        throw new IllegalStateException("Fit cannot be used with fetch.");
    }

    public void into(Target target) {
        Target target2 = target;
        long started = System.nanoTime();
        Utils.checkMain();
        if (target2 == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (!this.deferred) {
            Drawable drawable = null;
            if (!this.data.hasImage()) {
                this.picasso.cancelRequest(target2);
                if (this.setPlaceholder) {
                    drawable = getPlaceholderDrawable();
                }
                target2.onPrepareLoad(drawable);
                return;
            }
            Request request = createRequest(started);
            String requestKey = Utils.createKey(request);
            if (MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy)) {
                Bitmap bitmap = this.picasso.quickMemoryCacheCheck(requestKey);
                if (bitmap != null) {
                    this.picasso.cancelRequest(target2);
                    target2.onBitmapLoaded(bitmap, LoadedFrom.MEMORY);
                    return;
                }
            }
            if (this.setPlaceholder) {
                drawable = getPlaceholderDrawable();
            }
            target2.onPrepareLoad(drawable);
            TargetAction targetAction = new TargetAction(this.picasso, target, request, this.memoryPolicy, this.networkPolicy, this.errorDrawable, requestKey, this.tag, this.errorResId);
            this.picasso.enqueueAndSubmit(targetAction);
        } else {
            throw new IllegalStateException("Fit cannot be used with a Target.");
        }
    }

    public void into(RemoteViews remoteViews, int viewId, int notificationId, Notification notification) {
        into(remoteViews, viewId, notificationId, notification, null);
    }

    public void into(RemoteViews remoteViews, int viewId, int notificationId, Notification notification, String notificationTag) {
        into(remoteViews, viewId, notificationId, notification, notificationTag, null);
    }

    public void into(RemoteViews remoteViews, int viewId, int notificationId, Notification notification, String notificationTag, Callback callback) {
        long started = System.nanoTime();
        if (remoteViews == null) {
            throw new IllegalArgumentException("RemoteViews must not be null.");
        } else if (notification == null) {
            throw new IllegalArgumentException("Notification must not be null.");
        } else if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with RemoteViews.");
        } else if (this.placeholderDrawable == null && this.placeholderResId == 0 && this.errorDrawable == null) {
            Request request = createRequest(started);
            String key = Utils.createKey(request, new StringBuilder());
            Request request2 = request;
            NotificationAction notificationAction = new NotificationAction(this.picasso, request, remoteViews, viewId, notificationId, notification, notificationTag, this.memoryPolicy, this.networkPolicy, key, this.tag, this.errorResId, callback);
            performRemoteViewInto(notificationAction);
        } else {
            throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
        }
    }

    public void into(RemoteViews remoteViews, int viewId, int[] appWidgetIds) {
        into(remoteViews, viewId, appWidgetIds, (Callback) null);
    }

    public void into(RemoteViews remoteViews, int viewId, int[] appWidgetIds, Callback callback) {
        long started = System.nanoTime();
        if (remoteViews == null) {
            throw new IllegalArgumentException("remoteViews must not be null.");
        } else if (appWidgetIds == null) {
            throw new IllegalArgumentException("appWidgetIds must not be null.");
        } else if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with remote views.");
        } else if (this.placeholderDrawable == null && this.placeholderResId == 0 && this.errorDrawable == null) {
            Request request = createRequest(started);
            AppWidgetAction appWidgetAction = new AppWidgetAction(this.picasso, request, remoteViews, viewId, appWidgetIds, this.memoryPolicy, this.networkPolicy, Utils.createKey(request, new StringBuilder()), this.tag, this.errorResId, callback);
            performRemoteViewInto(appWidgetAction);
        } else {
            throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
        }
    }

    public void into(ImageView target) {
        into(target, null);
    }

    public void into(ImageView target, Callback callback) {
        ImageView imageView = target;
        Callback callback2 = callback;
        long started = System.nanoTime();
        Utils.checkMain();
        if (imageView == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (!this.data.hasImage()) {
            this.picasso.cancelRequest(imageView);
            if (this.setPlaceholder) {
                PicassoDrawable.setPlaceholder(imageView, getPlaceholderDrawable());
            }
        } else {
            if (this.deferred) {
                if (!this.data.hasSize()) {
                    int width = target.getWidth();
                    int height = target.getHeight();
                    if (width == 0 || height == 0) {
                        if (this.setPlaceholder) {
                            PicassoDrawable.setPlaceholder(imageView, getPlaceholderDrawable());
                        }
                        this.picasso.defer(imageView, new DeferredRequestCreator(this, imageView, callback2));
                        return;
                    }
                    this.data.resize(width, height);
                } else {
                    throw new IllegalStateException("Fit cannot be used with resize.");
                }
            }
            Request request = createRequest(started);
            String requestKey = Utils.createKey(request);
            if (MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy)) {
                Bitmap bitmap = this.picasso.quickMemoryCacheCheck(requestKey);
                if (bitmap != null) {
                    this.picasso.cancelRequest(imageView);
                    PicassoDrawable.setBitmap(target, this.picasso.context, bitmap, LoadedFrom.MEMORY, this.noFade, this.picasso.indicatorsEnabled);
                    if (this.picasso.loggingEnabled) {
                        String plainId = request.plainId();
                        StringBuilder sb = new StringBuilder();
                        sb.append("from ");
                        sb.append(LoadedFrom.MEMORY);
                        Utils.log("Main", "completed", plainId, sb.toString());
                    }
                    if (callback2 != null) {
                        callback.onSuccess();
                    }
                    return;
                }
            }
            if (this.setPlaceholder) {
                PicassoDrawable.setPlaceholder(imageView, getPlaceholderDrawable());
            }
            Picasso picasso2 = this.picasso;
            int i = this.memoryPolicy;
            int i2 = this.networkPolicy;
            int i3 = this.errorResId;
            Drawable drawable = this.errorDrawable;
            Object obj = this.tag;
            Object obj2 = obj;
            String str = requestKey;
            long j = started;
            ImageViewAction imageViewAction = new ImageViewAction(picasso2, target, request, i, i2, i3, drawable, requestKey, obj2, callback, this.noFade);
            this.picasso.enqueueAndSubmit(imageViewAction);
        }
    }

    private Drawable getPlaceholderDrawable() {
        if (this.placeholderResId == 0) {
            return this.placeholderDrawable;
        }
        if (VERSION.SDK_INT >= 21) {
            return this.picasso.context.getDrawable(this.placeholderResId);
        }
        if (VERSION.SDK_INT >= 16) {
            return this.picasso.context.getResources().getDrawable(this.placeholderResId);
        }
        TypedValue value = new TypedValue();
        this.picasso.context.getResources().getValue(this.placeholderResId, value, true);
        return this.picasso.context.getResources().getDrawable(value.resourceId);
    }

    private Request createRequest(long started) {
        int id = nextId.getAndIncrement();
        Request request = this.data.build();
        request.f33id = id;
        request.started = started;
        boolean loggingEnabled = this.picasso.loggingEnabled;
        String str = "Main";
        if (loggingEnabled) {
            Utils.log(str, "created", request.plainId(), request.toString());
        }
        Request transformed = this.picasso.transformRequest(request);
        if (transformed != request) {
            transformed.f33id = id;
            transformed.started = started;
            if (loggingEnabled) {
                String logId = transformed.logId();
                StringBuilder sb = new StringBuilder();
                sb.append("into ");
                sb.append(transformed);
                Utils.log(str, "changed", logId, sb.toString());
            }
        }
        return transformed;
    }

    private void performRemoteViewInto(RemoteViewsAction action) {
        if (MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy)) {
            Bitmap bitmap = this.picasso.quickMemoryCacheCheck(action.getKey());
            if (bitmap != null) {
                action.complete(bitmap, LoadedFrom.MEMORY);
                return;
            }
        }
        int i = this.placeholderResId;
        if (i != 0) {
            action.setImageResource(i);
        }
        this.picasso.enqueueAndSubmit(action);
    }
}
