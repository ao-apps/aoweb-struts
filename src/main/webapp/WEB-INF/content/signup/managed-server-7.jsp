<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2015, 2016, 2019, 2020, 2021, 2022  AO Industries, Inc.
    support@aoindustries.com
    7262 Bull Pen Cir
    Mobile, AL 36695

This file is part of aoweb-struts.

aoweb-struts is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

aoweb-struts is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/taglibs.jspf" %>

<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
  <skin:path>/signup/managed-server-7.do</skin:path>
  <skin:title><ao:message key="managed.title" /></skin:title>
  <skin:navImageAlt><ao:message key="managed.navImageAlt" /></skin:navImageAlt>
  <skin:keywords><ao:message key="managed.keywords" /></skin:keywords>
  <skin:description><ao:message key="managed.description" /></skin:description>
  <%@include file="add-parents.jspf" %>
  <skin:skin>
    <skin:content width="600">
      <skin:contentTitle><ao:message key="managed.title" /></skin:contentTitle>
      <skin:contentHorizontalDivider />
      <skin:contentLine>
        <ao:script>
          function selectStep(step) {
            /**/ if (step == "managed-server")   window.location.href=<ao:url>/signup/managed-server.do</ao:url>;
            else if (step == "managed-server-2") window.location.href=<ao:url>/signup/managed-server-2.do</ao:url>;
            else if (step == "managed-server-3") window.location.href=<ao:url>/signup/managed-server-3.do</ao:url>;
            else if (step == "managed-server-4") window.location.href=<ao:url>/signup/managed-server-4.do</ao:url>;
            else if (step == "managed-server-5") window.location.href=<ao:url>/signup/managed-server-5.do</ao:url>;
            else if (step == "managed-server-6") window.location.href=<ao:url>/signup/managed-server-6.do</ao:url>;
          }
        </ao:script>
        <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="7" />
        <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="managed-server" />
        <%@include file="managed-server-steps.jspf" %>
        <ao:br />
        <form action="<ao:url>/signup/managed-server-7-completed.do</ao:url>" method="post">
          <%@include file="managed-server-confirmation.jspf" %>
        </form>
      </skin:contentLine>
    </skin:content>
  </skin:skin>
</ao:bundle>
