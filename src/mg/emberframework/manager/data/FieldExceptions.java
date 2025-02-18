package mg.emberframework.manager.data;

import java.util.ArrayList;
import java.util.List;

public class FieldExceptions {
    private List<Exception> exceptions = new ArrayList<>();
    private String value = "";

    // Methods
    public boolean containsException() {
        return !getExceptions().isEmpty();
    }

    public String getExceptionMessage() {
        return getExceptionMessage("\n");
    }

    public String getExceptionMessage(String separator) {
        StringBuilder messageBuilder = new StringBuilder();
        for (Exception exception : getExceptions()) {
            messageBuilder.append(exception.getMessage()).append(separator);
        }
        return messageBuilder.toString();
    }

    public void addException(Exception exception) {
        getExceptions().add(exception);
    }

    // Constructor
    public FieldExceptions() {
    }

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