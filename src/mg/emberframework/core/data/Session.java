package mg.emberframework.core.data;

import jakarta.servlet.http.HttpSession;

/**
 * Wraps an {@link HttpSession} to manage session data in the framework.
 * <p>
 * This class provides methods to add, retrieve, and remove session attributes,
 * as well as clear the session entirely.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class Session {
    /** The underlying HTTP session object. */
    HttpSession userSession;

    /**
     * Retrieves an attribute from the session.
     * @param name the attribute name
     * @return the attribute value, or null if not found
     */
    public Object get(String name) {
        return getUserSession().getAttribute(name);
    }

    /**
     * Adds an attribute to the session.
     * @param name the attribute name
     * @param value the attribute value
     */
    public void add(String name, Object value) {
        getUserSession().setAttribute(name, value);
    }

    /**
     * Removes an attribute from the session.
     * @param name the attribute name
     */
    public void remove(String name) {
        getUserSession().removeAttribute(name);
    }

    /** Clears the session, invalidating it. */
    public void clear() {
        getUserSession().invalidate();
    }

    /** Default constructor. */
    public Session() {
    }

    /**
     * Constructs a session with an existing {@link HttpSession}.
     * @param session the HTTP session to wrap
     */
    public Session(HttpSession session) {
        setUserSession(session);
    }

    /**
     * Gets the underlying HTTP session.
     * @return the session object
     */
    public HttpSession getUserSession() {
        return userSession;
    }

    /**
     * Sets the underlying HTTP session.
     * @param userSession the session to set
     */
    public void setUserSession(HttpSession userSession) {
        this.userSession = userSession;
    }
}