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
     * Directional references.
     */
    public static final int
        NONE=0,
        UP=1,
        DOWN=2,
        UP_AND_DOWN=3
    ;

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
     * Starts the content area of a page.  The content area provides additional features such as a nice border, and vertical and horizontal dividers.
     */
    abstract public void startContent(HttpServletRequest req, ChainWriter out, PageAttributes pageAttributes, int[] colspans, String width) throws IOException;

    /**
     * Prints an entire content line including the provided title.  The colspan should match the total colspan in startContent for proper appearance
     */
    abstract public void printContentTitle(HttpServletRequest req, ChainWriter out, String title, int colspan);

    /**
     * Starts one line of content with the initial colspan set to the provided colspan.
     */
    abstract public void startContentLine(HttpServletRequest req, ChainWriter out, int colspan, String align);

    /**
     * Starts one line of content with the initial colspan set to the provided colspan.
     */
    abstract public void printContentVerticalDivider(HttpServletRequest req, ChainWriter out, boolean visible, int colspan, int rowspan, String align);

    /**
     * Ends one line of content.
     */
    abstract public void endContentLine(HttpServletRequest req, ChainWriter out, int rowspan, boolean endsInternal);

    /**
     * Prints a horizontal divider of the provided colspans.
     */
    abstract public void printContentHorizontalDivider(HttpServletRequest req, ChainWriter out, int[] colspansAndDirections, boolean endsInternal);

    /**
     * Ends the content area of a page.
     */
    abstract public void endContent(HttpServletRequest req, ChainWriter out, PageAttributes pageAttributes, int[] colspans) throws IOException;

    /**
     * Writes the contents between the page content and the HTML tag (not including the HTML tag itself).
     */
    abstract public void endSkin(HttpServletRequest req, ChainWriter out, PageAttributes pageAttributes) throws IOException;

    /**
     * Begins a light area.
     */
    abstract public void beginLightArea(ChainWriter out, String width, boolean nowrap);

    /**
     * Ends a light area.
     */
    abstract public void endLightArea(ChainWriter out);

    /**
     * Begins a white area.
     */
    abstract public void beginWhiteArea(ChainWriter out, String width, boolean nowrap);

    /**
     * Ends a white area.
     */
    abstract public void endWhiteArea(ChainWriter out);
}
