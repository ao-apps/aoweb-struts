package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.jsp.PageContext;

/**
 * During the processing of the skin, page attributes are built and stored here, one instance per request.
 *
 * @author  AO Industries, Inc.
 */
public class PageAttributes {
    
    /**
     * The following key is used to store the objects in the page attributes.
     */
    public static final String ATTRIBUTE_KEY = "pageAttributes";

    /**
     * The following scope is used to store the objects in the page attributes.
     */
    public static final int ATTRIBUTE_SCOPE = PageContext.PAGE_SCOPE;

    public static class Meta {

        private final String name;
        private final String content;

        Meta(String name, String content) {
            this.name = name;
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }
    }

    private String path;
    private String keywords;
    private String description;
    private String author;
    private String copyright;
    private List<Meta> metas;
    private List<Meta> unmodifiableMetas;
    private String title;
    private String navImageAlt;
    private List<Page> parents;
    private List<Page> siblings;
    private String onLoad;
    
    public PageAttributes() {
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

    public List<Meta> getMetas() {
        if(metas==null) return Collections.emptyList();
        if(unmodifiableMetas==null) unmodifiableMetas = Collections.unmodifiableList(metas);
        return unmodifiableMetas;
    }

    public void addMeta(String name, String content) {
        if(metas==null) metas = new ArrayList<Meta>();
        metas.add(new Meta(name, content));
    }

    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getNavImageAlt() {
        String navImageAlt = this.navImageAlt;
        if(navImageAlt==null || navImageAlt.length()==0) navImageAlt = this.title;
        return navImageAlt;
    }
    
    public void setNavImageAlt(String navImageAlt) {
        this.navImageAlt = navImageAlt;
    }

    public List<Page> getParents() {
        if(parents==null) {
            List<Page> emptyList = Collections.emptyList();
            return emptyList;
        }
        return parents;
    }

    public void addParent(Page parent) {
        if(parents==null) parents = new ArrayList<Page>();
        parents.add(parent);
    }

    public List<Page> getSiblings() {
        if(siblings==null) {
            List<Page> emptyList = Collections.emptyList();
            return emptyList;
        }
        return siblings;
    }

    public void addSibling(Page sibling) {
        if(siblings==null) siblings = new ArrayList<Page>();
        siblings.add(sibling);
    }

    public String getOnLoad() {
        return onLoad;
    }
    
    public void setOnLoad(String onLoad) {
        this.onLoad = onLoad;
    }
}
