/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2013, 2015, 2016, 2018, 2019, 2020, 2021, 2022  AO Industries, Inc.
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

import com.aoapps.encoding.Doctype;
import com.aoapps.encoding.Serialization;
import static com.aoapps.encoding.TextInJavaScriptEncoder.textInJavascriptEncoder;
import static com.aoapps.encoding.TextInXhtmlAttributeEncoder.encodeTextInXhtmlAttribute;
import static com.aoapps.encoding.TextInXhtmlEncoder.textInXhtmlEncoder;
import com.aoapps.encoding.servlet.SerializationEE;
import com.aoapps.hodgepodge.i18n.EditableResourceBundle;
import com.aoapps.html.any.AnyLINK;
import com.aoapps.html.any.AnyMETA;
import com.aoapps.html.any.attributes.Enum.Method;
import com.aoapps.html.servlet.BODY;
import com.aoapps.html.servlet.BODY_c;
import com.aoapps.html.servlet.ContentEE;
import com.aoapps.html.servlet.DocumentEE;
import com.aoapps.html.servlet.FlowContent;
import com.aoapps.html.servlet.HTML_c;
import com.aoapps.html.servlet.ScriptSupportingContent;
import com.aoapps.html.servlet.TABLE_c;
import com.aoapps.html.servlet.TBODY_c;
import com.aoapps.html.servlet.TD_c;
import com.aoapps.html.servlet.TR_c;
import com.aoapps.html.servlet.Union_Metadata_Phrasing;
import com.aoapps.html.util.GoogleAnalytics;
import com.aoapps.html.util.HeadUtil;
import com.aoapps.html.util.ImagePreload;
import static com.aoapps.lang.Strings.trimNullIfEmpty;
import com.aoapps.net.AnyURI;
import com.aoapps.net.EmptyURIParameters;
import com.aoapps.net.URIEncoder;
import com.aoapps.servlet.lastmodified.AddLastModified;
import com.aoapps.servlet.lastmodified.LastModifiedUtil;
import com.aoapps.style.AoStyle;
import static com.aoapps.taglib.AttributeUtils.appendWidthStyle;
import static com.aoapps.taglib.AttributeUtils.getWidthStyle;
import com.aoapps.web.resources.registry.Group;
import com.aoapps.web.resources.registry.Registry;
import com.aoapps.web.resources.registry.Style;
import com.aoapps.web.resources.renderer.Renderer;
import com.aoapps.web.resources.servlet.RegistryEE;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.reseller.Brand;
import com.aoindustries.web.struts.skintags.Child;
import com.aoindustries.web.struts.skintags.PageAttributes;
import com.aoindustries.web.struts.skintags.Parent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

/**
 * The skin for the home page of the site.
 *
 * @author  AO Industries, Inc.
 */
public class TextSkin extends Skin {

  // Matches TextOnlyLayout.NAME
  public static final String NAME = "Text";

  /**
   * The name of the {@linkplain com.aoapps.web.resources.servlet.RegistryEE.Application application-scope}
   * group that will be used for text skin web resources.
   */
  public static final Group.Name RESOURCE_GROUP = new Group.Name(TextSkin.class.getName());

  public static final Style TEXTSKIN_CSS = new Style("/textskin/textskin.css");

  static final com.aoapps.lang.i18n.Resources RESOURCES = com.aoapps.lang.i18n.Resources.getResources(ResourceBundle::getBundle, TextSkin.class);

