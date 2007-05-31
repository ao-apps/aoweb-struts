package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
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
