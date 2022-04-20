/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2019, 2020, 2021, 2022  AO Industries, Inc.
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
import com.aoapps.io.buffer.BufferResult;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author  AO Industries, Inc.
 */
public class AuthorHrefTag extends EncodingBufferedTag {

  @Override
  public MediaType getContentType() {
    return MediaType.TEXT;
  }

  @Override
  public MediaType getOutputType() {
    return null;
  }

  @Override
  protected void doTag(BufferResult capturedBody, Writer out) throws JspException, IOException {
    PageContext pageContext = (PageContext)getJspContext();
    String authorHref = capturedBody.trim().toString();
    PageTag pageTag = PageTag.getPageTag(pageContext.getRequest());
    if (pageTag == null) {
      PageAttributesBodyTag.getPageAttributes(pageContext).setAuthorHref(authorHref);
    } else {
      pageTag.setAuthorHref(authorHref);
    }
  }
}
