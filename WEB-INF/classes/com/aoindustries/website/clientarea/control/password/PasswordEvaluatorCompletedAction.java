package com.aoindustries.website.clientarea.control.password;

import com.aoindustries.aoserv.client.PasswordChecker;
import com.aoindustries.website.Skin;
import com.aoindustries.website.SkinAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Evaluates the strength of passwords, stores the results as a <code>PasswordChecker.Result[]</code> in request attribute "results".  The results are keys into the
 * resource bundle "/aoserv/client/ApplicationResources".
 *
 * @author  AO Industries, Inc.
 */
public class PasswordEvaluatorCompletedAction extends SkinAction {

    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin
    ) throws Exception {
        PasswordEvaluatorForm passwordEvaluatorForm = (PasswordEvaluatorForm)form;

        // Evaluate the password
        String password = passwordEvaluatorForm.getPassword();
        PasswordChecker.Result[] results = PasswordChecker.checkPassword(null, password, true, false);
            
        // Set request values
        request.setAttribute("results", results);

        return mapping.findForward("success");
    }
}
