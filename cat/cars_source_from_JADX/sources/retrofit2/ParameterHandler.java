package retrofit2;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.RequestBody;

abstract class ParameterHandler<T> {

    static final class Body<T> extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;
        private final Method method;

        /* renamed from: p */
        private final int f49p;

        Body(Method method2, int p, Converter<T, RequestBody> converter2) {
            this.method = method2;
            this.f49p = p;
            this.converter = converter2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) {
            if (value != null) {
                try {
                    builder.setBody((RequestBody) this.converter.convert(value));
                } catch (IOException e) {
                    Method method2 = this.method;
                    int i = this.f49p;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to convert ");
                    sb.append(value);
                    sb.append(" to RequestBody");
                    throw Utils.parameterError(method2, e, i, sb.toString(), new Object[0]);
                }
            } else {
                throw Utils.parameterError(this.method, this.f49p, "Body parameter value must not be null.", new Object[0]);
            }
        }
    }

    static final class Field<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        Field(String name2, Converter<T, String> valueConverter2, boolean encoded2) {
            this.name = (String) Objects.requireNonNull(name2, "name == null");
            this.valueConverter = valueConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) throws IOException {
            if (value != null) {
                String fieldValue = (String) this.valueConverter.convert(value);
                if (fieldValue != null) {
                    builder.addFormField(this.name, fieldValue, this.encoded);
                }
            }
        }
    }

    static final class FieldMap<T> extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Method method;

        /* renamed from: p */
        private final int f50p;
        private final Converter<T, String> valueConverter;

        FieldMap(Method method2, int p, Converter<T, String> valueConverter2, boolean encoded2) {
            this.method = method2;
            this.f50p = p;
            this.valueConverter = valueConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable Map<String, T> value) throws IOException {
            if (value != null) {
                for (Entry<String, T> entry : value.entrySet()) {
                    String entryKey = (String) entry.getKey();
                    if (entryKey != null) {
                        T entryValue = entry.getValue();
                        String str = "'.";
                        if (entryValue != null) {
                            String fieldEntry = (String) this.valueConverter.convert(entryValue);
                            if (fieldEntry != null) {
                                builder.addFormField(entryKey, fieldEntry, this.encoded);
                            } else {
                                Method method2 = this.method;
                                int i = this.f50p;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Field map value '");
                                sb.append(entryValue);
                                sb.append("' converted to null by ");
                                sb.append(this.valueConverter.getClass().getName());
                                sb.append(" for key '");
                                sb.append(entryKey);
                                sb.append(str);
                                throw Utils.parameterError(method2, i, sb.toString(), new Object[0]);
                            }
                        } else {
                            Method method3 = this.method;
                            int i2 = this.f50p;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Field map contained null value for key '");
                            sb2.append(entryKey);
                            sb2.append(str);
                            throw Utils.parameterError(method3, i2, sb2.toString(), new Object[0]);
                        }
                    } else {
                        throw Utils.parameterError(this.method, this.f50p, "Field map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw Utils.parameterError(this.method, this.f50p, "Field map was null.", new Object[0]);
        }
    }

    static final class Header<T> extends ParameterHandler<T> {
        private final String name;
        private final Converter<T, String> valueConverter;

        Header(String name2, Converter<T, String> valueConverter2) {
            this.name = (String) Objects.requireNonNull(name2, "name == null");
            this.valueConverter = valueConverter2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) throws IOException {
            if (value != null) {
                String headerValue = (String) this.valueConverter.convert(value);
                if (headerValue != null) {
                    builder.addHeader(this.name, headerValue);
                }
            }
        }
    }

    static final class HeaderMap<T> extends ParameterHandler<Map<String, T>> {
        private final Method method;

        /* renamed from: p */
        private final int f51p;
        private final Converter<T, String> valueConverter;

        HeaderMap(Method method2, int p, Converter<T, String> valueConverter2) {
            this.method = method2;
            this.f51p = p;
            this.valueConverter = valueConverter2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable Map<String, T> value) throws IOException {
            if (value != null) {
                for (Entry<String, T> entry : value.entrySet()) {
                    String headerName = (String) entry.getKey();
                    if (headerName != null) {
                        T headerValue = entry.getValue();
                        if (headerValue != null) {
                            builder.addHeader(headerName, (String) this.valueConverter.convert(headerValue));
                        } else {
                            Method method2 = this.method;
                            int i = this.f51p;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Header map contained null value for key '");
                            sb.append(headerName);
                            sb.append("'.");
                            throw Utils.parameterError(method2, i, sb.toString(), new Object[0]);
                        }
                    } else {
                        throw Utils.parameterError(this.method, this.f51p, "Header map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw Utils.parameterError(this.method, this.f51p, "Header map was null.", new Object[0]);
        }
    }

    static final class Headers extends ParameterHandler<okhttp3.Headers> {
        private final Method method;

        /* renamed from: p */
        private final int f52p;

        Headers(Method method2, int p) {
            this.method = method2;
            this.f52p = p;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable okhttp3.Headers headers) {
            if (headers != null) {
                builder.addHeaders(headers);
            } else {
                throw Utils.parameterError(this.method, this.f52p, "Headers parameter must not be null.", new Object[0]);
            }
        }
    }

    static final class Part<T> extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;
        private final okhttp3.Headers headers;
        private final Method method;

        /* renamed from: p */
        private final int f53p;

        Part(Method method2, int p, okhttp3.Headers headers2, Converter<T, RequestBody> converter2) {
            this.method = method2;
            this.f53p = p;
            this.headers = headers2;
            this.converter = converter2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) {
            if (value != null) {
                try {
                    builder.addPart(this.headers, (RequestBody) this.converter.convert(value));
                } catch (IOException e) {
                    Method method2 = this.method;
                    int i = this.f53p;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to convert ");
                    sb.append(value);
                    sb.append(" to RequestBody");
                    throw Utils.parameterError(method2, i, sb.toString(), e);
                }
            }
        }
    }

    static final class PartMap<T> extends ParameterHandler<Map<String, T>> {
        private final Method method;

        /* renamed from: p */
        private final int f54p;
        private final String transferEncoding;
        private final Converter<T, RequestBody> valueConverter;

        PartMap(Method method2, int p, Converter<T, RequestBody> valueConverter2, String transferEncoding2) {
            this.method = method2;
            this.f54p = p;
            this.valueConverter = valueConverter2;
            this.transferEncoding = transferEncoding2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable Map<String, T> value) throws IOException {
            if (value != null) {
                for (Entry<String, T> entry : value.entrySet()) {
                    String entryKey = (String) entry.getKey();
                    if (entryKey != null) {
                        T entryValue = entry.getValue();
                        if (entryValue != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("form-data; name=\"");
                            sb.append(entryKey);
                            sb.append("\"");
                            builder.addPart(okhttp3.Headers.m36of("Content-Disposition", sb.toString(), "Content-Transfer-Encoding", this.transferEncoding), (RequestBody) this.valueConverter.convert(entryValue));
                        } else {
                            Method method2 = this.method;
                            int i = this.f54p;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Part map contained null value for key '");
                            sb2.append(entryKey);
                            sb2.append("'.");
                            throw Utils.parameterError(method2, i, sb2.toString(), new Object[0]);
                        }
                    } else {
                        throw Utils.parameterError(this.method, this.f54p, "Part map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw Utils.parameterError(this.method, this.f54p, "Part map was null.", new Object[0]);
        }
    }

    static final class Path<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final Method method;
        private final String name;

        /* renamed from: p */
        private final int f55p;
        private final Converter<T, String> valueConverter;

        Path(Method method2, int p, String name2, Converter<T, String> valueConverter2, boolean encoded2) {
            this.method = method2;
            this.f55p = p;
            this.name = (String) Objects.requireNonNull(name2, "name == null");
            this.valueConverter = valueConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) throws IOException {
            if (value != null) {
                builder.addPathParam(this.name, (String) this.valueConverter.convert(value), this.encoded);
                return;
            }
            Method method2 = this.method;
            int i = this.f55p;
            StringBuilder sb = new StringBuilder();
            sb.append("Path parameter \"");
            sb.append(this.name);
            sb.append("\" value must not be null.");
            throw Utils.parameterError(method2, i, sb.toString(), new Object[0]);
        }
    }

    static final class Query<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        Query(String name2, Converter<T, String> valueConverter2, boolean encoded2) {
            this.name = (String) Objects.requireNonNull(name2, "name == null");
            this.valueConverter = valueConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) throws IOException {
            if (value != null) {
                String queryValue = (String) this.valueConverter.convert(value);
                if (queryValue != null) {
                    builder.addQueryParam(this.name, queryValue, this.encoded);
                }
            }
        }
    }

    static final class QueryMap<T> extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Method method;

        /* renamed from: p */
        private final int f56p;
        private final Converter<T, String> valueConverter;

        QueryMap(Method method2, int p, Converter<T, String> valueConverter2, boolean encoded2) {
            this.method = method2;
            this.f56p = p;
            this.valueConverter = valueConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable Map<String, T> value) throws IOException {
            if (value != null) {
                for (Entry<String, T> entry : value.entrySet()) {
                    String entryKey = (String) entry.getKey();
                    if (entryKey != null) {
                        T entryValue = entry.getValue();
                        String str = "'.";
                        if (entryValue != null) {
                            String convertedEntryValue = (String) this.valueConverter.convert(entryValue);
                            if (convertedEntryValue != null) {
                                builder.addQueryParam(entryKey, convertedEntryValue, this.encoded);
                            } else {
                                Method method2 = this.method;
                                int i = this.f56p;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Query map value '");
                                sb.append(entryValue);
                                sb.append("' converted to null by ");
                                sb.append(this.valueConverter.getClass().getName());
                                sb.append(" for key '");
                                sb.append(entryKey);
                                sb.append(str);
                                throw Utils.parameterError(method2, i, sb.toString(), new Object[0]);
                            }
                        } else {
                            Method method3 = this.method;
                            int i2 = this.f56p;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Query map contained null value for key '");
                            sb2.append(entryKey);
                            sb2.append(str);
                            throw Utils.parameterError(method3, i2, sb2.toString(), new Object[0]);
                        }
                    } else {
                        throw Utils.parameterError(this.method, this.f56p, "Query map contained null key.", new Object[0]);
                    }
                }
                return;
            }
            throw Utils.parameterError(this.method, this.f56p, "Query map was null", new Object[0]);
        }
    }

    static final class QueryName<T> extends ParameterHandler<T> {
        private final boolean encoded;
        private final Converter<T, String> nameConverter;

        QueryName(Converter<T, String> nameConverter2, boolean encoded2) {
            this.nameConverter = nameConverter2;
            this.encoded = encoded2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) throws IOException {
            if (value != null) {
                builder.addQueryParam((String) this.nameConverter.convert(value), null, this.encoded);
            }
        }
    }

    static final class RawPart extends ParameterHandler<okhttp3.MultipartBody.Part> {
        static final RawPart INSTANCE = new RawPart();

        private RawPart() {
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable okhttp3.MultipartBody.Part value) {
            if (value != null) {
                builder.addPart(value);
            }
        }
    }

    static final class RelativeUrl extends ParameterHandler<Object> {
        private final Method method;

        /* renamed from: p */
        private final int f57p;

        RelativeUrl(Method method2, int p) {
            this.method = method2;
            this.f57p = p;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable Object value) {
            if (value != null) {
                builder.setRelativeUrl(value);
            } else {
                throw Utils.parameterError(this.method, this.f57p, "@Url parameter is null.", new Object[0]);
            }
        }
    }

    static final class Tag<T> extends ParameterHandler<T> {
        final Class<T> cls;

        Tag(Class<T> cls2) {
            this.cls = cls2;
        }

        /* access modifiers changed from: 0000 */
        public void apply(RequestBuilder builder, @Nullable T value) {
            builder.addTag(this.cls, value);
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void apply(RequestBuilder requestBuilder, @Nullable T t) throws IOException;

    ParameterHandler() {
    }

    /* access modifiers changed from: 0000 */
    public final ParameterHandler<Iterable<T>> iterable() {
        return new ParameterHandler<Iterable<T>>() {
            /* access modifiers changed from: 0000 */
            public void apply(RequestBuilder builder, @Nullable Iterable<T> values) throws IOException {
                if (values != null) {
                    for (T value : values) {
                        ParameterHandler.this.apply(builder, value);
                    }
                }
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public final ParameterHandler<Object> array() {
        return new ParameterHandler<Object>() {
            /* access modifiers changed from: 0000 */
            public void apply(RequestBuilder builder, @Nullable Object values) throws IOException {
                if (values != null) {
                    int size = Array.getLength(values);
                    for (int i = 0; i < size; i++) {
                        ParameterHandler.this.apply(builder, Array.get(values, i));
                    }
                }
            }
        };
    }
}
