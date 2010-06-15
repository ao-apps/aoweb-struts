package com.aoindustries.website.clientarea.ticket;

/*
 * Copyright 2009-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.util.i18n.EditableResourceBundle;
import java.io.File;
import java.util.Locale;

/**
 * Provides a simplified interface for obtaining localized values from the ApplicationResources.properties files.
 * Is also an editable resource bundle.
 *
 * @author  AO Industries, Inc.
 */
public final class ApplicationResources_ja extends EditableResourceBundle {

    /**
     * Do not use directly.
     */
    public ApplicationResources_ja() {
        super(
            new File(System.getProperty("user.home")+"/common/ao/cvswork/aoweb-struts/WEB-INF/classes/com/aoindustries/website/clientarea/ticket/ApplicationResources_ja.properties"),
            Locale.JAPANESE,
            ApplicationResources.bundleSet
        );
    }
}
