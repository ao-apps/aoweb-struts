package com.aoindustries.website;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.util.ErrorPrinter;
import java.util.Locale;
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

    public ActionForward execute(Exception exception, ExceptionConfig config, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	ErrorPrinter.printStackTraces(exception);

        // Resolve the Locale, to be compatible with LocaleAction
        Locale locale = LocaleAction.getEffectiveLocale(request, response);
        request.setAttribute(Constants.LOCALE, locale);

        // Select Skin, to be compatible with SkinAction
        Skin skin = SkinAction.getSkin(request.getSession().getServletContext(), request, response);
        request.setAttribute(Constants.SKIN, skin);

        return mapping.findForward("exception");
    }
}
