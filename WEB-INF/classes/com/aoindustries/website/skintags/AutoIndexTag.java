package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.Skin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author  AO Industries, Inc.
 */
public class AutoIndexTag extends TagSupport {

    public AutoIndexTag() {
    }

    public int doStartTag() throws JspException {
        Skin skin = SkinTag.getSkin(pageContext);
        PageAttributes pageAttributes = PageAttributesTag.getPageAttributes(pageContext);
        skin.printAutoIndex((HttpServletRequest)pageContext.getRequest(), (HttpServletResponse)pageContext.getResponse(), pageContext.getOut(), pageAttributes);
        return SKIP_BODY;
    }
}
