package com.aoindustries.website;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.apache.struts.Globals;

/**
 * Filters setAttribute to make sure all session objects are precisely as
 * expected by <code>SessionResponseWrapper</code> to ensure proper conditional
 * use of <code>jsessionid</code>
 *
 * @author  AO Industries, Inc.
 */
public class FilteredHttpSession implements HttpSession {

    private final HttpSession wrapped;

    public FilteredHttpSession(HttpSession wrapped) {
        this.wrapped = wrapped;
    }

    public long getCreationTime() {
        return wrapped.getCreationTime();
    }

    public String getId() {
        return wrapped.getId();
    }

    public long getLastAccessedTime() {
        return wrapped.getLastAccessedTime();
    }

    public ServletContext getServletContext() {
        return wrapped.getServletContext();
    }

    public void setMaxInactiveInterval(int interval) {
        wrapped.setMaxInactiveInterval(interval);
    }

    public int getMaxInactiveInterval() {
        return wrapped.getMaxInactiveInterval();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public javax.servlet.http.HttpSessionContext getSessionContext() {
        return wrapped.getSessionContext();
    }

    public Object getAttribute(String name) {
        return wrapped.getAttribute(name);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public Object getValue(String name) {
        return wrapped.getValue(name);
    }

    public Enumeration getAttributeNames() {
        return wrapped.getAttributeNames();
    }

    /**
     * @deprecated
     */
    @Deprecated
    public String[] getValueNames() {
        return wrapped.getValueNames();
    }

    /**
     * Makes sure the session attribute is acceptable.  Throws AssertionError
     * if it is not.
     */
    static void checkSessionAttribute(String name, Object value) {
        // All all null to be set
        if(value!=null) {
            // These names are always allowed
            if(
                !Constants.AUTHENTICATION_TARGET.equals(name)
                && !Globals.LOCALE_KEY.equals(name)
                && !Constants.LAYOUT.equals(name)
                && !Constants.SU_REQUESTED.equals(name)
                && !Constants.AO_CONN.equals(name)
                && !Constants.AUTHENTICATED_AO_CONN.equals(name)
                // Must be an SessionActionForm if none of the above
                && !(value instanceof SessionActionForm)
            ) {
                throw new AssertionError(ApplicationResourcesAccessor.getMessage(Locale.getDefault(), "FilteredHttpSession.unexpectedSessionAttribute", name, value.getClass().getName()));
            }
        }
    }

    public void setAttribute(String name, Object value) {
        checkSessionAttribute(name, value);
        wrapped.setAttribute(name, value);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void putValue(String name, Object value) {
        checkSessionAttribute(name, value);
        wrapped.putValue(name, value);
    }

    public void removeAttribute(String name) {
        wrapped.removeAttribute(name);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void removeValue(String name) {
        wrapped.removeValue(name);
    }

    public void invalidate() {
        wrapped.invalidate();
    }

    public boolean isNew() {
        return wrapped.isNew();
    }
}
