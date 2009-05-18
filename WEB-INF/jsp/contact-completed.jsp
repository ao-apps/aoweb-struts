<%-- aoweb-struts --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/contact.do</skin:path>
    <skin:title><bean:message bundle="/ApplicationResources" key="contact.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/ApplicationResources" key="contact.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/ApplicationResources" key="contact.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/ApplicationResources" key="contact.description"/></skin:description>
    <aoweb:exists path="/WEB-INF/jsp/add-parents.jsp">
        <jsp:include page="/WEB-INF/jsp/add-parents.jsp"/>
    </aoweb:exists>
    <aoweb:exists path="/WEB-INF/jsp/add-siblings.jsp">
        <jsp:include page="/WEB-INF/jsp/add-siblings.jsp"/>
    </aoweb:exists>
    <skin:skin onLoad="document.forms['contactForm'].from.select(); document.forms['contactForm'].form.focus();">
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/ApplicationResources" key="contact.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <skin:lightArea>
                    <bean:message bundle="/ApplicationResources" key="contact-completed.messageReceived"/>
                </skin:lightArea>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
