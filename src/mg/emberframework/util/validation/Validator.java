package mg.emberframework.util.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mg.emberframework.manager.exception.ModelValidationException;
import mg.emberframework.util.validation.validator.FieldValidator;

public class Validator {
    public static void checkField(String value, Field field) throws ModelValidationException {
        Annotation[] annotations = field.getAnnotations();

        for(Annotation annotation : annotations) {
            FieldValidator validator = ValidatorRegistry.getValidator(annotation.annotationType());

            if (validator != null) {
                validator.validate(value , annotation, field);
            }
        }
    }

    public static List<Exception> getFieldExceptions(String value, Field field) {
        List<Exception> exceptions = new ArrayList<>();
        Annotation[] annotations = field.getAnnotations();

        for(Annotation annotation : annotations) {
            FieldValidator validator = ValidatorRegistry.getValidator(annotation.annotationType());

            if (validator != null) {
                try {
                    validator.validate(value , annotation, field);
                } catch (Exception e) {
                    exceptions.add(e);
                }
            }
        }

        return exceptions;
    }
}
