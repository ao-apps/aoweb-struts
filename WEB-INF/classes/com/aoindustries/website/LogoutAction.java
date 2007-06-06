package com.aoindustries.website;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
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
public class LogoutAction extends SkinAction {

    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin
    ) throws Exception {
        // Handle logout
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.AO_CONN);
        session.removeAttribute(Constants.AUTHENTICATED_AO_CONN);
        session.removeAttribute(Constants.AUTHENTICATION_TARGET);
        session.removeAttribute(Constants.SU_REQUESTED);
        //AuthenticatedAction.makeTomcatNonSecureCookie(request, response);

        // Try redirect
        String target = request.getParameter("target");
        if(target!=null && target.length()>0) {
            response.sendRedirect(response.encodeRedirectURL(target));
            return null;
        }

        return mapping.findForward("success");
    }
}
