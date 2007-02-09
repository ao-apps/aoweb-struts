package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import javax.servlet.jsp.tagext.BodyContent;

/**
 * Sets the author for the page.
 *
 * @author  AO Industries, Inc.
 */
public class AuthorTag extends PageAttributesTag {

    public AuthorTag() {
    }

    public int doStartTag(PageAttributes pageAttributes) {
        return EVAL_BODY_BUFFERED;
    }

    public int doAfterBody(PageAttributes pageAttributes) {
        pageAttributes.setAuthor(getBodyContent().getString().trim());
        return SKIP_BODY;
    }
}
