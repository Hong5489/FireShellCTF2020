package com.squareup.picasso;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

class Dispatcher {
    static final int AIRPLANE_MODE_CHANGE = 10;
    private static final int AIRPLANE_MODE_OFF = 0;
    private static final int AIRPLANE_MODE_ON = 1;
    private static final int BATCH_DELAY = 200;
    private static final String DISPATCHER_THREAD_NAME = "Dispatcher";
    static final int HUNTER_BATCH_COMPLETE = 8;
    static final int HUNTER_COMPLETE = 4;
    static final int HUNTER_DECODE_FAILED = 6;
    static final int HUNTER_DELAY_NEXT_BATCH = 7;
    static final int HUNTER_RETRY = 5;
    static final int NETWORK_STATE_CHANGE = 9;
    static final int REQUEST_BATCH_RESUME = 13;
    static final int REQUEST_CANCEL = 2;
    static final int REQUEST_GCED = 3;
    static final int REQUEST_SUBMIT = 1;
    private static final int RETRY_DELAY = 500;
    static final int TAG_PAUSE = 11;
    static final int TAG_RESUME = 12;
    boolean airplaneMode;
    final List<BitmapHunter> batch;
    final Cache cache;
    final Context context;
    final DispatcherThread dispatcherThread = new DispatcherThread();
    final Downloader downloader;
    final Map<Object, Action> failedActions;
    final Handler handler;
    final Map<String, BitmapHunter> hunterMap;
    final Handler mainThreadHandler;
    final Map<Object, Action> pausedActions;
    final Set<Object> pausedTags;
    final NetworkBroadcastReceiver receiver;
    final boolean scansNetworkChanges;
    final ExecutorService service;
    final Stats stats;

    private static class DispatcherHandler extends Handler {
        private final Dispatcher dispatcher;

        DispatcherHandler(Looper looper, Dispatcher dispatcher2) {
            super(looper);
            this.dispatcher = dispatcher2;
        }

