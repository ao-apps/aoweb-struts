package com.aoindustries.website;

/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServClientConfiguration;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.validator.UserId;
import com.aoindustries.aoserv.client.validator.ValidationException;
import com.aoindustries.security.AccountDisabledException;
import com.aoindustries.security.AccountNotFoundException;
import com.aoindustries.security.BadPasswordException;
import com.aoindustries.security.LoginException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
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
        Skin skin
    ) throws JspException, RemoteException, IOException {
        LoginForm loginForm = (LoginForm)form;

        ActionMessages errors = loginForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input");
        }

        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        ServletContext servletContext = getServlet().getServletContext();
        String message;
        Throwable throwable;
        try {
            // Get connector
            AOServConnector<?,?> aoConn = AOServClientConfiguration.getConnector(UserId.valueOf(username), password, false);

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
        } catch(ValidationException err) {
            message = err.getLocalizedMessage();
            throwable = err;
        } catch(AccountNotFoundException err) {
            MessageResources applicationResources = (MessageResources)request.getAttribute("/ApplicationResources");
            message = applicationResources.getMessage("login.accountNotFound");
            throwable = err;
        } catch(BadPasswordException err) {
            MessageResources applicationResources = (MessageResources)request.getAttribute("/ApplicationResources");
            message = applicationResources.getMessage("login.badPassword");
            throwable = err;
        } catch(AccountDisabledException err) {
            MessageResources applicationResources = (MessageResources)request.getAttribute("/ApplicationResources");
            message = applicationResources.getMessage("login.accountDisabled");
            throwable = err;
        } catch(LoginException err) {
            MessageResources applicationResources = (MessageResources)request.getAttribute("/ApplicationResources");
            message = applicationResources.getMessage("login.failed");
            throwable = err;
        }
        if(message!=null) request.setAttribute(Constants.AUTHENTICATION_MESSAGE, message);
        else LogFactory.getLogger(servletContext, LoginCompletedAction.class).log(Level.SEVERE, null, throwable);
        return mapping.findForward("failure");
    }
}
