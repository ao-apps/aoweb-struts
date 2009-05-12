package com.aoindustries.website;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import org.apache.struts.Globals;

/**
 * Provides site-wide settings.
 *
 * @author  AO Industries, Inc.
 */
public class SiteSettings {

    /**
     * Only one instance is created per unique classname.
     */
    private static final Map<String,SiteSettings> instanceCache = new HashMap<String,SiteSettings>();

    /**
     * Gets the proper settings instance as configured in the web.xml file.
     * This allows 
     */
    public static SiteSettings getInstance(ServletContext servletContext) throws JspException {
        String classname = servletContext.getInitParameter("com.aoindustries.website.SiteSettings.classname");
        try {
            synchronized(instanceCache) {
                SiteSettings settings = instanceCache.get(classname);
                if(settings==null) {
                    // Create through reflection
                    Class<? extends SiteSettings> clazz = Class.forName(classname).asSubclass(SiteSettings.class);
                    settings = clazz.newInstance();
                    instanceCache.put(classname, settings);
                }
                return settings;
            }
        } catch(ClassNotFoundException err) {
            throw new JspException("classname="+classname, err);
        } catch(InstantiationException err) {
            throw new JspException("classname="+classname, err);
        } catch(IllegalAccessException err) {
            throw new JspException("classname="+classname, err);
        }
    }

    public SiteSettings() {
    }

    private static final List<Skin> skins = Collections.singletonList((Skin)TextSkin.getInstance());

    /**
     * Gets the unmodifiable list of skins supported by this site.
     * 
     * The first one in the list will be used as the default skin, except
     * if Text mode is determined as the default, then any skin named "Text"
     * will be the default if available.
     */
    public List<Skin> getSkins() {
        return skins;
    }

    /**
     * Gets the unmodifiable list of languages supported by this site.
     *
     * The flags are obtained from http://commons.wikimedia.org/wiki/National_insignia
     *
     * Then they are scaled to a height of 24 pixels, rendered in gimp 2.
     *
     * The off version is created by filling with black, opacity 25% in gimp 2.
     */
    public List<Skin.Language> getLanguages(HttpServletRequest req) throws JspException {
        HttpSession session = req.getSession();
        Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        if(locale==null) locale = Locale.getDefault(); // Can't use: LocaleAction.getDefaultLocale(req); due to stack overflow
        boolean isUnitedStates = locale.getCountry().equals(Locale.US.getCountry());

        List<Skin.Language> languages = new ArrayList<Skin.Language>(2);
        if(isUnitedStates) {
            languages.add(
                new Skin.Language(
                    Locale.ENGLISH.getLanguage(),
                    "/ApplicationResources", "TextSkin.language.en_US.alt",
                    "/ApplicationResources", "TextSkin.language.en_US.flag.on.src",
                    "/ApplicationResources", "TextSkin.language.en_US.flag.off.src",
                    "/ApplicationResources", "TextSkin.language.en_US.flag.width",
                    "/ApplicationResources", "TextSkin.language.en_US.flag.height",
                    null
                )
            );
        } else {
            languages.add(
                new Skin.Language(
                    Locale.ENGLISH.getLanguage(),
                    "/ApplicationResources", "TextSkin.language.en.alt",
                    "/ApplicationResources", "TextSkin.language.en.flag.on.src",
                    "/ApplicationResources", "TextSkin.language.en.flag.off.src",
                    "/ApplicationResources", "TextSkin.language.en.flag.width",
                    "/ApplicationResources", "TextSkin.language.en.flag.height",
                    null
                )
            );
        }
        languages.add(
            new Skin.Language(
                Locale.JAPANESE.getLanguage(),
                "/ApplicationResources", "TextSkin.language.ja.alt",
                "/ApplicationResources", "TextSkin.language.ja.flag.on.src",
                "/ApplicationResources", "TextSkin.language.ja.flag.off.src",
                "/ApplicationResources", "TextSkin.language.ja.flag.width",
                "/ApplicationResources", "TextSkin.language.ja.flag.height",
                null
            )
        );
        return Collections.unmodifiableList(languages);
    }

    /**
     * Gets the google verify content or <code>null</code> if doesn't have one.
     */
    public String getGoogleVerifyContent() {
        return null;
    }
}
