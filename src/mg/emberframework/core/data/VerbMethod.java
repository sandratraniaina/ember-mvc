package mg.emberframework.core.data;

import java.lang.reflect.Method;
import java.util.Objects;

import jakarta.servlet.http.HttpServletRequest;
import mg.emberframework.annotation.http.RestApi;

public class VerbMethod {
    Method method;
    String verb;

    // Method
    public boolean isRequestValid(HttpServletRequest request) {
        String requestMethod = request.getMethod();
        return getVerb().equalsIgnoreCase(requestMethod);
    }

    public boolean isRestAPI() {
        return method.isAnnotationPresent(RestApi.class);
    }

    // Constructor
    public VerbMethod(Method method, String verb) {
        setMethod(method);
        setVerb(verb);
    }

    // Getters and setters
    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    // Override methods
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VerbMethod)) {
            return false;
        }
        VerbMethod other = (VerbMethod) obj;

        return other.getVerb().equalsIgnoreCase(this.getVerb());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getVerb());
    }
}
