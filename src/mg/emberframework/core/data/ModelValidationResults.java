package mg.emberframework.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Represents the validation results for a model's fields.
 * <p>
 * This class aggregates validation outcomes for individual fields, storing them
 * in a map where each key is a field name and the value is a {@link FieldValidationResult}.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class ModelValidationResults {
    /** The map of field names to their validation results. */
    Map<String, FieldValidationResult> fieldExceptions = new HashMap<>();

    /**
     * Checks if any field contains a validation exception.
     * @return true if at least one field has an exception, false otherwise
     */
    public boolean containsException() {
        FieldValidationResult temp = null;
        for (Entry<String, FieldValidationResult> entry : getFieldExceptions().entrySet()) {
            temp = entry.getValue();
            if (temp.containsException()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the exception message for a specific field.
     * @param field the name of the field
     * @return the exception message for the field, or an empty string if none exists
     */
    public String getFieldExceptionMessage(String field) {
        FieldValidationResult fieldExc = getFieldExceptions(field);
        return fieldExc.getExceptionMessage();
    }

    /**
     * Retrieves the exception message for a specific field with a custom separator.
     * @param field the name of the field
     * @param separator the separator to use between exception messages
     * @return the concatenated exception messages for the field
     */
    public String getFieldExceptionMessage(String field, String separator) {
        FieldValidationResult fieldExc = getFieldExceptions(field);
        return fieldExc.getExceptionMessage(separator);
    }

    /**
     * Retrieves the value associated with a field's validation result.
     * @param field the name of the field
     * @return the value of the field from its validation result
     */
    public String getFieldExceptionValue(String field) {
        FieldValidationResult fieldExc = getFieldExceptions(field);
        return fieldExc.getValue();
    }

    /**
     * Adds an exception to a field's validation result.
     * @param field the name of the field
     * @param exception the exception to add
     */
    public void addException(String field, Exception exception) {
        FieldValidationResult fieldExc = getFieldExceptions(field);
        fieldExc.addException(exception);
    }

    /**
     * Adds a complete validation result for a field.
     * @param field the name of the field
     * @param fieldExceptions the validation result to associate with the field
     */
    public void addFieldException(String field, FieldValidationResult fieldExceptions) {
        this.fieldExceptions.put(field, fieldExceptions);
    }

    /**
     * Retrieves or creates the validation result for a specific field.
     * @param field the name of the field
     * @return the existing or new {@link FieldValidationResult} for the field
     */
    public FieldValidationResult getFieldExceptions(String field) {
        FieldValidationResult fieldExc = getFieldExceptions().get(field);
        if (fieldExc == null) {
            fieldExc = new FieldValidationResult();
            getFieldExceptions().put(field, fieldExc);
        }
        return fieldExc;
    }

    /** Default constructor. */
    public ModelValidationResults() {
    }

    /**
     * Constructs a validation result with a pre-populated map of field exceptions.
     * @param exceptions the map of field names to their validation results
     */
    public ModelValidationResults(Map<String, FieldValidationResult> exceptions) {
        setFieldExceptions(exceptions);
    }

    /**
     * Gets the map of field validation results.
     * @return the field exceptions map
     */
    public Map<String, FieldValidationResult> getFieldExceptions() {
        return fieldExceptions;
    }

    /**
     * Sets the map of field validation results.
     * @param fieldExceptions the map to set
     */
    public void setFieldExceptions(Map<String, FieldValidationResult> fieldExceptions) {
        this.fieldExceptions = fieldExceptions;
    }
}