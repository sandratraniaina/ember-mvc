package mg.emberframework.annotation.validation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * Marks a field or parameter as required, meaning it cannot be null or empty.
 * <p>
 * This annotation ensures that the annotated field or parameter has a non-null
 * and non-empty value. It is useful for enforcing mandatory inputs in the framework.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface Required {
}