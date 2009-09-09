package com.aoindustries.website;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.util.EditableResourceBundle;
import java.io.File;

/**
 * @author  AO Industries, Inc.
 */
public final class SiteApplicationResources extends EditableResourceBundle {

    /**
     * Do not use directly.
     */
    public SiteApplicationResources() {
        super(new File(System.getProperty("user.home")+"/common/ao/cvswork/aoweb-struts/WEB-INF/classes/com/aoindustries/website/SiteApplicationResources.properties"));
    }
}
