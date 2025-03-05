package mg.emberframework.core.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.emberframework.core.FrontController;
import mg.emberframework.core.exception.UnauthorizedAccessException;
import mg.emberframework.core.exception.UrlNotFoundException;

/**
 * Handles exceptions and generates error responses in the Ember Framework.
 * <p>
 * This utility class provides methods to process exceptions, either by rendering
 * a default HTML error page or forwarding to a custom error page.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class ExceptionHandler {
    /** Logger for recording exceptions. */
    private static final Logger logger = Logger.getLogger(ExceptionHandler.class.getName());
    
    /** Private constructor to prevent instantiation. */
    private ExceptionHandler() {
    }

    /**
     * Processes an error by generating a default HTML error page.
     * @param response the HTTP response
     * @param statusCode the HTTP status code
     * @param exception the exception to process
     * @throws IOException if an I/O error occurs while writing the response
     */
    public static void processError(HttpServletResponse response, int statusCode, Exception exception) throws IOException {
        String errorName = getErrorName(statusCode);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html lang=\"en\">");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\">");
            writer.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            writer.println("<title>Ember MVC Error</title>");
            writer.println("<style>");
            writer.println(
                    "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4; color: #444; line-height: 1.6; padding: 20px; }");
            writer.println("h1 { color: #e04e39; text-align: center; }");
            writer.println("h2 { color: #1e719b; }");
            writer.println(
                    ".container { max-width: 800px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
            writer.println(".error-code { font-size: 1.2em; color: #e04e39; }");
            writer.println(".error-message { font-weight: bold; }");
            writer.println(
                    ".stack-trace { background-color: #f8f8f8; border: 1px solid #ddd; padding: 10px; overflow-x: auto; }");
            writer.println("</style>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<div class=\"container\">");
            writer.println("<h1>Ember MVC</h1>");
            writer.println("<h2 class=\"error-code\">" + statusCode + " " + errorName + "</h2>");
            writer.println("<p class=\"error-message\">" + exception.getMessage() + "</p>");
            writer.println("<h3>Stack Trace:</h3>");
            writer.println("<pre class=\"stack-trace\">");
            exception.printStackTrace(writer);
            writer.println("</pre>");
            writer.println("</div>");
            writer.println("</body>");
            writer.println("</html>");
        }
    }

    /**
     * Processes an error by forwarding to a custom error page.
     * @param request the HTTP request
     * @param response the HTTP response
     * @param statusCode the HTTP status code
     * @param exception the exception to process
     * @param page the custom error page URL
     * @throws ServletException if a servlet error occurs during forwarding
     * @throws IOException if an I/O error occurs
     */
    public static void processCustomError(HttpServletRequest request, HttpServletResponse response, int statusCode, Exception exception, String page) throws ServletException, IOException {
        request.setAttribute("exception", exception);
        request.setAttribute("status-code", statusCode);
        request.setAttribute("message", exception.getMessage());

        request.getRequestDispatcher(page).forward(request, response);
    }

    /**
     * Determines the error name based on the status code.
     * @param statusCode the HTTP status code
     * @return the corresponding error name
     */
    private static String getErrorName(Integer statusCode) {
        if (statusCode == null)
            return "Unknown Error";
        switch (statusCode) {
            case 404:
                return "Not Found";
            case 500:
                return "Internal Server Error";
            default:
                return "HTTP Error " + statusCode;
        }
    }

    /**
     * Handles a single exception and generates an appropriate response.
     * @param e the exception to handle
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     */
    public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if (!response.isCommitted()) {
                int errorCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                if (e instanceof UrlNotFoundException) {
                    errorCode = HttpServletResponse.SC_NOT_FOUND;
                } else if (e instanceof UnauthorizedAccessException) {
                    errorCode = HttpServletResponse.SC_UNAUTHORIZED;
                }
                if (FrontController.getCustomErrorPage() != null) {
                    processCustomError(request, response, errorCode, e, FrontController.getCustomErrorPage());
                } else {
                    processError(response, errorCode, e);
                }
            }
        } catch (IOException exc) {
            logger.log(Level.SEVERE, exc.getMessage());
        }
    }

    /**
     * Handles a list of exceptions and processes each one.
     * @param exceptions the list of exceptions to handle
     * @param request the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet error occurs
     */
    public static void handleExceptions(List<Exception> exceptions, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        for (Exception e : exceptions) {
            handleException(e, request, response);
        }
    }
}