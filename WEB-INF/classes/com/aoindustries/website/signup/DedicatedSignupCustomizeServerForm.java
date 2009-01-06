package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import java.io.Serializable;

/**
 * @author  AO Industries, Inc.
 */
public class DedicatedSignupCustomizeServerForm extends SignupCustomizeServerForm implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String getSignupSelectServerFromName() {
        return "dedicatedSignupSelectServerForm";
    }
}
