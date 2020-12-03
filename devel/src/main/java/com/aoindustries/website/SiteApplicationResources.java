/*
 * aoweb-struts-webapp - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2020  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts-webapp.
 *
 * aoweb-struts-webapp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts-webapp is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts-webapp.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.website;

import com.aoindustries.util.i18n.EditableResourceBundle;
import com.aoindustries.util.i18n.EditableResourceBundleSet;
import java.io.File;
import java.util.Locale;

/**
 * @author  AO Industries, Inc.
 */
public final class SiteApplicationResources extends EditableResourceBundle {

	static final EditableResourceBundleSet bundleSet = new EditableResourceBundleSet(
		SiteApplicationResources.class,
		Locale.ROOT,
		Locale.JAPANESE
	);

	static File getSourceFile(String filename) {
		return new File(System.getProperty("user.home") + "/maven2/ao/aoweb-struts/webapp/src/main/resources/com/aoindustries/website", filename);
	}

	public SiteApplicationResources() {
		super(Locale.ROOT, bundleSet, getSourceFile("SiteApplicationResources.properties"));
	}
}
