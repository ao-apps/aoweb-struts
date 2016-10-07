<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <%@include file="add-parents.jsp" %>
    <%@include file="aoserv.meta.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
                <skin:contentTitle><fmt:message key="aoserv.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <ao:script>
                        function selectStep(step) {
                            var form = document.forms['aoservSignupSelectPackageForm'];
                            form.selectedStep.value=step;
                            form.submit();
                        }
                    </ao:script>
                    <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="1" />
                    <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="aoserv" />
                    <%@include file="minimal-steps.jsp" %>
                    <br />
                    <html:form action="/aoserv-completed.do">
                        <div>
                            <input type="hidden" name="selectedStep" value="" />
                            <%@include file="signup-select-package-form.jsp" %>
                        </div>
                    </html:form>
                </skin:contentLine>
            </fmt:bundle>
        </skin:content>
    </skin:skin>
</html:html>
