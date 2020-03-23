package androidx.core.database;

import android.database.Cursor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 2}, mo6928d1 = {"\u00008\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u001a\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\b\u001a\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\b¢\u0006\u0002\u0010\u0007\u001a\u001c\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\b¢\u0006\u0002\u0010\n\u001a\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\b¢\u0006\u0002\u0010\f\u001a\u001c\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\b¢\u0006\u0002\u0010\u000f\u001a\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\b¢\u0006\u0002\u0010\u0012\u001a\u0017\u0010\u0013\u001a\u0004\u0018\u00010\u0014*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\b¨\u0006\u0015"}, mo6929d2 = {"getBlobOrNull", "", "Landroid/database/Cursor;", "index", "", "getDoubleOrNull", "", "(Landroid/database/Cursor;I)Ljava/lang/Double;", "getFloatOrNull", "", "(Landroid/database/Cursor;I)Ljava/lang/Float;", "getIntOrNull", "(Landroid/database/Cursor;I)Ljava/lang/Integer;", "getLongOrNull", "", "(Landroid/database/Cursor;I)Ljava/lang/Long;", "getShortOrNull", "", "(Landroid/database/Cursor;I)Ljava/lang/Short;", "getStringOrNull", "", "core-ktx_release"}, mo6930k = 2, mo6931mv = {1, 1, 10})
/* compiled from: Cursor.kt */
public final class CursorKt {
    public static final byte[] getBlobOrNull(Cursor $receiver, int index) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.isNull(index)) {
            return null;
        }
        return $receiver.getBlob(index);
    }

    public static final Double getDoubleOrNull(Cursor $receiver, int index) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.isNull(index)) {
            return null;
        }
        return Double.valueOf($receiver.getDouble(index));
    }

    public static final Float getFloatOrNull(Cursor $receiver, int index) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.isNull(index)) {
            return null;
        }
        return Float.valueOf($receiver.getFloat(index));
    }

    public static final Integer getIntOrNull(Cursor $receiver, int index) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.isNull(index)) {
            return null;
        }
        return Integer.valueOf($receiver.getInt(index));
    }

    public static final Long getLongOrNull(Cursor $receiver, int index) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.isNull(index)) {
            return null;
        }
        return Long.valueOf($receiver.getLong(index));
    }

    public static final Short getShortOrNull(Cursor $receiver, int index) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.isNull(index)) {
            return null;
        }
        return Short.valueOf($receiver.getShort(index));
    }

    public static final String getStringOrNull(Cursor $receiver, int index) {
        Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
        if ($receiver.isNull(index)) {
            return null;
        }
        return $receiver.getString(index);
    }
}
