/*
 * Copyright 2007-2013 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.skintags;

import com.aoindustries.encoding.MediaType;
import com.aoindustries.io.buffer.BufferResult;
import com.aoindustries.taglib.AutoEncodingBufferedTag;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspTag;

/**
 * @author  AO Industries, Inc.
 */
public class NavImageAltTag extends AutoEncodingBufferedTag {

    public MediaType getContentType() {
        return MediaType.TEXT;
    }

    public MediaType getOutputType() {
        return null;
    }

    protected void doTag(BufferResult capturedBody, Writer out) throws IOException {
        String navImageAlt = capturedBody.trim().toString();
        JspTag parent = findAncestorWithClass(this, NavImageAltAttribute.class);
        if(parent==null) {
            PageAttributesBodyTag.getPageAttributes((PageContext)getJspContext()).setNavImageAlt(navImageAlt);
        } else {
            NavImageAltAttribute navImageAltAttribute = (NavImageAltAttribute)parent;
            navImageAltAttribute.setNavImageAlt(navImageAlt);
        }
    }
}
