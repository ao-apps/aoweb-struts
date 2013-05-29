package com.aoindustries.website.signup;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.PackageCategory;
import java.io.Serializable;

/**
 * @author  AO Industries, Inc.
 */
public class ResellerSignupSelectPackageForm extends SignupSelectPackageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String getPackageCategory() {
        return PackageCategory.RESELLER;
    }
}
