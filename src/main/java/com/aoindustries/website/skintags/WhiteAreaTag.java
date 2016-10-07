package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.Skin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * Writes the skin white area.
 *
 * @author  AO Industries, Inc.
 */
public class WhiteAreaTag extends PageAttributesBodyTag {

    private String width;
    private boolean nowrap;

    public WhiteAreaTag() {
        init();
    }

    private void init() {
        width = null;
        nowrap = false;
    }

    public int doStartTag(PageAttributes pageAttributes) throws JspException {
        Skin skin = SkinTag.getSkin(pageContext);

        skin.beginWhiteArea((HttpServletRequest)pageContext.getRequest(), (HttpServletResponse)pageContext.getResponse(), pageContext.getOut(), width, nowrap);

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag(PageAttributes pageAttributes) throws JspException {
        try {
            Skin skin = SkinTag.getSkin(pageContext);

            skin.endWhiteArea((HttpServletRequest)pageContext.getRequest(), (HttpServletResponse)pageContext.getResponse(), pageContext.getOut());

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

    public boolean getNowrap() {
        return nowrap;
    }
    
    public void setNowrap(boolean nowrap) {
        this.nowrap = nowrap;
    }
}
