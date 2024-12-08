package mg.emberframework.manager.data;

import java.util.List;

class FieldExceptions {
    private List<Exception> exceptions;
    private String value;

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