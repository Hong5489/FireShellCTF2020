package retrofit2;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

abstract class ServiceMethod<T> {
    /* access modifiers changed from: 0000 */
    @Nullable
    public abstract T invoke(Object[] objArr);

    ServiceMethod() {
    }

    static <T> ServiceMethod<T> parseAnnotations(Retrofit retrofit, Method method) {
        RequestFactory requestFactory = RequestFactory.parseAnnotations(retrofit, method);
        Type returnType = method.getGenericReturnType();
        if (Utils.hasUnresolvableType(returnType)) {
            throw Utils.methodError(method, "Method return type must not include a type variable or wildcard: %s", returnType);
        } else if (returnType != Void.TYPE) {
            return HttpServiceMethod.parseAnnotations(retrofit, method, requestFactory);
        } else {
            throw Utils.methodError(method, "Service methods cannot return void.", new Object[0]);
        }
    }
}
