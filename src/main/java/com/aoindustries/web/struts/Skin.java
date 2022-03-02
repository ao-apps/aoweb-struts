/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2013, 2015, 2016, 2017, 2019, 2020, 2021, 2022  AO Industries, Inc.
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
import com.aoapps.encoding.servlet.DoctypeEE;
import com.aoapps.encoding.servlet.SerializationEE;
import com.aoapps.html.any.AnyLINK;
import com.aoapps.html.servlet.ContentEE;
import com.aoapps.html.servlet.DocumentEE;
import com.aoapps.html.servlet.FlowContent;
import com.aoapps.lang.io.function.IOConsumerE;
import com.aoapps.lang.io.function.IORunnableE;
import com.aoapps.net.AnyURI;
import com.aoapps.net.URIEncoder;
import com.aoapps.servlet.http.HttpServletUtil;
import com.aoapps.web.resources.registry.Registry;
import com.aoindustries.web.struts.skintags.PageAttributes;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

/**
 * One look-and-feel for the website.
 *
 * @author  AO Industries, Inc.
 */
// TODO: Throw IOException on most/all methods where is given a writer, then won't need so many exception conversions in implementations.
public abstract class Skin {

	/**
	 * Gets the default skin from the provided list for the provided request.
	 * Blackberry and Lynx will default to {@link TextSkin#NAME} if in the list, otherwise
	 * the first skin is selected.
	 */
	public static Skin getDefaultSkin(List<Skin> skins, ServletRequest req) {
		// Lynx and BlackBerry default to text
		String agent;
		if(req instanceof HttpServletRequest) {
			agent = ((HttpServletRequest)req).getHeader("user-agent");
		} else {
			agent = null;
		}
		if(
			agent!=null
			&& (
				agent.toLowerCase().contains("lynx")
				|| agent.startsWith("BlackBerry")
			)
		) {
			for(Skin skin : skins) {
				if(skin.getName().equals(TextSkin.NAME)) return skin;
			}
		}
		// Use the first as the default
		return skins.get(0);
	}

	/**
	 * Gets the skin for the current request.
	 *
	 * <ol>
	 *   <li>If the skin is already set in the request attribute {@link Constants#SKIN}, uses the current setting.</li>
	 *   <li>If the parameter {@link Constants#LAYOUT} exists, it will get the class name for the skin from the servlet parameters and set the skin.</li>
	 *   <li>If the parameter {@link Constants#LAYOUT} doesn't exist and a skin has been selected, then it returns the current skin.</li>
	 *   <li>Sets the skin from the servlet parameters.</li>
	 *   <li>Sets the {@link Constants#LAYOUT} in the session.</li>
	 *   <li>Stores the skin in request attribute {@link Constants#SKIN}.</li>
	 * </ol>
	 */
	public static Skin getSkin(SiteSettings settings, ServletRequest req) {
		{
			Skin skin = Constants.SKIN.context(req).get();
			if(skin != null) return skin;
		}

		List<Skin> skins = settings.getSkins();

		HttpSession session;
		if(req instanceof HttpServletRequest) {
			// TODO: Avoid creating session and don't store in session for default layout?
			session = ((HttpServletRequest)req).getSession();
		} else {
			assert req != null;
			session = null;
		}

		String layout = req.getParameter(Constants.LAYOUT.getName());
		// Trim and set to null if empty
		if(layout != null && (layout = layout.trim()).isEmpty()) layout = null;
		if(layout != null) {
			// Match against possibilities
			for(Skin skin : skins) {
				if(skin.getName().equals(layout)) {
					if(session != null) Constants.LAYOUT.context(session).set(layout);
					Constants.SKIN.context(req).set(skin);
					return skin;
				}
			}
		}

		// Try to reuse the currently selected skin
		layout = Constants.LAYOUT.context(session).get();
		if(layout != null) {
			// Match against possibilities
			for(Skin skin : skins) {
				if(skin.getName().equals(layout)) {
					if(session != null) Constants.LAYOUT.context(session).set(layout);
					Constants.SKIN.context(req).set(skin);
					return skin;
				}
			}
		}
		Skin skin = getDefaultSkin(skins, req);
		if(session != null) Constants.LAYOUT.context(session).set(skin.getName());
		Constants.SKIN.context(req).set(skin);
		return skin;
	}

