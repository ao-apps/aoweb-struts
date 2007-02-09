<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/logout.do</skin:path>
    <skin:title><bean:message bundle="/ApplicationResources" key="logout.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/ApplicationResources" key="logout.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/ApplicationResources" key="logout.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/ApplicationResources" key="logout.description"/></skin:description>
    <%@ include file="addParents.jsp" %>
    <%@ include file="addSiblings.jsp" %>
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
