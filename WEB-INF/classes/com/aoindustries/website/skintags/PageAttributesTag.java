package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import com.aoindustries.website.*;

/**
 * Resolves a shared instance of <code>PageAttributes</code> for all subclasses.
 *
 * @author  AO Industries, Inc.
 */
abstract public class PageAttributesTag extends BodyTagSupport {

    public PageAttributesTag() {
    }

    static PageAttributes getPageAttributes(PageContext pageContext) {
        PageAttributes pageAttributes = (PageAttributes)pageContext.getAttribute(PageAttributes.ATTRIBUTE_KEY, PageAttributes.ATTRIBUTE_SCOPE);
        if(pageAttributes==null) {
            pageAttributes = new PageAttributes();
            pageContext.setAttribute(PageAttributes.ATTRIBUTE_KEY, pageAttributes, PageAttributes.ATTRIBUTE_SCOPE);
        }
        return pageAttributes;
    }

    final public int doStartTag() throws JspException {
        return doStartTag(getPageAttributes(pageContext));
    }

    public int doStartTag(PageAttributes pageAttributes) throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    /*
    final public int doAfterBody() throws JspException {
        return doAfterBody(getPageAttributes());
    }

    public int doAfterBody(PageAttributes pageAttributes) throws JspException {
        return SKIP_BODY;
    }*/

    final public int doEndTag() throws JspException {
        return doEndTag(getPageAttributes(pageContext));
    }

    public int doEndTag(PageAttributes pageAttributes) throws JspException {
        return EVAL_PAGE;
    }
}
