/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2013, 2015, 2016, 2020, 2021  AO Industries, Inc.
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

import com.aoapps.encoding.MediaType;
import com.aoapps.encoding.taglib.EncodingBufferedTag;
import com.aoapps.io.buffer.BufferResult;
import com.aoapps.net.URIParametersMap;
import com.aoapps.net.URIParametersUtils;
import com.aoapps.taglib.HrefAttribute;
import com.aoapps.taglib.ParamsAttribute;
import com.aoapps.taglib.RelAttribute;
import com.aoapps.taglib.TypeAttribute;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author  AO Industries, Inc.
 */
public class LinkTag extends EncodingBufferedTag
	implements
		RelAttribute,
		HrefAttribute,
		ParamsAttribute,
		TypeAttribute
{

	@Override
	public MediaType getContentType() {
		return MediaType.URL;
	}

	@Override
	public MediaType getOutputType() {
		return null;
	}

	private String rel;
	private String href;
	private URIParametersMap params;
	private String type;

	public LinkTag() {
		init();
	}

	private void init() {
		rel = null;
		href = null;
		params = null;
		type = null;
	}

	@Override
	public void setRel(String rel) {
		this.rel = rel;
	}

	@Override
	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public void addParam(String name, Object value) {
		if(params == null) params = new URIParametersMap();
		params.add(name, value);
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	protected void doTag(BufferResult capturedBody, Writer out) throws JspException, IOException {
		String myHref = href;
		assert capturedBody.trim() == capturedBody : "URLs should have already been trimmed";
		if(myHref == null) myHref = capturedBody.toString();
		myHref = URIParametersUtils.addParams(myHref, params);
		PageAttributesBodyTag.getPageAttributes(
			(PageContext)getJspContext()
		).addLink(
			rel,
			myHref,
			type
		);
	}
}
