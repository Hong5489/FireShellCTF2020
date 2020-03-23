package androidx.core.graphics;

import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;

class TypefaceCompatApi21Impl extends TypefaceCompatBaseImpl {
    private static final String TAG = "TypefaceCompatApi21Impl";

    TypefaceCompatApi21Impl() {
    }

    private File getFile(ParcelFileDescriptor fd) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("/proc/self/fd/");
            sb.append(fd.getFd());
            String path = Os.readlink(sb.toString());
            if (OsConstants.S_ISREG(Os.stat(path).st_mode)) {
                return new File(path);
            }
            return null;
        } catch (ErrnoException e) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0049, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x004e, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r6.addSuppressed(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0052, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0055, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0056, code lost:
        if (r3 != null) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0060, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Typeface createFromFontInfo(android.content.Context r10, android.os.CancellationSignal r11, androidx.core.provider.FontsContractCompat.FontInfo[] r12, int r13) {
        /*
            r9 = this;
            int r0 = r12.length
            r1 = 0
            r2 = 1
            if (r0 >= r2) goto L_0x0006
            return r1
        L_0x0006:
            androidx.core.provider.FontsContractCompat$FontInfo r0 = r9.findBestInfo(r12, r13)
            android.content.ContentResolver r2 = r10.getContentResolver()
            android.net.Uri r3 = r0.getUri()     // Catch:{ IOException -> 0x0061 }
            java.lang.String r4 = "r"
            android.os.ParcelFileDescriptor r3 = r2.openFileDescriptor(r3, r4, r11)     // Catch:{ IOException -> 0x0061 }
            java.io.File r4 = r9.getFile(r3)     // Catch:{ all -> 0x0053 }
            if (r4 == 0) goto L_0x0031
            boolean r5 = r4.canRead()     // Catch:{ all -> 0x0053 }
            if (r5 != 0) goto L_0x0027
            goto L_0x0031
        L_0x0027:
            android.graphics.Typeface r5 = android.graphics.Typeface.createFromFile(r4)     // Catch:{ all -> 0x0053 }
            if (r3 == 0) goto L_0x0030
            r3.close()     // Catch:{ IOException -> 0x0061 }
        L_0x0030:
            return r5
        L_0x0031:
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ all -> 0x0053 }
            java.io.FileDescriptor r6 = r3.getFileDescriptor()     // Catch:{ all -> 0x0053 }
            r5.<init>(r6)     // Catch:{ all -> 0x0053 }
            android.graphics.Typeface r6 = super.createFromInputStream(r10, r5)     // Catch:{ all -> 0x0047 }
            r5.close()     // Catch:{ all -> 0x0053 }
            if (r3 == 0) goto L_0x0046
            r3.close()     // Catch:{ IOException -> 0x0061 }
        L_0x0046:
            return r6
        L_0x0047:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x0049 }
        L_0x0049:
            r7 = move-exception
            r5.close()     // Catch:{ all -> 0x004e }
            goto L_0x0052
        L_0x004e:
            r8 = move-exception
            r6.addSuppressed(r8)     // Catch:{ all -> 0x0053 }
        L_0x0052:
            throw r7     // Catch:{ all -> 0x0053 }
        L_0x0053:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0055 }
        L_0x0055:
            r5 = move-exception
            if (r3 == 0) goto L_0x0060
            r3.close()     // Catch:{ all -> 0x005c }
            goto L_0x0060
        L_0x005c:
            r6 = move-exception
            r4.addSuppressed(r6)     // Catch:{ IOException -> 0x0061 }
        L_0x0060:
            throw r5     // Catch:{ IOException -> 0x0061 }
        L_0x0061:
            r3 = move-exception
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompatApi21Impl.createFromFontInfo(android.content.Context, android.os.CancellationSignal, androidx.core.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
    }
}
