package mg.emberframework.core.data;

import jakarta.servlet.http.HttpSession;

public class Session {
    HttpSession userSession;

    // Class methods
    public Object get(String name) {
        return getUserSession().getAttribute(name);
    }

    public void add(String name, Object value) {
        getUserSession().setAttribute(name, value);
    }

    public void remove(String name) {
        getUserSession().removeAttribute(name);
    }

    public void clear() {
        getUserSession().invalidate();
    }

    // Contructor
    public Session() {
    }

    public Session(HttpSession session) {
        setUserSession(session);
    }

    // Getters and setters
    public HttpSession getUserSession() {
        return userSession;
    }

    public void setUserSession(HttpSession userSession) {
        this.userSession = userSession;
    }
}