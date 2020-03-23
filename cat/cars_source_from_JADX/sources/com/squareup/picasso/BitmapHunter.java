package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Picasso.Priority;
import com.squareup.picasso.RequestHandler.Result;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

class BitmapHunter implements Runnable {
    private static final Object DECODE_LOCK = new Object();
    private static final RequestHandler ERRORING_HANDLER = new RequestHandler() {
        public boolean canHandleRequest(Request data) {
            return true;
        }

        public Result load(Request request, int networkPolicy) throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append("Unrecognized type of request: ");
            sb.append(request);
            throw new IllegalStateException(sb.toString());
        }
    };
    private static final ThreadLocal<StringBuilder> NAME_BUILDER = new ThreadLocal<StringBuilder>() {
        /* access modifiers changed from: protected */
        public StringBuilder initialValue() {
            return new StringBuilder("Picasso-");
        }
    };
    private static final AtomicInteger SEQUENCE_GENERATOR = new AtomicInteger();
    Action action;
    List<Action> actions;
    final Cache cache;
    final Request data;
    final Dispatcher dispatcher;
    Exception exception;
    int exifOrientation;
    Future<?> future;
    final String key;
    LoadedFrom loadedFrom;
    final int memoryPolicy;
    int networkPolicy;
    final Picasso picasso;
    Priority priority;
    final RequestHandler requestHandler;
    Bitmap result;
    int retryCount;
    final int sequence = SEQUENCE_GENERATOR.incrementAndGet();
    final Stats stats;

    BitmapHunter(Picasso picasso2, Dispatcher dispatcher2, Cache cache2, Stats stats2, Action action2, RequestHandler requestHandler2) {
        this.picasso = picasso2;
        this.dispatcher = dispatcher2;
        this.cache = cache2;
        this.stats = stats2;
        this.action = action2;
        this.key = action2.getKey();
        this.data = action2.getRequest();
        this.priority = action2.getPriority();
        this.memoryPolicy = action2.getMemoryPolicy();
        this.networkPolicy = action2.getNetworkPolicy();
        this.requestHandler = requestHandler2;
        this.retryCount = requestHandler2.getRetryCount();
    }

    static Bitmap decodeStream(Source source, Request request) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(source);
        boolean isWebPFile = Utils.isWebPFile(bufferedSource);
        boolean isPurgeable = request.purgeable && VERSION.SDK_INT < 21;
        Options options = RequestHandler.createBitmapOptions(request);
        boolean calculateSize = RequestHandler.requiresInSampleSize(options);
        if (isWebPFile || isPurgeable) {
            byte[] bytes = bufferedSource.readByteArray();
            if (calculateSize) {
                BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, options, request);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        }
        InputStream stream = bufferedSource.inputStream();
        if (calculateSize) {
            MarkableInputStream markStream = new MarkableInputStream(stream);
            InputStream stream2 = markStream;
            markStream.allowMarksToExpire(false);
            long mark = markStream.savePosition(1024);
            BitmapFactory.decodeStream(stream2, null, options);
            RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, options, request);
            markStream.reset(mark);
            markStream.allowMarksToExpire(true);
            stream = stream2;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
        if (bitmap != null) {
            return bitmap;
        }
        throw new IOException("Failed to decode stream.");
    }

    public void run() {
        String str = "Picasso-Idle";
        try {
            updateThreadName(this.data);
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "executing", Utils.getLogIdsForHunter(this));
            }
            this.result = hunt();
            if (this.result == null) {
                this.dispatcher.dispatchFailed(this);
            } else {
                this.dispatcher.dispatchComplete(this);
            }
        } catch (ResponseException e) {
            if (!NetworkPolicy.isOfflineOnly(e.networkPolicy) || e.code != 504) {
                this.exception = e;
            }
            this.dispatcher.dispatchFailed(this);
        } catch (IOException e2) {
            this.exception = e2;
            this.dispatcher.dispatchRetry(this);
        } catch (OutOfMemoryError e3) {
            StringWriter writer = new StringWriter();
            this.stats.createSnapshot().dump(new PrintWriter(writer));
            this.exception = new RuntimeException(writer.toString(), e3);
            this.dispatcher.dispatchFailed(this);
        } catch (Exception e4) {
            this.exception = e4;
            this.dispatcher.dispatchFailed(this);
        } catch (Throwable th) {
            Thread.currentThread().setName(str);
            throw th;
        }
        Thread.currentThread().setName(str);
    }

    /* access modifiers changed from: 0000 */
    public Bitmap hunt() throws IOException {
        Bitmap bitmap = null;
        if (MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy)) {
            bitmap = this.cache.get(this.key);
            if (bitmap != null) {
                this.stats.dispatchCacheHit();
                this.loadedFrom = LoadedFrom.MEMORY;
                if (this.picasso.loggingEnabled) {
                    Utils.log("Hunter", "decoded", this.data.logId(), "from cache");
                }
                return bitmap;
            }
        }
        this.networkPolicy = this.retryCount == 0 ? NetworkPolicy.OFFLINE.index : this.networkPolicy;
        Result result2 = this.requestHandler.load(this.data, this.networkPolicy);
        if (result2 != null) {
            this.loadedFrom = result2.getLoadedFrom();
            this.exifOrientation = result2.getExifOrientation();
            bitmap = result2.getBitmap();
            if (bitmap == null) {
                Source source = result2.getSource();
                try {
                    bitmap = decodeStream(source, this.data);
                } finally {
                    try {
                        source.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        if (bitmap != null) {
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "decoded", this.data.logId());
            }
            this.stats.dispatchBitmapDecoded(bitmap);
            if (this.data.needsTransformation() || this.exifOrientation != 0) {
                synchronized (DECODE_LOCK) {
                    if (this.data.needsMatrixTransform() || this.exifOrientation != 0) {
                        bitmap = transformResult(this.data, bitmap, this.exifOrientation);
                        if (this.picasso.loggingEnabled) {
                            Utils.log("Hunter", "transformed", this.data.logId());
                        }
                    }
                    if (this.data.hasCustomTransformations()) {
                        bitmap = applyCustomTransformations(this.data.transformations, bitmap);
                        if (this.picasso.loggingEnabled) {
                            Utils.log("Hunter", "transformed", this.data.logId(), "from custom transformations");
                        }
                    }
                }
                if (bitmap != null) {
                    this.stats.dispatchBitmapTransformed(bitmap);
                }
            }
        }
        return bitmap;
    }

    /* access modifiers changed from: 0000 */
    public void attach(Action action2) {
        boolean loggingEnabled = this.picasso.loggingEnabled;
        Request request = action2.request;
        String str = "to ";
        String str2 = "joined";
        String str3 = "Hunter";
        if (this.action == null) {
            this.action = action2;
            if (loggingEnabled) {
                List<Action> list = this.actions;
                if (list == null || list.isEmpty()) {
                    Utils.log(str3, str2, request.logId(), "to empty hunter");
                } else {
                    Utils.log(str3, str2, request.logId(), Utils.getLogIdsForHunter(this, str));
                }
            }
            return;
        }
        if (this.actions == null) {
            this.actions = new ArrayList(3);
        }
        this.actions.add(action2);
        if (loggingEnabled) {
            Utils.log(str3, str2, request.logId(), Utils.getLogIdsForHunter(this, str));
        }
        Priority actionPriority = action2.getPriority();
        if (actionPriority.ordinal() > this.priority.ordinal()) {
            this.priority = actionPriority;
        }
    }

    /* access modifiers changed from: 0000 */
    public void detach(Action action2) {
        boolean detached = false;
        if (this.action == action2) {
            this.action = null;
            detached = true;
        } else {
            List<Action> list = this.actions;
            if (list != null) {
                detached = list.remove(action2);
            }
        }
        if (detached && action2.getPriority() == this.priority) {
            this.priority = computeNewPriority();
        }
        if (this.picasso.loggingEnabled) {
            Utils.log("Hunter", "removed", action2.request.logId(), Utils.getLogIdsForHunter(this, "from "));
        }
    }

    private Priority computeNewPriority() {
        Priority newPriority = Priority.LOW;
        List<Action> list = this.actions;
        boolean hasAny = false;
        boolean hasMultiple = list != null && !list.isEmpty();
        if (this.action != null || hasMultiple) {
            hasAny = true;
        }
        if (!hasAny) {
            return newPriority;
        }
        Action action2 = this.action;
        if (action2 != null) {
            newPriority = action2.getPriority();
        }
        if (hasMultiple) {
            int n = this.actions.size();
            for (int i = 0; i < n; i++) {
                Priority actionPriority = ((Action) this.actions.get(i)).getPriority();
                if (actionPriority.ordinal() > newPriority.ordinal()) {
                    newPriority = actionPriority;
                }
            }
        }
        return newPriority;
    }

    /* access modifiers changed from: 0000 */
    public boolean cancel() {
        if (this.action != null) {
            return false;
        }
        List<Action> list = this.actions;
        if (list != null && !list.isEmpty()) {
            return false;
        }
        Future<?> future2 = this.future;
        if (future2 == null || !future2.cancel(false)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean isCancelled() {
        Future<?> future2 = this.future;
        return future2 != null && future2.isCancelled();
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldRetry(boolean airplaneMode, NetworkInfo info) {
        if (!(this.retryCount > 0)) {
            return false;
        }
        this.retryCount--;
        return this.requestHandler.shouldRetry(airplaneMode, info);
    }

    /* access modifiers changed from: 0000 */
    public boolean supportsReplay() {
        return this.requestHandler.supportsReplay();
    }

    /* access modifiers changed from: 0000 */
    public Bitmap getResult() {
        return this.result;
    }

    /* access modifiers changed from: 0000 */
    public String getKey() {
        return this.key;
    }

    /* access modifiers changed from: 0000 */
    public int getMemoryPolicy() {
        return this.memoryPolicy;
    }

    /* access modifiers changed from: 0000 */
    public Request getData() {
        return this.data;
    }

    /* access modifiers changed from: 0000 */
    public Action getAction() {
        return this.action;
    }

    /* access modifiers changed from: 0000 */
    public Picasso getPicasso() {
        return this.picasso;
    }

    /* access modifiers changed from: 0000 */
    public List<Action> getActions() {
        return this.actions;
    }

    /* access modifiers changed from: 0000 */
    public Exception getException() {
        return this.exception;
    }

    /* access modifiers changed from: 0000 */
    public LoadedFrom getLoadedFrom() {
        return this.loadedFrom;
    }

    /* access modifiers changed from: 0000 */
    public Priority getPriority() {
        return this.priority;
    }

    static void updateThreadName(Request data2) {
        String name = data2.getName();
        StringBuilder builder = (StringBuilder) NAME_BUILDER.get();
        String str = "Picasso-";
        builder.ensureCapacity(str.length() + name.length());
        builder.replace(str.length(), builder.length(), name);
        Thread.currentThread().setName(builder.toString());
    }

    static BitmapHunter forRequest(Picasso picasso2, Dispatcher dispatcher2, Cache cache2, Stats stats2, Action action2) {
        Request request = action2.getRequest();
        List<RequestHandler> requestHandlers = picasso2.getRequestHandlers();
        int count = requestHandlers.size();
        for (int i = 0; i < count; i++) {
            RequestHandler requestHandler2 = (RequestHandler) requestHandlers.get(i);
            if (requestHandler2.canHandleRequest(request)) {
                BitmapHunter bitmapHunter = new BitmapHunter(picasso2, dispatcher2, cache2, stats2, action2, requestHandler2);
                return bitmapHunter;
            }
        }
        BitmapHunter bitmapHunter2 = new BitmapHunter(picasso2, dispatcher2, cache2, stats2, action2, ERRORING_HANDLER);
        return bitmapHunter2;
    }

    static Bitmap applyCustomTransformations(List<Transformation> transformations, Bitmap result2) {
        int i = 0;
        int count = transformations.size();
        while (i < count) {
            final Transformation transformation = (Transformation) transformations.get(i);
            try {
                Bitmap newResult = transformation.transform(result2);
                if (newResult == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Transformation ");
                    sb.append(transformation.key());
                    sb.append(" returned null after ");
                    sb.append(i);
                    final StringBuilder builder = sb.append(" previous transformation(s).\n\nTransformation list:\n");
                    for (Transformation t : transformations) {
                        builder.append(t.key());
                        builder.append(10);
                    }
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            throw new NullPointerException(builder.toString());
                        }
                    });
                    return null;
                } else if (newResult == result2 && result2.isRecycled()) {
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Transformation ");
                            sb.append(transformation.key());
                            sb.append(" returned input Bitmap but recycled it.");
                            throw new IllegalStateException(sb.toString());
                        }
                    });
                    return null;
                } else if (newResult == result2 || result2.isRecycled()) {
                    result2 = newResult;
                    i++;
                } else {
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Transformation ");
                            sb.append(transformation.key());
                            sb.append(" mutated input Bitmap but failed to recycle the original.");
                            throw new IllegalStateException(sb.toString());
                        }
                    });
                    return null;
                }
            } catch (RuntimeException e) {
                Picasso.HANDLER.post(new Runnable() {
                    public void run() {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Transformation ");
                        sb.append(transformation.key());
                        sb.append(" crashed with exception.");
                        throw new RuntimeException(sb.toString(), e);
                    }
                });
                return null;
            }
        }
        return result2;
    }

    static Bitmap transformResult(Request data2, Bitmap result2, int exifOrientation2) {
        int drawHeight;
        int drawWidth;
        int drawY;
        int drawX;
        Matrix matrix;
        Matrix matrix2;
        int inWidth;
        int inHeight;
        boolean onlyScaleDown;
        int targetWidth;
        int targetHeight;
        int exifRotation;
        int targetHeight2;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        int inHeight2;
        int inWidth2;
        float widthRatio;
        float f9;
        float f10;
        float scaleY;
        float scaleX;
        int drawX2;
        int drawY2;
        Request request = data2;
        int inWidth3 = result2.getWidth();
        int inHeight3 = result2.getHeight();
        boolean onlyScaleDown2 = request.onlyScaleDown;
        int drawWidth2 = inWidth3;
        int drawHeight2 = inHeight3;
        Matrix matrix3 = new Matrix();
        if (data2.needsMatrixTransform() || exifOrientation2 != 0) {
            int targetWidth2 = request.targetWidth;
            int targetHeight3 = request.targetHeight;
            float targetRotation = request.rotationDegrees;
            if (targetRotation != 0.0f) {
                double cosR = Math.cos(Math.toRadians((double) targetRotation));
                double sinR = Math.sin(Math.toRadians((double) targetRotation));
                drawX = 0;
                if (request.hasRotationPivot != 0) {
                    drawY = 0;
                    matrix3.setRotate(targetRotation, request.rotationPivotX, request.rotationPivotY);
                    drawWidth = drawWidth2;
                    drawHeight = drawHeight2;
                    double x1T = (((double) request.rotationPivotX) * (1.0d - cosR)) + (((double) request.rotationPivotY) * sinR);
                    int i = targetWidth2;
                    int i2 = targetHeight3;
                    double y1T = (((double) request.rotationPivotY) * (1.0d - cosR)) - (((double) request.rotationPivotX) * sinR);
                    onlyScaleDown = onlyScaleDown2;
                    inHeight = inHeight3;
                    inWidth = inWidth3;
                    double y2T = (((double) request.targetWidth) * sinR) + y1T;
                    matrix2 = matrix3;
                    double x3T = ((((double) request.targetWidth) * cosR) + x1T) - (((double) request.targetHeight) * sinR);
                    float f11 = targetRotation;
                    double y3T = (((double) request.targetWidth) * sinR) + y1T + (((double) request.targetHeight) * cosR);
                    double x4T = x1T - (((double) request.targetHeight) * sinR);
                    double d = sinR;
                    double d2 = cosR;
                    double cosR2 = (((double) request.targetWidth) * cosR) + x1T;
                    double y4T = (((double) request.targetHeight) * cosR) + y1T;
                    double maxX = Math.max(x4T, Math.max(x3T, Math.max(x1T, cosR2)));
                    double minX = Math.min(x4T, Math.min(x3T, Math.min(x1T, cosR2)));
                    double d3 = x3T;
                    double x3T2 = y2T;
                    double y2T2 = x1T;
                    double d4 = x4T;
                    double x4T2 = y4T;
                    double d5 = cosR2;
                    double d6 = x3T2;
                    targetWidth = (int) Math.floor(maxX - minX);
                    targetHeight = (int) Math.floor(Math.max(x4T2, Math.max(y3T, Math.max(y1T, x3T2))) - Math.min(x4T2, Math.min(y3T, Math.min(y1T, x3T2))));
                } else {
                    inWidth = inWidth3;
                    inHeight = inHeight3;
                    onlyScaleDown = onlyScaleDown2;
                    drawY = 0;
                    drawWidth = drawWidth2;
                    drawHeight = drawHeight2;
                    matrix2 = matrix3;
                    int i3 = targetWidth2;
                    int i4 = targetHeight3;
                    double cosR3 = cosR;
                    double sinR2 = sinR;
                    matrix2.setRotate(targetRotation);
                    double x2T = ((double) request.targetWidth) * cosR3;
                    double y2T3 = ((double) request.targetWidth) * sinR2;
                    double x3T3 = (((double) request.targetWidth) * cosR3) - (((double) request.targetHeight) * sinR2);
                    double x4T3 = -(((double) request.targetHeight) * sinR2);
                    double y3T2 = (((double) request.targetWidth) * sinR2) + (((double) request.targetHeight) * cosR3);
                    double y4T2 = ((double) request.targetHeight) * cosR3;
                    double maxX2 = Math.max(x4T3, Math.max(x3T3, Math.max(0.0d, x2T)));
                    double minX2 = Math.min(x4T3, Math.min(x3T3, Math.min(0.0d, x2T)));
                    double d7 = x4T3;
                    double y3T3 = y3T2;
                    double d8 = x2T;
                    double y4T3 = y4T2;
                    double maxY = Math.max(y4T3, Math.max(y3T3, Math.max(0.0d, y2T3)));
                    double d9 = x3T3;
                    double d10 = y3T3;
                    double d11 = maxY;
                    targetHeight = (int) Math.floor(maxY - Math.min(y4T3, Math.min(y3T3, Math.min(0.0d, y2T3))));
                    targetWidth = (int) Math.floor(maxX2 - minX2);
                }
            } else {
                inWidth = inWidth3;
                inHeight = inHeight3;
                onlyScaleDown = onlyScaleDown2;
                drawX = 0;
                drawY = 0;
                drawWidth = drawWidth2;
                drawHeight = drawHeight2;
                matrix2 = matrix3;
                targetWidth = targetWidth2;
                float f12 = targetRotation;
                targetHeight = targetHeight3;
            }
            if (exifOrientation2 != 0) {
                int exifRotation2 = getExifRotation(exifOrientation2);
                int exifTranslation = getExifTranslation(exifOrientation2);
                if (exifRotation2 != 0) {
                    matrix = matrix2;
                    matrix.preRotate((float) exifRotation2);
                    if (exifRotation2 == 90 || exifRotation2 == 270) {
                        int tmpHeight = targetHeight;
                        targetHeight = targetWidth;
                        targetWidth = tmpHeight;
                    }
                } else {
                    matrix = matrix2;
                }
                if (exifTranslation != 1) {
                    matrix.postScale((float) exifTranslation, 1.0f);
                }
                exifRotation = targetHeight;
                targetHeight2 = targetWidth;
            } else {
                matrix = matrix2;
                exifRotation = targetHeight;
                targetHeight2 = targetWidth;
            }
            if (request.centerCrop) {
                if (targetHeight2 != 0) {
                    inWidth2 = inWidth;
                    widthRatio = ((float) targetHeight2) / ((float) inWidth2);
                    inHeight2 = inHeight;
                } else {
                    inWidth2 = inWidth;
                    inHeight2 = inHeight;
                    widthRatio = ((float) exifRotation) / ((float) inHeight2);
                }
                if (exifRotation != 0) {
                    f10 = (float) exifRotation;
                    f9 = (float) inHeight2;
                } else {
                    f10 = (float) targetHeight2;
                    f9 = (float) inWidth2;
                }
                float heightRatio = f10 / f9;
                if (widthRatio > heightRatio) {
                    int newSize = (int) Math.ceil((double) (((float) inHeight2) * (heightRatio / widthRatio)));
                    if ((request.centerCropGravity & 48) == 48) {
                        drawY2 = 0;
                    } else if ((request.centerCropGravity & 80) == 80) {
                        drawY2 = inHeight2 - newSize;
                    } else {
                        drawY2 = (inHeight2 - newSize) / 2;
                    }
                    int drawHeight3 = newSize;
                    scaleX = widthRatio;
                    scaleY = ((float) exifRotation) / ((float) drawHeight3);
                    drawY = drawY2;
                    drawHeight = drawHeight3;
                } else if (widthRatio < heightRatio) {
                    int newSize2 = (int) Math.ceil((double) (((float) inWidth2) * (widthRatio / heightRatio)));
                    if ((request.centerCropGravity & 3) == 3) {
                        drawX2 = 0;
                    } else if ((request.centerCropGravity & 5) == 5) {
                        drawX2 = inWidth2 - newSize2;
                    } else {
                        drawX2 = (inWidth2 - newSize2) / 2;
                    }
                    int drawWidth3 = newSize2;
                    scaleX = ((float) targetHeight2) / ((float) drawWidth3);
                    scaleY = heightRatio;
                    drawX = drawX2;
                    drawWidth = drawWidth3;
                } else {
                    scaleY = heightRatio;
                    scaleX = heightRatio;
                    drawX = 0;
                    drawWidth = inWidth2;
                }
                if (shouldResize(onlyScaleDown, inWidth2, inHeight2, targetHeight2, exifRotation)) {
                    matrix.preScale(scaleX, scaleY);
                }
            } else {
                boolean onlyScaleDown3 = onlyScaleDown;
                int inHeight4 = inHeight;
                int inWidth4 = inWidth;
                if (request.centerInside) {
                    if (targetHeight2 != 0) {
                        f6 = (float) targetHeight2;
                        f5 = (float) inWidth4;
                    } else {
                        f6 = (float) exifRotation;
                        f5 = (float) inHeight4;
                    }
                    float widthRatio2 = f6 / f5;
                    if (exifRotation != 0) {
                        f8 = (float) exifRotation;
                        f7 = (float) inHeight4;
                    } else {
                        f8 = (float) targetHeight2;
                        f7 = (float) inWidth4;
                    }
                    float heightRatio2 = f8 / f7;
                    float scale = widthRatio2 < heightRatio2 ? widthRatio2 : heightRatio2;
                    if (shouldResize(onlyScaleDown3, inWidth4, inHeight4, targetHeight2, exifRotation)) {
                        matrix.preScale(scale, scale);
                    }
                } else if (!((targetHeight2 == 0 && exifRotation == 0) || (targetHeight2 == inWidth4 && exifRotation == inHeight4))) {
                    if (targetHeight2 != 0) {
                        f2 = (float) targetHeight2;
                        f = (float) inWidth4;
                    } else {
                        f2 = (float) exifRotation;
                        f = (float) inHeight4;
                    }
                    float sx = f2 / f;
                    if (exifRotation != 0) {
                        f4 = (float) exifRotation;
                        f3 = (float) inHeight4;
                    } else {
                        f4 = (float) targetHeight2;
                        f3 = (float) inWidth4;
                    }
                    float sy = f4 / f3;
                    if (shouldResize(onlyScaleDown3, inWidth4, inHeight4, targetHeight2, exifRotation)) {
                        matrix.preScale(sx, sy);
                    }
                }
            }
        } else {
            drawX = 0;
            drawY = 0;
            drawWidth = drawWidth2;
            drawHeight = drawHeight2;
            matrix = matrix3;
            int drawX3 = inWidth3;
            int drawWidth4 = inHeight3;
            boolean z = onlyScaleDown2;
        }
        Bitmap newResult = Bitmap.createBitmap(result2, drawX, drawY, drawWidth, drawHeight, matrix, true);
        Bitmap result3 = result2;
        if (newResult == result3) {
            return result3;
        }
        result2.recycle();
        return newResult;
    }

    private static boolean shouldResize(boolean onlyScaleDown, int inWidth, int inHeight, int targetWidth, int targetHeight) {
        return !onlyScaleDown || (targetWidth != 0 && inWidth > targetWidth) || (targetHeight != 0 && inHeight > targetHeight);
    }

    static int getExifRotation(int orientation) {
        switch (orientation) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 6:
                return 90;
            case 7:
            case 8:
                return 270;
            default:
                return 0;
        }
    }

    static int getExifTranslation(int orientation) {
        if (orientation == 2 || orientation == 7 || orientation == 4 || orientation == 5) {
            return -1;
        }
        return 1;
    }
}
