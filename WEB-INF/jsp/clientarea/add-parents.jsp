<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<%@ include file="../add-parents.jsp" %>
<skin:addParent useEncryption="false" path="/clientarea/index.do">
    <skin:title><bean:message bundle="/clientarea/ApplicationResources" key="index.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/ApplicationResources" key="index.navImageAlt"/></skin:navImageAlt>
</skin:addParent>
