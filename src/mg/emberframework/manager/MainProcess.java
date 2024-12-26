package mg.emberframework.manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.emberframework.controller.FrontController;
import mg.emberframework.manager.data.ModelView;
import mg.emberframework.manager.data.VerbMethod;
import mg.emberframework.manager.exception.AnnotationNotPresentException;
import mg.emberframework.manager.exception.DuplicateUrlException;
import mg.emberframework.manager.exception.IllegalReturnTypeException;
import mg.emberframework.manager.exception.InvalidControllerPackageException;
import mg.emberframework.manager.exception.InvalidRequestException;
import mg.emberframework.manager.exception.ModelValidationException;
import mg.emberframework.manager.exception.UrlNotFoundException;
import mg.emberframework.manager.handler.ExceptionHandler;
import mg.emberframework.manager.url.Mapping;
import mg.emberframework.util.PackageScanner;
import mg.emberframework.util.ReflectUtils;

public class MainProcess {
    static FrontController frontController;
    private List<Exception> exceptions;

    private static String handleRest(Object methodObject, HttpServletResponse response) {
        Gson gson = new Gson();
        String json = null;
        if (methodObject instanceof ModelView) {
            json = gson.toJson(((ModelView)methodObject).getData());
        } else {
            json = gson.toJson(methodObject);
        }   
        response.setContentType("application/json");
        return json;
    }

    public static void handleRequest(FrontController controller, HttpServletRequest request,
            HttpServletResponse response) throws IOException, UrlNotFoundException, ClassNotFoundException,
            NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException, ServletException, IllegalReturnTypeException, NoSuchFieldException, AnnotationNotPresentException, InvalidRequestException, ModelValidationException {
        PrintWriter out = response.getWriter();
        String verb = request.getMethod();

        if (controller.getException() != null) {
            ExceptionHandler.handleException(controller.getException(), response);
            return;
        }

        String url = request.getRequestURI().substring(request.getContextPath().length());
        Mapping mapping = frontController.getURLMapping().get(url);
        
        if (mapping == null) {
            throw new UrlNotFoundException("Oops, url not found!");
        }
        
        VerbMethod verbMethod = mapping.getSpecificVerbMethod(verb);
        
        Object result = ReflectUtils.executeRequestMethod(mapping, request, verb);

        if (verbMethod.isRestAPI()) {
            result = handleRest(result, response);
        }   

        if (result instanceof String) {
            out.println(result.toString());
        } else if (result instanceof ModelView) {
            ModelView modelView = ((ModelView) result);
            HashMap<String, Object> data = ((HashMap<String, Object>)modelView.getData());

            setRequestAttributes(request, data);

            request.getRequestDispatcher(modelView.getUrl()).forward(request, response);
        } else {
            throw new IllegalReturnTypeException("Invalid return type");
        }
    }

    private static void setRequestAttributes(HttpServletRequest request, HashMap<String, Object> data) {
        for (Entry<String, Object> entry : data.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    public static void init(FrontController controller)
            throws ClassNotFoundException, IOException, DuplicateUrlException, InvalidControllerPackageException {
        frontController = controller;

        String packageName = controller.getInitParameter("package_name");

        HashMap<String, Mapping> urlMappings;
        urlMappings = (HashMap<String, Mapping>) PackageScanner.scanPackage(packageName);

        controller.setURLMapping(urlMappings);
    }

    // Getters and setters
    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }
}
