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
 * Sets the copyright for the page.
 *
 * @author  AO Industries, Inc.
 */
public class CopyrightTag extends AutoEncodingBufferedTag {

    @Override
    public MediaType getContentType() {
        return MediaType.XHTML;
    }

    @Override
    public MediaType getOutputType() {
        return null;
    }

    @Override
    protected void doTag(AutoTempFileWriter capturedBody, Writer out) {
        String copyright = capturedBody.toString().trim();
        JspTag parent = findAncestorWithClass(this, CopyrightAttribute.class);
        if(parent==null) {
            PageAttributesBodyTag.getPageAttributes((PageContext)getJspContext()).setCopyright(copyright);
        } else {
            CopyrightAttribute copyrightAttribute = (CopyrightAttribute)parent;
            copyrightAttribute.setCopyright(copyright);
        }
    }
}
