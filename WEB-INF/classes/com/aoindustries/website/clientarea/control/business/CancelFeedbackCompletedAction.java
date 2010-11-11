package com.aoindustries.website.clientarea.control.business;

/*
 * Copyright 2003-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.command.CancelBusinessCommand;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.aoserv.client.validator.AccountingCode;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Performs the actual cancellation.
 *
 * @author  AO Industries, Inc.
 */
public class CancelFeedbackCompletedAction extends PermissionAction {

    @Override
    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        AOServConnector<?,?> aoConn
    ) throws Exception {
        CancelFeedbackForm cancelFeedbackForm = (CancelFeedbackForm)form;
        String business = cancelFeedbackForm.getBusiness();
        String reason = cancelFeedbackForm.getReason();

        Business bu;
        if(GenericValidator.isBlankOrNull(business)) {
            bu = null;
        } else {
            bu = aoConn.getBusinesses().get(AccountingCode.valueOf(business));
        }
        if(bu==null || !new CancelBusinessCommand(bu, null).validate(aoConn).isEmpty()) {
            return mapping.findForward("invalid-business");
        }

        // Do the actual cancellation
        new CancelBusinessCommand(bu, reason).execute(aoConn);

        // Set request values
        request.setAttribute("business", bu);

        return mapping.findForward("success");
    }

    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return CommandName.cancel_business.getPermissions();
    }
}
