package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.Skin;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

/**
 * Renders one popup, that may optionally be nested in a PopupGroupTag.
 *
 * @see  PopupGroupTag
 *
 * @author  AO Industries, Inc.
 */
public class PopupTag extends BodyTagSupport {

    private static long nextId=1;

    /**
     * Each use of the tag will use a new unique identifier to avoid any page element name conflicts.
     */
    synchronized private static long getNextId() {
        long next = nextId++;
        if(nextId==Long.MAX_VALUE) nextId = 1;
        return next;
    }

    long id;
    String width;

    public PopupTag() {
        init();
    }

    private void init() {
        this.width = null;
    }

    public int doStartTag() throws JspException {
        id = getNextId();
        Skin skin = SkinTag.getSkin(pageContext);
        // Look for containing popupGroup
        PopupGroupTag popupGroupTag = (PopupGroupTag)findAncestorWithClass(this, PopupGroupTag.class);
        if(popupGroupTag==null) {
            HttpSession session = pageContext.getSession();
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
            throw new JspException(applicationResources.getMessage(locale, "skintags.PopupTag.mustNestInPopupGroupTag"));
        } else {
            HttpServletResponse resp = (HttpServletResponse)pageContext.getResponse();
            skin.beginPopup((HttpServletRequest)pageContext.getRequest(), resp, pageContext.getOut(), popupGroupTag.id, id, width);
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException  {
        try {
            Skin skin = SkinTag.getSkin(pageContext);
            // Look for containing popupGroup
            PopupGroupTag popupGroupTag = (PopupGroupTag)findAncestorWithClass(this, PopupGroupTag.class);
            if(popupGroupTag==null) {
                HttpSession session = pageContext.getSession();
                Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
                MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
                throw new JspException(applicationResources.getMessage(locale, "skintags.PopupTag.mustNestInPopupGroupTag"));
            } else {
                HttpServletResponse resp = (HttpServletResponse)pageContext.getResponse();
                skin.endPopup((HttpServletRequest)pageContext.getRequest(), resp, pageContext.getOut(), popupGroupTag.id, id, width);
            }
            return EVAL_PAGE;
        } finally {
            init();
        }
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
