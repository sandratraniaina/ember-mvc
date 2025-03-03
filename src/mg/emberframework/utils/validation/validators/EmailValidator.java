package mg.emberframework.utils.validation.validators;

import java.lang.annotation.Annotation;

import mg.emberframework.core.exception.ModelValidationException;

public class EmailValidator implements FieldValidator{

    @Override
    public void validate(String value, Annotation annotation, String fieldName) throws ModelValidationException {
        boolean checker = (value).matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
        if (!checker) {
            throw new ModelValidationException(fieldName + " must be a valid email.");
        }
    }
    
}
