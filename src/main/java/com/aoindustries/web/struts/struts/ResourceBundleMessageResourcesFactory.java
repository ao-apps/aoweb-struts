/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2021  AO Industries, Inc.
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
package com.aoindustries.web.struts.struts;

import java.io.Serializable;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

/**
 * Sets the keywords for the page.s
 *
 * @author  AO Industries, Inc.
 */
public class ResourceBundleMessageResourcesFactory extends MessageResourcesFactory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public MessageResources createResources(String config) {
		return new ResourceBundleMessageResources(this, config, this.returnNull);
	}
}
