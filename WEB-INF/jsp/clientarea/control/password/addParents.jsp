<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<%@ include file="../addParents.jsp" %>
<skin:addParent useEncryption="false" path="/clientarea/control/password/PasswordIndex.ao">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.index.title"/></skin:title>
</skin:addParent>
