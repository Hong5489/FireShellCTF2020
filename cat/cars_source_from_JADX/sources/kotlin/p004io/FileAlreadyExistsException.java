package kotlin.p004io;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, mo6929d2 = {"Lkotlin/io/FileAlreadyExistsException;", "Lkotlin/io/FileSystemException;", "file", "Ljava/io/File;", "other", "reason", "", "(Ljava/io/File;Ljava/io/File;Ljava/lang/String;)V", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* renamed from: kotlin.io.FileAlreadyExistsException */
/* compiled from: Exceptions.kt */
public final class FileAlreadyExistsException extends FileSystemException {
    public FileAlreadyExistsException(File file, File other, String reason) {
        Intrinsics.checkParameterIsNotNull(file, "file");
        super(file, other, reason);
    }

    public /* synthetic */ FileAlreadyExistsException(File file, File file2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            file2 = null;
        }
        if ((i & 4) != 0) {
            str = null;
        }
        this(file, file2, str);
    }
}
