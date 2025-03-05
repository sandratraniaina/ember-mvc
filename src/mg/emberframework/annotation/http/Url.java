package mg.emberframework.annotation.http;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * Defines the URL path for a class or method handling HTTP requests.
 * <p>
 * When applied to a {@code @Controller} class, it specifies the base URL.
 * When applied to a method, it defines the specific endpoint relative to
 * the base URL. If no value is provided, an empty string is assumed.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Url {
    /**
     * The URL path for the annotated class or method.
     * @return the URL path (defaults to an empty string)
     */
    String value() default "";
}