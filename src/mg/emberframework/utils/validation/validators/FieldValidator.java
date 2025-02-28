package mg.emberframework.utils.validation.validators;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import mg.emberframework.core.exception.ModelValidationException;

public interface FieldValidator {
    public void validate(String value, Annotation annotation,Field field) throws ModelValidationException;
}
