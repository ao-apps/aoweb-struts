/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2020, 2021, 2022, 2023  AO Industries, Inc.
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

import com.aoapps.servlet.attribute.ScopeEE;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author  AO Industries, Inc.
 */
public class LightDarkTableRowTag extends BodyTagSupport {

  private static final long serialVersionUID = 1L;

  private transient ScopeEE.Page.Attribute<Boolean> pageAttributeId;

  public LightDarkTableRowTag() {
    init();
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    init();
  }

  public String getPageAttributeId() {
    return (pageAttributeId == null) ? null : pageAttributeId.getName();
  }

  public void setPageAttributeId(String pageAttributeId) {
    this.pageAttributeId = (pageAttributeId == null) ? null : ScopeEE.PAGE.attribute(pageAttributeId);
  }

  private void init() {
    // Always start with a light row
    pageAttributeId = ScopeEE.PAGE.attribute("LightDarkTableRowTag.isDark");
  }

  @Override
  public int doStartTag() throws JspException {
    try {
      JspWriter out = pageContext.getOut();
      out.write("<tr class=\"");
      boolean isDark = pageAttributeId.context(pageContext).computeIfAbsent(name -> false);
      out.write(isDark ? "aoDarkRow" : "aoLightRow");
      out.write("\">");
      return EVAL_BODY_INCLUDE;
    } catch (IOException err) {
      throw new JspTagException(err);
    }
  }

  @Override
  public int doEndTag() throws JspException {
    try {
      boolean isDark = pageAttributeId.context(pageContext).computeIfAbsent(name -> false);
      pageAttributeId.context(pageContext).set(!isDark);
      pageContext.getOut().write("</tr>");
      return EVAL_PAGE;
    } catch (IOException err) {
      throw new JspTagException(err);
    } finally {
      init();
    }
  }
}
