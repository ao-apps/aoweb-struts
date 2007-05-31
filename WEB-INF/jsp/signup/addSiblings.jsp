<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<skin:addSibling useEncryption="true" path="/signup/VirtualSignUp.ao">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="virtual.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="virtual.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="virtual.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/signup/dedicated.do">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="dedicated.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="dedicated.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/signup/managed.do">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="managed.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="managed.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="managed.description"/></skin:description>
</skin:addSibling>
