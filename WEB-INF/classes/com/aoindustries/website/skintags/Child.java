package com.aoindustries.website.skintags;

import java.util.Collection;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */

/**
 * Contains the information for a page used as a child.
 *
 * @author  AO Industries, Inc.
 */
public class Child extends Page {

    public Child(String title, String navImageAlt, String description, String author, String copyright, boolean useEncryption, String path, String keywords, Collection<Meta> metas) {
        super(title, navImageAlt, description, author, copyright, useEncryption, path, keywords, metas);
    }
}
