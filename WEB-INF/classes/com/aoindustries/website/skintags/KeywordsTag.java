package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.encoding.MediaType;
import com.aoindustries.io.AutoTempFileWriter;
import com.aoindustries.taglib.AutoEncodingBufferedTag;
import java.io.Writer;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspTag;

/**
 * @author  AO Industries, Inc.
 */
public class KeywordsTag extends AutoEncodingBufferedTag {

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
        String keywords = capturedBody.toString().trim();
        JspTag parent = findAncestorWithClass(this, KeywordsAttribute.class);
        if(parent==null) {
            PageAttributesBodyTag.getPageAttributes((PageContext)getJspContext()).setKeywords(keywords);
        } else {
            KeywordsAttribute keywordsAttribute = (KeywordsAttribute)parent;
            keywordsAttribute.setKeywords(keywords);
        }
    }
}
