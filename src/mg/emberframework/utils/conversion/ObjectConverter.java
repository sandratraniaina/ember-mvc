package mg.emberframework.utils.conversion;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ObjectConverter {
    // Interface for conversion strategy
    private interface Converter<T> {
        T convert(String value);
    }
    
    // Static registry of converters using Factory pattern
    private static final Map<Class<?>, Converter<?>> CONVERTERS = new HashMap<>();
    
    // Initialize the converter registry
    static {
        // Primitive types
        CONVERTERS.put(Boolean.TYPE, Boolean::parseBoolean);
        CONVERTERS.put(Boolean.class, Boolean::valueOf);
        CONVERTERS.put(Byte.TYPE, Byte::parseByte);
        CONVERTERS.put(Byte.class, Byte::valueOf);
        CONVERTERS.put(Character.TYPE, s -> !s.isEmpty() ? s.charAt(0) : '\0');
        CONVERTERS.put(Character.class, s -> !s.isEmpty() ? Character.valueOf(s.charAt(0)) : null);
        CONVERTERS.put(Short.TYPE, Short::parseShort);
        CONVERTERS.put(Short.class, Short::valueOf);
        CONVERTERS.put(Integer.TYPE, Integer::parseInt);
        CONVERTERS.put(Integer.class, Integer::valueOf);
        CONVERTERS.put(Long.TYPE, Long::parseLong);
        CONVERTERS.put(Long.class, Long::valueOf);
        CONVERTERS.put(Float.TYPE, Float::parseFloat);
        CONVERTERS.put(Float.class, Float::valueOf);
        CONVERTERS.put(Double.TYPE, Double::parseDouble);
        CONVERTERS.put(Double.class, Double::valueOf);
        
        // Common reference types
        CONVERTERS.put(String.class, s -> s); // Identity conversion
        CONVERTERS.put(BigDecimal.class, BigDecimal::new);
        CONVERTERS.put(BigInteger.class, BigInteger::new);
        
        // Date types
        CONVERTERS.put(Date.class, s -> Date.valueOf(LocalDate.parse(s)));
        CONVERTERS.put(LocalDate.class, LocalDate::parse);
        CONVERTERS.put(LocalDateTime.class, LocalDateTime::parse);
        
        // UUID
        CONVERTERS.put(UUID.class, UUID::fromString);
    }
    
    /**
     * Converts a string value to the specified type.
     *
     * @param value The string value to convert
     * @param clazz The target class type
     * @return The converted object or null if value is null
     * @throws UnsupportedOperationException if no converter exists for the specified class
     * @throws IllegalArgumentException if the value cannot be converted to the specified type
     */
    @SuppressWarnings("unchecked")
    public static <T> T castObject(String value, Class<T> clazz) {
        if (value == null) {
            return null;
        }
        
        // Get converter from registry
        Converter<T> converter = (Converter<T>) CONVERTERS.get(clazz);
        
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