package mg.emberframework.core.exception;

/**
 * Thrown when a user lacks the required authorization to access a resource.
 * <p>
 * This exception indicates that the current user does not have the necessary
 * roles or permissions, as defined by {@code @RequiredRole}, to proceed.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class UnauthorizedAccessException extends Exception {
    /**
     * Constructs an exception with a detail message.
     * @param message the detail message explaining the unauthorized access
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}