<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.packageDefinition.prompt" /></td>
    <td><ao:write scope="request" name="packageDefinition" property="display" /></td>
</tr>
