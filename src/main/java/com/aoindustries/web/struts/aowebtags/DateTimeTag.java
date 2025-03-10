/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2015, 2016, 2019, 2020, 2021, 2022, 2024  AO Industries, Inc.
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

import static com.aoapps.encoding.JavaScriptInXhtmlEncoder.encodeJavascriptInXhtml;

import com.aoapps.html.servlet.DocumentEE;
import com.aoapps.html.servlet.Union_Palpable_Phrasing;
import com.aoapps.lang.util.Sequence;
import com.aoapps.lang.util.UnsynchronizedSequence;
import com.aoapps.servlet.attribute.ScopeEE;
import com.aoapps.servlet.jsp.tagext.JspTagUtils;
import com.aoapps.sql.SQLUtility;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author  AO Industries, Inc.
 */
public class DateTimeTag extends BodyTagSupport {

  /**
   * The request attribute name used to store the sequence.
   */
  private static final ScopeEE.Request.Attribute<Sequence> SEQUENCE_REQUEST_ATTRIBUTE =
      ScopeEE.REQUEST.attribute(DateTimeTag.class.getName() + ".sequence");

  /**
   * Writes a JavaScript script tag that shows a date and time in the user's locale.
   *
   * <p>Because this needs to modify the DOM it can lead to poor performance or large data sets.
   * To provide more performance options, the JavaScript is written to scriptOut.  This could
   * then be buffered into one long script to execute at once or using body.onload.</p>
   *
   * <p>The provided sequence should start at one for any given HTML page because parts of the
   * script will only be written when the sequence is equal to one.</p>
   *
   * @see  SQLUtility#formatDateTime(long)
   */
  public static void writeDateTimeJavascript(long date, Sequence sequence, Union_Palpable_Phrasing<?> content, Appendable scriptOut) throws IOException {
    String dateTimeString = SQLUtility.formatDateTime(date);
    long id = sequence.getNextSequenceValue();
    String idString = Long.toString(id);
    // Write the element
    content.span().id(idAttr -> idAttr.append("chainWriterDateTime").append(idString)).__(dateTimeString);
    // Write the shared script only on first sequence
    if (id == 1) {
      scriptOut.append("  function chainWriterUpdateDateTime(id, millis, serverValue) {\n"
          + "    if (document.getElementById) {\n"
          + "      var date=new Date(millis);\n"
          + "      var clientValue=date.getFullYear() + \"-\";\n"
          + "      var month=date.getMonth()+1;\n"
          + "      if (month<10) {\n"
          + "        clientValue+=\"0\";\n"
          + "      }\n"
          + "      clientValue+=month+\"-\";\n"
          + "      var day=date.getDate();\n"
          + "      if (day<10) {\n"
          + "        clientValue+=\"0\";\n"
          + "      }\n"
          + "      clientValue+=day+\" \";\n"
          + "      var hour=date.getHours();\n"
          + "      if (hour<10) {\n"
          + "        clientValue+=\"0\";\n"
          + "      }\n"
          + "      clientValue+=hour+\":\";\n"
          + "      var minute=date.getMinutes();\n"
          + "      if (minute<10) {\n"
          + "        clientValue+=\"0\";\n"
          + "      }\n"
          + "      clientValue+=minute+\":\";\n"
          + "      var second=date.getSeconds();\n"
          + "      if (second<10) {\n"
          + "        clientValue+=\"0\";\n"
          + "      }\n"
          + "      clientValue+=second;\n"
          + "      if (clientValue != serverValue) {\n"
          + "        document.getElementById(\"chainWriterDateTime\"+id).firstChild.nodeValue=clientValue;\n"
          + "      }\n"
          + "    }\n"
          + "  }\n");
    }
    scriptOut.append("  chainWriterUpdateDateTime(");
    scriptOut.append(idString);
    scriptOut.append(", ");
    scriptOut.append(Long.toString(date));
    scriptOut.append(", \"");
    encodeJavascriptInXhtml(dateTimeString, scriptOut);
    scriptOut.append("\");\n");
  }

  /**
   * Writes a JavaScript script tag that shows a date and time in the user's locale.
   * Prints nothing when the date is {@code null}.
   *
   * <p>Because this needs to modify the DOM it can lead to poor performance or large data sets.
   * To provide more performance options, the JavaScript is written to scriptOut.  This could
   * then be buffered into one long script to execute at once or using body.onload.</p>
   *
   * <p>The provided sequence should start at one for any given HTML page because parts of the
   * script will only be written when the sequence is equal to one.</p>
   *
   * @see  SQLUtility#formatDateTime(java.lang.Long)
   */
  public static void writeDateTimeJavascript(Long date, Sequence sequence, Union_Palpable_Phrasing<?> content, Appendable scriptOut) throws IOException {
    if (date != null) {
      writeDateTimeJavascript(date.longValue(), sequence, content, scriptOut);
    }
  }

  /**
   * Writes a JavaScript script tag that shows a date and time in the user's locale.
   * Prints nothing when the date is {@code null}.
   *
   * <p>Because this needs to modify the DOM it can lead to poor performance or large data sets.
   * To provide more performance options, the JavaScript is written to scriptOut.  This could
   * then be buffered into one long script to execute at once or using body.onload.</p>
   *
   * <p>The provided sequence should start at one for any given HTML page because parts of the
   * script will only be written when the sequence is equal to one.</p>
   *
   * @see  SQLUtility#formatDateTime(java.util.Date)
   */
  public static void writeDateTimeJavascript(Date date, Sequence sequence, Union_Palpable_Phrasing<?> content, Appendable scriptOut) throws IOException {
    if (date != null) {
      writeDateTimeJavascript(date.getTime(), sequence, content, scriptOut);
    }
  }

  private static final long serialVersionUID = 1L;

  @Override
  public int doStartTag() throws JspException {
    return EVAL_BODY_BUFFERED;
  }

  @Override
  public int doEndTag() throws JspException {
    try {
      String millisString = getBodyContent().getString().trim();
      if (!millisString.isEmpty()) {
        Long date = Long.parseLong(millisString);
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        DocumentEE document = new DocumentEE(
            pageContext.getServletContext(),
            request,
            (HttpServletResponse) pageContext.getResponse(),
            pageContext.getOut(),
            false, // Do not add extra newlines to JSP
            false  // Do not add extra indentation to JSP
        );
        // Resolve the sequence
        Sequence sequence = SEQUENCE_REQUEST_ATTRIBUTE.context(request)
            .computeIfAbsent(name -> new UnsynchronizedSequence());
        // Resolve the scriptOut
        Optional<ScriptGroupTag> scriptGroupTag = JspTagUtils.findAncestor(this, ScriptGroupTag.class);
        if (scriptGroupTag.isPresent()) {
          writeDateTimeJavascript(date, sequence, document, scriptGroupTag.get().getScriptOut());
        } else {
          CharArrayWriter scriptOut = new CharArrayWriter();
          writeDateTimeJavascript(date, sequence, document, scriptOut);
          try (Writer script = document.script()._c()) {
            scriptOut.writeTo(script);
          }
        }
      }
      return EVAL_PAGE;
    } catch (IOException err) {
      throw new JspTagException(err);
    }
  }
}
