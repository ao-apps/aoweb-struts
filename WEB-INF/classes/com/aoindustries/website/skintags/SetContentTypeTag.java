package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.Constants;
import com.aoindustries.website.Skin;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

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

    public int doStartTag() throws JspException {
        Skin skin = (Skin)pageContext.getAttribute(Constants.SKIN, PageContext.REQUEST_SCOPE);
        if(skin==null) {
            HttpSession session = pageContext.getSession();
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
            throw new JspException(applicationResources.getMessage(locale, "skintags.unableToFindSkinInRequest"));
        }

        Locale locale = (Locale)pageContext.getSession().getAttribute(Globals.LOCALE_KEY);

        pageContext.getResponse().setContentType("text/html; charset="+skin.getCharacterSet(locale));
        return SKIP_BODY;
    }
}
