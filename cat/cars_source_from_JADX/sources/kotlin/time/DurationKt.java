package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001f\u0010%\u001a\u00020\u0007*\u00020\b2\u0006\u0010&\u001a\u00020\u0007H\nø\u0001\u0000¢\u0006\u0004\b'\u0010(\u001a\u001f\u0010%\u001a\u00020\u0007*\u00020\r2\u0006\u0010&\u001a\u00020\u0007H\nø\u0001\u0000¢\u0006\u0004\b)\u0010*\u001a \u0010+\u001a\u00020\u0007*\u00020\b2\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007ø\u0001\u0000¢\u0006\u0002\u0010.\u001a \u0010+\u001a\u00020\u0007*\u00020\r2\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007ø\u0001\u0000¢\u0006\u0002\u0010/\u001a \u0010+\u001a\u00020\u0007*\u00020\u00102\n\u0010,\u001a\u00060\u0001j\u0002`-H\u0007ø\u0001\u0000¢\u0006\u0002\u00100\"\u001b\u0010\u0000\u001a\u00020\u00018Â\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"!\u0010\u0006\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\r8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\u000e\u001a\u0004\b\u000b\u0010\u000f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00108FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\u0011\u001a\u0004\b\u000b\u0010\u0012\"!\u0010\u0013\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0014\u0010\n\u001a\u0004\b\u0015\u0010\f\"!\u0010\u0013\u001a\u00020\u0007*\u00020\r8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0014\u0010\u000e\u001a\u0004\b\u0015\u0010\u000f\"!\u0010\u0013\u001a\u00020\u0007*\u00020\u00108FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0014\u0010\u0011\u001a\u0004\b\u0015\u0010\u0012\"!\u0010\u0016\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0017\u0010\n\u001a\u0004\b\u0018\u0010\f\"!\u0010\u0016\u001a\u00020\u0007*\u00020\r8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0017\u0010\u000e\u001a\u0004\b\u0018\u0010\u000f\"!\u0010\u0016\u001a\u00020\u0007*\u00020\u00108FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u0017\u0010\u0011\u001a\u0004\b\u0018\u0010\u0012\"!\u0010\u0019\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001a\u0010\n\u001a\u0004\b\u001b\u0010\f\"!\u0010\u0019\u001a\u00020\u0007*\u00020\r8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001a\u0010\u000e\u001a\u0004\b\u001b\u0010\u000f\"!\u0010\u0019\u001a\u00020\u0007*\u00020\u00108FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001a\u0010\u0011\u001a\u0004\b\u001b\u0010\u0012\"!\u0010\u001c\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001d\u0010\n\u001a\u0004\b\u001e\u0010\f\"!\u0010\u001c\u001a\u00020\u0007*\u00020\r8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001d\u0010\u000e\u001a\u0004\b\u001e\u0010\u000f\"!\u0010\u001c\u001a\u00020\u0007*\u00020\u00108FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\u001d\u0010\u0011\u001a\u0004\b\u001e\u0010\u0012\"!\u0010\u001f\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b \u0010\n\u001a\u0004\b!\u0010\f\"!\u0010\u001f\u001a\u00020\u0007*\u00020\r8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b \u0010\u000e\u001a\u0004\b!\u0010\u000f\"!\u0010\u001f\u001a\u00020\u0007*\u00020\u00108FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b \u0010\u0011\u001a\u0004\b!\u0010\u0012\"!\u0010\"\u001a\u00020\u0007*\u00020\b8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b#\u0010\n\u001a\u0004\b$\u0010\f\"!\u0010\"\u001a\u00020\u0007*\u00020\r8FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b#\u0010\u000e\u001a\u0004\b$\u0010\u000f\"!\u0010\"\u001a\u00020\u0007*\u00020\u00108FX\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b#\u0010\u0011\u001a\u0004\b$\u0010\u0012\u0002\u0004\n\u0002\b\u0019¨\u00061"}, mo6929d2 = {"storageUnit", "Ljava/util/concurrent/TimeUnit;", "storageUnit$annotations", "()V", "getStorageUnit", "()Ljava/util/concurrent/TimeUnit;", "days", "Lkotlin/time/Duration;", "", "days$annotations", "(D)V", "getDays", "(D)D", "", "(I)V", "(I)D", "", "(J)V", "(J)D", "hours", "hours$annotations", "getHours", "microseconds", "microseconds$annotations", "getMicroseconds", "milliseconds", "milliseconds$annotations", "getMilliseconds", "minutes", "minutes$annotations", "getMinutes", "nanoseconds", "nanoseconds$annotations", "getNanoseconds", "seconds", "seconds$annotations", "getSeconds", "times", "duration", "times-kIfJnKk", "(DD)D", "times-mvk6XK0", "(ID)D", "toDuration", "unit", "Lkotlin/time/DurationUnit;", "(DLjava/util/concurrent/TimeUnit;)D", "(ILjava/util/concurrent/TimeUnit;)D", "(JLjava/util/concurrent/TimeUnit;)D", "kotlin-stdlib"}, mo6930k = 2, mo6931mv = {1, 1, 15})
/* compiled from: Duration.kt */
public final class DurationKt {
    public static /* synthetic */ void days$annotations(double d) {
    }

    public static /* synthetic */ void days$annotations(int i) {
    }

