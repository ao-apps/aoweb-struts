<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<skin:addSibling useEncryption="true" path="/clientarea/control/password/passwordEvaluator.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.navImageAlt"/></skin:navImageAlt>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/passwordGenerator.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.navImageAlt"/></skin:navImageAlt>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/businessAdministratorPasswordSetter.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.navImageAlt"/></skin:navImageAlt>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/linuxAccountPasswordSetter.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.navImageAlt"/></skin:navImageAlt>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/mySQLPasswordSetter.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.mySQLPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.mySQLPasswordSetter.navImageAlt"/></skin:navImageAlt>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/postgreSQLPasswordSetter.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.postgreSQLPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.postgreSQLPasswordSetter.navImageAlt"/></skin:navImageAlt>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/globalPasswordSetter.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.globalPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.globalPasswordSetter.navImageAlt"/></skin:navImageAlt>
</skin:addSibling>
