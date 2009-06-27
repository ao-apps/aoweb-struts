package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import javax.servlet.jsp.JspException;

/**
 * @author  AO Industries, Inc.
 */
public class LinkTag extends PageAttributesTag {

    private String rel;
    private String href;
    private String type;

    public LinkTag() {
        init();
    }

    private void init() {
        rel = null;
        href = null;
        type = null;
    }

    @Override
    public int doStartTag(PageAttributes pageAttributes) throws JspException {
        try {
            pageAttributes.addLink(rel, href, type);
            return SKIP_BODY;
        } finally {
            init();
        }
    }

    /**
     * @return the rel
     */
    public String getRel() {
        return rel;
    }

    /**
     * @param rel the rel to set
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

    /**
     * @return the href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href the href to set
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
