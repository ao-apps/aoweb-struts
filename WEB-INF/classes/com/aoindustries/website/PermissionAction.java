package com.aoindustries.website;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
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

    @Override
    final public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Locale locale,
        Skin skin,
        AOServConnector<?,?> aoConn
    ) throws Exception {
        Set<AOServPermission.Permission> permissions = getPermissions();
        
        // No permissions defined, default to denied
        if(permissions==null || permissions.isEmpty()) {
            SortedSet<AOServPermission> aoPerms = Collections.emptySortedSet();
            return executePermissionDenied(
                mapping,
                form,
                request,
                response,
                siteSettings,
                locale,
                skin,
                aoConn,
                aoPerms
            );
        }

        if(aoConn.getThisBusinessAdministrator().hasPermissions(permissions)) {
            // All permissions found, consider granted
            return executePermissionGranted(mapping, form, request, response, siteSettings, locale, skin, aoConn);
        } else {
            SortedSet<AOServPermission> aoPerms = new TreeSet<AOServPermission>();
            for(AOServPermission.Permission requiredPermission : permissions) aoPerms.add(aoConn.getAoservPermissions().get(requiredPermission.name()));
            return executePermissionDenied(
                mapping,
                form,
                request,
                response,
                siteSettings,
                locale,
                skin,
                aoConn,
                aoPerms
            );
        }
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
        SiteSettings siteSettings,
        Locale locale,
        Skin skin,
        AOServConnector<?,?> aoConn
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
        SiteSettings siteSettings,
        Locale locale,
        Skin skin,
        AOServConnector<?,?> aoConn,
        SortedSet<AOServPermission> permissions
    ) throws Exception {
        request.setAttribute(Constants.PERMISSION_DENIED, permissions);
        return mapping.findForward("permission-denied");
    }

    /**
     * Gets the set of permissions that are required for this action.  Returning a null or empty list will result in nothing being allowed.
     *
     * @see  AOServPermission
     */
    abstract public Set<AOServPermission.Permission> getPermissions();
}
