package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.encoding.MediaType;
import com.aoindustries.io.StringBuilderWriter;
import com.aoindustries.taglib.AutoEncodingBufferedTag;
import java.io.Writer;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.JspTag;

/**
 * Sets the copyright for the page.
 *
 * @author  AO Industries, Inc.
 */
public class CopyrightTag extends AutoEncodingBufferedTag {

    public MediaType getContentType() {
        return MediaType.XHTML;
    }

    public MediaType getOutputType() {
        return null;
    }

    protected void doTag(StringBuilderWriter capturedBody, Writer out) {
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
