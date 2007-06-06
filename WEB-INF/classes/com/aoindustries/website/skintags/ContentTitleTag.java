package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.util.StringUtility;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import com.aoindustries.website.*;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

/**
 * @author  AO Industries, Inc.
 */
public class ContentTitleTag extends BodyTagSupport {

    public ContentTitleTag() {
    }

    public int doStartTag() {
        return EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
        String title = getBodyContent().getString().trim();

        ContentTag contentTag = (ContentTag)findAncestorWithClass(this, ContentTag.class);
        if(contentTag==null) {
            HttpSession session = pageContext.getSession();
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
            throw new JspException(applicationResources.getMessage(locale, "skintags.ContentTitleTag.mustNestInContentTag"));
        }

        Skin skin = SkinTag.getSkin(pageContext);

        int[] colspans = contentTag.getColspansParsed();
        int totalColspan = 0;
        for(int c=0;c<colspans.length;c++) totalColspan += colspans[c];

        HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
        HttpServletResponse resp = (HttpServletResponse)pageContext.getResponse();
        skin.printContentTitle(req, resp, pageContext.getOut(), title, totalColspan);

        return EVAL_PAGE;
    }
}
