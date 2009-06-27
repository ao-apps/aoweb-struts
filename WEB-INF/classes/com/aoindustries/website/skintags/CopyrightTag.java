package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import javax.servlet.jsp.tagext.BodyContent;

/**
 * Sets the copyright for the page.
 *
 * @author  AO Industries, Inc.
 */
public class CopyrightTag extends PageAttributesBodyTag {

    public CopyrightTag() {
    }

    public int doStartTag(PageAttributes pageAttributes) {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag(PageAttributes pageAttributes) {
        pageAttributes.setCopyright(getBodyContent().getString().trim());
        return EVAL_PAGE;
    }
}
