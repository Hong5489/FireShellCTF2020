package kotlin.p004io;

import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, mo6929d2 = {"Lkotlin/io/FileSystemException;", "Ljava/io/IOException;", "file", "Ljava/io/File;", "other", "reason", "", "(Ljava/io/File;Ljava/io/File;Ljava/lang/String;)V", "getFile", "()Ljava/io/File;", "getOther", "getReason", "()Ljava/lang/String;", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* renamed from: kotlin.io.FileSystemException */
/* compiled from: Exceptions.kt */
public class FileSystemException extends IOException {
    private final File file;
    private final File other;
    private final String reason;

    public FileSystemException(File file2, File other2, String reason2) {
        Intrinsics.checkParameterIsNotNull(file2, "file");
        super(ExceptionsKt.constructMessage(file2, other2, reason2));
        this.file = file2;
        this.other = other2;
        this.reason = reason2;
    }

    public final File getFile() {
        return this.file;
    }

    public /* synthetic */ FileSystemException(File file2, File file3, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            file3 = null;
        }
        if ((i & 4) != 0) {
            str = null;
        }
        this(file2, file3, str);
    }

    public final File getOther() {
        return this.other;
    }

    public final String getReason() {
        return this.reason;
    }
}
