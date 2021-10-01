/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2020, 2021  AO Industries, Inc.
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
package com.aoindustries.web.struts.skintags;

import com.aoapps.html.servlet.DocumentEE;
import com.aoapps.lang.Strings;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @author  AO Industries, Inc.
 */
public class ContentTag extends PageAttributesBodyTag {

	public static final String TAG_NAME = "<skin:content>";

	private static final long serialVersionUID = 1L;

	/**
	 * Parses a String of comma-separated integers into an <code>int[]</code>.
	 */
	static int[] parseColspans(String colspans) {
		List<String> tokens = Strings.splitCommaSpace(colspans);
		int[] array = new int[tokens.size()];
		for(int c = 0; c < tokens.size(); c++) {
			array[c] = Integer.parseInt(tokens.get(c));
		}
		return array;
	}

	private String colspans;
	private int[] colspansParsed;
	private String width;

	public ContentTag() {
		init();
	}

	private void init() {
		colspans = "1";
		colspansParsed = new int[] {1};
		width = null;
	}

	@Override
	public int doStartTag(PageAttributes pageAttributes) throws JspException, IOException {
		HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
		HttpServletResponse resp = (HttpServletResponse)pageContext.getResponse();
		SkinTag.getSkin(req).startContent(
			req,
			resp,
			new DocumentEE(
				pageContext.getServletContext(),
				req,
				resp,
				pageContext.getOut(),
				false, // Do not add extra newlines to JSP
				false  // Do not add extra indentation to JSP
			),
			pageAttributes,
			colspansParsed,
			width
		);
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag(PageAttributes pageAttributes) throws JspException, IOException {
		try {
			HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
			HttpServletResponse resp = (HttpServletResponse)pageContext.getResponse();
			SkinTag.getSkin(req).endContent(
				req,
				resp,
				new DocumentEE(
					pageContext.getServletContext(),
					req,
					resp,
					pageContext.getOut(),
					false, // Do not add extra newlines to JSP
					false  // Do not add extra indentation to JSP
				),
				pageAttributes,
				colspansParsed
			);
			return EVAL_PAGE;
		} finally {
			init();
		}
	}

	public String getColspans() {
		return colspans;
	}

	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	int[] getColspansParsed() {
		return colspansParsed;
	}

	public void setColspans(String colspans) {
		this.colspans = colspans;
		this.colspansParsed = parseColspans(colspans);
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
}
