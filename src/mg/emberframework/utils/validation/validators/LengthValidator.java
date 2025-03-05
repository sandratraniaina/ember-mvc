package mg.emberframework.utils.validation.validators;

import java.lang.annotation.Annotation;

import mg.emberframework.annotation.validation.Length;
import mg.emberframework.core.exception.ModelValidationException;

public class LengthValidator implements FieldValidator {

    @Override
    public void validate(String value, Annotation annotation, String fieldName) throws ModelValidationException {
        int minLength = ((Length) annotation).length();
        if ((value).length() < minLength) {
            throw new ModelValidationException(fieldName + " must have at least " + minLength + " characters.");
        }
    }

}
