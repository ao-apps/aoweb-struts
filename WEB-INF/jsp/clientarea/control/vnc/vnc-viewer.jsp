<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Vertical applet resize doesn't work in Firefox 2, Java 1.6, Linux, XHTML 1.0 Strict/Transitional and HTML 4.01 --%>
<%-- Maybe fixed in JDK 1.7: http://forums.java.net/jive/thread.jspa?threadID=57236&tstart=30 --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>
        <title><c:out value="${virtualServer.server.name}" /></title>
        <script type="text/javascript">
            // From http://www.hypergeneric.com/corpus/javascript-inner-viewport-resize/
            function GetInnerSize() {
                // all except Explorer
                if (self.innerHeight) return [self.innerWidth,self.innerHeight]
                // Explorer 6 Strict Mode
                if (document.documentElement && document.documentElement.clientHeight) return [document.documentElement.clientWidth, document.documentElement.clientHeight];
                // other Explorers
                if (document.body) return [document.body.clientWidth, document.body.clientHeight];
                return [undefined,undefined];
            }
            function ResizeToInner(w,h) {
                if(window.resizeBy) {
                    var inner = GetInnerSize();
                    window.resizeBy(w-inner[0], h-inner[1]);
                } else {
                    // make sure we have a final x/y value
                    // pick one or the other windows value, not both
                    var x = window.screenLeft || window.screenX;
                    var y = window.screenTop || window.screenY;
                    // for now, move the window to the top left
                    // then resize to the maximum viewable dimension possible
                    window.moveTo(0,0);
                    window.resizeTo(screen.availWidth,screen.availHeight);
                    // now that we have set the browser to it's biggest possible size
                    // get the inner dimensions.  the offset is the difference.
                    var inner = GetInnerSize();
                    var ox = screen.availWidth-inner[0];
                    var oy = screen.availHeight-inner[1];
                    // now that we have an offset value, size the browser
                    // and position it
                    window.resizeTo(w+ox, h+oy);
                    window.moveTo(x,y);
                }
            }
        </script>
        <style type='text/css'>
          body {
            margin:0px;
            padding:0px;
          }
        </style>
    </head>
    <body>
        <applet code="VncViewer.class" archive="<c:out value="${siteSettings.brand.aowebStrutsHttpsUrlBase}clientarea/control/vnc/VncViewer.jar" />" width="100%" height="100%">
            <param name="HOST" value="<c:out value="${siteSettings.brand.aowebStrutsHttpsURL.host}" />" />
            <param name="PORT" value="<c:out value="${siteSettings.brand.aowebStrutsVncBind.port.port}" />" />
            <param name="PASSWORD" value="<c:out value="${virtualServer.vncPassword}" />" />
            <param name="trustUrlVncCert" value="yes" />
            <param name="showDotCursor" value="yes" />
            <param name="resizeAppletWindow" value="yes" />
            <param name="centerControls" value="yes" />
        </applet>
    </body>
</html>
