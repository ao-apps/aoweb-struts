package com.aoindustries.website.skintags;

/*
 * Copyright 2009-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.encoding.MediaType;
import com.aoindustries.io.StringBuilderWriter;
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

    public MediaType getContentType() {
        return MediaType.TEXT;
    }

    public MediaType getOutputType() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    protected void doTag(StringBuilderWriter capturedBody, Writer out) {
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
