package mg.emberframework.manager.data;

import java.util.HashMap;
import java.util.Map;

public class ModelValidationExceptionHandler {
    Map<String, Exception> exceptions = new HashMap<>();
   
    // Methods: addException(key, exc), checkException()
    public boolean containsException() {
        return getExceptions().size() > 0;
    }

    public void addException(String name, Exception exepctions) {
        getExceptions().put(name, exepctions);
    }

    // Constructor
    public ModelValidationExceptionHandler() {}

    public ModelValidationExceptionHandler(Map<String, Exception> exepctions) {
        setExceptions(exepctions);
    }

    // Getters and setters
    public Map<String, Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Map<String, Exception> exceptions) {
        this.exceptions = exceptions;
    }
}