	/**
	 * Gets the skin for the current request while {@linkplain SiteSettings#getInstance(javax.servlet.ServletContext) resolving site settings}.
	 *
	 * @see  SiteSettings#getInstance(javax.servlet.ServletContext)
	 * @see  #getSkin(com.aoindustries.web.struts.SiteSettings, javax.servlet.ServletRequest)
	 */
	public static Skin getSkin(ServletRequest req) {
		return getSkin(SiteSettings.getInstance(req.getServletContext()), req);
	}

	/**
	 * Resolves the current {@link Skin} and sets the request param {@link Constants#SKIN}.
	 *
	 * @author AO Industries, Inc.
	 */
	@WebListener
	public static class RequestListener implements ServletRequestListener {

		@Override
		public void requestInitialized(ServletRequestEvent sre) {
			// Select Skin
			getSkin(SiteSettings.getInstance(sre.getServletContext()), sre.getServletRequest());
		}

		@Override
		public void requestDestroyed(ServletRequestEvent sre) {
			// Nothing to do
		}
	}

	/**
	 * Directional references.
	 */
	public static final int
		NONE=0,
		UP=1,
		DOWN=2,
		UP_AND_DOWN=3
	;

	/**
	 * Prints the links to the alternate translations of this page.
	 *
	 * <a href="https://support.google.com/webmasters/answer/189077?hl=en">https://support.google.com/webmasters/answer/189077?hl=en</a>
	 */
	public static void printAlternativeLinks(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, String fullPath, List<Language> languages) throws IOException {
		if(languages.size()>1) {
			// Default language
			{
				Language language = languages.get(0);
				AnyURI uri = language.getUri();
				// TODO: hreflang attribute
				document.link(AnyLINK.Rel.ALTERNATE).hreflang("x-default").href(
					resp.encodeURL(
						URIEncoder.encodeURI(
							(
								uri == null
								? new AnyURI(fullPath).addEncodedParameter(Constants.LANGUAGE, URIEncoder.encodeURIComponent(language.getCode()))
								: uri
							).toASCIIString()
						)
					)
				).__();
			}
			// All languages
			for(Language language : languages) {
				AnyURI uri = language.getUri();
				document.link(AnyLINK.Rel.ALTERNATE).hreflang(language.getCode()).href(
					resp.encodeURL(
						URIEncoder.encodeURI(
							(
								uri == null
								? new AnyURI(fullPath).addEncodedParameter(Constants.LANGUAGE, URIEncoder.encodeURIComponent(language.getCode()))
								: uri
							).toASCIIString()
						)
					)
				).__();
			}
		}
	}

	/**
	 * Gets the name of this skin.
	 */
	public abstract String getName();

	/**
	 * Gets the display value for this skin in the response locale.
	 */
	public abstract String getDisplay(HttpServletRequest req, HttpServletResponse resp) throws JspException;

	/**
	 * Gets the prefix for URLs for the non-SSL server.  This should always end with a /.
	 *
	 * @deprecated  Please use {@link HttpServletUtil#getAbsoluteURL(javax.servlet.http.HttpServletRequest, java.lang.String)}
	 *              as {@code HttpServletUtil.getAbsoluteURL(req, "/")}.
	 */
	@Deprecated(forRemoval = true)
	public static String getDefaultUrlBase(HttpServletRequest req) {
		return HttpServletUtil.getAbsoluteURL(req, "/");
	}

	/**
	 * Gets the prefix for URLs for the server.  This should always end with a /.
	 */
	public String getUrlBase(HttpServletRequest req) throws JspException {
		return getDefaultUrlBase(req);
	}

	/**
	 * Configures the {@linkplain com.aoapps.web.resources.servlet.RegistryEE.Request request-scope web resources} that this skin uses.
	 * <p>
	 * Implementers should call <code>super.configureResources(…)</code> as a matter of convention, despite this default implementation doing nothing.
	 * </p>
	 */
	@SuppressWarnings("NoopMethodInAbstractClass")
	public void configureResources(ServletContext servletContext, HttpServletRequest req, HttpServletResponse resp, Registry requestRegistry, PageAttributes page) {
		// Do nothing
	}

