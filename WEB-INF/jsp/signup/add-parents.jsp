<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<%@ include file="../add-parents.jsp" %>
<skin:addParent useEncryption="false" path="/signup/SignUpIndex.ao">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="index.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="index.navImageAlt"/></skin:navImageAlt>
</skin:addParent>
