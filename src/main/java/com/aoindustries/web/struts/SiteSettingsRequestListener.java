/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009-2013, 2015, 2016, 2019, 2020, 2021  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.aoindustries.web.struts;

import com.aoapps.hodgepodge.i18n.EditableResourceBundle;
import com.aoapps.servlet.http.Cookies;
import com.aoapps.servlet.http.HttpServletUtil;
import com.aoindustries.web.struts.struts.ResourceBundleMessageResources;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Resolves the current {@link SiteSettings}, sets the request attribute {@link Constants#SITE_SETTINGS}, and enables
 * resource editor on the current request {@link SiteSettings#getCanEditResources() when enabled}.
 *
 * @author AO Industries, Inc.
 */
@WebListener
public class SiteSettingsRequestListener implements ServletRequestListener {

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		// Resolve the settings
		SiteSettings siteSettings = SiteSettings.getInstance(sre.getServletContext());
		ServletRequest request = sre.getServletRequest();
		Constants.SITE_SETTINGS.context(request).set(siteSettings);

		// Start the request tracking
		boolean canEditResources = (request instanceof HttpServletRequest) && siteSettings.getCanEditResources();
		EditableResourceBundle.ThreadSettings threadSettings;
		if(canEditResources) {
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			// Check for cookie
			boolean modifyAllText = "visible".equals(Cookies.getCookie(httpRequest, "EditableResourceBundleEditorVisibility")); // TODO: "EditableResourceBundleEditorVisibility" should be a constant?
			threadSettings = new EditableResourceBundle.ThreadSettings(
				HttpServletUtil.getAbsoluteURL(httpRequest, "/set-resource-bundle-value.do"),
				EditableResourceBundle.ThreadSettings.Mode.MARKUP,
				modifyAllText
			);
		} else {
			threadSettings = new EditableResourceBundle.ThreadSettings();
		}
		ResourceBundleMessageResources.setCachedEnabled(!canEditResources);
		EditableResourceBundle.setThreadSettings(threadSettings);
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		// Nothing to do
	}
}
