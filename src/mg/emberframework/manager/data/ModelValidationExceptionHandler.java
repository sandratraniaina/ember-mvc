package mg.emberframework.manager.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ModelValidationExceptionHandler {
    Map<String, FieldExceptions> fieldExceptions = new HashMap<>();

    // Methods
    public boolean containsException() {
        FieldExceptions temp = null;

        for (Entry<String, FieldExceptions> entry : getFieldExceptions().entrySet()) {
            temp = entry.getValue();
            if (temp.containsException()) {
                return true;
            }
        }

        return false;
    }

    public String getFieldExceptionMessage(String field) {
        FieldExceptions fieldExc = getFieldExceptions(field);
        return fieldExc.getExceptionMessage();
    }

    public String getFieldExceptionMessage(String field, String separator) {
        FieldExceptions fieldExc = getFieldExceptions(field);
        return fieldExc.getExceptionMessage(separator);
    }

    public String getFieldExceptionValue(String field) {
        FieldExceptions fieldExc = getFieldExceptions(field);
        return fieldExc.getValue();
    }

    public void addException(String field, Exception exepction) {
        FieldExceptions fieldExc = getFieldExceptions(field);
        fieldExc.addException(exepction);
    }

    public void addFieldException(String field, FieldExceptions fieldExceptions) {
        this.fieldExceptions.put(field, fieldExceptions);
    }

    public FieldExceptions getFieldExceptions(String field) {
        FieldExceptions fieldExc = getFieldExceptions().get(field);
        if (fieldExc == null) {
            fieldExc = new FieldExceptions();
            getFieldExceptions().put(field, fieldExc);
        }
        return fieldExc;
    }

    // Constructor
    public ModelValidationExceptionHandler() {
    }

    public ModelValidationExceptionHandler(Map<String, FieldExceptions> exepctions) {
        setFieldExceptions(exepctions);
    }

    // Getters and setters
    public Map<String, FieldExceptions> getFieldExceptions() {
        return fieldExceptions;
    }

    public void setFieldExceptions(Map<String, FieldExceptions> fieldExceptions) {
        this.fieldExceptions = fieldExceptions;
    }
}