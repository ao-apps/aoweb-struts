/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

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

    @Override
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin
    ) throws Exception {
        int acceptableProtocols = getAcceptableProtocols();
        boolean isSecure = request.isSecure();
        if(isSecure) {
            if((acceptableProtocols&HTTPS)!=0) {
                return executeProtocolAccepted(mapping, form, request, response, siteSettings, skin);
            } else {
                // Will default to true for safety with incorrect value in config file
                boolean redirectOnMismatch = siteSettings.getProtocolActionRedirectOnMismatch();
                if(redirectOnMismatch) {
                    String path = request.getRequestURI();
                    if(path.startsWith("/")) path=path.substring(1);
                    // Send permanent redirect
                    response.setHeader("Location", response.encodeRedirectURL(skin.getHttpUrlBase(request) + path));
                    request.setAttribute(com.aoindustries.website.Constants.HTTP_SERVLET_RESPONSE_STATUS, Integer.valueOf(HttpServletResponse.SC_MOVED_PERMANENTLY));
                    response.sendError(HttpServletResponse.SC_MOVED_PERMANENTLY);
                } else {
                    request.setAttribute(com.aoindustries.website.Constants.HTTP_SERVLET_RESPONSE_STATUS, Integer.valueOf(HttpServletResponse.SC_FORBIDDEN));
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, ApplicationResources.accessor.getMessage("ProtocolAction.httpsNotAllowed"));
                }
                return null;
            }
        } else {
            if((acceptableProtocols&HTTP)!=0) {
                return executeProtocolAccepted(mapping, form, request, response, siteSettings, skin);
            } else {
                // Will default to true for safety with incorrect value in config file
                boolean redirectOnMismatch = siteSettings.getProtocolActionRedirectOnMismatch();
                if(redirectOnMismatch) {
                    String path = request.getRequestURI();
                    if(path.startsWith("/")) path=path.substring(1);
                    // Send permanent redirect
                    response.setHeader("Location", response.encodeRedirectURL(skin.getHttpsUrlBase(request) + path));
                    response.sendError(HttpServletResponse.SC_MOVED_PERMANENTLY);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, ApplicationResources.accessor.getMessage("ProtocolAction.httpNotAllowed"));
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
        SiteSettings siteSettings,
        Skin skin
    ) throws Exception {
        return mapping.findForward("success");
    }
}
