package mg.emberframework.utils.validation.validators;

import java.lang.annotation.Annotation;

import mg.emberframework.core.exception.ModelValidationException;

public class RequiredValidator implements FieldValidator{

    @Override
    public void validate(String value, Annotation annotation, String fieldName) throws ModelValidationException {
        if (value == null || "".equals(value)) {
            throw new ModelValidationException(fieldName + " is required");
        }
    }

}
