package mg.emberframework.utils.validation.validators;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import mg.emberframework.manager.exception.ModelValidationException;

public class NeutralValidator implements FieldValidator{

    @Override
    public void validate(String value, Annotation annotation, Field field) throws ModelValidationException {
        // This method is empty because this validate field without validator annotation.
    }
    
}
