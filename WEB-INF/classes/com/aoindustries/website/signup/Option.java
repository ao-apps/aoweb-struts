package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import java.math.BigDecimal;

/**
 * @author  AO Industries, Inc.
 */
public class Option {

    final private int packageDefinitionLimit;
    final private String display;
    final private BigDecimal priceDifference;

    public Option(
        int packageDefinitionLimit,
        String display,
        BigDecimal priceDifference
    ) {
        this.packageDefinitionLimit = packageDefinitionLimit;
        this.display = display;
        this.priceDifference = priceDifference;
    }

    public int getPackageDefinitionLimit() {
        return packageDefinitionLimit;
    }

    public String getDisplay() {
        return display;
    }

    public BigDecimal getPriceDifference() {
        return priceDifference;
    }
}
