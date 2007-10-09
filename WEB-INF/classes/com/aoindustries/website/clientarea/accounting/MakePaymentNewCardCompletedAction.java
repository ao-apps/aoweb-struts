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
import com.aoindustries.aoserv.client.PaymentType;
import com.aoindustries.aoserv.client.TransactionType;
import com.aoindustries.aoserv.creditcards.AOServConnectorPrincipal;
import com.aoindustries.aoserv.creditcards.BusinessGroup;
import com.aoindustries.aoserv.creditcards.CreditCardFactory;
import com.aoindustries.aoserv.creditcards.CreditCardProcessorFactory;
import com.aoindustries.creditcards.AuthorizationResult;
import com.aoindustries.creditcards.CreditCardProcessor;
import com.aoindustries.creditcards.Transaction;
import com.aoindustries.creditcards.TransactionRequest;
import com.aoindustries.creditcards.TransactionResult;
import com.aoindustries.sql.SQLUtility;
import com.aoindustries.website.HttpsAction;
import com.aoindustries.website.RootAOServConnector;
import com.aoindustries.website.Skin;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 * Payment from stored credit card.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentNewCardCompletedAction extends MakePaymentNewCardAction {

    final public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        MakePaymentNewCardForm makePaymentNewCardForm=(MakePaymentNewCardForm)form;

        // Init request values
        initRequestAttributes(request, getServlet().getServletContext());

        String accounting = makePaymentNewCardForm.getAccounting();
        Business business = accounting==null ? null : aoConn.businesses.get(accounting);
        if(business==null) {
            // Redirect back to make-payment if business not found
            return mapping.findForward("make-payment");
        }
        request.setAttribute("business", business);

        // Validation
        ActionMessages errors = makePaymentNewCardForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);

            return mapping.findForward("input");
        }

        // Convert to pennies
        int pennies = SQLUtility.getPennies(makePaymentNewCardForm.getPaymentAmount());
        BigDecimal paymentAmount = new BigDecimal(makePaymentNewCardForm.getPaymentAmount());

        // Encapsulate the values into a new credit card
        String cardNumber = makePaymentNewCardForm.getCardNumber();
        com.aoindustries.creditcards.CreditCard newCreditCard = new com.aoindustries.creditcards.CreditCard(
            locale,
            null,
            null,
            null,
            null,
            null,
            cardNumber,
            null,
            Byte.parseByte(makePaymentNewCardForm.getExpirationMonth()),
            Short.parseShort(makePaymentNewCardForm.getExpirationYear()),
            null,
            makePaymentNewCardForm.getFirstName(),
            makePaymentNewCardForm.getLastName(),
            makePaymentNewCardForm.getCompanyName(),
            makePaymentNewCardForm.getEmail(),
            makePaymentNewCardForm.getPhone(),
            makePaymentNewCardForm.getFax(),
            null,
            makePaymentNewCardForm.getCustomerTaxId(),
            makePaymentNewCardForm.getStreetAddress1(),
            makePaymentNewCardForm.getStreetAddress2(),
            makePaymentNewCardForm.getCity(),
            makePaymentNewCardForm.getState(),
            makePaymentNewCardForm.getPostalCode(),
            makePaymentNewCardForm.getCountryCode(),
            makePaymentNewCardForm.getDescription()
        );

        // Perform the transaction
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(getServlet().getServletContext());
        
        // 1) Pick a processor
        CreditCardProcessor rootProcessor = CreditCardProcessorFactory.getCreditCardProcessor(rootConn);
        if(rootProcessor==null) throw new SQLException("Unable to find enabled CreditCardProcessor for root connector");
        com.aoindustries.aoserv.client.CreditCardProcessor rootAoProcessor = rootConn.creditCardProcessors.get(rootProcessor.getProviderId());
        if(rootAoProcessor==null) throw new SQLException("Unable to find CreditCardProcessor: "+rootProcessor.getProviderId());

        // 2) Add the transaction as pending on this processor
        Business rootBusiness = rootConn.businesses.get(accounting);
        if(rootBusiness==null) throw new SQLException("Unable to find Business: "+accounting);
        TransactionType paymentTransactionType = rootConn.transactionTypes.get(TransactionType.PAYMENT);
        if(paymentTransactionType==null) throw new SQLException("Unable to find TransactionType: "+TransactionType.PAYMENT);
        MessageResources applicationResources = (MessageResources)request.getAttribute("/clientarea/accounting/ApplicationResources");
        String paymentTypeName;
        if(cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            paymentTypeName = PaymentType.AMEX;
        } else if(cardNumber.startsWith("60")) {
            paymentTypeName = PaymentType.DISCOVER;
        } else if(cardNumber.startsWith("51") || cardNumber.startsWith("52") || cardNumber.startsWith("53") || cardNumber.startsWith("54") || cardNumber.startsWith("55")) {
            paymentTypeName = PaymentType.MASTERCARD;
        } else if(cardNumber.startsWith("4")) {
            paymentTypeName = PaymentType.VISA;
        } else {
            paymentTypeName = null;
        }
        PaymentType paymentType;
        if(paymentTypeName==null) paymentType = null;
        else {
            paymentType = rootConn.paymentTypes.get(paymentTypeName);
            if(paymentType==null) throw new SQLException("Unable to find PaymentType: "+paymentTypeName);
        }

        int transID = rootBusiness.addTransaction(
            rootBusiness,
            aoConn.getThisBusinessAdministrator(),
            paymentTransactionType,
            applicationResources.getMessage(locale, "makePaymentStoredCardCompleted.transaction.description"),
            1000,
            -pennies,
            paymentType,
            com.aoindustries.creditcards.CreditCard.maskCreditCardNumber(cardNumber),
            rootAoProcessor,
            com.aoindustries.aoserv.client.Transaction.WAITING_CONFIRMATION
        );
        com.aoindustries.aoserv.client.Transaction aoTransaction = rootConn.transactions.get(transID);
        if(aoTransaction==null) throw new SQLException("Unable to find Transaction: "+transID);

        // 3) Process
        AOServConnectorPrincipal principal = new AOServConnectorPrincipal(rootConn, aoConn.getThisBusinessAdministrator().getUsername().getUsername());
        BusinessGroup businessGroup = new BusinessGroup(rootBusiness, accounting);
        Transaction transaction = rootProcessor.sale(
            principal,
            businessGroup,
            new TransactionRequest(
                locale,
                false,
                request.getRemoteAddr(),
                120,
                Integer.toString(transID),
                TransactionRequest.CurrencyCode.USD,
                paymentAmount,
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
                applicationResources.getMessage(Locale.US, "makePaymentStoredCardCompleted.transaction.description")
            ),
            newCreditCard,
            locale
        );

        // 4) Decline/approve based on results
        AuthorizationResult authorizationResult = transaction.getAuthorizationResult();
        switch(authorizationResult.getCommunicationResult()) {
            case LOCAL_ERROR :
            case IO_ERROR :
            case GATEWAY_ERROR :
            {
                // Update transaction as failed
                aoTransaction.declined(Integer.parseInt(transaction.getPersistenceUniqueId()));

                TransactionResult.ErrorCode errorCode = authorizationResult.getErrorCode();
                ActionMessages mappedErrors = makePaymentNewCardForm.mapTransactionError(errorCode, locale);
                if(mappedErrors==null || mappedErrors.isEmpty()) {
                    // Not mapped, store to request attributes as generate error (to be displayed separate from specific fields)
                    request.setAttribute("errorReason", errorCode.toString(locale));
                } else {
                    // Store for display with specific fields
                    saveErrors(request, mappedErrors);
                }
                return mapping.findForward("error");
            }
            case SUCCESS :
                // Check approval result
                switch(authorizationResult.getApprovalResult()) {
                    case HOLD :
                    {
                        // Update transaction as held
                        aoTransaction.held(Integer.parseInt(transaction.getPersistenceUniqueId()));

                        // Store to request attributes
                        request.setAttribute("transaction", transaction);
                        request.setAttribute("aoTransaction", aoTransaction);
                        request.setAttribute("reviewReason", authorizationResult.getReviewReason().toString(locale));

                        String storeCard = makePaymentNewCardForm.getStoreCard();
                        if(
                            "store".equals(storeCard)
                            || "automatic".equals(storeCard)
                        ) {
                            // Store card
                            boolean storeSuccess;
                            try {
                                storeCard(rootProcessor, principal, businessGroup, newCreditCard, locale);
                                request.setAttribute("cardStored", "true");
                                storeSuccess = true;
                            } catch(IOException err) {
                                getServlet().log("Unable to store card", err);
                                request.setAttribute("storeError", err);
                                storeSuccess = false;
                            } catch(SQLException err) {
                                getServlet().log("Unable to store card", err);
                                request.setAttribute("storeError", err);
                                storeSuccess = false;
                            } catch(RuntimeException err) {
                                getServlet().log("Unable to store card", err);
                                request.setAttribute("storeError", err);
                                storeSuccess = false;
                            }
                            if(storeSuccess && "automatic".equals(storeCard)) {
                                // Set automatic
                                try {
                                    setAutomatic(rootConn, newCreditCard, business);
                                    request.setAttribute("cardSetAutomatic", "true");
                                } catch(SQLException err) {
                                    getServlet().log("Unable to set automatic", err);
                                    request.setAttribute("setAutomaticError", err);
                                } catch(RuntimeException err) {
                                    getServlet().log("Unable to set automatic", err);
                                    request.setAttribute("setAutomaticError", err);
                                }
                            }
                        }
                        return mapping.findForward("hold");
                    }
                    case DECLINED :
                    {
                        // Update transaction as declined
                        aoTransaction.declined(Integer.parseInt(transaction.getPersistenceUniqueId()));

                        // Store to request attributes
                        request.setAttribute("declineReason", authorizationResult.getDeclineReason().toString(locale));
                        return mapping.findForward("declined");
                    }
                    case APPROVED :
                    {
                        // Update transaction as successful
                        aoTransaction.approved(Integer.parseInt(transaction.getPersistenceUniqueId()));

                        // Store to request attributes
                        request.setAttribute("transaction", transaction);
                        request.setAttribute("aoTransaction", aoTransaction);

                        String storeCard = makePaymentNewCardForm.getStoreCard();
                        if(
                            "store".equals(storeCard)
                            || "automatic".equals(storeCard)
                        ) {
                            // Store card
                            boolean storeSuccess;
                            try {
                                storeCard(rootProcessor, principal, businessGroup, newCreditCard, locale);
                                request.setAttribute("cardStored", "true");
                                storeSuccess = true;
                            } catch(IOException err) {
                                getServlet().log("Unable to store card", err);
                                request.setAttribute("storeError", err);
                                storeSuccess = false;
                            } catch(SQLException err) {
                                getServlet().log("Unable to store card", err);
                                request.setAttribute("storeError", err);
                                storeSuccess = false;
                            } catch(RuntimeException err) {
                                getServlet().log("Unable to store card", err);
                                request.setAttribute("storeError", err);
                                storeSuccess = false;
                            }
                            if(storeSuccess && "automatic".equals(storeCard)) {
                                // Set automatic
                                try {
                                    setAutomatic(rootConn, newCreditCard, business);
                                    request.setAttribute("cardSetAutomatic", "true");
                                } catch(SQLException err) {
                                    getServlet().log("Unable to set automatic", err);
                                    request.setAttribute("setAutomaticError", err);
                                } catch(RuntimeException err) {
                                    getServlet().log("Unable to set automatic", err);
                                    request.setAttribute("setAutomaticError", err);
                                }
                            }
                        }
                        return mapping.findForward("success");
                    }
                    default:
                        throw new RuntimeException("Unexpected value for authorization approval result: "+authorizationResult.getApprovalResult());
                }
            default:
                throw new RuntimeException("Unexpected value for authorization communication result: "+authorizationResult.getCommunicationResult());
        }
    }
    
    private void storeCard(CreditCardProcessor rootProcessor, AOServConnectorPrincipal principal, BusinessGroup businessGroup, com.aoindustries.creditcards.CreditCard newCreditCard, Locale locale) throws SQLException, IOException {
        rootProcessor.storeCreditCard(
            principal,
            businessGroup,
            newCreditCard,
            locale
        );
    }
    
    /**
     * @param  rootConn  Since rootConn is used to store the card, it must also be used to get the new instance.
     *                   Otherwise there is a race condition between the non-root AOServConnector getting the invalidation signal
     *                   and this method being called.
     */
    private void setAutomatic(AOServConnector rootConn, com.aoindustries.creditcards.CreditCard newCreditCard, Business business) throws SQLException {
        String persistenceUniqueId = newCreditCard.getPersistenceUniqueId();
        CreditCard creditCard = rootConn.creditCards.get(Integer.parseInt(persistenceUniqueId));
        if(creditCard==null) throw new SQLException("Unable to find CreditCard: "+persistenceUniqueId);
        if(!creditCard.getBusiness().equals(business)) throw new SQLException("Requested business and CreditCard business do not match: "+creditCard.getBusiness().getAccounting()+"!="+business.getAccounting());
        business.setUseMonthlyCreditCard(creditCard);
    }
}
