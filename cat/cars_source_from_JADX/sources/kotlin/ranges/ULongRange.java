package kotlin.ranges;

import kotlin.Metadata;
import kotlin.ULong;
import kotlin.UnsignedKt;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00172\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0001\u0017B\u0018\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0002\u0010\u0006J\u001b\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H\u0002ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u000f\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0017\u0010\u0005\u001a\u00020\u00038VX\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0004\u001a\u00020\u00038VX\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\t\u0010\bø\u0001\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018"}, mo6929d2 = {"Lkotlin/ranges/ULongRange;", "Lkotlin/ranges/ULongProgression;", "Lkotlin/ranges/ClosedRange;", "Lkotlin/ULong;", "start", "endInclusive", "(JJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getEndInclusive", "()Lkotlin/ULong;", "getStart", "contains", "", "value", "contains-VKZWuLQ", "(J)Z", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: ULongRange.kt */
public final class ULongRange extends ULongProgression implements ClosedRange<ULong> {
    public static final Companion Companion = new Companion(null);
    /* access modifiers changed from: private */
    public static final ULongRange EMPTY;

    @Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, mo6929d2 = {"Lkotlin/ranges/ULongRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/ULongRange;", "getEMPTY", "()Lkotlin/ranges/ULongRange;", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
    /* compiled from: ULongRange.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final ULongRange getEMPTY() {
            return ULongRange.EMPTY;
        }
    }

    private ULongRange(long start, long endInclusive) {
        super(start, endInclusive, 1, null);
    }

    public /* synthetic */ ULongRange(long start, long endInclusive, DefaultConstructorMarker $constructor_marker) {
        this(start, endInclusive);
    }

    public /* bridge */ /* synthetic */ boolean contains(Comparable comparable) {
        return m926containsVKZWuLQ(((ULong) comparable).m244unboximpl());
    }

    public ULong getStart() {
        return ULong.m195boximpl(getFirst());
    }

    public ULong getEndInclusive() {
        return ULong.m195boximpl(getLast());
    }

    /* renamed from: contains-VKZWuLQ reason: not valid java name */
    public boolean m926containsVKZWuLQ(long value) {
        return UnsignedKt.ulongCompare(getFirst(), value) <= 0 && UnsignedKt.ulongCompare(value, getLast()) <= 0;
    }

    public boolean isEmpty() {
        return UnsignedKt.ulongCompare(getFirst(), getLast()) > 0;
    }

    public boolean equals(Object other) {
        return (other instanceof ULongRange) && ((isEmpty() && ((ULongRange) other).isEmpty()) || (getFirst() == ((ULongRange) other).getFirst() && getLast() == ((ULongRange) other).getLast()));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (((int) ULong.m201constructorimpl(getFirst() ^ ULong.m201constructorimpl(getFirst() >>> 32))) * 31) + ((int) ULong.m201constructorimpl(getLast() ^ ULong.m201constructorimpl(getLast() >>> 32)));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ULong.m238toStringimpl(getFirst()));
        sb.append("..");
        sb.append(ULong.m238toStringimpl(getLast()));
        return sb.toString();
    }

    static {
        ULongRange uLongRange = new ULongRange(-1, 0, null);
        EMPTY = uLongRange;
    }
}