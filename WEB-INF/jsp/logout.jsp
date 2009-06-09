<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/logout.do</skin:path>
    <skin:meta name="ROBOTS">NOINDEX</skin:meta>
    <skin:title><bean:message bundle="/ApplicationResources" key="logout.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/ApplicationResources" key="logout.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/ApplicationResources" key="logout.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/ApplicationResources" key="logout.description"/></skin:description>
    <aoweb:exists path="/WEB-INF/jsp/add-parents.jsp">
        <jsp:include page="/WEB-INF/jsp/add-parents.jsp"/>
    </aoweb:exists>
    <aoweb:exists path="/WEB-INF/jsp/add-siblings.jsp">
        <jsp:include page="/WEB-INF/jsp/add-siblings.jsp"/>
    </aoweb:exists>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/ApplicationResources" key="logout.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <bean:message bundle="/ApplicationResources" key="logout.text"/>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
