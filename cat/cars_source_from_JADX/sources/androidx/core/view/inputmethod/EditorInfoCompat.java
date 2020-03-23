package androidx.core.view.inputmethod;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

public final class EditorInfoCompat {
    private static final String CONTENT_MIME_TYPES_INTEROP_KEY = "androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
    private static final String CONTENT_MIME_TYPES_KEY = "androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final int IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE;
    public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;

    public static void setContentMimeTypes(EditorInfo editorInfo, String[] contentMimeTypes) {
        if (VERSION.SDK_INT >= 25) {
            editorInfo.contentMimeTypes = contentMimeTypes;
            return;
        }
        if (editorInfo.extras == null) {
            editorInfo.extras = new Bundle();
        }
        String str = "androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
        editorInfo.extras.putStringArray(str, contentMimeTypes);
        editorInfo.extras.putStringArray(str, contentMimeTypes);
    }

    public static String[] getContentMimeTypes(EditorInfo editorInfo) {
        if (VERSION.SDK_INT >= 25) {
            String[] result = editorInfo.contentMimeTypes;
            return result != null ? result : EMPTY_STRING_ARRAY;
        } else if (editorInfo.extras == null) {
            return EMPTY_STRING_ARRAY;
        } else {
            String str = "androidx.core.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
            String[] result2 = editorInfo.extras.getStringArray(str);
            if (result2 == null) {
                result2 = editorInfo.extras.getStringArray(str);
            }
            return result2 != null ? result2 : EMPTY_STRING_ARRAY;
        }
    }
}
