/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2019, 2021, 2022  AO Industries, Inc.
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

package com.aoindustries.web.struts.aowebtags;

import com.aoapps.lang.i18n.CurrencyUtil;
import com.aoapps.payments.CreditCard;
import com.aoapps.servlet.filter.FunctionContext;
import java.util.Currency;

/**
 * Tag library function implementations.
 *
 * @author  AO Industries, Inc.
 */
public final class Functions {

  /** Make no instances. */
  private Functions() {
    throw new AssertionError();
  }

  /**
   * @see  CreditCard#getCardNumberDisplay(java.lang.String)
   */
  public static String getCardNumberDisplay(String cardNumber) {
    return CreditCard.getCardNumberDisplay(cardNumber);
  }

  /**
   * Allows <code>0</code> that comes from JSP Expression Language functions.
   *
   * @see  CreditCard#getExpirationDisplay(java.lang.Byte, java.lang.Short)
   */
  public static String getExpirationDisplay(Byte expirationMonth, Short expirationYear) {
    if (expirationMonth != null && expirationMonth == 0) {
      expirationMonth = null;
    }
    if (expirationYear != null && expirationYear == 0) {
      expirationYear = null;
    }
    return CreditCard.getExpirationDisplay(expirationMonth, expirationYear);
  }

  /**
   * @see  CurrencyUtil#getSymbol(java.util.Currency, java.util.Locale)
   */
  public static String getCurrencySymbol(Currency currency) {
    return CurrencyUtil.getSymbol(currency, FunctionContext.getResponse().getLocale());
  }
}
