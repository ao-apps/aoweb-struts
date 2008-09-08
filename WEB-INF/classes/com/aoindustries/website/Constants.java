package com.aoindustries.website;

/*
 * Copyright 2007-2008 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */

/**
 * Constants that may be used by other applications.
 *
 * @author  AO Industries, Inc.
 */
public class Constants {

    /**
     * The session key that stores when a "su" has been requested.
     */
    public static final String SU_REQUESTED = "suRequested";

    /**
     * The session key used to store the effective <code>AOServConnector</code> when the user has successfully authenticated.  Any "su" can change this.
     */
    public static final String AO_CONN = "aoConn";

    /**
     * The session key used to store the <code>AOServConnector</code> that the user has authenticated as.  "su" will not changes this.
     */
    public static final String AUTHENTICATED_AO_CONN="authenticatedAoConn";

    /**
     * The session key that stores the authentication target.
     */
    public static final String AUTHENTICATION_TARGET="authenticationTarget";

    /**
     * The request key used to store authentication messages.
     */
    public static final String AUTHENTICATION_MESSAGE = "authenticationMessage";

    /**
     * The session key used to store the current <code>layout</code>.
     */
    public static final String LAYOUT = "layout";

    /**
     * The request key used to store the current <code>Skin</code>.
     */
    public static final String SKIN = "skin";

    /**
     * The request key used to store the current <code>Locale</code>.
     */
    public static final String LOCALE = "locale";
    
    /**
     * The session key used to store the <code>List&lt;AOServPermission&gt;</code> that ALL must be allowed for the specified task.
     */
    public static final String PERMISSION_DENIED = "permissionDenied";
}