	/**
	 * Writes all of the HTML preceding the content of the page.
	 * <p>
	 * Both the {@link Serialization} and {@link Doctype} may have been set
	 * on the request, and these must be considered in the HTML generation.
	 * </p>
	 *
	 * @see SerializationEE#get(javax.servlet.ServletContext, javax.servlet.http.HttpServletRequest)
	 * @see DoctypeEE#get(javax.servlet.ServletContext, javax.servlet.ServletRequest)
	 */
	public abstract void startSkin(HttpServletRequest req, HttpServletResponse resp, PageAttributes pageAttributes, DocumentEE document) throws JspException, IOException;

	/**
	 * Starts the content area of a page.  The content area provides additional features such as a nice border, and vertical and horizontal dividers.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 *
	 * @return  The {@link ContentEE} that should be used to write the area contents.
	 *          This is also given to {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, java.lang.String)},
	 *          {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, java.lang.String, int)},
	 *          {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int)},
	 *          {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String)},
	 *          {@link #contentHorizontalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #contentHorizontalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, boolean)},
	 *          {@link #contentHorizontalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int[], boolean)},
	 *          {@link #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int)},
	 *          and {@link #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int[])}.
	 */
	public final <
		PC extends FlowContent<PC>,
		__ extends ContentEE<__>
	> __ startContent(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		PC pc
	) throws JspException, IOException {
		return startContent(req, resp, pageAttributes, pc, new int[] {1}, null);
	}

	/**
	 * Starts the content area of a page.  The content area provides additional features such as a nice border, and vertical and horizontal dividers.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 *
	 * @return  The {@link ContentEE} that should be used to write the area contents.
	 *          This is also given to {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, java.lang.String)},
	 *          {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, java.lang.String, int)},
	 *          {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int)},
	 *          {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String)},
	 *          {@link #contentHorizontalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #contentHorizontalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, boolean)},
	 *          {@link #contentHorizontalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int[], boolean)},
	 *          {@link #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int)},
	 *          and {@link #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int[])}.
	 */
	public final <
		PC extends FlowContent<PC>,
		__ extends ContentEE<__>
	> __ startContent(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		PC pc,
		int contentColumns,
		String width
	) throws JspException, IOException {
		return startContent(req, resp, pageAttributes, pc, new int[] {contentColumns}, width);
	}

	/**
	 * Starts the content area of a page.  The content area provides additional features such as a nice border, and vertical and horizontal dividers.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 *
	 * @return  The {@link ContentEE} that should be used to write the area contents.
	 *          This is also given to {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, java.lang.String)},
	 *          {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, java.lang.String, int)},
	 *          {@link #contentTitle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int)},
	 *          {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String)},
	 *          {@link #contentHorizontalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #contentHorizontalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, boolean)},
	 *          {@link #contentHorizontalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int[], boolean)},
	 *          {@link #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE)},
	 *          {@link #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int)},
	 *          and {@link #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int[])}.
	 */
	public abstract <
		PC extends FlowContent<PC>,
		__ extends ContentEE<__>
	> __ startContent(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		PC pc,
		int[] contentColumnSpans,
		String width
	) throws JspException, IOException;

	/**
	 * Prints an entire content line including the provided title.  The colspan should match the total colspan in startContent for proper appearance
	 *
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 */
	public final void contentTitle(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		ContentEE<?> content
	) throws JspException, IOException {
		contentTitle(req, resp, content, pageAttributes.getTitle(), 1);
	}

	/**
	 * Prints an entire content line including the provided title.  The colspan should match the total colspan in startContent for proper appearance
	 *
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 */
	public final void contentTitle(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		ContentEE<?> content,
		int contentColumns
	) throws JspException, IOException {
		contentTitle(req, resp, content, pageAttributes.getTitle(), contentColumns);
	}

	/**
	 * Prints an entire content line including the provided title.  The colspan should match the total colspan in startContent for proper appearance
	 *
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 */
	public final void contentTitle(
		HttpServletRequest req,
		HttpServletResponse resp,
		ContentEE<?> content,
		String title
	) throws JspException, IOException {
		contentTitle(req, resp, content, title, 1);
	}

	/**
	 * Prints an entire content line including the provided title.  The colspan should match the total colspan in startContent for proper appearance
	 *
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 */
	public abstract void contentTitle(
		HttpServletRequest req,
		HttpServletResponse resp,
		ContentEE<?> content,
		String title,
		int contentColumns
	) throws JspException, IOException;

