<%-- aoweb-struts --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/clientarea/control/business/add-siblings.override.jsp">
    <jsp:include page="/WEB-INF/jsp/clientarea/control/business/add-siblings.override.jsp"/>
</aoweb:exists>
<aoweb:notExists path="/WEB-INF/jsp/clientarea/control/business/add-siblings.override.jsp">
    <skin:addSibling useEncryption="true" path="/clientarea/control/business/cancel.do">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.navImageAlt"/></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.description"/></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/clientarea/control/business/Disable.ao">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="business.disable.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="business.disable.navImageAlt"/></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="business.disable.description"/></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/clientarea/control/business/Profiles.ao">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="business.profiles.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="business.profiles.navImageAlt"/></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="business.profiles.description"/></skin:description>
    </skin:addSibling>
</aoweb:notExists>
