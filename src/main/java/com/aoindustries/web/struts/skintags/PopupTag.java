/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2020, 2021, 2022, 2023  AO Industries, Inc.
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
import com.aoapps.lang.util.Sequence;
import com.aoapps.lang.util.UnsynchronizedSequence;
import com.aoapps.servlet.attribute.ScopeEE;
import com.aoapps.servlet.jsp.tagext.JspTagUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Renders one popup, that may optionally be nested in a PopupGroupTag.
 *
 * @see  PopupGroupTag
 *
 * @author  AO Industries, Inc.
 */
public class PopupTag extends BodyTagSupport {

  public static final String TAG_NAME = "<skin:popup>";

  /**
   * The request attribute name used to store the sequence.
   */
  private static final ScopeEE.Request.Attribute<Sequence> SEQUENCE_REQUEST_ATTRIBUTE =
      ScopeEE.REQUEST.attribute(PopupTag.class.getName() + ".sequence");

  private static final long serialVersionUID = 1L;

  @SuppressWarnings("PackageVisibleField")
  transient long sequenceId;
  private transient String width;

  public PopupTag() {
    init();
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    init();
  }

  private void init() {
    this.width = null;
  }

  @Override
  public int doStartTag() throws JspException {
    try {
      HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
      sequenceId = SEQUENCE_REQUEST_ATTRIBUTE.context(req)
          .computeIfAbsent(name -> new UnsynchronizedSequence())
          .getNextSequenceValue();
      // Look for containing popupGroup
      PopupGroupTag popupGroupTag = JspTagUtils.requireAncestor(TAG_NAME, this, PopupGroupTag.TAG_NAME, PopupGroupTag.class);
      HttpServletResponse resp = (HttpServletResponse) pageContext.getResponse();
      SkinTag.getSkin(req).beginPopup(
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
          popupGroupTag.sequenceId,
          sequenceId,
          width
      );
      return EVAL_BODY_INCLUDE;
    } catch (IOException e) {
      throw new JspTagException(e);
    }
  }

  @Override
  public int doEndTag() throws JspException  {
    try {
      // Look for containing popupGroup
      PopupGroupTag popupGroupTag = JspTagUtils.requireAncestor(TAG_NAME, this, PopupGroupTag.TAG_NAME, PopupGroupTag.class);
      HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
      HttpServletResponse resp = (HttpServletResponse) pageContext.getResponse();
      SkinTag.getSkin(req).endPopup(
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
          popupGroupTag.sequenceId,
          sequenceId,
          width
      );
      return EVAL_PAGE;
    } catch (IOException e) {
      throw new JspTagException(e);
    } finally {
      init();
    }
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }
}
