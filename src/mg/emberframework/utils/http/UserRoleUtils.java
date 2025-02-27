package mg.emberframework.utils.http;

import java.lang.reflect.Method;
import java.util.Arrays;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mg.emberframework.annotation.RequiredRole;
import mg.emberframework.manager.data.VerbMethod;
import mg.emberframework.manager.exception.UnauthorizedAccessException;

public class UserRoleUtils {
    private String defaultRoleAttribute;

    public UserRoleUtils(String roleAttribute) {
        setDefaultRoleAttribute(roleAttribute);
    }

    public  boolean hasRequiredRole(String userRole, String[] requiredRoles) {
        if (userRole == null || requiredRoles == null || requiredRoles.length == 0) {
            return false;
        }

        for (String required : requiredRoles) {
            if (required.equalsIgnoreCase(userRole)) {
                return true;
            }
        }
        return false;
    }

    private String getUserRoleFromSession(HttpServletRequest request)
            throws UnauthorizedAccessException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnauthorizedAccessException("No active session found");
        }

        Object role = session.getAttribute(defaultRoleAttribute);
        if (role == null) {
            throw new UnauthorizedAccessException("No role defined in session");
        }

        return role.toString();
    }

    public void checkUserRole(HttpServletRequest request, VerbMethod verbMethod)
            throws UnauthorizedAccessException {
        Method method = verbMethod.getMethod();

        RequiredRole classRole = method.getDeclaringClass().getAnnotation(RequiredRole.class);
        RequiredRole methodRole = method.getAnnotation(RequiredRole.class);

        if (classRole == null && methodRole == null) {
            return;
        }

        String userRole = getUserRoleFromSession(request);

        if (classRole != null) {
            String[] classRequiredRoles = classRole.values();
            if (!hasRequiredRole(userRole, classRequiredRoles)) {
                throw new UnauthorizedAccessException(
                        "Class-level role check failed. Required: " + Arrays.toString(classRequiredRoles) +
                                ", Found: " + userRole);
            }
        }

        if (methodRole != null) {
            String[] methodRequiredRoles = methodRole.values();
            if (!hasRequiredRole(userRole, methodRequiredRoles)) {
                throw new UnauthorizedAccessException(
                        "Method-level role check failed. Required: " + Arrays.toString(methodRequiredRoles) +
                                ", Found: " + userRole);
            }
        }
    }

    // Getters and setters
    public String getDefaultRoleAttribute() {
        return defaultRoleAttribute;
    }

    public void setDefaultRoleAttribute(String defaultRoleAttribute) {
        this.defaultRoleAttribute = defaultRoleAttribute;
    }
}
