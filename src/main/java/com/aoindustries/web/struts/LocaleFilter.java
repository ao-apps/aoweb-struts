/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2019, 2020, 2021, 2022, 2024  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.aoindustries.web.struts;

import com.aoapps.lang.i18n.ThreadLocale;
import com.aoapps.servlet.attribute.AttributeEE;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Resolves the current {@link Locale}, optionally changing it with any language parameters, and sets the request param
 * {@link Constants#LOCALE}, and {@linkplain ThreadLocale#set(java.util.Locale) sets the thread locale}.
 *
 * @author AO Industries, Inc.
 */
// TODO: Use com.aoapps.servlet.filter.LocaleFilter instead?
public class LocaleFilter implements Filter {

  private ServletContext servletContext;

  @Override
  public void init(FilterConfig fc) {
    servletContext = fc.getServletContext();
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    try {
      // Resolve the locale
      SiteSettings siteSettings = SiteSettings.getInstance(servletContext);
      Locale locale = getEffectiveLocale(siteSettings, request, response);
      Constants.LOCALE.context(request).set(locale);

      Locale oldLocale = ThreadLocale.get();
      try {
        if (oldLocale != locale) {
          ThreadLocale.set(locale);
        }
        chain.doFilter(request, response);
      } finally {
        if (oldLocale != locale) {
          ThreadLocale.set(oldLocale);
        }
      }
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  public void destroy() {
    // Nothing to do
  }


  /**
   * Gets the effective locale for the request.  If the requested language is not
   * one of the enabled languages for this site, will set to the default language
   * (the first in the language list).
   *
   * <p>Also allows the parameter {@link Constants#LANGUAGE} to override the current settings.</p>
   *
   * <p>This also sets the struts, JSTL, and response locales to the same value.</p>
   *
   * <p>The {@linkplain ThreadLocale thread locale} is not set.</p>
   */
  public static Locale getEffectiveLocale(SiteSettings siteSettings, ServletRequest request, ServletResponse response) throws IOException, SQLException {
    HttpSession session;
    if (request instanceof HttpServletRequest) {
      session = ((HttpServletRequest) request).getSession();
    } else {
      assert request != null;
      session = null;
    }
    List<Skin.Language> languages = siteSettings.getLanguages(request);
    Locale locale = Globals.LOCALE_KEY.context(session).get();
    String language = request.getParameter(Constants.LANGUAGE);
    if (language != null && (language = language.trim()).length() > 0) {
      // Make sure is a supported language
      for (Skin.Language possLanguage : languages) {
        String code = possLanguage.getCode();
        if (code.equals(language)) {
          // Java 19: Deprecation of Locale Class Constructors, see https://bugs.openjdk.org/browse/JDK-8282819
          locale = locale == null ? new Locale(code) : new Locale(code, locale.getCountry(), locale.getVariant());
          if (session != null) {
            Globals.LOCALE_KEY.context(session).set(locale);
            AttributeEE.Jstl.FMT_LOCALE.context(session).set(locale);
          }
          response.setLocale(locale);
          return locale;
        }
      }
    }
    if (locale != null) {
      // Make sure the language is a supported value, otherwise return the default language
      String localeLanguage = locale.getLanguage();
      for (Skin.Language possLanguage : languages) {
        if (possLanguage.getCode().equals(localeLanguage)) {
          // Current value is from session and is OK
          response.setLocale(locale);
          // Make sure the JSTL value matches
          if (session != null && !locale.equals(AttributeEE.Jstl.FMT_LOCALE.context(session).get())) {
            AttributeEE.Jstl.FMT_LOCALE.context(session).set(locale);
          }
          return locale;
        }
      }
    }
    // Return the default
    locale = getDefaultLocale(languages);
    if (session != null) {
      Globals.LOCALE_KEY.context(session).set(locale);
      AttributeEE.Jstl.FMT_LOCALE.context(session).set(locale);
    }
    response.setLocale(locale);
    return locale;
  }

  /**
   * Gets the default locale for the provided request.  The session and {@linkplain ThreadLocale thread locale} are not set.
   */
  public static Locale getDefaultLocale(SiteSettings siteSettings, ServletRequest request) throws IOException, SQLException {
    return getDefaultLocale(siteSettings.getLanguages(request));
  }

  private static Locale getDefaultLocale(List<Skin.Language> languages) {
    // Java 19: Deprecation of Locale Class Constructors, see https://bugs.openjdk.org/browse/JDK-8282819
    return new Locale(languages.get(0).getCode());
  }
}