	/**
	 * Starts one line of content with the initial colspan set to the provided colspan.
	 *
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 *
	 * @return  The {@link FlowContent} that should be used to write the line contents.
	 *          This is also given to {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *          {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, boolean, int, int, java.lang.String, java.lang.String)},
	 *          {@link #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *          and {@link #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, int, boolean)}.
	 */
	public final <__ extends FlowContent<__>> __ startContentLine(
		HttpServletRequest req,
		HttpServletResponse resp,
		ContentEE<?> content
	) throws JspException, IOException {
		return startContentLine(req, resp, content, 1, null, null);
	}

	/**
	 * Starts one line of content with the initial colspan set to the provided colspan.
	 *
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 *
	 * @return  The {@link FlowContent} that should be used to write the line contents.
	 *          This is also given to {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *          {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, boolean, int, int, java.lang.String, java.lang.String)},
	 *          {@link #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *          and {@link #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, int, boolean)}.
	 */
	public abstract <__ extends FlowContent<__>> __ startContentLine(
		HttpServletRequest req,
		HttpServletResponse resp,
		ContentEE<?> content,
		int colspan,
		String align,
		String width
	) throws JspException, IOException;

	/**
	 * Ends one part of a line and starts the next.
	 *
	 * @param  contentLine  The {@link FlowContent} that was returned by
	 *                      {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)},
	 *                      {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String)},
	 *                      {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *                      or {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, boolean, int, int, java.lang.String, java.lang.String)}.
	 *
	 * @return  The {@link FlowContent} that should be used to write the line contents.
	 *          This is also given to {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *          {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, boolean, int, int, java.lang.String, java.lang.String)},
	 *          {@link #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *          and {@link #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, int, boolean)}.
	 */
	public final <__ extends FlowContent<__>> __ contentVerticalDivider(
		HttpServletRequest req,
		HttpServletResponse resp,
		FlowContent<?> contentLine
	) throws JspException, IOException {
		return contentVerticalDivider(req, resp, contentLine, true, 1, 1, null, null);
	}

	/**
	 * Ends one part of a line and starts the next.
	 *
	 * @param  contentLine  The {@link FlowContent} that was returned by
	 *                      {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)},
	 *                      {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String)},
	 *                      {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *                      or {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, boolean, int, int, java.lang.String, java.lang.String)}.
	 *
	 * @return  The {@link FlowContent} that should be used to write the line contents.
	 *          This is also given to {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *          {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, boolean, int, int, java.lang.String, java.lang.String)},
	 *          {@link #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *          and {@link #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, int, boolean)}.
	 */
	public abstract <__ extends FlowContent<__>> __ contentVerticalDivider(
		HttpServletRequest req,
		HttpServletResponse resp,
		FlowContent<?> contentLine,
		boolean visible,
		int colspan,
		int rowspan,
		String align,
		String width
	) throws JspException, IOException;

	/**
	 * Ends one line of content.
	 *
	 * @param  contentLine  The {@link FlowContent} that was returned by
	 *                      {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)},
	 *                      {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String)},
	 *                      {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *                      or {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, boolean, int, int, java.lang.String, java.lang.String)}.
	 */
	public final void endContentLine(
		HttpServletRequest req,
		HttpServletResponse resp,
		FlowContent<?> contentLine
	) throws JspException, IOException {
		endContentLine(req, resp, contentLine, 1, false);
	}

	/**
	 * Ends one line of content.
	 *
	 * @param  contentLine  The {@link FlowContent} that was returned by
	 *                      {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)},
	 *                      {@link #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String)},
	 *                      {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)},
	 *                      or {@link #contentVerticalDivider(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, boolean, int, int, java.lang.String, java.lang.String)}.
	 */
	public abstract void endContentLine(
		HttpServletRequest req,
		HttpServletResponse resp,
		FlowContent<?> contentLine,
		int rowspan,
		boolean endsInternal
	) throws JspException, IOException;

	/**
	 * {@linkplain #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE) Starts a content line},
	 * invokes the given line body, then
	 * {@linkplain #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent) ends the content line}.
	 *
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)
	 * @see  #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)
	 */
	public final <
		__ extends FlowContent<__>,
		Ex extends Throwable
	> void contentLine(
		HttpServletRequest req,
		HttpServletResponse resp,
		ContentEE<?> content,
		IOConsumerE<? super __, Ex> contentLine
	) throws JspException, IOException, Ex {
		this.<__, Ex>contentLine(req, resp, content, 1, null, null, 1, false, contentLine);
	}

