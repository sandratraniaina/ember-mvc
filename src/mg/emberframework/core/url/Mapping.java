package mg.emberframework.core.url;

import java.util.HashSet;
import java.util.Set;

import mg.emberframework.core.data.VerbMethod;
import mg.emberframework.core.exception.DuplicateUrlException;
import mg.emberframework.core.exception.InvalidRequestException;

public class Mapping {
    Class<?> clazz;
    Set<VerbMethod> verbMethods = new HashSet<>();

    // Method
    public VerbMethod getSpecificVerbMethod(String verb) throws InvalidRequestException {
        for(VerbMethod verbMethod : getVerbMethods()) {
            if (verbMethod.getVerb().equalsIgnoreCase(verb)) {
                return verbMethod;
            }
        }
        throw new InvalidRequestException("Invalid request method");
    }

    public void addVerbMethod(VerbMethod verbMethod) throws DuplicateUrlException {
        if (getVerbMethods().contains(verbMethod)) {
            throw new DuplicateUrlException("Duplicate url method!!");
        }
        getVerbMethods().add(verbMethod);
    }

    // Construtors
    public Mapping() {}
    public Mapping(Class<?> clazz) {
        setClazz(clazz);
    }

    // Getters and setters
    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Set<VerbMethod> getVerbMethods() {
        return verbMethods;
    }

    public void setVerbMethods(Set<VerbMethod> verbMethods) {
        this.verbMethods = verbMethods;
    }
}
