package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.util.Sequence;
import com.aoindustries.util.UnsynchronizedSequence;
import com.aoindustries.website.ApplicationResources;
import com.aoindustries.website.Skin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Renders one popup, that may optionally be nested in a PopupGroupTag.
 *
 * @see  PopupGroupTag
 *
 * @author  AO Industries, Inc.
 */
public class PopupTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;

    /**
     * The request attribute name used to store the sequence.
     */
    private static final String SEQUENCE_REQUEST_ATTRIBUTE_NAME = PopupTag.class.getName()+".sequence";

    long sequenceId;
    String width;

    public PopupTag() {
        init();
    }

    private void init() {
        this.width = null;
    }

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
        Sequence sequence = (Sequence)req.getAttribute(SEQUENCE_REQUEST_ATTRIBUTE_NAME);
        if(sequence==null) req.setAttribute(SEQUENCE_REQUEST_ATTRIBUTE_NAME, sequence = new UnsynchronizedSequence());
        sequenceId = sequence.getNextSequenceValue();
        Skin skin = SkinTag.getSkin(pageContext);
        // Look for containing popupGroup
        PopupGroupTag popupGroupTag = (PopupGroupTag)findAncestorWithClass(this, PopupGroupTag.class);
        if(popupGroupTag==null) throw new JspException(ApplicationResources.accessor.getMessage("skintags.PopupTag.mustNestInPopupGroupTag"));
        HttpServletResponse resp = (HttpServletResponse)pageContext.getResponse();
        skin.beginPopup(req, resp, pageContext.getOut(), popupGroupTag.sequenceId, sequenceId, width);
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException  {
        try {
            Skin skin = SkinTag.getSkin(pageContext);
            // Look for containing popupGroup
            PopupGroupTag popupGroupTag = (PopupGroupTag)findAncestorWithClass(this, PopupGroupTag.class);
            if(popupGroupTag==null) throw new JspException(ApplicationResources.accessor.getMessage("skintags.PopupTag.mustNestInPopupGroupTag"));
            HttpServletResponse resp = (HttpServletResponse)pageContext.getResponse();
            skin.endPopup((HttpServletRequest)pageContext.getRequest(), resp, pageContext.getOut(), popupGroupTag.sequenceId, sequenceId, width);
            return EVAL_PAGE;
        } finally {
            init();
        }
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
