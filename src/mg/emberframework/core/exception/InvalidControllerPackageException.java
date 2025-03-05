package mg.emberframework.core.exception;

/**
 * Thrown when the specified controller package is invalid or cannot be processed.
 * <p>
 * This exception is raised when the framework encounters an issue with the
 * controller package configuration, such as an incorrect or inaccessible package name.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class InvalidControllerPackageException extends Exception {
    /**
     * Constructs an exception with a detail message.
     * @param message the detail message explaining the invalid package
     */
    public InvalidControllerPackageException(String message) {
        super(message);
    }
}