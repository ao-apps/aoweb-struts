package com.aoindustries.website;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
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
 * Blocks direct (REQUEST) access to any .jsp file except /index.jsp
 *
 * @author  AO Industries, Inc.
 */
public class NoDirectJspFilter implements Filter {

    public void init(FilterConfig config) {
    }

    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String servletPath = httpRequest.getServletPath();
        if(servletPath.equals("/index.jsp")) chain.doFilter(request, response);
        else ((HttpServletResponse)response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    public void destroy() {
    }
}
