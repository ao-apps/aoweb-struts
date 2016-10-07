<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2009, 2015 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
		<skin:path>/signup/aoserv-5.do</skin:path>
		<skin:title><fmt:message key="aoserv.title" /></skin:title>
		<skin:navImageAlt><fmt:message key="aoserv.navImageAlt" /></skin:navImageAlt>
		<skin:keywords><fmt:message key="aoserv.keywords" /></skin:keywords>
		<skin:description><fmt:message key="aoserv.description" /></skin:description>
		<%@include file="add-parents.jsp" %>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle><fmt:message key="aoserv.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<ao:script>
						function selectStep(step) {
								 if(step=="aoserv")   window.location.href=<ao:url>aoserv.do</ao:url>;
							else if(step=="aoserv-2") window.location.href=<ao:url>aoserv-2.do</ao:url>;
							else if(step=="aoserv-3") window.location.href=<ao:url>aoserv-3.do</ao:url>;
							else if(step=="aoserv-4") window.location.href=<ao:url>aoserv-4.do</ao:url>;
						}
					</ao:script>
					<bean:define toScope="request" type="java.lang.String" id="stepNumber" value="5" />
					<bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="aoserv" />
					<%@include file="minimal-steps.jsp" %>
					<br />
					<form action="<ao:url>aoserv-5-completed.do</ao:url>" method="post">
						<%@include file="minimal-confirmation.jsp" %>
					</form>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
