package com.aoindustries.website.aowebtags;

/*
 * Copyright 2009-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.ApplicationResources;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.ValidationMessage;

/**
 * @author  AO Industries, Inc.
 */
public class ScriptGroupTagTEI extends TagExtraInfo {

    @Override
    public ValidationMessage[] validate(TagData data) {
        Object o = data.getAttribute("onloadMode");
        if(
            o != null
            && o != TagData.REQUEST_TIME_VALUE
            && !"none".equals(o)
            && !"before".equals(o)
            && !"after".equals(o)
        ) {
            return new ValidationMessage[] {
                new ValidationMessage(
                    data.getId(),
                    ApplicationResources.accessor.getMessage(
                        "Invalid value for onloadMode, should be one of \"none\", \"before\", or \"after\": {0}",
                        "aowebtags.ScriptGroupTag.onloadMode.invalid",
                        o
                    )
                )
            };
        } else {
            return null;
        }
    }
}
