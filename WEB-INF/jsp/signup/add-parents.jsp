<%--
  Copyright 2007-2008 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<%@ include file="../add-parents.jsp" %>
<skin:addParent useEncryption="false" path="/signup/SignUpIndex.ao">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="index.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="index.navImageAlt"/></skin:navImageAlt>
</skin:addParent>
