package mg.emberframework.util.reflection;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import mg.emberframework.annotation.RequestParameter;
import mg.emberframework.manager.data.File;
import mg.emberframework.manager.data.Session;
import mg.emberframework.util.conversion.ObjectConverter;
import mg.emberframework.util.io.FileUtils;

public class ObjectUtils {
    private ObjectUtils() {
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
                object = strValue != null ? ObjectConverter.castObject(strValue, clazz) : object;
            } else {
                String paramName = parameter.getName();
                strValue = request.getParameter(paramName);
                if (strValue != null) {
                    object = ObjectConverter.castObject(strValue, clazz);
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

            ReflectUtils.setObjectAttributesValues(instance, field, value);
        }

        return instance;
    }
}
