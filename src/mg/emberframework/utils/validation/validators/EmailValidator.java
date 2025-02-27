package mg.emberframework.utils.validation.validators;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import mg.emberframework.manager.exception.ModelValidationException;

public class EmailValidator implements FieldValidator{

    @Override
    public void validate(String value, Annotation annotation, Field field) throws ModelValidationException {
        boolean checker = (value).matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
        if (!checker) {
            throw new ModelValidationException(field.getName() + " must be a valid email.");
        }
    }
    
}
