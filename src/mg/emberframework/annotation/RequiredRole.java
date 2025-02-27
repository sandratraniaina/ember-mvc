package mg.emberframework.annotation;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredRole {
    String[] values();
}
