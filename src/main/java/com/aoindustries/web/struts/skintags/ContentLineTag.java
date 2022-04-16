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

import com.aoapps.html.servlet.ContentEE;
import com.aoapps.html.servlet.FlowContent;
import com.aoapps.servlet.jsp.tagext.JspTagUtils;
import com.aoindustries.web.struts.Skin;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author  AO Industries, Inc.
 */
public class ContentLineTag extends BodyTagSupport {

	public static final String TAG_NAME = "<skin:contentLine>";

	private static final long serialVersionUID = 1L;

	private int colspan;
	private String align;
	private String width;
	private boolean endsInternal;
	// Values only used between doStartTag and doEndTag
	private transient int lastRowSpan;
	private transient FlowContent<?> contentLine;

	public ContentLineTag() {
		init();
	}

	private void init() {
		colspan = 1;
		align = null;
		width = null;
		endsInternal = false;
		lastRowSpan = 1;
		contentLine = null;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			ContentTag contentTag = JspTagUtils.requireAncestor(TAG_NAME, this, ContentTag.TAG_NAME, ContentTag.class);

			HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
			ContentEE<?> content = contentTag.getContent();
			content.getDocument().setOut(pageContext.getOut());
			contentLine = SkinTag.getSkin(req).startContentLine(
				req,
				(HttpServletResponse)pageContext.getResponse(),
				content,
				colspan,
				align,
				width
			);
			return EVAL_BODY_INCLUDE;
		} catch(IOException err) {
			throw new JspTagException(err);
		}
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
			assert contentLine != null;
			contentLine.getDocument().setOut(pageContext.getOut());
			SkinTag.getSkin(req).endContentLine(
				req,
				(HttpServletResponse)pageContext.getResponse(),
				contentLine,
				lastRowSpan,
				endsInternal
			);
			return EVAL_PAGE;
		} catch(IOException err) {
			throw new JspTagException(err);
		} finally {
			init();
		}
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isEndsInternal() {
		return endsInternal;
	}

	public void setEndsInternal(boolean endsInternal) {
		this.endsInternal = endsInternal;
	}

	/**
	 * The row span on endContentLine either either 1 or the rowspan of the last contentVerticalDivider
	 */
	void setLastRowSpan(int lastRowSpan) {
		this.lastRowSpan = lastRowSpan;
	}

	/**
	 * Gets the {@link FlowContent} that was returned from {@link Skin#startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String)}.
	 *
	 * @throws IllegalStateException when not inside {@link #doStartTag()} and no content set
	 */
	@SuppressWarnings("unchecked")
	<__ extends FlowContent<__>> __ getContentLine() throws IllegalStateException {
		if(contentLine == null) throw new IllegalStateException();
		return (__)contentLine;
	}

	/**
	 * Called from {@link ContentVerticalDividerTag} when the current content line is replaced by
	 * {@link Skin#contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.DocumentEE, boolean, int, int, java.lang.String, java.lang.String)}.
	 *
	 * @throws IllegalStateException when not inside {@link #doStartTag()} and no content set
	 */
	void setContentLine(FlowContent<?> contentLine) throws IllegalStateException {
		if(this.contentLine == null) throw new IllegalStateException();
		this.contentLine = Objects.requireNonNull(contentLine);
	}
}
