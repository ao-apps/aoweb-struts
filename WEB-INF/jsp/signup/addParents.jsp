<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<%@ include file="../addParents.jsp" %>
<skin:addParent useEncryption="false" path="/signup/SignUpIndex.ao">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="index.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="index.navImageAlt"/></skin:navImageAlt>
</skin:addParent>
