package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import java.math.BigDecimal;
import java.util.Comparator;

/**
 * @author  AO Industries, Inc.
 */
public class Option {

    public static class PriceComparator implements Comparator<Option> {
        public int compare(Option pdl1, Option pdl2) {
            return pdl1.getPriceDifference().compareTo(pdl2.getPriceDifference());
        }
    }

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
