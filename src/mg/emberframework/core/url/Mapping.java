package mg.emberframework.core.url;

import java.util.HashSet;
import java.util.Set;

import mg.emberframework.core.data.VerbMethod;
import mg.emberframework.core.exception.DuplicateUrlException;
import mg.emberframework.core.exception.InvalidRequestException;

/**
 * Represents a mapping of a URL to a set of HTTP verb-specific methods.
 * <p>
 * This class associates a controller class with a collection of {@link VerbMethod}
 * instances, each corresponding to an HTTP verb (e.g., GET, POST), and ensures no
 * duplicate verb mappings are added for the same URL.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class Mapping {
    /** The controller class associated with this mapping. */
    Class<?> clazz;
    /** The set of verb-specific methods for this mapping. */
    Set<VerbMethod> verbMethods = new HashSet<>();

    /**
     * Retrieves the {@link VerbMethod} for a specific HTTP verb.
     * @param verb the HTTP verb (e.g., "GET", "POST")
     * @return the corresponding {@link VerbMethod}
     * @throws InvalidRequestException if no method is found for the specified verb
     */
    public VerbMethod getSpecificVerbMethod(String verb) throws InvalidRequestException {
        for (VerbMethod verbMethod : getVerbMethods()) {
            if (verbMethod.getVerb().equalsIgnoreCase(verb)) {
                return verbMethod;
            }
        }
        throw new InvalidRequestException("Invalid request method");
    }

    /**
     * Adds a {@link VerbMethod} to the mapping.
     * @param verbMethod the verb-specific method to add
     * @throws DuplicateUrlException if a method for the same verb already exists
     */
    public void addVerbMethod(VerbMethod verbMethod) throws DuplicateUrlException {
        if (getVerbMethods().contains(verbMethod)) {
            throw new DuplicateUrlException("Duplicate url method!!");
        }
        getVerbMethods().add(verbMethod);
    }

    /** Default constructor. */
    public Mapping() {}

    /**
     * Constructs a mapping with a specified controller class.
     * @param clazz the controller class
     */
    public Mapping(Class<?> clazz) {
        setClazz(clazz);
    }

    /**
     * Gets the controller class associated with this mapping.
     * @return the controller class
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * Sets the controller class associated with this mapping.
     * @param clazz the controller class to set
     */
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * Gets the set of verb-specific methods.
     * @return the set of {@link VerbMethod} instances
     */
    public Set<VerbMethod> getVerbMethods() {
        return verbMethods;
    }

    /**
     * Sets the set of verb-specific methods.
     * @param verbMethods the set of {@link VerbMethod} instances to set
     */
    public void setVerbMethods(Set<VerbMethod> verbMethods) {
        this.verbMethods = verbMethods;
    }
}