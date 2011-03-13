package com.aoindustries.website.skintags;

/*
 * Copyright 2009-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import java.util.Collection;

/**
 * Something with a set of meta data.
 *
 * @author  AO Industries, Inc.
 */
public interface MetasAttribute {

    Collection<Meta> getMetas();

    void addMeta(Meta meta);
}
