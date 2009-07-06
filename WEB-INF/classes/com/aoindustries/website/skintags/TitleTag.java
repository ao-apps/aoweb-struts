package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Sets the title for a page.
 *
 * @author  AO Industries, Inc.
 */
public class TitleTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;

    public TitleTag() {
    }

    @Override
    public int doStartTag() {
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() {
        String title = getBodyContent().getString().trim();
        AddParentTag addParentTag = (AddParentTag)findAncestorWithClass(this, AddParentTag.class);
        if(addParentTag!=null) {
            addParentTag.setTitle(title);
        } else {
            AddSiblingTag addSiblingTag = (AddSiblingTag)findAncestorWithClass(this, AddSiblingTag.class);
            if(addSiblingTag!=null) {
                addSiblingTag.setTitle(title);
            } else {
                PageAttributesBodyTag.getPageAttributes(pageContext).setTitle(title);
            }
        }
        return EVAL_PAGE;
    }
}
