package com.aoindustries.website;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Resolves the current locale, optionally changing it with any language parameters, sets the request param locale, and calls subclass implementation.
 *
 * @author Dan Armstrong &lt;dan@aoindustries.com&gt;
 */
public class LocaleAction extends Action {

    /**
     * Selects the <code>Locale</code>, sets the request attribute "locale", then the subclass execute method is invoked.
     *
     * @see #execute(ActionMapping,ActionForm,HttpServletRequest,HttpServletResponse,Locale)
     */
    final public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        // Resolve the locale
        Locale locale = getEffectiveLocale(request, response);
        request.setAttribute(Constants.LOCALE, locale);

        return execute(mapping, form, request, response, locale);
    }

    /**
     * Gets the effective locale for the request.
     */
    public static Locale getEffectiveLocale(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        String language=request.getParameter("language");
        if(language!=null && (language=language.trim()).length()>0) {
            if(Locale.ENGLISH.getLanguage().equals(language)) {
                locale = new Locale(Locale.ENGLISH.getLanguage(), locale.getCountry(), locale.getVariant());
                session.setAttribute(Globals.LOCALE_KEY, locale);
                //AuthenticatedAction.makeTomcatNonSecureCookie(request, response);
            } else if(Locale.JAPANESE.getLanguage().equals(language)) {
                locale = new Locale(Locale.JAPANESE.getLanguage(), locale.getCountry(), locale.getVariant());
                session.setAttribute(Globals.LOCALE_KEY, locale);
                //AuthenticatedAction.makeTomcatNonSecureCookie(request, response);
            }
        }
        return locale;
    }

    /**
     * Once the locale is selected, this version of the execute method is invoked.
     * The default implementation of this method simply returns the mapping of "success".
     */
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale
    ) throws Exception {
        return mapping.findForward("success");
    }
}
