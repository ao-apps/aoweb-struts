package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import java.util.Locale;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

/**
 * Sets the navImageAlt for a page.
 *
 * @author  AO Industries, Inc.
 */
public class NavImageAltTag extends PageAttributesTag {

    public NavImageAltTag() {
    }

    public int doStartTag(PageAttributes pageAttributes) {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag(PageAttributes pageAttributes) throws JspException {
        String navImageAlt = getBodyContent().getString().trim();
        AddParentTag addParentTag = (AddParentTag)findAncestorWithClass(this, AddParentTag.class);
        if(addParentTag!=null) {
            addParentTag.setNavImageAlt(navImageAlt);
        } else {
            AddSiblingTag addSiblingTag = (AddSiblingTag)findAncestorWithClass(this, AddSiblingTag.class);
            if(addSiblingTag!=null) {
                addSiblingTag.setNavImageAlt(navImageAlt);
            } else {
                pageAttributes.setNavImageAlt(navImageAlt);
            }
        }
        return SKIP_BODY;
    }
}
