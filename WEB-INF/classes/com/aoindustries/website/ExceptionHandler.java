package com.aoindustries.website;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.util.ErrorPrinter;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

/**
 * @author  AO Industries, Inc.
 */
public class ExceptionHandler extends org.apache.struts.action.ExceptionHandler {

    @Override
    public ActionForward execute(Exception exception, ExceptionConfig config, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        ErrorPrinter.printStackTraces(exception);

        // Resolve the SiteSettings, to be compatible with SiteSettingsAction
        ServletContext servletContext = request.getSession().getServletContext();
        SiteSettings siteSettings;
        try {
            siteSettings = SiteSettings.getInstance(servletContext);
        } catch(Exception err) {
            ErrorPrinter.printStackTraces(err);
            // Use default settings
            siteSettings = new SiteSettings(servletContext);
        }
        request.setAttribute(Constants.SITE_SETTINGS, siteSettings);

        // Resolve the Locale, to be compatible with LocaleAction
        Locale locale;
        try {
            locale = LocaleAction.getEffectiveLocale(siteSettings, request, response);
        } catch(Exception err) {
            ErrorPrinter.printStackTraces(err);
            // Use default locale
            locale = Locale.getDefault();
        }
        request.setAttribute(Constants.LOCALE, locale);

        // Select Skin, to be compatible with SkinAction
        Skin skin;
        try {
            skin = SkinAction.getSkin(siteSettings, request, response);
        } catch(Exception err) {
            ErrorPrinter.printStackTraces(err);
            // Use text skin
            skin = TextSkin.getInstance();
        }
        request.setAttribute(Constants.SKIN, skin);

        request.setAttribute("exception", exception);

        return mapping.findForward("exception");
    }
}
