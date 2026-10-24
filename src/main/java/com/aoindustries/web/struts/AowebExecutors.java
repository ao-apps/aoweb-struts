/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2026  AO Industries, Inc.
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

import com.aoapps.concurrent.Executors;
import com.aoapps.servlet.attribute.ScopeEE;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Provides site-wide shared {@link Executors}.
 *
 * @author  AO Industries, Inc.
 */
@WebListener
public class AowebExecutors implements ServletContextListener {

  private static final ScopeEE.Application.Attribute<Executors> APPLICATION_ATTRIBUTE =
      ScopeEE.APPLICATION.attribute(AowebExecutors.class.getName() + ".executors");

  /**
   * Gets the {@link Executors} to use for this application.
   *
   * @throws IllegalStateException if application not initialized or already destroyed
   */
  public static Executors getExecutors(ServletContext context) {
    Executors executors = APPLICATION_ATTRIBUTE.context(context).get();
    if (executors == null) {
      throw new IllegalStateException("Application not initialized or already destroyed");
    }
    return executors;
  }

  @Override
  public void contextInitialized(ServletContextEvent event) {
    APPLICATION_ATTRIBUTE.context(event.getServletContext()).computeIfAbsent(k -> new Executors());
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    Executors executors = APPLICATION_ATTRIBUTE.context(event.getServletContext()).replace(null);
    if (executors != null) {
      executors.close();
    }
  }
}
