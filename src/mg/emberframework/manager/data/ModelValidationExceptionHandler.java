package mg.emberframework.manager.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ModelValidationExceptionHandler {
    Map<String, FieldExceptions> fieldExceptions = new HashMap<>();

    // Methods
    public boolean containsException() {
        FieldExceptions fieldExceptions = null;
        
        for(Entry<String, FieldExceptions> entry : getFieldExceptions().entrySet()) {
            fieldExceptions = entry.getValue();
            if (fieldExceptions.containsException()) {
                return true;
            }
        }

        return false;
    }

    public String getFieldExceptionMessage(String field) {
        FieldExceptions fieldExceptions = getFieldExceptions(field);
        return fieldExceptions.getExceptionMessage();
    }

    public void addException(String field, Exception exepction) {
        FieldExceptions fieldExceptions = getFieldExceptions(field);
        fieldExceptions.addException(exepction);
    }

    public FieldExceptions getFieldExceptions(String field) {
        FieldExceptions fieldExceptions = getFieldExceptions().get(field);
        if (fieldExceptions == null) {
            fieldExceptions = new FieldExceptions();
            getFieldExceptions().put(field, fieldExceptions);
        }
        return fieldExceptions;
    }

    // Constructor
    public ModelValidationExceptionHandler() {}

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