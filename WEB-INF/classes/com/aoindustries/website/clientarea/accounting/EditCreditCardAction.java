package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.BusinessAdministrator;
import com.aoindustries.aoserv.client.BusinessProfile;
import com.aoindustries.aoserv.client.CreditCard;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.Skin;
import com.aoindustries.website.signup.SignupBusinessActionHelper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gets the form for editing a credit card.
 *
 * @author  AO Industries, Inc.
 */
public class EditCreditCardAction extends PermissionAction {

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        EditCreditCardForm editCreditCardForm=(EditCreditCardForm)form;

        String persistenceId = editCreditCardForm.getPersistenceId();
        if(GenericValidator.isBlankOrNull(persistenceId)) {
            // Redirect back to credit-card-manager if no persistenceId selected
            return mapping.findForward("credit-card-manager");
        }
        int pkey;
        try {
            pkey = Integer.parseInt(persistenceId);
        } catch(NumberFormatException err) {
            getServlet().log(null, err);
            // Redirect back to credit-card-manager if persistenceId can't be parsed to int
            return mapping.findForward("credit-card-manager");
        }
        // Find the credit card
        CreditCard creditCard = aoConn.creditCards.get(pkey);
        if(creditCard==null) {
            // Redirect back to credit-card-manager if card no longer exists or is inaccessible
            return mapping.findForward("credit-card-manager");
        }

        // Populate the initial details from selected card
        editCreditCardForm.setIsActive(creditCard.getIsActive() ? "true" : "false");
        editCreditCardForm.setAccounting(creditCard.getBusiness().getAccounting());
        editCreditCardForm.setFirstName(creditCard.getFirstName());
        editCreditCardForm.setLastName(creditCard.getLastName());
        editCreditCardForm.setCompanyName(creditCard.getCompanyName());
        editCreditCardForm.setEmail(creditCard.getEmail());
        editCreditCardForm.setPhone(creditCard.getPhone());
        editCreditCardForm.setFax(creditCard.getFax());
        editCreditCardForm.setCustomerTaxId(creditCard.getCustomerTaxId());
        editCreditCardForm.setStreetAddress1(creditCard.getStreetAddress1());
        editCreditCardForm.setStreetAddress2(creditCard.getStreetAddress2());
        editCreditCardForm.setCity(creditCard.getCity());
        editCreditCardForm.setState(creditCard.getState());
        editCreditCardForm.setPostalCode(creditCard.getPostalCode());
        editCreditCardForm.setCountryCode(creditCard.getCountryCode().getCode());
        editCreditCardForm.setDescription(creditCard.getDescription());

        initRequestAttributes(request, aoConn);
        
        request.setAttribute("creditCard", creditCard);

        return mapping.findForward("success");
    }

    protected void initRequestAttributes(HttpServletRequest request, AOServConnector aoConn) throws SQLException {
        // Build the list of years
        List<String> expirationYears = new ArrayList<String>(12);
        int startYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int c=0;c<12;c++) expirationYears.add(Integer.toString(startYear+c));

        // Build the list of countries
        List<SignupBusinessActionHelper.CountryOption> countryOptions = SignupBusinessActionHelper.getCountryOptions(aoConn);

        // Store to request attributes
        request.setAttribute("expirationYears", expirationYears);
        request.setAttribute("countryOptions", countryOptions);
    }

    public List<AOServPermission.Permission> getPermissions() {
        return Collections.singletonList(AOServPermission.Permission.add_credit_card);
    }
}
