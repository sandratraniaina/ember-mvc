package mg.emberframework.annotation.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that a method exposes a RESTful API endpoint.
 * <p>
 * Methods annotated with {@code @RestApi} are intended to handle HTTP requests
 * and return responses in a RESTful manner in JSON format.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RestApi {
}