package kotlin;

import java.io.PrintStream;
import java.io.PrintWriter;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\b\u001a\u00020\t*\u00020\u00032\u0006\u0010\n\u001a\u00020\u0003\u001a\r\u0010\u000b\u001a\u00020\t*\u00020\u0003H\b\u001a\u0015\u0010\u000b\u001a\u00020\t*\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\b\u001a\u0015\u0010\u000b\u001a\u00020\t*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\b\"!\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\f\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, mo6929d2 = {"stackTrace", "", "Ljava/lang/StackTraceElement;", "", "stackTrace$annotations", "(Ljava/lang/Throwable;)V", "getStackTrace", "(Ljava/lang/Throwable;)[Ljava/lang/StackTraceElement;", "addSuppressed", "", "exception", "printStackTrace", "stream", "Ljava/io/PrintStream;", "writer", "Ljava/io/PrintWriter;", "kotlin-stdlib"}, mo6930k = 5, mo6931mv = {1, 1, 15}, mo6933xi = 1, mo6934xs = "kotlin/ExceptionsKt")
/* compiled from: Exceptions.kt */
class ExceptionsKt__ExceptionsKt {
    public static /* synthetic */ void stackTrace$annotations(Throwable th) {
    }

    private static final void printStackTrace(Throwable $this$printStackTrace) {
        if ($this$printStackTrace != null) {
            $this$printStackTrace.printStackTrace();
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.Throwable");
    }

    private static final void printStackTrace(Throwable $this$printStackTrace, PrintWriter writer) {
        if ($this$printStackTrace != null) {
            $this$printStackTrace.printStackTrace(writer);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.Throwable");
    }

    private static final void printStackTrace(Throwable $this$printStackTrace, PrintStream stream) {
        if ($this$printStackTrace != null) {
            $this$printStackTrace.printStackTrace(stream);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.Throwable");
    }

    public static final StackTraceElement[] getStackTrace(Throwable $this$stackTrace) {
        Intrinsics.checkParameterIsNotNull($this$stackTrace, "$this$stackTrace");
        StackTraceElement[] stackTrace = $this$stackTrace.getStackTrace();
        if (stackTrace == null) {
            Intrinsics.throwNpe();
        }
        return stackTrace;
    }

    public static final void addSuppressed(Throwable $this$addSuppressed, Throwable exception) {
        Intrinsics.checkParameterIsNotNull($this$addSuppressed, "$this$addSuppressed");
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed($this$addSuppressed, exception);
    }
}
