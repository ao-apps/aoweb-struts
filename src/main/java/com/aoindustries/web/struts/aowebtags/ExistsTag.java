/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2020, 2021  AO Industries, Inc.
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
package com.aoindustries.web.struts.aowebtags;

import com.aoapps.servlet.ServletContextCache;
import java.net.MalformedURLException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Evaluates the body if the provided resource exists.
 *
 * @author  AO Industries, Inc.
 */
public class ExistsTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	private String path;

	public ExistsTag() {
		init();
	}

	private void init() {
		path = null;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			return ServletContextCache.getResource(pageContext.getServletContext(), path) != null ? EVAL_BODY_INCLUDE : SKIP_BODY;
		} catch(MalformedURLException err) {
			throw new JspTagException(err);
		}
	}

	@Override
	public int doEndTag() throws JspException {
		init();
		return EVAL_PAGE;
	}

	public String getPage() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
