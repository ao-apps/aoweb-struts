package com.aoindustries.website;

/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */

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
