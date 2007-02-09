package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import javax.servlet.jsp.tagext.BodyContent;

/**
 * Sets the title for a page.
 *
 * @author  AO Industries, Inc.
 */
public class TitleTag extends PageAttributesTag {

    public TitleTag() {
    }

    public int doStartTag(PageAttributes pageAttributes) {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag(PageAttributes pageAttributes) {
        String title = getBodyContent().getString().trim();
        AddParentTag addParentTag = (AddParentTag)findAncestorWithClass(this, AddParentTag.class);
        if(addParentTag!=null) {
            addParentTag.setTitle(title);
        } else {
            AddSiblingTag addSiblingTag = (AddSiblingTag)findAncestorWithClass(this, AddSiblingTag.class);
            if(addSiblingTag!=null) {
                addSiblingTag.setTitle(title);
            } else {
                pageAttributes.setTitle(title);
            }
        }
        return SKIP_BODY;
    }
}
