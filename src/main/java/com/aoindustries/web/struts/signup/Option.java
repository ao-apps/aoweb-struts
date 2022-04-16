/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2019, 2021, 2022  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.aoindustries.web.struts.signup;

import com.aoapps.lang.i18n.Monies;
import java.util.Comparator;

/**
 * @author  AO Industries, Inc.
 */
public class Option {

	public static final Comparator<Option> priceComparator =
		(pdl1, pdl2) -> pdl1.getPriceDifference().compareTo(pdl2.getPriceDifference());

	private final int packageDefinitionLimit;
	private final String display;
	private final Monies priceDifference;

	public Option(
		int packageDefinitionLimit,
		String display,
		Monies priceDifference
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

	public Monies getPriceDifference() {
		return priceDifference;
	}
}
