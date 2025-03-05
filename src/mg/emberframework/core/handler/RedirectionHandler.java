package mg.emberframework.core.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.emberframework.core.data.ModelView;

/**
 * Handles redirection and forwarding of requests in the Ember Framework.
 * <p>
 * This utility class processes {@link ModelView} objects to either redirect to a URL
 * or forward the request with data attributes to a view.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class RedirectionHandler {
    /** Private constructor to prevent instantiation. */
    private RedirectionHandler() {
    }

    /**
     * Sets request attributes from a data map.
     * @param request the HTTP request
     * @param data the map of attributes to set
     */
    private static void setRequestAttributes(HttpServletRequest request, HashMap<String, Object> data) {
        for (Entry<String, Object> entry : data.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Redirects or forwards the request based on the {@link ModelView}.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param modelView the model view containing the URL and data
     * @throws ServletException if a servlet error occurs during forwarding
     * @throws IOException if an I/O error occurs during redirection
     */
    public static void redirect(HttpServletRequest request, HttpServletResponse response, ModelView modelView)
            throws ServletException, IOException {
        if (modelView.isRedirect()) {
            response.sendRedirect(modelView.getUrl());
        } else {
            HashMap<String, Object> data = ((HashMap<String, Object>) modelView.getData());
            setRequestAttributes(request, data);
            request.getRequestDispatcher(modelView.getUrl()).forward(request, response);
        }
    }
}