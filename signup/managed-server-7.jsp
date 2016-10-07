<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009, 2015 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
		<skin:path>/signup/managed-server-7.do</skin:path>
		<skin:title><fmt:message key="managed.title" /></skin:title>
		<skin:navImageAlt><fmt:message key="managed.navImageAlt" /></skin:navImageAlt>
		<skin:keywords><fmt:message key="managed.keywords" /></skin:keywords>
		<skin:description><fmt:message key="managed.description" /></skin:description>
		<%@include file="add-parents.jsp" %>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle><fmt:message key="managed.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<ao:script>
						function selectStep(step) {
								 if(step=="managed-server")   window.location.href=<ao:url>managed-server.do</ao:url>;
							else if(step=="managed-server-2") window.location.href=<ao:url>managed-server-2.do</ao:url>;
							else if(step=="managed-server-3") window.location.href=<ao:url>managed-server-3.do</ao:url>;
							else if(step=="managed-server-4") window.location.href=<ao:url>managed-server-4.do</ao:url>;
							else if(step=="managed-server-5") window.location.href=<ao:url>managed-server-5.do</ao:url>;
							else if(step=="managed-server-6") window.location.href=<ao:url>managed-server-6.do</ao:url>;
						}
					</ao:script>
					<bean:define toScope="request" type="java.lang.String" id="stepNumber" value="7" />
					<bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="managed-server" />
					<%@include file="managed-server-steps.jsp" %>
					<br />
					<form action="<ao:url>managed-server-7-completed.do</ao:url>" method="post">
						<%@include file="managed-server-confirmation.jsp" %>
					</form>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
