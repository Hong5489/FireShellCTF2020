package kotlin.experimental;

import kotlin.Metadata;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\n\n\u0002\b\u0004\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\f\u001a\u0015\u0010\u0000\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\f\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0001H\b\u001a\r\u0010\u0004\u001a\u00020\u0003*\u00020\u0003H\b\u001a\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\f\u001a\u0015\u0010\u0005\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\f\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\f\u001a\u0015\u0010\u0006\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\f¨\u0006\u0007"}, mo6929d2 = {"and", "", "other", "", "inv", "or", "xor", "kotlin-stdlib"}, mo6930k = 2, mo6931mv = {1, 1, 15})
/* compiled from: bitwiseOperations.kt */
public final class BitwiseOperationsKt {
    private static final byte and(byte $this$and, byte other) {
        return (byte) ($this$and & other);
    }

    /* renamed from: or */
    private static final byte m31or(byte $this$or, byte other) {
        return (byte) ($this$or | other);
    }

    private static final byte xor(byte $this$xor, byte other) {
        return (byte) ($this$xor ^ other);
    }

    private static final byte inv(byte $this$inv) {
        return (byte) (~$this$inv);
    }

    private static final short and(short $this$and, short other) {
        return (short) ($this$and & other);
    }

    /* renamed from: or */
    private static final short m32or(short $this$or, short other) {
        return (short) ($this$or | other);
    }

    private static final short xor(short $this$xor, short other) {
        return (short) ($this$xor ^ other);
    }

    private static final short inv(short $this$inv) {
        return (short) (~$this$inv);
    }
}
