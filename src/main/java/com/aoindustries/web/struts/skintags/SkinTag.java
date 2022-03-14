/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2019, 2020, 2021, 2022  AO Industries, Inc.
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

import com.aoapps.encoding.Doctype;
import com.aoapps.encoding.Serialization;
import com.aoapps.encoding.servlet.DoctypeEE;
import com.aoapps.encoding.servlet.SerializationEE;
import com.aoapps.html.any.AnyDocument;
import com.aoapps.html.servlet.DocumentEE;
import com.aoapps.html.servlet.FlowContent;
import com.aoapps.lang.LocalizedIllegalArgumentException;
import com.aoapps.servlet.ServletUtil;
import com.aoapps.servlet.jsp.LocalizedJspTagException;
import com.aoapps.taglib.HtmlTag;
import com.aoindustries.web.struts.Constants;
import com.aoindustries.web.struts.Formtype;
import com.aoindustries.web.struts.Globals;
import static com.aoindustries.web.struts.Resources.PACKAGE_RESOURCES;
import com.aoindustries.web.struts.Skin;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 * Writes the skin header and footer.
 *
 * @author  AO Industries, Inc.
 */
public class SkinTag extends PageAttributesBodyTag implements TryCatchFinally {

	/**
	 * Gets the current skin from the request.  It is assumed the skin is already set.  Will throw an exception if not available.
	 */
	public static Skin getSkin(ServletRequest request) throws JspException {
		Skin skin = Constants.SKIN.context(request).get();
		if(skin == null) {
			throw new LocalizedJspTagException(PACKAGE_RESOURCES, "skintags.unableToFindSkinInRequest");
		}
		return skin;
	}

	public SkinTag() {
		init();
	}

	private static final long serialVersionUID = 2L;

	private Serialization serialization;
	public void setSerialization(String serialization) {
		if(serialization == null) {
			this.serialization = null;
		} else {
			serialization = serialization.trim();
			this.serialization = (serialization.isEmpty() || "auto".equalsIgnoreCase(serialization)) ? null : Serialization.valueOf(serialization.toUpperCase(Locale.ROOT));
		}
	}

	private Doctype doctype;
	public void setDoctype(String doctype) {
		if(doctype == null) {
			this.doctype = null;
		} else {
			doctype = doctype.trim();
			this.doctype = (doctype.isEmpty() || "default".equalsIgnoreCase(doctype)) ? null : Doctype.valueOf(doctype.toUpperCase(Locale.ROOT));
		}
	}

	private Boolean autonli;
	public void setAutonli(String autonli) {
		if(autonli == null) {
			this.autonli = null;
		} else {
			autonli = autonli.trim();
			if(autonli.isEmpty() || "auto".equalsIgnoreCase(autonli)) {
				this.autonli = null;
			} else if("true".equalsIgnoreCase(autonli)) {
				this.autonli = true;
			} else if("false".equalsIgnoreCase(autonli)) {
				this.autonli = false;
			} else {
				throw new LocalizedIllegalArgumentException(HtmlTag.RESOURCES, "autonli.invalid", autonli);
			}
		}
	}

	private Boolean indent;
	public void setIndent(String indent) {
		if(indent == null) {
			this.indent = null;
		} else {
			indent = indent.trim();
			if(indent.isEmpty() || "auto".equalsIgnoreCase(indent)) {
				this.indent = null;
			} else if("true".equalsIgnoreCase(indent)) {
				this.indent = true;
			} else if("false".equalsIgnoreCase(indent)) {
				this.indent = false;
			} else {
				throw new LocalizedIllegalArgumentException(HtmlTag.RESOURCES, "indent.invalid", indent);
			}
		}
	}

	private String layout;
	public void setLayout(String layout) {
		this.layout = layout.trim();
	}

	private Formtype formtype;
	public void setFormtype(String formtype) {
		if(formtype == null) {
			this.formtype = null;
		} else {
			formtype = formtype.trim();
			this.formtype = (formtype.isEmpty() || "none".equalsIgnoreCase(formtype)) ? null : Formtype.valueOf(formtype.toUpperCase(Locale.ROOT));
		}
	}

