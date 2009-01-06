package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */

/**
 * Contains the information for a page used for parents and siblings.
 *
 * @author  AO Industries, Inc.
 */
public class Page {

    private String title;
    private String navImageAlt;
    private String description;
    private boolean useEncryption;
    private String path;

    public Page(String title, String navImageAlt, String description, boolean useEncryption, String path) {
        this.title = title;
        this.navImageAlt = navImageAlt;
        this.description = description;
        this.useEncryption = useEncryption;
        this.path = path;
    }
    
    public String getTitle() {
        return title;
    }

    public String getNavImageAlt() {
        return navImageAlt;
    }

    public String getDescription() {
        return description;
    }

    public boolean getUseEncryption() {
        return useEncryption;
    }
    
    public String getPath() {
        return path;
    }

    public String toString() {
        return title;
    }
}
