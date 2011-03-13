package com.aoindustries.website;

/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.util.i18n.ApplicationResourcesAccessor;
import javax.servlet.http.HttpServletRequest;
import com.aoindustries.website.skintags.PageAttributes;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

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
     * Gets the name of this skin.
     */
    abstract public String getName();

    /**
     * Gets the display value for this skin.
     */
    abstract public String getDisplay(HttpServletRequest req) throws JspException;

    /**
     * Gets the prefix for URLs for the SSL server.  This should always end with a /.
     */
    public static String getDefaultHttpsUrlBase(HttpServletRequest req) throws JspException {
        int port = req.getServerPort();
        String contextPath = req.getContextPath();
        if(port!=80 && port!=443) {
            // Non-ssl development area
            return "https://"+req.getServerName()+":11257"+contextPath+"/";
        } else {
            return "https://"+req.getServerName()+contextPath+"/";
        }
    }

    /**
     * Gets the prefix for URLs for the SSL server.  This should always end with a /.
     */
    public String getHttpsUrlBase(HttpServletRequest req) throws JspException {
        return getDefaultHttpsUrlBase(req);
    }

    /**
     * Gets the prefix for URLs for the non-SSL server.  This should always end with a /.
     */
    public static String getDefaultHttpUrlBase(HttpServletRequest req) throws JspException {
        int port = req.getServerPort();
        String contextPath = req.getContextPath();
        if(port!=80 && port!=443) {
            // Non-ssl development area
            return "http://"+req.getServerName()+":11156"+contextPath+"/";
        } else {
            return "http://"+req.getServerName()+contextPath+"/";
        }
    }

    /**
     * Gets the prefix for URLs for the non-SSL server.  This should always end with a /.
     */
    public String getHttpUrlBase(HttpServletRequest req) throws JspException {
        return getDefaultHttpUrlBase(req);
    }

    /**
     * Writes the contents between the HTML tag and the page content (not including the HTML tag itself).
     */
    abstract public void startSkin(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes) throws JspException;

    /**
     * Starts the content area of a page.  The content area provides additional features such as a nice border, and vertical and horizontal dividers.
     */
    abstract public void startContent(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes, int[] colspans, String width) throws JspException;

    /**
     * Prints an entire content line including the provided title.  The colspan should match the total colspan in startContent for proper appearance
     */
    abstract public void printContentTitle(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String title, int colspan) throws JspException;

    /**
     * Starts one line of content with the initial colspan set to the provided colspan.
     */
    abstract public void startContentLine(HttpServletRequest req, HttpServletResponse resp, JspWriter out, int colspan, String align, String width) throws JspException;

    /**
     * Starts one line of content with the initial colspan set to the provided colspan.
     */
    abstract public void printContentVerticalDivider(HttpServletRequest req, HttpServletResponse resp, JspWriter out, boolean visible, int colspan, int rowspan, String align, String width) throws JspException;

    /**
     * Ends one line of content.
     */
    abstract public void endContentLine(HttpServletRequest req, HttpServletResponse resp, JspWriter out, int rowspan, boolean endsInternal) throws JspException;

    /**
     * Prints a horizontal divider of the provided colspans.
     */
    abstract public void printContentHorizontalDivider(HttpServletRequest req, HttpServletResponse resp, JspWriter out, int[] colspansAndDirections, boolean endsInternal) throws JspException;

    /**
     * Ends the content area of a page.
     */
    abstract public void endContent(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes, int[] colspans) throws JspException;

    /**
     * Writes the contents between the page content and the HTML tag (not including the HTML tag itself).
     */
    abstract public void endSkin(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes) throws JspException;

    /**
     * Begins a light area.
     */
    abstract public void beginLightArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String width, boolean nowrap) throws JspException;

    /**
     * Ends a light area.
     */
    abstract public void endLightArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out) throws JspException;

    /**
     * Begins a white area.
     */
    abstract public void beginWhiteArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String width, boolean nowrap) throws JspException;

    /**
     * Ends a white area.
     */
    abstract public void endWhiteArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out) throws JspException;

    public static class Language {
        private final String code;
        private final ApplicationResourcesAccessor displayAccessor;
        private final String displayKey;
        private final ApplicationResourcesAccessor flagOnSrcAccessor;
        private final String flagOnSrcKey;
        private final ApplicationResourcesAccessor flagOffSrcAccessor;
        private final String flagOffSrcKey;
        private final ApplicationResourcesAccessor flagWidthAccessor;
        private final String flagWidthKey;
        private final ApplicationResourcesAccessor flagHeightAccessor;
        private final String flagHeightKey;
        private final String url;

        /**
         * @param url the constant URL to use or <code>null</code> to have automatically set.
         */
        public Language(
            String code,
            ApplicationResourcesAccessor displayAccessor,
            String displayKey,
            ApplicationResourcesAccessor flagOnSrcAccessor,
            String flagOnSrcKey,
            ApplicationResourcesAccessor flagOffSrcAccessor,
            String flagOffSrcKey,
            ApplicationResourcesAccessor flagWidthAccessor,
            String flagWidthKey,
            ApplicationResourcesAccessor flagHeightAccessor,
            String flagHeightKey,
            String url
        ) {
            this.code = code;
            this.displayAccessor = displayAccessor;
            this.displayKey = displayKey;
            this.flagOnSrcAccessor = flagOnSrcAccessor;
            this.flagOnSrcKey = flagOnSrcKey;
            this.flagOffSrcAccessor = flagOffSrcAccessor;
            this.flagOffSrcKey = flagOffSrcKey;
            this.flagWidthAccessor = flagWidthAccessor;
            this.flagWidthKey = flagWidthKey;
            this.flagHeightAccessor = flagHeightAccessor;
            this.flagHeightKey = flagHeightKey;
            this.url = url;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getDisplay() {
            return displayAccessor.getMessage(displayKey);
        }

        public String getFlagOnSrc() {
            return flagOnSrcAccessor.getMessage(flagOnSrcKey);
        }

        public String getFlagOffSrc() {
            return flagOffSrcAccessor.getMessage(flagOffSrcKey);
        }

        public String getFlagWidth() {
            return flagWidthAccessor.getMessage(flagWidthKey);
        }

        public String getFlagHeight() {
            return flagHeightAccessor.getMessage(flagHeightKey);
        }

        /**
         * Gets the absolute URL to use for this language or <code>null</code>
         * to change language on the existing page.
         */
        public String getUrl() {
            return url;
        }
    }

    /**
     * Prints the auto index of all the page siblings.
     */
    abstract public void printAutoIndex(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes) throws JspException;

    /**
     * Begins a popup group.
     */
    abstract public void beginPopupGroup(HttpServletRequest req, JspWriter out, long groupId) throws JspException;

    /**
     * Ends a popup group.
     */
    abstract public void endPopupGroup(HttpServletRequest req, JspWriter out, long groupId) throws JspException;

    /**
     * Begins a popup that is in a popup group.
     */
    abstract public void beginPopup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId, String width) throws JspException;

    /**
     * Prints a popup close link/image/button for a popup that is part of a popup group.
     */
    abstract public void printPopupClose(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId) throws JspException;

    /**
     * Ends a popup that is in a popup group.
     */
    abstract public void endPopup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId, String width) throws JspException;
}
