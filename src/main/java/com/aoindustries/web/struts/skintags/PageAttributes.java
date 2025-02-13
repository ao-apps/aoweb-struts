/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2019, 2020, 2021, 2022, 2024  AO Industries, Inc.
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

import static com.aoindustries.web.struts.Resources.PACKAGE_RESOURCES;

import com.aoapps.lang.LocalizedIllegalArgumentException;
import com.aoapps.lang.Strings;
import com.aoapps.servlet.attribute.ScopeEE;
import com.aoindustries.web.struts.Formtype;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * During the processing of the skin, page attributes are built and stored here, one instance per request.
 *
 * @author  AO Industries, Inc.
 */
public class PageAttributes {

  /**
   * The possible values for layout.
   */
  public static final String LAYOUT_NORMAL = "normal";

  /**
   * The possible values for layout.
   */
  public static final String LAYOUT_MINIMAL = "minimal";

  /**
   * The following key is used to store the objects in the page attributes.
   */
  public static final ScopeEE.Request.Attribute<PageAttributes> REQUEST_ATTRIBUTE =
      ScopeEE.REQUEST.attribute("pageAttributes");

  public static class Link {

    private final String rel;
    private final String href;
    private final String type;

    Link(String rel, String href, String type) {
      this.rel = Strings.trimNullIfEmpty(rel);
      this.href = Strings.nullIfEmpty(href);
      this.type = Strings.trimNullIfEmpty(type);
    }

    public String getRel() {
      return rel;
    }

    /**
     * Gets the already URL-encoded href.
     * TODO: Not URL encoded here.
     */
    public String getHref() {
      return href;
    }

    public String getType() {
      return type;
    }
  }

  private String path;
  private String keywords;
  private String description;
  private String author;
  private String authorHref;
  private String copyright;
  private List<Meta> metas;
  private List<Meta> unmodifiableMetas;
  private List<Link> links;
  private List<Link> unmodifiableLinks;
  private String title;
  private String navImageAlt;
  private List<Parent> parents;
  private List<Child> children;
  private String layout;
  private Formtype formtype;
  private String onload;

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getAuthorHref() {
    return authorHref;
  }

  public void setAuthorHref(String authorHref) {
    this.authorHref = authorHref;
  }

  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  @SuppressWarnings("ReturnOfCollectionOrArrayField") // Returning unmodifiable
  public List<Meta> getMetas() {
    if (metas == null) {
      return Collections.emptyList();
    }
    if (unmodifiableMetas == null) {
      unmodifiableMetas = Collections.unmodifiableList(metas);
    }
    return unmodifiableMetas;
  }

  public void addMeta(Meta meta) {
    if (metas == null) {
      metas = new ArrayList<>();
    }
    metas.add(meta);
  }

  /**
   * Gets an optional set of additional links to include for this page
   * in the order they should be added.
   *
   * <p>Please note, that any links to stylesheets here are never optimized.  Please
   * prefer the {@link com.aoapps.web.resources.servlet.RegistryEE.Page page-scope web resource registry}.</p>
   */
  @SuppressWarnings("ReturnOfCollectionOrArrayField") // Returning unmodifiable
  public List<Link> getLinks() {
    if (links == null) {
      return Collections.emptyList();
    }
    if (unmodifiableLinks == null) {
      unmodifiableLinks = Collections.unmodifiableList(links);
    }
    return unmodifiableLinks;
  }

  /**
   * Adds an additional link to include for this page
   * in the order they should be added.
   *
   * <p>Please note, that any links to stylesheets here are never optimized.  Please
   * prefer the {@link com.aoapps.web.resources.servlet.RegistryEE.Page page-scope web resource registry}.</p>
   */
  public void addLink(String rel, String href, String type) {
    if (links == null) {
      links = new ArrayList<>();
    }
    links.add(new Link(rel, href, type));
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getNavImageAlt() {
    String myNavImageAlt = this.navImageAlt;
    if (myNavImageAlt == null || myNavImageAlt.length() == 0) {
      myNavImageAlt = this.title;
    }
    return myNavImageAlt;
  }

  public void setNavImageAlt(String navImageAlt) {
    this.navImageAlt = navImageAlt;
  }

  public List<Parent> getParents() {
    if (parents == null) {
      List<Parent> emptyList = Collections.emptyList();
      return emptyList;
    }
    return parents;
  }

  public void addParent(Parent parent) {
    if (parents == null) {
      parents = new ArrayList<>();
    }
    parents.add(parent);
  }

  /**
   * Gets the direct children of this page.
   */
  public List<Child> getChildren() {
    if (children == null) {
      List<Child> emptyList = Collections.emptyList();
      return emptyList;
    }
    return children;
  }

  public void addChild(Child child) {
    if (children == null) {
      children = new ArrayList<>();
    }
    children.add(child);
  }

  public String getLayout() {
    return layout;
  }

  public void setLayout(String layout) {
    if (layout.equals(LAYOUT_NORMAL) || layout.equals(LAYOUT_MINIMAL)) {
      this.layout = layout;
    } else {
      throw new LocalizedIllegalArgumentException(PACKAGE_RESOURCES, "skintags.PageAttributes.setLayout.invalid");
    }
  }

  public Formtype getFormtype() {
    return formtype;
  }

  /**
   * @param  formtype  Includes appropriate content in the head based on the type of form.
   */
  public void setFormtype(Formtype formtype) {
    this.formtype = formtype;
  }

  public String getOnload() {
    return onload;
  }

  public void setOnload(String onload) {
    this.onload = onload;
  }
}
