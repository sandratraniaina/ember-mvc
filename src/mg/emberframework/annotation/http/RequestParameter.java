package mg.emberframework.annotation.http;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * Marks a method parameter as an HTTP request parameter.
 * <p>
 * This annotation binds a method parameter to a specific request parameter
 * identified by the {@code value} attribute, allowing the framework to inject
 * the parameter's value from the HTTP request.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParameter {
    /**
     * The name of the request parameter to bind to.
     * @return the parameter name
     */
    String value();
}