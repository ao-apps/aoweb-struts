package com.aoindustries.website.clientarea;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.util.ApplicationResourcesAccessor;
import com.aoindustries.util.EditableResourceBundle;
import com.aoindustries.util.EditableResourceBundleSet;
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
            new File(System.getProperty("user.home")+"/common/ao/cvswork/aoweb-struts/WEB-INF/classes/com/aoindustries/website/clientarea/ApplicationResources.properties"),
            new Locale(""),
            bundleSet
        );
    }

    private static final ApplicationResourcesAccessor accessor = new ApplicationResourcesAccessor(bundleSet.getBaseName());

    public static String getMessage(Locale locale, String key) {
        return accessor.getMessage(locale, key);
    }

    public static String getMessage(Locale locale, String key, Object... args) {
        return accessor.getMessage(locale, key, args);
    }

    public static String getMessage(String missingDefault, Locale locale, String key) {
        return accessor.getMessage(missingDefault, locale, key);
    }

    public static String getMessage(String missingDefault, Locale locale, String key, Object... args) {
        return accessor.getMessage(missingDefault, locale, key, args);
    }
}
