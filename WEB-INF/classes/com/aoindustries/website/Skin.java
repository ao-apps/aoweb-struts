package com.aoindustries.website;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import com.aoindustries.website.skintags.PageAttributes;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

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
        if(locale!=null && locale.getLanguage().equals(Locale.JAPANESE.getLanguage())) {
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
     * Gets the prefix for URLs for the SSL server.  This should always end with a /.
     */
    public String getHttpsUrlBase(HttpServletRequest req) throws JspException {
        int port = req.getServerPort();
        if(port!=80 && port!=443) {
            // Non-ssl development area
            return "https://"+req.getServerName()+":8443/";
        } else {
            return "https://"+req.getServerName()+"/";
        }
    }

    /**
     * Gets the prefix for URLs for the non-SSL server.  This should always end with a /.
     */
    public String getHttpUrlBase(HttpServletRequest req) throws JspException {
        int port = req.getServerPort();
        if(port!=80 && port!=443) {
            // Non-ssl development area
            return "http://"+req.getServerName()+":8081/";
        } else {
            return "http://"+req.getServerName()+"/";
        }
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
    abstract public void startContentLine(HttpServletRequest req, HttpServletResponse resp, JspWriter out, int colspan, String align) throws JspException;

    /**
     * Starts one line of content with the initial colspan set to the provided colspan.
     */
    abstract public void printContentVerticalDivider(HttpServletRequest req, HttpServletResponse resp, JspWriter out, boolean visible, int colspan, int rowspan, String align) throws JspException;

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
    abstract public void endSkin(HttpServletRequest req, JspWriter out, PageAttributes pageAttributes) throws JspException;

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
        private String code;
        private String display;
        private String flagOnSrc;
        private String flagOffSrc;
        private String flagWidth;
        private String flagHeight;
        
        public Language(String code, String display, String flagOnSrc, String flagOffSrc, String flagWidth, String flagHeight) {
            this.code = code;
            this.display = display;
            this.flagOnSrc = flagOnSrc;
            this.flagOffSrc = flagOffSrc;
            this.flagWidth = flagWidth;
            this.flagHeight = flagHeight;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getDisplay() {
            return display;
        }
        
        public String getFlagOnSrc() {
            return flagOnSrc;
        }

        public String getFlagOffSrc() {
            return flagOffSrc;
        }

        public String getFlagWidth() {
            return flagWidth;
        }

        public String getFlagHeight() {
            return flagHeight;
        }
    }

    /**
     * Gets the list of languages supported by this site.
     *
     * The flags are obtained from http://commons.wikimedia.org/wiki/National_insignia.
     *
     * Then they are scaled to a height of 24 pixels.
     *
     * The off version is created by covering with black, opacity 25% in gimp 2.
     */
    public List<Language> getLanguages(HttpServletRequest req) throws JspException {
        HttpSession session = req.getSession();
        Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        MessageResources applicationResources = (MessageResources)req.getAttribute("/ApplicationResources");
        if(applicationResources==null) throw new JspException("Unable to load resources: /ApplicationResources");
        List<Language> languages = new ArrayList<Language>(2);
        languages.add(
            new Language(
                Locale.ENGLISH.getLanguage(),
                applicationResources.getMessage(locale, "TextSkin.language.en.alt"),
                applicationResources.getMessage(locale, "TextSkin.language.en.flag.on.src"),
                applicationResources.getMessage(locale, "TextSkin.language.en.flag.off.src"),
                applicationResources.getMessage(locale, "TextSkin.language.en.flag.width"),
                applicationResources.getMessage(locale, "TextSkin.language.en.flag.height")
            )
        );
        languages.add(
            new Language(
                Locale.JAPANESE.getLanguage(),
                applicationResources.getMessage(locale, "TextSkin.language.ja.alt"),
                applicationResources.getMessage(locale, "TextSkin.language.ja.flag.on.src"),
                applicationResources.getMessage(locale, "TextSkin.language.ja.flag.off.src"),
                applicationResources.getMessage(locale, "TextSkin.language.ja.flag.width"),
                applicationResources.getMessage(locale, "TextSkin.language.ja.flag.height")
            )
        );
        return languages;
    }

    public static class Layout {
        private String name;
        private String display;
        
        public Layout(String name, String display) {
            this.name = name;
            this.display = display;
        }
        
        public String getName() {
            return name;
        }
        
        public String getDisplay() {
            return display;
        }
    }

    /**
     * Gets the layouts supported by this site.
     */
    public List<Layout> getLayouts(HttpServletRequest req) throws JspException {
        HttpSession session = req.getSession();
        Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        MessageResources applicationResources = (MessageResources)req.getAttribute("/ApplicationResources");
        if(applicationResources==null) throw new JspException("Unable to load resources: /ApplicationResources");
        List<Layout> layouts = new ArrayList<Layout>(2);
        layouts.add(new Layout("Text", applicationResources.getMessage(locale, "TextSkin.name")));
        return layouts;
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
    abstract public void beginPopup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId) throws JspException;

    /**
     * Prints a popup close link/image/button for a popup that is part of a popup group.
     */
    abstract public void printPopupClose(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId) throws JspException;

    /**
     * Ends a popup that is in a popup group.
     */
    abstract public void endPopup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId) throws JspException;
}
