package com.aoindustries.website;

/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class SessionTimeoutAction extends HttpsAction {

    @Override
    public ActionForward executeProtocolAccepted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin
    ) throws Exception {
        // Logout, just in case session not actually expired
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.AO_CONN);
        session.removeAttribute(Constants.AUTHENTICATED_AO_CONN);
        session.removeAttribute(Constants.AUTHENTICATION_TARGET);
        session.removeAttribute(Constants.SU_REQUESTED);

        // Save the target so authentication will return to the previous page
        String target = request.getParameter("target");
        if(target!=null && target.length()>0 && !target.endsWith("/login.do")) {
            request.getSession().setAttribute(Constants.AUTHENTICATION_TARGET, target);
        }

        // Set the authenticationMessage
        request.setAttribute(Constants.AUTHENTICATION_MESSAGE, ApplicationResources.accessor.getMessage("SessionTimeoutAction.authenticationMessage"));

        return mapping.findForward("success");
    }
}
