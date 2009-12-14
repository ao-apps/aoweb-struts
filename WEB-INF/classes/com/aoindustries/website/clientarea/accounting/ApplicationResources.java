package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
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
            new Locale(""), // Locale.ROOT in Java 1.6
            Locale.JAPANESE
        )
    );

    /**
     * Do not use directly.
     */
    public ApplicationResources() {
        super(
            new File(System.getProperty("user.home")+"/common/ao/cvswork/aoweb-struts/WEB-INF/classes/com/aoindustries/website/clientarea/accounting/ApplicationResources.properties"),
            new Locale(""),
            bundleSet
        );
    }
}
