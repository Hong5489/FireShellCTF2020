package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.ImageView;
import android.widget.RemoteViews;
import androidx.core.internal.view.SupportMenu;
import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

public class Picasso {
    static final Handler HANDLER = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 3) {
                Action action = (Action) msg.obj;
                if (action.getPicasso().loggingEnabled) {
                    Utils.log("Main", "canceled", action.request.logId(), "target got garbage collected");
                }
                action.picasso.cancelExistingRequest(action.getTarget());
            } else if (i == 8) {
                List<BitmapHunter> batch = (List) msg.obj;
                int n = batch.size();
                for (int i2 = 0; i2 < n; i2++) {
                    BitmapHunter hunter = (BitmapHunter) batch.get(i2);
                    hunter.picasso.complete(hunter);
                }
            } else if (i == 13) {
                List<Action> batch2 = (List) msg.obj;
                int n2 = batch2.size();
                for (int i3 = 0; i3 < n2; i3++) {
                    Action action2 = (Action) batch2.get(i3);
                    action2.picasso.resumeAction(action2);
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown handler message received: ");
                sb.append(msg.what);
                throw new AssertionError(sb.toString());
            }
        }
    };
    static final String TAG = "Picasso";
    static volatile Picasso singleton = null;
    final Cache cache;
    private final CleanupThread cleanupThread;
    final Context context;
    final Config defaultBitmapConfig;
    final Dispatcher dispatcher;
    boolean indicatorsEnabled;
    private final Listener listener;
    volatile boolean loggingEnabled;
    final ReferenceQueue<Object> referenceQueue;
    private final List<RequestHandler> requestHandlers;
    private final RequestTransformer requestTransformer;
    boolean shutdown;
    final Stats stats;
    final Map<Object, Action> targetToAction;
    final Map<ImageView, DeferredRequestCreator> targetToDeferredRequestCreator;

    public static class Builder {
        private Cache cache;
        private final Context context;
        private Config defaultBitmapConfig;
        private Downloader downloader;
        private boolean indicatorsEnabled;
        private Listener listener;
        private boolean loggingEnabled;
        private List<RequestHandler> requestHandlers;
        private ExecutorService service;
        private RequestTransformer transformer;

        public Builder(Context context2) {
            if (context2 != null) {
                this.context = context2.getApplicationContext();
                return;
            }
            throw new IllegalArgumentException("Context must not be null.");
        }

        public Builder defaultBitmapConfig(Config bitmapConfig) {
            if (bitmapConfig != null) {
                this.defaultBitmapConfig = bitmapConfig;
                return this;
            }
            throw new IllegalArgumentException("Bitmap config must not be null.");
        }

        public Builder downloader(Downloader downloader2) {
            if (downloader2 == null) {
                throw new IllegalArgumentException("Downloader must not be null.");
            } else if (this.downloader == null) {
                this.downloader = downloader2;
                return this;
            } else {
                throw new IllegalStateException("Downloader already set.");
            }
        }

        public Builder executor(ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException("Executor service must not be null.");
            } else if (this.service == null) {
                this.service = executorService;
                return this;
            } else {
                throw new IllegalStateException("Executor service already set.");
            }
        }

        public Builder memoryCache(Cache memoryCache) {
            if (memoryCache == null) {
                throw new IllegalArgumentException("Memory cache must not be null.");
            } else if (this.cache == null) {
                this.cache = memoryCache;
                return this;
            } else {
                throw new IllegalStateException("Memory cache already set.");
            }
        }

        public Builder listener(Listener listener2) {
            if (listener2 == null) {
                throw new IllegalArgumentException("Listener must not be null.");
            } else if (this.listener == null) {
                this.listener = listener2;
                return this;
            } else {
                throw new IllegalStateException("Listener already set.");
            }
        }

        public Builder requestTransformer(RequestTransformer transformer2) {
            if (transformer2 == null) {
                throw new IllegalArgumentException("Transformer must not be null.");
            } else if (this.transformer == null) {
                this.transformer = transformer2;
                return this;
            } else {
                throw new IllegalStateException("Transformer already set.");
            }
        }

        public Builder addRequestHandler(RequestHandler requestHandler) {
            if (requestHandler != null) {
                if (this.requestHandlers == null) {
                    this.requestHandlers = new ArrayList();
                }
                if (!this.requestHandlers.contains(requestHandler)) {
                    this.requestHandlers.add(requestHandler);
                    return this;
                }
                throw new IllegalStateException("RequestHandler already registered.");
            }
            throw new IllegalArgumentException("RequestHandler must not be null.");
        }

        public Builder indicatorsEnabled(boolean enabled) {
            this.indicatorsEnabled = enabled;
            return this;
        }

        public Builder loggingEnabled(boolean enabled) {
            this.loggingEnabled = enabled;
            return this;
        }

        public Picasso build() {
            Context context2 = this.context;
            if (this.downloader == null) {
                this.downloader = new OkHttp3Downloader(context2);
            }
            if (this.cache == null) {
                this.cache = new LruCache(context2);
            }
            if (this.service == null) {
                this.service = new PicassoExecutorService();
            }
            if (this.transformer == null) {
                this.transformer = RequestTransformer.IDENTITY;
            }
            Stats stats = new Stats(this.cache);
            Dispatcher dispatcher = new Dispatcher(context2, this.service, Picasso.HANDLER, this.downloader, this.cache, stats);
            Context context3 = context2;
            Picasso picasso = new Picasso(context2, dispatcher, this.cache, this.listener, this.transformer, this.requestHandlers, stats, this.defaultBitmapConfig, this.indicatorsEnabled, this.loggingEnabled);
            return picasso;
        }
    }

    private static class CleanupThread extends Thread {
        private final Handler handler;
        private final ReferenceQueue<Object> referenceQueue;

        CleanupThread(ReferenceQueue<Object> referenceQueue2, Handler handler2) {
            this.referenceQueue = referenceQueue2;
            this.handler = handler2;
            setDaemon(true);
            setName("Picasso-refQueue");
        }

        public void run() {
            Process.setThreadPriority(10);
            while (true) {
                try {
                    RequestWeakReference<?> remove = (RequestWeakReference) this.referenceQueue.remove(1000);
                    Message message = this.handler.obtainMessage();
                    if (remove != null) {
                        message.what = 3;
                        message.obj = remove.action;
                        this.handler.sendMessage(message);
                    } else {
                        message.recycle();
                    }
                } catch (InterruptedException e) {
                    return;
                } catch (Exception e2) {
                    this.handler.post(new Runnable() {
                        public void run() {
                            throw new RuntimeException(e2);
                        }
                    });
                    return;
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void shutdown() {
            interrupt();
        }
    }

    public interface Listener {
        void onImageLoadFailed(Picasso picasso, Uri uri, Exception exc);
    }

    public enum LoadedFrom {
        MEMORY(-16711936),
        DISK(-16776961),
        NETWORK(SupportMenu.CATEGORY_MASK);
        
        final int debugColor;

        private LoadedFrom(int debugColor2) {
            this.debugColor = debugColor2;
        }
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH
    }

    public interface RequestTransformer {
        public static final RequestTransformer IDENTITY = new RequestTransformer() {
            public Request transformRequest(Request request) {
                return request;
            }
        };

        Request transformRequest(Request request);
    }

    Picasso(Context context2, Dispatcher dispatcher2, Cache cache2, Listener listener2, RequestTransformer requestTransformer2, List<RequestHandler> extraRequestHandlers, Stats stats2, Config defaultBitmapConfig2, boolean indicatorsEnabled2, boolean loggingEnabled2) {
        Context context3 = context2;
        Dispatcher dispatcher3 = dispatcher2;
        List<RequestHandler> list = extraRequestHandlers;
        Stats stats3 = stats2;
        this.context = context3;
        this.dispatcher = dispatcher3;
        this.cache = cache2;
        this.listener = listener2;
        this.requestTransformer = requestTransformer2;
        this.defaultBitmapConfig = defaultBitmapConfig2;
        List<RequestHandler> allRequestHandlers = new ArrayList<>(7 + (list != null ? extraRequestHandlers.size() : 0));
        allRequestHandlers.add(new ResourceRequestHandler(context3));
        if (list != null) {
            allRequestHandlers.addAll(list);
        }
        allRequestHandlers.add(new ContactsPhotoRequestHandler(context3));
        allRequestHandlers.add(new MediaStoreRequestHandler(context3));
        allRequestHandlers.add(new ContentStreamRequestHandler(context3));
        allRequestHandlers.add(new AssetRequestHandler(context3));
        allRequestHandlers.add(new FileRequestHandler(context3));
        allRequestHandlers.add(new NetworkRequestHandler(dispatcher3.downloader, stats3));
        this.requestHandlers = Collections.unmodifiableList(allRequestHandlers);
        this.stats = stats3;
        this.targetToAction = new WeakHashMap();
        this.targetToDeferredRequestCreator = new WeakHashMap();
        this.indicatorsEnabled = indicatorsEnabled2;
        this.loggingEnabled = loggingEnabled2;
        this.referenceQueue = new ReferenceQueue<>();
        this.cleanupThread = new CleanupThread(this.referenceQueue, HANDLER);
        this.cleanupThread.start();
    }

    public void cancelRequest(ImageView view) {
        if (view != null) {
            cancelExistingRequest(view);
            return;
        }
        throw new IllegalArgumentException("view cannot be null.");
    }

    public void cancelRequest(Target target) {
        if (target != null) {
            cancelExistingRequest(target);
            return;
        }
        throw new IllegalArgumentException("target cannot be null.");
    }

    public void cancelRequest(RemoteViews remoteViews, int viewId) {
        if (remoteViews != null) {
            cancelExistingRequest(new RemoteViewsTarget(remoteViews, viewId));
            return;
        }
        throw new IllegalArgumentException("remoteViews cannot be null.");
    }

    public void cancelTag(Object tag) {
        Utils.checkMain();
        if (tag != null) {
            List<Action> actions = new ArrayList<>(this.targetToAction.values());
            int n = actions.size();
            for (int i = 0; i < n; i++) {
                Action action = (Action) actions.get(i);
                if (tag.equals(action.getTag())) {
                    cancelExistingRequest(action.getTarget());
                }
            }
            List<DeferredRequestCreator> deferredRequestCreators = new ArrayList<>(this.targetToDeferredRequestCreator.values());
            int n2 = deferredRequestCreators.size();
            for (int i2 = 0; i2 < n2; i2++) {
                DeferredRequestCreator deferredRequestCreator = (DeferredRequestCreator) deferredRequestCreators.get(i2);
                if (tag.equals(deferredRequestCreator.getTag())) {
                    deferredRequestCreator.cancel();
                }
            }
            return;
        }
        throw new IllegalArgumentException("Cannot cancel requests with null tag.");
    }

    public void pauseTag(Object tag) {
        if (tag != null) {
            this.dispatcher.dispatchPauseTag(tag);
            return;
        }
        throw new IllegalArgumentException("tag == null");
    }

    public void resumeTag(Object tag) {
        if (tag != null) {
            this.dispatcher.dispatchResumeTag(tag);
            return;
        }
        throw new IllegalArgumentException("tag == null");
    }

    public RequestCreator load(Uri uri) {
        return new RequestCreator(this, uri, 0);
    }

    public RequestCreator load(String path) {
        if (path == null) {
            return new RequestCreator(this, null, 0);
        }
        if (path.trim().length() != 0) {
            return load(Uri.parse(path));
        }
        throw new IllegalArgumentException("Path must not be empty.");
    }

    public RequestCreator load(File file) {
        if (file == null) {
            return new RequestCreator(this, null, 0);
        }
        return load(Uri.fromFile(file));
    }

    public RequestCreator load(int resourceId) {
        if (resourceId != 0) {
            return new RequestCreator(this, null, resourceId);
        }
        throw new IllegalArgumentException("Resource ID must not be zero.");
    }

    public void invalidate(Uri uri) {
        if (uri != null) {
            this.cache.clearKeyUri(uri.toString());
        }
    }

    public void invalidate(String path) {
        if (path != null) {
            invalidate(Uri.parse(path));
        }
    }

    public void invalidate(File file) {
        if (file != null) {
            invalidate(Uri.fromFile(file));
            return;
        }
        throw new IllegalArgumentException("file == null");
    }

    public void setIndicatorsEnabled(boolean enabled) {
        this.indicatorsEnabled = enabled;
    }

    public boolean areIndicatorsEnabled() {
        return this.indicatorsEnabled;
    }

    public void setLoggingEnabled(boolean enabled) {
        this.loggingEnabled = enabled;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public StatsSnapshot getSnapshot() {
        return this.stats.createSnapshot();
    }

    public void shutdown() {
        if (this == singleton) {
            throw new UnsupportedOperationException("Default singleton instance cannot be shutdown.");
        } else if (!this.shutdown) {
            this.cache.clear();
            this.cleanupThread.shutdown();
            this.stats.shutdown();
            this.dispatcher.shutdown();
            for (DeferredRequestCreator deferredRequestCreator : this.targetToDeferredRequestCreator.values()) {
                deferredRequestCreator.cancel();
            }
            this.targetToDeferredRequestCreator.clear();
            this.shutdown = true;
        }
    }

    /* access modifiers changed from: 0000 */
    public List<RequestHandler> getRequestHandlers() {
        return this.requestHandlers;
    }

    /* access modifiers changed from: 0000 */
    public Request transformRequest(Request request) {
        Request transformed = this.requestTransformer.transformRequest(request);
        if (transformed != null) {
            return transformed;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Request transformer ");
        sb.append(this.requestTransformer.getClass().getCanonicalName());
        sb.append(" returned null for ");
        sb.append(request);
        throw new IllegalStateException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public void defer(ImageView view, DeferredRequestCreator request) {
        if (this.targetToDeferredRequestCreator.containsKey(view)) {
            cancelExistingRequest(view);
        }
        this.targetToDeferredRequestCreator.put(view, request);
    }

    /* access modifiers changed from: 0000 */
    public void enqueueAndSubmit(Action action) {
        Object target = action.getTarget();
        if (!(target == null || this.targetToAction.get(target) == action)) {
            cancelExistingRequest(target);
            this.targetToAction.put(target, action);
        }
        submit(action);
    }

    /* access modifiers changed from: 0000 */
    public void submit(Action action) {
        this.dispatcher.dispatchSubmit(action);
    }

    /* access modifiers changed from: 0000 */
    public Bitmap quickMemoryCacheCheck(String key) {
        Bitmap cached = this.cache.get(key);
        if (cached != null) {
            this.stats.dispatchCacheHit();
        } else {
            this.stats.dispatchCacheMiss();
        }
        return cached;
    }

    /* access modifiers changed from: 0000 */
    public void complete(BitmapHunter hunter) {
        Action single = hunter.getAction();
        List<Action> joined = hunter.getActions();
        boolean shouldDeliver = false;
        boolean hasMultiple = joined != null && !joined.isEmpty();
        if (single != null || hasMultiple) {
            shouldDeliver = true;
        }
        if (shouldDeliver) {
            Uri uri = hunter.getData().uri;
            Exception exception = hunter.getException();
            Bitmap result = hunter.getResult();
            LoadedFrom from = hunter.getLoadedFrom();
            if (single != null) {
                deliverAction(result, from, single, exception);
            }
            if (hasMultiple) {
                int n = joined.size();
                for (int i = 0; i < n; i++) {
                    deliverAction(result, from, (Action) joined.get(i), exception);
                }
            }
            Listener listener2 = this.listener;
            if (!(listener2 == null || exception == null)) {
                listener2.onImageLoadFailed(this, uri, exception);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void resumeAction(Action action) {
        Bitmap bitmap = null;
        if (MemoryPolicy.shouldReadFromMemoryCache(action.memoryPolicy)) {
            bitmap = quickMemoryCacheCheck(action.getKey());
        }
        String str = "Main";
        if (bitmap != null) {
            deliverAction(bitmap, LoadedFrom.MEMORY, action, null);
            if (this.loggingEnabled) {
                String logId = action.request.logId();
                StringBuilder sb = new StringBuilder();
                sb.append("from ");
                sb.append(LoadedFrom.MEMORY);
                Utils.log(str, "completed", logId, sb.toString());
                return;
            }
            return;
        }
        enqueueAndSubmit(action);
        if (this.loggingEnabled) {
            Utils.log(str, "resumed", action.request.logId());
        }
    }

    private void deliverAction(Bitmap result, LoadedFrom from, Action action, Exception e) {
        if (!action.isCancelled()) {
            if (!action.willReplay()) {
                this.targetToAction.remove(action.getTarget());
            }
            String str = "Main";
            if (result == null) {
                action.error(e);
                if (this.loggingEnabled) {
                    Utils.log(str, "errored", action.request.logId(), e.getMessage());
                }
            } else if (from != null) {
                action.complete(result, from);
                if (this.loggingEnabled) {
                    String logId = action.request.logId();
                    StringBuilder sb = new StringBuilder();
                    sb.append("from ");
                    sb.append(from);
                    Utils.log(str, "completed", logId, sb.toString());
                }
            } else {
                throw new AssertionError("LoadedFrom cannot be null.");
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void cancelExistingRequest(Object target) {
        Utils.checkMain();
        Action action = (Action) this.targetToAction.remove(target);
        if (action != null) {
            action.cancel();
            this.dispatcher.dispatchCancel(action);
        }
        if (target instanceof ImageView) {
            DeferredRequestCreator deferredRequestCreator = (DeferredRequestCreator) this.targetToDeferredRequestCreator.remove((ImageView) target);
            if (deferredRequestCreator != null) {
                deferredRequestCreator.cancel();
            }
        }
    }

    public static Picasso get() {
        if (singleton == null) {
            synchronized (Picasso.class) {
                if (singleton == null) {
                    if (PicassoProvider.context != null) {
                        singleton = new Builder(PicassoProvider.context).build();
                    } else {
                        throw new IllegalStateException("context == null");
                    }
                }
            }
        }
        return singleton;
    }

    public static void setSingletonInstance(Picasso picasso) {
        if (picasso != null) {
            synchronized (Picasso.class) {
                if (singleton == null) {
                    singleton = picasso;
                } else {
                    throw new IllegalStateException("Singleton instance already exists.");
                }
            }
            return;
        }
        throw new IllegalArgumentException("Picasso must not be null.");
    }
}
