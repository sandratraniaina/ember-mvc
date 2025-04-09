package mg.emberframework.utils.conversion;

import java.lang.reflect.Constructor;

import mg.emberframework.utils.registry.ConverterRegistry;
import mg.emberframework.utils.registry.TypeRegistry;

public class ObjectConverter {
    private static final ConverterRegistry registry = new ConverterRegistry();
    private static final TypeRegistry typeRegistry = new TypeRegistry();

    private ObjectConverter() {
    }

    public static <T> Object castObject(String value, Class<T> clazz) {
        if (value == null || value.isEmpty()) {
            return typeRegistry.getDefaultValue(clazz);
        }

        // Get converter from registry
        ConverterRegistry.Converter<T> converter = registry.getConverter(clazz);

        if (converter != null) {
            try {
                return converter.convert(value);
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Cannot convert '" + value + "' to " + clazz.getSimpleName(), e);
            }
        }

        // If no converter found and the class has a string constructor, try to use it
        try {
            Constructor<T> constructor = clazz.getConstructor(String.class);
            return constructor.newInstance(value);
        } catch (Exception e) {
            throw new UnsupportedOperationException(
                    "No converter available for type: " + clazz.getName());
        }
    }
}