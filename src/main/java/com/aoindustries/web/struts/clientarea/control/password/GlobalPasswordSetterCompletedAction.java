/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2000-2009, 2016, 2017, 2018, 2019, 2021  AO Industries, Inc.
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
 * along with aoweb-struts.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.web.struts.clientarea.control.password;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.account.User;
import com.aoindustries.aoserv.client.master.Permission;
import com.aoindustries.web.struts.PermissionAction;
import com.aoindustries.web.struts.Skin;
import java.sql.SQLException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class GlobalPasswordSetterCompletedAction extends PermissionAction {

	@Override
	public ActionForward executePermissionGranted(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		Skin skin,
		AOServConnector aoConn
	) throws Exception {
		GlobalPasswordSetterForm globalPasswordSetterForm = (GlobalPasswordSetterForm)form;

		// Validation
		ActionMessages errors = globalPasswordSetterForm.validate(mapping, request);
		if(errors!=null && !errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("input");
		}

		// Reset passwords here and clear the passwords from the form
		ActionMessages messages = new ActionMessages();
		List<String> usernames = globalPasswordSetterForm.getUsernames();
		List<String> newPasswords = globalPasswordSetterForm.getNewPasswords();
		List<String> confirmPasswords = globalPasswordSetterForm.getConfirmPasswords();
		for(int c=0;c<usernames.size();c++) {
			String newPassword = newPasswords.get(c);
			if(newPassword.length()>0) {
				User.Name username = User.Name.valueOf(usernames.get(c));
				User un = aoConn.getAccount().getUser().get(username);
				if(un == null) throw new SQLException("Unable to find Username: " + username);
				un.setPassword(newPassword);
				messages.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage("password.globalPasswordSetter.field.confirmPasswords.passwordReset"));
				newPasswords.set(c, "");
				confirmPasswords.set(c, "");
			}
		}
		saveMessages(request, messages);

		return mapping.findForward("success");
	}

	static final Set<Permission.Name> permissions = Collections.unmodifiableSet(
		EnumSet.of(
			Permission.Name.set_business_administrator_password,
			Permission.Name.set_linux_server_account_password,
			Permission.Name.set_mysql_server_user_password,
			Permission.Name.set_postgres_server_user_password
		)
	);

	@Override
	public Set<Permission.Name> getPermissions() {
		return permissions;
	}
}
