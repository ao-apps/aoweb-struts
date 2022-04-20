<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2021, 2022  AO Industries, Inc.
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
--%><%--

Calls Struts 2 <s:head />, used for skins with formtype="struts2".

See Formtype.struts2
See Skin.startSkin
See https://struts.apache.org/tag-developers/head-tag.html

--%><%@ page language="java" pageEncoding="UTF-8"
%><%@include file="/WEB-INF/taglibs.jspf"
%><s:head />