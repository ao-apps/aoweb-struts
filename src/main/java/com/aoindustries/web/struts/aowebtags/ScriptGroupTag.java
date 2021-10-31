/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2019, 2020, 2021  AO Industries, Inc.
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
package com.aoindustries.web.struts.aowebtags;

import com.aoapps.encoding.MediaWriter;
import com.aoapps.html.servlet.DocumentEE;
import com.aoapps.lang.util.Sequence;
import com.aoapps.lang.util.UnsynchronizedSequence;
import com.aoapps.servlet.attribute.ScopeEE;
import com.aoapps.servlet.jsp.LocalizedJspTagException;
import static com.aoindustries.web.struts.Resources.PACKAGE_RESOURCES;
import java.io.CharArrayWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author  AO Industries, Inc.
 */
public class ScriptGroupTag extends BodyTagSupport {

	/**
	 * The maximum buffer size that will be allowed between requests.  This is
	 * so that an unusually large request will not continue to use lots of heap
	 * space.
	 */
	private static final int MAX_PERSISTENT_BUFFER_SIZE = 1024 * 1024;

	/**
	 * The request attribute name used to store the sequence.
	 */
	private static final ScopeEE.Request.Attribute<Sequence> SEQUENCE_REQUEST_ATTRIBUTE =
		ScopeEE.REQUEST.attribute(ScriptGroupTag.class.getName() + ".sequence");

	private static final long serialVersionUID = 1L;

	private String onloadMode;

	private CharArrayWriter scriptOut = new CharArrayWriter();

	public ScriptGroupTag() {
		init();
	}

	private void init() {
		onloadMode = "none";
		// Bring back down to size if exceeds MAX_PERSISTENT_BUFFER_SIZE
		if(scriptOut.size() > MAX_PERSISTENT_BUFFER_SIZE) {
			scriptOut = new CharArrayWriter();
		} else {
			scriptOut.reset();
		}
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			if(scriptOut.size() > 0) {
				HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
				DocumentEE document = new DocumentEE(
					pageContext.getServletContext(),
					request,
					(HttpServletResponse)pageContext.getResponse(),
					pageContext.getOut(),
					false, // Do not add extra newlines to JSP
					false  // Do not add extra indentation to JSP
				);
				try (MediaWriter script = document.script()._c()) {
					if("none".equals(onloadMode)) {
						scriptOut.writeTo(script);
					} else {
						Sequence sequence = SEQUENCE_REQUEST_ATTRIBUTE.context(request).computeIfAbsent(__ -> new UnsynchronizedSequence());
						String sequenceId = Long.toString(sequence.getNextSequenceValue());
						boolean wroteScript = false;
						script.write("  var scriptOutOldOnload"); script.write(sequenceId); script.write("=window.onload;\n"
								+ "  function scriptOutOnload"); script.write(sequenceId); script.write("() {\n");
						if("before".equals(onloadMode)) {
							scriptOut.writeTo(script);
							wroteScript = true;
						}
						script.write("    if(scriptOutOldOnload"); script.write(sequenceId); script.write(") {\n"
								+ "      scriptOutOldOnload"); script.write(sequenceId); script.write("();\n"
								+ "      scriptOutOldOnload"); script.write(sequenceId); script.write("=null;\n"
								+ "    }\n");
						if(!wroteScript && "after".equals(onloadMode)) {
							scriptOut.writeTo(script);
							wroteScript = true;
						}
						script.write("  }\n"
								+ "  window.onload = scriptOutOnload"); script.write(sequenceId); script.write(';');
						if(!wroteScript) {
							throw new LocalizedJspTagException(PACKAGE_RESOURCES, "aowebtags.ScriptGroupTag.onloadMode.invalid", onloadMode);
						}
					}
				}
			}
			return EVAL_PAGE;
		} catch(IOException err) {
			throw new JspTagException(err);
		} finally {
			init();
		}
	}

	public String getOnloadMode() {
		return onloadMode;
	}

	public void setOnloadMode(String onloadMode) {
		this.onloadMode = onloadMode;
	}

	/**
	 * Gets the buffered used to store the JavaScript.
	 */
	Appendable getScriptOut() {
		return scriptOut;
	}
}
