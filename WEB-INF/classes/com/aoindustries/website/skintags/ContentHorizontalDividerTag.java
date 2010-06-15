/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.skintags;

import com.aoindustries.util.StringUtility;
import com.aoindustries.website.ApplicationResources;
import com.aoindustries.website.Skin;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author  AO Industries, Inc.
 */
public class ContentHorizontalDividerTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private String colspansAndDirections;
    private boolean endsInternal;

    public ContentHorizontalDividerTag() {
        init();
    }

    private void init() {
        this.colspansAndDirections = "1";
        this.endsInternal = false;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            ContentTag contentTag = (ContentTag)findAncestorWithClass(this, ContentTag.class);
            if(contentTag==null) throw new JspException(ApplicationResources.accessor.getMessage("skintags.ContentHorizontalDividerTag.mustNestInContentTag"));

            Skin skin = SkinTag.getSkin(pageContext);

            List<String> list = StringUtility.splitStringCommaSpace(colspansAndDirections);
            if((list.size()&1)==0) throw new JspException(ApplicationResources.accessor.getMessage("skintags.ContentHorizontalDivider.colspansAndDirections.mustBeOddNumberElements"));

            int[] array = new int[list.size()];
            for(int c=0;c<list.size();c+=2) {
                if(c>0) {
                    String direction = list.get(c-1);
                    if("up".equalsIgnoreCase(direction)) array[c-1]=Skin.UP;
                    else if("down".equalsIgnoreCase(direction)) array[c-1]=Skin.DOWN;
                    else if("upAndDown".equalsIgnoreCase(direction)) array[c-1]=Skin.UP_AND_DOWN;
                    else throw new JspException(ApplicationResources.accessor.getMessage("skintags.ContentHorizontalDivider.colspansAndDirections.invalidDirection", direction));
                }
                array[c]=Integer.parseInt(list.get(c));
            }

            HttpServletRequest req = (HttpServletRequest)pageContext.getRequest();
            HttpServletResponse resp = (HttpServletResponse)pageContext.getResponse();
            skin.printContentHorizontalDivider(req, resp, pageContext.getOut(), array, endsInternal);

            return SKIP_BODY;
        } finally {
            init();
        }
    }

    public String getColspansAndDirections() {
        return colspansAndDirections;
    }

    public void setColspansAndDirections(String colspansAndDirections) {
        this.colspansAndDirections = colspansAndDirections;
    }

    public boolean isEndsInternal() {
        return endsInternal;
    }

    public void setEndsInternal(boolean endsInternal) {
        this.endsInternal = endsInternal;
    }
}
