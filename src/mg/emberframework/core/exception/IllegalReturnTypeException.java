package mg.emberframework.core.exception;

/**
 * Thrown when a method's return type is not supported by the framework.
 * <p>
 * This exception is raised when a controller method returns a type that cannot
 * be processed or rendered, such as an unsupported object for a REST API or view.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class IllegalReturnTypeException extends Exception {
    /**
     * Constructs an exception with a detail message.
     * @param message the detail message explaining the illegal return type
     */
    public IllegalReturnTypeException(String message) {
        super(message);
    }
}