package mg.emberframework.manager.data;

import java.util.List;
import java.util.Map;

public class ModelValidationExceptionHandler {
    Map<String, List<Exception>> exceptions;
   
    // Methods: addException(key, exc), 

    // Constructor
    public ModelValidationExceptionHandler() {}

    public ModelValidationExceptionHandler(Map<String, List<Exception>> exceptions) {
        setExceptions(exceptions);
    }

    // Getters and setters
    public Map<String, List<Exception>> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Map<String, List<Exception>> exceptions) {
        this.exceptions = exceptions;
    }
}