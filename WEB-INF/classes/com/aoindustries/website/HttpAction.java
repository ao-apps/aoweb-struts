package com.aoindustries.website;

/*
 * Copyright 2007-2008 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Will only accept the HTTP protocol.
 *
 * @author Dan Armstrong &lt;dan@aoindustries.com&gt;
 */
public class HttpAction extends ProtocolAction {

    public int getAcceptableProtocols() {
        return HTTP;
    }
}
