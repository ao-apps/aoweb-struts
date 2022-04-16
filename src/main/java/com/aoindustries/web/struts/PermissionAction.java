/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2018, 2019, 2021, 2022  AO Industries, Inc.
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

package com.aoindustries.web.struts;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.account.Administrator;
import com.aoindustries.aoserv.client.master.Permission;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Makes sure the authenticated user has the necessary permissions to perform the requested task.
 * If they do not, sets the request attribute "permissionDenied" with the <code>List&lt;AOServConnector&gt;</code> and returns mapping for "permissionDenied".
 * Otherwise, if all the permissions have been granted, calls <code>executePermissionGranted</code>.
 *
 * The default implementation of this new <code>executePermissionGranted</code> method simply returns the mapping
 * of "success".
 *
 * @author  AO Industries, Inc.
 */
public abstract class PermissionAction extends AuthenticatedAction {

	@Override
	public final ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		AOServConnector aoConn
	) throws Exception {
		Set<Permission.Name> permissions = getPermissions();

		// No permissions defined, default to denied
		if(permissions==null || permissions.isEmpty()) {
			List<Permission> aoPerms = Collections.emptyList();
			return executePermissionDenied(
				mapping,
				form,
				request,
				response,
				aoConn,
				aoPerms
			);
		}

		Administrator thisBA = aoConn.getCurrentAdministrator();
		// Return denied on first missing permission
		for(Permission.Name permission : permissions) {
			if(!thisBA.hasPermission(permission)) {
				List<Permission> aoPerms = new ArrayList<>(permissions.size());
				for(Permission.Name requiredPermission : permissions) {
					Permission aoPerm = aoConn.getMaster().getPermission().get(requiredPermission);
					if(aoPerm==null) throw new SQLException("Unable to find AOServPermission: "+requiredPermission);
					aoPerms.add(aoPerm);
				}
				return executePermissionDenied(
					mapping,
					form,
					request,
					response,
					aoConn,
					aoPerms
				);
			}
		}

		// All permissions found, consider granted
		return executePermissionGranted(mapping, form, request, response, aoConn);
	}

	/**
	 * Called when permission has been granted.  By default,
	 * returns mapping for "success".
	 */
	public ActionForward executePermissionGranted(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		AOServConnector aoConn
	) throws Exception {
		return mapping.findForward("success");
	}

	/**
	 * Called when the permissions has been denied.  By default,
	 * sets request attribute <code>Constants.PERMISSION_DENIED</code>
	 * and returns mapping for "permission-denied".
	 */
	public ActionForward executePermissionDenied(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		AOServConnector aoConn,
		List<Permission> permissions
	) throws Exception {
		Constants.PERMISSION_DENIED.context(request).set(permissions);
		return mapping.findForward("permission-denied");
	}

	/**
	 * Gets the set of permissions that are required for this action.  Returning a null or empty set will result in nothing being allowed.
	 *
	 * @see  Permission
	 */
	public abstract Set<Permission.Name> getPermissions();
}
