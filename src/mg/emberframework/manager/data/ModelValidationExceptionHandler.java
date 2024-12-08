package mg.emberframework.manager.data;

import java.util.HashMap;
import java.util.Map;

public class ModelValidationExceptionHandler {
    Map<String, FieldExceptions> fieldExceptions = new HashMap<>();

    // Methods

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