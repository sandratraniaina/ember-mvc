package mg.emberframework.utils.validation.validators;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import mg.emberframework.annotation.validation.Length;
import mg.emberframework.manager.exception.ModelValidationException;

public class LengthValidator implements FieldValidator {

    @Override
    public void validate(String value, Annotation annotation, Field field) throws ModelValidationException {
        int minLength = ((Length) annotation).length();
        if ((value).length() < minLength) {
            throw new ModelValidationException(field.getName() + " must have at least " + minLength + " characters.");
        }
    }

}
