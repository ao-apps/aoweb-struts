package com.aoindustries.website.aowebtags;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.servlet.http.ServletUtil;
import java.net.MalformedURLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Evaluates the body if the provided page does not exist.
 *
 * @author  AO Industries, Inc.
 */
public class NotExistsTag extends BodyTagSupport {

    private String page;

    public NotExistsTag() {
        init();
    }

    private void init() {
        page = null;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            return ServletUtil.resourceExists(pageContext.getServletContext(), (HttpServletRequest)pageContext.getRequest(), page) ? SKIP_BODY : EVAL_BODY_INCLUDE;
        } catch(MalformedURLException err) {
            throw new JspException(err);
        }
    }

    @Override
    public int doEndTag() {
        init();
        return EVAL_PAGE;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
