<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.packageDefinition.prompt"/></TD>
    <TD><bean:write scope="request" name="packageDefinition" property="display"/></TD>
</TR>
