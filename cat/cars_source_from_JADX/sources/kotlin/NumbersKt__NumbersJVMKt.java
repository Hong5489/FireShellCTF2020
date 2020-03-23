package kotlin;

import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000*\n\u0000\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0001H\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\b\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0001H\b\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0002H\b\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0001H\b\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0002H\b\u001a\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\u0002H\b\u001a\u0015\u0010\u0005\u001a\u00020\t*\u00020\n2\u0006\u0010\b\u001a\u00020\u0001H\b\u001a\r\u0010\u000b\u001a\u00020\f*\u00020\u0006H\b\u001a\r\u0010\u000b\u001a\u00020\f*\u00020\tH\b\u001a\r\u0010\r\u001a\u00020\f*\u00020\u0006H\b\u001a\r\u0010\r\u001a\u00020\f*\u00020\tH\b\u001a\r\u0010\u000e\u001a\u00020\f*\u00020\u0006H\b\u001a\r\u0010\u000e\u001a\u00020\f*\u00020\tH\b\u001a\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0001H\b\u001a\u0015\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0001H\b\u001a\u0015\u0010\u0011\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0001H\b\u001a\u0015\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0001H\b\u001a\r\u0010\u0012\u001a\u00020\u0001*\u00020\u0001H\b\u001a\r\u0010\u0012\u001a\u00020\u0002*\u00020\u0002H\b\u001a\r\u0010\u0013\u001a\u00020\u0001*\u00020\u0001H\b\u001a\r\u0010\u0013\u001a\u00020\u0002*\u00020\u0002H\b\u001a\r\u0010\u0014\u001a\u00020\u0002*\u00020\u0006H\b\u001a\r\u0010\u0014\u001a\u00020\u0001*\u00020\tH\b\u001a\r\u0010\u0015\u001a\u00020\u0002*\u00020\u0006H\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\tH\b¨\u0006\u0016"}, mo6929d2 = {"countLeadingZeroBits", "", "", "countOneBits", "countTrailingZeroBits", "fromBits", "", "Lkotlin/Double$Companion;", "bits", "", "Lkotlin/Float$Companion;", "isFinite", "", "isInfinite", "isNaN", "rotateLeft", "bitCount", "rotateRight", "takeHighestOneBit", "takeLowestOneBit", "toBits", "toRawBits", "kotlin-stdlib"}, mo6930k = 5, mo6931mv = {1, 1, 15}, mo6933xi = 1, mo6934xs = "kotlin/NumbersKt")
/* compiled from: NumbersJVM.kt */
class NumbersKt__NumbersJVMKt extends NumbersKt__BigIntegersKt {
    private static final boolean isNaN(double $this$isNaN) {
        return Double.isNaN($this$isNaN);
    }

    private static final boolean isNaN(float $this$isNaN) {
        return Float.isNaN($this$isNaN);
    }

    private static final boolean isInfinite(double $this$isInfinite) {
        return Double.isInfinite($this$isInfinite);
    }

    private static final boolean isInfinite(float $this$isInfinite) {
        return Float.isInfinite($this$isInfinite);
    }

    private static final boolean isFinite(double $this$isFinite) {
        return !Double.isInfinite($this$isFinite) && !Double.isNaN($this$isFinite);
    }

    private static final boolean isFinite(float $this$isFinite) {
        return !Float.isInfinite($this$isFinite) && !Float.isNaN($this$isFinite);
    }

    private static final long toBits(double $this$toBits) {
        return Double.doubleToLongBits($this$toBits);
    }

    private static final long toRawBits(double $this$toRawBits) {
        return Double.doubleToRawLongBits($this$toRawBits);
    }

    private static final double fromBits(DoubleCompanionObject $this$fromBits, long bits) {
        return Double.longBitsToDouble(bits);
    }

    private static final int toBits(float $this$toBits) {
        return Float.floatToIntBits($this$toBits);
    }

    private static final int toRawBits(float $this$toRawBits) {
        return Float.floatToRawIntBits($this$toRawBits);
    }

    private static final float fromBits(FloatCompanionObject $this$fromBits, int bits) {
        return Float.intBitsToFloat(bits);
    }

    private static final int countOneBits(int $this$countOneBits) {
        return Integer.bitCount($this$countOneBits);
    }

    private static final int countLeadingZeroBits(int $this$countLeadingZeroBits) {
        return Integer.numberOfLeadingZeros($this$countLeadingZeroBits);
    }

    private static final int countTrailingZeroBits(int $this$countTrailingZeroBits) {
        return Integer.numberOfTrailingZeros($this$countTrailingZeroBits);
    }

    private static final int takeHighestOneBit(int $this$takeHighestOneBit) {
        return Integer.highestOneBit($this$takeHighestOneBit);
    }

    private static final int takeLowestOneBit(int $this$takeLowestOneBit) {
        return Integer.lowestOneBit($this$takeLowestOneBit);
    }

    private static final int rotateLeft(int $this$rotateLeft, int bitCount) {
        return Integer.rotateLeft($this$rotateLeft, bitCount);
    }

    private static final int rotateRight(int $this$rotateRight, int bitCount) {
        return Integer.rotateRight($this$rotateRight, bitCount);
    }

    private static final int countOneBits(long $this$countOneBits) {
        return Long.bitCount($this$countOneBits);
    }

    private static final int countLeadingZeroBits(long $this$countLeadingZeroBits) {
        return Long.numberOfLeadingZeros($this$countLeadingZeroBits);
    }

    private static final int countTrailingZeroBits(long $this$countTrailingZeroBits) {
        return Long.numberOfTrailingZeros($this$countTrailingZeroBits);
    }

    private static final long takeHighestOneBit(long $this$takeHighestOneBit) {
        return Long.highestOneBit($this$takeHighestOneBit);
    }

    private static final long takeLowestOneBit(long $this$takeLowestOneBit) {
        return Long.lowestOneBit($this$takeLowestOneBit);
    }

    private static final long rotateLeft(long $this$rotateLeft, int bitCount) {
        return Long.rotateLeft($this$rotateLeft, bitCount);
    }

    private static final long rotateRight(long $this$rotateRight, int bitCount) {
        return Long.rotateRight($this$rotateRight, bitCount);
    }
}
