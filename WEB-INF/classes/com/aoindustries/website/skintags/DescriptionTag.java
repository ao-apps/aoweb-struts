package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2008 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Sets the description for the page.
 *
 * @author  AO Industries, Inc.
 */
public class DescriptionTag extends BodyTagSupport {

    public DescriptionTag() {
    }

    public int doStartTag() {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() {
        String description = getBodyContent().getString().trim();
        AddParentTag addParentTag = (AddParentTag)findAncestorWithClass(this, AddParentTag.class);
        if(addParentTag!=null) {
            addParentTag.setDescription(description);
        } else {
            AddSiblingTag addSiblingTag = (AddSiblingTag)findAncestorWithClass(this, AddSiblingTag.class);
            if(addSiblingTag!=null) {
                addSiblingTag.setDescription(description);
            } else {
                PageAttributesTag.getPageAttributes(pageContext).setDescription(description);
            }
        }
        return EVAL_PAGE;
    }
}
