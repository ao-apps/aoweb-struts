package com.aoindustries.website.skintags;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.ApplicationResourcesAccessor;
import java.util.Locale;
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
                        ApplicationResourcesAccessor.getMessage(
                            "Invalid value for layout, must be either \"normal\" or \"minimal\"",
                            Locale.getDefault(),
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
