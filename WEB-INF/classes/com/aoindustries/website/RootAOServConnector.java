package com.aoindustries.website;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.util.StandardErrorHandler;
import java.io.IOException;
import javax.servlet.ServletContext;

/**
 * @author  AO Industries, Inc.
 */
final public class RootAOServConnector {
    
    private RootAOServConnector() {}

    /**
     * Gets the root connector.  Because this potentially has unrestricted privileges, this must be used at an absolute minimum for situations
     * where a user isn't logged-in but access to the master is required, such as for sign-up requests.
     */
    public static AOServConnector getRootAOServConnector(ServletContext servletContext) throws IOException {
        return AOServConnector.getConnector(
            servletContext.getInitParameter("root.aoserv.client.username"),
            servletContext.getInitParameter("root.aoserv.client.password"),
            new StandardErrorHandler()
        );
    }
}
