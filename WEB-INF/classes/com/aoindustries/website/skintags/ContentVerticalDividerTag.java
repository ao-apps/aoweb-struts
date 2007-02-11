package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.Constants;
import com.aoindustries.website.Skin;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

/**
 * @author  AO Industries, Inc.
 */
public class ContentVerticalDividerTag extends TagSupport {

    private boolean visible;
    private int colspan;
    private int rowspan;
    private String align;

    public ContentVerticalDividerTag() {
        init();
    }

    private void init() {
        this.visible = true;
        this.colspan = 1;
        this.rowspan = 1;
        this.align = null;
    }

    public int doStartTag() throws JspException {
        try {
            ContentLineTag contentLineTag = (ContentLineTag)findAncestorWithClass(this, ContentLineTag.class);
            if(contentLineTag==null) {
                HttpSession session = pageContext.getSession();
                Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
                MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
                throw new JspException(applicationResources.getMessage(locale, "skintags.ContentVerticalDividerTag.mustNestInContentLineTag"));
            }

            Skin skin = SkinTag.getSkin(pageContext);

            HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
            skin.printContentVerticalDivider(req, pageContext.getOut(), visible, colspan, rowspan, align);

            contentLineTag.setLastRowSpan(rowspan);

            return SKIP_BODY;
        } finally {
            init();
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }
}
