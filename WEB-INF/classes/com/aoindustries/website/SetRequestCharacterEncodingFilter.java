package com.aoindustries.website;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import java.io.IOException;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.Globals;

/**
 * Sets the request encoding based on the users locale stored in their session.  If
 * the session doesn't exist or doesn't have a locale set, will not set character
 * encoding, which leaves the application server default behavior.
 *
 * @author  AO Industries, Inc.
 */
public class SetRequestCharacterEncodingFilter implements Filter {

    public void init(FilterConfig config) {
    }

    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        if(session!=null) {
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            if(locale!=null) {
                request.setCharacterEncoding(Skin.getCharacterSet(locale));
            }
        }
        
        chain.doFilter(request, response);
    }
    
    public void destroy() {
    }
}
