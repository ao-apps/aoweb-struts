package com.aoindustries.website;

/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.util.i18n.ThreadLocale;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.Config;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Resolves the current locale, optionally changing it with any language parameters, sets the request param locale, and calls subclass implementation.
 *
 * @author Dan Armstrong &lt;dan@aoindustries.com&gt;
 */
public class LocaleAction extends SiteSettingsAction {

    /**
     * Selects the <code>Locale</code> then the subclass execute method is invoked.
     *
     * @see #execute(ActionMapping,ActionForm,HttpServletRequest,HttpServletResponse)
     */
    @Override
    final public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings
    ) throws Exception {
        getEffectiveLocale(siteSettings, request, response);
        return executeL(mapping, form, request, response, siteSettings);
    }

    /**
     * <p>
     * Gets the effective locale for the request.  If the requested language is not
     * one of the enabled languages for this site, will set to the default language
     * (the first in the language list).
     * </p>
     * <p>Also allows the parameter "language" to override the current settings.</p>
     * <p>This also sets the struts, JSTL, response, and thread locales to the same value.</p>
     */
    public static Locale getEffectiveLocale(SiteSettings siteSettings, HttpServletRequest request, HttpServletResponse response) throws JspException, IOException {
        HttpSession session = request.getSession();
        List<Skin.Language> languages = siteSettings.getLanguages(request);
        Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        String language = request.getParameter("language");
        if(language!=null && (language=language.trim()).length()>0) {
            // Make sure is a supported language
            for(Skin.Language possLanguage : languages) {
                String code = possLanguage.getCode();
                if(code.equals(language)) {
                    locale = locale==null ? new Locale(code) : new Locale(code, locale.getCountry(), locale.getVariant());
                    session.setAttribute(Globals.LOCALE_KEY, locale);
                    Config.set(session, Config.FMT_LOCALE, locale);
                    response.setLocale(locale);
                    ThreadLocale.set(locale);
                    return locale;
                }
            }
        }
        if(locale!=null) {
            // Make sure the language is a supported value, otherwise return the default language
            String localeLanguage = locale.getLanguage();
            for(Skin.Language possLanguage : languages) {
                if(possLanguage.getCode().equals(localeLanguage)) {
                    // Current value is from session and is OK
                    response.setLocale(locale);
                    ThreadLocale.set(locale);
                    // Make sure the JSTL value matches
                    if(!locale.equals(Config.get(session, Config.FMT_LOCALE))) Config.set(session, Config.FMT_LOCALE, locale);
                    return locale;
                }
            }
        }
        // Return the default
        locale = getDefaultLocale(languages);
        session.setAttribute(Globals.LOCALE_KEY, locale);
        Config.set(session, Config.FMT_LOCALE, locale);
        response.setLocale(locale);
        ThreadLocale.set(locale);
        return locale;
    }

    /**
     * Gets the default locale for the provided request.  The session is not set.
     */
    public static Locale getDefaultLocale(SiteSettings siteSettings, HttpServletRequest request) throws JspException, IOException {
        return getDefaultLocale(siteSettings.getLanguages(request));
    }

    /**
     * Gets the default locale for the provided languages.  The session is not set.
     */
    private static Locale getDefaultLocale(List<Skin.Language> languages) {
        return new Locale(languages.get(0).getCode());
    }

    /**
     * Once the locale is selected, this version of the execute method is invoked.
     * The default implementation of this method simply returns the mapping of "success".
     */
    public ActionForward executeL(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings
    ) throws Exception {
        return mapping.findForward("success");
    }
}
