package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.Skin;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

/**
 * Renders a popup close link/image/button.  Must be nested inside a PopupTag.
 *
 * @see  PopupTag
 *
 * @author  AO Industries, Inc.
 */
public class PopupCloseTag extends TagSupport {

    public PopupCloseTag() {
    }

    public int doStartTag() throws JspException {
        Skin skin = SkinTag.getSkin(pageContext);

        // Look for the containing popup tag
        PopupTag popupTag = (PopupTag)findAncestorWithClass(this, PopupTag.class);
        if(popupTag==null) {
            HttpSession session = pageContext.getSession();
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
            throw new JspException(applicationResources.getMessage(locale, "skintags.PopupCloseTag.mustNestInPopupTag"));
        }
 
        // Look for containing popupGroup tag
        PopupGroupTag popupGroupTag = (PopupGroupTag)findAncestorWithClass(popupTag, PopupGroupTag.class);
        if(popupGroupTag==null) {
            HttpSession session = pageContext.getSession();
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
            throw new JspException(applicationResources.getMessage(locale, "skintags.PopupTag.mustNestInPopupGroupTag"));
        } else {
            skin.printPopupClose((HttpServletRequest)pageContext.getRequest(), pageContext.getOut(), popupGroupTag.id, popupTag.id);
        }
        return SKIP_BODY;
    }
}
