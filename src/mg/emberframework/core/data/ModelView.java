package mg.emberframework.core.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a model and view for rendering responses in the framework.
 * <p>
 * This class encapsulates a URL, data attributes, and a redirect flag, typically
 * used to pass data to a view or redirect to another URL.
 * 
 * @author Sandratra NIAINA
 * @version 1.0.0
 * @since 1.0.0
 */
public class ModelView {
    /** The URL for rendering or redirection. */
    String url;
    /** The data attributes to pass to the view. */
    Map<String, Object> data = new HashMap<>();
    /** Indicates if this is a redirect response. */
    boolean redirect = false;

    /**
     * Adds an object to the data map.
     * @param attribute the attribute name
     * @param object the object to store
     */
    public void addObject(String attribute, Object object) {
        getData().put(attribute, object);
    }

    /** Default constructor. */
    public ModelView() {
    }

    /**
     * Constructs a model view with a URL and data.
     * @param url the target URL
     * @param data the data map
     */
    public ModelView(String url, Map<String, Object> data) {
        setUrl(url);
        setData(data);
    }

    /**
     * Gets the target URL.
     * @return the URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the target URL.
     * @param url the URL to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the data map.
     * @return the data map
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Sets the data map.
     * @param data the data to set
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * Checks if this is a redirect response.
     * @return true if redirect, false otherwise
     */
    public boolean isRedirect() {
        return redirect;
    }

    /**
     * Sets the redirect flag.
     * @param redirect the redirect status to set
     */
    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }
}