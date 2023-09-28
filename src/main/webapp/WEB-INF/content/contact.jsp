<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2000-2009, 2015, 2016, 2019, 2020, 2021, 2022, 2023  AO Industries, Inc.
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
<%@ page import="com.aoindustries.aoserv.client.AoservConnector" %>
<%@ page import="com.aoapps.web.resources.registry.Group" %>
<%@ page import="com.aoapps.web.resources.registry.Script" %>
<%@ page import="com.aoapps.web.resources.servlet.RegistryEE" %>
<%@ page import="com.aoindustries.web.struts.AuthenticatedAction" %>
<%@ page import="com.aoindustries.web.struts.ContactCompletedAction" %>
<%@ page import="com.aoindustries.web.struts.ReCaptcha" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@include file="/WEB-INF/taglibs.jspf" %>

<%@include file="add-parents.jspf" %>
<%@include file="contact.meta.jspf" %>
<%!
  private static final Group.Name RESOURCE_GROUP = new Group.Name("/contact.jsp");
%>
<%
  // Check if logged-in
  AoservConnector authenticatedAoConn = AuthenticatedAction.getAuthenticatedAoConn(request, response);
  pageContext.setAttribute("authenticatedAoConn", authenticatedAoConn);

  if (authenticatedAoConn == null) {
    // TODO: <wr:script src="https://www.google.com/recaptcha/enterprise.js" async="true" defer="true" />
    Script enterpriseJs = Script.builder()
        .uri("https://www.google.com/recaptcha/enterprise.js?hl="
            + URLEncoder.encode(response.getLocale().toLanguageTag(), StandardCharsets.UTF_8))
        .async(true)
        .defer(true)
        .build();
    RegistryEE.Page.get(request)
        .activate(RESOURCE_GROUP)
        .getGroup(RESOURCE_GROUP)
            .scripts
            .add(enterpriseJs);
    // reCAPTCHA settings
    pageContext.setAttribute("recaptchaSitekey", ReCaptcha.getSitekey(pageContext.getServletContext()));
    pageContext.setAttribute("recaptchaAction", ContactCompletedAction.RECAPTCHA_ACTION);
  }
