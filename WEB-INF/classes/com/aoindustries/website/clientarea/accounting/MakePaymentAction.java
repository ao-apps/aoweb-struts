package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.website.AuthenticatedAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gets the list of businesses or redirects to next step if only one business accessible.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentAction extends AuthenticatedAction {

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
        Business thisBusiness = aoConn.getThisBusinessAdministrator().getUsername().getBusiness();

        // Get the list of businesses that are not canceled or have a non-zero balance, or are thisBusiness
        SortedSet<Business> businesses = new TreeSet<Business>();
        for(Business business : aoConn.getBusinesses().getSet()) {
            if(
                thisBusiness.equals(business)
                || (
                    business.getCanceled()==null
                    && !business.billParent()
                ) || business.getAccountBalance().compareTo(BigDecimal.ZERO)!=0
            ) businesses.add(business);
        }
        if(businesses.size()==1) {
            // Redirect, only one option
            response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"clientarea/accounting/make-payment-select-card.do?accounting="+businesses.first().getAccounting()));
            return null;
        } else {
            // Show selector screen
            request.setAttribute("businesses", businesses);
            return mapping.findForward("success");
        }
    }
}
