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
import com.aoindustries.aoserv.client.master.Permission;
import com.aoindustries.aoserv.client.mysql.Server;
import com.aoindustries.aoserv.client.mysql.User;
import com.aoindustries.aoserv.client.mysql.UserServer;
import com.aoindustries.web.struts.PermissionAction;
import com.aoindustries.web.struts.Skin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Prepares for MySQL user password setting.  Populates lists in mySQLPasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class MySQLPasswordSetterAction extends PermissionAction {

	@Override
	public ActionForward executePermissionGranted(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		Locale locale,
		Skin skin,
		AOServConnector aoConn
	) throws Exception {
		MySQLPasswordSetterForm mySQLPasswordSetterForm = (MySQLPasswordSetterForm)form;

		List<UserServer> msus = aoConn.getMysql().getUserServer().getRows();

		List<String> packages = new ArrayList<>(msus.size());
		List<String> usernames = new ArrayList<>(msus.size());
		List<String> mySQLServers = new ArrayList<>(msus.size());
		List<String> servers = new ArrayList<>(msus.size());
		List<String> newPasswords = new ArrayList<>(msus.size());
		List<String> confirmPasswords = new ArrayList<>(msus.size());
		for(UserServer msu : msus) {
			if(msu.canSetPassword()) {
				User mu = msu.getMySQLUser();
				com.aoindustries.aoserv.client.account.User un = mu.getUsername();
				Server ms = msu.getMySQLServer();
				packages.add(un.getPackage().getName().toString());
				usernames.add(un.getUsername().toString());
				mySQLServers.add(ms.getName().toString());
				servers.add(ms.getLinuxServer().getHostname().toString());
				newPasswords.add("");
				confirmPasswords.add("");
			}
		}

		// Store to the form
		mySQLPasswordSetterForm.setPackages(packages);
		mySQLPasswordSetterForm.setUsernames(usernames);
		mySQLPasswordSetterForm.setMySQLServers(mySQLServers);
		mySQLPasswordSetterForm.setServers(servers);
		mySQLPasswordSetterForm.setNewPasswords(newPasswords);
		mySQLPasswordSetterForm.setConfirmPasswords(confirmPasswords);

		return mapping.findForward("success");
	}

	@Override
	public Set<Permission.Name> getPermissions() {
		return Collections.singleton(Permission.Name.set_mysql_server_user_password);
	}
}
