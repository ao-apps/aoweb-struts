package com.aoindustries.website;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author  AO Industries, Inc.
 */
public class SessionSharingRequestWrapper extends HttpServletRequestWrapper {

    final private SessionSharingResponseWrapper response;

    public SessionSharingRequestWrapper(HttpServletRequest request, SessionSharingResponseWrapper response) {
        super(request);
        this.response = response;
    }

    public HttpSession getSession() {
        HttpSession session = super.getSession();
        processSessionCookie(session);
        return session;
    }

    public HttpSession getSession(boolean create) {
        HttpSession session = super.getSession(create);
        processSessionCookie(session);
        return session;
    }

    private void processSessionCookie(HttpSession session) {
        if (null == response || null == session) {
            // No response or session object attached, skip the pre processing
            return;
        }
        // cookieOverWritten - Flag to filter multiple "Set-Cookie" headers
        Object cookieOverWritten = getAttribute("COOKIE_OVERWRITTEN_FLAG");
        //System.err.println("DEBUG: SessionSharingRequestWrapper: cookieOverWritten="+cookieOverWritten);
        //System.err.println("DEBUG: SessionSharingRequestWrapper: isSecure()="+isSecure());
        //System.err.println("DEBUG: SessionSharingRequestWrapper: isRequestedSessionIdFromCookie()="+isRequestedSessionIdFromCookie());
        //System.err.println("DEBUG: SessionSharingRequestWrapper: session.isNew()="+session.isNew());
        if (null == cookieOverWritten && isSecure() /*&& isRequestedSessionIdFromCookie()*/ && session.isNew()) {
            // Might have created the cookie in SSL protocol and tomcat will lose the session
            // if there is change in protocol from HTTPS to HTTP. To avoid this, trick the browser
            // using the HTTP and HTTPS session cookie.
            Cookie cookie = new Cookie("JSESSIONID", session.getId());
            cookie.setMaxAge(-1); // Life of the browser or timeout
            String contextPath = getContextPath();
            if ((contextPath != null) && (contextPath.length() > 0)) {
                cookie.setPath(contextPath);
            } else {
                cookie.setPath("/");
            }
            //System.err.println("DEBUG: SessionSharingRequestWrapper: Setting cookie:");
            //System.err.println("    cookie.getComment()="+cookie.getComment());
            //System.err.println("    cookie.getDomain()="+cookie.getDomain());
            //System.err.println("    cookie.getMaxAge()="+cookie.getMaxAge());
            //System.err.println("    cookie.getName()="+cookie.getName());
            //System.err.println("    cookie.getPath()="+cookie.getPath());
            //System.err.println("    cookie.getSecure()="+cookie.getSecure());
            //System.err.println("    cookie.getValue()="+cookie.getValue());
            //System.err.println("    cookie.getVersion()="+cookie.getVersion());

            response.addCookie(cookie); // Adding an "Set-Cookie" header to the response
            setAttribute("COOKIE_OVERWRITTEN_FLAG", "true");// To avoid multiple "Set-Cookie" header
        }
    }
    
    /**
     * @deprecated  Added this comment to avoid deprecated warnings during compilation.
     *
     * @see  HttpServletRequestWrapper#getRealPath(String)
     */
    public String getRealPath(String path) {
        return super.getRealPath(path);
    }

    /**
     * @deprecated  Added this comment to avoid deprecated warnings during compilation.
     *
     * @see  HttpServletRequestWrapper#isRequestedSessionIdFromUrl()
     */
    public boolean isRequestedSessionIdFromUrl() {
        return super.isRequestedSessionIdFromUrl();
    }
}
