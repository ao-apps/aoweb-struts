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
                    if(ox!=0 || oy!=0) {
                        // Move by half to keep centered
                        var oldX = window.screenLeft || window.screenX;
                        var oldY = window.screenTop || window.screenY;
                        var newX = oldX-Math.round(ox/2);
                        var newY = oldY-Math.round(oy/2);
                        if(window.moveBy) window.moveBy(-Math.round(ox/2), -Math.round(oy/2));
                        else window.moveTo(newX, newY);
                        var actualX = window.screenLeft || window.screenX;
                        var actualY = window.screenTop || window.screenY;
                        inner = getInnerSize();
                        ox = w-inner[0];
                        oy = h-inner[1];
                        if(ox!=0 || oy!=0) {
                            window.resizeBy(ox, oy);
                            // Move again since didn't move far enough before resize
                            if(actualX!=newX || actualY!=newY) {
                                if(window.moveBy) window.moveBy(newX-actualX, newY-actualY);
                                else window.moveTo(newX, newy);
                            }
                            inner = getInnerSize();
                            ox = w-inner[0];
                            oy = h-inner[1];
                            var didSecondResize = false;
                            if(ox!=0 || oy!=0) {
                                // Move and resize again since resizeBy smaller than requested.
                                // This occurs when window is not resized enough due to right side ending up offscreen.
                                if(window.moveBy) {
                                    window.moveBy(-ox, -oy);
                                } else {
                                    var x = window.screenLeft || window.screenX;
                                    var y = window.screenTop || window.screenY;
                                    window.moveTo(x-ox, y-oy);
                                }
                                inner = getInnerSize();
                                ox = w-inner[0];
                                oy = h-inner[1];
                                if(ox!=0 || oy!=0) {
                                    window.resizeBy(ox, oy);
                                    didSecondResize = true;
                                }
                            }
                            if(!didSecondResize) {
                                // IE8 applet resize bug workaround.
                                // The applet is not resized when window.resizeBy is followed by getInnerSize().
                                // Browser detect from http://www.javascriptkit.com/javatutors/navigator.shtml
                                if (/MSIE (\d+\.\d+);/.test(navigator.userAgent)){ //test for MSIE x.x;
                                    var ieversion=new Number(RegExp.$1) // capture x.x portion and store as a number
                                    if(ieversion>=8) {
                                        window.resizeBy(-10, -10);
                                        window.resizeBy(10, 10);
                                    }
                                }
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
