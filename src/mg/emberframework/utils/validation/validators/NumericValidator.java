package mg.emberframework.utils.validation.validators;

import java.lang.annotation.Annotation;

import mg.emberframework.core.exception.ModelValidationException;

public class NumericValidator implements FieldValidator{

    @Override
    public void validate(String value, Annotation annotation, String fieldName) throws ModelValidationException {
        String regex = "-?\\d+(\\.\\d+)?";
        if (!(value.matches(regex))) {
            throw new ModelValidationException(fieldName + " should be numeric");
        }
    }
    
}