        public void handleMessage(final Message msg) {
            boolean z = false;
            switch (msg.what) {
                case 1:
                    this.dispatcher.performSubmit((Action) msg.obj);
                    return;
                case 2:
                    this.dispatcher.performCancel((Action) msg.obj);
                    return;
                case 4:
                    this.dispatcher.performComplete((BitmapHunter) msg.obj);
                    return;
                case 5:
                    this.dispatcher.performRetry((BitmapHunter) msg.obj);
                    return;
                case 6:
                    this.dispatcher.performError((BitmapHunter) msg.obj, false);
                    return;
                case 7:
                    this.dispatcher.performBatchComplete();
                    return;
                case 9:
                    this.dispatcher.performNetworkStateChange((NetworkInfo) msg.obj);
                    return;
                case 10:
                    Dispatcher dispatcher2 = this.dispatcher;
                    if (msg.arg1 == 1) {
                        z = true;
                    }
                    dispatcher2.performAirplaneModeChange(z);
                    return;
                case 11:
                    this.dispatcher.performPauseTag(msg.obj);
                    return;
                case 12:
                    this.dispatcher.performResumeTag(msg.obj);
                    return;
                default:
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Unknown handler message received: ");
                            sb.append(msg.what);
                            throw new AssertionError(sb.toString());
                        }
                    });
                    return;
            }
        }
    }

    static class DispatcherThread extends HandlerThread {
        DispatcherThread() {
            super("Picasso-Dispatcher", 10);
        }
    }

    static class NetworkBroadcastReceiver extends BroadcastReceiver {
        static final String EXTRA_AIRPLANE_STATE = "state";
        private final Dispatcher dispatcher;

        NetworkBroadcastReceiver(Dispatcher dispatcher2) {
            this.dispatcher = dispatcher2;
        }

        /* access modifiers changed from: 0000 */
        public void register() {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.AIRPLANE_MODE");
            if (this.dispatcher.scansNetworkChanges) {
                filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            }
            this.dispatcher.context.registerReceiver(this, filter);
        }

        /* access modifiers changed from: 0000 */
        public void unregister() {
            this.dispatcher.context.unregisterReceiver(this);
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                    String str = EXTRA_AIRPLANE_STATE;
                    if (intent.hasExtra(str)) {
                        this.dispatcher.dispatchAirplaneModeChange(intent.getBooleanExtra(str, false));
                    }
                } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                    this.dispatcher.dispatchNetworkStateChange(((ConnectivityManager) Utils.getService(context, "connectivity")).getActiveNetworkInfo());
                }
            }
        }
    }

    Dispatcher(Context context2, ExecutorService service2, Handler mainThreadHandler2, Downloader downloader2, Cache cache2, Stats stats2) {
        this.dispatcherThread.start();
        Utils.flushStackLocalLeaks(this.dispatcherThread.getLooper());
        this.context = context2;
        this.service = service2;
        this.hunterMap = new LinkedHashMap();
        this.failedActions = new WeakHashMap();
        this.pausedActions = new WeakHashMap();
        this.pausedTags = new LinkedHashSet();
        this.handler = new DispatcherHandler(this.dispatcherThread.getLooper(), this);
        this.downloader = downloader2;
        this.mainThreadHandler = mainThreadHandler2;
        this.cache = cache2;
        this.stats = stats2;
        this.batch = new ArrayList(4);
        this.airplaneMode = Utils.isAirplaneModeOn(this.context);
        this.scansNetworkChanges = Utils.hasPermission(context2, "android.permission.ACCESS_NETWORK_STATE");
        this.receiver = new NetworkBroadcastReceiver(this);
        this.receiver.register();
    }

    /* access modifiers changed from: 0000 */
    public void shutdown() {
        ExecutorService executorService = this.service;
        if (executorService instanceof PicassoExecutorService) {
            executorService.shutdown();
        }
        this.downloader.shutdown();
        this.dispatcherThread.quit();
        Picasso.HANDLER.post(new Runnable() {
            public void run() {
                Dispatcher.this.receiver.unregister();
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void dispatchSubmit(Action action) {
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(1, action));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchCancel(Action action) {
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(2, action));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchPauseTag(Object tag) {
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(11, tag));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchResumeTag(Object tag) {
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(12, tag));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchComplete(BitmapHunter hunter) {
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(4, hunter));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchRetry(BitmapHunter hunter) {
        Handler handler2 = this.handler;
        handler2.sendMessageDelayed(handler2.obtainMessage(5, hunter), 500);
    }

    /* access modifiers changed from: 0000 */
    public void dispatchFailed(BitmapHunter hunter) {
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(6, hunter));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchNetworkStateChange(NetworkInfo info) {
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(9, info));
    }

    /* access modifiers changed from: 0000 */
    public void dispatchAirplaneModeChange(boolean airplaneMode2) {
        Handler handler2 = this.handler;
        handler2.sendMessage(handler2.obtainMessage(10, airplaneMode2, 0));
    }

    /* access modifiers changed from: 0000 */
    public void performSubmit(Action action) {
        performSubmit(action, true);
    }

    /* access modifiers changed from: 0000 */
    public void performSubmit(Action action, boolean dismissFailed) {
        boolean contains = this.pausedTags.contains(action.getTag());
        String str = DISPATCHER_THREAD_NAME;
        if (contains) {
            this.pausedActions.put(action.getTarget(), action);
            if (action.getPicasso().loggingEnabled) {
                String logId = action.request.logId();
                StringBuilder sb = new StringBuilder();
                sb.append("because tag '");
                sb.append(action.getTag());
                sb.append("' is paused");
                Utils.log(str, "paused", logId, sb.toString());
            }
            return;
        }
        BitmapHunter hunter = (BitmapHunter) this.hunterMap.get(action.getKey());
        if (hunter != null) {
            hunter.attach(action);
        } else if (this.service.isShutdown()) {
            if (action.getPicasso().loggingEnabled) {
                Utils.log(str, "ignored", action.request.logId(), "because shut down");
            }
        } else {
            BitmapHunter hunter2 = BitmapHunter.forRequest(action.getPicasso(), this, this.cache, this.stats, action);
            hunter2.future = this.service.submit(hunter2);
            this.hunterMap.put(action.getKey(), hunter2);
            if (dismissFailed) {
                this.failedActions.remove(action.getTarget());
            }
            if (action.getPicasso().loggingEnabled) {
                Utils.log(str, "enqueued", action.request.logId());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void performCancel(Action action) {
        String key = action.getKey();
        BitmapHunter hunter = (BitmapHunter) this.hunterMap.get(key);
        String str = "canceled";
        String str2 = DISPATCHER_THREAD_NAME;
        if (hunter != null) {
            hunter.detach(action);
            if (hunter.cancel()) {
                this.hunterMap.remove(key);
                if (action.getPicasso().loggingEnabled) {
                    Utils.log(str2, str, action.getRequest().logId());
                }
            }
        }
        if (this.pausedTags.contains(action.getTag())) {
            this.pausedActions.remove(action.getTarget());
            if (action.getPicasso().loggingEnabled) {
                Utils.log(str2, str, action.getRequest().logId(), "because paused request got canceled");
            }
        }
        Action remove = (Action) this.failedActions.remove(action.getTarget());
        if (remove != null && remove.getPicasso().loggingEnabled) {
            Utils.log(str2, str, remove.getRequest().logId(), "from replaying");
        }
    }

    /* access modifiers changed from: 0000 */
    public void performPauseTag(Object tag) {
        if (this.pausedTags.add(tag)) {
            Iterator<BitmapHunter> it = this.hunterMap.values().iterator();
            while (it.hasNext()) {
                BitmapHunter hunter = (BitmapHunter) it.next();
                boolean loggingEnabled = hunter.getPicasso().loggingEnabled;
                Action single = hunter.getAction();
                List<Action> joined = hunter.getActions();
                boolean hasMultiple = joined != null && !joined.isEmpty();
                if (single != null || hasMultiple) {
                    String str = "' was paused";
                    String str2 = "because tag '";
                    String str3 = "paused";
                    String str4 = DISPATCHER_THREAD_NAME;
                    if (single != null && single.getTag().equals(tag)) {
                        hunter.detach(single);
                        this.pausedActions.put(single.getTarget(), single);
                        if (loggingEnabled) {
                            String logId = single.request.logId();
                            StringBuilder sb = new StringBuilder();
                            sb.append(str2);
                            sb.append(tag);
                            sb.append(str);
                            Utils.log(str4, str3, logId, sb.toString());
                        }
                    }
                    if (hasMultiple) {
                        for (int i = joined.size() - 1; i >= 0; i--) {
                            Action action = (Action) joined.get(i);
                            if (action.getTag().equals(tag)) {
                                hunter.detach(action);
                                this.pausedActions.put(action.getTarget(), action);
                                if (loggingEnabled) {
                                    String logId2 = action.request.logId();
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(str2);
                                    sb2.append(tag);
                                    sb2.append(str);
                                    Utils.log(str4, str3, logId2, sb2.toString());
                                }
                            }
                        }
                    }
                    if (hunter.cancel()) {
                        it.remove();
                        if (loggingEnabled) {
                            Utils.log(str4, "canceled", Utils.getLogIdsForHunter(hunter), "all actions paused");
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void performResumeTag(Object tag) {
        if (this.pausedTags.remove(tag)) {
            List<Action> batch2 = null;
            Iterator<Action> i = this.pausedActions.values().iterator();
            while (i.hasNext()) {
                Action action = (Action) i.next();
                if (action.getTag().equals(tag)) {
                    if (batch2 == null) {
                        batch2 = new ArrayList<>();
                    }
                    batch2.add(action);
                    i.remove();
                }
            }
            if (batch2 != null) {
                Handler handler2 = this.mainThreadHandler;
                handler2.sendMessage(handler2.obtainMessage(13, batch2));
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void performRetry(BitmapHunter hunter) {
        if (!hunter.isCancelled()) {
            boolean willReplay = false;
            if (this.service.isShutdown()) {
                performError(hunter, false);
                return;
            }
            NetworkInfo networkInfo = null;
            if (this.scansNetworkChanges) {
                networkInfo = ((ConnectivityManager) Utils.getService(this.context, "connectivity")).getActiveNetworkInfo();
            }
            if (hunter.shouldRetry(this.airplaneMode, networkInfo)) {
                if (hunter.getPicasso().loggingEnabled) {
                    Utils.log(DISPATCHER_THREAD_NAME, "retrying", Utils.getLogIdsForHunter(hunter));
                }
                if (hunter.getException() instanceof ContentLengthException) {
                    hunter.networkPolicy |= NetworkPolicy.NO_CACHE.index;
                }
                hunter.future = this.service.submit(hunter);
            } else {
                if (this.scansNetworkChanges && hunter.supportsReplay()) {
                    willReplay = true;
                }
                performError(hunter, willReplay);
                if (willReplay) {
                    markForReplay(hunter);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void performComplete(BitmapHunter hunter) {
        if (MemoryPolicy.shouldWriteToMemoryCache(hunter.getMemoryPolicy())) {
            this.cache.set(hunter.getKey(), hunter.getResult());
        }
        this.hunterMap.remove(hunter.getKey());
        batch(hunter);
        if (hunter.getPicasso().loggingEnabled) {
            Utils.log(DISPATCHER_THREAD_NAME, "batched", Utils.getLogIdsForHunter(hunter), "for completion");
        }
    }

    /* access modifiers changed from: 0000 */
    public void performBatchComplete() {
        List<BitmapHunter> copy = new ArrayList<>(this.batch);
        this.batch.clear();
        Handler handler2 = this.mainThreadHandler;
        handler2.sendMessage(handler2.obtainMessage(8, copy));
        logBatch(copy);
    }

    /* access modifiers changed from: 0000 */
    public void performError(BitmapHunter hunter, boolean willReplay) {
        if (hunter.getPicasso().loggingEnabled) {
            String logIdsForHunter = Utils.getLogIdsForHunter(hunter);
            StringBuilder sb = new StringBuilder();
            sb.append("for error");
            sb.append(willReplay ? " (will replay)" : "");
            Utils.log(DISPATCHER_THREAD_NAME, "batched", logIdsForHunter, sb.toString());
        }
        this.hunterMap.remove(hunter.getKey());
        batch(hunter);
    }

    /* access modifiers changed from: 0000 */
    public void performAirplaneModeChange(boolean airplaneMode2) {
        this.airplaneMode = airplaneMode2;
    }

    /* access modifiers changed from: 0000 */
    public void performNetworkStateChange(NetworkInfo info) {
        ExecutorService executorService = this.service;
        if (executorService instanceof PicassoExecutorService) {
            ((PicassoExecutorService) executorService).adjustThreadCount(info);
        }
        if (info != null && info.isConnected()) {
            flushFailedActions();
        }
    }

    private void flushFailedActions() {
        if (!this.failedActions.isEmpty()) {
            Iterator<Action> iterator = this.failedActions.values().iterator();
            while (iterator.hasNext()) {
                Action action = (Action) iterator.next();
                iterator.remove();
                if (action.getPicasso().loggingEnabled) {
                    Utils.log(DISPATCHER_THREAD_NAME, "replaying", action.getRequest().logId());
                }
                performSubmit(action, false);
            }
        }
    }

    private void markForReplay(BitmapHunter hunter) {
        Action action = hunter.getAction();
        if (action != null) {
            markForReplay(action);
        }
        List<Action> joined = hunter.getActions();
        if (joined != null) {
            int n = joined.size();
            for (int i = 0; i < n; i++) {
                markForReplay((Action) joined.get(i));
            }
        }
    }

    private void markForReplay(Action action) {
        Object target = action.getTarget();
        if (target != null) {
            action.willReplay = true;
            this.failedActions.put(target, action);
        }
    }

    private void batch(BitmapHunter hunter) {
        if (!hunter.isCancelled()) {
            if (hunter.result != null) {
                hunter.result.prepareToDraw();
            }
            this.batch.add(hunter);
            if (!this.handler.hasMessages(7)) {
                this.handler.sendEmptyMessageDelayed(7, 200);
            }
        }
    }

    private void logBatch(List<BitmapHunter> copy) {
        if (copy != null && !copy.isEmpty() && ((BitmapHunter) copy.get(0)).getPicasso().loggingEnabled) {
            StringBuilder builder = new StringBuilder();
            for (BitmapHunter bitmapHunter : copy) {
                if (builder.length() > 0) {
                    builder.append(", ");
                }
                builder.append(Utils.getLogIdsForHunter(bitmapHunter));
            }
            Utils.log(DISPATCHER_THREAD_NAME, "delivered", builder.toString());
        }
    }
}
