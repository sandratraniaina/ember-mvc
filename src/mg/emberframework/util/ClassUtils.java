package mg.emberframework.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassUtils {
    private static final Map<Class<?>, Object> DEFAULT_VALUES = new HashMap<>();

    static {
        // Primitive types
        DEFAULT_VALUES.put(Boolean.TYPE, false);
        DEFAULT_VALUES.put(Character.TYPE, '\u0000');
        DEFAULT_VALUES.put(Byte.TYPE, (byte) 0);
        DEFAULT_VALUES.put(Short.TYPE, (short) 0);
        DEFAULT_VALUES.put(Integer.TYPE, 0);
        DEFAULT_VALUES.put(Long.TYPE, 0L);
        DEFAULT_VALUES.put(Float.TYPE, 0.0f);
        DEFAULT_VALUES.put(Double.TYPE, 0.0);

        // Common reference types
        DEFAULT_VALUES.put(String.class, "");
        DEFAULT_VALUES.put(BigDecimal.class, BigDecimal.ZERO);
        DEFAULT_VALUES.put(BigInteger.class, BigInteger.ZERO);
        DEFAULT_VALUES.put(Date.class, null);
        DEFAULT_VALUES.put(LocalDate.class, null);
        DEFAULT_VALUES.put(LocalDateTime.class, null);
        DEFAULT_VALUES.put(List.class, Collections.emptyList());
        DEFAULT_VALUES.put(Set.class, Collections.emptySet());
        DEFAULT_VALUES.put(Map.class, Collections.emptyMap());
    }

    private ClassUtils() {
    }

    public static <T> T getDefaultValue(Class<T> clazz) {
        // Static map to avoid recreating the map on each method call

        @SuppressWarnings("unchecked")
        T defaultValue = (T) DEFAULT_VALUES.get(clazz);

        // Return null for any class not explicitly mapped
        return defaultValue;
    }
}
