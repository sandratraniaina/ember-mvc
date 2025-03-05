package mg.emberframework.annotation.validation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * Specifies the exact length that a field or parameter's value must have.
 * <p>
 * This annotation enforces that the string value of a field or parameter
 * matches the length defined by the {@code length} attribute. It is typically
 * used for string-based inputs.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface Length {
    /**
     * The exact length that the annotated field or parameter's value must have.
     * @return the required length
     */
    int length();
}