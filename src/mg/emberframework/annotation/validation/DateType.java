package mg.emberframework.annotation.validation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * Specifies that a field or parameter must conform to a date format.
 * <p>
 * This annotation validates that the value of the annotated field or parameter
 * matches the date format specified by the {@code format} attribute (e.g., "yyyy-MM-dd").
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface DateType {
    /**
     * The date format that the annotated field or parameter must follow.
     * @return the date format string (e.g., "yyyy-MM-dd")
     */
    String format();
}