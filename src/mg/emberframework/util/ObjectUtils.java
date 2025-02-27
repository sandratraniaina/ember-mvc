package mg.emberframework.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Date;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import mg.emberframework.annotation.RequestParameter;
import mg.emberframework.manager.data.File;
import mg.emberframework.manager.data.Session;

public class ObjectUtils {
    private ObjectUtils() {
    }

    public static boolean isClassModel(Class<?> type) {
        return !ClassUtils.isPrimitive(type) && !type.equals(Session.class) && !type.equals(File.class);
    }

    public static Object getParameterInstance(HttpServletRequest request, Parameter parameter, Class<?> clazz,
            Object object)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            IOException, ServletException, IllegalArgumentException, SecurityException {
        String strValue;

        RequestParameter annotatedType = parameter.getAnnotation(RequestParameter.class);
        String annotationValue = annotatedType != null ? annotatedType.value() : "";

        if (ClassUtils.isPrimitive(clazz)) {

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
            object = ObjectUtils.getObjectInstance(clazz, annotationValue, request);
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
            NoSuchMethodException, SecurityException {
        Object instance = classType.getConstructor().newInstance();
        Field[] fields = classType.getDeclaredFields();

        String className = null;
        String paramName = null;

        className = annotationValue.split("\\.")[0] + ".";

        for (Field field : fields) {
            paramName = className + field.getName();
            String value = request.getParameter(paramName);

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
}
