package mg.emberframework.core.data;

import java.lang.reflect.Method;
import java.util.Objects;

import jakarta.servlet.http.HttpServletRequest;
import mg.emberframework.annotation.http.RestApi;

/**
 * Represents a method associated with an HTTP verb.
 * <p>
 * This class pairs a {@link Method} with its corresponding HTTP verb (e.g., GET, POST)
 * and provides utilities to validate requests and check for REST API status.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class VerbMethod {
    /** The method associated with the verb. */
    Method method;
    /** The HTTP verb (e.g., "GET", "POST"). */
    String verb;

    /**
     * Checks if the HTTP request matches the method's verb.
     * @param request the HTTP request
     * @return true if the request method matches the verb, false otherwise
     */
    public boolean isRequestValid(HttpServletRequest request) {
        String requestMethod = request.getMethod();
        return getVerb().equalsIgnoreCase(requestMethod);
    }

    /**
     * Checks if the method is annotated as a REST API endpoint.
     * @return true if annotated with {@code @RestApi}, false otherwise
     */
    public boolean isRestAPI() {
        return method.isAnnotationPresent(RestApi.class);
    }

    /**
     * Constructs a verb-method pair.
     * @param method the method
     * @param verb the associated HTTP verb
     */
    public VerbMethod(Method method, String verb) {
        setMethod(method);
        setVerb(verb);
    }

    /**
     * Gets the method.
     * @return the method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Sets the method.
     * @param method the method to set
     */
    public void setMethod(Method method) {
        this.method = method;
    }

    /**
     * Gets the HTTP verb.
     * @return the verb
     */
    public String getVerb() {
        return verb;
    }

    /**
     * Sets the HTTP verb.
     * @param verb the verb to set
     */
    public void setVerb(String verb) {
        this.verb = verb;
    }

    /**
     * Compares this object to another for equality based on the verb.
     * @param obj the object to compare with
     * @return true if the verbs match (case-insensitive), false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VerbMethod)) {
            return false;
        }
        VerbMethod other = (VerbMethod) obj;
        return other.getVerb().equalsIgnoreCase(this.getVerb());
    }

    /**
     * Computes the hash code based on the verb.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(getVerb());
    }
}