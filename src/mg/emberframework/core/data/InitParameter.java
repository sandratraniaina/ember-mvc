package mg.emberframework.core.data;

public class InitParameter {
    private String errorParamName;
    private String controllerPackageName;
    private String errorRedirectionParamName;
    private String roleAttributeName;

    public InitParameter() {

    }

    public InitParameter(String errorParamName, String controllerPackageName, String errorRedirectionParamName, String roleAttributeName) {
        setErrorParamName(errorParamName);
        setControllerPackageName(controllerPackageName);
        setErrorRedirectionParamName(errorRedirectionParamName);
        setRoleAttributeName(roleAttributeName);
    }

    public String getErrorParamName() {
        return errorParamName;
    }

    public void setErrorParamName(String errorParamName) {
        if (errorParamName == null) {
            errorParamName = "errors";
        }
        this.errorParamName = errorParamName;
    }

    public String getControllerPackageName() {
        return controllerPackageName;
    }

    public void setControllerPackageName(String controllerPackageName) {
        if (controllerPackageName == null) {
            controllerPackageName = "controllers";
        }
        this.controllerPackageName = controllerPackageName;
    }

    public String getErrorRedirectionParamName() {
        return errorRedirectionParamName;
    }

    public void setErrorRedirectionParamName(String errorRedirectionParamName) {
        if (errorRedirectionParamName == null) {
            errorRedirectionParamName = "error-url";
        }
        this.errorRedirectionParamName = errorRedirectionParamName;
    }

    public String getRoleAttributeName() {
        return roleAttributeName;
    }

    public void setRoleAttributeName(String roleAttributeName) {
        if (roleAttributeName == null) {
            roleAttributeName = "role";
        }
        this.roleAttributeName = roleAttributeName;
    }
}
