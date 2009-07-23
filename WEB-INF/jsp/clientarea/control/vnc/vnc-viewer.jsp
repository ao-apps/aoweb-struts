<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html lang="true" xhtml="true">
    <head>
        <title><c:out value="${virtualServer.server.name}" /></title>
        <ao:script>
            // From http://www.hypergeneric.com/corpus/javascript-inner-viewport-resize/
            function getInnerSize() {
                // all except Explorer
                if (self.innerHeight) return [self.innerWidth,self.innerHeight]
                // Explorer 6 Strict Mode
                if (document.documentElement && document.documentElement.clientHeight) return [document.documentElement.clientWidth, document.documentElement.clientHeight];
                // other Explorers
                if (document.body) return [document.body.clientWidth, document.body.clientHeight];
                return [undefined,undefined];
            }
            function resizeVncViewer(w,h) {
                if(window.resizeBy) {
                    var inner = getInnerSize();
                    var ox = w-inner[0];
                    var oy = h-inner[1];
                    if(ox>0 || oy>0) {
                        // Move first since resizeBy larger may not result in desired size
                        if(window.moveBy) {
                            window.moveBy(-Math.round(ox/2), -Math.round(oy/2));
                        } else {
                            var x = window.screenLeft || window.screenX;
                            var y = window.screenTop || window.screenY;
                            window.moveTo(x-Math.round(ox/2), y-Math.round(oy/2));
                        }
                    }
                    window.resizeBy(ox, oy);
                    if(ox<=0 && oy<=0) {
                        // Move after since resizeBy smaller
                        if(window.moveBy) {
                            window.moveBy(-Math.round(ox/2), -Math.round(oy/2));
                        } else {
                            var x = window.screenLeft || window.screenX;
                            var y = window.screenTop || window.screenY;
                            window.moveTo(x-Math.round(ox/2), y-Math.round(oy/2));
                        }
                    }
                    // If the above didn't result in the desired size, do one last resizeBy/moveBy set.
                    // This occurs when window is not resized enough due to right side ending up offscreen.
                    inner = getInnerSize();
                    ox = w-inner[0];
                    oy = h-inner[1];
                    if(ox!=0 || oy!=0) {
                        if(ox>0 || oy>0) {
                            // Move first since resizeBy larger may not result in desired size
                            if(window.moveBy) {
                                window.moveBy(-ox, -oy);
                            } else {
                                var x = window.screenLeft || window.screenX;
                                var y = window.screenTop || window.screenY;
                                window.moveTo(x-ox, y-oy);
                            }
                        }
                        window.resizeBy(ox, oy);
                        if(ox<=0 && oy<=0) {
                            // Move after since resizeBy smaller
                            if(window.moveBy) {
                                window.moveBy(-ox, -oy);
                            } else {
                                var x = window.screenLeft || window.screenX;
                                var y = window.screenTop || window.screenY;
                                window.moveTo(x-ox, y-oy);
                            }
                        }
                    }
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
                    var inner = getInnerSize();
                    var ox = screen.availWidth-inner[0];
                    var oy = screen.availHeight-inner[1];
                    // now that we have an offset value, size the browser
                    // and position it
                    window.resizeTo(w+ox, h+oy);
                    window.moveTo(x,y);
                }
                document.getElementById('container').style.width=w+"px";
                document.getElementById('container').style.height=h+"px";
            }
        </ao:script>
        <style type='text/css'>
          html, body {
            margin:0px;
            padding:0px;
            border:0px;
          }
          html {
              /** Hide IE 6 vertical scrollbar */
              overflow: auto;
          }
          #container {
              position:absolute;
              width:100%;
              height:100%;
              margin:0px;
              padding:0px;
              border:0px;
          }
        </style>
    </head>
    <body>
        <div id="container">
            <applet
                code="VncViewer.class"
                archive="<c:out value="${siteSettings.brand.aowebStrutsHttpsUrlBase}clientarea/control/vnc/VncViewer.jar" />"
                width="100%"
                height="100%"
                hspace="0"
                vspace="0"
                style="padding:0px; margin:0px; border:0px; display:block;"
            >
                <param name="HOST" value="<c:out value="${siteSettings.brand.aowebStrutsHttpsURL.host}" />" />
                <param name="PORT" value="<c:out value="${siteSettings.brand.aowebStrutsVncBind.port.port}" />" />
                <param name="PASSWORD" value="<c:out value="${virtualServer.vncPassword}" />" />
                <param name="trustUrlVncCert" value="yes" />
                <param name="showDotCursor" value="yes" />
                <param name="resizeApplet" value="no" />
                <param name="resizeAppletWindow" value="yes" />
                <param name="centerControls" value="yes" />
            </applet>
        </div>
    </body>
</html:html>
