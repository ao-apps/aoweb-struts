/*
 * Copyright 2007-2011 by AO Industries, Inc.,
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
 * @author  AO Industries, Inc.
 */
public class LoginAction extends HttpsAction {

    @Override
    public ActionForward executeProtocolAccepted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin
    ) throws Exception {
        String target = request.getParameter("target");
        if(target!=null && target.length()>0 && !target.endsWith("/login.do")) {
            request.getSession().setAttribute(Constants.AUTHENTICATION_TARGET, target);
        }

        // Return success
        return mapping.findForward("success");
    }
}
