package com.aoindustries.website;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.security.AccountDisabledException;
import com.aoindustries.security.AccountNotFoundException;
import com.aoindustries.security.BadPasswordException;
import com.aoindustries.util.StandardErrorHandler;
import com.aoindustries.util.WrappedException;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class LoginAction extends HttpsAction {

    public ActionForward executeProtocolAccepted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin
    ) throws Exception {
        String target = request.getParameter("target");
        if(target!=null && target.length()>0 && !target.endsWith("/login.do")) {
            request.getSession().setAttribute(Constants.AUTHENTICATION_TARGET, target);
            //AuthenticatedAction.makeTomcatNonSecureCookie(request, response);
        }

        // Return success
        return mapping.findForward("success");
    }
}
