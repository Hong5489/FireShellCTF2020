package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import javax.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter.Factory;

final class OptionalConverterFactory extends Factory {
    static final Factory INSTANCE = new OptionalConverterFactory();

    static final class OptionalConverter<T> implements Converter<ResponseBody, Optional<T>> {
        final Converter<ResponseBody, T> delegate;

        OptionalConverter(Converter<ResponseBody, T> delegate2) {
            this.delegate = delegate2;
        }

        public Optional<T> convert(ResponseBody value) throws IOException {
            return Optional.ofNullable(this.delegate.convert(value));
        }
    }

    OptionalConverterFactory() {
    }

    @Nullable
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(type) != Optional.class) {
            return null;
        }
        return new OptionalConverter(retrofit.responseBodyConverter(getParameterUpperBound(0, (ParameterizedType) type), annotations));
    }
}
