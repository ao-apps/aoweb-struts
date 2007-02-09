package com.aoindustries.website.skintags;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.io.ChainWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import com.aoindustries.website.*;

/**
 * Writes the skin header and footer.
 *
 * @author  AO Industries, Inc.
 */
public class SkinTag extends PageAttributesTag {

    private String onLoad;

    public SkinTag() {
        init();
    }

    private void init() {
        onLoad = null;
    }

    public int doStartTag(PageAttributes pageAttributes) throws JspException {
        try {
            pageAttributes.setOnLoad(onLoad);

            ChainWriter out = new ChainWriter(pageContext.getOut());
            Skin skin = (Skin)pageContext.getAttribute(Constants.SKIN, PageContext.REQUEST_SCOPE);
            if(skin==null) throw new JspException("Unable to find skin in the request attributes");

            HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
            skin.startSkin(req, out, pageAttributes);

            return EVAL_BODY_INCLUDE;
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public int doEndTag(PageAttributes pageAttributes) throws JspException {
        try {
            ChainWriter out = new ChainWriter(pageContext.getOut());
            Skin skin = (Skin)pageContext.getAttribute(Constants.SKIN, PageContext.REQUEST_SCOPE);
            if(skin==null) throw new JspException("Unable to find skin in the request attributes");

            HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
            skin.endSkin(req, out, pageAttributes);

            return EVAL_PAGE;
        } catch(IOException err) {
            throw new JspException(err);
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
