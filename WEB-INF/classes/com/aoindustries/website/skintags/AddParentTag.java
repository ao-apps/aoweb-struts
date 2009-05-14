package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import java.util.Locale;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

/**
 * Adds a parent to the hierarchy above this page.
 *
 * @author  AO Industries, Inc.
 */
public class AddParentTag extends PageAttributesTag {

    private String title;
    private String navImageAlt;
    private String description;
    private boolean useEncryptionSet;
    private boolean useEncryption;
    private String path;

    public AddParentTag() {
        init();
    }

    private void init() {
        title = null;
        navImageAlt = null;
        description = null;
        useEncryptionSet = false;
        useEncryption = false;
        path = null;
    }

    public int doStartTag(PageAttributes pageAttributes) {
        return EVAL_BODY_BUFFERED;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setNavImageAlt(String navImageAlt) {
        this.navImageAlt = navImageAlt;
    }
    
    void setDescription(String description) {
        this.description = description;
    }

    public boolean getUseEncryption() {
        return useEncryption;
    }
    
    public void setUseEncryption(boolean useEncryption) {
        this.useEncryptionSet = true;
        this.useEncryption = useEncryption;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int doEndTag(PageAttributes pageAttributes) throws JspException {
        try {
            if(title==null) {
                HttpSession session = pageContext.getSession();
                Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
                MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
                throw new JspException(applicationResources.getMessage(locale, "skintags.AddParentTag.needsTitleTag"));
            }
            String navImageAlt = this.navImageAlt;
            if(navImageAlt == null || navImageAlt.length()==0) navImageAlt=title;
            String description = this.description;
            if(description == null || description.length()==0) description=title;
            pageAttributes.addParent(new Page(title, navImageAlt, description, useEncryptionSet ? useEncryption : pageContext.getRequest().isSecure(), path));
            return EVAL_PAGE;
        } finally {
            init();
        }
    }
}
