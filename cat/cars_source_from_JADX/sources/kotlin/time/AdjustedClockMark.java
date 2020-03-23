package kotlin.time;

import kotlin.Metadata;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u0003\u0018\u00002\u00020\u0001B\u0018\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u0010\u0010\u000b\u001a\u00020\u0004H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0007J\u001b\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u0004H\u0002ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u0002\u0004\n\u0002\b\u0019¨\u0006\u0010"}, mo6929d2 = {"Lkotlin/time/AdjustedClockMark;", "Lkotlin/time/ClockMark;", "mark", "adjustment", "Lkotlin/time/Duration;", "(Lkotlin/time/ClockMark;DLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getAdjustment", "()D", "D", "getMark", "()Lkotlin/time/ClockMark;", "elapsedNow", "plus", "duration", "plus-LRDsOJo", "(D)Lkotlin/time/ClockMark;", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: Clock.kt */
final class AdjustedClockMark extends ClockMark {
    private final double adjustment;
    private final ClockMark mark;

    private AdjustedClockMark(ClockMark mark2, double adjustment2) {
        this.mark = mark2;
        this.adjustment = adjustment2;
    }

    public /* synthetic */ AdjustedClockMark(ClockMark mark2, double adjustment2, DefaultConstructorMarker $constructor_marker) {
        this(mark2, adjustment2);
    }

    public final double getAdjustment() {
        return this.adjustment;
    }

    public final ClockMark getMark() {
        return this.mark;
    }

    public double elapsedNow() {
        return Duration.m995minusLRDsOJo(this.mark.elapsedNow(), this.adjustment);
    }

    /* renamed from: plus-LRDsOJo reason: not valid java name */
    public ClockMark m967plusLRDsOJo(double duration) {
        return new AdjustedClockMark(this.mark, Duration.m996plusLRDsOJo(this.adjustment, duration), null);
    }
}
