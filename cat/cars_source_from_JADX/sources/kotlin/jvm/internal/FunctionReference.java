package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

public class FunctionReference extends CallableReference implements FunctionBase, KFunction {
    private final int arity;

    public FunctionReference(int arity2) {
        this.arity = arity2;
    }

    public FunctionReference(int arity2, Object receiver) {
        super(receiver);
        this.arity = arity2;
    }

    public int getArity() {
        return this.arity;
    }

    /* access modifiers changed from: protected */
    public KFunction getReflected() {
        return (KFunction) super.getReflected();
    }

    /* access modifiers changed from: protected */
    public KCallable computeReflected() {
        return Reflection.function(this);
    }

    public boolean isInline() {
        return getReflected().isInline();
    }

    public boolean isExternal() {
        return getReflected().isExternal();
    }

    public boolean isOperator() {
        return getReflected().isOperator();
    }

    public boolean isInfix() {
        return getReflected().isInfix();
    }

    public boolean isSuspend() {
        return getReflected().isSuspend();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004f, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(getBoundReceiver(), r1.getBoundReceiver()) != false) goto L_0x0053;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r6) {
        /*
            r5 = this;
            r0 = 1
            if (r6 != r5) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r6 instanceof kotlin.jvm.internal.FunctionReference
            r2 = 0
            if (r1 == 0) goto L_0x0054
            r1 = r6
            kotlin.jvm.internal.FunctionReference r1 = (kotlin.jvm.internal.FunctionReference) r1
            kotlin.reflect.KDeclarationContainer r3 = r5.getOwner()
            if (r3 != 0) goto L_0x0019
            kotlin.reflect.KDeclarationContainer r3 = r1.getOwner()
            if (r3 != 0) goto L_0x0052
            goto L_0x0027
        L_0x0019:
            kotlin.reflect.KDeclarationContainer r3 = r5.getOwner()
            kotlin.reflect.KDeclarationContainer r4 = r1.getOwner()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0052
        L_0x0027:
            java.lang.String r3 = r5.getName()
            java.lang.String r4 = r1.getName()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0052
            java.lang.String r3 = r5.getSignature()
            java.lang.String r4 = r1.getSignature()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0052
            java.lang.Object r3 = r5.getBoundReceiver()
            java.lang.Object r4 = r1.getBoundReceiver()
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4)
            if (r3 == 0) goto L_0x0052
            goto L_0x0053
        L_0x0052:
            r0 = r2
        L_0x0053:
            return r0
        L_0x0054:
            boolean r0 = r6 instanceof kotlin.reflect.KFunction
            if (r0 == 0) goto L_0x0061
            kotlin.reflect.KCallable r0 = r5.compute()
            boolean r0 = r6.equals(r0)
            return r0
        L_0x0061:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.jvm.internal.FunctionReference.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return (((getOwner() == null ? 0 : getOwner().hashCode() * 31) + getName().hashCode()) * 31) + getSignature().hashCode();
    }

    public String toString() {
        String str;
        KCallable reflected = compute();
        if (reflected != this) {
            return reflected.toString();
        }
        if ("<init>".equals(getName())) {
            str = "constructor (Kotlin reflection is not available)";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("function ");
            sb.append(getName());
            sb.append(" (Kotlin reflection is not available)");
            str = sb.toString();
        }
        return str;
    }
}
