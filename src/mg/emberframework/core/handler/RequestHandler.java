package mg.emberframework.core.handler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.HashMap;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.emberframework.core.FrontController;
import mg.emberframework.core.data.InitParameter;
import mg.emberframework.core.data.ModelValidationResults;
import mg.emberframework.core.data.ModelView;
import mg.emberframework.core.data.VerbMethod;
import mg.emberframework.core.exception.*;
import mg.emberframework.core.url.Mapping;
import mg.emberframework.utils.http.RequestUtils;
import mg.emberframework.utils.http.UrlParser;
import mg.emberframework.utils.http.UserRoleUtils;
import mg.emberframework.utils.reflection.ReflectionUtils;
import mg.emberframework.utils.scan.PackageScanner;
import mg.emberframework.utils.validation.Validator;

/**
 * Main process handler for the Ember Framework.
 * <p>
 * This class manages the initialization of the framework and the handling of HTTP
 * requests, including URL mapping, role validation, method execution, and response generation.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class RequestHandler {
    /** Constant for JSON content type. */
    private static final String CONTENT_TYPE_JSON = "application/json";
    /** Gson instance for JSON serialization. */
    private static final Gson gson = new Gson();

    /**
     * Initializes the framework by scanning for controller mappings and setting parameters.
     * @param controller the front controller instance
     * @throws ClassNotFoundException if a class cannot be found during scanning
     * @throws IOException if an I/O error occurs during initialization
     * @throws DuplicateUrlException if duplicate URL mappings are detected
     * @throws InvalidControllerPackageException if the controller package is invalid
     */
    public void init(FrontController controller)
            throws ClassNotFoundException, IOException, DuplicateUrlException, InvalidControllerPackageException {
        String packageName = controller.getInitParameter("package_name");
        String errorParamName = controller.getInitParameter("error_param_name");
        String errorRedirectionParamName = controller.getInitParameter("error_redirection_param_name");
        String roleAttributeName = controller.getInitParameter("role_attribute_name");
        String customErrorPage = controller.getInitParameter("custom_error_page");

        InitParameter initParameter = new InitParameter(
                errorParamName, packageName, errorRedirectionParamName, roleAttributeName);

        HashMap<String, Mapping> urlMappings = (HashMap<String, Mapping>) PackageScanner.scanPackage(packageName);

        FrontController.setUrlMapping(urlMappings);
        FrontController.setInitParameter(initParameter);
        FrontController.setCustomErrorPage(customErrorPage);
    }

    /**
     * Handles an incoming HTTP request and generates a response.
     * @param controller the front controller instance
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     * @throws UrlNotFoundException if the requested URL is not mapped
     * @throws NoSuchMethodException if a method cannot be found
     * @throws SecurityException if a security violation occurs
     * @throws IllegalAccessException if access to a method is denied
     * @throws IllegalArgumentException if an argument is invalid
     * @throws InvocationTargetException if method invocation fails
     * @throws InstantiationException if an object cannot be instantiated
     * @throws ServletException if a servlet error occurs
     * @throws IllegalReturnTypeException if the return type is unsupported
     * @throws AnnotationNotPresentException if a required annotation is missing
     * @throws InvalidRequestException if the request is malformed
     * @throws UnauthorizedAccessException if the user lacks permission
     * @throws URISyntaxException if the URI syntax is invalid
     */
    public void handleRequest(FrontController controller, HttpServletRequest request,
            HttpServletResponse response) throws IOException, UrlNotFoundException,
            NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException, ServletException, IllegalReturnTypeException,
            AnnotationNotPresentException, InvalidRequestException,
            UnauthorizedAccessException, URISyntaxException {

        ModelValidationResults modelValidationResults;

        if (controller.getException() != null) {
            ExceptionHandler.handleException(controller.getException(), request, response);
            return;
        }

        String verb = request.getMethod();
        String url = UrlParser.getRoute(request.getRequestURI());

        Mapping mapping = findMappingForUrl(controller, url);
        if (mapping == null) {
            throw new UrlNotFoundException("Oops, url not found!(" + url + ")");
        }

        VerbMethod verbMethod = mapping.getSpecificVerbMethod(verb);

        validateUserRole(controller, request, verbMethod);

        modelValidationResults = Validator.validateMethod(verbMethod.getMethod(), request);

        if (modelValidationResults.containsException()) {
            request = RequestUtils.generateHttpServletRequest(request, "GET");
        }

        Object result = processRequest(controller, request, mapping, verb, verbMethod, modelValidationResults);

        if (verbMethod.isRestAPI()) {
            result = convertToJson(result, response);
        }

        sendResponse(result, request, response);
    }

    /**
     * Finds the mapping for a given URL.
     * @param controller the front controller instance
     * @param url the requested URL
     * @return the corresponding {@link Mapping}, or null if not found
     */
    private Mapping findMappingForUrl(FrontController controller, String url) {
        return controller.getUrlMapping().get(url);
    }

    /**
     * Validates the user's role for accessing the requested method.
     * @param controller the front controller instance
     * @param request the HTTP request
     * @param verbMethod the method and verb pair
     * @throws UnauthorizedAccessException if the user lacks required roles
     */
    private void validateUserRole(FrontController controller, HttpServletRequest request, VerbMethod verbMethod)
            throws UnauthorizedAccessException {
        UserRoleUtils userRoleUtility = new UserRoleUtils(controller.getInitParameter().getRoleAttributeName());
        userRoleUtility.checkUserRole(request, verbMethod);
    }

    /**
     * Processes the request and returns the result.
     * @param controller the front controller instance
     * @param request the HTTP request
     * @param mapping the URL mapping
     * @param verb the HTTP verb
     * @param verbMethod the method and verb pair
     * @param modelValidationResults the validation results
     * @return the result of the request processing
     * @throws IllegalAccessException if access to a method is denied
     * @throws IllegalArgumentException if an argument is invalid
     * @throws InvocationTargetException if method invocation fails
     * @throws InstantiationException if an object cannot be instantiated
     * @throws NoSuchMethodException if a method cannot be found
     * @throws SecurityException if a security violation occurs
     * @throws AnnotationNotPresentException if a required annotation is missing
     * @throws InvalidRequestException if the request is malformed
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    private Object processRequest(FrontController controller, HttpServletRequest request,
            Mapping mapping, String verb, VerbMethod verbMethod, ModelValidationResults modelValidationResults)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            InstantiationException, NoSuchMethodException, SecurityException, AnnotationNotPresentException,
            InvalidRequestException, IOException, ServletException {

        ModelValidationResults temp = ((ModelValidationResults) request.getAttribute(controller.getInitParameter().getErrorParamName()));

        if (temp == null || !temp.containsException()) {
            request.setAttribute(controller.getInitParameter().getErrorParamName(), modelValidationResults);
        }

        if (modelValidationResults.containsException()) {
            return handleValidationException(controller, request, verbMethod, modelValidationResults);
        } else {
            return ReflectionUtils.executeRequestMethod(mapping, request, verb);
        }
    }

    /**
     * Handles validation exceptions by returning a {@link ModelView}.
     * @param controller the front controller instance
     * @param request the HTTP request
     * @param verbMethod the method and verb pair
     * @param modelValidationResults the validation results
     * @return a {@link ModelView} with error information
     */
    private ModelView handleValidationException(FrontController controller,
            HttpServletRequest request, VerbMethod verbMethod, ModelValidationResults modelValidationResults) {
        ModelView modelView = new ModelView();
        modelView.setRedirect(false);
        modelView.setUrl(request.getParameter(controller.getInitParameter().getErrorRedirectionParamName()));

        if (verbMethod.isRestAPI()) {
            modelView.addObject(controller.getInitParameter().getErrorParamName(), modelValidationResults);
        }

        return modelView;
    }

    /**
     * Converts the result to JSON for REST API responses.
     * @param methodObject the result object
     * @param response the HTTP response
     * @return the JSON string representation
     */
    private String convertToJson(Object methodObject, HttpServletResponse response) {
        String json;
        if (methodObject instanceof ModelView modelView) {
            json = gson.toJson(modelView.getData());
        } else {
            json = gson.toJson(methodObject);
        }
        response.setContentType(CONTENT_TYPE_JSON);
        return json;
    }

    /**
     * Sends the response based on the result type.
     * @param result the result of the request processing
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     * @throws IllegalReturnTypeException if the return type is unsupported
     */
    private void sendResponse(Object result, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, IllegalReturnTypeException {
        if (result instanceof String) {
            response.getWriter().println(result.toString());
        } else if (result instanceof ModelView modelView) {
            RedirectionHandler.redirect(request, response, modelView);
        } else {
            throw new IllegalReturnTypeException("Invalid return type");
        }
    }
}