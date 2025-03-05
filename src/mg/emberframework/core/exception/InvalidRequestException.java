package mg.emberframework.core.exception;

/**
 * Thrown when an HTTP request is invalid or malformed.
 * <p>
 * This exception indicates that a request does not meet the expected format or
 * requirements for processing within the framework.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class InvalidRequestException extends Exception {
    /**
     * Constructs an exception with a detail message.
     * @param message the detail message explaining the invalid request
     */
    public InvalidRequestException(String message) {
        super(message);
    }
}