  @WebListener
  public static class Initializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
      RegistryEE.Application.get(event.getServletContext())
        .getGroup(RESOURCE_GROUP)
        .styles
        .add(
          AoStyle.AO_STYLE,
          TEXTSKIN_CSS
        );
    }
    @Override
    public void contextDestroyed(ServletContextEvent event) {
      // Do nothing
    }
  }

  /**
   * Reuse a single instance, not synchronized because if more than one is
   * made no big deal.
   */
  private static final TextSkin instance = new TextSkin();
  public static TextSkin getInstance() {
    return instance;
  }

  protected TextSkin() {
    // Do nothing
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDisplay(HttpServletRequest req, HttpServletResponse resp) throws JspException {
    return RESOURCES.getMessage(resp.getLocale(), "name");
  }

  /**
   * Print the logo for the top left part of the page.
   */
  public <__ extends FlowContent<__>> void printLogo(HttpServletRequest req, HttpServletResponse resp, PageAttributes pageAttributes, __ td, String urlBase) throws JspException, IOException {
    // Print no logo by default
  }

  /**
   * Prints the search form, if any exists.
   */
  public <__ extends FlowContent<__>> void printSearch(HttpServletRequest req, HttpServletResponse resp, __ div) throws JspException, IOException {
    // Do nothing
  }

  /**
   * Prints the common pages area, which is at the top of the site.
   */
  public <__ extends FlowContent<__>> void printCommonPages(HttpServletRequest req, HttpServletResponse resp, __ td) throws JspException, IOException {
    // Do nothing
  }

  /**
   * Prints the lines for any JavaScript sources.
   */
  public <__ extends ScriptSupportingContent<__>> void printJavascriptSources(HttpServletRequest req, HttpServletResponse resp, __ head, String urlBase) throws JspException, IOException {
    // Do nothing
  }

  /**
   * Prints the line for the favicon.
   */
  public <__ extends Union_Metadata_Phrasing<__>> void printFavIcon(HttpServletRequest req, HttpServletResponse resp, __ head, String urlBase) throws JspException, IOException {
    // Do nothing
  }

  @Override
  public void configureResources(
    ServletContext servletContext,
    HttpServletRequest req,
    HttpServletResponse resp,
    PageAttributes pageAttributes,
    Registry requestRegistry
  ) {
    super.configureResources(servletContext, req, resp, pageAttributes, requestRegistry);
    requestRegistry.activate(RESOURCE_GROUP);
  }

  @Override
  public <__ extends FlowContent<__>> __ startPage(
    HttpServletRequest req,
    HttpServletResponse resp,
    PageAttributes pageAttributes,
    DocumentEE document
  ) throws JspException, IOException {
    boolean isOkResponseStatus = (resp.getStatus() == HttpServletResponse.SC_OK);
    ServletContext servletContext = req.getServletContext();
    SiteSettings settings = SiteSettings.getInstance(servletContext);
    Brand brand;
    try {
      brand = settings.getBrand();
    } catch (SQLException e) {
      throw new JspException(e);
    }
    String trackingId = brand.getAowebStrutsGoogleAnalyticsNewTrackingCode();
    List<Language> languages;
    try {
      languages = settings.getLanguages(req);
    } catch (SQLException e) {
      throw new JspException(e);
    }
    String layout = pageAttributes.getLayout();
    if (!layout.equals(PageAttributes.LAYOUT_NORMAL)) {
      throw new JspException("TODO: Implement layout: "+layout);
    }
    Locale locale = resp.getLocale();
    String urlBase = getUrlBase(req);
    final String path; {
      String path_ = pageAttributes.getPath();
      if (path_ == null) {
        throw new NullPointerException("pageAttributes.path is null");
      }
      if (path_.startsWith("/")) {
        path_ = path_.substring(1);
      }
      path = path_;
    }
    final String fullPath = urlBase + path;
    List<Skin> skins = settings.getSkins();
    List<Parent> parents = pageAttributes.getParents();
    AOServConnector aoConn = AuthenticatedAction.getAoConn(req, resp);
    // Do this before getting session, since session may be conditionally created by path in LoginAction
    String target = LoginAction.addTarget(req::getSession, fullPath);
    HttpSession session = req.getSession(false);
    //if (session == null) {
    //  session = req.getSession(false); // Get again, just in case of authentication
    //}
    // Write doctype
    document.xmlDeclaration();
    document.doctype();
    // Write <html>
    HTML_c<DocumentEE> html_c = document.html().lang()._c();
    html_c.head__(head -> {
      // If this is not the default skin, then robots noindex
      boolean robotsMetaUsed = false;
      if (!isOkResponseStatus || !getName().equals(skins.get(0).getName())) {
        head.meta().name(AnyMETA.Name.ROBOTS).content("noindex, nofollow").__();
        robotsMetaUsed = true;
      }
      HeadUtil.standardMeta(head, resp.getContentType());
      Doctype doctype = document.encodingContext.getDoctype();
      if (doctype == Doctype.HTML5) {
        GoogleAnalytics.writeGlobalSiteTag(head, trackingId);
      } else {
        GoogleAnalytics.writeAnalyticsJs(head, trackingId);
      }
      // Mobile support
      head
        .meta().name(AnyMETA.Name.VIEWPORT).content("width=device-width, initial-scale=1.0").__()
        // TODO: This is probably only appropriate for single-page applications!
        //       See https://medium.com/@firt/dont-use-ios-web-app-meta-tag-irresponsibly-in-your-progressive-web-apps-85d70f4438cb
        .meta().name(AnyMETA.Name.APPLE_MOBILE_WEB_APP_CAPABLE).content("yes").__()
        .meta().name(AnyMETA.Name.APPLE_MOBILE_WEB_APP_STATUS_BAR_STYLE).content("black").__();
      // Authors
      // TODO: 3.0.0: dcterms copyright
      String author = pageAttributes.getAuthor();
      if (author != null && !(author = author.trim()).isEmpty()) {
        head.meta().name(AnyMETA.Name.AUTHOR).content(author).__();
      }
      String authorHref = pageAttributes.getAuthorHref();
      if (authorHref != null && !(authorHref = authorHref.trim()).isEmpty()) {
        head.link(AnyLINK.Rel.AUTHOR).href(
          // TODO: RFC 3986-only always?
          resp.encodeURL(
            URIEncoder.encodeURI(authorHref) // TODO: Conditionally convert from context-relative paths
          )
        ).__();
      }
      head.title__(pageAttributes.getTitle());
      String description = pageAttributes.getDescription();
      if (description != null && !(description = description.trim()).isEmpty()) {
        head.meta().name(AnyMETA.Name.DESCRIPTION).content(description).__();
      }
      String keywords = pageAttributes.getKeywords();
      if (keywords != null && !(keywords = keywords.trim()).isEmpty()) {
        head.meta().name(AnyMETA.Name.KEYWORDS).content(keywords).__();
      }
      // TODO: 3.0.0: Review HTML 4/HTML 5 differences from here
      String copyright = pageAttributes.getCopyright();
      if (copyright != null && !(copyright = copyright.trim()).isEmpty()) {
        // TODO: 3.0.0: Dublin Core: https://stackoverflow.com/questions/6665312/is-the-copyright-meta-tag-valid-in-html5
        head.meta().name("copyright").content(copyright).__();
      }
      // If this is an authenticated page, redirect to session timeout after one hour
      if (isOkResponseStatus && aoConn != null && session != null) {
        head.meta().httpEquiv(AnyMETA.HttpEquiv.REFRESH).content(content -> {
          content.write(Integer.toString(Math.max(60, session.getMaxInactiveInterval() - 60)));
          content.write(";URL=");
          content.write(
            resp.encodeRedirectURL(
              URIEncoder.encodeURI(
                target == null ? (
                  urlBase
                  + "session-timeout.do"
                ) : (
                  urlBase
                  + "session-timeout.do?target="
                  + URIEncoder.encodeURIComponent(target)
                )
              )
            )
          );
        }).__();
      }
      for (com.aoindustries.web.struts.skintags.Meta meta : pageAttributes.getMetas()) {
        // Skip robots if not on default skin
        boolean isRobots = meta.getName().equalsIgnoreCase(AnyMETA.Name.ROBOTS.getValue());
        if (!robotsMetaUsed || !isRobots) {
          head.meta().name(meta.getName()).content(meta.getContent()).__();
          if (isRobots) {
            robotsMetaUsed = true;
          }
        }
      }
      if (isOkResponseStatus) {
        String googleVerify = brand.getAowebStrutsGoogleVerifyContent();
        if (googleVerify != null) {
          head.meta().name("verify-v1").content(googleVerify).__();
        }
      }
      printAlternativeLinks(req, resp, head, fullPath, languages);

      // Configure skin resources
      Registry requestRegistry = RegistryEE.Request.get(servletContext, req);
      configureResources(servletContext, req, resp, pageAttributes, requestRegistry);
      // Configure page resources
      Registry pageRegistry = RegistryEE.Page.get(req);
      if (pageRegistry == null) {
        // Create a new page-scope registry
        pageRegistry = new Registry();
        RegistryEE.Page.set(req, pageRegistry);
      }
      // Render links
      Renderer.get(servletContext).renderStyles(
        req,
        resp,
        head,
        true, // registeredActivations
        null, // No additional activations
        requestRegistry, // request-scope
        RegistryEE.Session.get(req.getSession(false)), // session-scope
        pageRegistry
      );
      defaultPrintLinks(servletContext, req, resp, head, pageAttributes);
      printJavascriptSources(req, resp, head, urlBase);
      Formtype formtype = pageAttributes.getFormtype();
      if (formtype != null) {
        formtype.doHead(servletContext, req, resp, head);
      }
      printFavIcon(req, resp, head, urlBase);
      // TODO: Canonical?
    });
    BODY<HTML_c<DocumentEE>> body = html_c.body();
    String onload = pageAttributes.getOnload();
    if (onload != null && !(onload = onload.trim()).isEmpty()) {
      body.onload(onload);
    }
    BODY_c<HTML_c<DocumentEE>> body_c = body._c();
    TD_c<TR_c<TBODY_c<TABLE_c<BODY_c<HTML_c<DocumentEE>>>>>> td_c = body_c.table().cellspacing(10).cellpadding(0)._c()
      .tbody_c()
        .tr_c()
          .td().style("vertical-align:top").__(td -> {
            printLogo(req, resp, pageAttributes, td, urlBase);
            td.hr__();
            boolean isLoggedIn = aoConn != null;
            if (isLoggedIn) {
              td.text(RESOURCES.getMessage(locale, "logoutPrompt"))
              .form()
                .style("display:inline")
                .id("logout_form")
                .method(Method.Value.POST)
                .action(resp.encodeURL(URIEncoder.encodeURI(urlBase + "logout.do")))
              .__(form -> form
                .div().style("display:inline").__(div -> {
                  if (target != null) {
                    div.input().hidden().name("target").value(target).__();
                  }
                  // TODO: Variant that takes ResourceBundle?
                  div.input().submit__(RESOURCES.getMessage(locale, "logoutButtonLabel"));
                })
              );
            } else {
              td.text(RESOURCES.getMessage(locale, "loginPrompt"))
              .form()
                .style("display:inline")
                .id("login_form")
                .method(Method.Value.POST)
                .action(resp.encodeURL(URIEncoder.encodeURI(urlBase + "login.do")))
              .__(form -> form
                .div().style("display:inline").__(div -> {
                  // Only include the target when they are in the /clientarea/ part of the site
                  if (path.startsWith("clientarea/") && target != null) {
                    div.input().hidden().name("target").value(target).__();
                  }
                  div.input().submit__(RESOURCES.getMessage(locale, "loginButtonLabel"));
                })
              );
            }
            td.hr__()
            .div().style("white-space:nowrap").__(div -> {
              if (skins.size() > 1) {
                div.script().out(script -> {
                  script.append("function selectLayout(layout) {\n");
                  for (Skin skin : skins) {
                    script.append("  if (layout == ").text(skin.getName()).append(") window.top.location.href=").text(
                      resp.encodeURL(
                        new AnyURI(fullPath)
                          .addEncodedParameter(Constants.LAYOUT.getName(), URIEncoder.encodeURIComponent(skin.getName()))
                          .toASCIIString()
                      )
                    ).append(";\n");
                  }
                  script.append('}');
                }).__()
                .form().action("").style("display:inline").__(form -> form
                  .div().style("display:inline").__(div2 -> div2
                    .text(RESOURCES.getMessage(locale, "layoutPrompt"))
                    .select().name("layout_selector").onchange("selectLayout(this.form.layout_selector.options[this.form.layout_selector.selectedIndex].value);").__(select -> {
                      for (Skin skin : skins) {
                        select.option()
                          .value(skin.getName())
                          .selected(getName().equals(skin.getName()))
                        .__(skin.getDisplay(req, resp));
                      }
                    })
                  )
                ).br__();
              }
              if (languages.size() > 1) {
                for (Language language : languages) {
                  AnyURI uri = language.getUri();
                  if (language.getCode().equalsIgnoreCase(locale.getLanguage())) {
                    div.nbsp().a()
                      .href(
                        resp.encodeURL(
                          URIEncoder.encodeURI(
                            (
                              uri == null
                              ? new AnyURI(fullPath).addEncodedParameter(Constants.LANGUAGE, URIEncoder.encodeURIComponent(language.getCode()))
                              : uri
                            ).toASCIIString()
                          )
                        )
                      )
                      .hreflang(language.getCode())
                    .__(a -> a
                      .img()
                        .src(
                          resp.encodeURL(
                            URIEncoder.encodeURI(
                              urlBase + language.getFlagOnSrc(req, locale)
                            )
                          )
                        ).style("border:1px solid; vertical-align:bottom")
                        .width(language.getFlagWidth(req, locale))
                        .height(language.getFlagHeight(req, locale))
                        .alt(language.getDisplay(req, locale))
                      .__()
                    );
                  } else {
                    div.nbsp().a()
                      .href(
                        resp.encodeURL(
                          URIEncoder.encodeURI(
                            (
                              uri == null
                              ? new AnyURI(fullPath).addEncodedParameter(Constants.LANGUAGE, URIEncoder.encodeURIComponent(language.getCode()))
                              : uri
                            ).toASCIIString()
                          )
                        )
                      )
                      .hreflang(language.getCode())
                      .onmouseover(onmouseover -> onmouseover
                        .append("document.images[").text("flagSelector_" + language.getCode()).append("].src=").text(
                          resp.encodeURL(
                            URIEncoder.encodeURI(
                              urlBase
                              + language.getFlagOnSrc(req, locale)
                            )
                          )
                        ).append(';')
                      )
                      .onmouseout(onmouseout -> onmouseout
                        .append("document.images[").text("flagSelector_" + language.getCode()).append("].src=").text(
                          resp.encodeURL(
                            URIEncoder.encodeURI(
                              urlBase
                              + language.getFlagOffSrc(req, locale)
                            )
                          )
                        ).append(';')
                      )
                    .__(a -> a
                      .img()
                        .src(
                          resp.encodeURL(
                            URIEncoder.encodeURI(
                              urlBase
                              + language.getFlagOffSrc(req, locale)
                            )
                          )
                        ).id("flagSelector_" + language.getCode())
                        .style("border:1px solid; vertical-align:bottom")
                        .width(language.getFlagWidth(req, locale))
                        .height(language.getFlagHeight(req, locale))
                        .alt(language.getDisplay(req, locale))
                      .__()
                    );
                    ImagePreload.writeImagePreloadScript(
                      resp.encodeURL(
                        URIEncoder.encodeURI(
                          urlBase + language.getFlagOnSrc(req, locale)
                        )
                      ),
                      div
                    );
                  }
                }
                div.br__();
              }
              printSearch(req, resp, div);
            })
            .hr__()
            // Display the parents
            .b__(RESOURCES.getMessage(locale, "currentLocation")).br__()
            .div().style("white-space:nowrap").__(div -> {
              for (Parent parent : parents) {
                String navAlt = parent.getNavImageAlt();
                String parentPath = parent.getPath();
                if (parentPath.startsWith("/")) {
                  parentPath = parentPath.substring(1);
                }
                div.a(
                  resp.encodeURL(
                    URIEncoder.encodeURI(
                      urlBase
                      + URIEncoder.encodeURI(parentPath)
                    )
                  )
                ).__(navAlt).br__();
              }
              // Always include the current page in the current location area
              div.a(resp.encodeURL(URIEncoder.encodeURI(fullPath))).__(pageAttributes.getNavImageAlt()).br__();
            })
            .hr__()
            // Related Pages
            .b__(RESOURCES.getMessage(locale, "relatedPages")).br__()
            .div().style("white-space:nowrap").__(div -> {
              List<Child> related = pageAttributes.getChildren();
              if (related.isEmpty() && !parents.isEmpty()) {
                related = parents.get(parents.size() - 1).getChildren();
              }
              for (Child tpage : related) {
                String navAlt = tpage.getNavImageAlt();
                String siblingPath = tpage.getPath();
                if (siblingPath.startsWith("/")) {
                  siblingPath = siblingPath.substring(1);
                }
                div.a(
                  resp.encodeURL(
                    URIEncoder.encodeURI(
                      urlBase
                      + URIEncoder.encodeURI(siblingPath)
                    )
                  )
                ).__(navAlt).br__();
              }
            })
            .hr__();
            printBelowRelatedPages(req, td);
          })
          .td().style("vertical-align:top")._c();
            printCommonPages(req, resp, td_c);
    @SuppressWarnings("unchecked") __ flow = (__)td_c;
    return flow;
  }

  public static <__ extends Union_Metadata_Phrasing<__>> void defaultPrintLinks(ServletContext servletContext, HttpServletRequest req, HttpServletResponse resp, __ head, PageAttributes pageAttributes) throws IOException {
    for (PageAttributes.Link link : pageAttributes.getLinks()) {
      String href = link.getHref();
      String rel = link.getRel();
      head.link()
        .rel(rel)
        .href(href == null ? null :
          LastModifiedUtil.buildURL(
            servletContext,
            req,
            resp,
            href,
            EmptyURIParameters.getInstance(),
            AddLastModified.AUTO,
            false,
            // TODO: Support canonical flag on link
            AnyLINK.Rel.CANONICAL.toString().equalsIgnoreCase(rel)
          )
        )
        .type(link.getType())
      .__();
    }
  }

  @Override
  public void endPage(
    HttpServletRequest req,
    HttpServletResponse resp,
    PageAttributes pageAttributes,
    FlowContent<?> flow
  ) throws JspException, IOException {
    @SuppressWarnings("unchecked")
    TD_c<TR_c<TBODY_c<TABLE_c<BODY_c<HTML_c<DocumentEE>>>>>> td_c = (TD_c)flow;
    BODY_c<HTML_c<DocumentEE>> body_c = td_c
              .__()
            .__()
          .__()
        .__();
        // TODO: SemanticCMS component for this, also make sure in other layouts and skins
        EditableResourceBundle.printEditableResourceBundleLookups(
          textInJavascriptEncoder,
          textInXhtmlEncoder,
          body_c.getRawUnsafe(),
          SerializationEE.get(req.getServletContext(), req) == Serialization.XML,
          4,
          true
        );
    DocumentEE document = body_c
      .__()
    .__();
    assert document != null : "Is fully closed back to DocumentEE";
  }

  @Override
  public <
    PC extends FlowContent<PC>,
    __ extends ContentEE<__>
  > __ startContent(
    HttpServletRequest req,
    HttpServletResponse resp,
    PageAttributes pageAttributes,
    PC pc,
    int[] contentColumnSpans,
    String width
  ) throws JspException, IOException {
    width = trimNullIfEmpty(width);
    final int totalColumns;
    {
      int totalColumns_ = 0;
      for (int c = 0; c < contentColumnSpans.length; c++) {
        if (c > 0) {
          totalColumns_++;
        }
        totalColumns_ += contentColumnSpans[c];
      }
      totalColumns = totalColumns_;
    }
    TBODY_c<TABLE_c<PC>> tbody = pc.table()
      .clazz("ao-packed")
      .style(getWidthStyle(width))
    ._c()
      .thead__(thead -> thead
        .tr__(tr -> tr
          .td().colspan(totalColumns).__(td -> td
            .hr__()
          )
        )
      )
      .tbody_c();
    @SuppressWarnings("unchecked")
    __ content = (__)tbody;
    return content;
  }

  @Override
  public void contentTitle(
    HttpServletRequest req,
    HttpServletResponse resp,
    ContentEE<?> content,
    String title,
    int contentColumns
  ) throws JspException, IOException {
    FlowContent<?> contentLine = startContentLine(req, resp, content, contentColumns, "center", null); {
      contentLine.h1__(title);
    } endContentLine(req, resp, contentLine);
  }

  @Override
  public <__ extends FlowContent<__>> __ startContentLine(
    HttpServletRequest req,
    HttpServletResponse resp,
    ContentEE<?> content,
    int colspan,
    String align,
    String width
  ) throws JspException, IOException {
    // Lying about "DocumentEE" here, but it makes the compiler happy and is otherwise irrelevant
    @SuppressWarnings("unchecked")
    TBODY_c<TABLE_c<DocumentEE>> tbody = (TBODY_c)content;
    align = trimNullIfEmpty(align);
    width = trimNullIfEmpty(width);
    TD_c<TR_c<TBODY_c<TABLE_c<DocumentEE>>>> td = tbody
      .tr_c()
        .td()
          .style(
            align == null ? null : "text-align:" + align,
            getWidthStyle(width),
            "vertical-align:top"
          )
          .colspan(colspan)
        ._c();
    @SuppressWarnings("unchecked")
    __ contentLine = (__)td;
    return contentLine;
  }

  @Override
  public <__ extends FlowContent<__>> __ contentVerticalDivider(
    HttpServletRequest req,
    HttpServletResponse resp,
    FlowContent<?> contentLine,
    boolean visible,
    int colspan,
    int rowspan,
    String align,
    String width
  ) throws JspException, IOException {
    // Lying about "DocumentEE" here, but it makes the compiler happy and is otherwise irrelevant
    @SuppressWarnings("unchecked")
    TD_c<TR_c<TBODY_c<TABLE_c<DocumentEE>>>> td = (TD_c)contentLine;
    align = trimNullIfEmpty(align);
    width = trimNullIfEmpty(width);
    TR_c<TBODY_c<TABLE_c<DocumentEE>>> tr = td.__();
    if (visible) {
      tr.td__('\u00A0');
    }
    TD_c<TR_c<TBODY_c<TABLE_c<DocumentEE>>>> newTd = tr.td()
      .style(
        align == null ? null : "text-align:" + align,
        getWidthStyle(width),
        "vertical-align:top"
      )
      .colspan(colspan)
      .rowspan(rowspan)
    ._c();
    @SuppressWarnings("unchecked")
    __ newContentLine = (__)newTd;
    return newContentLine;
  }

  @Override
  public void endContentLine(
    HttpServletRequest req,
    HttpServletResponse resp,
    FlowContent<?> contentLine,
    int rowspan,
    boolean endsInternal
  ) throws JspException, IOException {
    // Lying about "DocumentEE" here, but it makes the compiler happy and is otherwise irrelevant
    @SuppressWarnings("unchecked")
    TD_c<TR_c<TBODY_c<TABLE_c<DocumentEE>>>> td = (TD_c)contentLine;
    TABLE_c<DocumentEE> table = td
        .__()
      .__()
    .__();
    assert table != null : "Is fully closed back to TABLE_c";
  }

  @Override
  public void contentHorizontalDivider(
    HttpServletRequest req,
    HttpServletResponse resp,
    ContentEE<?> content,
    int[] colspansAndDirections,
    boolean endsInternal
  ) throws JspException, IOException {
    // Lying about "DocumentEE" here, but it makes the compiler happy and is otherwise irrelevant
    @SuppressWarnings("unchecked")
    TBODY_c<TABLE_c<DocumentEE>> tbody = (TBODY_c)content;
    tbody.tr__(tr -> {
      for (int c = 0; c < colspansAndDirections.length; c += 2) {
        if (c > 0) {
          int direction = colspansAndDirections[c - 1];
          switch (direction) {
            case UP:
              tr.td__('\u00A0');
              break;
            case DOWN:
              tr.td__('\u00A0');
              break;
            case UP_AND_DOWN:
              tr.td__('\u00A0');
              break;
            default: throw new IllegalArgumentException("Unknown direction: " + direction);
          }
        }
        int colspan = colspansAndDirections[c];
        tr.td()
          .colspan(colspan)
        .__(td -> td
          .hr__()
        );
      }
    });
  }

  @Override
  public void endContent(
    HttpServletRequest req,
    HttpServletResponse resp,
    PageAttributes pageAttributes,
    ContentEE<?> content,
    int[] contentColumnSpans
  ) throws JspException, IOException {
    // Lying about "DocumentEE" here, but it makes the compiler happy and is otherwise irrelevant
    @SuppressWarnings("unchecked")
    TBODY_c<TABLE_c<DocumentEE>> tbody = (TBODY_c)content;
    final int totalColumns;
    {
      int totalColumns_ = 0;
      for (int c = 0; c < contentColumnSpans.length; c++) {
        if (c > 0) {
          totalColumns_ += 1;
        }
        totalColumns_ += contentColumnSpans[c];
      }
      totalColumns = totalColumns_;
    }
    tbody.tr__(tr -> tr
      .td()
        .colspan(totalColumns)
      .__(td -> td
        .hr__()
      )
    );
    TABLE_c<DocumentEE> table = tbody.__();
    String copyright = pageAttributes.getCopyright();
    if (copyright != null) {
      copyright = copyright.trim();
    }
    if (copyright != null && !copyright.isEmpty()) {
      String copyright_ = copyright;
      table.tfoot__(tfoot -> tfoot
        .tr__(tr -> tr
          .td()
            .colspan(totalColumns)
            .style("text-align:center", "font-size:x-small")
          .__(copyright_)
        )
      );
    }
    table.__();
  }

  @Override
  public <
    PC extends FlowContent<PC>,
    __ extends FlowContent<__>
  > __ startLightArea(
    HttpServletRequest req,
    HttpServletResponse resp,
    PC pc,
    String align,
    String width,
    boolean nowrap
  ) throws JspException, IOException {
    align = trimNullIfEmpty(align);
    TD_c<TR_c<TBODY_c<TABLE_c<PC>>>> td = pc.table()
      .clazz("ao-packed")
      .style("border:5px outset #a0a0a0", getWidthStyle(width))
    ._c()
      .tbody_c()
        .tr_c()
          .td()
            .clazz("aoLightRow")
            .style(
              "padding:4px",
              (align != null) ? ("text-align:" + align) : null,
              nowrap ? "white-space:nowrap" : null
            )
          ._c();
    @SuppressWarnings("unchecked")
    __ lightArea = (__)td;
    return lightArea;
  }

  @Override
  public void endLightArea(
    HttpServletRequest req,
    HttpServletResponse resp,
    FlowContent<?> lightArea
  ) throws JspException, IOException {
    // Lying about "DocumentEE" here, but it makes the compiler happy and is otherwise irrelevant
    @SuppressWarnings("unchecked")
    TD_c<TR_c<TBODY_c<TABLE_c<DocumentEE>>>> td = (TD_c)lightArea;
    TABLE_c<?> table = td
        .__()
      .__()
    .__();
    assert table != null : "Is fully closed back to TABLE_c";
  }

  @Override
  public <
    PC extends FlowContent<PC>,
    __ extends FlowContent<__>
  > __ startWhiteArea(
    HttpServletRequest req,
    HttpServletResponse resp,
    PC pc,
    String align,
    String width,
    boolean nowrap
  ) throws JspException, IOException {
    align = trimNullIfEmpty(align);
    TD_c<TR_c<TBODY_c<TABLE_c<PC>>>> td = pc.table()
      .clazz("ao-packed")
      .style("border:5px outset #a0a0a0", getWidthStyle(width))
    ._c()
      .tbody_c()
        .tr_c()
          .td()
            .clazz("aoWhiteRow")
            .style(
              "padding:4px",
              (align != null) ? ("text-align:" + align) : null,
              nowrap ? "white-space:nowrap" : null
            )
          ._c();
    @SuppressWarnings("unchecked")
    __ whiteArea = (__)td;
    return whiteArea;
  }

  @Override
  public void endWhiteArea(
    HttpServletRequest req,
    HttpServletResponse resp,
    FlowContent<?> whiteArea
  ) throws JspException, IOException {
    // Lying about "DocumentEE" here, but it makes the compiler happy and is otherwise irrelevant
    @SuppressWarnings("unchecked")
    TD_c<TR_c<TBODY_c<TABLE_c<DocumentEE>>>> td = (TD_c)whiteArea;
    TABLE_c<DocumentEE> table = td
        .__()
      .__()
    .__();
    assert table != null : "Is fully closed back to TABLE_c";
  }

  @Override
  public void printAutoIndex(
    HttpServletRequest req,
    HttpServletResponse resp,
    PageAttributes pageAttributes,
    DocumentEE document
  ) throws JspException, IOException {
    String urlBase = getUrlBase(req);
    //Locale locale = resp.getLocale();

    document.unsafe("<table cellpadding=\"0\" cellspacing=\"10\">\n");
    List<Child> siblings = pageAttributes.getChildren();
    if (siblings.isEmpty()) {
      List<Parent> parents = pageAttributes.getParents();
      if (!parents.isEmpty()) {
        siblings = parents.get(parents.size()-1).getChildren();
      }
    }
    for (Child sibling : siblings) {
      String navAlt=sibling.getNavImageAlt();
      String siblingPath = sibling.getPath();
      if (siblingPath.startsWith("/")) {
        siblingPath=siblingPath.substring(1);
      }

      document.unsafe("  <tr>\n"
      + "    <td style=\"white-space:nowrap\"><a class=\"aoLightLink\" href=\"");
      encodeTextInXhtmlAttribute(
        resp.encodeURL(
          URIEncoder.encodeURI(
            urlBase
            + URIEncoder.encodeURI(siblingPath)
          )
        ),
        document.getRawUnsafe()
      );
      document.unsafe("\">").text(navAlt).unsafe("</a></td>\n"
      + "    <td style=\"width:12px; white-space:nowrap\">\u00A0</td>\n"
      + "    <td style=\"white-space:nowrap\">");
      String description = sibling.getDescription();
      if (description != null && !(description = description.trim()).isEmpty()) {
        document.text(description);
      } else {
        String title = sibling.getTitle();
        if (title != null && !(title = title.trim()).isEmpty()) {
          document.text(title);
        } else {
          document.nbsp();
        }
      }
      document.unsafe("</td>\n"
      + "  </tr>\n");
    }
    document.unsafe("</table>\n");
  }

  /**
   * Prints content below the related pages area on the left.
   */
  public <__ extends FlowContent<__>> void printBelowRelatedPages(HttpServletRequest req, __ td) throws JspException, IOException {
    // Do nothing
  }

  /**
   * Begins a popup group.
   *
   * @see  #defaultBeginPopupGroup(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.DocumentEE, long)
   */
  @Override
  public void beginPopupGroup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId) throws JspException, IOException {
    defaultBeginPopupGroup(req, resp, document, groupId);
  }

  /**
   * Default implementation of beginPopupGroup.
   */
  public static void defaultBeginPopupGroup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId) throws IOException {
    String groupIdStr = Long.toString(groupId);
    document.script().out(script -> script.indent()
      .append("var popupGroupTimer").append(groupIdStr).append("=null;").nli()
      .append("var popupGroupAuto").append(groupIdStr).append("=null;").nli()
      .append("function popupGroupHideAllDetails").append(groupIdStr).append("() {").incDepth().nli()
        .append("var spanElements = document.getElementsByTagName ? document.getElementsByTagName(\"div\") : document.all.tags(\"div\");").nli()
        .append("for (var c = 0; c < spanElements.length; c++) {").incDepth().nli()
          .append("if (spanElements[c].id.indexOf(\"aoPopup_").append(groupIdStr).append("_\") == 0) {").incDepth().nli()
            .append("spanElements[c].style.visibility=\"hidden\";")
          .decDepth().nli().append('}')
        .decDepth().nli().append('}')
      .decDepth().nli().append('}').nli()
      .append("function popupGroupToggleDetails").append(groupIdStr).append("(popupId) {").incDepth().nli()
        .append("if (popupGroupTimer").append(groupIdStr).append(" != null) {").incDepth().nli()
          .append("clearTimeout(popupGroupTimer").append(groupIdStr).append(");").nli()
        .decDepth().nli().append('}')
        .append("var elemStyle = document.getElementById(\"aoPopup_").append(groupIdStr).append("_\"+popupId).style;").nli()
        .append("if (elemStyle.visibility == \"visible\") {").incDepth().nli()
          .append("elemStyle.visibility=\"hidden\";")
        .decDepth().nli().append("} else {").incDepth().nli()
          .append("popupGroupHideAllDetails").append(groupIdStr).append("();").nli()
          .append("elemStyle.visibility=\"visible\";")
        .decDepth().nli().append('}')
      .decDepth().nli().append('}').nli()
      .append("function popupGroupShowDetails").append(groupIdStr).append("(popupId) {").incDepth().nli()
        .append("if (popupGroupTimer").append(groupIdStr).append(" != null) {\n").incDepth().nli()
          .append("clearTimeout(popupGroupTimer").append(groupIdStr).append(");").nli()
        .decDepth().nli().append('}')
        .append("var elemStyle = document.getElementById(\"aoPopup_").append(groupIdStr).append("_\"+popupId).style;").nli()
        .append("if (elemStyle.visibility != \"visible\") {").incDepth().nli()
          .append("popupGroupHideAllDetails").append(groupIdStr).append("();").nli()
          .append("elemStyle.visibility=\"visible\";")
        .decDepth().nli().append('}')
      .decDepth().nli().append('}')
    ).__();
  }

  /**
   * Ends a popup group.
   *
   * @see  #defaultEndPopupGroup(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.DocumentEE, long)
   */
  @Override
  public void endPopupGroup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId) throws JspException, IOException {
    defaultEndPopupGroup(req, resp, document, groupId);
  }

  /**
   * Default implementation of endPopupGroup.
   */
  public static void defaultEndPopupGroup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId) throws JspException, IOException {
    // Nothing at the popup group end
  }

  /**
   * Begins a popup that is in a popup group.
   *
   * @see  #defaultBeginPopup(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.DocumentEE, long, long, java.lang.String, java.lang.String)
   */
  @Override
  public void beginPopup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId, long popupId, String width) throws JspException, IOException {
    defaultBeginPopup(req, resp, document, groupId, popupId, width, getUrlBase(req));
  }

  /**
   * Default implementation of beginPopup.
   */
  public static void defaultBeginPopup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId, long popupId, String width, String urlBase) throws IOException {
    if (groupId < 0) {
      throw new IllegalArgumentException("groupId < 0: " + groupId);
    }
    final String groupIdStr = Long.toString(groupId);

    if (popupId < 0) {
      throw new IllegalArgumentException("popupId < 0: " + popupId);
    }
    final String popupIdStr = Long.toString(popupId);

    width = trimNullIfEmpty(width);
    Locale locale = resp.getLocale();

    document.unsafe("<div id=\"aoPopupAnchor_").unsafe(groupIdStr).unsafe('_').unsafe(popupIdStr).unsafe("\" class=\"aoPopupAnchor\">")
    .img()
      .clazz("aoPopupAnchorImg")
      .src(
        resp.encodeURL(
          URIEncoder.encodeURI(
            urlBase
            + RESOURCES.getMessage(locale, "popup.src")
          )
        )
      ).alt(RESOURCES.getMessage(locale, "popup.alt"))
      .width(Integer.parseInt(RESOURCES.getMessage(locale, "popup.width")))
      .height(Integer.parseInt(RESOURCES.getMessage(locale, "popup.height")))
      .onmouseover(onmouseover -> onmouseover
        .append("popupGroupTimer")
        .append(groupIdStr)
        .append("=setTimeout(\"popupGroupAuto")
        .append(groupIdStr)
        .append("=true; popupGroupShowDetails")
        .append(groupIdStr)
        .append('(')
        .append(popupIdStr)
        .append(")\", 1000);")
      ).onmouseout(onmouseout -> onmouseout
        .append("if (popupGroupAuto")
        .append(groupIdStr)
        .append(") popupGroupHideAllDetails")
        .append(groupIdStr)
        .append("(); if (popupGroupTimer")
        .append(groupIdStr)
        .append(" != null) clearTimeout(popupGroupTimer")
        .append(groupIdStr)
        .append(");")
      ).onclick(onclick -> onclick
        .append("popupGroupAuto")
        .append(groupIdStr)
        .append("=false; popupGroupToggleDetails")
        .append(groupIdStr)
        .append('(')
        .append(popupIdStr)
        .append(");")
    ).__()
    // Used to be span width=\"100%\"
    .unsafe("    <div id=\"aoPopup_").unsafe(groupIdStr).unsafe('_').unsafe(popupIdStr).unsafe("\" class=\"aoPopupMain\"");
    if (width != null) {
      document.unsafe(" style=\"");
      appendWidthStyle(width, document.getRawUnsafe());
      document.unsafe('"');
    }
    document.unsafe(">\n"
    + "        <table class=\"aoPopupTable ao-packed\">\n"
    + "            <tr>\n"
    + "                <td class=\"aoPopupTL\">")
    .img()
      .src(
        resp.encodeURL(
          URIEncoder.encodeURI(
            urlBase + "textskin/popup_topleft.gif"
          )
        )
      ).width(12).height(12).alt("").__()
    .unsafe("</td>\n"
    + "                <td class=\"aoPopupTop\" style=\"background-image:url(");
    encodeTextInXhtmlAttribute(
      resp.encodeURL(
        URIEncoder.encodeURI(
          urlBase + "textskin/popup_top.gif"
        )
      ),
      document.getRawUnsafe()
    );
    document.unsafe(");\"></td>\n"
    + "                <td class=\"aoPopupTR\">")
    .img()
      .src(
        resp.encodeURL(
          URIEncoder.encodeURI(
            urlBase + "textskin/popup_topright.gif"
          )
        )
      ).width(12).height(12).alt("").__()
    .unsafe("</td>\n"
    + "            </tr>\n"
    + "            <tr>\n"
    + "                <td class=\"aoPopupLeft\" style=\"background-image:url(");
    encodeTextInXhtmlAttribute(
      resp.encodeURL(
        URIEncoder.encodeURI(
          urlBase + "textskin/popup_left.gif"
        )
      ),
      document.getRawUnsafe()
    );
    document.unsafe(");\"></td>\n"
    + "                <td class=\"aoPopupLightRow\">");
  }

  /**
   * Prints a popup close link/image/button for a popup that is part of a popup group.
   *
   * @see  #defaultPrintPopupClose(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.DocumentEE, long, long, java.lang.String)
   */
  @Override
  public void printPopupClose(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId, long popupId) throws JspException, IOException {
    defaultPrintPopupClose(req, resp, document, groupId, popupId, getUrlBase(req));
  }

  /**
   * Default implementation of printPopupClose.
   */
  public static void defaultPrintPopupClose(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId, long popupId, String urlBase) throws IOException {
    Locale locale = resp.getLocale();

    document.img()
      .clazz("aoPopupClose")
      .src(
        resp.encodeURL(
          URIEncoder.encodeURI(
            urlBase + RESOURCES.getMessage(locale, "popupClose.src")
          )
        )
      ).alt(RESOURCES.getMessage(locale, "popupClose.alt"))
      .width(Integer.parseInt(RESOURCES.getMessage(locale, "popupClose.width")))
      .height(Integer.parseInt(RESOURCES.getMessage(locale, "popupClose.height")))
      .onclick("popupGroupHideAllDetails" + groupId + "();")
    .__();
  }

  /**
   * Ends a popup that is in a popup group.
   *
   * @see  #defaultEndPopup(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.DocumentEE, long, long, java.lang.String, java.lang.String)
   */
  @Override
  public void endPopup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId, long popupId, String width) throws JspException, IOException {
    TextSkin.defaultEndPopup(req, resp, document, groupId, popupId, width, getUrlBase(req));
  }

  /**
   * Default implementation of endPopup.
   */
  public static void defaultEndPopup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId, long popupId, String width, String urlBase) throws IOException {
    document.unsafe("</td>\n"
    + "                <td class=\"aoPopupRight\" style=\"background-image:url(");
    encodeTextInXhtmlAttribute(
      resp.encodeURL(
        URIEncoder.encodeURI(
          urlBase + "textskin/popup_right.gif"
        )
      ),
      document.getRawUnsafe()
    );
    document.unsafe(");\"></td>\n"
    + "            </tr>\n"
    + "            <tr>\n"
    + "                <td class=\"aoPopupBL\">")
    .img()
      .src(
        resp.encodeURL(
          URIEncoder.encodeURI(
            urlBase + "textskin/popup_bottomleft.gif"
          )
        )
      ).width(12).height(12).alt("").__()
    .unsafe("</td>\n"
    + "                <td class=\"aoPopupBottom\" style=\"background-image:url(");
    encodeTextInXhtmlAttribute(
      resp.encodeURL(
        URIEncoder.encodeURI(
          urlBase + "textskin/popup_bottom.gif"
        )
      ),
      document.getRawUnsafe()
    );
    document.unsafe(");\"></td>\n"
    + "                <td class=\"aoPopupBR\">");
    document.img()
      .src(
        resp.encodeURL(
          URIEncoder.encodeURI(
            urlBase + "textskin/popup_bottomright.gif"
          )
        )
      ).width(12).height(12).alt("").__()
    .unsafe("</td>\n"
    + "            </tr>\n"
    + "        </table>\n"
    + "    </div>\n"
    + "</div>\n");
    String groupIdStr = Long.toString(groupId);
    String popupIdStr = Long.toString(popupId);
    document.script().out(script -> script.append(
        "  // Override onload\n"
      + "  var aoPopupOldOnload_").append(groupIdStr).append('_').append(popupIdStr).append(" = window.onload;\n"
      + "  function adjustPositionOnload_").append(groupIdStr).append('_').append(popupIdStr).append("() {\n"
      + "    adjustPosition_").append(groupIdStr).append('_').append(popupIdStr).append("();\n"
      + "    if (aoPopupOldOnload_").append(groupIdStr).append('_').append(popupIdStr).append(") {\n"
      + "      aoPopupOldOnload_").append(groupIdStr).append('_').append(popupIdStr).append("();\n"
      + "      aoPopupOldOnload_").append(groupIdStr).append('_').append(popupIdStr).append(" = null;\n"
      + "    }\n"
      + "  }\n"
      + "  window.onload = adjustPositionOnload_").append(groupIdStr).append('_').append(popupIdStr).append(";\n"
      + "  // Override onresize\n"
      + "  var aoPopupOldOnresize_").append(groupIdStr).append('_').append(popupIdStr).append(" = window.onresize;\n"
      + "  function adjustPositionOnresize_").append(groupIdStr).append('_').append(popupIdStr).append("() {\n"
      + "    adjustPosition_").append(groupIdStr).append('_').append(popupIdStr).append("();\n"
      + "    if (aoPopupOldOnresize_").append(groupIdStr).append('_').append(popupIdStr).append(") {\n"
      + "      aoPopupOldOnresize_").append(groupIdStr).append('_').append(popupIdStr).append("();\n"
      + "    }\n"
      + "  }\n"
      + "  window.onresize = adjustPositionOnresize_").append(groupIdStr).append('_').append(popupIdStr).append(";\n"
      + "  function adjustPosition_").append(groupIdStr).append('_').append(popupIdStr).append("() {\n"
      + "    var popupAnchor = document.getElementById(\"aoPopupAnchor_").append(groupIdStr).append('_').append(popupIdStr).append("\");\n"
      + "    var popup = document.getElementById(\"aoPopup_").append(groupIdStr).append('_').append(popupIdStr).append("\");\n"
      + "    // Find the screen position of the anchor\n"
      + "    var popupAnchorLeft = 0;\n"
      + "    var obj = popupAnchor;\n"
      + "    if (obj.offsetParent) {\n"
      + "      popupAnchorLeft = obj.offsetLeft\n"
      + "      while (obj = obj.offsetParent) {\n"
      + "        popupAnchorLeft += obj.offsetLeft\n"
      + "      }\n"
      + "    }\n"
      + "    var popupAnchorRight = popupAnchorLeft + popupAnchor.offsetWidth;\n"
      + "    // Find the width of the popup\n"
      + "    var popupWidth = popup.offsetWidth;\n"
      + "    // Find the width of the screen\n"
      + "    var screenWidth = (document.compatMode && document.compatMode == \"CSS1Compat\") ? document.documentElement.clientWidth : document.body.clientWidth;\n"
      + "    // Find the desired screen position of the popup\n"
      + "    var popupScreenPosition = 0;\n"
      + "    if (screenWidth <= (popupWidth+12)) {\n"
      + "      popupScreenPosition = 0;\n"
      + "    } else {\n"
      + "      popupScreenPosition = screenWidth - popupWidth - 12;\n"
      + "      if (popupAnchorRight < popupScreenPosition) popupScreenPosition = popupAnchorRight;\n"
      + "    }\n"
      + "    popup.style.left=(popupScreenPosition-popupAnchorLeft)+\"px\";\n"
      + "  }\n"
      + "  // Call once at parse time for when the popup is activated while page loading (before onload called)\n"
      + "  adjustPosition_").append(groupIdStr).append('_').append(popupIdStr).append("();"
    )).__();
  }
}