	/**
	 * {@linkplain #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE) Starts a content line},
	 * invokes the given line body, then
	 * {@linkplain #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent) ends the content line}.
	 *
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE)
	 * @see  #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)
	 */
	public final <
		__ extends FlowContent<__>,
		Ex extends Throwable
	> void contentLine(
		HttpServletRequest req,
		HttpServletResponse resp,
		ContentEE<?> content,
		IORunnableE<Ex> contentLine
	) throws JspException, IOException, Ex {
		contentLine(req, resp, content, 1, null, null, 1, false, contentLine);
	}

	/**
	 * {@linkplain #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String) Starts a content line},
	 * invokes the given line body, then
	 * {@linkplain #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, int, boolean) ends the content line}.
	 *
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String)
	 * @see  #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, int, boolean)
	 */
	public final <
		__ extends FlowContent<__>,
		Ex extends Throwable
	> void contentLine(
		HttpServletRequest req,
		HttpServletResponse resp,
		ContentEE<?> content,
		int colspan,
		String align,
		String width,
		int endRowspan,
		boolean endsInternal,
		IOConsumerE<? super __, Ex> contentLine
	) throws JspException, IOException, Ex {
		__ flow = startContentLine(req, resp, content, colspan, align, width);
		if(contentLine != null) contentLine.accept(flow);
		endContentLine(req, resp, flow, endRowspan, endsInternal);
	}

	/**
	 * {@linkplain #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String) Starts a content line},
	 * invokes the given line body, then
	 * {@linkplain #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, int, boolean) ends the content line}.
	 *
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #startContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.ContentEE, int, java.lang.String, java.lang.String)
	 * @see  #endContentLine(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, int, boolean)
	 */
	public final <
		__ extends FlowContent<__>,
		Ex extends Throwable
	> void contentLine(
		HttpServletRequest req,
		HttpServletResponse resp,
		ContentEE<?> content,
		int colspan,
		String align,
		String width,
		int endRowspan,
		boolean endsInternal,
		IORunnableE<Ex> contentLine
	) throws JspException, IOException, Ex {
		__ flow = startContentLine(req, resp, content, colspan, align, width);
		if(contentLine != null) contentLine.run();
		endContentLine(req, resp, flow, endRowspan, endsInternal);
	}

	/**
	 * Prints a horizontal divider.
	 *
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 */
	public final void contentHorizontalDivider(
		HttpServletRequest req,
		HttpServletResponse resp,
		ContentEE<?> content
	) throws JspException, IOException {
		contentHorizontalDivider(req, resp, content, new int[] {1}, false);
	}

	/**
	 * Prints a horizontal divider of the provided colspan.
	 *
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 */
	public final void contentHorizontalDivider(
		HttpServletRequest req,
		HttpServletResponse resp,
		ContentEE<?> content,
		int colspan,
		boolean endsInternal
	) throws JspException, IOException {
		contentHorizontalDivider(req, resp, content, new int[] {colspan}, endsInternal);
	}

	/**
	 * Prints a horizontal divider of the provided colspans.
	 *
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 */
	public abstract void contentHorizontalDivider(
		HttpServletRequest req,
		HttpServletResponse resp,
		ContentEE<?> content,
		int[] colspansAndDirections,
		boolean endsInternal
	) throws JspException, IOException;

	/**
	 * Ends the content area of a page.
	 *
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 */
	public final void endContent(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		ContentEE<?> content
	) throws JspException, IOException {
		endContent(req, resp, pageAttributes, content, new int[] {1});
	}

	/**
	 * Ends the content area of a page.
	 *
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 */
	public final void endContent(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		ContentEE<?> content,
		int contentColumns
	) throws JspException, IOException {
		endContent(req, resp, pageAttributes, content, new int[] {contentColumns});
	}

	/**
	 * Ends the content area of a page.
	 *
	 * @param  content  The {@link ContentEE} that was returned by
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)}
	 *                  {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)}
	 *                  or {@link #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)}.
	 */
	public abstract void endContent(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		ContentEE<?> content,
		int[] contentColumnSpans
	) throws JspException, IOException;

	/**
	 * {@linkplain #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent) Starts a content area},
	 * invokes the given area body, then
	 * {@linkplain #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE) ends the content area}.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)
	 * @see  #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE)
	 */
	public final <
		PC extends FlowContent<PC>,
		__ extends ContentEE<__>,
		Ex extends Throwable
	> void content(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		PC pc,
		IOConsumerE<? super __, Ex> content
	) throws JspException, IOException, Ex {
		this.<PC, __, Ex>content(req, resp, pageAttributes, pc, new int[] {1}, null, new int[] {1}, content);
	}

