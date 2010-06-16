package com.aoindustries.website.signup;

/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import java.io.Serializable;

/**
 * @author  AO Industries, Inc.
 */
public class VirtualManagedSignupCustomizeServerForm extends SignupCustomizeServerForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    protected String getSignupSelectPackageFormName() {
        return "virtualManagedSignupSelectPackageForm";
    }
}
