package com.aoindustries.website;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.io.ChainWriter;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.aoindustries.website.skintags.PageAttributes;

/**
 * One look-and-feel for the website.
 *
 * @author  AO Industries, Inc.
 */
abstract public class Skin {

    /**
     * Provides the correct character set for the given locale.
     */
    public String getCharacterSet(Locale locale) {
        if(locale.getLanguage().equals(Locale.JAPANESE.getLanguage())) {
            return "euc-jp";
        } else {
            return "iso-8859-1";
        }
    }

    /**
     * Gets the name of this skin.
     */
    abstract public String getName();

    /**
     * Writes the contents between the HTML tag and the page content (not including the HTML tag itself).
     */
    abstract public void startSkin(HttpServletRequest req, ChainWriter out, PageAttributes pageAttributes) throws IOException;

    /**
     * Writes the contents between the page content and the HTML tag (not including the HTML tag itself).
     */
    abstract public void endSkin(HttpServletRequest req, ChainWriter out, PageAttributes pageAttributes) throws IOException;
    
    /**
     * Begins a light area.
     */
    abstract public void beginLightArea(ChainWriter out);

    /**
     * Ends a light area.
     */
    abstract public void endLightArea(ChainWriter out);
}
