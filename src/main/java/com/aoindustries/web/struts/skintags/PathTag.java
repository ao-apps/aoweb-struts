/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2013, 2016, 2019, 2020, 2021  AO Industries, Inc.
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
import com.aoapps.taglib.AttributeUtils;
import com.aoapps.taglib.ParamUtils;
import com.aoapps.taglib.ParamsAttribute;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.DynamicAttributes;

/**
 * Sets the path for a page or its PathAttribute parent.
 *
 * @author  AO Industries, Inc.
 */
public class PathTag extends EncodingBufferedTag implements ParamsAttribute, DynamicAttributes {

	private URIParametersMap params;
	// TODO: canonical, absolute, addLastModified?

	@Override
	public MediaType getContentType() {
		return MediaType.URL;
	}

	@Override
	public MediaType getOutputType() {
		return null;
	}

	@Override
	public void addParam(String name, Object value) {
		if(params == null) params = new URIParametersMap();
		params.add(name, value);
	}

	/**
	 * @see  ParamUtils#addDynamicAttribute(java.lang.String, java.lang.String, java.lang.Object, java.util.List, com.aoapps.taglib.ParamsAttribute)
	 */
	@Override
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		List<String> expectedPatterns = new ArrayList<>();
		if(!ParamUtils.addDynamicAttribute(uri, localName, value, expectedPatterns, this)) {
			throw AttributeUtils.newDynamicAttributeFailedException(uri, localName, value, expectedPatterns);
		}
	}

	@Override
	protected void doTag(BufferResult capturedBody, Writer out) throws JspException, IOException {
		PageContext pageContext = (PageContext)getJspContext();
		assert capturedBody.trim() == capturedBody : "URLs should have already been trimmed";
		String path = capturedBody.toString();
		path = URIParametersUtils.addParams(path, params);
		PageTag pageTag = PageTag.getPageTag(pageContext.getRequest());
		if(pageTag==null) {
			PageAttributesBodyTag.getPageAttributes(pageContext).setPath(path);
		} else {
			pageTag.setPath(path);
		}
	}
}
