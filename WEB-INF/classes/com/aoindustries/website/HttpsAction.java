package com.aoindustries.website;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
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
 * Will only accept the HTTPS protocol.
 *
 * @author Dan Armstrong &lt;dan@aoindustries.com&gt;
 */
public class HttpsAction extends ProtocolAction {

    public int getAcceptableProtocols() {
        return HTTPS;
    }
}