    public static /* synthetic */ void days$annotations(long j) {
    }

    public static /* synthetic */ void hours$annotations(double d) {
    }

    public static /* synthetic */ void hours$annotations(int i) {
    }

    public static /* synthetic */ void hours$annotations(long j) {
    }

    public static /* synthetic */ void microseconds$annotations(double d) {
    }

    public static /* synthetic */ void microseconds$annotations(int i) {
    }

    public static /* synthetic */ void microseconds$annotations(long j) {
    }

    public static /* synthetic */ void milliseconds$annotations(double d) {
    }

    public static /* synthetic */ void milliseconds$annotations(int i) {
    }

    public static /* synthetic */ void milliseconds$annotations(long j) {
    }

    public static /* synthetic */ void minutes$annotations(double d) {
    }

    public static /* synthetic */ void minutes$annotations(int i) {
    }

    public static /* synthetic */ void minutes$annotations(long j) {
    }

    public static /* synthetic */ void nanoseconds$annotations(double d) {
    }

    public static /* synthetic */ void nanoseconds$annotations(int i) {
    }

    public static /* synthetic */ void nanoseconds$annotations(long j) {
    }

    public static /* synthetic */ void seconds$annotations(double d) {
    }

    public static /* synthetic */ void seconds$annotations(int i) {
    }

    public static /* synthetic */ void seconds$annotations(long j) {
    }

    private static /* synthetic */ void storageUnit$annotations() {
    }

    /* access modifiers changed from: private */
    public static final TimeUnit getStorageUnit() {
        return TimeUnit.NANOSECONDS;
    }

    public static final double toDuration(int $this$toDuration, TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return toDuration((double) $this$toDuration, unit);
    }

    public static final double toDuration(long $this$toDuration, TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return toDuration((double) $this$toDuration, unit);
    }

    public static final double toDuration(double $this$toDuration, TimeUnit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "unit");
        return Duration.m972constructorimpl(DurationUnitKt.convertDurationUnit($this$toDuration, unit, TimeUnit.NANOSECONDS));
    }

    public static final double getNanoseconds(int $this$nanoseconds) {
        return toDuration($this$nanoseconds, TimeUnit.NANOSECONDS);
    }

    public static final double getNanoseconds(long $this$nanoseconds) {
        return toDuration($this$nanoseconds, TimeUnit.NANOSECONDS);
    }

    public static final double getNanoseconds(double $this$nanoseconds) {
        return toDuration($this$nanoseconds, TimeUnit.NANOSECONDS);
    }

    public static final double getMicroseconds(int $this$microseconds) {
        return toDuration($this$microseconds, TimeUnit.MICROSECONDS);
    }

    public static final double getMicroseconds(long $this$microseconds) {
        return toDuration($this$microseconds, TimeUnit.MICROSECONDS);
    }

    public static final double getMicroseconds(double $this$microseconds) {
        return toDuration($this$microseconds, TimeUnit.MICROSECONDS);
    }

    public static final double getMilliseconds(int $this$milliseconds) {
        return toDuration($this$milliseconds, TimeUnit.MILLISECONDS);
    }

    public static final double getMilliseconds(long $this$milliseconds) {
        return toDuration($this$milliseconds, TimeUnit.MILLISECONDS);
    }

    public static final double getMilliseconds(double $this$milliseconds) {
        return toDuration($this$milliseconds, TimeUnit.MILLISECONDS);
    }

    public static final double getSeconds(int $this$seconds) {
        return toDuration($this$seconds, TimeUnit.SECONDS);
    }

    public static final double getSeconds(long $this$seconds) {
        return toDuration($this$seconds, TimeUnit.SECONDS);
    }

    public static final double getSeconds(double $this$seconds) {
        return toDuration($this$seconds, TimeUnit.SECONDS);
    }

    public static final double getMinutes(int $this$minutes) {
        return toDuration($this$minutes, TimeUnit.MINUTES);
    }

    public static final double getMinutes(long $this$minutes) {
        return toDuration($this$minutes, TimeUnit.MINUTES);
    }

    public static final double getMinutes(double $this$minutes) {
        return toDuration($this$minutes, TimeUnit.MINUTES);
    }

    public static final double getHours(int $this$hours) {
        return toDuration($this$hours, TimeUnit.HOURS);
    }

    public static final double getHours(long $this$hours) {
        return toDuration($this$hours, TimeUnit.HOURS);
    }

    public static final double getHours(double $this$hours) {
        return toDuration($this$hours, TimeUnit.HOURS);
    }

    public static final double getDays(int $this$days) {
        return toDuration($this$days, TimeUnit.DAYS);
    }

    public static final double getDays(long $this$days) {
        return toDuration($this$days, TimeUnit.DAYS);
    }

    public static final double getDays(double $this$days) {
        return toDuration($this$days, TimeUnit.DAYS);
    }

    /* renamed from: times-mvk6XK0 reason: not valid java name */
    private static final double m1017timesmvk6XK0(int $this$times, double duration) {
        return Duration.m999timesimpl(duration, $this$times);
    }

    /* renamed from: times-kIfJnKk reason: not valid java name */
    private static final double m1016timeskIfJnKk(double $this$times, double duration) {
        return Duration.m998timesimpl(duration, $this$times);
    }
}
