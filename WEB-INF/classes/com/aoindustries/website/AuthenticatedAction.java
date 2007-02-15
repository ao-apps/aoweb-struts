package com.aoindustries.website;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.util.ErrorPrinter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Ensures the user is logged in.  Forwards to "login" if not logged in.  Otherwise, it sets the
 * request attribute "aoConn" and then calls
 * <code>execute(ActionMapping,ActionForm,HttpServletRequest,HttpServletResponse,Locale,Skin,AOServConnector)</code>.
 * The default implementation of this new <code>execute</code> method simply returns the mapping
 * of "success".<br>
 * <br>
 * More simply put, without overriding the new execute method, this action returns either the mapping
 * for "login" or "success".
 *
 * @author  AO Industries, Inc.
 */
abstract public class AuthenticatedAction extends SkinAction {

    final public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin
    ) throws Exception {
        // Handle login
        AOServConnector aoConn = getAoConn(request);
        if(aoConn==null) {
            String target = request.getRequestURL().toString();
            if(!target.endsWith("/login.do")) {
                request.getSession().setAttribute(Constants.AUTHENTICATION_TARGET, target);
            } else {
                request.getSession().removeAttribute(Constants.AUTHENTICATION_TARGET);
            }
            return mapping.findForward("login");
        }
        
        // Set request values
        request.setAttribute("aoConn", aoConn);

        return execute(mapping, form, request, response, locale, skin, aoConn);
    }

    /**
     * Gets the AOServConnector for the user or <code>null</code> if not logged in.  This also handles the "su" behavior.
     */
    public static AOServConnector getAoConn(HttpServletRequest request) {
        HttpSession session = request.getSession();
        AOServConnector authenticatedAoConn = (AOServConnector)session.getAttribute(Constants.AUTHENTICATED_AO_CONN);
        // Not logged in
        if(authenticatedAoConn==null) return null;
        
        // Is a "su" requested?
        String su=request.getParameter("su");
        if(su!=null && (su=su.trim()).length()>0) {
            try {
                AOServConnector aoConn = authenticatedAoConn.switchUsers(su);
                session.setAttribute(Constants.AO_CONN, aoConn);
                return aoConn;
            } catch(IOException err) {
                ErrorPrinter.printStackTraces(err);
            }
        }

        // Look for previous effective user
        AOServConnector aoConn = (AOServConnector)session.getAttribute(Constants.AO_CONN);
        if(aoConn!=null) return aoConn;

        // Default effective user to authenticated user
        session.setAttribute(Constants.AO_CONN, authenticatedAoConn);
        return authenticatedAoConn;
    }

    /**
     * Once authentication has been handled, this version of the execute method is invoked.
     * The default implementation of this method simply returns the mapping of "success".
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        return mapping.findForward("success");
    }
}
