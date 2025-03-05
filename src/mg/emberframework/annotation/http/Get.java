package mg.emberframework.annotation.http;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * Indicates that a method handles HTTP GET requests.
 * <p>
 * This annotation is typically used within a {@code @Controller} class to
 * map a method to a specific URL for retrieving resources.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Get {
}