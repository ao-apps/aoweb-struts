package com.aoindustries.website;

/*
 * Copyright 2009-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServClientConfiguration;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Brand;
import com.aoindustries.aoserv.client.validator.UserId;
import com.aoindustries.aoserv.client.validator.ValidationException;
import com.aoindustries.security.LoginException;
import com.aoindustries.util.i18n.ThreadLocale;
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

/**
 * Provides site-wide settings.
 *
 * @author  AO Industries, Inc.
 */
public class SiteSettings {

    /**
     * This logger doesn't use the ticket handler to avoid logging loops
     * due to RootAOServConnector being used as part of the logging process.
     */
    //private static final Logger logger = Logger.getLogger(SiteSettings.class.getName());

    // <editor-fold desc="Instance Selection">
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
        if(classname==null) classname = SiteSettings.class.getName();
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
    // </editor-fold>
    // <editor-fold desc="Skins">
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
    // </editor-fold>
    // <editor-fold desc="AOServ Integration">
    /**
     * Gets the username for the root AOServConnector.
     *
     * @see #getRootAOServConnector()
     */
    protected UserId getRootAOServConnectorUsername() throws IOException {
        try {
            return UserId.valueOf(servletContext.getInitParameter("root.aoserv.client.username"));
        } catch(ValidationException err) {
            IOException ioErr = new IOException(err.getMessage());
            ioErr.initCause(err);
            throw ioErr;
        }
    }

    /**
     * Gets the password for the root AOServConnector.
     *
     * @see #getRootAOServConnector()
     */
    protected String getRootAOServConnectorPassword() {
        return servletContext.getInitParameter("root.aoserv.client.password");
    }

    private AOServConnector<?,?> rootAOServConnector = null;
    private final Object rootAOServConnectorLock = new Object();

    /**
     * Gets the root connector.  Because this potentially has unrestricted privileges, this must be used at an absolute minimum for situations
     * where a user isn't logged-in but access to the master is required, such as for sign up requests.
     */
    public AOServConnector<?,?> getRootAOServConnector() throws IOException {
        synchronized(rootAOServConnectorLock) {
            if(rootAOServConnector==null) {
                try {
                    rootAOServConnector = AOServClientConfiguration.getConnector(
                        getRootAOServConnectorUsername(),
                        getRootAOServConnectorPassword(),
                        false
                    );
                } catch(LoginException err) {
                    IOException ioErr = new IOException(err.getMessage());
                    ioErr.initCause(err);
                    throw ioErr;
                }
            }
            return rootAOServConnector;
        }
    }

    /**
     * Gets the Brand for this site.  The returned instance has the permissions of the
     * site's RootAOServConnector and should therefore be used carefully to not
     * allow privilege escelation.
     */
    public Brand getBrand() throws IOException {
        Brand br = getRootAOServConnector().getThisBusinessAdministrator().getUsername().getBusiness().getBrand();
        if(br==null) throw new AssertionError("Unable to find Brand for username="+getRootAOServConnectorUsername());
        return br;
    }
    // </editor-fold>
    // <editor-fold desc="Languages">
    /**
     * Gets the unmodifiable list of languages supported by this site.
     *
     * The flags are obtained from http://commons.wikimedia.org/wiki/National_insignia
     *
     * Then they are scaled to a height of 24 pixels, rendered in gimp 2.
     *
     * The off version is created by filling with black, opacity 25% in gimp 2.
     */
    public List<Skin.Language> getLanguages(HttpServletRequest req) throws IOException {
        boolean isUnitedStates = ThreadLocale.get().getCountry().equals(Locale.US.getCountry());

        Brand brand = getBrand();
        List<Skin.Language> languages = new ArrayList<Skin.Language>(2);
        if(brand.getEnglishEnabled()) {
            if(isUnitedStates) {
                languages.add(
                    new Skin.Language(
                        Locale.ENGLISH.getLanguage(),
                        ApplicationResources.accessor, "TextSkin.language.en_US.alt",
                        ApplicationResources.accessor, "TextSkin.language.en_US.flag.on.src",
                        ApplicationResources.accessor, "TextSkin.language.en_US.flag.off.src",
                        ApplicationResources.accessor, "TextSkin.language.en_US.flag.width",
                        ApplicationResources.accessor, "TextSkin.language.en_US.flag.height",
                        null
                    )
                );
            } else {
                languages.add(
                    new Skin.Language(
                        Locale.ENGLISH.getLanguage(),
                        ApplicationResources.accessor, "TextSkin.language.en.alt",
                        ApplicationResources.accessor, "TextSkin.language.en.flag.on.src",
                        ApplicationResources.accessor, "TextSkin.language.en.flag.off.src",
                        ApplicationResources.accessor, "TextSkin.language.en.flag.width",
                        ApplicationResources.accessor, "TextSkin.language.en.flag.height",
                        null
                    )
                );
            }
        }
        if(brand.getJapaneseEnabled()) {
            languages.add(
                new Skin.Language(
                    Locale.JAPANESE.getLanguage(),
                    ApplicationResources.accessor, "TextSkin.language.ja.alt",
                    ApplicationResources.accessor, "TextSkin.language.ja.flag.on.src",
                    ApplicationResources.accessor, "TextSkin.language.ja.flag.off.src",
                    ApplicationResources.accessor, "TextSkin.language.ja.flag.width",
                    ApplicationResources.accessor, "TextSkin.language.ja.flag.height",
                    null
                )
            );
        }
        return Collections.unmodifiableList(languages);
    }
    // </editor-fold>
    // <editor-fold desc="Development vs Production">
    /**
     * Defaults to <code>true</code>.
     */
    public boolean getProtocolActionRedirectOnMismatch() {
        return !"false".equals(servletContext.getInitParameter("com.aoindustries.website.ProtocolAction.redirectOnMismatch"));
    }

    /**
     * Defaults to <code>false</code>.
     */
    public boolean getExceptionShowError() {
        return "true".equals(servletContext.getInitParameter("exception.showError"));
    }

    /**
     * Determines if this site allows direct editing of resource bundles.
     */
    public boolean getCanEditResources() {
        return "true".equals(servletContext.getInitParameter("com.aoindustries.website.SiteSettings.canEditResources"));
    }
    // </editor-fold>
}
