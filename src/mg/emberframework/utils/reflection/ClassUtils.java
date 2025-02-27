package mg.emberframework.utils.reflection;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mg.emberframework.manager.data.File;
import mg.emberframework.manager.data.Session;

public class ClassUtils {
    private static final Map<Class<?>, Object> DEFAULT_VALUES = new HashMap<>();
    private static final Set<Class<?>> NON_MODEL_CLASSES = new HashSet<>();

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

        NON_MODEL_CLASSES.addAll(Arrays.asList(
                // System/utility classes
                Session.class,
                File.class,
                Class.class,

                // Collections and arrays
                Collection.class,
                List.class,
                Set.class,
                Map.class,

                // Add any other classes that should not be considered models
                InputStream.class,
                OutputStream.class,
                Reader.class,
                Writer.class));
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

    public static boolean isPrimitive(Class<?> clazz) {
        // String is not actually a primitive type in Java
        if (clazz == null) {
            return false;
        }

        // Use the built-in isPrimitive() method for actual primitive checks
        if (clazz.isPrimitive()) {
            return true;
        }

        // If you want to include String and wrapper classes as "primitives" for your
        // application logic:
        return clazz == String.class ||
                clazz == Boolean.class ||
                clazz == Character.class ||
                clazz == Byte.class ||
                clazz == Short.class ||
                clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Float.class ||
                clazz == Double.class;
    }

    public static boolean isClassModel(Class<?> type) {
        if (type == null) {
            return false;
        }

        // First check if it's a primitive or common utility type
        if (isPrimitive(type)) {
            return false;
        }

        // Check if the class is in the exclusion list
        return !NON_MODEL_CLASSES.contains(type) &&
        // You might also want to exclude arrays and collections
                !type.isArray() &&
                !Collection.class.isAssignableFrom(type) &&
                !Map.class.isAssignableFrom(type);
    }

    public static boolean hasAttributeOfType(Class<?> clazz, Class<?> type) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static String getMethodName(String initial, String attributeName) {
        return initial + Character.toUpperCase(attributeName.charAt(0)) + attributeName.substring(1);
    }

    public static String getSetterMethod(String attributeName) {
        return getMethodName("set", attributeName);
    }

    public static Class<?>[] getArgsClasses(Object... args) {
        Class<?>[] classes = new Class[args.length];
        int i = 0;

        for (Object object : args) {
            classes[i] = object.getClass();
            i++;
        }

        return classes;
    }
}
