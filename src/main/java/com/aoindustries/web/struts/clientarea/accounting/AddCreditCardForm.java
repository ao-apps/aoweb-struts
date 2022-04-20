/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2021, 2022  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.aoindustries.web.struts.clientarea.accounting;

import com.aoapps.lang.LocalizedIllegalArgumentException;
import com.aoapps.payments.CreditCard;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author  AO Industries, Inc.
 */
public class AddCreditCardForm extends CreditCardForm implements Serializable {

  private static final long serialVersionUID = 2L;

  @Override
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }
    // cardNumber
    String cardNumber = getCardNumber();
    if (GenericValidator.isBlankOrNull(cardNumber)) {
      errors.add("cardNumber", new ActionMessage("addCreditCardForm.cardNumber.required"));
    } else if (!GenericValidator.isCreditCard(CreditCard.numbersOnly(cardNumber))) {
      errors.add("cardNumber", new ActionMessage("addCreditCardForm.cardNumber.invalid"));
    }
    // expirationMonth and expirationYear
    String expirationMonth = getExpirationMonth();
    String expirationYear = getExpirationYear();
    if (
      GenericValidator.isBlankOrNull(expirationMonth)
      || GenericValidator.isBlankOrNull(expirationYear)
    ) {
      errors.add("expirationDate", new ActionMessage("addCreditCardForm.expirationDate.required"));
    }
    // cardCode
    String cardCode = getCardCode();
    if (GenericValidator.isBlankOrNull(cardCode)) {
      errors.add("cardCode", new ActionMessage("addCreditCardForm.cardCode.required"));
    } else {
      try {
        CreditCard.validateCardCode(cardCode);
      } catch (LocalizedIllegalArgumentException e) {
        errors.add("cardCode", new ActionMessage(e.getLocalizedMessage(), false));
      }
    }
    return errors;
  }
}
