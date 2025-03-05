package mg.emberframework.core.exception;

/**
 * Thrown when a required annotation is missing from a class or method.
 * <p>
 * This exception indicates that an expected annotation, such as {@code @Controller}
 * or {@code @Get}, is not present where it is required by the framework.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class AnnotationNotPresentException extends Exception {
    /**
     * Constructs an exception with a detail message.
     * @param message the detail message explaining the missing annotation
     */
    public AnnotationNotPresentException(String message) {
        super(message);
    }
}