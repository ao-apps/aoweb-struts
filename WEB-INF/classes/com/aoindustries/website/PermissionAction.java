package com.aoindustries.website;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.BusinessAdministrator;
import com.aoindustries.util.ErrorPrinter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;
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
abstract public class PermissionAction extends AuthenticatedAction {

    final public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        List<AOServPermission.Permission> permissions = getPermissions();
        if(permissions==null || permissions.isEmpty()) {
            request.setAttribute(Constants.PERMISSION_DENIED, Collections.emptyList());
            return mapping.findForward("permission-denied");
        }

        BusinessAdministrator thisBA = aoConn.getThisBusinessAdministrator();
        for(AOServPermission.Permission permission : permissions) {
            if(!thisBA.hasPermission(permission)) {
                List<AOServPermission> aoPerms = new ArrayList<AOServPermission>(permissions.size());
                for(AOServPermission.Permission requiredPermission : permissions) {
                    AOServPermission aoPerm = aoConn.aoservPermissions.get(requiredPermission);
                    if(aoPerm==null) throw new SQLException("Unable to find AOServPermission: "+requiredPermission);
                    aoPerms.add(aoPerm);
                }
                request.setAttribute(Constants.PERMISSION_DENIED, aoPerms);
                return mapping.findForward("permission-denied");
            }
        }
        return executePermissionGranted(mapping, form, request, response, locale, skin, aoConn);
    }

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        return mapping.findForward("success");
    }

    /**
     * Gets the list of permissions that are required for this action.  Returning a null or empty list will result in nothing being allowed.
     *
     * @see  AOServPermission.Permission
     */
    abstract public List<AOServPermission.Permission> getPermissions();
}
