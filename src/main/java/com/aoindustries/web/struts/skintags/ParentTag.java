/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2019, 2020, 2021, 2022  AO Industries, Inc.
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

/**
 * Adds a parent to the hierarchy above this page.
 *
 * @author  AO Industries, Inc.
 */
public class ParentTag extends PageTag {

  private static final long serialVersionUID = 1L;

  static final ScopeEE.Request.Attribute<Stack<ParentTag>> STACK_REQUEST_ATTRIBUTE =
      ScopeEE.REQUEST.attribute(ParentTag.class.getName() + ".stack");

  private List<Child> children;

  @Override
  protected void init() {
    super.init();
    children = null;
  }

  @Override
  public int doStartTag() throws JspException {
    ServletRequest request = pageContext.getRequest();
    Stack<ParentTag> stack = STACK_REQUEST_ATTRIBUTE.context(request).computeIfAbsent(__ -> new Stack<>());
    stack.push(this);
    return super.doStartTag();
  }

  /**
   * Gets the children of this parent page.
   */
  public List<Child> getChildren() {
    if (children == null) {
      return Collections.emptyList();
    } else {
      return Collections.unmodifiableList(children);
    }
  }

  public void addChild(Child child) {
    if (children == null) {
      children = new ArrayList<>();
    }
    children.add(child);
  }

  @Override
  @SuppressWarnings("unchecked")
  protected int doEndTag(
      String title,
      String navImageAlt,
      String description,
      String author,
      String authorHref,
      String copyright,
      String path,
      String keywords,
      Collection<Meta> metas
  ) throws JspException, IOException {
    Stack<ParentTag> stack = STACK_REQUEST_ATTRIBUTE.context(pageContext.getRequest()).get();
    if (stack != null && !stack.isEmpty() && stack.peek() == this) {
      stack.pop();
    }

    PageAttributesBodyTag.getPageAttributes(pageContext).addParent(
        new Parent(title, navImageAlt, description, author, authorHref, copyright, path, keywords, metas, children)
    );
    return EVAL_PAGE;
  }
}
