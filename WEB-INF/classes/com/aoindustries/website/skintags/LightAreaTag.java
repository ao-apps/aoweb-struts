package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.io.ChainWriter;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import com.aoindustries.website.*;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

/**
 * Writes the skin light area.
 *
 * @author  AO Industries, Inc.
 */
public class LightAreaTag extends PageAttributesTag {

    private String width;
    private boolean nowrap;

    public LightAreaTag() {
        init();
    }

    private void init() {
        width = null;
        nowrap = false;
    }

    public int doStartTag(PageAttributes pageAttributes) throws JspException {
        ChainWriter out = new ChainWriter(pageContext.getOut());
        Skin skin = (Skin)pageContext.getAttribute(Constants.SKIN, PageContext.REQUEST_SCOPE);
        if(skin==null) {
            HttpSession session = pageContext.getSession();
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
            throw new JspException(applicationResources.getMessage(locale, "skintags.unableToFindSkinInRequest"));
        }

        skin.beginLightArea(out, width, nowrap);

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag(PageAttributes pageAttributes) throws JspException {
        try {
            ChainWriter out = new ChainWriter(pageContext.getOut());
            Skin skin = (Skin)pageContext.getAttribute(Constants.SKIN, PageContext.REQUEST_SCOPE);
            if(skin==null) {
                HttpSession session = pageContext.getSession();
                Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
                MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
                throw new JspException(applicationResources.getMessage(locale, "skintags.unableToFindSkinInRequest"));
            }

            skin.endLightArea(out);

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
