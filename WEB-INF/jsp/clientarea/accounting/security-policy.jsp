<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<br>
<skin:lightArea width="500">
    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="securityPolicy.securityNotice.title"/>
    <hr>
    <%-- Should make a per-provider notice based on root-level business --%>
    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="securityPolicy.securityNotice.body"/>
</skin:lightArea>
