package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.CreditCard;
import com.aoindustries.aoserv.client.PaymentType;
import com.aoindustries.aoserv.client.TransactionType;
import com.aoindustries.aoserv.client.command.AddTransactionCommand;
import com.aoindustries.aoserv.client.command.ApproveTransactionCommand;
import com.aoindustries.aoserv.client.command.DeclineTransactionCommand;
import com.aoindustries.aoserv.client.command.HoldTransactionCommand;
import com.aoindustries.aoserv.client.validator.AccountingCode;
import com.aoindustries.aoserv.client.validator.ValidationException;
import com.aoindustries.aoserv.creditcards.AOServConnectorPrincipal;
import com.aoindustries.aoserv.creditcards.BusinessGroup;
import com.aoindustries.aoserv.creditcards.CreditCardFactory;
import com.aoindustries.aoserv.creditcards.CreditCardProcessorFactory;
import com.aoindustries.creditcards.AuthorizationResult;
import com.aoindustries.creditcards.CreditCardProcessor;
import com.aoindustries.creditcards.Transaction;
import com.aoindustries.creditcards.TransactionRequest;
import com.aoindustries.util.i18n.Money;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Currency;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * Payment from stored credit card.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentStoredCardCompletedAction extends MakePaymentStoredCardAction {

    @Override
    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        MakePaymentStoredCardForm makePaymentStoredCardForm = (MakePaymentStoredCardForm)form;

        // Init request values
        AccountingCode accounting;
        try {
            String accountingS = makePaymentStoredCardForm.getAccounting();
            accounting = accountingS==null ? null : AccountingCode.valueOf(accountingS);
        } catch(ValidationException err) {
            // Redirect back to make-payment if invalid accounting code
            return mapping.findForward("make-payment");
        }
        Business business = accounting==null ? null : aoConn.getBusinesses().get(accounting);
        if(business==null) {
            // Redirect back to make-payment if business not found
            return mapping.findForward("make-payment");
        }

        // If the card pkey in "", new card was selected
        String pkeyString = makePaymentStoredCardForm.getPkey();
        if(pkeyString==null) {
            // pkey not provided, redirect back to make-payment
            return mapping.findForward("make-payment");
        }
        if("".equals(pkeyString)) {
            response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"clientarea/accounting/make-payment-new-card.do?accounting="+URLEncoder.encode(accounting.toString(), "UTF-8")));
            return null;
        }

        int pkey;
        try {
            pkey = Integer.parseInt(pkeyString);
        } catch(NumberFormatException err) {
            // Can't parse int, redirect back to make-payment
            return mapping.findForward("make-payment");
        }
        CreditCard creditCard = aoConn.getCreditCards().get(pkey);
        if(creditCard==null) {
            // creditCard not found, redirect back to make-payment
            return mapping.findForward("make-payment");
        }

        request.setAttribute("business", business);
        request.setAttribute("creditCard", creditCard);

        // Validation
        ActionMessages errors = makePaymentStoredCardForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);

            return mapping.findForward("input");
        }

        // Convert to money
        Money paymentAmount = new Money(Currency.getInstance(makePaymentStoredCardForm.getCurrency()), new BigDecimal(makePaymentStoredCardForm.getPaymentAmount()));

        // Perform the transaction
        AOServConnector rootConn = siteSettings.getRootAOServConnector();
        
        // 1) Pick a processor
        CreditCard rootCreditCard = rootConn.getCreditCards().get(creditCard.getPkey());
        com.aoindustries.aoserv.client.CreditCardProcessor rootAoProcessor = rootCreditCard.getCreditCardProcessor();
        CreditCardProcessor rootProcessor = CreditCardProcessorFactory.getCreditCardProcessor(rootAoProcessor);

        // 2) Add the transaction as pending on this processor
        Business rootBusiness = rootConn.getBusinesses().get(accounting);
        TransactionType paymentTransactionType = rootConn.getTransactionTypes().get(TransactionType.PAYMENT);
        String paymentTypeName;
        String cardInfo = creditCard.getCardInfo();
        if(cardInfo.startsWith("34") || cardInfo.startsWith("37")) {
            paymentTypeName = PaymentType.AMEX;
        } else if(cardInfo.startsWith("60")) {
            paymentTypeName = PaymentType.DISCOVER;
        } else if(cardInfo.startsWith("51") || cardInfo.startsWith("52") || cardInfo.startsWith("53") || cardInfo.startsWith("54") || cardInfo.startsWith("55")) {
            paymentTypeName = PaymentType.MASTERCARD;
        } else if(cardInfo.startsWith("4")) {
            paymentTypeName = PaymentType.VISA;
        } else {
            paymentTypeName = null;
        }
        PaymentType paymentType;
        if(paymentTypeName==null) paymentType = null;
        else {
            paymentType = rootConn.getPaymentTypes().get(paymentTypeName);
        }

        int transID = new AddTransactionCommand(
            rootBusiness,
            rootBusiness,
            aoConn.getThisBusinessAdministrator(),
            paymentTransactionType,
            ApplicationResources.accessor.getMessage("makePaymentStoredCardCompleted.transaction.description"),
            BigDecimal.ONE,
            paymentAmount.negate(),
            paymentType,
            cardInfo,
            rootAoProcessor,
            com.aoindustries.aoserv.client.Transaction.Status.W
        ).execute(rootConn);
        com.aoindustries.aoserv.client.Transaction aoTransaction = rootConn.getTransactions().get(transID);

        // 3) Process
        Transaction transaction = rootProcessor.sale(
            new AOServConnectorPrincipal(rootConn, aoConn.getThisBusinessAdministrator().getUsername().getUsername().toString()),
            new BusinessGroup(rootBusiness, accounting.toString()),
            new TransactionRequest(
                false,
                request.getRemoteAddr(),
                120,
                Integer.toString(transID),
                paymentAmount.getCurrency(),
                paymentAmount.getValue(),
                null,
                false,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                ApplicationResources.accessor.getMessage("makePaymentStoredCardCompleted.transaction.description")
            ),
            CreditCardFactory.getCreditCard(rootCreditCard)
        );
        
        // 4) Decline/approve based on results
        AuthorizationResult authorizationResult = transaction.getAuthorizationResult();
        switch(authorizationResult.getCommunicationResult()) {
            case LOCAL_ERROR :
            case IO_ERROR :
            case GATEWAY_ERROR :
            {
                // Update transaction as failed
                new DeclineTransactionCommand(
                    aoTransaction,
                    rootConn.getCreditCardTransactions().get(Integer.parseInt(transaction.getPersistenceUniqueId()))
                ).execute(rootConn);
                // Get the list of active credit cards stored for this business
                SortedSet<CreditCard> creditCards = new TreeSet<CreditCard>();
                for(CreditCard tCreditCard : business.getCreditCards()) {
                    if(tCreditCard.getDeactivatedOn()==null) creditCards.add(tCreditCard);
                }
                // Store to request attributes, return success
                request.setAttribute("business", business);
                request.setAttribute("creditCards", creditCards);
                request.setAttribute("lastPaymentCreditCard", creditCard.getProviderUniqueId());
                request.setAttribute("errorReason", authorizationResult.getErrorCode().toString());
                return mapping.findForward("error");
            }
            case SUCCESS :
                // Check approval result
                switch(authorizationResult.getApprovalResult()) {
                    case HOLD :
                        new HoldTransactionCommand(
                            aoTransaction,
                            rootConn.getCreditCardTransactions().get(Integer.parseInt(transaction.getPersistenceUniqueId()))
                        ).execute(rootConn);
                        request.setAttribute("business", business);
                        request.setAttribute("creditCard", creditCard);
                        request.setAttribute("transaction", transaction);
                        request.setAttribute("aoTransaction", aoTransaction);
                        request.setAttribute("reviewReason", authorizationResult.getReviewReason().toString());
                        return mapping.findForward("hold");
                    case DECLINED :
                        // Update transaction as declined
                        new DeclineTransactionCommand(
                            aoTransaction,
                            rootConn.getCreditCardTransactions().get(Integer.parseInt(transaction.getPersistenceUniqueId()))
                        ).execute(rootConn);
                        // Get the list of active credit cards stored for this business
                        SortedSet<CreditCard> creditCards = new TreeSet<CreditCard>();
                        for(CreditCard tCreditCard : business.getCreditCards()) {
                            if(tCreditCard.getDeactivatedOn()==null) creditCards.add(tCreditCard);
                        }
                        // Store to request attributes, return success
                        request.setAttribute("business", business);
                        request.setAttribute("creditCards", creditCards);
                        request.setAttribute("lastPaymentCreditCard", creditCard.getProviderUniqueId());
                        request.setAttribute("declineReason", authorizationResult.getDeclineReason().toString());
                        return mapping.findForward("declined");
                    case APPROVED :
                        // Update transaction as successful
                        new ApproveTransactionCommand(
                            aoTransaction,
                            aoConn.getCreditCardTransactions().get(Integer.parseInt(transaction.getPersistenceUniqueId()))
                        ).execute(aoConn);
                        request.setAttribute("business", business);
                        request.setAttribute("creditCard", creditCard);
                        request.setAttribute("transaction", transaction);
                        request.setAttribute("aoTransaction", aoTransaction);
                        return mapping.findForward("success");
                    default:
                        throw new RuntimeException("Unexpected value for authorization approval result: "+authorizationResult.getApprovalResult());
                }
            default:
                throw new RuntimeException("Unexpected value for authorization communication result: "+authorizationResult.getCommunicationResult());
        }
    }
}
