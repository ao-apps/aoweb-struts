/*
 * aoweb-struts-core - Core API for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2013, 2015, 2016, 2018, 2019, 2020  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts-core.
 *
 * aoweb-struts-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts-core.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.website;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.reseller.Brand;
import com.aoindustries.encoding.Doctype;
import static com.aoindustries.encoding.JavaScriptInXhtmlAttributeEncoder.encodeJavaScriptInXhtmlAttribute;
import com.aoindustries.encoding.MediaWriter;
import com.aoindustries.encoding.NewEncodingUtils;
import static com.aoindustries.encoding.TextInJavaScriptEncoder.textInJavaScriptEncoder;
import static com.aoindustries.encoding.TextInXhtmlAttributeEncoder.encodeTextInXhtmlAttribute;
import static com.aoindustries.encoding.TextInXhtmlEncoder.encodeTextInXhtml;
import static com.aoindustries.encoding.TextInXhtmlEncoder.textInXhtmlEncoder;
import com.aoindustries.html.Html;
import com.aoindustries.html.Link;
import com.aoindustries.html.Meta;
import com.aoindustries.html.Script;
import com.aoindustries.html.servlet.HtmlEE;
import com.aoindustries.html.util.GoogleAnalytics;
import com.aoindustries.html.util.ImagePreload;
import com.aoindustries.io.ContentType;
import com.aoindustries.net.AnyURI;
import com.aoindustries.net.URIEncoder;
import com.aoindustries.servlet.filter.EncodeURIFilter;
import com.aoindustries.style.AoStyle;
import static com.aoindustries.taglib.AttributeUtils.appendWidthStyle;
import com.aoindustries.taglib.HtmlTag;
import static com.aoindustries.util.StringUtility.trimNullIfEmpty;
import com.aoindustries.util.i18n.EditableResourceBundle;
import com.aoindustries.web.resources.registry.Group;
import com.aoindustries.web.resources.registry.Registry;
import com.aoindustries.web.resources.registry.Style;
import com.aoindustries.web.resources.registry.Styles;
import com.aoindustries.web.resources.renderer.Renderer;
import com.aoindustries.web.resources.servlet.RegistryEE;
import com.aoindustries.website.skintags.Child;
import com.aoindustries.website.skintags.PageAttributes;
import com.aoindustries.website.skintags.Parent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import org.apache.struts.util.MessageResources;

/**
 * The skin for the home page of the site.
 *
 * @author  AO Industries, Inc.
 */
public class TextSkin extends Skin {

	/**
	 * The name of the {@link Group} of web resources for the text skin.
	 */
	public static final String STYLE_GROUP = TextSkin.class.getName();

	public static final Style TEXTSKIN_CSS = new Style("/textskin/textskin.css");

	@SuppressWarnings("deprecation")
	public static final Style TEXTSKIN_IE6_CSS = Style.builder().uri("/textskin/textskin-ie6.css").ie("IE 6").build();

