package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
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
