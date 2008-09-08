package com.aoindustries.website;

/*
 * Copyright 2007-2008 by AO Industries, Inc.,
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
 * @author  AO Industries, Inc.
 */
public class SessionSharingFilter implements Filter {

    public void init(FilterConfig config) {
    }

    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        SessionSharingResponseWrapper myresponse = new SessionSharingResponseWrapper((HttpServletResponse)response, request.getServerName());
        SessionSharingRequestWrapper myrequest = new SessionSharingRequestWrapper((HttpServletRequest)request, myresponse);
        chain.doFilter(myrequest, myresponse);
    }
    
    public void destroy() {
    }
}
