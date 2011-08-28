/*
 * Copyright 2009-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.skintags;
import com.aoindustries.encoding.MediaType;
import com.aoindustries.io.AutoTempFileWriter;
import com.aoindustries.taglib.AutoEncodingBufferedTag;
import com.aoindustries.taglib.ContentAttribute;
import com.aoindustries.taglib.NameAttribute;
import java.io.Writer;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspTag;

/**
 * @author  AO Industries, Inc.
 */
public class MetaTag extends AutoEncodingBufferedTag implements NameAttribute, ContentAttribute {

    private String name;
    private String content;

    @Override
    public MediaType getContentType() {
        return MediaType.TEXT;
    }

    @Override
    public MediaType getOutputType() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected void doTag(AutoTempFileWriter capturedBody, Writer out) {
        String myContent = content;
        if(myContent==null) myContent = capturedBody.toString().trim();
        Meta meta = new Meta(name, myContent);
        JspTag parent = findAncestorWithClass(this, MetasAttribute.class);
        if(parent==null) {
            PageAttributesBodyTag.getPageAttributes((PageContext)getJspContext()).addMeta(meta);
        } else {
            MetasAttribute metasAttribute = (MetasAttribute)parent;
            metasAttribute.addMeta(meta);
        }
    }
}
