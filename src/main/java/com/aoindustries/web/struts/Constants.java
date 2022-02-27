/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2019, 2021, 2022  AO Industries, Inc.
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

import com.aoapps.servlet.attribute.AttributeEE;
import com.aoapps.servlet.attribute.ScopeEE;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.master.Permission;
import java.util.List;
import java.util.Locale;

/**
 * Constants that may be used by other applications.
 *
 * @author  AO Industries, Inc.
 */
public final class Constants {

	/** Make no instances. */
	private Constants() {throw new AssertionError();}

	/**
	 * The session key that stores when a {@link #SU} has been requested.
	 */
	public static final ScopeEE.Session.Attribute<String> SU_REQUESTED =
		ScopeEE.SESSION.attribute("suRequested");

	/**
	 * The session key used to store the effective <code>AOServConnector</code> when the user has successfully authenticated.
	 * Any {@link #SU} can change this.
	 * Also stored per-request as request attribute once authentication resolved, this way a request is consistent even
	 * when session state changing.
	 */
	public static final AttributeEE.Name<AOServConnector> AO_CONN =
		AttributeEE.attribute("aoConn");

	/**
	 * The session key used to store the <code>AOServConnector</code> that the user has authenticated as.
	 * {@link #SU} will not changes this.
	 */
	public static final ScopeEE.Session.Attribute<AOServConnector> AUTHENTICATED_AO_CONN =
		ScopeEE.SESSION.attribute("authenticatedAoConn");

	/**
	 * The session key that stores the authentication target.
	 */
	public static final ScopeEE.Session.Attribute<String> AUTHENTICATION_TARGET =
		ScopeEE.SESSION.attribute("authenticationTarget");

	/**
	 * The session key that stores the set of targets that have been recently seen.
	 * Targets are given to the client by ID, and the associated URL is stored here.
	 * This is to prevent cross-site redirect attacks.
	 */
	public static final String TARGETS = "targets";

	/**
	 * The request key used for switch-user.
	 */
	public static final String SU = "su";

	/**
	 * The request key used for current language.
	 */
	public static final String LANGUAGE = "language";

	/**
	 * The request key used to store authentication messages.
	 */
	public static final ScopeEE.Request.Attribute<String> AUTHENTICATION_MESSAGE =
		ScopeEE.REQUEST.attribute("authenticationMessage");

	/**
	 * The session key used to store the current <code>layout</code>.  The layout setting
	 * affects the per-request skin selection.
	 */
	// Matches ao-web-framework/WebSiteRequest.LAYOUT
	public static final ScopeEE.Session.Attribute<String> LAYOUT =
		ScopeEE.SESSION.attribute("layout");

	/**
	 * The request key used to store the current <code>Skin</code>.
	 */
	public static final ScopeEE.Request.Attribute<Skin> SKIN =
		ScopeEE.REQUEST.attribute("skin");

	/**
	 * The request key used to store the current <code>SiteSettings</code>.
	 */
	public static final ScopeEE.Request.Attribute<SiteSettings> SITE_SETTINGS =
		ScopeEE.REQUEST.attribute("siteSettings");

	/**
	 * The request key used to store the current <code>Locale</code>.
	 */
	// TODO: Do not store in request, use response locale instead
	public static final ScopeEE.Request.Attribute<Locale> LOCALE =
		ScopeEE.REQUEST.attribute("locale");

	/**
	 * The request key used to store the <code>List&lt;Permission&gt;</code> that ALL must be allowed for the specified task.
	 */
	public static final ScopeEE.Request.Attribute<List<Permission>> PERMISSION_DENIED =
		ScopeEE.REQUEST.attribute("permissionDenied");
}
