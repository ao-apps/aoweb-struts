/*
 * aoweb-struts-core - Core API for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2019  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts-core.
 *
 * aoweb-struts-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts-core.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.website;

/**
 * Constants that may be used by other applications.
 *
 * @author  AO Industries, Inc.
 */
public class Constants {

	private Constants() {
	}

	/**
	 * The session key that stores when a "su" has been requested.
	 */
	public static final String SU_REQUESTED = "suRequested";

	/**
	 * The session key used to store the effective <code>AOServConnector</code> when the user has successfully authenticated.  Any "su" can change this.
	 */
	public static final String AO_CONN = "aoConn";

	/**
	 * The session key used to store the <code>AOServConnector</code> that the user has authenticated as.  "su" will not changes this.
	 */
	public static final String AUTHENTICATED_AO_CONN="authenticatedAoConn";

	/**
	 * The session key that stores the authentication target.
	 * // TODO: Review AUTHENTICATION_TARGET stored as decodedURI
	 */
	public static final String AUTHENTICATION_TARGET="authenticationTarget";

	/**
	 * The request key used to store authentication messages.
	 */
	public static final String AUTHENTICATION_MESSAGE = "authenticationMessage";

	/**
	 * The session key used to store the current <code>layout</code>.  The layout setting
	 * affects the per-request skin selection.
	 */
	public static final String LAYOUT = "layout";

	/**
	 * The request key used to store the current <code>Skin</code>.
	 */
	public static final String SKIN = "skin";

	/**
	 * The request key used to store the current <code>SiteSettings</code>.
	 */
	public static final String SITE_SETTINGS = "siteSettings";

	/**
	 * The request key used to store the current <code>Locale</code>.
	 */
	public static final String LOCALE = "locale";

	/**
	 * The request key used to store the <code>List&lt;AOServPermission&gt;</code> that ALL must be allowed for the specified task.
	 */
	public static final String PERMISSION_DENIED = "permissionDenied";

	/**
	 * Until version 3.0 there will not be a getStatus method on the HttpServletResponse class.
	 * To allow code to detect the current status, anytime the status is set one should
	 * also set the request attribute of this name to a java.lang.Integer of the status.
	 */
	public static final String HTTP_SERVLET_RESPONSE_STATUS = "httpServletResponseStatus";
}