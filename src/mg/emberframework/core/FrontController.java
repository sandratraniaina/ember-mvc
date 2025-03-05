package mg.emberframework.core;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.emberframework.core.data.InitParameter;
import mg.emberframework.core.exception.DuplicateUrlException;
import mg.emberframework.core.exception.InvalidControllerPackageException;
import mg.emberframework.core.handler.ExceptionHandler;
import mg.emberframework.core.handler.RequestHandler;
import mg.emberframework.core.url.Mapping;

/**
 * The central servlet for handling HTTP requests in the Ember Framework.
 * <p>
 * This class extends {@link HttpServlet} to process GET and POST requests, initialize
 * the framework, and manage URL mappings, initialization parameters, and exceptions.
 * It is annotated with {@code @MultipartConfig} to support file uploads.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
@MultipartConfig
public class FrontController extends HttpServlet {
    /** The map of URL mappings to controller methods. */
    private static Map<String, Mapping> urlMappings;
    /** The exception encountered during initialization or processing, if any. */
    private static Exception exception = null;
    /** The initialization parameters for the framework. */
    private static InitParameter initParameter;
    /** The custom error page URL, if specified. */
    private static String customErrorPage;

    /** The request handler for processing HTTP requests. */
    private transient RequestHandler requestHandler;

    /**
     * Processes an HTTP request by delegating to the request handler.
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs during processing
     * @throws IOException if an I/O error occurs during processing
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            requestHandler.handleRequest(this, request, response);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, request, response);
        }
    }

    /**
     * Handles HTTP GET requests.
     * @param req the HTTP request
     * @param resp the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (ServletException e) {
            ExceptionHandler.handleException(
                    new ServletException("A servlet error has occurred while executing doGet method", e.getCause()), req,
                    resp);
        } catch (IOException e) {
            ExceptionHandler.handleException(
                    new IOException("An IO error has occurred while executing doGet method", e.getCause()), req, resp);
        }
    }

    /**
     * Handles HTTP POST requests.
     * @param req the HTTP request
     * @param resp the HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (ServletException e) {
            ExceptionHandler.handleException(
                    new ServletException("A servlet error has occurred while executing doPost method", e.getCause()), req,
                    resp);
        } catch (IOException e) {
            ExceptionHandler.handleException(
                    new IOException("An IO error has occurred while executing doPost method", e.getCause()), req, resp);
        }
    }

    /**
     * Initializes the servlet by setting up the request handler and framework configuration.
     * @throws ServletException if an error occurs during initialization
     */
    @Override
    public void init() throws ServletException {
        try {
            requestHandler = new RequestHandler();
            requestHandler.init(this);
        } catch (InvalidControllerPackageException | DuplicateUrlException e) {
            setException(e);
        } catch (Exception e) {
            setException(new Exception("An error has occurred during initialization + " + e.getMessage(), e.getCause()));
        }
    }

    /**
     * Gets the URL mappings.
     * @return the map of URL mappings
     */
    public Map<String, Mapping> getUrlMapping() {
        return urlMappings;
    }

    /**
     * Sets the URL mappings.
     * @param urlMapping the map of URL mappings to set
     */
    public static void setUrlMapping(Map<String, Mapping> urlMapping) {
        urlMappings = urlMapping;
    }

    /**
     * Gets the exception encountered during processing.
     * @return the exception, or null if none
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Sets the exception encountered during processing.
     * @param newException the exception to set
     */
    public static void setException(Exception newException) {
        exception = newException;
    }

    /**
     * Gets the initialization parameters.
     * @return the initialization parameters
     */
    public InitParameter getInitParameter() {
        return initParameter;
    }

    /**
     * Sets the initialization parameters.
     * @param newInitParameter the initialization parameters to set
     */
    public static void setInitParameter(InitParameter newInitParameter) {
        initParameter = newInitParameter;
    }

    /**
     * Gets the request handler.
     * @return the request handler
     */
    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

    /**
     * Sets the request handler.
     * @param requestHandler the request handler to set
     */
    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    /**
     * Gets the custom error page URL.
     * @return the custom error page URL, or null if not set
     */
    public static String getCustomErrorPage() {
        return customErrorPage;
    }

    /**
     * Sets the custom error page URL.
     * @param customErrorPage the custom error page URL to set
     */
    public static void setCustomErrorPage(String customErrorPage) {
        FrontController.customErrorPage = customErrorPage;
    }
}