	/**
	 * {@linkplain #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent) Starts a content area},
	 * invokes the given area body, then
	 * {@linkplain #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE) ends the content area}.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent)
	 * @see  #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE)
	 */
	public final <
		PC extends FlowContent<PC>,
		__ extends ContentEE<__>,
		Ex extends Throwable
	> void content(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		PC pc,
		IORunnableE<Ex> content
	) throws JspException, IOException, Ex {
		content(req, resp, pageAttributes, pc, new int[] {1}, null, new int[] {1}, content);
	}

	/**
	 * {@linkplain #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String) Starts a content area},
	 * invokes the given area body, then
	 * {@linkplain #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int) ends the content area}.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)
	 * @see  #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int)
	 */
	public final <
		PC extends FlowContent<PC>,
		__ extends ContentEE<__>,
		Ex extends Throwable
	> void content(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		PC pc,
		int startContentColumns,
		String width,
		int endContentColumns,
		IOConsumerE<? super __, Ex> content
	) throws JspException, IOException, Ex {
		this.<PC, __, Ex>content(req, resp, pageAttributes, pc, new int[] {startContentColumns}, width, new int[] {endContentColumns}, content);
	}

	/**
	 * {@linkplain #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String) Starts a content area},
	 * invokes the given area body, then
	 * {@linkplain #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int) ends the content area}.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int, java.lang.String)
	 * @see  #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int)
	 */
	public final <
		PC extends FlowContent<PC>,
		__ extends ContentEE<__>,
		Ex extends Throwable
	> void content(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		PC pc,
		int startContentColumns,
		String width,
		int endContentColumns,
		IORunnableE<Ex> content
	) throws JspException, IOException, Ex {
		content(req, resp, pageAttributes, pc, new int[] {startContentColumns}, width, new int[] {endContentColumns}, content);
	}

	/**
	 * {@linkplain #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String) Starts a content area},
	 * invokes the given area body, then
	 * {@linkplain #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int[]) ends the content area}.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)
	 * @see  #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int[])
	 */
	public final <
		PC extends FlowContent<PC>,
		__ extends ContentEE<__>,
		Ex extends Throwable
	> void content(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		PC pc,
		int[] startContentColumnSpans,
		String width,
		int[] endContentColumnSpans,
		IOConsumerE<? super __, Ex> content
	) throws JspException, IOException, Ex {
		__ contentEE = startContent(req, resp, pageAttributes, pc, startContentColumnSpans, width);
		if(content != null) content.accept(contentEE);
		endContent(req, resp, pageAttributes, contentEE, endContentColumnSpans);
	}

	/**
	 * {@linkplain #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String) Starts a content area},
	 * invokes the given area body, then
	 * {@linkplain #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int[]) ends the content area}.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #startContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.FlowContent, int[], java.lang.String)
	 * @see  #endContent(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoindustries.web.struts.skintags.PageAttributes, com.aoapps.html.servlet.ContentEE, int[])
	 */
	public final <
		PC extends FlowContent<PC>,
		__ extends ContentEE<__>,
		Ex extends Throwable
	> void content(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		PC pc,
		int[] startContentColumnSpans,
		String width,
		int[] endContentColumnSpans,
		IORunnableE<Ex> content
	) throws JspException, IOException, Ex {
		__ contentEE = startContent(req, resp, pageAttributes, pc, startContentColumnSpans, width);
		if(content != null) content.run();
		endContent(req, resp, pageAttributes, contentEE, endContentColumnSpans);
	}

	/**
	 * Writes all of the HTML following the content of the page,
	 * <p>
	 * Both the {@link Serialization} and {@link Doctype} may have been set
	 * on the request, and these must be considered in the HTML generation.
	 * </p>
	 *
	 * @see SerializationEE#get(javax.servlet.ServletContext, javax.servlet.http.HttpServletRequest)
	 * @see DoctypeEE#get(javax.servlet.ServletContext, javax.servlet.ServletRequest)
	 */
	public abstract void endSkin(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		DocumentEE document
	) throws JspException, IOException;

