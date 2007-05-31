package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.PackageCategory;
import java.io.Serializable;

/**
 * @author  AO Industries, Inc.
 */
public class ManagedSignupSelectServerForm extends SignupSelectServerForm implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String getPackageCategory() {
        return PackageCategory.MANAGED;
    }
}
