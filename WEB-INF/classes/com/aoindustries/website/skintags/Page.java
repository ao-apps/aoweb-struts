package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
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
    private boolean useEncryption;
    private String path;

    public Page(String title, String navImageAlt, boolean useEncryption, String path) {
        this.title = title;
        this.navImageAlt = navImageAlt;
        this.useEncryption = useEncryption;
        this.path = path;
    }
    
    public String getTitle() {
        return title;
    }

    public String getNavImageAlt() {
        return navImageAlt;
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
