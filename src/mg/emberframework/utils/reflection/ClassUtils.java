package mg.emberframework.utils.reflection;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import mg.emberframework.utils.registry.*;

public class ClassUtils {
    private static final TypeRegistry REGISTRY = new TypeRegistry();

    private ClassUtils() {
    }

    public static <T> T getDefaultValue(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        T defaultValue = (T) REGISTRY.getDefaultValue(clazz);
        return defaultValue;
    }

    public static boolean isPrimitive(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        if (clazz.isPrimitive()) {
            return true;
        }

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

        if (isPrimitive(type)) {
            return false;
        }

        return !REGISTRY.isNonModelClass(type) &&
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

    // Optional: Expose registry modification methods if needed
    public static void registerCustomDefaultValue(Class<?> clazz, Object value) {
        REGISTRY.registerDefaultValue(clazz, value);
    }

    public static void registerCustomNonModelClass(Class<?> clazz) {
        REGISTRY.registerNonModelClass(clazz);
    }
}