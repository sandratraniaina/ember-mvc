package mg.emberframework.core.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.emberframework.core.data.ModelView;

public class RedirectionHandler {
    private RedirectionHandler() {
    }

    private static void setRequestAttributes(HttpServletRequest request, HashMap<String, Object> data) {
        for (Entry<String, Object> entry : data.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }

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
