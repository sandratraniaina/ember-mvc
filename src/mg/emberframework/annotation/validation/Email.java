package mg.emberframework.annotation.validation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * Indicates that a field or parameter must contain a valid email address.
 * <p>
 * This annotation enforces email format validation (e.g., user@domain.com) on
 * the annotated field or parameter. The framework will check for compliance
 * with standard email conventions.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface Email {
}