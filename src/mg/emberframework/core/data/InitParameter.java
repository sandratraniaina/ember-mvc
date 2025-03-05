package mg.emberframework.core.data;

/**
 * Holds initialization parameters for the framework.
 * <p>
 * This class stores configuration settings such as parameter names for errors,
 * controller packages, and role attributes, with default values if not specified.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class InitParameter {
    /** The parameter name for errors. */
    private String errorParamName;
    /** The package name for controllers. */
    private String controllerPackageName;
    /** The parameter name for error redirection. */
    private String errorRedirectionParamName;
    /** The attribute name for roles. */
    private String roleAttributeName;

    /** Default constructor. */
    public InitParameter() {
    }

    /**
     * Constructs an initialization parameter object with specified values.
     * @param errorParamName the error parameter name
     * @param controllerPackageName the controller package name
     * @param errorRedirectionParamName the error redirection parameter name
     * @param roleAttributeName the role attribute name
     */
    public InitParameter(String errorParamName, String controllerPackageName, String errorRedirectionParamName, String roleAttributeName) {
        setErrorParamName(errorParamName);
        setControllerPackageName(controllerPackageName);
        setErrorRedirectionParamName(errorRedirectionParamName);
        setRoleAttributeName(roleAttributeName);
    }

    /**
     * Gets the error parameter name.
     * @return the error parameter name
     */
    public String getErrorParamName() {
        return errorParamName;
    }

    /**
     * Sets the error parameter name, defaulting to "errors" if null.
     * @param errorParamName the name to set
     */
    public void setErrorParamName(String errorParamName) {
        if (errorParamName == null) {
            errorParamName = "errors";
        }
        this.errorParamName = errorParamName;
    }

    /**
     * Gets the controller package name.
     * @return the controller package name
     */
    public String getControllerPackageName() {
        return controllerPackageName;
    }

    /**
     * Sets the controller package name, defaulting to "controllers" if null.
     * @param controllerPackageName the name to set
     */
    public void setControllerPackageName(String controllerPackageName) {
        if (controllerPackageName == null) {
            controllerPackageName = "controllers";
        }
        this.controllerPackageName = controllerPackageName;
    }

    /**
     * Gets the error redirection parameter name.
     * @return the error redirection parameter name
     */
    public String getErrorRedirectionParamName() {
        return errorRedirectionParamName;
    }

    /**
     * Sets the error redirection parameter name, defaulting to "error-url" if null.
     * @param errorRedirectionParamName the name to set
     */
    public void setErrorRedirectionParamName(String errorRedirectionParamName) {
        if (errorRedirectionParamName == null) {
            errorRedirectionParamName = "error-url";
        }
        this.errorRedirectionParamName = errorRedirectionParamName;
    }

    /**
     * Gets the role attribute name.
     * @return the role attribute name
     */
    public String getRoleAttributeName() {
        return roleAttributeName;
    }

    /**
     * Sets the role attribute name, defaulting to "role" if null.
     * @param roleAttributeName the name to set
     */
    public void setRoleAttributeName(String roleAttributeName) {
        if (roleAttributeName == null) {
            roleAttributeName = "role";
        }
        this.roleAttributeName = roleAttributeName;
    }
}