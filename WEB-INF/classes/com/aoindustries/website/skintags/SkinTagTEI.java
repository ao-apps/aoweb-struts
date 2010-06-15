/*
 * Copyright 2009-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.skintags;

import com.aoindustries.website.ApplicationResources;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.ValidationMessage;

/**
 * @author  AO Industries, Inc.
 */
public class SkinTagTEI extends TagExtraInfo {

    @Override
    public ValidationMessage[] validate(TagData data) {
        Object o = data.getAttribute("layout");
        if(
            o != null
            && o != TagData.REQUEST_TIME_VALUE
        ) {
            if(PageAttributes.LAYOUT_NORMAL.equals(o) || PageAttributes.LAYOUT_MINIMAL.equals(o)) return null;
            else {
                return new ValidationMessage[] {
                    new ValidationMessage(
                        data.getId(),
                        ApplicationResources.accessor.getMessage(
                            "Invalid value for layout, must be either \"normal\" or \"minimal\"",
                            "skintags.SkinTagTEI.validate.layout.invalid"
                        )
                    )
                };
            }
        } else {
            return null;
        }
    }
}