	/**
	 * Begins a lighter colored area of the site.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 *
	 * @return  The {@link FlowContent} that should be used to write the area contents.
	 *          This is also given to {@link #endLightArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)}
	 *          to finish the area.
	 */
	// TODO: Rename startLightArea for consistency
	public abstract <
		PC extends FlowContent<PC>,
		__ extends FlowContent<__>
	> __ beginLightArea(
		HttpServletRequest req,
		HttpServletResponse resp,
		PC pc,
		String align,
		String width,
		boolean nowrap
	) throws JspException, IOException;

	/**
	 * Ends a lighter area of the site.
	 *
	 * @param  lightArea  The {@link FlowContent} that was returned by
	 *                    {@link #beginLightArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, java.lang.String, java.lang.String, boolean)}.
	 */
	public abstract void endLightArea(
		HttpServletRequest req,
		HttpServletResponse resp,
		FlowContent<?> lightArea
	) throws JspException, IOException;

	/**
	 * {@linkplain #beginLightArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, java.lang.String, java.lang.String, boolean) Begins a light area},
	 * invokes the given area body, then
	 * {@linkplain #endLightArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent) ends the light area}.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #beginLightArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, java.lang.String, java.lang.String, boolean)
	 * @see  #endLightArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)
	 */
	public final <
		PC extends FlowContent<PC>,
		__ extends FlowContent<__>,
		Ex extends Throwable
	> void lightArea(
		HttpServletRequest req,
		HttpServletResponse resp,
		PC pc,
		String align,
		String width,
		boolean nowrap,
		IOConsumerE<? super __, Ex> lightArea
	) throws JspException, IOException, Ex {
		__ flow = beginLightArea(req, resp, pc, align, width, nowrap);
		if(lightArea != null) lightArea.accept(flow);
		endLightArea(req, resp, flow);
	}

	/**
	 * {@linkplain #beginLightArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, java.lang.String, java.lang.String, boolean) Begins a light area},
	 * invokes the given area body, then
	 * {@linkplain #endLightArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent) ends the light area}.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #beginLightArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, java.lang.String, java.lang.String, boolean)
	 * @see  #endLightArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)
	 */
	public final <
		PC extends FlowContent<PC>,
		Ex extends Throwable
	> void lightArea(
		HttpServletRequest req,
		HttpServletResponse resp,
		PC pc,
		String align,
		String width,
		boolean nowrap,
		IORunnableE<Ex> lightArea
	) throws JspException, IOException, Ex {
		FlowContent<?> flow = beginLightArea(req, resp, pc, align, width, nowrap);
		if(lightArea != null) lightArea.run();
		endLightArea(req, resp, flow);
	}

	/**
	 * Begins a white area of the site.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 *
	 * @return  The {@link FlowContent} that should be used to write the area contents.
	 *          This is also given to {@link #endWhiteArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)}
	 *          to finish the area.
	 */
	// TODO: Rename startWhiteArea for consistency
	public abstract <
		PC extends FlowContent<PC>,
		__ extends FlowContent<__>
	> __ beginWhiteArea(
		HttpServletRequest req,
		HttpServletResponse resp,
		PC pc,
		String align,
		String width,
		boolean nowrap
	) throws JspException, IOException;

	/**
	 * Ends a white area of the site.
	 *
	 * @param  whiteArea  The {@link FlowContent} that was returned by
	 *                    {@link #beginWhiteArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, java.lang.String, java.lang.String, boolean)}.
	 */
	public abstract void endWhiteArea(
		HttpServletRequest req,
		HttpServletResponse resp,
		FlowContent<?> whiteArea
	) throws JspException, IOException;

	/**
	 * {@linkplain #beginWhiteArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, java.lang.String, java.lang.String, boolean) Begins a white area},
	 * invokes the given area body, then
	 * {@linkplain #endWhiteArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent) ends the white area}.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <__>  This content model, which will be the parent content model of child elements
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #beginWhiteArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, java.lang.String, java.lang.String, boolean)
	 * @see  #endWhiteArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)
	 */
	public final <
		PC extends FlowContent<PC>,
		__ extends FlowContent<__>,
		Ex extends Throwable
	> void whiteArea(
		HttpServletRequest req,
		HttpServletResponse resp,
		PC pc,
		String align,
		String width,
		boolean nowrap,
		IOConsumerE<? super __, Ex> whiteArea
	) throws JspException, IOException, Ex {
		__ flow = beginWhiteArea(req, resp, pc, align, width, nowrap);
		if(whiteArea != null) whiteArea.accept(flow);
		endWhiteArea(req, resp, flow);
	}

