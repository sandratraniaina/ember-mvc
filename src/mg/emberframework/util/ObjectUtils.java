package mg.emberframework.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import mg.emberframework.annotation.RequestParameter;
import mg.emberframework.manager.data.File;
import mg.emberframework.manager.data.Session;
import mg.emberframework.manager.exception.ModelValidationException;
import mg.emberframework.util.validation.Validator;

public class ObjectUtils {
    private ObjectUtils() {
    }

    public static Object getParameterInstance(HttpServletRequest request, Parameter parameter, Class<?> clazz,
            Object object)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, ServletException, IllegalArgumentException, SecurityException,
            ModelValidationException {
        String strValue;

        RequestParameter annotatedType = parameter.getAnnotation(RequestParameter.class);
        String annotationValue = annotatedType != null ? annotatedType.value() : "";

        if (ObjectUtils.isPrimitive(clazz)) {

            if (parameter.isAnnotationPresent(RequestParameter.class)) {
                strValue = request.getParameter(annotationValue);
                object = strValue != null ? ObjectUtils.castObject(strValue, clazz) : object;
            } else {
                String paramName = parameter.getName();
                strValue = request.getParameter(paramName);
                if (strValue != null) {
                    object = ObjectUtils.castObject(strValue, clazz);
                }
            }
        } else if (clazz.equals(Session.class)) {
            object = new Session(request.getSession());
        } else if (clazz.equals(File.class)) {
            object = FileUtils.createRequestFile(annotationValue, request);
        } else {

            if (parameter.isAnnotationPresent(RequestParameter.class)) {
                object = ObjectUtils.getObjectInstance(clazz, annotationValue, request);
            }
        }
        return object;
    }

    private static void setObjectAttributesValues(Object instance, Field field, String value)
            throws SecurityException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        Object fieldValue = castObject(value, field.getType());
        String setterMethodName = ReflectUtils.getSetterMethod(field.getName());
        Method method = instance.getClass().getMethod(setterMethodName, field.getType());
        method.invoke(instance, fieldValue);
    }

    public static Object getObjectInstance(Class<?> classType, String annotationValue, HttpServletRequest request)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, ModelValidationException {
        Object instance = classType.getConstructor().newInstance();
        Field[] fields = classType.getDeclaredFields();

        String className = null;
        String paramName = null;

        className = annotationValue.split("\\.")[0] + ".";

        for (Field field : fields) {
            paramName = className + field.getName();
            String value = request.getParameter(paramName);

            Validator.checkField(value, field);

            setObjectAttributesValues(instance, field, value);
        }

        return instance;
    }

    public static Object castObject(String value, Class<?> clazz) {
        if (value == null) {
            return null;
        } else if (clazz == Integer.TYPE) {
            return Integer.parseInt(value);
        } else if (clazz == Double.TYPE) {
            return Double.parseDouble(value);
        } else if (clazz == Float.TYPE) {
            return Float.parseFloat(value);
        } else if (clazz == Date.class) {
            return Date.valueOf(LocalDate.parse(value));
        } else {
            return value;
        }
    }

    public static boolean isPrimitive(Class<?> clazz) {
        List<Class<?>> primitiveTypes = new ArrayList<>();
        primitiveTypes.add(Integer.TYPE);
        primitiveTypes.add(Double.TYPE);
        primitiveTypes.add(String.class);

        return primitiveTypes.contains(clazz);
    }

    public static Object getDefaultValue(Class<?> clazz) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return getDefaultValue(clazz.getConstructor().newInstance());
    }

    public static Object getDefaultValue(Object object) {
        HashMap<Class<?>, Object> keyValues = new HashMap<>();
        keyValues.put(Integer.TYPE, 0);
        keyValues.put(Double.TYPE, 0.0);
        keyValues.put(String.class, "");
        keyValues.put(Date.class, null);

        return keyValues.get(object.getClass());
    }
}
