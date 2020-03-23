package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KMutableProperty2.Setter;
import kotlin.reflect.KProperty2.Getter;

public abstract class MutablePropertyReference2 extends MutablePropertyReference implements KMutableProperty2 {
    /* access modifiers changed from: protected */
    public KCallable computeReflected() {
        return Reflection.mutableProperty2(this);
    }

    public Object invoke(Object receiver1, Object receiver2) {
        return get(receiver1, receiver2);
    }

    public Getter getGetter() {
        return ((KMutableProperty2) getReflected()).getGetter();
    }

    public Setter getSetter() {
        return ((KMutableProperty2) getReflected()).getSetter();
    }

    public Object getDelegate(Object receiver1, Object receiver2) {
        return ((KMutableProperty2) getReflected()).getDelegate(receiver1, receiver2);
    }
}
