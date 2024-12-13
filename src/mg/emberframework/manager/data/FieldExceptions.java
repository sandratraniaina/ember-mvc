package mg.emberframework.manager.data;

import java.util.ArrayList;
import java.util.List;

class FieldExceptions {
    private List<Exception> exceptions = new ArrayList<>();
    private String value = "";

    // Methods
    public boolean containsException() {
        return  !getExceptions().isEmpty();
    }
    
    public String getExceptionMessage() {
        String message = "";
        for (Exception exception: getExceptions()) {
            message += exception.getMessage() + "\n";
        }

        return message;
    }

    public void addException(Exception exception) {
        getExceptions().add(exception);
    }

    // Constructor
    public FieldExceptions() {}

    public FieldExceptions(List<Exception> exceptions, String value) {
        setExceptions(exceptions);
        setValue(value);
    }

    // Errors
    public List<Exception> getExceptions() {
        return exceptions;
    }
    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}