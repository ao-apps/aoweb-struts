/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2020, 2021, 2022  AO Industries, Inc.
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

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Resolves a shared instance of <code>PageAttributes</code> for all subclasses.
 *
 * @author  AO Industries, Inc.
 */
public abstract class PageAttributesTag extends TagSupport {

  private static final long serialVersionUID = 1L;

  protected PageAttributesTag() {
    // Do nothing
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated  You should probably be implementing in {@link #doStartTag(com.aoindustries.web.struts.skintags.PageAttributes)}
   *
   * @see  #doStartTag(com.aoindustries.web.struts.skintags.PageAttributes)
   */
  @Deprecated(forRemoval = false)
  @Override
  public int doStartTag() throws JspException {
    try {
      return doStartTag(PageAttributesBodyTag.getPageAttributes(pageContext));
    } catch (IOException err) {
      throw new JspTagException(err);
    }
  }

  public int doStartTag(PageAttributes pageAttributes) throws JspException, IOException {
    return SKIP_BODY;
  }

  /**
   * {@inheritDoc}
   *
   * @deprecated  You should probably be implementing in {@link #doEndTag(com.aoindustries.web.struts.skintags.PageAttributes)}
   *
   * @see  #doEndTag(com.aoindustries.web.struts.skintags.PageAttributes)
   */
  @Deprecated(forRemoval = false)
  @Override
  public int doEndTag() throws JspException {
    try {
      return doEndTag(PageAttributesBodyTag.getPageAttributes(pageContext));
    } catch (IOException err) {
      throw new JspTagException(err);
    }
  }

  public int doEndTag(PageAttributes pageAttributes) throws JspException, IOException {
    return EVAL_PAGE;
  }
}
