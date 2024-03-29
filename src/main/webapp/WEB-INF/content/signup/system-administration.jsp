<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2009, 2016, 2019, 2020, 2021, 2022  AO Industries, Inc.
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

<%@include file="add-parents.jspf" %>
<%@include file="system-administration.meta.jspf" %>
<skin:skin formtype="struts1">
  <skin:content width="600">
    <ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
      <skin:contentTitle><ao:message key="system-administration.title" /></skin:contentTitle>
      <skin:contentHorizontalDivider />
      <skin:contentLine>
        <ao:script>
          function selectStep(step) {
            var form = document.forms['systemAdministrationSignupSelectPackageForm'];
            form.selectedStep.value=step;
            form.submit();
          }
        </ao:script>
        <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="1" />
        <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="system-administration" />
        <%@include file="minimal-steps.jspf" %>
        <ao:br />
        <html:form action="/system-administration-completed.do">
          <div>
            <ao:input type="hidden" name="selectedStep" />
            <%@include file="signup-select-package-form.jspf" %>
          </div>
        </html:form>
      </skin:contentLine>
    </ao:bundle>
  </skin:content>
</skin:skin>
