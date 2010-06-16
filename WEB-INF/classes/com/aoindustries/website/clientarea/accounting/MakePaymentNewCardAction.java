package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.BusinessAdministrator;
import com.aoindustries.aoserv.client.BusinessProfile;
import com.aoindustries.aoserv.client.validator.AccountingCode;
import com.aoindustries.util.i18n.Money;
import com.aoindustries.website.AuthenticatedAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import com.aoindustries.website.signup.SignupBusinessActionHelper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import java.util.SortedMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gets the form for adding a credit card.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentNewCardAction extends AuthenticatedAction {

    @Override
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        AOServConnector<?,?> aoConn
    ) throws Exception {
        MakePaymentNewCardForm makePaymentNewCardForm=(MakePaymentNewCardForm)form;

        String accounting = makePaymentNewCardForm.getAccounting();
        if(GenericValidator.isBlankOrNull(accounting)) {
            // Redirect back to credit-card-manager it no accounting selected
            return mapping.findForward("make-payment");
        }

        // Populate the initial details from the selected accounting code or authenticated user
        Business business = aoConn.getBusinesses().get(AccountingCode.valueOf(accounting));
        BusinessProfile profile = business.getBusinessProfile();
        if(profile!=null) {
            makePaymentNewCardForm.setFirstName(AddCreditCardAction.getFirstName(profile.getBillingContact()));
            makePaymentNewCardForm.setLastName(AddCreditCardAction.getLastName(profile.getBillingContact()));
            makePaymentNewCardForm.setCompanyName(profile.getName());
            makePaymentNewCardForm.setEmail(profile.getBillingEmail().isEmpty() ? "" : profile.getBillingEmail().get(0).toString());
            makePaymentNewCardForm.setPhone(profile.getPhone());
            makePaymentNewCardForm.setFax(profile.getFax());
            makePaymentNewCardForm.setStreetAddress1(profile.getAddress1());
            makePaymentNewCardForm.setStreetAddress2(profile.getAddress2());
            makePaymentNewCardForm.setCity(profile.getCity());
            makePaymentNewCardForm.setState(profile.getState());
            makePaymentNewCardForm.setPostalCode(profile.getZip());
            makePaymentNewCardForm.setCountryCode(profile.getCountry().getCode());
        } else {
            BusinessAdministrator thisBA = aoConn.getThisBusinessAdministrator();
            makePaymentNewCardForm.setFirstName(AddCreditCardAction.getFirstName(thisBA.getName()));
            makePaymentNewCardForm.setLastName(AddCreditCardAction.getLastName(thisBA.getName()));
            makePaymentNewCardForm.setEmail(thisBA.getEmail().toString());
            makePaymentNewCardForm.setPhone(thisBA.getWorkPhone());
            makePaymentNewCardForm.setFax(thisBA.getFax());
            makePaymentNewCardForm.setStreetAddress1(thisBA.getAddress1());
            makePaymentNewCardForm.setStreetAddress2(thisBA.getAddress2());
            makePaymentNewCardForm.setCity(thisBA.getCity());
            makePaymentNewCardForm.setState(thisBA.getState());
            makePaymentNewCardForm.setPostalCode(thisBA.getZIP());
            makePaymentNewCardForm.setCountryCode(thisBA.getCountry()==null ? "" : thisBA.getCountry().getCode());
        }

        initRequestAttributes(request, getServlet().getServletContext());

        // Prompt for amount of payment defaults to current balance.
        SortedMap<Currency,Money> balances = business.getAccountBalances();
        Money posBalance = null;
        for(Money balance : balances.values()) {
            if(balance.getValue().compareTo(BigDecimal.ZERO)>0) {
                posBalance = balance;
                break;
            }
        }
        if(posBalance!=null) {
            makePaymentNewCardForm.setCurrency(posBalance.getCurrency().getCurrencyCode());
            makePaymentNewCardForm.setPaymentAmount(posBalance.getValue().toPlainString());
        } else {
            makePaymentNewCardForm.setCurrency(business.getPackageDefinition().getMonthlyRate().getCurrency().getCurrencyCode());
            makePaymentNewCardForm.setPaymentAmount("");
        }

        request.setAttribute("currencies", balances.keySet());
        request.setAttribute("business", business);

        return mapping.findForward("success");
    }

    protected void initRequestAttributes(HttpServletRequest request, ServletContext context) throws IOException {
        // Build the list of years
        List<String> expirationYears = new ArrayList<String>(12);
        int startYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int c=0;c<12;c++) expirationYears.add(Integer.toString(startYear+c));

        // Build the list of countries
        // We use the root connector to provide a better set of country values
        List<SignupBusinessActionHelper.CountryOption> countryOptions = SignupBusinessActionHelper.getCountryOptions(SiteSettings.getInstance(context).getRootAOServConnector());

        // Store to request attributes
        request.setAttribute("expirationYears", expirationYears);
        request.setAttribute("countryOptions", countryOptions);
    }
}