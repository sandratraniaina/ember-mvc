package mg.emberframework.core.exception;

/**
 * Thrown when a requested URL cannot be found or mapped.
 * <p>
 * This exception indicates that no controller or method is associated with the
 * requested URL in the framework's routing configuration.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class UrlNotFoundException extends Exception {
    /**
     * Constructs an exception with a detail message.
     * @param message the detail message explaining the missing URL
     */
    public UrlNotFoundException(String message) {
        super(message);
    }
}