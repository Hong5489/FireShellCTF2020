package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.LongCompanionObject;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, mo6929d2 = {"Lkotlin/time/TestClock;", "Lkotlin/time/AbstractLongClock;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(D)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: Clocks.kt */
public final class TestClock extends AbstractLongClock {
    private long reading;

    public TestClock() {
        super(TimeUnit.NANOSECONDS);
    }

    /* access modifiers changed from: protected */
    public long read() {
        return this.reading;
    }

    /* renamed from: plusAssign-LRDsOJo reason: not valid java name */
    public final void m1019plusAssignLRDsOJo(double duration) {
        long newReading;
        double delta = Duration.m1004toDoubleimpl(duration, getUnit());
        long longDelta = (long) delta;
        if (longDelta == Long.MIN_VALUE || longDelta == LongCompanionObject.MAX_VALUE) {
            double newReading2 = ((double) this.reading) + delta;
            if (newReading2 > ((double) LongCompanionObject.MAX_VALUE) || newReading2 < ((double) Long.MIN_VALUE)) {
                m1018overflowLRDsOJo(duration);
            }
            newReading = (long) newReading2;
        } else {
            long j = this.reading;
            newReading = j + longDelta;
            if ((j ^ longDelta) >= 0 && (j ^ newReading) < 0) {
                m1018overflowLRDsOJo(duration);
            }
        }
        this.reading = newReading;
    }

    /* renamed from: overflow-LRDsOJo reason: not valid java name */
    private final void m1018overflowLRDsOJo(double duration) {
        StringBuilder sb = new StringBuilder();
        sb.append("TestClock will overflow if its reading ");
        sb.append(this.reading);
        sb.append("ns is advanced by ");
        sb.append(Duration.m1010toStringimpl(duration));
        sb.append('.');
        throw new IllegalStateException(sb.toString());
    }
}
