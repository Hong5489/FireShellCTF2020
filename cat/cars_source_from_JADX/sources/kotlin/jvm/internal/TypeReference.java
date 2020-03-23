package kotlin.jvm.internal;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.KVariance;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0017\u001a\u00020\u0013H\u0002J\u0013\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u0013H\u0016J\f\u0010\u0017\u001a\u00020\u0013*\u00020\u0006H\u0002R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00058VX\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0011R\u001c\u0010\u0012\u001a\u00020\u0013*\u0006\u0012\u0002\b\u00030\u00148BX\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001e"}, mo6929d2 = {"Lkotlin/jvm/internal/TypeReference;", "Lkotlin/reflect/KType;", "classifier", "Lkotlin/reflect/KClassifier;", "arguments", "", "Lkotlin/reflect/KTypeProjection;", "isMarkedNullable", "", "(Lkotlin/reflect/KClassifier;Ljava/util/List;Z)V", "annotations", "", "getAnnotations", "()Ljava/util/List;", "getArguments", "getClassifier", "()Lkotlin/reflect/KClassifier;", "()Z", "arrayClassName", "", "Ljava/lang/Class;", "getArrayClassName", "(Ljava/lang/Class;)Ljava/lang/String;", "asString", "equals", "other", "", "hashCode", "", "toString", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: TypeReference.kt */
public final class TypeReference implements KType {
    private final List<KTypeProjection> arguments;
    private final KClassifier classifier;
    private final boolean isMarkedNullable;

    @Metadata(mo6927bv = {1, 0, 3}, mo6930k = 3, mo6931mv = {1, 1, 15})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0 = new int[KVariance.values().length];

        static {
            $EnumSwitchMapping$0[KVariance.INVARIANT.ordinal()] = 1;
            $EnumSwitchMapping$0[KVariance.IN.ordinal()] = 2;
            $EnumSwitchMapping$0[KVariance.OUT.ordinal()] = 3;
        }
    }

    public TypeReference(KClassifier classifier2, List<KTypeProjection> arguments2, boolean isMarkedNullable2) {
        Intrinsics.checkParameterIsNotNull(classifier2, "classifier");
        Intrinsics.checkParameterIsNotNull(arguments2, "arguments");
        this.classifier = classifier2;
        this.arguments = arguments2;
        this.isMarkedNullable = isMarkedNullable2;
    }

    public KClassifier getClassifier() {
        return this.classifier;
    }

    public List<KTypeProjection> getArguments() {
        return this.arguments;
    }

    public boolean isMarkedNullable() {
        return this.isMarkedNullable;
    }

    public List<Annotation> getAnnotations() {
        return CollectionsKt.emptyList();
    }

    public boolean equals(Object other) {
        return (other instanceof TypeReference) && Intrinsics.areEqual((Object) getClassifier(), (Object) ((TypeReference) other).getClassifier()) && Intrinsics.areEqual((Object) getArguments(), (Object) ((TypeReference) other).getArguments()) && isMarkedNullable() == ((TypeReference) other).isMarkedNullable();
    }

    public int hashCode() {
        return (((getClassifier().hashCode() * 31) + getArguments().hashCode()) * 31) + Boolean.valueOf(isMarkedNullable()).hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(asString());
        sb.append(" (Kotlin reflection is not available)");
        return sb.toString();
    }

    private final String asString() {
        String klass;
        String args;
        KClassifier classifier2 = getClassifier();
        Class cls = null;
        if (!(classifier2 instanceof KClass)) {
            classifier2 = null;
        }
        KClass kClass = (KClass) classifier2;
        if (kClass != null) {
            cls = JvmClassMappingKt.getJavaClass(kClass);
        }
        Class javaClass = cls;
        if (javaClass == null) {
            klass = getClassifier().toString();
        } else if (javaClass.isArray()) {
            klass = getArrayClassName(javaClass);
        } else {
            klass = javaClass.getName();
        }
        String nullable = "";
        if (getArguments().isEmpty()) {
            args = nullable;
        } else {
            args = CollectionsKt.joinToString$default(getArguments(), ", ", "<", ">", 0, null, new TypeReference$asString$args$1(this), 24, null);
        }
        if (isMarkedNullable()) {
            nullable = "?";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(klass);
        sb.append(args);
        sb.append(nullable);
        return sb.toString();
    }

    private final String getArrayClassName(Class<?> $this$arrayClassName) {
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) boolean[].class)) {
            return "kotlin.BooleanArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) char[].class)) {
            return "kotlin.CharArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) byte[].class)) {
            return "kotlin.ByteArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) short[].class)) {
            return "kotlin.ShortArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) int[].class)) {
            return "kotlin.IntArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) float[].class)) {
            return "kotlin.FloatArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) long[].class)) {
            return "kotlin.LongArray";
        }
        if (Intrinsics.areEqual((Object) $this$arrayClassName, (Object) double[].class)) {
            return "kotlin.DoubleArray";
        }
        return "kotlin.Array";
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        if (r0 != null) goto L_0x0025;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String asString(kotlin.reflect.KTypeProjection r4) {
        /*
            r3 = this;
            kotlin.reflect.KVariance r0 = r4.getVariance()
            if (r0 != 0) goto L_0x0009
            java.lang.String r0 = "*"
            return r0
        L_0x0009:
            kotlin.reflect.KType r0 = r4.getType()
            boolean r1 = r0 instanceof kotlin.jvm.internal.TypeReference
            if (r1 != 0) goto L_0x0012
            r0 = 0
        L_0x0012:
            kotlin.jvm.internal.TypeReference r0 = (kotlin.jvm.internal.TypeReference) r0
            if (r0 == 0) goto L_0x001d
            java.lang.String r0 = r0.asString()
            if (r0 == 0) goto L_0x001d
            goto L_0x0025
        L_0x001d:
            kotlin.reflect.KType r0 = r4.getType()
            java.lang.String r0 = java.lang.String.valueOf(r0)
        L_0x0025:
            kotlin.reflect.KVariance r1 = r4.getVariance()
            if (r1 == 0) goto L_0x0062
            int[] r2 = kotlin.jvm.internal.TypeReference.WhenMappings.$EnumSwitchMapping$0
            int r1 = r1.ordinal()
            r1 = r2[r1]
            r2 = 1
            if (r1 == r2) goto L_0x0060
            r2 = 2
            if (r1 == r2) goto L_0x004e
            r2 = 3
            if (r1 != r2) goto L_0x0062
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "out "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            goto L_0x0061
        L_0x004e:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "in "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            goto L_0x0061
        L_0x0060:
            r1 = r0
        L_0x0061:
            return r1
        L_0x0062:
            kotlin.NoWhenBranchMatchedException r1 = new kotlin.NoWhenBranchMatchedException
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.jvm.internal.TypeReference.asString(kotlin.reflect.KTypeProjection):java.lang.String");
    }
}
