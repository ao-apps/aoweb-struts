/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.skintags;

import com.aoindustries.taglib.HtmlTag;
import java.io.IOException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Sets the content type for the page.
 *
 * @author  AO Industries, Inc.
 */
public class SetContentTypeTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    public SetContentTypeTag() {
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            // Skin skin = SkinTag.getSkin(pageContext);

            ServletResponse response = pageContext.getResponse();

            boolean allowApplicationXhtmlXml = "true".equals(pageContext.getServletContext().getInitParameter("com.aoindustries.website.skintags.SetContentTypeTag.allowApplicationXhtmlXml"));
            final String contentType;
            if(!allowApplicationXhtmlXml) {
                contentType = HtmlTag.CONTENT_TYPE_HTML;
            } else {
                HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
                contentType = HtmlTag.getXhtmlContentType(request);
            }

            // Set the content type
            // System.out.println("DEBUG: SetContentTypeTag: doStartTag: response.getContentType()="+response.getContentType());
            response.setContentType(contentType);
            // System.out.println("DEBUG: SetContentTypeTag: doStartTag: response.setContentType("+contentType+")");
            // System.out.println("DEBUG: SetContentTypeTag: doStartTag: response.getContentType()="+response.getContentType());

            // Set the encoding
            // System.out.println("DEBUG: SetContentTypeTag: doStartTag: response.getCharacterEncoding()="+response.getCharacterEncoding());
            response.setCharacterEncoding("UTF-8");
            // System.out.println("DEBUG: SetContentTypeTag: doStartTag: response.setCharacterEncoding("+charset+")");
            // System.out.println("DEBUG: SetContentTypeTag: doStartTag: response.getCharacterEncoding()="+response.getCharacterEncoding());

            JspWriter out = pageContext.getOut();
            out.clear();
            response.resetBuffer(); // Cannot have even whitespace before the xml declaration
            // Disabled per A.1 at http://www.w3.org/TR/xhtml-media-types/#compatGuidelines
            //if(isApplicationXhtmlXml) {
                // Also write the xml declaration
                // out.print("<?xml version=\"1.0\" encoding=\"");
                // out.print(charset);
                // out.print("\"?>");
            //}

            return SKIP_BODY;
        } catch(IOException err) {
            throw new JspException(err);
        }
    }
}
