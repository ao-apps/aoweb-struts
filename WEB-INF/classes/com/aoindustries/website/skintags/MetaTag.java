package com.aoindustries.website.skintags;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Adds a META tag for a page.
 *
 * @author  AO Industries, Inc.
 */
public class MetaTag extends BodyTagSupport {

    private String name;

    public MetaTag() {
        init();
    }

    private void init() {
        name = null;
    }

    @Override
    public int doStartTag() {
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() {
        try {
            String content = getBodyContent().getString().trim();
            PageAttributesBodyTag.getPageAttributes(pageContext).addMeta(name, content);
            return EVAL_PAGE;
        } finally {
            init();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
