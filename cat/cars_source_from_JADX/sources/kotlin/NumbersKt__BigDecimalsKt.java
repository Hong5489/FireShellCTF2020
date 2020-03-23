package kotlin;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0001H\n\u001a\u0015\u0010\u0002\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\n\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0001H\n\u001a\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\n\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\n\u001a\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\n\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\n\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\n\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000bH\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000eH\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\f\u001a\u00020\rH\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000fH\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000f2\u0006\u0010\f\u001a\u00020\rH\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u0010H\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\b\u001a\r\u0010\u0011\u001a\u00020\u0001*\u00020\u0001H\n¨\u0006\u0012"}, mo6929d2 = {"dec", "Ljava/math/BigDecimal;", "div", "other", "inc", "minus", "mod", "plus", "rem", "times", "toBigDecimal", "", "mathContext", "Ljava/math/MathContext;", "", "", "", "unaryMinus", "kotlin-stdlib"}, mo6930k = 5, mo6931mv = {1, 1, 15}, mo6933xi = 1, mo6934xs = "kotlin/NumbersKt")
/* compiled from: BigDecimals.kt */
class NumbersKt__BigDecimalsKt {
    private static final BigDecimal plus(BigDecimal $this$plus, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        BigDecimal add = $this$plus.add(other);
        Intrinsics.checkExpressionValueIsNotNull(add, "this.add(other)");
        return add;
    }

    private static final BigDecimal minus(BigDecimal $this$minus, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
        BigDecimal subtract = $this$minus.subtract(other);
        Intrinsics.checkExpressionValueIsNotNull(subtract, "this.subtract(other)");
        return subtract;
    }

    private static final BigDecimal times(BigDecimal $this$times, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($this$times, "$this$times");
        BigDecimal multiply = $this$times.multiply(other);
        Intrinsics.checkExpressionValueIsNotNull(multiply, "this.multiply(other)");
        return multiply;
    }

    private static final BigDecimal div(BigDecimal $this$div, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($this$div, "$this$div");
        BigDecimal divide = $this$div.divide(other, RoundingMode.HALF_EVEN);
        Intrinsics.checkExpressionValueIsNotNull(divide, "this.divide(other, RoundingMode.HALF_EVEN)");
        return divide;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use rem(other) instead", replaceWith = @ReplaceWith(expression = "rem(other)", imports = {}))
    private static final BigDecimal mod(BigDecimal $this$mod, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($this$mod, "$this$mod");
        BigDecimal remainder = $this$mod.remainder(other);
        Intrinsics.checkExpressionValueIsNotNull(remainder, "this.remainder(other)");
        return remainder;
    }

    private static final BigDecimal rem(BigDecimal $this$rem, BigDecimal other) {
        Intrinsics.checkParameterIsNotNull($this$rem, "$this$rem");
        BigDecimal remainder = $this$rem.remainder(other);
        Intrinsics.checkExpressionValueIsNotNull(remainder, "this.remainder(other)");
        return remainder;
    }

    private static final BigDecimal unaryMinus(BigDecimal $this$unaryMinus) {
        Intrinsics.checkParameterIsNotNull($this$unaryMinus, "$this$unaryMinus");
        BigDecimal negate = $this$unaryMinus.negate();
        Intrinsics.checkExpressionValueIsNotNull(negate, "this.negate()");
        return negate;
    }

    private static final BigDecimal inc(BigDecimal $this$inc) {
        Intrinsics.checkParameterIsNotNull($this$inc, "$this$inc");
        BigDecimal add = $this$inc.add(BigDecimal.ONE);
        Intrinsics.checkExpressionValueIsNotNull(add, "this.add(BigDecimal.ONE)");
        return add;
    }

    private static final BigDecimal dec(BigDecimal $this$dec) {
        Intrinsics.checkParameterIsNotNull($this$dec, "$this$dec");
        BigDecimal subtract = $this$dec.subtract(BigDecimal.ONE);
        Intrinsics.checkExpressionValueIsNotNull(subtract, "this.subtract(BigDecimal.ONE)");
        return subtract;
    }

    private static final BigDecimal toBigDecimal(int $this$toBigDecimal) {
        BigDecimal valueOf = BigDecimal.valueOf((long) $this$toBigDecimal);
        Intrinsics.checkExpressionValueIsNotNull(valueOf, "BigDecimal.valueOf(this.toLong())");
        return valueOf;
    }

    private static final BigDecimal toBigDecimal(int $this$toBigDecimal, MathContext mathContext) {
        return new BigDecimal($this$toBigDecimal, mathContext);
    }

    private static final BigDecimal toBigDecimal(long $this$toBigDecimal) {
        BigDecimal valueOf = BigDecimal.valueOf($this$toBigDecimal);
        Intrinsics.checkExpressionValueIsNotNull(valueOf, "BigDecimal.valueOf(this)");
        return valueOf;
    }

    private static final BigDecimal toBigDecimal(long $this$toBigDecimal, MathContext mathContext) {
        return new BigDecimal($this$toBigDecimal, mathContext);
    }

    private static final BigDecimal toBigDecimal(float $this$toBigDecimal) {
        return new BigDecimal(String.valueOf($this$toBigDecimal));
    }

    private static final BigDecimal toBigDecimal(float $this$toBigDecimal, MathContext mathContext) {
        return new BigDecimal(String.valueOf($this$toBigDecimal), mathContext);
    }

    private static final BigDecimal toBigDecimal(double $this$toBigDecimal) {
        return new BigDecimal(String.valueOf($this$toBigDecimal));
    }

    private static final BigDecimal toBigDecimal(double $this$toBigDecimal, MathContext mathContext) {
        return new BigDecimal(String.valueOf($this$toBigDecimal), mathContext);
    }
}
