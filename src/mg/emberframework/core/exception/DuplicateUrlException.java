package mg.emberframework.core.exception;

/**
 * Thrown when a duplicate URL mapping is detected.
 * <p>
 * This exception is raised when multiple methods or controllers attempt to map
 * to the same URL, as defined by {@code @Url}, causing a conflict.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class DuplicateUrlException extends Exception {
    /**
     * Constructs an exception with a detail message.
     * @param message the detail message explaining the duplicate URL
     */
    public DuplicateUrlException(String message) {
        super(message);
    }
}