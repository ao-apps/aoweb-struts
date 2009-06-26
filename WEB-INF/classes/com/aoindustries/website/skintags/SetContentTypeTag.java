package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.Skin;
import java.util.Locale;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.Globals;

/**
 * Sets the content type for the page.
 *
 * @see  Skin#getCharacterSet(Locale)
 *
 * @author  AO Industries, Inc.
 */
public class SetContentTypeTag extends TagSupport {

    public SetContentTypeTag() {
    }

    @Override
    public int doStartTag() throws JspException {
        // Skin skin = SkinTag.getSkin(pageContext);

        ServletResponse response = pageContext.getResponse();

        // Set the content type
        response.setContentType("text/html");

        // Set the locale
        Locale locale = (Locale)pageContext.getSession().getAttribute(Globals.LOCALE_KEY);
        response.setLocale(locale);
        
        // Set the encoding
        String charset = Skin.getCharacterSet(locale);
        response.setCharacterEncoding(charset);

        return SKIP_BODY;
    }
}
