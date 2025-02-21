package mg.emberframework.manager.data;

public class InitParameter {
    private String errorParamName;
    private String controllerPackageName;
    private String errorRedirectionParamName;

    public InitParameter() {
        
    }
    
    public InitParameter(String errorParamName, String controllerPackageName, String errorRedirectionParamName) {
        this.errorParamName = errorParamName;
        this.controllerPackageName = controllerPackageName;
        this.errorRedirectionParamName = errorRedirectionParamName;
    }

    public String getErrorParamName() {
        return errorParamName;
    }

    public void setErrorParamName(String errorParamName) {
        this.errorParamName = errorParamName;
    }

    public String getControllerPackageName() {
        return controllerPackageName;
    }

    public void setControllerPackageName(String controllerPackageName) {
        this.controllerPackageName = controllerPackageName;
    }

    public String getErrorRedirectionParamName() {
        return errorRedirectionParamName;
    }

    public void setErrorRedirectionParamName(String errorRedirectionParamName) {
        this.errorRedirectionParamName = errorRedirectionParamName;
    }
}
