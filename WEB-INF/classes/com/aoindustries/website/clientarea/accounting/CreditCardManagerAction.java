package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.CreditCard;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.Skin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Displays the list of credit cards.
 *
 * @author  AO Industries, Inc.
 */
public class CreditCardManagerAction extends PermissionAction {

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        // Create a map from business to list of credit cards
        List<BusinessAndCreditCards> businessCreditCards = new ArrayList<BusinessAndCreditCards>();
        for(Business business : aoConn.businesses.getRows()) {
            List<CreditCard> ccs = business.getCreditCards();
            boolean hasActiveCard = false;
            for(CreditCard cc : ccs) {
                if(cc.getIsActive()) {
                    hasActiveCard = true;
                    break;
                }
            }
            businessCreditCards.add(new BusinessAndCreditCards(business, ccs, hasActiveCard));
        }
        boolean showAccounting = aoConn.businesses.getRows().size()>1;

        request.setAttribute("businessCreditCards", businessCreditCards);
        request.setAttribute("showAccounting", showAccounting ? "true" : "false");

        return mapping.findForward("success");
    }

    public List<AOServPermission.Permission> getPermissions() {
        return Collections.singletonList(AOServPermission.Permission.get_credit_cards);
    }
    
    public static class BusinessAndCreditCards {

        final private Business business;
        final private List<CreditCard> creditCards;
        final private boolean hasActiveCard;
        
        private BusinessAndCreditCards(Business business, List<CreditCard> creditCards, boolean hasActiveCard) {
            this.business=business;
            this.creditCards=creditCards;
            this.hasActiveCard=hasActiveCard;
        }
        
        public Business getBusiness() {
            return business;
        }
        
        public List<CreditCard> getCreditCards() {
            return creditCards;
        }
        
        public boolean getHasActiveCard() {
            return hasActiveCard;
        }
    }
}