%>
<%-- reCAPTCHA does not work in xml mode, forcing sgml --%>
<skin:skin serialization="sgml" formtype="struts1" onload="document.forms['contactForm'].from.select(); document.forms['contactForm'].from.focus();">
  <skin:content colspans="3" width="600">
    <ao:bundle basename="com.aoindustries.web.struts.i18n.ApplicationResources">
      <skin:contentTitle><ao:message key="contact.title" /></skin:contentTitle>
      <skin:contentHorizontalDivider colspansAndDirections="1,down,1" />
      <skin:contentLine>
        <ao:message key="contact.text.mainDescription" />
        <skin:contentVerticalDivider />
        <skin:lightArea>
          <table class="ao-no-border">
            <tbody>
              <tr>
                <th style='white-space:nowrap' colspan='2'><ao:message key="contact.header.dayPhone" /></th>
              </tr>
              <logic:notEmpty name="siteSettings" property="brand.supportTollFree">
                <tr>
                  <td style="white-space:nowrap"><ao:message key="contact.label.tollfree" /></td>
                  <td style="white-space:nowrap;text-align:right"><code><ao:write name="siteSettings" property="brand.supportTollFree" /></code></td>
                </tr>
              </logic:notEmpty>
              <logic:notEmpty name="siteSettings" property="brand.supportDayPhone">
                <tr>
                  <td style="white-space:nowrap"><ao:message key="contact.label.direct" /></td>
                  <td style="white-space:nowrap;text-align:right"><code><ao:write name="siteSettings" property="brand.supportDayPhone" /></code></td>
                </tr>
              </logic:notEmpty>
              <tr>
                <th style='white-space:nowrap' colspan='2'><ao:message key="contact.header.emergencyPhone" /></th>
              </tr>
              <logic:notEmpty name="siteSettings" property="brand.supportEmergencyPhone1">
                <tr>
                  <td style="white-space:nowrap"><ao:message key="contact.label.primary" /></td>
                  <td style="white-space:nowrap;text-align:right"><code><ao:write name="siteSettings" property="brand.supportEmergencyPhone1" /></code></td>
                </tr>
              </logic:notEmpty>
              <logic:notEmpty name="siteSettings" property="brand.supportEmergencyPhone2">
                <tr>
                  <td style="white-space:nowrap"><ao:message key="contact.label.secondary" /></td>
                  <td style="white-space:nowrap;text-align:right"><code><ao:write name="siteSettings" property="brand.supportEmergencyPhone2" /></code></td>
                </tr>
              </logic:notEmpty>
              <tr>
                <th style='white-space:nowrap' colspan='2'><ao:message key="contact.header.email" /></th>
              </tr>
              <tr>
                <td style='white-space:nowrap' colspan='2'>
                  <a class="aoDarkLink" href="mailto:<ao:write name="siteSettings" property="brand.supportEmailAddress" />">
                    <code><ao:write name="siteSettings" property="brand.supportEmailAddress" /></code>
                  </a>
                </td>
              </tr>
              <logic:notEmpty name="siteSettings" property="brand.supportFax">
                <tr>
                  <th style='white-space:nowrap' colspan='2'><ao:message key="contact.header.fax" /></th>
                </tr>
                <tr>
                  <td style='white-space:nowrap' colspan='2'><code><ao:write name="siteSettings" property="brand.supportFax" /></code></td>
                </tr>
              </logic:notEmpty>
              <tr>
                <th style='white-space:nowrap' colspan='2'><ao:message key="contact.header.mailingAddress" /></th>
              </tr>
              <logic:notEmpty name="siteSettings" property="brand.supportMailingAddress1">
                <tr>
                  <td style='white-space:nowrap' colspan='2'><code><ao:write name="siteSettings" property="brand.supportMailingAddress1" /></code></td>
                </tr>
              </logic:notEmpty>
              <logic:notEmpty name="siteSettings" property="brand.supportMailingAddress2">
                <tr>
                  <td style='white-space:nowrap' colspan='2'><code><ao:write name="siteSettings" property="brand.supportMailingAddress2" /></code></td>
                </tr>
              </logic:notEmpty>
              <logic:notEmpty name="siteSettings" property="brand.supportMailingAddress3">
                <tr>
                  <td style='white-space:nowrap' colspan='2'><code><ao:write name="siteSettings" property="brand.supportMailingAddress3" /></code></td>
                </tr>
              </logic:notEmpty>
              <logic:notEmpty name="siteSettings" property="brand.supportMailingAddress4">
                <tr>
                  <td style='white-space:nowrap' colspan='2'><code><ao:write name="siteSettings" property="brand.supportMailingAddress4" /></code></td>
                </tr>
              </logic:notEmpty>
            </tbody>
          </table>
        </skin:lightArea>
      </skin:contentLine>
      <skin:contentHorizontalDivider colspansAndDirections="1,up,1" />
      <skin:contentLine colspan="3">
        <ao:message key="contact.text.formWelcome" /><ao:br />
        <ao:br />
        <skin:lightArea>
          <html:javascript staticJavascript='false' bundle="/ApplicationResources" formName="contactForm" />
          <html:form action="/contact-completed" onsubmit="return validateContactForm(this);">
            <table class="ao-packed">
              <tbody>
                <%-- TODO: Default to authenticatedAoConn email address when logged-in (not considering "su") --%>
                <tr>
                  <td style="white-space:nowrap"><ao:message key="contact.field.from.prompt" /></td>
                  <td><html:text size="30" property="from" /></td>
                  <td><html:errors bundle="/ApplicationResources" property="from" /></td>
                </tr>
                <tr>
                  <td style="white-space:nowrap"><ao:message key="contact.field.subject.prompt" /></td>
                  <td><html:text size="45" property="subject" /></td>
                  <td><html:errors bundle="/ApplicationResources" property="subject" /></td>
                </tr>
                <tr><td colspan='2'>&#160;</td></tr>
                <tr><td colspan='2'><ao:message key="contact.field.message.prompt" /></td></tr>
                <tr><td colspan='2'><html:textarea property="message" cols="80" rows="12" /></td></tr>
              </tbody>
              <tfoot>
                <tr>
                  <td colspan="2" style="text-align:center">
                    <c:if test="${pageScope.authenticatedAoConn == null}">
                      <ao:script>
                        function enableSubmitButton() {
                          document.getElementById("submitButton").disabled = false;
                        }
                        function disableSubmitButton() {
                          document.getElementById("submitButton").disabled = true;
                        }
                      </ao:script>
                      <div
                        class="g-recaptcha"
                        style="display:inline-block; margin-top:0.5em; margin-bottom:0.5em"
                        data-sitekey="${fn:escapeXml(recaptchaSitekey)}"
                        data-action="${fn:escapeXml(recaptchaAction)}"
                        data-callback="enableSubmitButton"
                        data-expired-callback="disableSubmitButton"
                      ></div>
                    </c:if>
                    <div>
                      <ao:input
                        id="submitButton"
                        style="margin-top:0.5em; margin-bottom:0.5em"
                        type="submit"
                        value="${ao:message('contact.field.submit.label')}"
                        disabled="${pageScope.authenticatedAoConn == null}"
                      />
                    </div>
                  </td>
                </tr>
              </tfoot>
            </table>
          </html:form>
        </skin:lightArea>
      </skin:contentLine>
    </ao:bundle>
  </skin:content>
</skin:skin>
