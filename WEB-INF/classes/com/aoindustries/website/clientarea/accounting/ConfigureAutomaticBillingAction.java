package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.CreditCard;
import com.aoindustries.aoserv.client.validator.AccountingCode;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Allows the selection of the card to use for automatic billing.
 *
 * @author  AO Industries, Inc.
 */
public class ConfigureAutomaticBillingAction extends PermissionAction {

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
        // Business must be selected and accessible
        String accounting = request.getParameter("accounting");
        if(GenericValidator.isBlankOrNull(accounting)) {
            return mapping.findForward("credit-card-manager");
        }
        Business business = aoConn.getBusinesses().get(AccountingCode.valueOf(accounting));
        if(business==null) {
            return mapping.findForward("credit-card-manager");
        }

        // Get the list of cards for the business, must have at least one card.
        // Build list of active cards
        SortedSet<CreditCard> activeCards = new TreeSet<CreditCard>();
        CreditCard automaticCard = null;
        for(CreditCard creditCard : new TreeSet<CreditCard>(business.getCreditCards())) {
            if(creditCard.isActive()) {
                activeCards.add(creditCard);
                // The first automatic card is used
                if(automaticCard==null && creditCard.getUseMonthly()) automaticCard = creditCard;
            }
        }
        if(activeCards.isEmpty()) return mapping.findForward("credit-card-manager");

        // Store request attributes
        request.setAttribute("business", business);
        request.setAttribute("activeCards", activeCards);
        request.setAttribute("automaticCard", automaticCard);

        return mapping.findForward("success");
    }

    private static Set<AOServPermission.Permission> permissions;
    static {
        Set<AOServPermission.Permission> newSet = new HashSet<AOServPermission.Permission>(2*4/3+1);
        newSet.add(AOServPermission.Permission.get_credit_cards);
        newSet.add(AOServPermission.Permission.edit_credit_card);
        permissions = Collections.unmodifiableSet(newSet);
    }

    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return permissions;
    }
}
