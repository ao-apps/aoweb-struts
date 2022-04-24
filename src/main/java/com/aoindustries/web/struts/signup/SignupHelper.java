/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2020, 2021, 2022  AO Industries, Inc.
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

package com.aoindustries.web.struts.signup;

import com.aoapps.servlet.attribute.ScopeEE;
import java.util.function.Supplier;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionServlet;

/**
 * Utilities usable by any signup step.
 *
 * @author  AO Industries, Inc.
 */
public final class SignupHelper {

  /** Make no instances. */
  private SignupHelper() {
    throw new AssertionError();
  }

  /**
   * Gets the form of the provided class from the session.  If it is not in
   * the session will create the form, set its servlet, and add it to the
   * session.
   */
  public static <T extends ActionForm> T getSessionActionForm(
      ActionServlet servlet,
      HttpSession session,
      ScopeEE.Session.Attribute<T> attribute,
      Supplier<T> factory
  ) {
    return attribute.context(session).computeIfAbsent(__ -> {
      T form = factory.get();
      form.setServlet(servlet);
      return form;
    });
  }
}
