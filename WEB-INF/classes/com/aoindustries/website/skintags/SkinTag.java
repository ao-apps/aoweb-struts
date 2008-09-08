package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2008 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
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
 * Writes the skin header and footer.
 *
 * @author  AO Industries, Inc.
 */
public class SkinTag extends PageAttributesTag {

    /**
     * Gets the current skin from the session.  It is assumed the skin is already set.  Will throw an exception if not available.
     */
    static Skin getSkin(PageContext pageContext) throws JspException {
        Skin skin = (Skin)pageContext.getAttribute(Constants.SKIN, PageContext.REQUEST_SCOPE);
        if(skin==null) {
            HttpSession session = pageContext.getSession();
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            MessageResources applicationResources = (MessageResources)pageContext.getRequest().getAttribute("/ApplicationResources");
            throw new JspException(applicationResources.getMessage(locale, "skintags.unableToFindSkinInRequest"));
        }
        return skin;
    }

    private String onLoad;

    public SkinTag() {
        init();
    }

    private void init() {
        onLoad = null;
    }

    public int doStartTag(PageAttributes pageAttributes) throws JspException {
        pageAttributes.setOnLoad(onLoad);

        Skin skin = SkinTag.getSkin(pageContext);

        HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
        HttpServletResponse resp = (HttpServletResponse)pageContext.getResponse();
        skin.startSkin(req, resp, pageContext.getOut(), pageAttributes);

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag(PageAttributes pageAttributes) throws JspException {
        try {
            Skin skin = SkinTag.getSkin(pageContext);

            HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
            skin.endSkin(req, pageContext.getOut(), pageAttributes);

            return EVAL_PAGE;
        } finally {
            init();
        }
    }

    public String getOnLoad() {
        return onLoad;
    }
    
    public void setOnLoad(String onLoad) {
        this.onLoad = onLoad;
    }
}
