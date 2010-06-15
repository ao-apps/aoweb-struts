/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.skintags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

/**
 * Common parent to parent and child tags.
 *
 * @author  AO Industries, Inc.
 */
abstract public class PageTag extends BodyTagSupport implements
        KeywordsAttribute,
        DescriptionAttribute,
        AuthorAttribute,
        CopyrightAttribute,
        PathAttribute,
        MetasAttribute,
        TitleAttribute,
        NavImageAltAttribute,
        EncryptAttribute {

    private String title;
    private String navImageAlt;
    private String description;
    private String author;
    private String copyright;
    private String path;
    private String keywords;
    private Collection<Meta> metas;
    private boolean encryptSet;
    private boolean encrypt;

    public PageTag() {
        init();
    }

    protected void init() {
        title = null;
        navImageAlt = null;
        description = null;
        author = null;
        copyright = null;
        path = null;
        keywords = null;
        metas = null;
        encryptSet = false;
        encrypt = false;
    }

    @Override
    public int doStartTag() {
        return EVAL_BODY_BUFFERED;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNavImageAlt() {
        return navImageAlt;
    }

    public void setNavImageAlt(String navImageAlt) {
        this.navImageAlt = navImageAlt;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Collection<Meta> getMetas() {
        if(metas==null) return Collections.emptyList();
        return metas;
    }

    public void addMeta(Meta meta) {
        if(metas==null) metas = new ArrayList<Meta>();
        metas.add(meta);
    }

    public boolean getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encryptSet = true;
        this.encrypt = encrypt;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            if(title==null) {
                MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
                throw new JspException(applicationResources.getMessage("skintags.PageTag.needsTitleTag"));
            }
            String myNavImageAlt = this.navImageAlt;
            if(myNavImageAlt == null || myNavImageAlt.length()==0) myNavImageAlt=title;
            String myDescription = this.description;
            if(myDescription == null || myDescription.length()==0) myDescription=title;
            return doEndTag(title, myNavImageAlt, myDescription, author, copyright, encryptSet ? encrypt : pageContext.getRequest().isSecure(), path, keywords, metas);
        } finally {
            init();
        }
    }

    abstract protected int doEndTag(
        String title,
        String navImageAlt,
        String description,
        String author,
        String copyright,
        boolean useEncryption,
        String path,
        String keywords,
        Collection<Meta> metas
    ) throws JspException;
}
