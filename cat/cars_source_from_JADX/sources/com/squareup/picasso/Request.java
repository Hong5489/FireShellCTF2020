package com.squareup.picasso;

import android.graphics.Bitmap.Config;
import android.net.Uri;
import com.squareup.picasso.Picasso.Priority;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class Request {
    private static final long TOO_LONG_LOG = TimeUnit.SECONDS.toNanos(5);
    public final boolean centerCrop;
    public final int centerCropGravity;
    public final boolean centerInside;
    public final Config config;
    public final boolean hasRotationPivot;

    /* renamed from: id */
    int f33id;
    int networkPolicy;
    public final boolean onlyScaleDown;
    public final Priority priority;
    public final boolean purgeable;
    public final int resourceId;
    public final float rotationDegrees;
    public final float rotationPivotX;
    public final float rotationPivotY;
    public final String stableKey;
    long started;
    public final int targetHeight;
    public final int targetWidth;
    public final List<Transformation> transformations;
    public final Uri uri;

    public static final class Builder {
        private boolean centerCrop;
        private int centerCropGravity;
        private boolean centerInside;
        private Config config;
        private boolean hasRotationPivot;
        private boolean onlyScaleDown;
        private Priority priority;
        private boolean purgeable;
        private int resourceId;
        private float rotationDegrees;
        private float rotationPivotX;
        private float rotationPivotY;
        private String stableKey;
        private int targetHeight;
        private int targetWidth;
        private List<Transformation> transformations;
        private Uri uri;

        public Builder(Uri uri2) {
            setUri(uri2);
        }

        public Builder(int resourceId2) {
            setResourceId(resourceId2);
        }

        Builder(Uri uri2, int resourceId2, Config bitmapConfig) {
            this.uri = uri2;
            this.resourceId = resourceId2;
            this.config = bitmapConfig;
        }

        private Builder(Request request) {
            this.uri = request.uri;
            this.resourceId = request.resourceId;
            this.stableKey = request.stableKey;
            this.targetWidth = request.targetWidth;
            this.targetHeight = request.targetHeight;
            this.centerCrop = request.centerCrop;
            this.centerInside = request.centerInside;
            this.centerCropGravity = request.centerCropGravity;
            this.rotationDegrees = request.rotationDegrees;
            this.rotationPivotX = request.rotationPivotX;
            this.rotationPivotY = request.rotationPivotY;
            this.hasRotationPivot = request.hasRotationPivot;
            this.purgeable = request.purgeable;
            this.onlyScaleDown = request.onlyScaleDown;
            if (request.transformations != null) {
                this.transformations = new ArrayList(request.transformations);
            }
            this.config = request.config;
            this.priority = request.priority;
        }

        /* access modifiers changed from: 0000 */
        public boolean hasImage() {
            return (this.uri == null && this.resourceId == 0) ? false : true;
        }

        /* access modifiers changed from: 0000 */
        public boolean hasSize() {
            return (this.targetWidth == 0 && this.targetHeight == 0) ? false : true;
        }

        /* access modifiers changed from: 0000 */
        public boolean hasPriority() {
            return this.priority != null;
        }

        public Builder setUri(Uri uri2) {
            if (uri2 != null) {
                this.uri = uri2;
                this.resourceId = 0;
                return this;
            }
            throw new IllegalArgumentException("Image URI may not be null.");
        }

        public Builder setResourceId(int resourceId2) {
            if (resourceId2 != 0) {
                this.resourceId = resourceId2;
                this.uri = null;
                return this;
            }
            throw new IllegalArgumentException("Image resource ID may not be 0.");
        }

        public Builder stableKey(String stableKey2) {
            this.stableKey = stableKey2;
            return this;
        }

        public Builder resize(int targetWidth2, int targetHeight2) {
            if (targetWidth2 < 0) {
                throw new IllegalArgumentException("Width must be positive number or 0.");
            } else if (targetHeight2 < 0) {
                throw new IllegalArgumentException("Height must be positive number or 0.");
            } else if (targetHeight2 == 0 && targetWidth2 == 0) {
                throw new IllegalArgumentException("At least one dimension has to be positive number.");
            } else {
                this.targetWidth = targetWidth2;
                this.targetHeight = targetHeight2;
                return this;
            }
        }

        public Builder clearResize() {
            this.targetWidth = 0;
            this.targetHeight = 0;
            this.centerCrop = false;
            this.centerInside = false;
            return this;
        }

        public Builder centerCrop() {
            return centerCrop(17);
        }

        public Builder centerCrop(int alignGravity) {
            if (!this.centerInside) {
                this.centerCrop = true;
                this.centerCropGravity = alignGravity;
                return this;
            }
            throw new IllegalStateException("Center crop can not be used after calling centerInside");
        }

        public Builder clearCenterCrop() {
            this.centerCrop = false;
            this.centerCropGravity = 17;
            return this;
        }

        public Builder centerInside() {
            if (!this.centerCrop) {
                this.centerInside = true;
                return this;
            }
            throw new IllegalStateException("Center inside can not be used after calling centerCrop");
        }

        public Builder clearCenterInside() {
            this.centerInside = false;
            return this;
        }

        public Builder onlyScaleDown() {
            if (this.targetHeight == 0 && this.targetWidth == 0) {
                throw new IllegalStateException("onlyScaleDown can not be applied without resize");
            }
            this.onlyScaleDown = true;
            return this;
        }

        public Builder clearOnlyScaleDown() {
            this.onlyScaleDown = false;
            return this;
        }

        public Builder rotate(float degrees) {
            this.rotationDegrees = degrees;
            return this;
        }

        public Builder rotate(float degrees, float pivotX, float pivotY) {
            this.rotationDegrees = degrees;
            this.rotationPivotX = pivotX;
            this.rotationPivotY = pivotY;
            this.hasRotationPivot = true;
            return this;
        }

        public Builder clearRotation() {
            this.rotationDegrees = 0.0f;
            this.rotationPivotX = 0.0f;
            this.rotationPivotY = 0.0f;
            this.hasRotationPivot = false;
            return this;
        }

        public Builder purgeable() {
            this.purgeable = true;
            return this;
        }

        public Builder config(Config config2) {
            if (config2 != null) {
                this.config = config2;
                return this;
            }
            throw new IllegalArgumentException("config == null");
        }

        public Builder priority(Priority priority2) {
            if (priority2 == null) {
                throw new IllegalArgumentException("Priority invalid.");
            } else if (this.priority == null) {
                this.priority = priority2;
                return this;
            } else {
                throw new IllegalStateException("Priority already set.");
            }
        }

        public Builder transform(Transformation transformation) {
            if (transformation == null) {
                throw new IllegalArgumentException("Transformation must not be null.");
            } else if (transformation.key() != null) {
                if (this.transformations == null) {
                    this.transformations = new ArrayList(2);
                }
                this.transformations.add(transformation);
                return this;
            } else {
                throw new IllegalArgumentException("Transformation key must not be null.");
            }
        }

        public Builder transform(List<? extends Transformation> transformations2) {
            if (transformations2 != null) {
                int size = transformations2.size();
                for (int i = 0; i < size; i++) {
                    transform((Transformation) transformations2.get(i));
                }
                return this;
            }
            throw new IllegalArgumentException("Transformation list must not be null.");
        }

        public Request build() {
            if (this.centerInside && this.centerCrop) {
                throw new IllegalStateException("Center crop and center inside can not be used together.");
            } else if (this.centerCrop && this.targetWidth == 0 && this.targetHeight == 0) {
                throw new IllegalStateException("Center crop requires calling resize with positive width and height.");
            } else if (this.centerInside && this.targetWidth == 0 && this.targetHeight == 0) {
                throw new IllegalStateException("Center inside requires calling resize with positive width and height.");
            } else {
                if (this.priority == null) {
                    this.priority = Priority.NORMAL;
                }
                Request request = r2;
                Request request2 = new Request(this.uri, this.resourceId, this.stableKey, this.transformations, this.targetWidth, this.targetHeight, this.centerCrop, this.centerInside, this.centerCropGravity, this.onlyScaleDown, this.rotationDegrees, this.rotationPivotX, this.rotationPivotY, this.hasRotationPivot, this.purgeable, this.config, this.priority);
                return request;
            }
        }
    }

    private Request(Uri uri2, int resourceId2, String stableKey2, List<Transformation> transformations2, int targetWidth2, int targetHeight2, boolean centerCrop2, boolean centerInside2, int centerCropGravity2, boolean onlyScaleDown2, float rotationDegrees2, float rotationPivotX2, float rotationPivotY2, boolean hasRotationPivot2, boolean purgeable2, Config config2, Priority priority2) {
        this.uri = uri2;
        this.resourceId = resourceId2;
        this.stableKey = stableKey2;
        if (transformations2 == null) {
            this.transformations = null;
        } else {
            this.transformations = Collections.unmodifiableList(transformations2);
        }
        this.targetWidth = targetWidth2;
        this.targetHeight = targetHeight2;
        this.centerCrop = centerCrop2;
        this.centerInside = centerInside2;
        this.centerCropGravity = centerCropGravity2;
        this.onlyScaleDown = onlyScaleDown2;
        this.rotationDegrees = rotationDegrees2;
        this.rotationPivotX = rotationPivotX2;
        this.rotationPivotY = rotationPivotY2;
        this.hasRotationPivot = hasRotationPivot2;
        this.purgeable = purgeable2;
        this.config = config2;
        this.priority = priority2;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("Request{");
        int i = this.resourceId;
        if (i > 0) {
            builder.append(i);
        } else {
            builder.append(this.uri);
        }
        List<Transformation> list = this.transformations;
        if (list != null && !list.isEmpty()) {
            for (Transformation transformation : this.transformations) {
                builder.append(' ');
                builder.append(transformation.key());
            }
        }
        if (this.stableKey != null) {
            builder.append(" stableKey(");
            builder.append(this.stableKey);
            builder.append(')');
        }
        if (this.targetWidth > 0) {
            builder.append(" resize(");
            builder.append(this.targetWidth);
            builder.append(',');
            builder.append(this.targetHeight);
            builder.append(')');
        }
        if (this.centerCrop) {
            builder.append(" centerCrop");
        }
        if (this.centerInside) {
            builder.append(" centerInside");
        }
        if (this.rotationDegrees != 0.0f) {
            builder.append(" rotation(");
            builder.append(this.rotationDegrees);
            if (this.hasRotationPivot) {
                builder.append(" @ ");
                builder.append(this.rotationPivotX);
                builder.append(',');
                builder.append(this.rotationPivotY);
            }
            builder.append(')');
        }
        if (this.purgeable) {
            builder.append(" purgeable");
        }
        if (this.config != null) {
            builder.append(' ');
            builder.append(this.config);
        }
        builder.append('}');
        return builder.toString();
    }

    /* access modifiers changed from: 0000 */
    public String logId() {
        long delta = System.nanoTime() - this.started;
        if (delta > TOO_LONG_LOG) {
            StringBuilder sb = new StringBuilder();
            sb.append(plainId());
            sb.append('+');
            sb.append(TimeUnit.NANOSECONDS.toSeconds(delta));
            sb.append('s');
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(plainId());
        sb2.append('+');
        sb2.append(TimeUnit.NANOSECONDS.toMillis(delta));
        sb2.append("ms");
        return sb2.toString();
    }

    /* access modifiers changed from: 0000 */
    public String plainId() {
        StringBuilder sb = new StringBuilder();
        sb.append("[R");
        sb.append(this.f33id);
        sb.append(']');
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String getName() {
        Uri uri2 = this.uri;
        if (uri2 != null) {
            return String.valueOf(uri2.getPath());
        }
        return Integer.toHexString(this.resourceId);
    }

    public boolean hasSize() {
        return (this.targetWidth == 0 && this.targetHeight == 0) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public boolean needsTransformation() {
        return needsMatrixTransform() || hasCustomTransformations();
    }

    /* access modifiers changed from: 0000 */
    public boolean needsMatrixTransform() {
        return hasSize() || this.rotationDegrees != 0.0f;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasCustomTransformations() {
        return this.transformations != null;
    }

    public Builder buildUpon() {
        return new Builder();
    }
}
