package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.PasswordChecker;
import com.aoindustries.website.HttpsAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * Evaluates the strength of passwords, stores the results as a <code>PasswordChecker.Result[]</code> in request attribute "results".
 *
 * @author  AO Industries, Inc.
 */
public class PasswordEvaluatorCompletedAction extends HttpsAction {

    @Override
    public ActionForward executeProtocolAccepted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin
    ) throws Exception {
        PasswordEvaluatorForm passwordEvaluatorForm = (PasswordEvaluatorForm)form;

        ActionMessages errors = passwordEvaluatorForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input");
        }

        // Evaluate the password
        String password = passwordEvaluatorForm.getPassword();
        PasswordChecker.Result[] results = PasswordChecker.checkPassword(null, password, PasswordChecker.PasswordStrength.STRICT);
            
        // Set request values
        request.setAttribute("results", results);

        return mapping.findForward("success");
    }
}
