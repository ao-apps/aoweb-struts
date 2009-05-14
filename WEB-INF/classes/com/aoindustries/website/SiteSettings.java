package com.aoindustries.website;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.util.ErrorHandler;
import com.aoindustries.util.StandardErrorHandler;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public static SiteSettings getInstance(ServletContext servletContext) {
        String classname = servletContext.getInitParameter("com.aoindustries.website.SiteSettings.classname");
        try {
            synchronized(instanceCache) {
                SiteSettings settings = instanceCache.get(classname);
                if(settings==null) {
                    // Create through reflection
                    Class<? extends SiteSettings> clazz = Class.forName(classname).asSubclass(SiteSettings.class);
                    Constructor<? extends SiteSettings> constructor = clazz.getConstructor(new Class[] {ServletContext.class});
                    settings = constructor.newInstance(new Object[] {servletContext});
                    instanceCache.put(classname, settings);
                }
                return settings;
            }
        } catch(ClassNotFoundException err) {
            throw new RuntimeException("classname="+classname, err);
        } catch(NoSuchMethodException err) {
            throw new RuntimeException("classname="+classname, err);
        } catch(InvocationTargetException err) {
            throw new RuntimeException("classname="+classname, err);
        } catch(InstantiationException err) {
            throw new RuntimeException("classname="+classname, err);
        } catch(IllegalAccessException err) {
            throw new RuntimeException("classname="+classname, err);
        }
    }

    private final ServletContext servletContext;

    public SiteSettings(ServletContext servletContext) {
        this.servletContext = servletContext;
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
    public List<Skin.Language> getLanguages(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        if(locale==null) locale = Locale.getDefault(); // Can't use: LocaleAction.getDefaultLocale(req); due to stack overflow
        boolean isUnitedStates = locale.getCountry().equals(Locale.US.getCountry());

        List<Skin.Language> languages = new ArrayList<Skin.Language>(2);
        if(getEnglishEnabled()) {
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
        }
        if(getJapaneseEnabled()) {
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
        }
        return Collections.unmodifiableList(languages);
    }

    /**
     * Determines if English is enabled, enabled by default.
     */
    protected boolean getEnglishEnabled() {
        return true;
    }

    /**
     * Determines if Japense is enabled, disabled by default.
     */
    protected boolean getJapaneseEnabled() {
        return true;
    }

    /**
     * Gets the google verify content or <code>null</code> if doesn't have one.
     */
    public String getGoogleVerifyContent() {
        return null;
    }

    /**
     * If true (the default), all of the common aoweb-struts content will have ROBOTS NOINDEX.
     */
    public boolean getNoindexAowebStruts() {
        return true;
    }

    /**
     * Gets the Google Analytics New Tracking Code (ga.js) or <code>null</code>
     * if unavailable.
     */
    public String getGoogleAnalyticsNewTrackingCode() {
        return null;
    }

    /**
     * Gets the username for the root AOServConnector.
     *
     * @see #getRootAOServConnector()
     */
    protected String getRootAOServConnectorUsername() {
        return servletContext.getInitParameter("root.aoserv.client.username");
    }

    /**
     * Gets the password for the root AOServConnector.
     *
     * @see #getRootAOServConnector()
     */
    protected String getRootAOServConnectorPassword() {
        return servletContext.getInitParameter("root.aoserv.client.password");
    }

    private static final ErrorHandler standardErrorHandler = new StandardErrorHandler();

    private AOServConnector rootAOServConnector = null;
    private final Object rootAOServConnectorLock = new Object();

    /**
     * Gets the root connector.  Because this potentially has unrestricted privileges, this must be used at an absolute minimum for situations
     * where a user isn't logged-in but access to the master is required, such as for sign up requests.
     */
    public AOServConnector getRootAOServConnector() throws IOException {
        synchronized(rootAOServConnectorLock) {
            if(rootAOServConnector==null) {
                rootAOServConnector = AOServConnector.getConnector(
                    getRootAOServConnectorUsername(),
                    getRootAOServConnectorPassword(),
                    standardErrorHandler
                );
            }
            return rootAOServConnector;
        }
    }

    /**
     * Gets the SMTP server used by this website.
     */
    public String getSmtpServer() {
        return servletContext.getInitParameter("com.aoindustries.website.smtp.server");
    }

    /**
     * Gets the From address used for any signup emails.
     */
    public String getSignupFromAddress() {
        return servletContext.getInitParameter("com.aoindustries.website.signup.from.address");
    }

    /**
     * Gets the display name used for any signup emails.
     */
    public String getSignupFromPersonal() {
        return servletContext.getInitParameter("com.aoindustries.website.signup.from.personal");
    }

    /**
     * Gets the email address that is notified when a signup occurs.
     */
    public String getSignupAdminAddress() {
        return servletContext.getInitParameter("com.aoindustries.website.signup.admin.address");
    }

    public boolean getProtocolActionRedirectOnMismatch() {
        return !"false".equals(servletContext.getInitParameter("com.aoindustries.website.ProtocolAction.redirectOnMismatch"));
    }

    public boolean getExceptionShowError() {
        return "true".equals(servletContext.getInitParameter("exception.showError"));
    }
}
