package com.aoindustries.website.aowebtags;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.io.ChainWriter;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author  AO Industries, Inc.
 */
public class DateTimeTag extends BodyTagSupport {

    public DateTimeTag() {
    }

    @Override
    public int doStartTag() {
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            String millisString = getBodyContent().getString().trim();
            long millis;
            if(millisString.length()==0) millis = -1;
            else millis = Long.parseLong(millisString);
            ChainWriter.writeDateTimeJavaScript(millis, pageContext.getOut());
            return EVAL_PAGE;
        } catch(IOException err) {
            throw new JspException(err);
        }
    }
}
