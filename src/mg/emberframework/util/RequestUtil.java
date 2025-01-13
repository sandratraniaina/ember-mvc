package mg.emberframework.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class RequestUtil {
    private RequestUtil() {}

    public static HttpServletRequest generateHttpServletRequest(HttpServletRequest source, String method) {
        return new HttpServletRequestWrapper(source) {
            @Override
            public String getMethod() {
                return method;
            }
        };
    }
}
