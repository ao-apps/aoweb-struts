<%--
  Copyright 2000-2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/signup/dedicated.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="dedicated.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="dedicated.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="dedicated.description"/></skin:description>
    <%@ include file="addParents.jsp" %>
    <%@ include file="addSiblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="1"/>
                <%@ include file="dedicatedSteps.jsp" %>
                <br>
<SCRIPT language='JavaScript1.2'><!--
  function hideAllDetails() {
    <logic:iterate scope="request" name="servers" id="server">
        document.getElementById('popup_<bean:write name="server" property="packageDefinition"/>').style.visibility='hidden';
    </logic:iterate>
  }

  function toggleDetails(index) {
    var elemStyle = document.getElementById('popup_'+index).style;
    if(elemStyle.visibility=='visible') {
      hideAllDetails();
    } else {
      hideAllDetails();
      elemStyle.visibility='visible';
    }
  }

  function showDetails(index) {
    var elemStyle = document.getElementById('popup_'+index).style;
    if(elemStyle.visibility!='visible') {
      hideAllDetails();
      elemStyle.visibility='visible';
    }
  }
// --></SCRIPT>
                <html:form action="/dedicatedCompleted.do">
                    <skin:lightArea>
                        <table border="0" cellspacing="0" cellpadding="2">
                            <TR><TD COLSPAN="6"><B><bean:message bundle="/signup/ApplicationResources" key="dedicated.stepLabel"/></B><BR><HR></TD></TR>
                            <TR><TD COLSPAN="6"><bean:message bundle="/signup/ApplicationResources" key="dedicated.stepHelp"/><BR><BR></TD></TR>
                            <tr>
                                <th nowrap><bean:message bundle="/signup/ApplicationResources" key="dedicated.select.header"/></th>
                                <th nowrap><bean:message bundle="/signup/ApplicationResources" key="dedicated.packageDefinition.header"/></th>
                                <th nowrap><bean:message bundle="/signup/ApplicationResources" key="dedicated.setup.header"/></th>
                                <th nowrap><bean:message bundle="/signup/ApplicationResources" key="dedicated.minimumMonthlyRate.header"/></th>
                                <th nowrap><bean:message bundle="/signup/ApplicationResources" key="dedicated.maximumMonthlyRate.header"/></th>
                                <th nowrap><bean:message bundle="/signup/ApplicationResources" key="dedicated.details.header"/></th>
                            </tr>
                            <% int row = 0; %>
                            <logic:iterate scope="request" name="servers" id="server">
                                <tr class='<%= (row&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                                    <td nowrap><html:radio property="packageDefinition" idName="server" value="packageDefinition"/></td>
                                    <td nowrap><b><bean:write name="server" property="name"/></b></td>
                                    <td nowrap>
                                        <logic:empty name="server" property="setup"><bean:message bundle="/signup/ApplicationResources" key="dedicated.setup.none"/></logic:empty>
                                        <logic:notEmpty name="server" property="setup">$<bean:write name="server" property="setup"/></logic:notEmpty>
                                    </td>
                                    <td nowrap>$<bean:write name="server" property="minimumMonthly"/></td>
                                    <td nowrap>$<bean:write name="server" property="maximumMonthly"/></td>
                                    <td nowrap>
                                        <A
                                            class='<%= ((row++)&1)==0 ? "ao_dark_link" : "ao_light_link" %>'
                                            href='javascript:toggleDetails(<bean:write name="server" property="packageDefinition"/>);'
                                            onmouseover='javascript:showDetails(<bean:write name="server" property="packageDefinition"/>);'
                                        >
                                            <bean:message bundle="/signup/ApplicationResources" key="dedicated.details.link"/>
                                        </A>
                                        <SPAN width='100%' id='popup_<bean:write name="server" property="packageDefinition"/>' style='align:left; white-space:nowrap; position:absolute; visibility: hidden; z-index:1'>
                                            <DIV class='ao_popup'>
                                                <table border="0" cellspacing="0" cellpadding="2" style='font-size:80%;'>
                                                    <tr><th colspan="3" class='ao_light_row' style='font-size:100%;'><bean:write name="server" property="name"/></th></tr>
                                                    <tr>
                                                        <th><bean:message bundle="/signup/ApplicationResources" key="dedicated.resource.header"/></th>
                                                        <th><bean:message bundle="/signup/ApplicationResources" key="dedicated.minimum.header"/></th>
                                                        <th><bean:message bundle="/signup/ApplicationResources" key="dedicated.maximum.header"/></th>
                                                    </tr>
                                                    <% int row2 = 0; %>
                                                    <tr class='<%= ((row2++)&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                                                        <td nowrap><b><bean:message bundle="/signup/ApplicationResources" key="dedicated.cpu.header"/></b></td>
                                                        <td nowrap><bean:write name="server" property="minimumCpu" filter="false"/></td>
                                                        <td nowrap><bean:write name="server" property="maximumCpu" filter="false"/></td>
                                                    </tr>
                                                    <tr class='<%= ((row2++)&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                                                        <td nowrap><b><bean:message bundle="/signup/ApplicationResources" key="dedicated.ram.header"/></b></td>
                                                        <td nowrap><bean:write name="server" property="minimumRam"/></td>
                                                        <td nowrap><bean:write name="server" property="maximumRam"/></td>
                                                    </tr>
                                                    <logic:notEmpty name="server" property="maximumIDE">
                                                        <tr class='<%= ((row2++)&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                                                            <td nowrap><b><bean:message bundle="/signup/ApplicationResources" key="dedicated.ide.header"/></b></td>
                                                            <td nowrap>
                                                                <logic:empty name="server" property="minimumIDE">&nbsp;</logic:empty>
                                                                <logic:notEmpty name="server" property="minimumIDE"><bean:write name="server" property="minimumIDE"/></logic:notEmpty>
                                                            </td>
                                                            <td nowrap><bean:write name="server" property="maximumIDE"/></td>
                                                        </tr>
                                                    </logic:notEmpty>
                                                    <logic:notEmpty name="server" property="maximumSATA">
                                                        <tr class='<%= ((row2++)&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                                                            <td nowrap><b><bean:message bundle="/signup/ApplicationResources" key="dedicated.sata.header"/></b></td>
                                                            <td nowrap>
                                                                <logic:empty name="server" property="minimumSATA">&nbsp;</logic:empty>
                                                                <logic:notEmpty name="server" property="minimumSATA"><bean:write name="server" property="minimumSATA"/></logic:notEmpty>
                                                            </td>
                                                            <td nowrap><bean:write name="server" property="maximumSATA"/></td>
                                                        </tr>
                                                    </logic:notEmpty>
                                                    <logic:notEmpty name="server" property="maximumSCSI">
                                                        <tr class='<%= ((row2++)&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                                                            <td nowrap><b><bean:message bundle="/signup/ApplicationResources" key="dedicated.scsi.header"/></b></td>
                                                            <td nowrap>
                                                                <logic:empty name="server" property="minimumSCSI">&nbsp;</logic:empty>
                                                                <logic:notEmpty name="server" property="minimumSCSI"><bean:write name="server" property="minimumSCSI"/></logic:notEmpty>
                                                            </td>
                                                            <td nowrap><bean:write name="server" property="maximumSCSI"/></td>
                                                        </tr>
                                                    </logic:notEmpty>
                                                </table>
                                            </DIV>
                                        </SPAN>
                                    </td>
                                </tr>
                            </logic:iterate>
                            <tr><td colspan="6" align="center"><br><html:submit styleClass='ao_button'><bean:message bundle="/signup/ApplicationResources" key="dedicated.submit.label"/></html:submit><br><br></td></tr>
                        </table>
                    </skin:lightArea>
                </html:form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
