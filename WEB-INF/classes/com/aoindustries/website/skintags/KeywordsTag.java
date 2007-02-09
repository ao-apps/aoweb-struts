package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import javax.servlet.jsp.tagext.BodyContent;

/**
 * Sets the keywords for the page.
 *
 * @author  AO Industries, Inc.
 */
public class KeywordsTag extends PageAttributesTag {

    public KeywordsTag() {
    }

    public int doStartTag(PageAttributes pageAttributes) {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag(PageAttributes pageAttributes) {
        pageAttributes.setKeywords(getBodyContent().getString().trim());
        return SKIP_BODY;
    }
}
