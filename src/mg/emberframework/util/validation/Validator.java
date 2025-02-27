package mg.emberframework.util.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import mg.emberframework.manager.exception.ModelValidationException;
import mg.emberframework.util.ClassUtils;
import mg.emberframework.util.validation.validators.FieldValidator;
import mg.emberframework.annotation.RequestParameter;
import mg.emberframework.manager.data.FieldValidationResult;
import mg.emberframework.manager.data.ModelValidationResults;

public class Validator {

    private Validator() {}
    
    public static void checkField(String value, Field field) throws ModelValidationException {
        Annotation[] annotations = field.getAnnotations();

        for(Annotation annotation : annotations) {
            FieldValidator validator = ValidatorRegistry.getValidator(annotation.annotationType());

            if (validator != null) {
                validator.validate(value , annotation, field);
            }
        }
    }

    public static FieldValidationResult getModelFieldExceptions(String value, Field field) {
        List<Exception> exceptions = new ArrayList<>();
        FieldValidationResult fieldExceptions = new FieldValidationResult(exceptions, value);

        Annotation[] annotations = field.getDeclaredAnnotations();

        for(Annotation annotation : annotations) {
            FieldValidator validator = ValidatorRegistry.getValidator(annotation.annotationType());

            if (validator != null) {
                try {
                    validator.validate(value , annotation, field);
                } catch (Exception e) {
                    fieldExceptions.addException(e);
                }
            }
        }

        return fieldExceptions;
    }

    public static void validateModel(Class<?> modelType, String identifier, ModelValidationResults handler, HttpServletRequest request) {
        Field[] fields = modelType.getDeclaredFields();

        for (Field field : fields) {
            String name = identifier + "." + field.getName();
            FieldValidationResult temp = getModelFieldExceptions(request.getParameter(name), field);
            handler.addFieldException(name, temp);
        }
    }

    public static ModelValidationResults validateMethod(Method method, HttpServletRequest request) {
        ModelValidationResults handler = new ModelValidationResults();

        Parameter[] parameters = method.getParameters();

        for(Parameter parameter : parameters) {
            if (ClassUtils.isClassModel(parameter.getType())) {
                String identifier = parameter.getAnnotation(RequestParameter.class).value();
                validateModel(parameter.getType() , identifier, handler, request);
            }
        }

        return handler;
    }
}
