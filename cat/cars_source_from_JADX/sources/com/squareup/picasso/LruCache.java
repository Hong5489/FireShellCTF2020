package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;

public final class LruCache implements Cache {
    final android.util.LruCache<String, BitmapAndSize> cache;

    static final class BitmapAndSize {
        final Bitmap bitmap;
        final int byteCount;

        BitmapAndSize(Bitmap bitmap2, int byteCount2) {
            this.bitmap = bitmap2;
            this.byteCount = byteCount2;
        }
    }

    public LruCache(Context context) {
        this(Utils.calculateMemoryCacheSize(context));
    }

    public LruCache(int maxByteCount) {
        this.cache = new android.util.LruCache<String, BitmapAndSize>(maxByteCount) {
            /* access modifiers changed from: protected */
            public int sizeOf(String key, BitmapAndSize value) {
                return value.byteCount;
            }
        };
    }

    public Bitmap get(String key) {
        BitmapAndSize bitmapAndSize = (BitmapAndSize) this.cache.get(key);
        if (bitmapAndSize != null) {
            return bitmapAndSize.bitmap;
        }
        return null;
    }

    public void set(String key, Bitmap bitmap) {
        if (key == null || bitmap == null) {
            throw new NullPointerException("key == null || bitmap == null");
        }
        int byteCount = Utils.getBitmapBytes(bitmap);
        if (byteCount > maxSize()) {
            this.cache.remove(key);
        } else {
            this.cache.put(key, new BitmapAndSize(bitmap, byteCount));
        }
    }

    public int size() {
        return this.cache.size();
    }

    public int maxSize() {
        return this.cache.maxSize();
    }

    public void clear() {
        this.cache.evictAll();
    }

    public void clearKeyUri(String uri) {
        for (String key : this.cache.snapshot().keySet()) {
            if (key.startsWith(uri) && key.length() > uri.length() && key.charAt(uri.length()) == 10) {
                this.cache.remove(key);
            }
        }
    }

    public int hitCount() {
        return this.cache.hitCount();
    }

    public int missCount() {
        return this.cache.missCount();
    }

    public int putCount() {
        return this.cache.putCount();
    }

    public int evictionCount() {
        return this.cache.evictionCount();
    }
}
