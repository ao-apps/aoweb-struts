package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2008 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.Skin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Causes all nested popups to only display one at a time.
 *
 * @see  PopupTag
 *
 * @author  AO Industries, Inc.
 */
public class PopupGroupTag extends BodyTagSupport {

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

    public PopupGroupTag() {
    }

    public int doStartTag() throws JspException {
        id = getNextId();
        Skin skin = SkinTag.getSkin(pageContext);
        skin.beginPopupGroup((HttpServletRequest)pageContext.getRequest(), pageContext.getOut(), id);
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        Skin skin = SkinTag.getSkin(pageContext);
        skin.endPopupGroup((HttpServletRequest)pageContext.getRequest(), pageContext.getOut(), id);
        return EVAL_PAGE;
    }
}
