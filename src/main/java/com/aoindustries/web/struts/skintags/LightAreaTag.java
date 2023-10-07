/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2019, 2021, 2022, 2023  AO Industries, Inc.
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

import com.aoapps.html.servlet.DocumentEE;
import com.aoapps.html.servlet.FlowContent;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * Writes the skin light area.
 *
 * @author  AO Industries, Inc.
 */
// TODO: Convert these to TryCatchFinally when have init()
public class LightAreaTag extends PageAttributesBodyTag {

  private static final long serialVersionUID = 1L;

  private transient String align;
  private transient String width;
  private transient boolean nowrap;
  // Values only used between doStartTag and doEndTag
  private transient FlowContent<?> lightArea;

  public LightAreaTag() {
    init();
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    init();
  }

  private void init() {
    align = null;
    width = null;
    nowrap = false;
    lightArea = null;
  }

  @Override
  public int doStartTag(PageAttributes pageAttributes) throws JspException, IOException {
    HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
    HttpServletResponse resp = (HttpServletResponse) pageContext.getResponse();
    lightArea = SkinTag.getSkin(req).startLightArea(
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
        align,
        width,
        nowrap
    );
    return EVAL_BODY_INCLUDE;
  }

  @Override
  public int doEndTag(PageAttributes pageAttributes) throws JspException, IOException {
    try {
      HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
      assert lightArea != null;
      lightArea.getDocument().setOut(pageContext.getOut());
      SkinTag.getSkin(req).endLightArea(
          req,
          (HttpServletResponse) pageContext.getResponse(),
          lightArea
      );
      return EVAL_PAGE;
    } finally {
      init();
    }
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

  public boolean getNowrap() {
    return nowrap;
  }

  public void setNowrap(boolean nowrap) {
    this.nowrap = nowrap;
  }
}
