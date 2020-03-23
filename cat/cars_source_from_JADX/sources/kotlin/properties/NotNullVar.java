package kotlin.properties;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J$\u0010\u0007\u001a\u00028\u00002\b\u0010\b\u001a\u0004\u0018\u00010\u00022\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0002¢\u0006\u0002\u0010\u000bJ,\u0010\f\u001a\u00020\r2\b\u0010\b\u001a\u0004\u0018\u00010\u00022\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u0005\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u000eR\u0012\u0010\u0005\u001a\u0004\u0018\u00018\u0000X\u000e¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\u000f"}, mo6929d2 = {"Lkotlin/properties/NotNullVar;", "T", "", "Lkotlin/properties/ReadWriteProperty;", "()V", "value", "Ljava/lang/Object;", "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "(Ljava/lang/Object;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: Delegates.kt */
final class NotNullVar<T> implements ReadWriteProperty<Object, T> {
    private T value;

    public T getValue(Object thisRef, KProperty<?> property) {
        Intrinsics.checkParameterIsNotNull(property, "property");
        T t = this.value;
        if (t != null) {
            return t;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Property ");
        sb.append(property.getName());
        sb.append(" should be initialized before get.");
        throw new IllegalStateException(sb.toString());
    }

    public void setValue(Object thisRef, KProperty<?> property, T value2) {
        Intrinsics.checkParameterIsNotNull(property, "property");
        Intrinsics.checkParameterIsNotNull(value2, "value");
        this.value = value2;
    }
}
