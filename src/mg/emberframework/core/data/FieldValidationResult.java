package mg.emberframework.core.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the validation result for a single field.
 * <p>
 * This class stores a list of exceptions and the field's value, providing
 * methods to check for exceptions and retrieve their messages.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class FieldValidationResult {
    /** The list of validation exceptions for the field. */
    private List<Exception> exceptions = new ArrayList<>();
    /** The value of the field being validated. */
    private String value = "";

    /**
     * Checks if the field has any validation exceptions.
     * @return true if exceptions exist, false otherwise
     */
    public boolean containsException() {
        return !getExceptions().isEmpty();
    }

    /**
     * Retrieves the exception messages with a default newline separator.
     * @return the concatenated exception messages
     */
    public String getExceptionMessage() {
        return getExceptionMessage("\n");
    }

    /**
     * Retrieves the exception messages with a custom separator.
     * @param separator the separator to use between messages
     * @return the concatenated exception messages
     */
    public String getExceptionMessage(String separator) {
        StringBuilder messageBuilder = new StringBuilder();
        for (Exception exception : getExceptions()) {
            messageBuilder.append(exception.getMessage()).append(separator);
        }
        return messageBuilder.toString();
    }

    /**
     * Adds an exception to the field's validation result.
     * @param exception the exception to add
     */
    public void addException(Exception exception) {
        getExceptions().add(exception);
    }

    /** Default constructor. */
    public FieldValidationResult() {
    }

    /**
     * Constructs a validation result with a list of exceptions and a value.
     * @param exceptions the list of exceptions
     * @param value the field's value
     */
    public FieldValidationResult(List<Exception> exceptions, String value) {
        setExceptions(exceptions);
        setValue(value);
    }

    /**
     * Gets the list of exceptions.
     * @return the exceptions list
     */
    public List<Exception> getExceptions() {
        return exceptions;
    }

    /**
     * Sets the list of exceptions.
     * @param exceptions the exceptions to set
     */
    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    /**
     * Gets the field's value.
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the field's value.
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}