package mg.emberframework.utils.validation.validators;

import java.lang.annotation.Annotation;

import mg.emberframework.core.exception.ModelValidationException;

public class NeutralValidator implements FieldValidator{

    @Override
    public void validate(String value, Annotation annotation, String fieldName) throws ModelValidationException {
        // This method is empty because this validate field without validator annotation.
    }
    
}
