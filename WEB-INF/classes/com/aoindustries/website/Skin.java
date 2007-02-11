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
    abstract public void startSkin(HttpServletRequest req, JspWriter out, PageAttributes pageAttributes) throws JspException;

    /**
     * Starts the content area of a page.  The content area provides additional features such as a nice border, and vertical and horizontal dividers.
     */
    abstract public void startContent(HttpServletRequest req, JspWriter out, PageAttributes pageAttributes, int[] colspans, String width) throws JspException;

    /**
     * Prints an entire content line including the provided title.  The colspan should match the total colspan in startContent for proper appearance
     */
    abstract public void printContentTitle(HttpServletRequest req, JspWriter out, String title, int colspan) throws JspException;

    /**
     * Starts one line of content with the initial colspan set to the provided colspan.
     */
    abstract public void startContentLine(HttpServletRequest req, JspWriter out, int colspan, String align) throws JspException;

    /**
     * Starts one line of content with the initial colspan set to the provided colspan.
     */
    abstract public void printContentVerticalDivider(HttpServletRequest req, JspWriter out, boolean visible, int colspan, int rowspan, String align) throws JspException;

    /**
     * Ends one line of content.
     */
    abstract public void endContentLine(HttpServletRequest req, JspWriter out, int rowspan, boolean endsInternal) throws JspException;

    /**
     * Prints a horizontal divider of the provided colspans.
     */
    abstract public void printContentHorizontalDivider(HttpServletRequest req, JspWriter out, int[] colspansAndDirections, boolean endsInternal) throws JspException;

    /**
     * Ends the content area of a page.
     */
    abstract public void endContent(HttpServletRequest req, JspWriter out, PageAttributes pageAttributes, int[] colspans) throws JspException;

    /**
     * Writes the contents between the page content and the HTML tag (not including the HTML tag itself).
     */
    abstract public void endSkin(HttpServletRequest req, JspWriter out, PageAttributes pageAttributes) throws JspException;

    /**
     * Begins a light area.
     */
    abstract public void beginLightArea(HttpServletRequest req, JspWriter out, String width, boolean nowrap) throws JspException;

    /**
     * Ends a light area.
     */
    abstract public void endLightArea(HttpServletRequest req, JspWriter out) throws JspException;

    /**
     * Begins a white area.
     */
    abstract public void beginWhiteArea(HttpServletRequest req, JspWriter out, String width, boolean nowrap) throws JspException;

    /**
     * Ends a white area.
     */
    abstract public void endWhiteArea(HttpServletRequest req, JspWriter out) throws JspException;

    public static class Language {
        private String code;
        private String display;
        
        public Language(String code, String display) {
            this.code = code;
            this.display = display;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getDisplay() {
            return display;
        }
    }

    /**
     * Gets the list of languages supported by this site.
     */
    public List<Language> getLanguages(HttpServletRequest req) throws JspException {
        HttpSession session = req.getSession();
        Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        MessageResources applicationResources = (MessageResources)req.getAttribute("/ApplicationResources");
        if(applicationResources==null) throw new JspException("Unable to load resources: /ApplicationResources");
        List<Language> languages = new ArrayList<Language>(2);
        languages.add(new Language(Locale.ENGLISH.getLanguage(), applicationResources.getMessage(locale, "TextSkin.language.en")));
        languages.add(new Language(Locale.JAPANESE.getLanguage(), applicationResources.getMessage(locale, "TextSkin.language.ja")));
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
}
