package mg.emberframework.annotation.http;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

/**
 * Specifies the roles required to access a class or method.
 * <p>
 * This annotation can be applied to a {@code @Controller} class or individual
 * methods to enforce role-based access control. The {@code values} attribute
 * defines the roles that are permitted.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface RequiredRole {
    /**
     * The roles required to access the annotated class or method.
     * @return an array of role names
     */
    String[] values();
}