/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009-2013, 2015, 2016, 2020, 2021, 2022, 2023  AO Industries, Inc.
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
import com.aoapps.html.any.attributes.text.Name;
import com.aoapps.io.buffer.BufferResult;
import com.aoapps.lang.Coercion;
import com.aoapps.taglib.ContentAttribute;
import com.aoapps.taglib.NameAttribute;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author  AO Industries, Inc.
 */
public class MetaTag extends EncodingBufferedTag implements NameAttribute, ContentAttribute {

  private String name;
  private Object content;

  @Override
  public MediaType getContentType() {
    return MediaType.TEXT;
  }

  @Override
  public MediaType getOutputType() {
    return null;
  }

  @Override
  public void setName(Object name) throws IOException {
    this.name = Coercion.toString(Name.name.normalize(name));
  }

  @Override
  public void setContent(Object content) {
    this.content = content;
  }

  @Override
  protected void doTag(BufferResult capturedBody, Writer out) throws JspException, IOException {
    PageContext pageContext = (PageContext) getJspContext();
    Object myContent = content;
    if (myContent == null) {
      myContent = capturedBody.trim().toString();
    }
    Meta meta = new Meta(
        name,
        Coercion.toString(myContent)
    );
    PageTag pageTag = PageTag.getPageTag(pageContext.getRequest());
    if (pageTag == null) {
      PageAttributesBodyTag.getPageAttributes(pageContext).addMeta(meta);
    } else {
      pageTag.addMeta(meta);
    }
  }
}
