package com.squareup.picasso;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Thumbnails;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.RequestHandler.Result;
import java.io.IOException;
import okio.Okio;

class MediaStoreRequestHandler extends ContentStreamRequestHandler {
    private static final String[] CONTENT_ORIENTATION = {"orientation"};

    enum PicassoKind {
        MICRO(3, 96, 96),
        MINI(1, 512, 384),
        FULL(2, -1, -1);
        
        final int androidKind;
        final int height;
        final int width;

        private PicassoKind(int androidKind2, int width2, int height2) {
            this.androidKind = androidKind2;
            this.width = width2;
            this.height = height2;
        }
    }

    MediaStoreRequestHandler(Context context) {
        super(context);
    }

    public boolean canHandleRequest(Request data) {
        Uri uri = data.uri;
        if ("content".equals(uri.getScheme())) {
            if ("media".equals(uri.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    public Result load(Request request, int networkPolicy) throws IOException {
        Bitmap bitmap;
        Request request2 = request;
        ContentResolver contentResolver = this.context.getContentResolver();
        int exifOrientation = getExifOrientation(contentResolver, request2.uri);
        String mimeType = contentResolver.getType(request2.uri);
        int i = 1;
        boolean isVideo = mimeType != null && mimeType.startsWith("video/");
        if (request.hasSize()) {
            PicassoKind picassoKind = getPicassoKind(request2.targetWidth, request2.targetHeight);
            if (!isVideo && picassoKind == PicassoKind.FULL) {
                return new Result(null, Okio.source(getInputStream(request)), LoadedFrom.DISK, exifOrientation);
            }
            long id = ContentUris.parseId(request2.uri);
            Options options = createBitmapOptions(request);
            options.inJustDecodeBounds = true;
            long id2 = id;
            calculateInSampleSize(request2.targetWidth, request2.targetHeight, picassoKind.width, picassoKind.height, options, request);
            if (isVideo) {
                if (picassoKind != PicassoKind.FULL) {
                    i = picassoKind.androidKind;
                }
                bitmap = Thumbnails.getThumbnail(contentResolver, id2, i, options);
            } else {
                bitmap = Images.Thumbnails.getThumbnail(contentResolver, id2, picassoKind.androidKind, options);
            }
            if (bitmap != null) {
                return new Result(bitmap, null, LoadedFrom.DISK, exifOrientation);
            }
        }
        return new Result(null, Okio.source(getInputStream(request)), LoadedFrom.DISK, exifOrientation);
    }

    static PicassoKind getPicassoKind(int targetWidth, int targetHeight) {
        if (targetWidth <= PicassoKind.MICRO.width && targetHeight <= PicassoKind.MICRO.height) {
            return PicassoKind.MICRO;
        }
        if (targetWidth > PicassoKind.MINI.width || targetHeight > PicassoKind.MINI.height) {
            return PicassoKind.FULL;
        }
        return PicassoKind.MINI;
    }

    static int getExifOrientation(ContentResolver contentResolver, Uri uri) {
        Cursor cursor = null;
        try {
            Cursor cursor2 = contentResolver.query(uri, CONTENT_ORIENTATION, null, null, null);
            if (cursor2 != null) {
                if (cursor2.moveToFirst()) {
                    int i = cursor2.getInt(0);
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    return i;
                }
            }
            if (cursor2 != null) {
                cursor2.close();
            }
            return 0;
        } catch (RuntimeException e) {
            if (cursor != null) {
                cursor.close();
            }
            return 0;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}
