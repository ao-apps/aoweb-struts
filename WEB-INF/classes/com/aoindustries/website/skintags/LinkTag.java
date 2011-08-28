package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.encoding.MediaType;
import com.aoindustries.io.AutoTempFileWriter;
import com.aoindustries.taglib.AutoEncodingBufferedTag;
import com.aoindustries.taglib.HrefAttribute;
import com.aoindustries.taglib.RelAttribute;
import com.aoindustries.taglib.TypeAttribute;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.jsp.PageContext;

/**
 * @author  AO Industries, Inc.
 */
public class LinkTag extends AutoEncodingBufferedTag implements RelAttribute, HrefAttribute, TypeAttribute {

    @Override
    public MediaType getContentType() {
        return MediaType.URL;
    }

    @Override
    public MediaType getOutputType() {
        return MediaType.XHTML;
    }

    private String rel;
    private String href;
    private String type;
    private String conditionalCommentExpression;

    public LinkTag() {
        init();
    }

    private void init() {
        rel = null;
        href = null;
        type = null;
        conditionalCommentExpression = null;
    }

    @Override
    public String getRel() {
        return rel;
    }

    @Override
    public void setRel(String rel) {
        this.rel = rel;
    }

    @Override
    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public String getConditionalCommentExpression() {
        return conditionalCommentExpression;
    }

    public void setConditionalCommentExpression(String conditionalCommentExpression) {
        this.conditionalCommentExpression = conditionalCommentExpression;
    }

    @Override
    protected void doTag(AutoTempFileWriter capturedBody, Writer out) throws IOException {
        String myHref = href;
        if(myHref==null) myHref = capturedBody.toString().trim();
        PageAttributesBodyTag.getPageAttributes((PageContext)getJspContext()).addLink(rel, href, type, conditionalCommentExpression);
    }
}
