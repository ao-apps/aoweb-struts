/*
 * aoweb-struts-core - Core API for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009-2013, 2015, 2016, 2017, 2018, 2019  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts-core.
 *
 * aoweb-struts-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts-core.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.website;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.linux.User;
import com.aoindustries.aoserv.client.reseller.Brand;
import com.aoindustries.util.WrappedException;
import com.aoindustries.validation.ValidationException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
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
	 * This logger doesn't use the ticket handler to avoid logging loops
	 * due to RootAOServConnector being used as part of the logging process.
	 */
	private static final Logger logger = Logger.getLogger(SiteSettings.class.getName());
	// <editor-fold desc="Instance Selection">
	/**
	 * Only one instance is created per unique classname.
	 */
	private static final Map<String,SiteSettings> instanceCache = new HashMap<>();

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
					Constructor<? extends SiteSettings> constructor = clazz.getConstructor(new Class<?>[] {ServletContext.class});
					settings = constructor.newInstance(new Object[] {servletContext});
					instanceCache.put(classname, settings);
				}
				return settings;
			}
		} catch(ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException err) {
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
	protected User.Name getRootAOServConnectorUsername() {
		try {
			return User.Name.valueOf(servletContext.getInitParameter("root.aoserv.client.username"));
		} catch(ValidationException e) {
			throw new WrappedException(e);
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
					logger
				);
			}
			return rootAOServConnector;
		}
	}

	/**
	 * Gets the Brand for this site.  The returned instance has the permissions of the
	 * site's RootAOServConnector and should therefore be used carefully to not
	 * allow privilege escelation.
	 */
	public Brand getBrand() throws IOException, SQLException {
		Brand brand = getRootAOServConnector().getCurrentAdministrator().getUsername().getPackage().getAccount().getBrand();
		if(brand == null) throw new SQLException("Unable to find Brand for username=" + getRootAOServConnectorUsername());
		return brand;
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
	public List<Skin.Language> getLanguages(HttpServletRequest req) throws IOException, SQLException {
		HttpSession session = req.getSession();
		Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
		if(locale==null) locale = Locale.getDefault(); // Can't use: LocaleAction.getDefaultLocale(req); due to stack overflow
		boolean isUnitedStates = locale.getCountry().equals(Locale.US.getCountry());

		Brand brand = getBrand();
		List<Skin.Language> languages = new ArrayList<>(2);
		if(brand.getEnglishEnabled()) {
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
		if(brand.getJapaneseEnabled()) {
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
	// </editor-fold>
	// <editor-fold desc="Development vs Production">
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