	/**
	 * {@linkplain #beginWhiteArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, java.lang.String, java.lang.String, boolean) Begins a white area},
	 * invokes the given area body, then
	 * {@linkplain #endWhiteArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent) ends the white area}.
	 *
	 * @param  <PC>  The parent content model this area is within
	 * @param  <Ex>  An arbitrary exception type that may be thrown
	 *
	 * @see  #beginWhiteArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent, java.lang.String, java.lang.String, boolean)
	 * @see  #endWhiteArea(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.aoapps.html.servlet.FlowContent)
	 */
	public final <
		PC extends FlowContent<PC>,
		Ex extends Throwable
	> void whiteArea(
		HttpServletRequest req,
		HttpServletResponse resp,
		PC pc,
		String align,
		String width,
		boolean nowrap,
		IORunnableE<Ex> whiteArea
	) throws JspException, IOException, Ex {
		FlowContent<?> flow = beginWhiteArea(req, resp, pc, align, width, nowrap);
		if(whiteArea != null) whiteArea.run();
		endWhiteArea(req, resp, flow);
	}

	public static class Language {
		private final String code;
		private final com.aoapps.lang.i18n.Resources resources;
		private final String displayKey;
		private final String flagOnSrcKey;
		private final String flagOffSrcKey;
		private final String flagWidthKey;
		private final String flagHeightKey;
		private final AnyURI uri;

		/**
		 * @param uri the constant URL to use or {@code null} to have automatically set.
		 */
		public Language(
			String code,
			com.aoapps.lang.i18n.Resources resources,
			String displayKey,
			String flagOnSrcKey,
			String flagOffSrcKey,
			String flagWidthKey,
			String flagHeightKey,
			AnyURI uri
		) {
			this.code = code;
			this.resources = resources;
			this.displayKey = displayKey;
			this.flagOnSrcKey = flagOnSrcKey;
			this.flagOffSrcKey = flagOffSrcKey;
			this.flagWidthKey = flagWidthKey;
			this.flagHeightKey = flagHeightKey;
			this.uri = uri;
		}

		public String getCode() {
			return code;
		}

		public String getDisplay(HttpServletRequest req, Locale locale) throws JspException {
			return resources.getMessage(locale, displayKey);
		}

		public String getFlagOnSrc(HttpServletRequest req, Locale locale) throws JspException {
			return resources.getMessage(locale, flagOnSrcKey);
		}

		public String getFlagOffSrc(HttpServletRequest req, Locale locale) throws JspException {
			return resources.getMessage(locale, flagOffSrcKey);
		}

		public int getFlagWidth(HttpServletRequest req, Locale locale) throws JspException {
			return Integer.parseInt(resources.getMessage(locale, flagWidthKey));
		}

		public int getFlagHeight(HttpServletRequest req, Locale locale) throws JspException {
			return Integer.parseInt(resources.getMessage(locale, flagHeightKey));
		}

		/**
		 * Gets the {@linkplain AnyURI URL} to use for this language or <code>null</code>
		 * to change language on the existing page.
		 */
		public AnyURI getUri() {
			return uri;
		}
	}

	/**
	 * Prints the auto index of all the page siblings.
	 */
	public abstract void printAutoIndex(
		HttpServletRequest req,
		HttpServletResponse resp,
		PageAttributes pageAttributes,
		DocumentEE document
	) throws JspException, IOException;

	/**
	 * Begins a popup group.
	 */
	public abstract void beginPopupGroup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId) throws JspException, IOException;

	/**
	 * Ends a popup group.
	 */
	public abstract void endPopupGroup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId) throws JspException, IOException;

	/**
	 * Begins a popup that is in a popup group.
	 */
	public abstract void beginPopup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId, long popupId, String width) throws JspException, IOException;

	/**
	 * Prints a popup close link/image/button for a popup that is part of a popup group.
	 */
	public abstract void printPopupClose(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId, long popupId) throws JspException, IOException;

	/**
	 * Ends a popup that is in a popup group.
	 */
	public abstract void endPopup(HttpServletRequest req, HttpServletResponse resp, DocumentEE document, long groupId, long popupId, String width) throws JspException, IOException;
}
