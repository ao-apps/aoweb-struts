/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2023, 2024  AO Industries, Inc.
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

import com.aoapps.lang.Strings;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.recaptchaenterprise.v1.RecaptchaEnterpriseServiceSettings;
import com.google.recaptchaenterprise.v1.ProjectName;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Consolidates access to reCAPTCHA services.
 * <ol>
 * <li>See <a href="https://cloud.google.com/java/docs/reference/google-cloud-recaptchaenterprise/latest/overview">google-cloud-recaptchaenterprise overview</a></li>
 * <li>See <a href="https://cloud.google.com/recaptcha-enterprise/docs/instrument-web-pages-with-checkbox#expandable-1">Install checkbox keys (checkbox challenge) on websites</a></li>
 * </ol>
 *
 * @author  AO Industries, Inc.
 */
public final class ReCaptcha {

  /** Make no instances. */
  private ReCaptcha() {
    throw new AssertionError();
  }

  /**
   * The init parameter name that provides the project name.
   */
  private static final String PROJECT_NAME_INIT_PARAM_NAME = ReCaptcha.class.getName() + ".projectName";

  /**
   * Gets the project name.
   *
   * @throws ServletException when no project name configured.
   */
  public static ProjectName getProjectName(ServletContext servletContext) throws ServletException {
    String projectName = Strings.trimNullIfEmpty(servletContext.getInitParameter(PROJECT_NAME_INIT_PARAM_NAME));
    if (projectName == null) {
      throw new ServletException("No project name found in init parameter: " + PROJECT_NAME_INIT_PARAM_NAME);
    }
    return ProjectName.of(projectName);
  }

  /**
   * The init parameter name that provides the site key.
   */
  private static final String SITEKEY_INIT_PARAM_NAME = ReCaptcha.class.getName() + ".sitekey";

  /**
   * Gets the site key configured in application init parameters.
   *
   * @throws ServletException when no site key configured.
   */
  public static String getSitekey(ServletContext servletContext) throws ServletException {
    String sitekey = Strings.trimNullIfEmpty(servletContext.getInitParameter(SITEKEY_INIT_PARAM_NAME));
    if (sitekey == null) {
      throw new ServletException("No site key found in init parameter: " + SITEKEY_INIT_PARAM_NAME);
    }
    return sitekey;
  }

  /**
   * The init parameter name that provides the path to the Google Credentials JSON file.
   */
  private static final String GOOGLE_CREDENTIALS_JSON_INIT_PARAM_NAME = ReCaptcha.class.getName() + ".googleCredentialsJson";

  /**
   * Gets the path to the Google Credentials JSON file.
   *
   * @throws ServletException when no path configured.
   */
  public static String getGoogleCredentialsJson(ServletContext servletContext) throws ServletException {
    String googleCredentialsJson = Strings.trimNullIfEmpty(servletContext.getInitParameter(GOOGLE_CREDENTIALS_JSON_INIT_PARAM_NAME));
    if (googleCredentialsJson == null) {
      throw new ServletException("No path found in init parameter: " + GOOGLE_CREDENTIALS_JSON_INIT_PARAM_NAME);
    }
    return googleCredentialsJson;
  }

  /**
   * Builds the settings with credentials obtained from {@link #getGoogleCredentialsJson(javax.servlet.ServletContext)}.
   *
   * <p>See <a href="https://stackoverflow.com/a/65106296/7121505">How to point GOOGLE_APPLICATION_CREDENTIALS to my JSON file? - Stack Overflow</a>.</p>
   */
  public static RecaptchaEnterpriseServiceSettings createSettings(ServletContext servletContext) throws IOException, ServletException {
    GoogleCredentials credentials;
    try (InputStream jsonIn = new FileInputStream(getGoogleCredentialsJson(servletContext))) {
      credentials = GoogleCredentials.fromStream(jsonIn);
    }
    return RecaptchaEnterpriseServiceSettings.newBuilder()
        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
        .build();
  }
}
