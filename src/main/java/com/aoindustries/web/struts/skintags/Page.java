/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2019, 2021, 2022  AO Industries, Inc.
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

import java.util.Collection;
import java.util.Collections;

/**
 * Contains the information for a page used for parents and children.
 *
 * @author  AO Industries, Inc.
 */
public abstract class Page {

  private final String title;
  private final String navImageAlt;
  private final String description;
  private final String author;
  private final String authorHref;
  private final String copyright;
  private final String path;
  private final String keywords;
  private final Collection<Meta> metas;

  protected Page(String title, String navImageAlt, String description, String author, String authorHref, String copyright, String path, String keywords, Collection<Meta> metas) {
    if (title == null) {
      throw new IllegalArgumentException("title is null");
    }
    if (description == null) {
      throw new IllegalArgumentException("description is null");
    }
    if (path == null) {
      throw new IllegalArgumentException("path is null");
    }
    //if (keywords == null) {
    //  throw new IllegalArgumentException("keywords is null");
    //}
    this.title = title;
    this.navImageAlt = navImageAlt;
    this.description = description;
    this.author = author;
    this.authorHref = authorHref;
    this.copyright = copyright;
    this.path = path;
    this.keywords = keywords;
    this.metas = metas;
  }

  public String getTitle() {
    return title;
  }

  /**
   * If not specified, the nav image defaults to the title.
   */
  public String getNavImageAlt() {
    String myNavImageAlt = this.navImageAlt;
    if (myNavImageAlt == null || myNavImageAlt.length() == 0) {
      myNavImageAlt = this.title;
    }
    return myNavImageAlt;
  }

  public String getDescription() {
    return description;
  }

  public String getAuthor() {
    return author;
  }

  public String getAuthorHref() {
    return authorHref;
  }

  public String getCopyright() {
    return copyright;
  }

  public String getPath() {
    return path;
  }

  public String getKeywords() {
    return keywords;
  }

  public Collection<Meta> getMetas() {
    if (metas == null) {
      return Collections.emptyList();
    }
    return metas;
  }

  @Override
  public String toString() {
    return title;
  }
}
