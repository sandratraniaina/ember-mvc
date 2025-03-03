package mg.emberframework.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ModelValidationResults {
    Map<String, FieldValidationResult> fieldExceptions = new HashMap<>();

    // Methods
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

    public String getFieldExceptionMessage(String field) {
        FieldValidationResult fieldExc = getFieldExceptions(field);
        return fieldExc.getExceptionMessage();
    }

    public String getFieldExceptionMessage(String field, String separator) {
        FieldValidationResult fieldExc = getFieldExceptions(field);
        return fieldExc.getExceptionMessage(separator);
    }

    public String getFieldExceptionValue(String field) {
        FieldValidationResult fieldExc = getFieldExceptions(field);
        return fieldExc.getValue();
    }

    public void addException(String field, Exception exepction) {
        FieldValidationResult fieldExc = getFieldExceptions(field);
        fieldExc.addException(exepction);
    }

    public void addFieldException(String field, FieldValidationResult fieldExceptions) {
        this.fieldExceptions.put(field, fieldExceptions);
    }

    public FieldValidationResult getFieldExceptions(String field) {
        FieldValidationResult fieldExc = getFieldExceptions().get(field);
        if (fieldExc == null) {
            fieldExc = new FieldValidationResult();
            getFieldExceptions().put(field, fieldExc);
        }
        return fieldExc;
    }

    // Constructor
    public ModelValidationResults() {
    }

    public ModelValidationResults(Map<String, FieldValidationResult> exepctions) {
        setFieldExceptions(exepctions);
    }

    // Getters and setters
    public Map<String, FieldValidationResult> getFieldExceptions() {
        return fieldExceptions;
    }

    public void setFieldExceptions(Map<String, FieldValidationResult> fieldExceptions) {
        this.fieldExceptions = fieldExceptions;
    }
}