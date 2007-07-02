<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<skin:addSibling useEncryption="true" path="/clientarea/control/password/password-evaluator.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/password-generator.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/business-administrator-password-setter.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/interbase-password-setter.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.interBasePasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.interBasePasswordSetter.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.interBasePasswordSetter.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/linux-account-password-setter.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/mysql-password-setter.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.mySQLPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.mySQLPasswordSetter.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.mySQLPasswordSetter.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/postgresql-password-setter.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.postgreSQLPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.postgreSQLPasswordSetter.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.postgreSQLPasswordSetter.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/password/global-password-setter.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.globalPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.globalPasswordSetter.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.globalPasswordSetter.description"/></skin:description>
</skin:addSibling>
