package mg.emberframework.annotation.http;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * Marks a class as a controller to handle HTTP requests in the framework.
 * <p>
 * Classes annotated with {@code @Controller} are responsible for defining
 * request-handling logic, typically by containing methods annotated with
 * HTTP-specific annotations such as {@code @Get} or {@code @Post}.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {}