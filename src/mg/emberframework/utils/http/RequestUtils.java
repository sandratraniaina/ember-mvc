package mg.emberframework.utils.http;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class RequestUtils {
    private RequestUtils() {}

    public static HttpServletRequestWrapper generateHttpServletRequest(HttpServletRequest source, String method) {
        return new HttpServletRequestWrapper(source) {
            @Override
            public String getMethod() {
                return method;
            }
        };
    }

    public static String getRequestRefererUrl(HttpServletRequest request) {
        return request.getHeader("Referer");
    }
}
