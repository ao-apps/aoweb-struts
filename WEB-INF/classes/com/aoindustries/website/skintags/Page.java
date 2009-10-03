package com.aoindustries.website.skintags;

import java.util.Collection;
import java.util.Collections;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */

/**
 * Contains the information for a page used for parents and children.
 *
 * @author  AO Industries, Inc.
 */
abstract public class Page {

    private final String title;
    private final String navImageAlt;
    private final String description;
    private final String author;
    private final String copyright;
    private final boolean encrypt;
    private final String path;
    private final String keywords;
    private final Collection<Meta> metas;

    public Page(String title, String navImageAlt, String description, String author, String copyright, boolean encrypt, String path, String keywords, Collection<Meta> metas) {
        this.title = title;
        this.navImageAlt = navImageAlt;
        this.description = description;
        this.author = author;
        this.copyright = copyright;
        this.encrypt = encrypt;
        this.path = path;
        this.keywords = keywords;
        this.metas = metas;
    }
    
    public String getTitle() {
        return title;
    }

    /**
     * If not specified, the nav image defaults to the title.
     */
    public String getNavImageAlt() {
        String myNavImageAlt = this.navImageAlt;
        if(myNavImageAlt==null || myNavImageAlt.length()==0) myNavImageAlt = this.title;
        return myNavImageAlt;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getCopyright() {
        return copyright;
    }

    public boolean getEncrypt() {
        return encrypt;
    }
    
    public String getPath() {
        return path;
    }

    public String getKeywords() {
        return keywords;
    }

    public Collection<Meta> getMetas() {
        if(metas==null) return Collections.emptyList();
        return metas;
    }

    @Override
    public String toString() {
        return title;
    }
}
