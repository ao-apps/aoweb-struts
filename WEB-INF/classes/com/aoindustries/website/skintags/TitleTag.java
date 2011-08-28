/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.skintags;

import com.aoindustries.encoding.MediaType;
import com.aoindustries.io.AutoTempFileWriter;
import com.aoindustries.taglib.AutoEncodingBufferedTag;
import java.io.Writer;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspTag;

/**
 * @author  AO Industries, Inc.
 */
public class TitleTag extends AutoEncodingBufferedTag {

    @Override
    public MediaType getContentType() {
        return MediaType.TEXT;
    }

    @Override
    public MediaType getOutputType() {
        return null;
    }

    @Override
    protected void doTag(AutoTempFileWriter capturedBody, Writer out) {
        String title = capturedBody.toString().trim();
        JspTag parent = findAncestorWithClass(this, TitleAttribute.class);
        if(parent==null) {
            PageAttributesBodyTag.getPageAttributes((PageContext)getJspContext()).setTitle(title);
        } else {
            TitleAttribute titleAttribute = (TitleAttribute)parent;
            titleAttribute.setTitle(title);
        }
    }
}
