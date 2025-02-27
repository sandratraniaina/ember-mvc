package mg.emberframework.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import mg.emberframework.annotation.RequestParameter;
import mg.emberframework.manager.data.Session;
import mg.emberframework.manager.exception.AnnotationNotPresentException;
import mg.emberframework.manager.exception.InvalidRequestException;
import mg.emberframework.manager.url.Mapping;

public class ReflectUtils {
    private ReflectUtils() {
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

    public static void setSessionAttribute(Object object, HttpServletRequest request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String methodName = null; 
        for(Field field : object.getClass().getDeclaredFields()) {
            if (field.getType().equals(Session.class)) {
                methodName = getSetterMethod(field.getName());
                Session session = new Session(request.getSession());
                executeMethod(object, methodName, session);
            }
        }
    }

    public static Object executeRequestMethod(Mapping mapping, HttpServletRequest request, String verb)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException,
            AnnotationNotPresentException, InvalidRequestException, IOException, ServletException {
        List<Object> objects = new ArrayList<>();

        Class<?> objClass = mapping.getClazz();
        Object requestObject = objClass.getConstructor().newInstance();
        Method method = mapping.getSpecificVerbMethod(verb).getMethod();
        
        setSessionAttribute(requestObject, request);

        for (Parameter parameter : method.getParameters()) {
            Class<?> clazz = parameter.getType();
            Object object = ClassUtils.getDefaultValue(clazz);
            if (!parameter.isAnnotationPresent(RequestParameter.class) && !clazz.equals(Session.class)) {
                throw new AnnotationNotPresentException(
                        "One of you parameter require `@RequestParameter` annotation");
            }

            object = ObjectUtils.getParameterInstance(request, parameter, clazz, object);

            objects.add(object);
        }

        return executeMethod(requestObject, method.getName(), objects.toArray());
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

    public static Object executeMethod(Object object, String methodName, Object... args) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = object.getClass().getMethod(methodName, getArgsClasses(args));
        return method.invoke(object, args);
    }

    public static Object executeClassMethod(Class<?> clazz, String methodName, Object... args)
            throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            InstantiationException {
        Object object = clazz.getConstructor().newInstance();
        return executeMethod(object, methodName, args);
    }
}
