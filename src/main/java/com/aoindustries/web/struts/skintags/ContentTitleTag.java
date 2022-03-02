/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2020, 2021, 2022  AO Industries, Inc.
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
package com.aoindustries.web.struts.skintags;

import com.aoapps.encoding.MediaType;
import com.aoapps.encoding.taglib.EncodingBufferedTag;
import com.aoapps.html.servlet.ContentEE;
import com.aoapps.io.buffer.BufferResult;
import com.aoapps.servlet.jsp.tagext.JspTagUtils;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author  AO Industries, Inc.
 */
public class ContentTitleTag extends EncodingBufferedTag {

	public static final String TAG_NAME = "<skin:contentTitle>";

	@Override
	public MediaType getContentType() {
		return MediaType.TEXT;
	}

	@Override
	public MediaType getOutputType() {
		return MediaType.XHTML;
	}

	@Override
	protected void doTag(BufferResult capturedBody, Writer out) throws JspException, IOException {
		PageContext pageContext = (PageContext)getJspContext();
		HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
		String title = capturedBody.trim().toString();

		// TODO: Should we instead set request attributes instead of tag ancestors?  This would allow tag interaction across include boundaries.
		ContentTag contentTag = JspTagUtils.requireAncestor(TAG_NAME, this, ContentTag.TAG_NAME, ContentTag.class);

		int[] colspans = contentTag.getColspansParsed();
		int totalColspan = 0;
		for(int c = 0; c < colspans.length; c++) {
			totalColspan += colspans[c];
		}

		ContentEE<?> content = contentTag.getContent();
		content.getDocument().setOut(pageContext.getOut());
		SkinTag.getSkin(req).contentTitle(
			req,
			(HttpServletResponse)pageContext.getResponse(),
			content,
			title,
			totalColspan
		);
	}
}
