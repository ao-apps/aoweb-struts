<%-- aoweb-struts --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/accounting/make-payment-select-card.do?accounting=<bean:write scope="request" name="makePaymentStoredCardForm" property="accounting"/></skin:path>
    <logic:equal name="siteSettings" property="noindexAowebStruts" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <skin:lightArea>
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardError.error.header"/>
                    <hr>
                    <bean:define scope="request" name="errorReason" id="errorReason" type="java.lang.String"/>
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardError.error.description" arg0="<%= errorReason %>"/>
                </skin:lightArea><br>
                <%@ include file="make-payment-select-card-shared.jsp" %>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
