<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/clientarea/control/password/add-siblings.override.jsp">
    <jsp:include page="/WEB-INF/jsp/clientarea/control/password/add-siblings.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/WEB-INF/jsp/clientarea/control/password/add-siblings.override.jsp">
    <skin:addSibling useEncryption="true" path="/clientarea/control/password/password-evaluator.do">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.title" /></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.navImageAlt" /></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.description" /></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/clientarea/control/password/password-generator.do">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.title" /></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.navImageAlt" /></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.description" /></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/clientarea/control/password/business-administrator-password-setter.do">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.title" /></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.navImageAlt" /></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.description" /></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/clientarea/control/password/linux-account-password-setter.do">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.title" /></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.navImageAlt" /></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.description" /></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/clientarea/control/password/mysql-password-setter.do">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.mySQLPasswordSetter.title" /></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.mySQLPasswordSetter.navImageAlt" /></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.mySQLPasswordSetter.description" /></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/clientarea/control/password/postgresql-password-setter.do">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.postgreSQLPasswordSetter.title" /></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.postgreSQLPasswordSetter.navImageAlt" /></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.postgreSQLPasswordSetter.description" /></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/clientarea/control/password/global-password-setter.do">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.globalPasswordSetter.title" /></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.globalPasswordSetter.navImageAlt" /></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.globalPasswordSetter.description" /></skin:description>
    </skin:addSibling>
</aoweb:notExists>
