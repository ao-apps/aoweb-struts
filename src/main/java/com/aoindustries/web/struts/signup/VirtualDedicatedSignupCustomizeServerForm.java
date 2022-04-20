/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2021, 2022  AO Industries, Inc.
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

import com.aoapps.servlet.attribute.ScopeEE;
import java.io.Serializable;

/**
 * @author  AO Industries, Inc.
 */
public class VirtualDedicatedSignupCustomizeServerForm extends SignupCustomizeServerForm implements Serializable {

  public static final ScopeEE.Session.Attribute<VirtualDedicatedSignupCustomizeServerForm> SESSION_ATTRIBUTE =
    ScopeEE.SESSION.attribute("virtualDedicatedSignupCustomizeServerForm");

  private static final long serialVersionUID = 1L;

  @Override
  protected ScopeEE.Session.Attribute<VirtualDedicatedSignupSelectPackageForm> getSignupSelectPackageFormName() {
    return VirtualDedicatedSignupSelectPackageForm.SESSION_ATTRIBUTE;
  }
}
