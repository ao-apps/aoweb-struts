package com.aoindustries.website.aowebtags;

/*
 * Copyright 2009-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.servlet.http.ServletUtil;
import java.net.MalformedURLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Evaluates the body if the provided page does not exist.
 *
 * @author  AO Industries, Inc.
 */
public class NotExistsTag extends BodyTagSupport {

    private String path;

    public NotExistsTag() {
        init();
    }

    private void init() {
        path = null;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            return ServletUtil.resourceExists(pageContext.getServletContext(), path) ? SKIP_BODY : EVAL_BODY_INCLUDE;
        } catch(MalformedURLException err) {
            throw new JspException(err);
        }
    }

    @Override
    public int doEndTag() {
        init();
        return EVAL_PAGE;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
