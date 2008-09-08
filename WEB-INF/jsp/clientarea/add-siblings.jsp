<%--
  Copyright 2007-2008 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<skin:addSibling useEncryption="false" path="/clientarea/control/index.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="index.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="index.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="index.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="false" path="/clientarea/accounting/index.do">
    <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="index.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="index.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="index.description"/></skin:description>
</skin:addSibling>
