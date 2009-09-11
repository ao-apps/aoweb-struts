package com.aoindustries.website;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.util.ModifiableResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class SetResourceBundleValueAction extends HttpsAction {

    @Override
    public ActionForward executeProtocolAccepted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Locale userLocale,
        Skin skin
    ) throws Exception {
        // If disabled, return 404 status
        if(!siteSettings.getCanEditResources()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        String baseName = request.getParameter("baseName");
        Locale locale = new Locale(request.getParameter("locale")); // TODO: Parse country and variant, too.
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        boolean modified = "true".equals(request.getParameter("modified"));

        // Find the bundle
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
        if(!resourceBundle.getLocale().equals(locale)) throw new AssertionError("resourceBundle.locale!=locale");
        if(!(resourceBundle instanceof ModifiableResourceBundle)) throw new AssertionError("resourceBundle is not a ModifiableResourceBundle");
        ((ModifiableResourceBundle)resourceBundle).setString(key, value, modified);

        // Set request parameters
        request.setAttribute("baseName", baseName);
        request.setAttribute("locale", locale);
        request.setAttribute("key", key);
        request.setAttribute("value", value);
        request.setAttribute("modified", modified);

        // Return success
        return mapping.findForward("success");
    }
}
