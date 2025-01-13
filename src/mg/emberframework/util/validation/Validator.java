package mg.emberframework.util.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import mg.emberframework.manager.exception.ModelValidationException;
import mg.emberframework.util.validation.validator.FieldValidator;

import mg.emberframework.manager.data.FieldExceptions;
import mg.emberframework.manager.data.ModelValidationExceptionHandler;

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

    public static FieldExceptions getModelFieldExceptions(String value, Field field) {
        List<Exception> exceptions = new ArrayList<>();
        FieldExceptions fieldExceptions = new FieldExceptions(exceptions, value);

        Annotation[] annotations = field.getAnnotations();

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

    public static <T> void validateInstance(T instance, String identifier, ModelValidationExceptionHandler handler, HttpServletRequest request) {
        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field field : fields) {
            String name = identifier + "." + field.getName();
            FieldExceptions temp = getModelFieldExceptions(request.getParameter(name), field);
            if (temp.containsException()) {
                handler.addFieldException(name, temp);
            }
        }
    }
}
