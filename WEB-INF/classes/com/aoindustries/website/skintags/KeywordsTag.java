package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import javax.servlet.jsp.tagext.BodyContent;

/**
 * Sets the keywords for the page.
 *
 * @author  AO Industries, Inc.
 */
public class KeywordsTag extends PageAttributesBodyTag {

    public KeywordsTag() {
    }

    public int doStartTag(PageAttributes pageAttributes) {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag(PageAttributes pageAttributes) {
        pageAttributes.setKeywords(getBodyContent().getString().trim());
        return EVAL_PAGE;
    }
}
