package com.aoindustries.website.signup;

/*
 * Copyright 2009-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.PackageCategory;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.website.HttpAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class IndexAction extends HttpAction {

    @Override
    public ActionForward executeProtocolAccepted(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, SiteSettings siteSettings, Skin skin) throws Exception {
        AOServConnector rootConn = SiteSettings.getInstance(getServlet().getServletContext()).getRootAOServConnector();

        // Determine the active packages per category
        Map<PackageCategory,List<PackageDefinition>> categories = rootConn.getThisBusinessAdministrator().getUsername().getBusiness().getActivePackageDefinitions();
        // 404 when no packages defined
        if(categories.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        // 301 redirect when only one package category is available
        if(categories.size()==1) {
            String categoryName = categories.keySet().iterator().next().getName();
            if(PackageCategory.AOSERV.equals(categoryName)) {
                response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"signup/aoserv.do"));
                return null;
            }
            if(PackageCategory.APPLICATION.equals(categoryName)) {
                response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"signup/application.do"));
                return null;
            }
            if(PackageCategory.BACKUP.equals(categoryName)) {
                response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"signup/backup.do"));
                return null;
            }
            if(PackageCategory.COLOCATION.equals(categoryName)) {
                response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"signup/colocation.do"));
                return null;
            }
            if(PackageCategory.DEDICATED.equals(categoryName)) {
                response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"signup/dedicated-server.do"));
                return null;
            }
            if(PackageCategory.MANAGED.equals(categoryName)) {
                response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"signup/managed-server.do"));
                return null;
            }
            if(PackageCategory.RESELLER.equals(categoryName)) {
                response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"signup/reseller.do"));
                return null;
            }
            if(PackageCategory.SYSADMIN.equals(categoryName)) {
                response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"signup/system-administration.do"));
                return null;
            }
            if(PackageCategory.VIRTUAL.equals(categoryName)) {
                response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"signup/virtual-hosting.do"));
                return null;
            }
            if(PackageCategory.VIRTUAL_DEDICATED.equals(categoryName)) {
                response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"signup/virtual-dedicated-server.do"));
                return null;
            }
            if(PackageCategory.VIRTUAL_MANAGED.equals(categoryName)) {
                response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"signup/virtual-managed-server.do"));
                return null;
            }
            throw new ServletException("Unsupported package category: "+categoryName);
        }
        request.setAttribute("categories", categories);
        return mapping.findForward("success");
    }
}
