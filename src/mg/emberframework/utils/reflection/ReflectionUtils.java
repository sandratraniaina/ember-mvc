package mg.emberframework.utils.reflection;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import mg.emberframework.annotation.http.RequestParameter;
import mg.emberframework.core.data.Session;
import mg.emberframework.core.exception.AnnotationNotPresentException;
import mg.emberframework.core.exception.InvalidRequestException;
import mg.emberframework.core.url.Mapping;
import mg.emberframework.utils.conversion.ObjectConverter;

public class ReflectionUtils {
    private ReflectionUtils() {
    }

    public static void setObjectAttributesValues(Object instance, Field field, String value)
            throws SecurityException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        Object fieldValue = ObjectConverter.castObject(value, field.getType());
        String setterMethodName = ClassUtils.getSetterMethod(field.getName());
        Method method = instance.getClass().getMethod(setterMethodName, field.getType());
        method.invoke(instance, fieldValue);
    }

    public static void setSessionAttribute(Object object, HttpServletRequest request) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String methodName = null;
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getType().equals(Session.class)) {
                methodName = ClassUtils.getSetterMethod(field.getName());
                Session session = new Session(request.getSession());
                executeMethod(object, methodName, session);
            }
        }
    }

    public static Object executeRequestMethod(Mapping mapping, HttpServletRequest request, String verb)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException,
            AnnotationNotPresentException, InvalidRequestException, IOException, ServletException {
        List<Object> methodParameters = new ArrayList<>();

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

            methodParameters.add(object);
        }

        return method.invoke(requestObject, methodParameters.toArray());
    }

    public static Object executeMethod(Object object, String methodName, Object... args) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = object.getClass().getMethod(methodName, ClassUtils.getArgsClasses(args));
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
