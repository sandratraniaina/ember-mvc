package mg.emberframework.utils.registry;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
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

import mg.emberframework.core.data.Session;

public class TypeRegistry {
    private final Map<Class<?>, Object> defaultValues = new HashMap<>();
    private final Set<Class<?>> nonModelClasses = new HashSet<>();

    public TypeRegistry() {
        // Initialize default values
        registerDefaultValues();
        registerNonModelClasses();
    }

    private void registerDefaultValues() {
        // Primitive types
        defaultValues.put(Boolean.TYPE, false);
        defaultValues.put(Character.TYPE, '\u0000');
        defaultValues.put(Byte.TYPE, (byte) 0);
        defaultValues.put(Short.TYPE, (short) 0);
        defaultValues.put(Integer.TYPE, 0);
        defaultValues.put(Long.TYPE, 0L);
        defaultValues.put(Float.TYPE, 0.0f);
        defaultValues.put(Double.TYPE, 0.0);

        // Common reference types
        defaultValues.put(String.class, "");
        defaultValues.put(BigDecimal.class, BigDecimal.ZERO);
        defaultValues.put(BigInteger.class, BigInteger.ZERO);
        defaultValues.put(Date.class, null);
        defaultValues.put(LocalDate.class, null);
        defaultValues.put(LocalDateTime.class, null);
        defaultValues.put(List.class, Collections.emptyList());
        defaultValues.put(Set.class, Collections.emptySet());
        defaultValues.put(Map.class, Collections.emptyMap());
    }

    private void registerNonModelClasses() {
        nonModelClasses.addAll(Arrays.asList(
                // System/utility classes
                Session.class,
                File.class,
                Class.class,

                // Collections and arrays
                Collection.class,
                List.class,
                Set.class,
                Map.class,

                // IO classes
                InputStream.class,
                OutputStream.class,
                Reader.class,
                Writer.class));
    }

    public Object getDefaultValue(Class<?> clazz) {
        return defaultValues.get(clazz);
    }

    public boolean isNonModelClass(Class<?> clazz) {
        return nonModelClasses.contains(clazz);
    }

    // Optional: Methods to allow runtime modification
    public void registerDefaultValue(Class<?> clazz, Object value) {
        defaultValues.put(clazz, value);
    }

    public void registerNonModelClass(Class<?> clazz) {
        nonModelClasses.add(clazz);
    }
}
