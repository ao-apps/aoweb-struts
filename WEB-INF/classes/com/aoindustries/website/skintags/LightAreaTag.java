package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.io.ChainWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import com.aoindustries.website.*;

/**
 * Writes the skin light area.
 *
 * @author  AO Industries, Inc.
 */
public class LightAreaTag extends PageAttributesTag {

    public LightAreaTag() {
    }

    public int doStartTag(PageAttributes pageAttributes) throws JspException {
        ChainWriter out = new ChainWriter(pageContext.getOut());
        Skin skin = (Skin)pageContext.getAttribute(Constants.SKIN, PageContext.REQUEST_SCOPE);
        if(skin==null) throw new JspException("Unable to find skin in the request attributes");

        skin.beginLightArea(out);

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag(PageAttributes pageAttributes) throws JspException {
        ChainWriter out = new ChainWriter(pageContext.getOut());
        Skin skin = (Skin)pageContext.getAttribute(Constants.SKIN, PageContext.REQUEST_SCOPE);
        if(skin==null) throw new JspException("Unable to find skin in the request attributes");

        skin.endLightArea(out);

        return EVAL_PAGE;
    }
}
