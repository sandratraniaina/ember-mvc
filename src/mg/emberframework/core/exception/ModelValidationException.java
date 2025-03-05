package mg.emberframework.core.exception;

/**
 * Thrown when model validation fails.
 * <p>
 * This exception is raised when a model's fields do not pass the validation
 * rules defined by annotations such as {@code @Numeric} or {@code @Required}.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class ModelValidationException extends Exception {
    /**
     * Constructs an exception with a detail message.
     * @param message the detail message explaining the validation failure
     */
    public ModelValidationException(String message) {
        super(message);
    }
}