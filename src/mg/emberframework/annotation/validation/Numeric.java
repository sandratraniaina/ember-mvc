package mg.emberframework.annotation.validation;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * Indicates that a field or parameter must contain a numeric value.
 * <p>
 * This annotation can be applied to fields or method parameters to enforce
 * that their values are valid numbers (e.g., integers, decimals). The framework
 * will validate the input accordingly.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface Numeric {
}