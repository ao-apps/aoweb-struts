/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2019, 2021, 2022  AO Industries, Inc.
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

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter to write selective values into URL when cookies disabled for SEO purposes.
 * If the client doesn't support cookies:
 * <ol>
 *   <li>If this site supports more than one language, adds a language parameter if it doesn't exist.</li>
 *   <li>If this site supports more than one skin, adds a layout parameter if it doesn't exist.</li>
 * </ol>
 * <p>
 * This should be used for both the REQUEST and ERROR dispatchers.
 * </p>
 * <p>
 * <strong>This must be first in the filter chain</strong>, or at least before all filters that
 * perform any URL rewriting.  This filter selectively does not pass the URL
 * rewriting up the filter chain, as it is intended to block default session
 * URL rewriting provided by servlet contains.
 * </p>
 *
 * @author  AO Industries, Inc.
 */
public class SessionFilter implements Filter {

  @Override
  public void init(FilterConfig config) {
    // Do nothing
  }

  @Override
  public void doFilter(
    ServletRequest request,
    ServletResponse response,
    FilterChain chain
  ) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest)request;
    SessionResponseWrapper myresponse = new SessionResponseWrapper(httpRequest, (HttpServletResponse)response);
    SessionRequestWrapper myrequest = new SessionRequestWrapper(httpRequest);
    chain.doFilter(myrequest, myresponse);
    // Could improve the efficiency by removing temporary sessions proactively here
    /*
    // The only time we keep the session data is when the user is logged-in or supports cookie-based sessions
    HttpSession session = myrequest.getSession(false);
    if (session != null) {
      if (session.isNew()...
      try {
        session.invalidate();
      } catch (IllegalStateException err) {
        // Ignore this because the session could have been already invalidated
      }
    }*/
  }

  @Override
  public void destroy() {
    // Do nothing
  }
}
