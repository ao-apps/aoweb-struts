/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2021, 2022  AO Industries, Inc.
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

package com.aoindustries.web.struts;

import com.aoapps.html.servlet.MetadataContent;
import com.aoapps.net.URIEncoder;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * The types of forms that may have resources added to document head.
 *
 * @author  AO Industries, Inc.
 */
public enum Formtype {
  /**
   * Adds <code>/struts1/commons-validator-1.3.1-compress.js</code> to {@code <head>} for Struts 1.
   */
  STRUTS1 {
    @Override
    public void doHead(ServletContext servletContext, HttpServletRequest req, HttpServletResponse resp, MetadataContent<?> content) throws IOException {
      content.script().src(
          resp.encodeURL(
              URIEncoder.encodeURI(
                  req.getContextPath() + "/struts1/commons-validator-1.3.1-compress.js"
              )
          )
      ).__();
    }
  },

  /**
   * Calls <a href="https://struts.apache.org/tag-developers/head-tag.html">{@code <s:head />}</a> within
   * {@code <head>} for <a href="https://struts.apache.org/">Struts 2</a>.
   * <p>
   * See <code>/WEB-INF/struts2/head.jsp</code>
   * </p>
   */
  STRUTS2 {
    @Override
    public void doHead(ServletContext servletContext, HttpServletRequest req, HttpServletResponse resp, MetadataContent<?> content) throws JspException, IOException {
      final String struts2HeadInclude = "/WEB-INF/struts2/head.jsp";
      try {
        servletContext.getRequestDispatcher(struts2HeadInclude).include(req, resp);
      } catch (ServletException e) {
        throw new JspException(e);
      }
    }
  };

  public abstract void doHead(ServletContext servletContext, HttpServletRequest req, HttpServletResponse resp, MetadataContent<?> content) throws JspException, IOException;
}