	private String onload;
	public void setOnload(String onload) {
		this.onload = onload;
	}

	// Values that are used in doFinally
	private transient Serialization oldSerialization;
	private transient boolean setSerialization;
	private transient Doctype oldDoctype;
	private transient boolean setDoctype;
	private transient Boolean oldAutonli;
	private transient boolean setAutonli;
	private transient Boolean oldIndent;
	private transient boolean setIndent;
	// Values only used between doStartTag and doEndTag
	private transient FlowContent<?> flow;

	private void init() {
		serialization = null;
		doctype = Doctype.DEFAULT;
		autonli = null;
		indent = null;
		layout = "normal";
		formtype = null;
		onload = null;
		oldSerialization = null;
		setSerialization = false;
		oldDoctype = null;
		setDoctype = false;
		oldAutonli = null;
		setAutonli = false;
		oldIndent = null;
		setIndent = false;
		flow = null;
	}

	@Override
	public int doStartTag(PageAttributes pageAttributes) throws JspException, IOException {
		try {
			pageAttributes.setLayout(layout);
			pageAttributes.setFormtype(formtype);
			pageAttributes.setOnload(onload);

			ServletContext servletContext = pageContext.getServletContext();
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();

			if(serialization == null) {
				serialization = SerializationEE.get(servletContext, request);
				oldSerialization = null;
				setSerialization = false;
			} else {
				oldSerialization = SerializationEE.replace(request, serialization);
				setSerialization = true;
			}
			if(doctype == null) {
				doctype = DoctypeEE.get(servletContext, request);
				oldDoctype = null;
				setDoctype = false;
			} else {
				oldDoctype = DoctypeEE.replace(request, doctype);
				setDoctype = true;
			}
			if(autonli == null) {
				autonli = DocumentEE.getAutonli(servletContext, request);
				oldAutonli = null;
				setAutonli = false;
			} else {
				oldAutonli = DocumentEE.replaceAutonli(request, autonli);
				setAutonli = true;
			}
			assert autonli != null;
			if(indent == null) {
				indent = DocumentEE.getIndent(servletContext, request);
				oldIndent = null;
				setIndent = false;
			} else {
				oldIndent = DocumentEE.replaceIndent(request, indent);
				setIndent = true;
			}
			assert indent != null;

			// Clear the output buffer
			response.resetBuffer();

			// Set the content type
			ServletUtil.setContentType(response, serialization.getContentType(), AnyDocument.ENCODING.name());

			// Set the response locale from the Struts locale
			Locale locale = Globals.LOCALE_KEY.context(pageContext.getSession()).get();
			response.setLocale(locale);

			// Set the Struts XHTML mode by Serialization
			Globals.XHTML_KEY.context(pageContext).set(Boolean.toString(serialization == Serialization.XML));

			// Start the skin
			flow = SkinTag.getSkin(request).startPage(
				request,
				response,
				pageAttributes,
				new DocumentEE(servletContext, request, response, pageContext.getOut(), autonli, indent)
			);

			return EVAL_BODY_INCLUDE;
		} catch(ServletException e) {
			throw new JspTagException(e);
		}
	}

	@Override
	public int doEndTag(PageAttributes pageAttributes) throws JspException, IOException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		assert flow != null;
		flow.getDocument().setOut(pageContext.getOut());
		SkinTag.getSkin(request).endPage(
			request,
			(HttpServletResponse)pageContext.getResponse(),
			pageAttributes,
			flow
		);
		return EVAL_PAGE;
	}

	@Override
	public void doCatch(Throwable t) throws Throwable {
		throw t;
	}

	@Override
	public void doFinally() {
		try {
			ServletRequest request = pageContext.getRequest();
			if(setIndent) DocumentEE.setIndent(request, oldIndent);
			if(setAutonli) DocumentEE.setIndent(request, oldAutonli);
			if(setDoctype) DoctypeEE.set(request, oldDoctype);
			if(setSerialization) SerializationEE.set(request, oldSerialization);
		} finally {
			init();
		}
	}
}
