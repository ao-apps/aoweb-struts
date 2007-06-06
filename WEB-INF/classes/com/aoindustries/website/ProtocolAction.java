package com.aoindustries.website;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * Makes sure the request coming in is in the right protocol (either http or https) by checking
 * the <code>HttpServletRequest.isSecure()</code> method.
 *
 * @author Dan Armstrong &lt;dan@aoindustries.com&gt;
 */
abstract public class ProtocolAction extends SkinAction {

    /**
     * Flag used to indicate HTTP is acceptable.
     */
    public static final int HTTP = 1;

    /**
     * Flag used to indicate HTTPS is acceptable.
     */
    public static final int HTTPS = 2;

    final public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin
    ) throws Exception {
        int acceptableProtocols = getAcceptableProtocols();
        boolean isSecure = request.isSecure();
        if(isSecure) {
            if((acceptableProtocols&HTTPS)!=0) {
                return executeProtocolAccepted(mapping, form, request, response, locale, skin);
            } else {
                // Will default to true for safety with incorrect value in config file
                boolean redirectOnMismatch = !"false".equals(getServlet().getServletContext().getInitParameter("com.aoindustries.website.ProtocolAction.redirectOnMismatch"));
                if(redirectOnMismatch) {
                    String path = request.getRequestURI();
                    if(path.startsWith("/")) path=path.substring(1);
                    response.sendRedirect(response.encodeRedirectURL(skin.getHttpUrlBase(request) + path));
                } else {
                    MessageResources applicationResources = (MessageResources)request.getAttribute("/ApplicationResources");
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, applicationResources.getMessage(locale, "ProtocolAction.httpsNotAllowed"));
                }
                return null;
            }
        } else {
            if((acceptableProtocols&HTTP)!=0) {
                return executeProtocolAccepted(mapping, form, request, response, locale, skin);
            } else {
                // Will default to true for safety with incorrect value in config file
                boolean redirectOnMismatch = !"false".equals(getServlet().getServletContext().getInitParameter("com.aoindustries.website.ProtocolAction.redirectOnMismatch"));
                if(redirectOnMismatch) {
                    String path = request.getRequestURI();
                    if(path.startsWith("/")) path=path.substring(1);
                    response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request) + path));
                } else {
                    MessageResources applicationResources = (MessageResources)request.getAttribute("/ApplicationResources");
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, applicationResources.getMessage(locale, "ProtocolAction.httpNotAllowed"));
                }
                return null;
            }
        }
    }

    /**
     * Return the bitwise or of the acceptable protocols.
     */
    abstract public int getAcceptableProtocols();

    /**
     * Once the protocols is accepted, this version of the execute method is invoked.
     * The default implementation of this method simply returns the mapping of "success".
     */
    public ActionForward executeProtocolAccepted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin
    ) throws Exception {
        return mapping.findForward("success");
    }
}
