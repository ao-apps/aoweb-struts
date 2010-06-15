/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.skintags;

import com.aoindustries.encoding.MediaType;
import com.aoindustries.io.StringBuilderWriter;
import com.aoindustries.taglib.AutoEncodingBufferedTag;
import com.aoindustries.website.ApplicationResources;
import java.io.Writer;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspTag;

/**
 * @author  AO Industries, Inc.
 */
public class EncryptTag extends AutoEncodingBufferedTag {

    public MediaType getContentType() {
        return MediaType.TEXT;
    }

    public MediaType getOutputType() {
        return null;
    }

    protected void doTag(StringBuilderWriter capturedBody, Writer out) throws JspException {
        String encrypt = capturedBody.toString().trim();
        boolean encryptFlag;
        if(encrypt.equalsIgnoreCase("true")) encryptFlag = true;
        else if(encrypt.equalsIgnoreCase("false")) encryptFlag = false;
        else throw new JspException(ApplicationResources.accessor.getMessage("skintags.EncryptTag.encrypt.invalid", encrypt));
        JspTag parent = findAncestorWithClass(this, EncryptAttribute.class);
        if(parent==null) {
            PageAttributesBodyTag.getPageAttributes((PageContext)getJspContext()).setEncrypt(encryptFlag);
        } else {
            EncryptAttribute encryptAttribute = (EncryptAttribute)parent;
            encryptAttribute.setEncrypt(encryptFlag);
        }
    }
}
