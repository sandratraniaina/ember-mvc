package mg.emberframework.utils.registry;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConverterRegistry {
    // Interface for conversion strategy
    public interface Converter<T> {
        T convert(String value);
    }
    
    private final Map<Class<?>, Converter<?>> converters = new HashMap<>();
    
    public ConverterRegistry() {
        registerDefaultConverters();
    }
    
    private void registerDefaultConverters() {
        // Primitive types
        converters.put(Boolean.TYPE, Boolean::parseBoolean);
        converters.put(Boolean.class, Boolean::valueOf);
        converters.put(Byte.TYPE, Byte::parseByte);
        converters.put(Byte.class, Byte::valueOf);
        converters.put(Character.TYPE, s -> !s.isEmpty() ? s.charAt(0) : '\0');
        converters.put(Character.class, s -> !s.isEmpty() ? Character.valueOf(s.charAt(0)) : null);
        converters.put(Short.TYPE, Short::parseShort);
        converters.put(Short.class, Short::valueOf);
        converters.put(Integer.TYPE, Integer::parseInt);
        converters.put(Integer.class, Integer::valueOf);
        converters.put(Long.TYPE, Long::parseLong);
        converters.put(Long.class, Long::valueOf);
        converters.put(Float.TYPE, Float::parseFloat);
        converters.put(Float.class, Float::valueOf);
        converters.put(Double.TYPE, Double::parseDouble);
        converters.put(Double.class, Double::valueOf);
        
        // Common reference types
        converters.put(String.class, s -> s); // Identity conversion
        converters.put(BigDecimal.class, BigDecimal::new);
        converters.put(BigInteger.class, BigInteger::new);
        
        // Date types
        converters.put(Date.class, s -> Date.valueOf(LocalDate.parse(s)));
        converters.put(LocalDate.class, LocalDate::parse);
        converters.put(LocalDateTime.class, LocalDateTime::parse);
        
        // UUID
        converters.put(UUID.class, UUID::fromString);
    }
    
    @SuppressWarnings("unchecked")
    public <T> Converter<T> getConverter(Class<T> clazz) {
        return (Converter<T>) converters.get(clazz);
    }
    
    // Optional: Method to allow adding custom converters
    public <T> void registerConverter(Class<T> clazz, Converter<T> converter) {
        converters.put(clazz, converter);
    }
}