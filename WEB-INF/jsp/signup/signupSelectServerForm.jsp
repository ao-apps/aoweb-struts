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

<skin:lightArea>
    <SCRIPT language='JavaScript1.2'><!--
      function hideAllDetails() {
        <logic:iterate scope="request" name="servers" id="server">
            document.getElementById('popup_<bean:write name="server" property="minimumConfiguration.packageDefinition"/>').style.visibility='hidden';
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
    <table border="0" cellspacing="0" cellpadding="2">
        <TR><TD COLSPAN="6"><B><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.stepLabel"/></B><BR><HR></TD></TR>
        <TR><TD COLSPAN="6">
            <bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.stepHelp"/><BR>
            <BR>
        </TD></TR>
        <tr>
            <th nowrap><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.select.header"/></th>
            <th nowrap><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.packageDefinition.header"/></th>
            <th nowrap><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.setup.header"/></th>
            <th nowrap><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.minimumMonthlyRate.header"/></th>
            <th nowrap><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.maximumMonthlyRate.header"/></th>
            <th nowrap>&nbsp;</th>
        </tr>
        <% int row = 0; %>
        <logic:iterate scope="request" name="servers" id="server" indexId="serverIndex">
            <bean:define name="server" property="minimumConfiguration.packageDefinition" id="packageDefinition" type="java.lang.Integer"/>
            <tr class='<%= (row&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                <td nowrap><html:radio property="packageDefinition" idName="server" value="minimumConfiguration.packageDefinition"/></td>
                <td nowrap>
                    <span style="position:relative;">
                        <b><bean:write name="server" property="minimumConfiguration.name"/></b>
                        <html:img
                            bundle="/signup/ApplicationResources"
                            altKey="signupSelectServerForm.details.image.alt"
                            srcKey="signupSelectServerForm.details.image.src"
                            border="0"
                            align="absmiddle"
                            style="cursor:pointer; cursor:hand;"
                            onmouseover='<%= "showDetails("+packageDefinition+");" %>'
                            onclick='<%= "toggleDetails("+packageDefinition+");" %>'
                        />
                        <SPAN width='100%' id='popup_<bean:write name="server" property="minimumConfiguration.packageDefinition"/>' style='white-space:nowrap; position:absolute; bottom:30px; visibility: hidden; z-index:1'>
                            <DIV class='ao_popup'>
                                <table border="0" cellspacing="0" cellpadding="2" style='font-size:80%;'>
                                    <tr>
                                        <td colspan="3" class='ao_light_row' style='font-size:100%;'>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <th class='ao_light_row'><bean:write name="server" property="minimumConfiguration.name"/></th>
                                                    <td class='ao_light_row' align="right">
                                                        <html:img
                                                            bundle="/signup/ApplicationResources"
                                                            altKey="signupSelectServerForm.close.image.alt"
                                                            srcKey="signupSelectServerForm.close.image.src"
                                                            border="0"
                                                            align="absmiddle"
                                                            style="cursor:pointer; cursor:hand;"
                                                            onclick="hideAllDetails();"
                                                        />
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.resource.header"/></th>
                                        <th><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.minimum.header"/></th>
                                        <th><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.maximum.header"/></th>
                                    </tr>
                                    <% int row2 = 0; %>
                                    <tr class='<%= ((row2++)&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                                        <td nowrap><b><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.cpu.header"/></b></td>
                                        <td nowrap><bean:write name="server" property="minimumConfiguration.cpu" filter="false"/></td>
                                        <td nowrap><bean:write name="server" property="maximumConfiguration.cpu" filter="false"/></td>
                                    </tr>
                                    <tr class='<%= ((row2++)&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                                        <td nowrap><b><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.ram.header"/></b></td>
                                        <td nowrap><bean:write name="server" property="minimumConfiguration.ram"/></td>
                                        <td nowrap><bean:write name="server" property="maximumConfiguration.ram"/></td>
                                    </tr>
                                    <logic:notEmpty name="server" property="maximumConfiguration.ide">
                                        <tr class='<%= ((row2++)&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                                            <td nowrap><b><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.ide.header"/></b></td>
                                            <td nowrap>
                                                <logic:empty name="server" property="minimumConfiguration.ide">&nbsp;</logic:empty>
                                                <logic:notEmpty name="server" property="minimumConfiguration.ide"><bean:write name="server" property="minimumConfiguration.ide"/></logic:notEmpty>
                                                </td>
                                            <td nowrap><bean:write name="server" property="maximumConfiguration.ide"/></td>
                                        </tr>
                                    </logic:notEmpty>
                                    <logic:notEmpty name="server" property="maximumConfiguration.sata">
                                        <tr class='<%= ((row2++)&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                                            <td nowrap><b><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.sata.header"/></b></td>
                                            <td nowrap>
                                                <logic:empty name="server" property="minimumConfiguration.sata">&nbsp;</logic:empty>
                                                <logic:notEmpty name="server" property="minimumConfiguration.sata"><bean:write name="server" property="minimumConfiguration.sata"/></logic:notEmpty>
                                                </td>
                                            <td nowrap><bean:write name="server" property="maximumConfiguration.sata"/></td>
                                        </tr>
                                    </logic:notEmpty>
                                    <logic:notEmpty name="server" property="maximumConfiguration.scsi">
                                        <tr class='<%= ((row2++)&1)==0 ? "ao_light_row" : "ao_dark_row" %>'>
                                            <td nowrap><b><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.scsi.header"/></b></td>
                                            <td nowrap>
                                                <logic:empty name="server" property="minimumConfiguration.scsi">&nbsp;</logic:empty>
                                                <logic:notEmpty name="server" property="minimumConfiguration.scsi"><bean:write name="server" property="minimumConfiguration.scsi"/></logic:notEmpty>
                                                </td>
                                            <td nowrap><bean:write name="server" property="maximumConfiguration.scsi"/></td>
                                        </tr>
                                    </logic:notEmpty>
                                </table>
                            </DIV>
                        </SPAN>
                    </span>
                </td>
                <td nowrap>
                    <logic:empty name="server" property="minimumConfiguration.setup"><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.setup.none"/></logic:empty>
                    <logic:notEmpty name="server" property="minimumConfiguration.setup">$<bean:write name="server" property="minimumConfiguration.setup"/></logic:notEmpty>
                </td>
                <td nowrap>$<bean:write name="server" property="minimumConfiguration.monthly"/></td>
                <td nowrap>$<bean:write name="server" property="maximumConfiguration.monthly"/></td>
                <logic:equal name="serverIndex" value="0">
                    <bean:size scope="request" name="servers" id="serversSize"/>
                    <td rowspan="<%= serversSize %>" nowrap>
                        <html:errors bundle="/signup/ApplicationResources" property="packageDefinition"/>
                    </td>
                </logic:equal>
            </tr>
        </logic:iterate>
        <tr><td colspan="6" align="center"><br><html:submit styleClass='ao_button'><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.submit.label"/></html:submit><br><br></td></tr>
    </table>
</skin:lightArea>
