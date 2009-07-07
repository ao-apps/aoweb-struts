package com.aoindustries.website;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.util.StandardErrorHandler;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 * @author  AO Industries, Inc.
 */
public class LoginCompletedAction extends HttpsAction {

    @Override
    public ActionForward executeProtocolAccepted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Locale locale,
        Skin skin
    ) throws Exception {
        LoginForm loginForm = (LoginForm)form;

        ActionMessages errors = loginForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input");
        }

        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        try {
            // Get connector
            AOServConnector aoConn = AOServConnector.getConnector(username, password, new StandardErrorHandler());
            aoConn.ping();

            // Store in session
            HttpSession session = request.getSession();
            session.setAttribute(Constants.AUTHENTICATED_AO_CONN, aoConn);
            session.setAttribute(Constants.AO_CONN, aoConn);

            // Try redirect
            String target = (String)session.getAttribute(Constants.AUTHENTICATION_TARGET);   // Get from session
            if(target==null) target = request.getParameter(Constants.AUTHENTICATION_TARGET); // With no cookies will be encoded in URL
            if(target!=null && target.length()>0) {
                response.sendRedirect(response.encodeRedirectURL(target));
                return null;
            }

            // Redirect to the clientarea instead of returning mapping because of switch from HTTPS to HTTP
            response.sendRedirect(response.encodeRedirectURL(skin.getHttpUrlBase(request)+"clientarea/index.do"));
            return null;
            // Return success
            //return mapping.findForward("success");
        } catch(IOException err) {
            String message=err.getMessage();
            if(message!=null) {
                MessageResources applicationResources = (MessageResources)request.getAttribute("/ApplicationResources");
        		if(message.indexOf("Unable to find BusinessAdministrator")!=-1) message=applicationResources.getMessage(locale, "login.accountNotFound");
                else if(message.indexOf("Connection attempted with invalid password")!=-1) message=applicationResources.getMessage(locale, "login.badPassword");
                else if(message.indexOf("BusinessAdministrator disabled")!=-1) message=applicationResources.getMessage(locale, "accountDisabled");
                else message=null;
    	    }
            if(message!=null) request.setAttribute(Constants.AUTHENTICATION_MESSAGE, message);
            else LogFactory.getLogger(getServlet().getServletContext(), LoginCompletedAction.class).log(Level.SEVERE, null, err);
            return mapping.findForward("failure");
        }
    }
}
