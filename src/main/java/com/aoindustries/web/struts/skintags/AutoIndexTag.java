/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2021, 2022, 2025, 2026  AO Industries, Inc.
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @author  AO Industries, Inc.
 */
public class AutoIndexTag extends TagSupport {

  private static final long serialVersionUID = 1L;

  @Override
  public int doStartTag() throws JspException {
    try {
      HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
      HttpServletResponse resp = (HttpServletResponse) pageContext.getResponse();
      SkinTag.getSkin(req).printAutoIndex(
          req,
          resp,
          PageAttributesBodyTag.getPageAttributes(pageContext),
          new DocumentEE(
              pageContext.getServletContext(),
              req,
              resp,
              pageContext.getOut(),
              false, // Do not add extra newlines to JSP
              false  // Do not add extra indentation to JSP
          )
      );
      return SKIP_BODY;
    } catch (IOException e) {
      throw new JspTagException(e);
    }
  }
}
