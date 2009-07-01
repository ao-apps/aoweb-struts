package com.aoindustries.website.aowebtags;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.io.ChainWriter;
import com.aoindustries.util.Sequence;
import com.aoindustries.util.UnsynchronizedSequence;
import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author  AO Industries, Inc.
 */
public class DateTag extends BodyTagSupport {

    /**
     * The request attribute name used to store the sequence.
     */
    private static final String SEQUENCE_REQUEST_ATTRIBUTE_NAME = DateTag.class.getName()+".sequence";

    public DateTag() {
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
            ServletRequest request = pageContext.getRequest();
            Sequence sequence = (Sequence)request.getAttribute(SEQUENCE_REQUEST_ATTRIBUTE_NAME);
            if(sequence==null) request.setAttribute(SEQUENCE_REQUEST_ATTRIBUTE_NAME, sequence = new UnsynchronizedSequence());
            ChainWriter.writeDateJavaScript(millis, sequence, pageContext.getOut());
            return EVAL_PAGE;
        } catch(IOException err) {
            throw new JspException(err);
        }
    }
}
