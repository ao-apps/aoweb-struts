package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.CreditCard;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.Skin;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Prompts if the user really wants to delete a credit card.
 *
 * @author  AO Industries, Inc.
 */
public class DeleteCreditCardAction extends PermissionAction {

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        // Make sure the credit card still exists, redirect to credit-card-manager if doesn't
        CreditCard creditCard = null;
        String S = request.getParameter("pkey");
        if(S!=null && S.length()>0) {
            try {
                int pkey = Integer.parseInt(S);
                creditCard = aoConn.creditCards.get(pkey);
            } catch(NumberFormatException err) {
                getServlet().log(null, err);
            }
        }
        if(creditCard==null) return mapping.findForward("credit-card-manager");

        // Set request attributes
        request.setAttribute("creditCard", creditCard);

        // Return status success
        return mapping.findForward("success");
    }

    public List<AOServPermission.Permission> getPermissions() {
        return Collections.singletonList(AOServPermission.Permission.delete_credit_card);
    }
}
