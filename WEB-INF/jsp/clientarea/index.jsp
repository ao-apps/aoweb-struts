<%--
  Copyright 2000-2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/index.do</skin:path>
    <skin:title><bean:message bundle="/clientarea/ApplicationResources" key="index.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/ApplicationResources" key="index.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/ApplicationResources" key="index.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/ApplicationResources" key="index.description"/></skin:description>
    <%@ include file="../add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/ApplicationResources" key="index.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <skin:autoIndex/>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
