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
<skin:addSibling useEncryption="true" path="/signup/virtual-dedicated-server.do">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/signup/virtual-managed-server.do">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/signup/dedicated-server.do">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="dedicated.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="dedicated.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/signup/managed-server.do">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="managed.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="managed.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="managed.description"/></skin:description>
</skin:addSibling>
