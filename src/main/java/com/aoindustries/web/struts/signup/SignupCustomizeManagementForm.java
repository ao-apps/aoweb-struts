/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2017, 2020, 2021, 2022  AO Industries, Inc.
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
import com.aoindustries.web.struts.SessionActionForm;
import java.io.Serializable;
import org.apache.struts.action.ActionForm;

/**
 * @author  AO Industries, Inc.
 */
public class SignupCustomizeManagementForm extends ActionForm implements Serializable, SessionActionForm {

  public static final ScopeEE.Session.Attribute<SignupCustomizeManagementForm> MANAGED_SESSION_ATTRIBUTE =
      ScopeEE.SESSION.attribute("managedSignupCustomizeManagementForm");

  public static final ScopeEE.Session.Attribute<SignupCustomizeManagementForm> VIRTUAL_MANAGED_SESSION_ATTRIBUTE =
      ScopeEE.SESSION.attribute("virtualManagedSignupCustomizeManagementForm");

  private static final long serialVersionUID = 1L;

  private int backupOnsiteOption;
  private int backupOffsiteOption;
  private String backupDvdOption;
  private int distributionScanOption;
  private int failoverOption;
  private String formCompleted;

  public SignupCustomizeManagementForm() {
    setBackupOnsiteOption(-1);
    setBackupOffsiteOption(-1);
    setBackupDvdOption("");
    setDistributionScanOption(-1);
    setFailoverOption(-1);
    setFormCompleted("false");
  }

  @Override
  public boolean isEmpty() {
    return
        backupOnsiteOption == -1
            && backupOffsiteOption == -1
            && "".equals(backupDvdOption)
            && distributionScanOption == -1
            && failoverOption == -1
            && "false".equalsIgnoreCase(formCompleted)
    ;
  }

  public final int getBackupOnsiteOption() {
    return backupOnsiteOption;
  }

  public final void setBackupOnsiteOption(int backupOnsiteOption) {
    this.backupOnsiteOption = backupOnsiteOption;
  }

  public final int getBackupOffsiteOption() {
    return backupOffsiteOption;
  }

  public final void setBackupOffsiteOption(int backupOffsiteOption) {
    this.backupOffsiteOption = backupOffsiteOption;
  }

  public final String getBackupDvdOption() {
    return backupDvdOption;
  }

  public final void setBackupDvdOption(String backupDvdOption) {
    this.backupDvdOption = backupDvdOption;
  }

  public final int getDistributionScanOption() {
    return distributionScanOption;
  }

  public final void setDistributionScanOption(int distributionScanOption) {
    this.distributionScanOption = distributionScanOption;
  }

  public final int getFailoverOption() {
    return failoverOption;
  }

  public final void setFailoverOption(int failoverOption) {
    this.failoverOption = failoverOption;
  }

  public final String getFormCompleted() {
    return formCompleted;
  }

  public final void setFormCompleted(String formCompleted) {
    this.formCompleted = formCompleted;
  }
}
