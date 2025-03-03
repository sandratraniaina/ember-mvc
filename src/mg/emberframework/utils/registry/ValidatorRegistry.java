package mg.emberframework.utils.registry;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import mg.emberframework.annotation.validation.DateType;
import mg.emberframework.annotation.validation.Email;
import mg.emberframework.annotation.validation.Length;
import mg.emberframework.annotation.validation.Numeric;
import mg.emberframework.annotation.validation.Required;
import mg.emberframework.utils.validation.validators.DateValidator;
import mg.emberframework.utils.validation.validators.EmailValidator;
import mg.emberframework.utils.validation.validators.FieldValidator;
import mg.emberframework.utils.validation.validators.LengthValidator;
import mg.emberframework.utils.validation.validators.NeutralValidator;
import mg.emberframework.utils.validation.validators.NumericValidator;
import mg.emberframework.utils.validation.validators.RequiredValidator;

public class ValidatorRegistry {
    private ValidatorRegistry() {
    }

    private static final Map<Class<? extends Annotation>, FieldValidator> validators = new HashMap<>();

    static {
        validators.put(Length.class, new LengthValidator());
        validators.put(DateType.class, new DateValidator());
        validators.put(Email.class, new EmailValidator());
        validators.put(Required.class, new RequiredValidator());
        validators.put(Numeric.class, new NumericValidator());
    }

    public static FieldValidator getValidator(Class<? extends Annotation> annotation) {
        FieldValidator validator = validators.get(annotation);
        if (validator == null) {
            validator = new NeutralValidator();
        }
        return validator;
    }
}
