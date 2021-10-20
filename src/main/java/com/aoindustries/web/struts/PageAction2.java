/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2020, 2021  AO Industries, Inc.
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

import com.aoapps.web.resources.registry.Registry;
import com.aoapps.web.resources.servlet.PageServlet;
import com.aoapps.web.resources.servlet.RegistryEE;
import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 * An action that creates a {@link com.aoapps.web.resources.servlet.RegistryEE.Page page-scope web resource registry},
 * if not already present.
 *
 * @see  PageServlet
 */
public abstract class PageAction2 extends ActionSupport {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates the page-scope registry, if not already present, then invokes
	 * {@link #execute(com.aoapps.web.resources.registry.Registry)}.
	 * The registry if left on the request to be available to any forwarding target.
	 */
	@Override
	/* Cannot be final due to overridden for @Action annotation */
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		Registry pageRegistry = RegistryEE.Page.get(request);
		if(pageRegistry == null) {
			// Create a new page-scope registry
			pageRegistry = new Registry();
			RegistryEE.Page.set(request, pageRegistry);
		}
		return execute(pageRegistry);
	}

	/**
	 * Once the page registry is set resolved, this version of the execute method is invoked.
	 */
	public abstract String execute(
		Registry pageRegistry
	) throws Exception;
}
