/*
 * Copyright 2009-2013, 2015 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.skintags;

import com.aoindustries.encoding.MediaType;
import com.aoindustries.encoding.Coercion;
import com.aoindustries.io.buffer.BufferResult;
import com.aoindustries.taglib.AutoEncodingBufferedTag;
import com.aoindustries.taglib.ContentAttribute;
import com.aoindustries.taglib.NameAttribute;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspTag;

/**
 * @author  AO Industries, Inc.
 */
public class MetaTag extends AutoEncodingBufferedTag implements NameAttribute, ContentAttribute {

    private Object name;
    private Object content;

    public MediaType getContentType() {
        return MediaType.TEXT;
    }

    public MediaType getOutputType() {
        return null;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    protected void doTag(BufferResult capturedBody, Writer out) throws IOException {
        Object myContent = content;
        if(myContent==null) myContent = capturedBody.trim().toString();
        Meta meta = new Meta(
			Coercion.toString(name),
			Coercion.toString(myContent)
		);
        JspTag parent = findAncestorWithClass(this, MetasAttribute.class);
        if(parent==null) {
            PageAttributesBodyTag.getPageAttributes((PageContext)getJspContext()).addMeta(meta);
        } else {
            MetasAttribute metasAttribute = (MetasAttribute)parent;
            metasAttribute.addMeta(meta);
        }
    }
}
