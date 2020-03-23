package kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.text.CharsKt;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0004\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0001\u001a\"\u0010\f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000e\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u0001H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u000e\u001a\u0010\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\tH\u0001\u001a\u0018\u0010\u0012\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\u0013H\u0001\u001a\"\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016\u001a\"\u0010\u0017\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0001ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0016\u001a\u0010\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0013H\u0001\u001a\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0013H\u0000\u001a\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\tH\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, mo6929d2 = {"doubleToUInt", "Lkotlin/UInt;", "v", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "ulongCompare", "", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToString", "", "base", "kotlin-stdlib"}, mo6930k = 2, mo6931mv = {1, 1, 15})
/* compiled from: UnsignedUtils.kt */
public final class UnsignedKt {
    public static final int uintCompare(int v1, int v2) {
        return Intrinsics.compare(v1 ^ Integer.MIN_VALUE, Integer.MIN_VALUE ^ v2);
    }

    public static final int ulongCompare(long v1, long v2) {
        return ((v1 ^ Long.MIN_VALUE) > (Long.MIN_VALUE ^ v2) ? 1 : ((v1 ^ Long.MIN_VALUE) == (Long.MIN_VALUE ^ v2) ? 0 : -1));
    }

    /* renamed from: uintDivide-J1ME1BU reason: not valid java name */
    public static final int m358uintDivideJ1ME1BU(int v1, int v2) {
        return UInt.m132constructorimpl((int) ((((long) v1) & 4294967295L) / (4294967295L & ((long) v2))));
    }

    /* renamed from: uintRemainder-J1ME1BU reason: not valid java name */
    public static final int m359uintRemainderJ1ME1BU(int v1, int v2) {
        return UInt.m132constructorimpl((int) ((((long) v1) & 4294967295L) % (4294967295L & ((long) v2))));
    }

    /* renamed from: ulongDivide-eb3DHEI reason: not valid java name */
    public static final long m360ulongDivideeb3DHEI(long v1, long v2) {
        long dividend = v1;
        long divisor = v2;
        long j = 0;
        if (divisor < 0) {
            if (ulongCompare(v1, v2) >= 0) {
                j = 1;
            }
            return ULong.m201constructorimpl(j);
        } else if (dividend >= 0) {
            return ULong.m201constructorimpl(dividend / divisor);
        } else {
            int i = 1;
            long quotient = ((dividend >>> 1) / divisor) << 1;
            if (ulongCompare(ULong.m201constructorimpl(dividend - (quotient * divisor)), ULong.m201constructorimpl(divisor)) < 0) {
                i = 0;
            }
            return ULong.m201constructorimpl(((long) i) + quotient);
        }
    }

    /* renamed from: ulongRemainder-eb3DHEI reason: not valid java name */
    public static final long m361ulongRemaindereb3DHEI(long v1, long v2) {
        long j;
        long dividend = v1;
        long divisor = v2;
        long j2 = 0;
        if (divisor < 0) {
            if (ulongCompare(v1, v2) < 0) {
                j = v1;
            } else {
                j = ULong.m201constructorimpl(v1 - v2);
            }
            return j;
        } else if (dividend >= 0) {
            return ULong.m201constructorimpl(dividend % divisor);
        } else {
            long rem = dividend - ((((dividend >>> 1) / divisor) << 1) * divisor);
            if (ulongCompare(ULong.m201constructorimpl(rem), ULong.m201constructorimpl(divisor)) >= 0) {
                j2 = divisor;
            }
            return ULong.m201constructorimpl(rem - j2);
        }
    }

    public static final int doubleToUInt(double v) {
        if (Double.isNaN(v)) {
            return 0;
        }
        if (v <= uintToDouble(0)) {
            return 0;
        }
        if (v >= uintToDouble(-1)) {
            return -1;
        }
        double d = (double) Integer.MAX_VALUE;
        if (v <= d) {
            return UInt.m132constructorimpl((int) v);
        }
        return UInt.m132constructorimpl(UInt.m132constructorimpl((int) (v - d)) + UInt.m132constructorimpl(Integer.MAX_VALUE));
    }

    public static final long doubleToULong(double v) {
        if (Double.isNaN(v)) {
            return 0;
        }
        if (v <= ulongToDouble(0)) {
            return 0;
        }
        if (v >= ulongToDouble(-1)) {
            return -1;
        }
        if (v < ((double) LongCompanionObject.MAX_VALUE)) {
            return ULong.m201constructorimpl((long) v);
        }
        return ULong.m201constructorimpl(ULong.m201constructorimpl((long) (v - 9.223372036854776E18d)) - Long.MIN_VALUE);
    }

    public static final double uintToDouble(int v) {
        return ((double) (Integer.MAX_VALUE & v)) + (((double) ((v >>> 31) << 30)) * ((double) 2));
    }

    public static final double ulongToDouble(long v) {
        return (((double) (v >>> 11)) * ((double) 2048)) + ((double) (2047 & v));
    }

    public static final String ulongToString(long v) {
        return ulongToString(v, 10);
    }

    public static final String ulongToString(long v, int base) {
        String str = "java.lang.Long.toString(this, checkRadix(radix))";
        if (v >= 0) {
            String l = Long.toString(v, CharsKt.checkRadix(base));
            Intrinsics.checkExpressionValueIsNotNull(l, str);
            return l;
        }
        long quotient = ((v >>> 1) / ((long) base)) << 1;
        long rem = v - (((long) base) * quotient);
        if (rem >= ((long) base)) {
            rem -= (long) base;
            quotient++;
        }
        StringBuilder sb = new StringBuilder();
        String l2 = Long.toString(quotient, CharsKt.checkRadix(base));
        Intrinsics.checkExpressionValueIsNotNull(l2, str);
        sb.append(l2);
        String l3 = Long.toString(rem, CharsKt.checkRadix(base));
        Intrinsics.checkExpressionValueIsNotNull(l3, str);
        sb.append(l3);
        return sb.toString();
    }
}
