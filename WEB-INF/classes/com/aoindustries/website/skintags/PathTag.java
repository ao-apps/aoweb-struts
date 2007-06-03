package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import javax.servlet.jsp.tagext.BodyContent;

/**
 * Sets the path for a page.
 *
 * @author  AO Industries, Inc.
 */
public class PathTag extends PageAttributesTag {

    public PathTag() {
    }

    public int doStartTag(PageAttributes pageAttributes) {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag(PageAttributes pageAttributes) {
        String path = getBodyContent().getString().trim();
        pageAttributes.setPath(path);
        return EVAL_PAGE;
    }
}
