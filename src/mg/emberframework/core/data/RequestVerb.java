package mg.emberframework.core.data;

import java.lang.reflect.Method;

/**
 * Defines constants and utilities for HTTP request verbs.
 * <p>
 * This utility class provides standard HTTP verb constants and a method to
 * determine the verb associated with a given method based on annotations.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class RequestVerb {
    /** Constant for HTTP GET verb. */
    public static final String GET = "GET";
    /** Constant for HTTP POST verb. */
    public static final String POST = "POST";
    /** Constant for HTTP DELETE verb. */
    public static final String DELETE = "DELETE";
    /** Constant for HTTP PUT verb. */
    public static final String PUT = "PUT";

    /** Private constructor to prevent instantiation. */
    private RequestVerb() {
    }

    /**
     * Determines the HTTP verb for a method based on its annotations.
     * <p>
     * Defaults to {@code GET} unless the method is annotated with {@code @Post}.
     * @param method the method to analyze
     * @return the HTTP verb (e.g., "GET", "POST")
     */
    public static String getMethodVerb(Method method) {
        String verb = RequestVerb.GET;
        if (method.isAnnotationPresent(mg.emberframework.annotation.http.Post.class)) {
            verb = RequestVerb.POST;
        }
        return verb;
    }
}