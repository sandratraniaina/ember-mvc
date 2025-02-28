package mg.emberframework.core.data;

import java.lang.reflect.Method;

public class RequestVerb {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";
    public static final String PUT = "PUT";

    private RequestVerb() {
    }

    public static String getMethodVerb(Method method) {
        String verb = RequestVerb.GET;
        if (method.isAnnotationPresent(mg.emberframework.annotation.request.Post.class)) {
            verb = RequestVerb.POST;
        }
        return verb;
    }
}
