package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.servlet.jsp.JspException;

/**
 * Adds a parent to the hierarchy above this page.
 *
 * @author  AO Industries, Inc.
 */
public class ParentTag extends PageTag {

    private static final long serialVersionUID = 1L;

    private List<Child> children;

    @Override
    protected void init() {
        super.init();
        children = null;
    }

    /**
     * Gets the children of this parent page.
     */
    public List<Child> getChildren() {
        if(children==null) {
            List<Child> emptyList = Collections.emptyList();
            return emptyList;
        }
        return children;
    }

    public void addChild(Child child) {
        if(children==null) children = new ArrayList<Child>();
        children.add(child);
    }

    protected int doEndTag(
        String title,
        String navImageAlt,
        String description,
        String author,
        String copyright,
        boolean useEncryption,
        String path,
        String keywords,
        Collection<Meta> metas
    ) throws JspException {
        PageAttributesBodyTag.getPageAttributes(pageContext).addParent(
            new Parent(title, navImageAlt, description, author, copyright, useEncryption, path, keywords, metas, children)
        );
        return EVAL_PAGE;
    }
}