	@WebListener
	public static class Initializer implements ServletContextListener {
		@Override
		public void contextInitialized(ServletContextEvent event) {
			Styles styles = RegistryEE.get(event.getServletContext()).getGroup(STYLE_GROUP).styles;
			styles.add(AoStyle.AO_STYLE); // TODO: Remove all imports of ao-style in *.css files
			styles.add(TEXTSKIN_CSS);
			styles.add(TEXTSKIN_IE6_CSS);
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
	private static TextSkin instance;
	public static TextSkin getInstance() {
		if(instance==null) instance = new TextSkin();
		return instance;
	}

	protected TextSkin() {}

	@Override
	public String getName() {
		return "Text";
	}

	@Override
	public String getDisplay(HttpServletRequest req) throws JspException {
		Locale locale = LocaleAction.getLocale(req.getServletContext(), req);
		MessageResources applicationResources = (MessageResources)req.getAttribute("/ApplicationResources");
		if(applicationResources==null) throw new JspException("Unable to load resources: /ApplicationResources");
		return applicationResources.getMessage(locale, "TextSkin.name");
	}

	/**
	 * Print the logo for the top left part of the page.
	 */
	public void printLogo(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String urlBase) throws JspException {
		// Print no logo by default
	}

	/**
	 * Prints the search form, if any exists.
	 */
	public void printSearch(HttpServletRequest req, HttpServletResponse resp, JspWriter out) throws JspException {
	}

	/**
	 * Prints the common pages area, which is at the top of the site.
	 */
	public void printCommonPages(HttpServletRequest req, HttpServletResponse resp, JspWriter out) throws JspException {
	}

	/**
	 * Prints the lines for any JavaScript sources.
	 */
	public void printJavaScriptSources(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String urlBase) throws JspException {
	}

	/**
	 * Prints the line for the favicon.
	 */
	public void printFavIcon(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String urlBase) throws JspException {
	}

	public static MessageResources getMessageResources(HttpServletRequest req) throws JspException {
		MessageResources resources = (MessageResources)req.getAttribute("/ApplicationResources");
		if(resources==null) throw new JspException("Unable to load resources: /ApplicationResources");
		return resources;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void startSkin(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes) throws JspException {
		try {
			ServletContext servletContext = req.getServletContext();
			SiteSettings settings = SiteSettings.getInstance(servletContext);
			Brand brand = settings.getBrand();
			String trackingId = brand.getAowebStrutsGoogleAnalyticsNewTrackingCode();
			// Write doctype
			Html html = HtmlEE.get(servletContext, req, out);
			html.xmlDeclaration(resp.getCharacterEncoding());
			html.doctype();
			// Write <html>
			HtmlTag.beginHtmlTag(resp, out, html.serialization, null);
			out.write('\n');
			
			String layout = pageAttributes.getLayout();
			if(!layout.equals(PageAttributes.LAYOUT_NORMAL)) throw new JspException("TODO: Implement layout: "+layout);
			Locale locale = LocaleAction.getLocale(servletContext, req);
			MessageResources applicationResources = getMessageResources(req);
			String urlBase = getUrlBase(req);
			String path = pageAttributes.getPath();
			if(path.startsWith("/")) path=path.substring(1);
			final String fullPath = urlBase + path;
			final String encodedFullPath = resp.encodeURL(URIEncoder.encodeURI(fullPath));
			List<Skin> skins = settings.getSkins();
			boolean isOkResponseStatus;
			{
				Integer responseStatus = (Integer)req.getAttribute(Constants.HTTP_SERVLET_RESPONSE_STATUS);
				isOkResponseStatus = responseStatus==null || responseStatus==HttpServletResponse.SC_OK;
			}

			out.print("  <head>\n");
			// If this is not the default skin, then robots noindex
			boolean robotsMetaUsed = false;
			if(!isOkResponseStatus || !getName().equals(skins.get(0).getName())) {
				out.print("    ");
				html.meta(Meta.Name.ROBOTS).content("noindex, nofollow").__().nl();
			}
			if(html.doctype == Doctype.HTML5) {
				out.print("    ");
				html.meta().charset(resp.getCharacterEncoding()).__().nl();
			} else {
				out.print("    ");
				html.meta(Meta.HttpEquiv.CONTENT_TYPE).content(resp.getContentType()).__().nl();
				// Default style language
				out.print("    ");
				html.meta(Meta.HttpEquiv.CONTENT_STYLE_TYPE).content(com.aoindustries.html.Style.Type.TEXT_CSS).__().nl();
				out.print("    ");
				html.meta(Meta.HttpEquiv.CONTENT_SCRIPT_TYPE).content(Script.Type.TEXT_JAVASCRIPT).__().nl();
			}
			if(html.doctype == Doctype.HTML5) {
				GoogleAnalytics.writeGlobalSiteTag(html, trackingId);
			} else {
				GoogleAnalytics.writeAnalyticsJs(html, trackingId);
			}
			// Mobile support
			out.print("    ");
			html.meta(Meta.Name.VIEWPORT).content("width=device-width, initial-scale=1.0").__().nl();
			out.print("    ");
			html.meta(Meta.Name.APPLE_MOBILE_WEB_APP_CAPABLE).content("yes").__().nl();
			out.print("    ");
			html.meta(Meta.Name.APPLE_MOBILE_WEB_APP_STATUS_BAR_STYLE).content("black").__().nl();
			// Authors
			// TODO: dcterms copyright
			String author = pageAttributes.getAuthor();
			if(author != null && (author = author.trim()).length() > 0) {
				out.print("    ");
				html.meta(Meta.Name.AUTHOR).content(author).__().nl();
			}
			String authorHref = pageAttributes.getAuthorHref();
			if(authorHref != null && (authorHref = authorHref.trim()).length() > 0) {
				out.print("    ");
				// TODO: RFC 3986-only always?
				html.link(Link.Rel.AUTHOR).href(
					resp.encodeURL(
						URIEncoder.encodeURI(authorHref)
					)
				).__().nl();
			}
			out.print("    <title>");
			// No more page stack, just show current page only
			/*
			List<Parent> parents = pageAttributes.getParents();
			for(Parent parent : parents) {
				encodeTextInXhtml(parent.getTitle(), out);
				out.print(" - ");
			}
			 */
			encodeTextInXhtml(pageAttributes.getTitle(), out);
			out.print("</title>\n");
			String description = pageAttributes.getDescription();
			if(description != null && (description = description.trim()).length() > 0) {
				out.print("    ");
				html.meta(Meta.Name.DESCRIPTION).content(description).__().nl();
			}
			String keywords = pageAttributes.getKeywords();
			if(keywords != null && (keywords = keywords.trim()).length() > 0) {
				out.print("    ");
				html.meta(Meta.Name.KEYWORDS).content(keywords).__().nl();
			}
			// TODO: Review HTML 4/HTML 5 differences from here
			// If this is an authenticated page, redirect to session timeout after one hour
			AOServConnector aoConn = AuthenticatedAction.getAoConn(req, resp);
			HttpSession session = req.getSession(false);
			//if(session == null) session = req.getSession(false); // Get again, just in case of authentication
			if(isOkResponseStatus && aoConn != null && session != null) {
				out.print("    ");
				html.meta(Meta.HttpEquiv.REFRESH).content(content -> {
					content.write(Integer.toString(Math.max(60, session.getMaxInactiveInterval() - 60)));
					content.write(";URL=");
					content.write(
						resp.encodeRedirectURL(
							URIEncoder.encodeURI(
								urlBase
								+ "session-timeout.do?target="
								+ URIEncoder.encodeURIComponent(fullPath)
							)
						)
					);
				}).__().nl();
			}
			for(com.aoindustries.website.skintags.Meta meta : pageAttributes.getMetas()) {
				// Skip robots if not on default skin
				boolean isRobots = meta.getName().equalsIgnoreCase("robots");
				if(!robotsMetaUsed || !isRobots) {
					out.print("    ");
					html.meta().name(meta.getName()).content(meta.getContent()).__().nl();
					if(isRobots) robotsMetaUsed = true;
				}
			}
			if(isOkResponseStatus) {
				String googleVerify = brand.getAowebStrutsGoogleVerifyContent();
				if(googleVerify!=null) {
					out.print("    ");
					html.meta().name("verify-v1").content(googleVerify).__().nl();
				}
			}
			String copyright = pageAttributes.getCopyright();
			if(copyright!=null && copyright.length()>0) {
				out.print("    ");
				// TODO: Dublin Core: https://stackoverflow.com/questions/6665312/is-the-copyright-meta-tag-valid-in-html5
				html.meta().name("copyright").content(copyright).__().nl();
			}
			List<Language> languages = settings.getLanguages(req);
			printAlternativeLinks(req, resp, out, fullPath, languages);

			Registry registry = RegistryEE.get(servletContext, req);
			// Add skin-specific styles
			Styles skinStyles = registry.getGroup(STYLE_GROUP).styles;
			// Add page styles
			Styles pageStyles = registry.getGroup(PageAttributes.STYLE_GROUP).styles;
			addPageStyles(req, resp, pageAttributes, registry.global.styles, skinStyles, pageStyles);
			// Render links
			out.print("    ");
			Renderer.get(servletContext).renderStyles(req,
				resp,
				html,
				new HashSet<>(
					Arrays.asList(Group.GLOBAL,
						STYLE_GROUP,
						PageAttributes.STYLE_GROUP
					)
				),
				"    "
			);
			html.nl();

			defaultPrintLinks(req, resp, out, pageAttributes);
			printJavaScriptSources(req, resp, out, urlBase);
			out.print("    ");
			html.script().src(
				resp.encodeURL(
					URIEncoder.encodeURI(
						urlBase + "commons-validator-1.3.1-compress.js"
					)
				)
			).__().nl();
			printFavIcon(req, resp, out, urlBase);
			// TODO: Canonical?
			out.print("  </head>\n"
					+ "  <body");
			String onload = pageAttributes.getOnload();
			if(onload != null && !onload.isEmpty()) {
				out.print(" onload=\"");
				encodeJavaScriptInXhtmlAttribute(onload, out);
				out.print('"');
			}
			out.print(">\n"
					+ "    <table cellspacing=\"10\" cellpadding=\"0\">\n"
					+ "      <tr>\n"
					+ "        <td valign=\"top\">\n");
			printLogo(req, resp, out, urlBase);
			if(aoConn!=null) {
				out.print("          ");
				html.hr__().nl();
				out.print("          ");
				out.print(applicationResources.getMessage(locale, "TextSkin.logoutPrompt"));
				out.print("<form style=\"display:inline\" id=\"logout_form\" method=\"post\" action=\"");
				encodeTextInXhtmlAttribute(
					resp.encodeURL(
						URIEncoder.encodeURI(
							urlBase + "logout.do"
						)
					),
					out
				);
				out.print("\"><div style=\"display:inline;\">");
				html.input.hidden().name("target").value(fullPath).__();
				// Variant that takes ResourceBundle?
				html.input.submit__(applicationResources.getMessage(locale, "TextSkin.logoutButtonLabel"));
				out.print("</div></form>\n");
			} else {
				out.print("          ");
				html.hr__().nl();
				out.print("          ");
				out.print(applicationResources.getMessage(locale, "TextSkin.loginPrompt"));
				out.print("<form style=\"display:inline\" id=\"login_form\" method=\"post\" action=\"");
				encodeTextInXhtmlAttribute(
					resp.encodeURL(
						URIEncoder.encodeURI(
							urlBase + "login.do"
						)
					),
					out
				);
				out.print("\"><div style=\"display:inline\">");
				// Only include the target when they are not in the /clientarea/ part of the site
				if(path.startsWith("clientarea/")) {
					html.input.hidden().name("target").value(fullPath).__();
				}
				html.input.submit__(applicationResources.getMessage(locale, "TextSkin.loginButtonLabel"));
				out.print("</div></form>\n");
			}
			out.print("          ");
			html.hr__().nl();
			out.print("          <div style=\"white-space: nowrap\">\n");
			if(skins.size()>1) {
				html.script().out(script -> {
					script.write("function selectLayout(layout) {\n");
					for(Skin skin : skins) {
						script.write("  if(layout==\"");
						NewEncodingUtils.encodeTextInJavaScriptInXhtml(skin.getName(), script);
						script.write("\") window.top.location.href=\"");
						NewEncodingUtils.encodeTextInJavaScriptInXhtml(
							resp.encodeURL(
								new AnyURI(fullPath)
									.addEncodedParameter("layout", URIEncoder.encodeURIComponent(skin.getName()))
									.toASCIIString()
							),
							script
						);
						script.write("\";\n");
					}
					script.write('}');
				}).__().nl();
				out.print("            <form action=\"\" style=\"display:inline\"><div style=\"display:inline\">\n"
						+ "              ");
				out.print(applicationResources.getMessage(locale, "TextSkin.layoutPrompt"));
				out.print("<select name=\"layout_selector\" onchange=\"selectLayout(this.form.layout_selector.options[this.form.layout_selector.selectedIndex].value);\">\n");
				for(Skin skin : skins) {
					out.print("                ");
					html.option().value(skin.getName()).selected(getName().equals(skin.getName())).text__(skin.getDisplay(req)).nl();
				}
				out.print("              </select>\n"
						+ "            </div></form>");
				html.br__().nl();
			}
			if(languages.size()>1) {
				out.print("            ");
				for(Language language : languages) {
					AnyURI uri = language.getUri();
					if(language.getCode().equalsIgnoreCase(locale.getLanguage())) {
						out.print("&#160;<a href=\"");
						encodeTextInXhtmlAttribute(
							resp.encodeURL(
								URIEncoder.encodeURI(
									(
										uri == null
										? new AnyURI(fullPath).addEncodedParameter("language", URIEncoder.encodeURIComponent(language.getCode()))
										: uri
									).toASCIIString()
								)
							),
							out
						);
						out.print("\" hreflang=\"");
						encodeTextInXhtmlAttribute(
							language.getCode(),
							out
						);
						out.print("\">");
						html.img()
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
							.__();
						out.print("</a>");
					} else {
						out.print("&#160;<a href=\"");
						encodeTextInXhtmlAttribute(
							resp.encodeURL(
								URIEncoder.encodeURI(
									(
										uri == null
										? new AnyURI(fullPath).addEncodedParameter("language", URIEncoder.encodeURIComponent(language.getCode()))
										: uri
									).toASCIIString()
								)
							),
							out
						);
						out.print("\" hreflang=\"");
						encodeTextInXhtmlAttribute(
							language.getCode(),
							out
						);
						out.print("\" onmouseover='document.images.flagSelector_");
						NewEncodingUtils.encodeTextInJavaScriptInXhtmlAttribute(language.getCode(), out);
						out.print(".src=\"");
						NewEncodingUtils.encodeTextInJavaScriptInXhtmlAttribute(
							resp.encodeURL(
								URIEncoder.encodeURI(
									urlBase
									+ language.getFlagOnSrc(req, locale)
								)
							),
							out
						);
						out.print("\";' onmouseout='document.images.flagSelector_");
						out.print(language.getCode());
						out.print(".src=\"");
						NewEncodingUtils.encodeTextInJavaScriptInXhtmlAttribute(
							resp.encodeURL(
								URIEncoder.encodeURI(
									urlBase
									+ language.getFlagOffSrc(req, locale)
								)
							),
							out
						);
						out.print("\";'>");
						html.img()
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
							.__();
						out.print("</a>");
						ImagePreload.writeImagePreloadScript(
							resp.encodeURL(
								URIEncoder.encodeURI(
									urlBase + language.getFlagOnSrc(req, locale)
								)
							),
							html
						);
					}
				}
				html.br__().nl();
			}
			printSearch(req, resp, out);
			out.print("          </div>\n"
					+ "          ");
			html.hr__().nl();
			// Display the parents
			out.print("          <b>");
			out.print(applicationResources.getMessage(locale, "TextSkin.currentLocation"));
			out.print("</b>");
			html.br__().nl();
			out.print("          <div style=\"white-space:nowrap\">\n");
			List<Parent> parents = pageAttributes.getParents();
			for(Parent parent : parents) {
				String navAlt = parent.getNavImageAlt();
				String parentPath = parent.getPath();
				if(parentPath.startsWith("/")) parentPath=parentPath.substring(1);
				out.print("            <a href=\"");
				encodeTextInXhtmlAttribute(
					resp.encodeURL(
						URIEncoder.encodeURI(
							urlBase
							+ URIEncoder.encodeURI(parentPath)
						)
					),
					out
				);
				out.print("\">");
				encodeTextInXhtml(navAlt, out);
				out.print("</a>");
				html.br__().nl();
			}
			// Always include the current page in the current location area
			out.print("            <a href=\"");
			encodeTextInXhtmlAttribute(
				encodedFullPath,
				out
			);
			out.print("\">");
			encodeTextInXhtml(pageAttributes.getNavImageAlt(), out);
			out.print("</a>");
			html.br__().nl();
			out.print("          </div>\n"
					+ "          ");
			html.hr__().nl();
			out.print("          <b>");
			out.print(applicationResources.getMessage(locale, "TextSkin.relatedPages"));
			out.print("</b>");
			html.br__().nl();
			// Display the siblings
			out.print("          <div style=\"white-space:nowrap\">\n");
			List<Child> siblings = pageAttributes.getChildren();
			if(siblings.isEmpty() && !parents.isEmpty()) {
				siblings = parents.get(parents.size()-1).getChildren();
			}
			for(Child sibling : siblings) {
				String navAlt=sibling.getNavImageAlt();
				String siblingPath = sibling.getPath();
				if(siblingPath.startsWith("/")) siblingPath=siblingPath.substring(1);
				out.print("          <a href=\"");
				encodeTextInXhtmlAttribute(
					resp.encodeURL(
						URIEncoder.encodeURI(
							urlBase
							+ URIEncoder.encodeURI(siblingPath)
						)
					),
					out
				);
				out.print("\">");
				encodeTextInXhtml(navAlt, out);
				out.print("</a>");
				html.br__().nl();
			}
			out.print("          </div>\n");
			html.hr__().nl();
			printBelowRelatedPages(req, out);
			out.print("        </td>\n"
					+ "        <td valign=\"top\">\n");
			printCommonPages(req, resp, out);
		} catch(IOException | SQLException err) {
			throw new JspException(err);
		}
	}

	/**
	 * Adds the additional pages styles to the provided request-scoped page group.
	 *
	 * @see  PageAttributes#STYLE_GROUP
	 */
	// TODO: Move to Skin or SkinUtil?
	public static void addPageStyles(HttpServletRequest req, HttpServletResponse resp, PageAttributes pageAttributes, Styles globalStyles, Styles skinStyles, Styles pageStyles) {
		Style lastStyle = null;
		for(PageAttributes.Link link : pageAttributes.getLinks()) {
			String type = link.getType();
			if(
				Link.Rel.STYLESHEET.toString().equalsIgnoreCase(link.getRel())
				&& (
					type == null
					|| ContentType.CSS.equalsIgnoreCase(type)
				)
			) {
				Style newStyle = Style.builder()
					.uri(link.getHref())
					.ie(link.getConditionalCommentExpression())
					.build();
				if(lastStyle == null) {
					// Set this to do after all global and skin styles
					// TODO: Use Prelude/Main/Coda with this Coda, or allow ordering declared where needed
					// TODO: Or  Prelude/Global/Theme/Page/View/.../Code
					// TODO: Or just guarantee ordering within the page styles, and use <wr: /> taglib when need more
					// TODO:     <wr: /> have a new "page" scope, which would depend on a handler being currently registered for Skin tags versus JSP page, versus ...
					// TODO:     If doing this, remove support for these links this way?
					for(Style style : globalStyles.getSnapshot()) {
						pageStyles.addOrdering(style, newStyle);
					}
					for(Style style : skinStyles.getSnapshot()) {
						pageStyles.addOrdering(style, newStyle);
					}
				} else {
					pageStyles.addOrdering(newStyle, lastStyle);
				}
				lastStyle = newStyle;
			}
		}
	}

	/**
	 * Prints links except those that are <code>rel="stylesheet" and text={null | "text/css"}</code>.
	 *
	 * @see  #addPageStyles(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.website.skintags.PageAttributes, com.aoindustries.web.resources.registry.Styles, com.aoindustries.web.resources.registry.Styles, com.aoindustries.web.resources.registry.Styles)
	 */
	// TODO: Move to Skin or SkinUtil?
	public static void defaultPrintLinks(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes) throws JspException {
		try {
			Html html = HtmlEE.get(req, out);
			for(PageAttributes.Link link : pageAttributes.getLinks()) {
				String type = link.getType();
				if(
					!Link.Rel.STYLESHEET.toString().equalsIgnoreCase(link.getRel())
					|| (
						type != null
						&& !ContentType.CSS.equalsIgnoreCase(type)
					)
				) {
					String conditionalCommentExpression = link.getConditionalCommentExpression();
					if(conditionalCommentExpression!=null) {
						out.print("    <!--[if ");
						out.print(conditionalCommentExpression);
						out.print("]>\n"
								+ "  ");
					}
					out.print("    ");
					html.link()
						.rel(link.getRel())
						.href(
							EncodeURIFilter.getActiveFilter(req).encode(
								link.getHref(),
								html.doctype,
								resp.getCharacterEncoding()
							)
						)
						.type(link.getType())
						.__().nl();

					if(conditionalCommentExpression!=null) out.print("    <![endif]-->\n");
				}
			}
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void startContent(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes, int[] colspans, String width) throws JspException {
		width = trimNullIfEmpty(width);
		try {
			Html html = HtmlEE.get(req, out);
			out.print("          <table class=\"ao-packed\"");
			if(width != null) {
				out.print(" style=\"");
				appendWidthStyle(width, out);
				out.print('"');
			}
			out.print(">\n"
					+ "            <tr>\n");
			int totalColumns=0;
			for(int c=0;c<colspans.length;c++) {
				if(c>0) totalColumns++;
				totalColumns+=colspans[c];
			}
			out.print("              <td");
			if(totalColumns!=1) {
				out.print(" colspan=\"");
				out.print(totalColumns);
				out.print('"');
			}
			out.print(">");
			html.hr__();
			out.print("</td>\n"
					+ "            </tr>\n");
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void printContentTitle(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String title, int colspan) throws JspException {
		try {
			startContentLine(req, resp, out, colspan, "center", null);
			out.print("<h1>");
			encodeTextInXhtml(title, out);
			out.print("</h1>\n");
			endContentLine(req, resp, out, 1, false);
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void startContentLine(HttpServletRequest req, HttpServletResponse resp, JspWriter out, int colspan, String align, String width) throws JspException {
		align = trimNullIfEmpty(align);
		width = trimNullIfEmpty(width);
		try {
			out.print("            <tr>\n"
					+ "              <td");
			if(align != null || width != null) {
				out.append(" style=\"");
				if(align != null) {
					out.append("text-align:");
					encodeTextInXhtmlAttribute(align, out);
				}
				if(width != null) {
					if(align != null) out.append(';');
					appendWidthStyle(width, out);
				}
				out.append('"');
			}
			out.print(" valign=\"top\"");
			if(colspan!=1) {
				out.print(" colspan=\"");
				out.print(colspan);
				out.print('"');
			}
			out.print('>');
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void printContentVerticalDivider(HttpServletRequest req, HttpServletResponse resp, JspWriter out, boolean visible, int colspan, int rowspan, String align, String width) throws JspException {
		align = trimNullIfEmpty(align);
		width = trimNullIfEmpty(width);
		try {
			out.print("              </td>\n");
			if(visible) out.print("              <td>&#160;</td>\n");
			out.print("              <td");
			if(align != null || width != null) {
				out.append(" style=\"");
				if(align != null) {
					out.append("text-align:");
					encodeTextInXhtmlAttribute(align, out);
				}
				if(width != null) {
					if(align != null) out.append(';');
					appendWidthStyle(width, out);
				}
				out.append('"');
			}
			out.print(" valign=\"top\"");
			if(colspan!=1) {
				out.print(" colspan=\"");
				out.print(colspan);
				out.print('"');
			}
			if(rowspan!=1) {
				out.print(" rowspan=\"");
				out.print(rowspan);
				out.print('"');
			}
			out.print('>');
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void endContentLine(HttpServletRequest req, HttpServletResponse resp, JspWriter out, int rowspan, boolean endsInternal) throws JspException {
		try {
			out.print("              </td>\n"
					+ "            </tr>\n");
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void printContentHorizontalDivider(HttpServletRequest req, HttpServletResponse resp, JspWriter out, int[] colspansAndDirections, boolean endsInternal) throws JspException {
		try {
			Html html = HtmlEE.get(req, out);
			out.print("            <tr>\n");
			for(int c=0;c<colspansAndDirections.length;c+=2) {
				if(c>0) {
					int direction=colspansAndDirections[c-1];
					switch(direction) {
						case UP:
							out.print("              <td>&#160;</td>\n");
							break;
						case DOWN:
							out.print("              <td>&#160;</td>\n");
							break;
						case UP_AND_DOWN:
							out.print("              <td>&#160;</td>\n");
							break;
						default: throw new IllegalArgumentException("Unknown direction: "+direction);
					}
				}

				int colspan=colspansAndDirections[c];
				out.print("              <td");
				if(colspan!=1) {
					out.print(" colspan=\"");
					out.print(colspan);
					out.print('"');
				}
				out.print('>');
				html.hr__();
				out.print("</td>\n");
			}
			out.print("            </tr>\n");
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void endContent(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes, int[] colspans) throws JspException {
		try {
			Html html = HtmlEE.get(req, out);
			int totalColumns=0;
			for(int c=0;c<colspans.length;c++) {
				if(c>0) totalColumns+=1;
				totalColumns+=colspans[c];
			}
			out.print("            <tr><td");
			if(totalColumns!=1) {
				out.print(" colspan=\"");
				out.print(totalColumns);
				out.print('"');
			}
			out.print('>');
			html.hr__();
			out.print("</td></tr>\n");
			String copyright = pageAttributes.getCopyright();
			if(copyright!=null && copyright.length()>0) {
				out.print("            <tr><td");
				if(totalColumns!=1) {
					out.print(" colspan=\"");
					out.print(totalColumns);
					out.print('"');
				}
				out.print(" style=\"text-align:center\"><span style=\"font-size: x-small\">");
				out.print(copyright);
				out.print("</span></td></tr>\n");
			}
			out.print("          </table>\n");
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void endSkin(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes) throws JspException {
		try {
			out.print("        </td>\n"
					+ "      </tr>\n"
					+ "    </table>\n");
			// TODO: SemanticCMS component for this, also make sure in other layouts and skins
			EditableResourceBundle.printEditableResourceBundleLookups(
				textInJavaScriptEncoder,
				textInXhtmlEncoder,
				out,
				4,
				true
			);
			out.print("  </body>\n");
			HtmlTag.endHtmlTag(out);
			out.write('\n');
		} catch(IOException  err) {
			throw new JspException(err);
		}
	}

	@Override
	public void beginLightArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String align, String width, boolean nowrap) throws JspException {
		align = trimNullIfEmpty(align);
		width = trimNullIfEmpty(width);
		try {
			out.print("<table class=\"ao-packed\" style=\"border:5px outset #a0a0a0");
			if(width != null) {
				out.print(';');
				appendWidthStyle(width, out);
			}
			out.print("\">\n"
					+ "  <tr>\n"
					+ "    <td class=\"aoLightRow\" style=\"padding:4px");
			if(align != null) {
				out.append(";text-align:");
				encodeTextInXhtmlAttribute(align, out);
			}
			if(nowrap) out.print(";white-space:nowrap;");
			out.print("\">");
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void endLightArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out) throws JspException {
		try {
			out.print("</td>\n"
					+ "  </tr>\n"
					+ "</table>\n");
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void beginWhiteArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String align, String width, boolean nowrap) throws JspException {
		align = trimNullIfEmpty(align);
		width = trimNullIfEmpty(width);
		try {
			out.print("<table class=\"ao-packed\" style=\"border:5px outset #a0a0a0");
			if(width != null) {
				out.print(';');
				appendWidthStyle(width, out);
			}
			out.print("\">\n"
					+ "  <tr>\n"
					+ "    <td class=\"aoWhiteRow\" style=\"padding:4px;");
			if(align != null) {
				out.append(";text-align:");
				encodeTextInXhtmlAttribute(align, out);
			}
			if(nowrap) out.print(" white-space:nowrap;");
			out.print("\">");
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void endWhiteArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out) throws JspException {
		try {
			out.print("</td>\n"
					+ "  </tr>\n"
					+ "</table>\n");
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	@Override
	public void printAutoIndex(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes) throws JspException {
		try {
			String urlBase = getUrlBase(req);
			//Locale locale = resp.getLocale();

			out.print("<table cellpadding=\"0\" cellspacing=\"10\">\n");
			List<Child> siblings = pageAttributes.getChildren();
			if(siblings.isEmpty()) {
				List<Parent> parents = pageAttributes.getParents();
				if(!parents.isEmpty()) siblings = parents.get(parents.size()-1).getChildren();
			}
			for(Child sibling : siblings) {
				String navAlt=sibling.getNavImageAlt();
				String siblingPath = sibling.getPath();
				if(siblingPath.startsWith("/")) siblingPath=siblingPath.substring(1);

				out.print("  <tr>\n"
						+ "    <td style=\"white-space:nowrap\"><a class=\"aoLightLink\" href=\"");
				encodeTextInXhtmlAttribute(
					resp.encodeURL(
						URIEncoder.encodeURI(
							urlBase
							+ URIEncoder.encodeURI(siblingPath)
						)
					),
					out
				);
				out.print("\">");
				encodeTextInXhtml(navAlt, out);
				out.print("</a></td>\n"
						+ "    <td style=\"width:12px; white-space:nowrap\">&#160;</td>\n"
						+ "    <td style=\"white-space:nowrap\">");
				String description = sibling.getDescription();
				if(description!=null && (description=description.trim()).length()>0) {
					encodeTextInXhtml(description, out);
				} else {
					String title = sibling.getTitle();
					if(title!=null && (title=title.trim()).length()>0) {
						encodeTextInXhtml(title, out);
					} else {
						out.print("&#160;");
					}
				}
				out.print("</td>\n"
						+ "  </tr>\n")
				;
			}
			out.print("</table>\n");
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	/**
	 * Prints content below the related pages area on the left.
	 */
	public void printBelowRelatedPages(HttpServletRequest req, JspWriter out) throws JspException {
	}

	/**
	 * Begins a popup group.
	 *
	 * @see  #defaultBeginPopupGroup(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.jsp.JspWriter, long)
	 */
	@Override
	public void beginPopupGroup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId) throws JspException {
		defaultBeginPopupGroup(req, resp, out, groupId);
	}

	/**
	 * Default implementation of beginPopupGroup.
	 */
	public static void defaultBeginPopupGroup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId) throws JspException {
		try {
			Html html = HtmlEE.get(req, out);
			try (MediaWriter script = html.script().out__()) {
				String groupIdStr = Long.toString(groupId);
				script.write("  var popupGroupTimer"); script.write(groupIdStr); script.write("=null;\n"
					+ "  var popupGroupAuto"); script.write(groupIdStr); script.write("=null;\n"
					+ "  function popupGroupHideAllDetails"); script.write(groupIdStr); script.write("() {\n"
					+ "    var spanElements = document.getElementsByTagName ? document.getElementsByTagName(\"div\") : document.all.tags(\"div\");\n"
					+ "    for (var c=0; c < spanElements.length; c++) {\n"
					+ "      if(spanElements[c].id.indexOf(\"aoPopup_"); script.write(groupIdStr); script.write("_\")==0) {\n"
					+ "        spanElements[c].style.visibility=\"hidden\";\n"
					+ "      }\n"
					+ "    }\n"
					+ "  }\n"
					+ "  function popupGroupToggleDetails"); script.write(groupIdStr); script.write("(popupId) {\n"
					+ "    if(popupGroupTimer"); script.write(groupIdStr); script.write("!=null) clearTimeout(popupGroupTimer"); script.write(groupIdStr); script.write(");\n"
					+ "    var elemStyle = document.getElementById(\"aoPopup_"); script.write(groupIdStr); script.write("_\"+popupId).style;\n"
					+ "    if(elemStyle.visibility==\"visible\") {\n"
					+ "      elemStyle.visibility=\"hidden\";\n"
					+ "    } else {\n"
					+ "      popupGroupHideAllDetails"); script.write(groupIdStr); script.write("();\n"
					+ "      elemStyle.visibility=\"visible\";\n"
					+ "    }\n"
					+ "  }\n"
					+ "  function popupGroupShowDetails"); script.write(groupIdStr); script.write("(popupId) {\n"
					+ "    if(popupGroupTimer"); script.write(groupIdStr); script.write("!=null) clearTimeout(popupGroupTimer"); script.write(groupIdStr); script.write(");\n"
					+ "    var elemStyle = document.getElementById(\"aoPopup_"); script.write(groupIdStr); script.write("_\"+popupId).style;\n"
					+ "    if(elemStyle.visibility!=\"visible\") {\n"
					+ "      popupGroupHideAllDetails"); script.write(groupIdStr); script.write("();\n"
					+ "      elemStyle.visibility=\"visible\";\n"
					+ "    }\n"
					+ "  }\n");
			}
			html.nl();
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	/**
	 * Ends a popup group.
	 *
	 * @see  #defaultEndPopupGroup(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.jsp.JspWriter, long)
	 */
	@Override
	public void endPopupGroup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId) throws JspException {
		defaultEndPopupGroup(req, resp, out, groupId);
	}

	/**
	 * Default implementation of endPopupGroup.
	 */
	public static void defaultEndPopupGroup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId) throws JspException {
		// Nothing at the popup group end
	}

	/**
	 * Begins a popup that is in a popup group.
	 *
	 * @see  #defaultBeginPopup(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.jsp.JspWriter, long, long, java.lang.String, java.lang.String)
	 */
	@Override
	public void beginPopup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId, String width) throws JspException {
		defaultBeginPopup(req, resp, out, groupId, popupId, width, getUrlBase(req));
	}

	/**
	 * Default implementation of beginPopup.
	 */
	public static void defaultBeginPopup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId, String width, String urlBase) throws JspException {
		if(groupId < 0) throw new IllegalArgumentException("groupId < 0: " + groupId);
		final String groupIdStr = Long.toString(groupId);

		if(popupId < 0) throw new IllegalArgumentException("popupId < 0: " + popupId);
		final String popupIdStr = Long.toString(popupId);

		width = trimNullIfEmpty(width);
		try {
			ServletContext servletContext = req.getServletContext();
			Html html = HtmlEE.get(servletContext, req, out);
			Locale locale = LocaleAction.getLocale(servletContext, req);
			MessageResources applicationResources = getMessageResources(req);

			out.print("<div id=\"aoPopupAnchor_");
			out.print(groupIdStr);
			out.print('_');
			out.print(popupIdStr);
			out.print("\" class=\"aoPopupAnchor\">");
			html.img()
				.clazz("aoPopupAnchorImg")
				.src(
					resp.encodeURL(
						URIEncoder.encodeURI(
							urlBase
							+ applicationResources.getMessage(locale, "TextSkin.popup.src")
						)
					)
				).alt(applicationResources.getMessage(locale, "TextSkin.popup.alt"))
				.width(Integer.parseInt(applicationResources.getMessage(locale, "TextSkin.popup.width")))
				.height(Integer.parseInt(applicationResources.getMessage(locale, "TextSkin.popup.height")))
				.onmouseover(onmouseover -> {
					onmouseover.write("popupGroupTimer");
					onmouseover.write(groupIdStr);
					onmouseover.write("=setTimeout(\"popupGroupAuto");
					onmouseover.write(groupIdStr);
					onmouseover.write("=true; popupGroupShowDetails");
					onmouseover.write(groupIdStr);
					onmouseover.write('(');
					onmouseover.write(popupIdStr);
					onmouseover.write(")\", 1000);");
				}).onmouseout(onmouseout -> {
					onmouseout.write("if(popupGroupAuto");
					onmouseout.write(groupIdStr);
					onmouseout.write(") popupGroupHideAllDetails");
					onmouseout.write(groupIdStr);
					onmouseout.write("(); if(popupGroupTimer");
					onmouseout.write(groupIdStr);
					onmouseout.write("!=null) clearTimeout(popupGroupTimer");
					onmouseout.write(groupIdStr);
					onmouseout.write(");");
				}).onclick(onclick -> {
					onclick.write("popupGroupAuto");
					onclick.write(groupIdStr);
					onclick.write("=false; popupGroupToggleDetails");
					onclick.write(groupIdStr);
					onclick.write('(');
					onclick.write(popupIdStr);
					onclick.write(");");
				}).__().nl();
			out.print("    <div id=\"aoPopup_"); // Used to be span width=\"100%\"
			out.print(groupIdStr);
			out.print('_');
			out.print(popupIdStr);
			out.print("\" class=\"aoPopupMain\"");
			if(width != null) {
				out.print(" style=\"");
				appendWidthStyle(width, out);
				out.print('"');
			}
			out.print(">\n"
					+ "        <table class=\"aoPopupTable ao-packed\">\n"
					+ "            <tr>\n"
					+ "                <td class=\"aoPopupTL\">");
			html.img()
				.src(
					resp.encodeURL(
						URIEncoder.encodeURI(
							urlBase + "textskin/popup_topleft.gif"
						)
					)
				).width(12).height(12).alt("").__();
			out.print("</td>\n"
					+ "                <td class=\"aoPopupTop\" style=\"background-image:url(");
			encodeTextInXhtmlAttribute(
				resp.encodeURL(
					URIEncoder.encodeURI(
						urlBase + "textskin/popup_top.gif"
					)
				),
				out
			);
			out.print(");\"></td>\n"
					+ "                <td class=\"aoPopupTR\">");
			html.img()
				.src(
					resp.encodeURL(
						URIEncoder.encodeURI(
							urlBase + "textskin/popup_topright.gif"
						)
					)
				).width(12).height(12).alt("").__();
			out.print("</td>\n"
					+ "            </tr>\n"
					+ "            <tr>\n"
					+ "                <td class=\"aoPopupLeft\" style=\"background-image:url(");
			encodeTextInXhtmlAttribute(
				resp.encodeURL(
					URIEncoder.encodeURI(
						urlBase + "textskin/popup_left.gif"
					)
				),
				out
			);
			out.print(");\"></td>\n"
					+ "                <td class=\"aoPopupLightRow\">");
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	/**
	 * Prints a popup close link/image/button for a popup that is part of a popup group.
	 *
	 * @see  #defaultPrintPopupClose(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.jsp.JspWriter, long, long, java.lang.String)
	 */
	@Override
	public void printPopupClose(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId) throws JspException {
		defaultPrintPopupClose(req, resp, out, groupId, popupId, getUrlBase(req));
	}

	/**
	 * Default implementation of printPopupClose.
	 */
	public static void defaultPrintPopupClose(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId, String urlBase) throws JspException {
		try {
			ServletContext servletContext = req.getServletContext();
			Html html = HtmlEE.get(servletContext, req, out);
			Locale locale = LocaleAction.getLocale(servletContext, req);
			MessageResources applicationResources = getMessageResources(req);

			html.img()
				.clazz("aoPopupClose")
				.src(
					resp.encodeURL(
						URIEncoder.encodeURI(
							urlBase + applicationResources.getMessage(locale, "TextSkin.popupClose.src")
						)
					)
				).alt(applicationResources.getMessage(locale, "TextSkin.popupClose.alt"))
				.width(Integer.parseInt(applicationResources.getMessage(locale, "TextSkin.popupClose.width")))
				.height(Integer.parseInt(applicationResources.getMessage(locale, "TextSkin.popupClose.height")))
				.onclick("popupGroupHideAllDetails" + groupId + "();")
				.__();
		} catch(IOException err) {
			throw new JspException(err);
		}
	}

	/**
	 * Ends a popup that is in a popup group.
	 *
	 * @see  #defaultEndPopup(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.jsp.JspWriter, long, long, java.lang.String, java.lang.String)
	 */
	@Override
	public void endPopup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId, String width) throws JspException {
		TextSkin.defaultEndPopup(req, resp, out, groupId, popupId, width, getUrlBase(req));
	}

	/**
	 * Default implementation of endPopup.
	 */
	public static void defaultEndPopup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId, String width, String urlBase) throws JspException {
		try {
			Html html = HtmlEE.get(req, out);
			out.print("</td>\n"
				+ "                <td class=\"aoPopupRight\" style=\"background-image:url(");
			encodeTextInXhtmlAttribute(
				resp.encodeURL(
					URIEncoder.encodeURI(
						urlBase + "textskin/popup_right.gif"
					)
				),
				out
			);
			out.print(");\"></td>\n"
				+ "            </tr>\n"
				+ "            <tr>\n" 
				+ "                <td class=\"aoPopupBL\">");
			html.img()
				.src(
					resp.encodeURL(
						URIEncoder.encodeURI(
							urlBase + "textskin/popup_bottomleft.gif"
						)
					)
				).width(12).height(12).alt("").__();
			out.print("</td>\n"
				+ "                <td class=\"aoPopupBottom\" style=\"background-image:url(");
			encodeTextInXhtmlAttribute(
				resp.encodeURL(
					URIEncoder.encodeURI(
						urlBase + "textskin/popup_bottom.gif"
					)
				),
				out
			);
			out.print(");\"></td>\n"
				+ "                <td class=\"aoPopupBR\">");
			html.img()
				.src(
					resp.encodeURL(
						URIEncoder.encodeURI(
							urlBase + "textskin/popup_bottomright.gif"
						)
					)
				).width(12).height(12).alt("").__();
			out.print("</td>\n"
				+ "            </tr>\n"
				+ "        </table>\n"
				+ "    </div>\n"
				+ "</div>\n");
			try (MediaWriter script = html.script().out__()) {
				String groupIdStr = Long.toString(groupId);
				String popupIdStr = Long.toString(popupId);
				script.write("\t// Override onload\n"
					+ "\tvar aoPopupOldOnload_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write(" = window.onload;\n"
					+ "\tfunction adjustPositionOnload_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write("() {\n"
					+ "\t\tadjustPosition_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write("();\n"
					+ "\t\tif(aoPopupOldOnload_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write(") {\n"
					+ "\t\t\taoPopupOldOnload_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write("();\n"
					+ "\t\t\taoPopupOldOnload_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write(" = null;\n"
					+ "\t\t}\n"
					+ "\t}\n"
					+ "\twindow.onload = adjustPositionOnload_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write(";\n"
					+ "\t// Override onresize\n"
					+ "\tvar aoPopupOldOnresize_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write(" = window.onresize;\n"
					+ "\tfunction adjustPositionOnresize_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write("() {\n"
					+ "\t\tadjustPosition_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write("();\n"
					+ "\t\tif(aoPopupOldOnresize_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write(") {\n"
					+ "\t\t\taoPopupOldOnresize_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write("();\n"
					+ "\t\t}\n"
					+ "\t}\n"
					+ "\twindow.onresize = adjustPositionOnresize_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write(";\n"
					+ "\tfunction adjustPosition_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write("() {\n"
					+ "\t\tvar popupAnchor = document.getElementById(\"aoPopupAnchor_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write("\");\n"
					+ "\t\tvar popup = document.getElementById(\"aoPopup_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write("\");\n"
					+ "\t\t// Find the screen position of the anchor\n"
					+ "\t\tvar popupAnchorLeft = 0;\n"
					+ "\t\tvar obj = popupAnchor;\n"
					+ "\t\tif(obj.offsetParent) {\n"
					+ "\t\t\tpopupAnchorLeft = obj.offsetLeft\n"
					+ "\t\t\twhile (obj = obj.offsetParent) {\n"
					+ "\t\t\t\tpopupAnchorLeft += obj.offsetLeft\n"
					+ "\t\t\t}\n"
					+ "\t\t}\n"
					+ "\t\tvar popupAnchorRight = popupAnchorLeft + popupAnchor.offsetWidth;\n"
					+ "\t\t// Find the width of the popup\n"
					+ "\t\tvar popupWidth = popup.offsetWidth;\n"
					+ "\t\t// Find the width of the screen\n"
					+ "\t\tvar screenWidth = (document.compatMode && document.compatMode == \"CSS1Compat\") ? document.documentElement.clientWidth : document.body.clientWidth;\n"
					+ "\t\t// Find the desired screen position of the popup\n"
					+ "\t\tvar popupScreenPosition = 0;\n"
					+ "\t\tif(screenWidth<=(popupWidth+12)) {\n"
					+ "\t\t\tpopupScreenPosition = 0;\n"
					+ "\t\t} else {\n"
					+ "\t\t\tpopupScreenPosition = screenWidth - popupWidth - 12;\n"
					+ "\t\t\tif(popupAnchorRight < popupScreenPosition) popupScreenPosition = popupAnchorRight;\n"
					+ "\t\t}\n"
					+ "\t\tpopup.style.left=(popupScreenPosition-popupAnchorLeft)+\"px\";\n"
					+ "\t}\n"
					+ "\t// Call once at parse time for when the popup is activated while page loading (before onload called)\n"
					+ "\tadjustPosition_");
				script.write(groupIdStr);
				script.write('_');
				script.write(popupIdStr);
				script.write("();\n");
			}
		} catch(IOException err) {
			throw new JspException(err);
		}
	}
}