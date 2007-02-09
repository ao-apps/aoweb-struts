package com.aoindustries.website;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
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
public class LoginAction extends SkinAction {

    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin
    ) throws Exception {
        String target = request.getParameter("target");
        if(target!=null && target.length()>0) {
            request.getSession().setAttribute(Constants.AUTHENTICATION_TARGET, target);
        }

        // Return success
        return mapping.findForward("success");
    }
}
