package com.aoindustries.website;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.util.ErrorPrinter;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ensures the user is logged in.  Forwards to "login" if not logged in.  Next it checks the user permissions and returns status 403 if they don't have the
 * proper permissions.  Otherwise, it calls the subclass doGet.
 *
 * @author  AO Industries, Inc.
 */
abstract public class AuthenticatedServlet extends HttpServlet {

    public void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        // Must be logged in
        AOServConnector aoConn = (AOServConnector)request.getSession().getAttribute("aoConn");
        if(aoConn==null) {
            // Save target for later
            String target = request.getRequestURL().toString();
            if(!target.endsWith("/login.do")) {
                request.getSession().setAttribute(Constants.AUTHENTICATION_TARGET, target);
            } else {
                request.getSession().removeAttribute(Constants.AUTHENTICATION_TARGET);
            }

            // TODO: This might not be completely web-app relative compatible

            int port = request.getServerPort();
            String url;
            if(port!=80 && port!=443) {
                // Non-ssl development area
                url = "http://"+request.getServerName()+":"+port+"/login.do";
            } else {
                url = "https://"+request.getServerName()+"/login.do";
            }
            response.sendRedirect(url);
            return;
        }

        doGet(request, response, aoConn);
    }

    abstract public void doGet(
        HttpServletRequest request,
        HttpServletResponse response,
        AOServConnector aoConn
    ) throws IOException;
}
