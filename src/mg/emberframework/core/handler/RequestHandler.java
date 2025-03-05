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
 * Manages request processing, initialization, and request handling flow.
 */
public class RequestHandler {
    // Constants
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final Gson gson = new Gson();

    public void init(FrontController controller)
            throws ClassNotFoundException, IOException, DuplicateUrlException, InvalidControllerPackageException {

        // Extract initialization parameters
        String packageName = controller.getInitParameter("package_name");
        String errorParamName = controller.getInitParameter("error_param_name");
        String errorRedirectionParamName = controller.getInitParameter("error_redirection_param_name");
        String roleAttributeName = controller.getInitParameter("role_attribute_name");
        String customErrorPage = controller.getInitParameter("custom_error_page");

        // Create initialization parameter object
        InitParameter initParameter = new InitParameter(
                errorParamName, packageName, errorRedirectionParamName, roleAttributeName);

        // Scan package for mappings
        HashMap<String, Mapping> urlMappings = (HashMap<String, Mapping>) PackageScanner.scanPackage(packageName);

        // Set mappings and parameters in FrontController
        FrontController.setUrlMapping(urlMappings);
        FrontController.setInitParameter(initParameter);
        FrontController.setCustomErrorPage(customErrorPage);
    }

    public void handleRequest(FrontController controller, HttpServletRequest request,
            HttpServletResponse response) throws IOException, UrlNotFoundException,
            NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException, ServletException, IllegalReturnTypeException,
            AnnotationNotPresentException, InvalidRequestException,
            UnauthorizedAccessException, URISyntaxException {

        ModelValidationResults modelValidationResults;

        // Check for existing exceptions in controller
        if (controller.getException() != null) {
            ExceptionHandler.handleException(controller.getException(), response);
            return;
        }

        // Extract request information
        String verb = request.getMethod();
        String url = UrlParser.getRoute(request.getRequestURI());

        // Find mapping for URL
        Mapping mapping = findMappingForUrl(controller, url);
        if (mapping == null) {
            throw new UrlNotFoundException("Oops, url not found!(" + url + ")");
        }

        // Get method for HTTP verb
        VerbMethod verbMethod = mapping.getSpecificVerbMethod(verb);

        // Validate user role for access
        validateUserRole(controller, request, verbMethod);

        // Validate method parameters
        modelValidationResults = Validator.validateMethod(verbMethod.getMethod(), request);

        if (modelValidationResults.containsException()) {
            request = RequestUtils.generateHttpServletRequest(request, "GET");
        }

        // Process request and get result
        Object result = processRequest(controller, request, mapping, verb, verbMethod, modelValidationResults);

        // Handle REST API responses
        if (verbMethod.isRestAPI()) {
            result = convertToJson(result, response);
        }

        // Send response based on result type
        sendResponse(result, request, response);
    }

    private Mapping findMappingForUrl(FrontController controller, String url) {
        return controller.getUrlMapping().get(url);
    }

    private void validateUserRole(FrontController controller, HttpServletRequest request, VerbMethod verbMethod)
            throws UnauthorizedAccessException {
        UserRoleUtils userRoleUtility = new UserRoleUtils(controller.getInitParameter().getRoleAttributeName());
        userRoleUtility.checkUserRole(request, verbMethod);
    }

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