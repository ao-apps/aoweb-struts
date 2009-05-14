<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/logout.do</skin:path>
    <skin:title><bean:message bundle="/ApplicationResources" key="logout.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/ApplicationResources" key="logout.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/ApplicationResources" key="logout.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/ApplicationResources" key="logout.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
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
