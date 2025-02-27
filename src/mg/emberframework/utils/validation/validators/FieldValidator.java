package mg.emberframework.utils.validation.validators;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import mg.emberframework.manager.exception.ModelValidationException;

public interface FieldValidator {
    public void validate(String value, Annotation annotation,Field field) throws ModelValidationException;
}
