/*
 * Copyright 2009-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.clientarea;

import com.aoindustries.util.i18n.ApplicationResourcesAccessor;
import com.aoindustries.util.i18n.EditableResourceBundle;
import com.aoindustries.util.i18n.EditableResourceBundleSet;
import java.io.File;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author  AO Industries, Inc.
 */
public final class ApplicationResources extends EditableResourceBundle {

    static final EditableResourceBundleSet bundleSet = new EditableResourceBundleSet(
        ApplicationResources.class.getName(),
        Arrays.asList(
            Locale.ROOT,
            Locale.JAPANESE
        )
    );

    /**
     * Do not use directly.
     */
    public ApplicationResources() {
        super(
            Locale.ROOT,
            bundleSet,
            new File(System.getProperty("user.home")+"/common/ao/cvswork/aoweb-struts/WEB-INF/classes/com/aoindustries/website/clientarea/ApplicationResources.properties")
        );
    }

    static final ApplicationResourcesAccessor accessor = ApplicationResourcesAccessor.getInstance(bundleSet.getBaseName());
}
