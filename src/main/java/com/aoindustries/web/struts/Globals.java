/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2021  AO Industries, Inc.
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
 * along with aoweb-struts.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.web.struts;

import com.aoapps.servlet.attribute.AttributeEE;
import com.aoapps.servlet.attribute.ScopeEE;
import java.util.Locale;
import org.apache.struts.action.ActionMessages;

/**
 * {@linkplain org.apache.struts.Globals Struts 1 Globals} as {@link AttributeEE}.
 *
 * @author  AO Industries, Inc.
 */
public class Globals {

	private Globals() {
	}

	/**
	 * The Struts 1 action errors.
	 *
	 * @see  org.apache.struts.Globals#ERROR_KEY
	 */
	public static final ScopeEE.Request.Attribute<ActionMessages> ERROR_KEY =
		ScopeEE.REQUEST.attribute(org.apache.struts.Globals.ERROR_KEY);

	/**
	 * The Struts 1 exception.
	 *
	 * @see  org.apache.struts.Globals#EXCEPTION_KEY
	 */
	public static final ScopeEE.Request.Attribute<Throwable> EXCEPTION_KEY =
		ScopeEE.REQUEST.attribute(org.apache.struts.Globals.EXCEPTION_KEY);

	/**
	 * The Struts 1 session locale.
	 *
	 * @see  org.apache.struts.Globals#LOCALE_KEY
	 */
	public static final ScopeEE.Session.Attribute<Locale> LOCALE_KEY =
		ScopeEE.SESSION.attribute(org.apache.struts.Globals.LOCALE_KEY);

	/**
	 * The Struts 1 XHTML flag.
	 *
	 * @see  org.apache.struts.Globals#XHTML_KEY
	 */
	public static final ScopeEE.Page.Attribute<String> XHTML_KEY =
		ScopeEE.PAGE.attribute(org.apache.struts.Globals.XHTML_KEY);
}
