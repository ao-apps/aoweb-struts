<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009, 2015, 2016 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<%@include file="add-parents.jsp" %>
	<fmt:bundle basename="com.aoindustries.website.clientarea.ticket.ApplicationResources">
		<skin:path>/clientarea/ticket/create.do</skin:path>
		<skin:title><fmt:message key="create.title" /></skin:title>
		<skin:navImageAlt><fmt:message key="create.navImageAlt" /></skin:navImageAlt>
		<skin:keywords><fmt:message key="create.keywords" /></skin:keywords>
		<skin:description><fmt:message key="create.description" /></skin:description>
		<skin:skin>
			<skin:content>
				<skin:contentTitle><fmt:message key="create.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<logic:present scope="request" name="permissionDenied">
						<%@include file="../../_permission-denied.jsp" %>
					</logic:present>
					<logic:notPresent scope="request" name="permissionDenied">
						<skin:lightArea>
							<fmt:message key="create-completed.message" />
							<html:link action="/edit" paramId="pkey" paramScope="request" paramName="pkey"><ao:write scope="request" name="pkey" /></html:link>
						</skin:lightArea>
					</logic:notPresent>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
