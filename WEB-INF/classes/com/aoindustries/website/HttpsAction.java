package com.aoindustries.website;

/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */

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
