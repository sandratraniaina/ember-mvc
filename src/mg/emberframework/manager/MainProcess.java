package mg.emberframework.manager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.emberframework.controller.FrontController;
import mg.emberframework.manager.data.InitParameter;
import mg.emberframework.manager.data.ModelValidationResults;
import mg.emberframework.manager.data.ModelView;
import mg.emberframework.manager.data.VerbMethod;
import mg.emberframework.manager.exception.*;
import mg.emberframework.manager.handler.ExceptionHandler;
import mg.emberframework.manager.handler.RedirectionHandler;
import mg.emberframework.manager.url.Mapping;
import mg.emberframework.util.*;
import mg.emberframework.util.http.RequestUtil;
import mg.emberframework.util.http.UrlParser;
import mg.emberframework.util.http.UserRoleUtility;
import mg.emberframework.util.reflection.ReflectUtils;
import mg.emberframework.util.scan.PackageScanner;
import mg.emberframework.util.validation.Validator;

/**
 * Main process handler for the Ember Framework.
 * Manages request processing, initialization, and request handling flow.
 */
public class MainProcess {
    // Constants
    private static final String CONTENT_TYPE_JSON = "application/json";

    // Instance variables
    private List<Exception> exceptions;

    // Static variables
    private static FrontController frontController;
    private static ModelValidationResults validationExceptionHandler = new ModelValidationResults();
    private static String defaultRoleAttribute;
    private static final Gson gson = new Gson();

    public static void init(FrontController controller)
            throws ClassNotFoundException, IOException, DuplicateUrlException, InvalidControllerPackageException {
        frontController = controller;

        // Extract initialization parameters
        String packageName = controller.getInitParameter("package_name");
        String errorParamName = controller.getInitParameter("error_param_name");
        String errorRedirectionParamName = controller.getInitParameter("error_redirection_param_name");
        String roleAttributeName = controller.getInitParameter("role_attribute_name");

        // Create initialization parameter object
        InitParameter initParameter = new InitParameter(
                errorParamName, packageName, errorRedirectionParamName, roleAttributeName);

        // Store role attribute name for role checking
        defaultRoleAttribute = initParameter.getRoleAttributeName();

        // Scan package for mappings
        HashMap<String, Mapping> urlMappings = (HashMap<String, Mapping>) PackageScanner.scanPackage(packageName);

        // Set mappings and parameters in FrontController
        FrontController.setUrlMapping(urlMappings);
        FrontController.setInitParameter(initParameter);
    }

    public static void handleRequest(FrontController controller, HttpServletRequest request,
            HttpServletResponse response) throws IOException, UrlNotFoundException,
            NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException, ServletException, IllegalReturnTypeException,
            AnnotationNotPresentException, InvalidRequestException,
            UnauthorizedAccessException, URISyntaxException {

        // Check for existing exceptions in controller
        if (controller.getException() != null) {
            ExceptionHandler.handleException(controller.getException(), response);
            return;
        }

        // Extract request information
        String verb = request.getMethod();
        String url = UrlParser.getRoute(request.getRequestURI());

        // Find mapping for URL
        Mapping mapping = findMappingForUrl(url);
        if (mapping == null) {
            throw new UrlNotFoundException("Oops, url not found!(" + url + ")");
        }

        // Get method for HTTP verb
        VerbMethod verbMethod = mapping.getSpecificVerbMethod(verb);

        // Validate user role for access
        validateUserRole(request, verbMethod);

        // Validate method parameters
        validationExceptionHandler = Validator.validateMethod(verbMethod.getMethod(), request);

        // Process request and get result
        Object result = processRequest(controller, request, mapping, verb, verbMethod);

        // Prepare request with validation results
        prepareRequest(controller, request);

        // Handle REST API responses
        if (verbMethod.isRestAPI()) {
            result = convertToJson(result, response);
        }

        // Send response based on result type
        sendResponse(result, request, response);
    }

    private static Mapping findMappingForUrl(String url) {
        return frontController.getUrlMapping().get(url);
    }

    private static void validateUserRole(HttpServletRequest request, VerbMethod verbMethod)
            throws UnauthorizedAccessException {
        UserRoleUtility userRoleUtility = new UserRoleUtility(defaultRoleAttribute);
        userRoleUtility.checkUserRole(request, verbMethod);
    }

    private static Object processRequest(FrontController controller, HttpServletRequest request,
            Mapping mapping, String verb, VerbMethod verbMethod)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            InstantiationException, NoSuchMethodException, SecurityException, AnnotationNotPresentException,
            InvalidRequestException, IOException, ServletException {

        if (validationExceptionHandler.containsException()) {
            request = RequestUtil.generateHttpServletRequest(request, "GET");
            return handleValidationException(controller, request, verbMethod);
        } else {
            return ReflectUtils.executeRequestMethod(mapping, request, verb);
        }
    }

    private static ModelView handleValidationException(FrontController controller,
            HttpServletRequest request, VerbMethod verbMethod) {
        ModelView modelView = new ModelView();
        modelView.setRedirect(false);
        modelView.setUrl(request.getParameter(controller.getInitParameter().getErrorRedirectionParamName()));

        if (verbMethod.isRestAPI()) {
            modelView.addObject(controller.getInitParameter().getErrorParamName(), validationExceptionHandler);
        }

        return modelView;
    }

    private static void prepareRequest(FrontController controller, HttpServletRequest request) {
        if (validationExceptionHandler == null) {
            validationExceptionHandler = new ModelValidationResults();
        }

        if (request.getAttribute(controller.getInitParameter().getErrorParamName()) == null) {
            request.setAttribute(controller.getInitParameter().getErrorParamName(), validationExceptionHandler);
        }
    }

    private static String convertToJson(Object methodObject, HttpServletResponse response) {
        String json;
        if (methodObject instanceof ModelView modelView) {
            json = gson.toJson(modelView.getData());
        } else {
            json = gson.toJson(methodObject);
        }
        response.setContentType(CONTENT_TYPE_JSON);
        return json;
    }

    private static void sendResponse(Object result, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, IllegalReturnTypeException {
        if (result instanceof String) {
            response.getWriter().println(result.toString());
        } else if (result instanceof ModelView modelView) {
            RedirectionHandler.redirect(request, response, modelView);
        } else {
            throw new IllegalReturnTypeException("Invalid return type");
        }
    }

    // Getters and setters
    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }
}