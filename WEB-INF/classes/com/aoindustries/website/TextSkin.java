package com.aoindustries.website;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.io.ChainWriter;
import com.aoindustries.website.skintags.Page;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.aoindustries.website.skintags.PageAttributes;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;

/**
 * The skin for the home page of the site.
 *
 * @author  AO Industries, Inc.
 */
public class TextSkin extends Skin {

    public String getName() {
        return "Text";
    }

    /**
     * Print the logo for the top left part of the page.
     */
    public void printLogo(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String urlBase) throws JspException {
        // Print no logo by default
    }

    /**
     * Prints the search form, if any exists.
     */
    public void printSearch(HttpServletRequest req, HttpServletResponse resp, JspWriter out) throws JspException {
    }

    /**
     * Prints the common pages area, which is at the top of the site.
     */
    public void printCommonPages(HttpServletRequest req, HttpServletResponse resp, JspWriter out) throws JspException {
    }

    /**
     * Prints the lines to include any CSS files.
     */
    public void printCssIncludes(HttpServletResponse resp, JspWriter out, String urlBase) throws JspException {
    }

    /**
     * Prints the lines for any JavaScript sources.
     */
    public void printJavaScriptSources(HttpServletResponse resp, JspWriter out, String urlBase) throws JspException {
    }

    /**
     * Prints the line for the favicon.
     */
    public void printFavIcon(HttpServletResponse resp, JspWriter out, String urlBase) throws JspException {
    }

    public static MessageResources getMessageResources(HttpServletRequest req) throws JspException {
        MessageResources resources = (MessageResources)req.getAttribute("/ApplicationResources");
        if(resources==null) throw new JspException("Unable to load resources: /ApplicationResources");
        return resources;
    }

    public void startSkin(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes) throws JspException {
        try {
            boolean isSecure = req.isSecure();
            HttpSession session = req.getSession();
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            MessageResources applicationResources = getMessageResources(req);
            String httpsUrlBase = getHttpsUrlBase(req);
            String httpUrlBase = getHttpUrlBase(req);
            String urlBase = isSecure ? httpsUrlBase : httpUrlBase;
            String path = pageAttributes.getPath();
            if(path.startsWith("/")) path=path.substring(1);
            final String fullPath = (isSecure ? httpsUrlBase : httpUrlBase) + path;
            final String encodedFullPath = resp.encodeURL(fullPath);

            out.print("  <HEAD>\n"
                    + "    <META http-equiv='Content-Type' content='text/html; charset=");
            out.print(getCharacterSet(locale)); out.print("'>\n");
            String keywords = pageAttributes.getKeywords();
            if(keywords!=null && keywords.length()>0) {
                out.print("    <META name='keywords' content='"); ChainWriter.writeHtmlAttribute(keywords, out); out.print("'>\n");
            }
            String description = pageAttributes.getDescription();
            if(description!=null && description.length()>0) {
                out.print("    <META name='description' content='"); ChainWriter.writeHtmlAttribute(description, out); out.print("'>\n");
            }
            String author = pageAttributes.getAuthor();
            if(author!=null && author.length()>0) {
                out.print("    <META name='author' content='"); ChainWriter.writeHtmlAttribute(author, out); out.print("'>\n");
            }
            out.print("    <LINK rel='stylesheet' href='"); out.print(resp.encodeURL(urlBase+"textskin/global.css")); out.print("' type='text/css'>\n");
            printCssIncludes(resp, out, urlBase);
            printJavaScriptSources(resp, out, urlBase);
            out.print("    <TITLE>");
            List<Page> parents = pageAttributes.getParents();
            for(Page parent : parents) {
                ChainWriter.writeHtml(parent.getTitle(), out);
                out.print(" - ");
            }
            ChainWriter.writeHtml(pageAttributes.getTitle(), out);
            out.print("</TITLE>\n"
                    + "    <SCRIPT type='text/javascript' language='Javascript1.1' src='");
            out.print(resp.encodeURL(urlBase + "commons-validator-1.3.1-compress.js"));
            out.print("'></SCRIPT>\n");
            printFavIcon(resp, out, urlBase);
            out.print("  </HEAD>\n"
                    + "  <BODY");
            String onLoad = pageAttributes.getOnLoad();
            if(onLoad!=null && onLoad.length()>0) {
                out.print(" onLoad=\""); out.print(onLoad); out.print('"');
            }
            out.print(">\n"
                    + "    <TABLE border=0 cellspacing=10 cellpadding=0>\n"
                    + "      <TR>\n"
                    + "        <TD valign='top'>\n");
            printLogo(req, resp, out, urlBase);
            AOServConnector aoConn = AuthenticatedAction.getAoConn(req, resp);
            if(aoConn!=null) {
                out.print("          <HR>\n"
                        + "          ");
                out.print(applicationResources.getMessage(locale, "TextSkin.logoutPrompt"));
                out.print("<FORM style='display:inline;' name='logout_form' method='post' action='");
                out.print(resp.encodeURL(urlBase+"logout.do"));
                out.print("'><INPUT type='hidden' name='target' value='");
                ChainWriter.writeHtmlAttribute(fullPath, out);
                out.print("'><INPUT type='submit' value='");
                out.print(applicationResources.getMessage(locale, "TextSkin.logoutButtonLabel"));
                out.print("'></FORM>\n");
            } else {
                out.print("          <HR>\n"
                        + "          ");
                out.print(applicationResources.getMessage(locale, "TextSkin.loginPrompt"));
                out.print("<FORM style='display:inline;' name='login_form' method='post' action='");
                out.print(resp.encodeURL(httpsUrlBase+"login.do"));
                out.print("'><INPUT type='hidden' name='target' value='");
                ChainWriter.writeHtmlAttribute(fullPath, out);
                out.print("'><INPUT type='submit' value='");
                out.print(applicationResources.getMessage(locale, "TextSkin.loginButtonLabel"));
                out.print("'></FORM>\n");
            }
            out.print("          <HR>\n"
                    + "          <SPAN style='white-space: nowrap'>\n");
            List<Layout> layouts = getLayouts(req);
            if(layouts.size()>1) {
                out.print("<SCRIPT language='JavaScript1.2'><!--\n");
                if(layouts.size()>1) {
                    out.print("  function selectLayout(layout) {\n");
                    for(Layout layout : layouts) {
                        out.print("    if(layout=='");
                        out.print(layout.getName());
                        out.print("') window.top.location.href='");
                        ChainWriter.writeHtmlAttribute(resp.encodeURL(fullPath+"?layout="+layout.getName()), out);
                        out.print("';\n");
                    }
                    out.print("  }\n");
                }
                out.print("// --></SCRIPT>\n");
                if(layouts.size()>1) {
                    out.print("            <FORM style='display:inline;'>\n"
                            + "              ");
                    out.print(applicationResources.getMessage(locale, "TextSkin.layoutPrompt"));
                    out.print("<SELECT name='layout_selector' onChange='selectLayout(this.form.layout_selector.options[this.form.layout_selector.selectedIndex].value);'>\n");
                    for(Layout layout : layouts) {
                        out.print("                <OPTION value='");
                        out.print(layout.getName());
                        out.print('\'');
                        if(getName().equals(layout.getName())) out.print(" selected");
                        out.print('>');
                        out.print(layout.getDisplay());
                        out.print("</OPTION>\n");
                    }
                    out.print("              </SELECT>\n"
                            + "            </FORM><BR>\n");
                }
            }
            List<Language> languages = getLanguages(req);
            if(languages.size()>1) {
                out.print("            ");
                for(Language language : languages) {
                    if(language.getCode().equalsIgnoreCase(locale.getLanguage())) {
                        out.print("&nbsp;<A href='");
                        ChainWriter.writeHtmlAttribute(resp.encodeURL(fullPath+(fullPath.indexOf('?')==-1 ? '?' : '&')+"language="+language.getCode()), out);
                        out.print("'><IMG src='");
                        out.print(resp.encodeURL(urlBase + language.getFlagOnSrc()));
                        out.print("' border='1' width='");
                        out.print(language.getFlagWidth());
                        out.print("' height='");
                        out.print(language.getFlagHeight());
                        out.print("' alt='");
                        out.print(language.getDisplay());
                        out.print("'></A>");
                    } else {
                        out.print("&nbsp;<A href='");
                        ChainWriter.writeHtmlAttribute(resp.encodeURL(fullPath+(fullPath.indexOf('?')==-1 ? '?' : '&')+"language="+language.getCode()), out);
                        out.print("' onMouseOver='document.images[\"flagSelector_");
                        out.print(language.getCode());
                        out.print("\"].src=\"");
                        out.print(resp.encodeURL(urlBase + language.getFlagOnSrc()));
                        out.print("\";' onMouseOut='document.images[\"flagSelector_");
                        out.print(language.getCode());
                        out.print("\"].src=\"");
                        out.print(resp.encodeURL(urlBase + language.getFlagOffSrc()));
                        out.print("\";'><IMG src='");
                        out.print(resp.encodeURL(urlBase + language.getFlagOffSrc()));
                        out.print("' name='flagSelector_");
                        out.print(language.getCode());
                        out.print("' border='1' width='");
                        out.print(language.getFlagWidth());
                        out.print("' height='");
                        out.print(language.getFlagHeight());
                        out.print("' alt='");
                        out.print(language.getDisplay());
                        out.print("'></A>");
                        ChainWriter.writeHtmlImagePreloadJavaScript(resp.encodeURL(urlBase + language.getFlagOnSrc()), out);
                    }
                }
                out.print("<BR>\n");
            }
            printSearch(req, resp, out);
            out.print("          </SPAN>\n"
                    + "          <HR>\n"
            // Display the parents
                    + "          <B>");
            out.print(applicationResources.getMessage(locale, "TextSkin.currentLocation"));
            out.print("</B><BR>\n"
                    + "          <SPAN style='white-space: nowrap'>\n");
            for(Page parent : parents) {
                String navAlt = parent.getNavImageAlt();
                boolean useEncryption = parent.getUseEncryption();
                String parentPath = parent.getPath();
                if(parentPath.startsWith("/")) parentPath=parentPath.substring(1);
                out.print("            <A href='");
                ChainWriter.writeHtmlAttribute(resp.encodeURL((useEncryption ? httpsUrlBase : httpUrlBase) + parentPath), out);
                out.print("'>");
                ChainWriter.writeHtml(navAlt, out);
                out.print("</A><BR>\n");
            }
            // Always include the current page in the current location area
            out.print("            <A href='");
            ChainWriter.writeHtmlAttribute(encodedFullPath, out);
            out.print("'>");
            ChainWriter.writeHtml(pageAttributes.getNavImageAlt(), out);
            out.print("</A><BR>\n"
                    + "          </SPAN>\n"
                    + "          <HR>\n"
                    + "          <B>");
            out.print(applicationResources.getMessage(locale, "TextSkin.relatedPages"));
            out.print("</B><BR>\n"
            // Display the siblings
                    + "          <SPAN style='white-space: nowrap'>\n");
            List<Page> siblings = pageAttributes.getSiblings();
            for(Page sibling : siblings) {
                String navAlt=sibling.getNavImageAlt();
                boolean useEncryption = sibling.getUseEncryption();
                String siblingPath = sibling.getPath();
                if(siblingPath.startsWith("/")) siblingPath=siblingPath.substring(1);
                out.print("          <A href='");
                ChainWriter.writeHtmlAttribute(resp.encodeURL((useEncryption ? httpsUrlBase : httpUrlBase) + siblingPath), out);
                out.print("'>");
                ChainWriter.writeHtml(navAlt, out);
                out.print("</A><BR>\n");
            }
            out.print("          </SPAN>\n"
                    + "          <HR>\n");
            printBelowRelatedPages(req, out);
            out.print("        </TD>\n"
                    + "        <TD valign='top'>\n");
            printCommonPages(req, resp, out);
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void startContent(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes, int[] colspans, String width) throws JspException {
        try {
            out.print("          <TABLE cellpadding='0' cellspacing='0' border='0'");
            if(width!=null && (width=width.trim()).length()>0) {
                out.print(" width='");
                out.print(width);
                out.print('\'');
            }
            out.print(" align='left' valign='top'>\n"
                    + "            <TR>\n");
            int totalColumns=0;
            for(int c=0;c<colspans.length;c++) {
                if(c>0) totalColumns++;
                totalColumns+=colspans[c];
            }
            out.print("              <TD");
            if(totalColumns!=1) {
                out.print(" colspan='");
                out.print(totalColumns);
                out.print('\'');
            }
            out.print("><HR></TD>\n"
                    + "            </TR>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void printContentTitle(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String title, int colspan) throws JspException {
        try {
            startContentLine(req, resp, out, colspan, "center");
            out.print("<H1>");
            out.print(title);
            out.print("</H1>\n");
            endContentLine(req, resp, out, 1, false);
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void startContentLine(HttpServletRequest req, HttpServletResponse resp, JspWriter out, int colspan, String align) throws JspException {
        try {
            out.print("            <TR>\n"
                    + "              <TD valign='top'");
            if(colspan!=1) {
                out.print(" colspan='");
                out.print(colspan);
                out.print('\'');
            }
            if(align!=null && (align=align.trim()).length()>0) {
                out.print(" align='");
                out.print(align);
                out.print('\'');
            }
            out.print('>');
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void printContentVerticalDivider(HttpServletRequest req, HttpServletResponse resp, JspWriter out, boolean visible, int colspan, int rowspan, String align) throws JspException {
        try {
            out.print("              </TD>\n");
            if(visible) out.print("              <TD>&nbsp;</TD>\n");
            out.print("              <TD valign='top'");
            if(colspan!=1) {
                out.print(" colspan='");
                out.print(colspan);
                out.print('\'');
            }
            if(rowspan!=1) {
                out.print(" rowspan='");
                out.print(rowspan);
                out.print('\'');
            }
            if(align!=null && (align=align.trim()).length()>0) {
                out.print(" align='");
                out.print(align);
                out.print('\'');
            }
            out.print('>');
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void endContentLine(HttpServletRequest req, HttpServletResponse resp, JspWriter out, int rowspan, boolean endsInternal) throws JspException {
        try {
            out.print("              </TD>\n"
                    + "            </TR>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void printContentHorizontalDivider(HttpServletRequest req, HttpServletResponse resp, JspWriter out, int[] colspansAndDirections, boolean endsInternal) throws JspException {
        try {
            out.print("            <TR>\n");
            for(int c=0;c<colspansAndDirections.length;c+=2) {
                if(c>0) {
                    int direction=colspansAndDirections[c-1];
                    switch(direction) {
                        case UP:
                            out.print("              <TD>&nbsp;</TD>\n");
                            break;
                        case DOWN:
                            out.print("              <TD>&nbsp;</TD>\n");
                            break;
                        case UP_AND_DOWN:
                            out.print("              <TD>&nbsp;</TD>\n");
                            break;
                        default: throw new IllegalArgumentException("Unknown direction: "+direction);
                    }
                }

                int colspan=colspansAndDirections[c];
                out.print("              <TD");
                if(colspan!=1) {
                    out.print(" colspan='");
                    out.print(colspan);
                    out.print('\'');
                }
                out.print("><HR></TD>\n");
            }
            out.print("            </TR>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void endContent(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes, int[] colspans) throws JspException {
        try {
            int totalColumns=0;
            for(int c=0;c<colspans.length;c++) {
                if(c>0) totalColumns+=1;
                totalColumns+=colspans[c];
            }
            out.print("            <TR><TD");
            if(totalColumns!=1) {
                out.print(" colspan='");
                out.print(totalColumns);
                out.print('\'');
            }
            out.print("><HR></TD></TR>\n");
            String copyright = pageAttributes.getCopyright();
            if(copyright!=null && copyright.length()>0) {
                out.print("            <TR><TD");
                if(totalColumns!=1) {
                    out.print(" colspan='");
                    out.print(totalColumns);
                    out.print('\'');
                }
                out.print(" align='center'><FONT size=-2>");
                out.print(copyright);
                out.print("</FONT></TD></TR>\n");
            }
            out.print("          </TABLE>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void endSkin(HttpServletRequest req, JspWriter out, PageAttributes pageAttributes) throws JspException {
        try {
            out.print("        </TD>\n"
                    + "      </TR>\n"
                    + "    </TABLE>\n"
                    + "  </BODY>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void beginLightArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String width, boolean nowrap) throws JspException {
        try {
            out.print("<TABLE border='5' cellpadding='0' cellspacing='0'>\n"
                    + "  <TR>\n"
                    + "    <TD class='ao_light_row' style='padding:4px;'");
            if(width!=null && (width=width.trim()).length()>0) {
                out.print(" width='");
                out.print(width);
                out.print('\'');
            }
            if(nowrap) out.print(" nowrap");
            out.print('>');
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void endLightArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out) throws JspException {
        try {
            out.print("</TD>\n"
                    + "  </TR>\n"
                    + "</TABLE>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }
    
    public void beginWhiteArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out, String width, boolean nowrap) throws JspException {
        try {
            out.print("<TABLE border='5' cellpadding='0' cellspacing='0'>\n"
                    + "  <TR>\n"
                    + "    <TD class='ao_white_row' style='padding:4px;'");
            if(width!=null && (width=width.trim()).length()>0) {
                out.print(" width='");
                out.print(width);
                out.print('\'');
            }
            if(nowrap) out.print(" nowrap");
            out.print('>');
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void endWhiteArea(HttpServletRequest req, HttpServletResponse resp, JspWriter out) throws JspException {
        try {
            out.print("</TD>\n"
                    + "  </TR>\n"
                    + "</TABLE>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void printAutoIndex(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes) throws JspException {
        try {
            String httpsUrlBase = getHttpsUrlBase(req);
            String httpUrlBase = getHttpUrlBase(req);

            out.print("<table cellpadding='0' cellspacing='10' border='0'>\n");
            List<Page> siblings = pageAttributes.getSiblings();
            for(Page sibling : siblings) {
                String navAlt=sibling.getNavImageAlt();
                boolean useEncryption = sibling.getUseEncryption();
                String siblingPath = sibling.getPath();
                if(siblingPath.startsWith("/")) siblingPath=siblingPath.substring(1);

                out.print("  <TR>\n"
                        + "    <TD nowrap><A class='ao_light_link' href='");
                ChainWriter.writeHtmlAttribute(resp.encodeURL((useEncryption ? httpsUrlBase : httpUrlBase) + siblingPath), out);
                out.print("'>");
                ChainWriter.writeHtml(navAlt, out);
                out.print("</A></TD>\n"
                        + "    <TD width='12' nowrap>&nbsp;</TD>\n"
                        + "    <TD nowrap>");
                String description = sibling.getDescription();
                if(description!=null && (description=description.trim()).length()>0) {
                    out.print(description);
                } else {
                    String title = sibling.getTitle();
                    if(title!=null && (title=title.trim()).length()>0) {
                        out.print(title);
                    } else {
                        out.print("&nbsp;");
                    }
                }
                out.print("</TD>\n"
                        + "  </TR>\n")
                ;
            }
            out.print("</table>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    /**
     * Prints content below the related pages area on the left.
     */
    public void printBelowRelatedPages(HttpServletRequest req, JspWriter out) throws JspException {
    }

    /**
     * Begins a popup group.
     */
    public void beginPopupGroup(HttpServletRequest req, JspWriter out, long groupId) throws JspException {
        try {
            out.print("<SCRIPT language=\"JavaScript1.2\"><!--\n"
                    + "    var popupGroupTimer"); out.print(groupId); out.print("=null;\n"
                    + "    var popupGroupAuto"); out.print(groupId); out.print("=null;\n"
                    + "    function popupGroupHideAllDetails"); out.print(groupId); out.print("() {\n"
                    + "        var spanElements = document.getElementsByTagName ? document.getElementsByTagName(\"span\") : document.all.tags(\"span\");\n"
                    + "        for (var c=0; c < spanElements.length; c++) {\n"
                    + "            if(spanElements[c].id.indexOf(\"groupedPopup_"); out.print(groupId); out.print("_\")==0) {\n"
                    + "                spanElements[c].style.visibility=\"hidden\";\n"
                    + "            }\n"
                    + "        }\n"
                    + "    }\n"
                    + "    function popupGroupToggleDetails"); out.print(groupId); out.print("(popupId) {\n"
                    + "        if(popupGroupTimer"); out.print(groupId); out.print("!=null) clearTimeout(popupGroupTimer"); out.print(groupId); out.print(");\n"
                    + "        var elemStyle = document.getElementById(\"groupedPopup_"); out.print(groupId); out.print("_\"+popupId).style;\n"
                    + "        if(elemStyle.visibility==\"visible\") {\n"
                    + "            elemStyle.visibility=\"hidden\";\n"
                    + "        } else {\n"
                    + "            popupGroupHideAllDetails"); out.print(groupId); out.print("();\n"
                    + "            elemStyle.visibility=\"visible\";\n"
                    + "        }\n"
                    + "    }\n"
                    + "    function popupGroupShowDetails"); out.print(groupId); out.print("(popupId) {\n"
                    + "        if(popupGroupTimer"); out.print(groupId); out.print("!=null) clearTimeout(popupGroupTimer"); out.print(groupId); out.print(");\n"
                    + "        var elemStyle = document.getElementById(\"groupedPopup_"); out.print(groupId); out.print("_\"+popupId).style;\n"
                    + "        if(elemStyle.visibility!=\"visible\") {\n"
                    + "            popupGroupHideAllDetails"); out.print(groupId); out.print("();\n"
                    + "            elemStyle.visibility=\"visible\";\n"
                    + "        }\n"
                    + "    }\n"
                    + "// --></SCRIPT>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    /**
     * Ends a popup group.
     */
    public void endPopupGroup(HttpServletRequest req, JspWriter out, long groupId) {
        // Nothing at the popup group end
    }

    /**
     * Begins a popup that is in a popup group.
     */
    public void beginPopup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId, String width) throws JspException {
        try {
            HttpSession session = req.getSession();
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            MessageResources applicationResources = getMessageResources(req);
            String urlBase = req.isSecure() ? getHttpsUrlBase(req) : getHttpUrlBase(req);

            out.print("<SPAN id=\"groupedPopupAnchor_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("\" style=\"position:relative;\"><img src=\"");
            out.print(resp.encodeURL(urlBase + applicationResources.getMessage(locale, "TextSkin.popup.src")));
            out.print("\" alt=\"");
            out.print(applicationResources.getMessage(locale, "TextSkin.popup.alt"));
            out.print("\" border=\"0\" width=\"");
            out.print(applicationResources.getMessage(locale, "TextSkin.popup.width"));
            out.print("\" height=\"");
            out.print(applicationResources.getMessage(locale, "TextSkin.popup.height"));
            out.print("\" align=\"absmiddle\" style=\"cursor:pointer; cursor:hand;\" onMouseOver=\"popupGroupTimer");
            out.print(groupId);
            out.print("=setTimeout('popupGroupAuto");
            out.print(groupId);
            out.print("=true; popupGroupShowDetails");
            out.print(groupId);
            out.print('(');
            out.print(popupId);
            out.print(")', 1000);\" onMouseOut=\"if(popupGroupAuto");
            out.print(groupId);
            out.print(") popupGroupHideAllDetails");
            out.print(groupId);
            out.print("(); if(popupGroupTimer");
            out.print(groupId);
            out.print("!=null) clearTimeout(popupGroupTimer");
            out.print(groupId);
            out.print(");\" onClick=\"popupGroupAuto");
            out.print(groupId);
            out.print("=false; popupGroupToggleDetails");
            out.print(groupId);
            out.print('(');
            out.print(popupId);
            out.print(");\">\n"
                    + "    <SPAN width=\"100%\" id=\"groupedPopup_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("\" style=\"white-space:nowrap; position:absolute; bottom:30px; left:30px; visibility: hidden; z-index:1\">\n"
                    + "        <TABLE border=\"0\" cellpadding=\"0\" cellspacing=\"0\"");
            if(width!=null && width.length()>0) {
                out.print(" width=\"");
                out.print(width);
                out.print('"');
            }
            out.print(">\n"
                    + "            <TR>\n"
                    + "                <TD nowrap width=\"12\"><IMG src=\"");
            out.print(resp.encodeURL(urlBase + "textskin/popup_topleft.gif"));
            out.print("\" width=\"12\" height=\"12\"></TD>\n"
                    + "                <TD nowrap height=\"12\" style=\"background-image:url(");
            out.print(resp.encodeURL(urlBase + "textskin/popup_top.gif"));
            out.print(");\"><IMG src=\"");
            out.print(resp.encodeURL(urlBase + "textskin/popup_top.gif"));
            out.print("\" width=\"1\" height=\"12\"></TD>\n"
                    + "                <TD nowrap width=\"12\"><IMG src=\"");
            out.print(resp.encodeURL(urlBase + "textskin/popup_topright.gif"));
            out.print("\" width=\"12\" height=\"12\"></TD>\n"
                    + "            </TR>\n"
                    + "            <TR>\n"
                    + "                <TD style=\"background-image:url(");
            out.print(resp.encodeURL(urlBase + "textskin/popup_left.gif"));
            out.print(");\"><IMG src=\"");
            out.print(resp.encodeURL(urlBase + "textskin/popup_left.gif"));
            out.print("\" width=\"12\" height=\"12\"></TD>\n"
                    + "                <TD class=\"ao_popup_light_row\">");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    /**
     * Prints a popup close link/image/button for a popup that is part of a popup group.
     */
    public void printPopupClose(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId) throws JspException {
        try {
            HttpSession session = req.getSession();
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            MessageResources applicationResources = getMessageResources(req);
            String urlBase = req.isSecure() ? getHttpsUrlBase(req) : getHttpUrlBase(req);

            out.print("<img src=\"");
            out.print(resp.encodeURL(urlBase + applicationResources.getMessage(locale, "TextSkin.popupClose.src")));
            out.print("\" alt=\"");
            out.print(applicationResources.getMessage(locale, "TextSkin.popupClose.alt"));
            out.print("\" border=\"0\" width=\"");
            out.print(applicationResources.getMessage(locale, "TextSkin.popupClose.width"));
            out.print("\" height=\"");
            out.print(applicationResources.getMessage(locale, "TextSkin.popupClose.height"));
            out.print("\" align=\"absmiddle\" style=\"cursor:pointer; cursor:hand;\" onClick=\"popupGroupHideAllDetails");
            out.print(groupId);
            out.print("();\">");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    /**
     * Ends a popup that is in a popup group.
     */
    public void endPopup(HttpServletRequest req, HttpServletResponse resp, JspWriter out, long groupId, long popupId, String width) throws JspException {
        try {
            String urlBase = req.isSecure() ? getHttpsUrlBase(req) : getHttpUrlBase(req);
            out.print("</TD>\n"
                    + "                <TD style=\"background-image:url(");
            out.print(resp.encodeURL(urlBase + "textskin/popup_right.gif"));
            out.print(");\"><IMG src=\"");
            out.print(resp.encodeURL(urlBase + "textskin/popup_right.gif"));
            out.print("\" width=\"12\" height=\"1\"></TD>\n"
                    + "            </TR>\n"
                    + "            <TR>\n" 
                    + "                <TD nowrap width=\"12\"><IMG src=\"");
            out.print(resp.encodeURL(urlBase + "textskin/popup_bottomleft.gif"));
            out.print("\" width=\"12\" height=\"12\"></TD>\n"
                    + "                <TD nowrap height=\"12\" style=\"background-image:url(");
            out.print(resp.encodeURL(urlBase + "textskin/popup_bottom.gif"));
            out.print(");\"><IMG src=\"");
            out.print(resp.encodeURL(urlBase + "textskin/popup_bottom.gif"));
            out.print("\" width=\"1\" height=\"12\"></TD>\n"
                    + "                <TD nowrap width=\"12\"><IMG src=\"");
            out.print(resp.encodeURL(urlBase + "textskin/popup_bottomright.gif"));
            out.print("\" width=\"12\" height=\"12\"></TD>\n"
                    + "            </TR>\n"
                    + "        </TABLE>\n"
                    + "    </SPAN>\n"
                    + "</SPAN>\n"
                    + "<SCRIPT language='JavaScript1.2'><!--\n"
                    + "    // Override onload\n"
                    + "    var groupedPopupOldOnload_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print(" = window.onload;\n"
                    + "    function adjustPositionOnload_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("() {\n"
                    + "        adjustPosition_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("();\n"
                    + "        if(groupedPopupOldOnload_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print(") {\n"
                    + "            groupedPopupOldOnload_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("();\n"
                    + "            groupedPopupOldOnload_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print(" = null;\n"
                    + "        }\n"
                    + "    }\n"
                    + "    window.onload = adjustPositionOnload_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print(";\n"
                    + "    // Override onresize\n"
                    + "    var groupedPopupOldOnresize_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print(" = window.onresize;\n"
                    + "    function adjustPositionOnresize_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("() {\n"
                    + "        adjustPosition_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("();\n"
                    + "        if(groupedPopupOldOnresize_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print(") {\n"
                    + "            groupedPopupOldOnresize_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("();\n"
                    + "        }\n"
                    + "    }\n"
                    + "    window.onresize = adjustPositionOnresize_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print(";\n"
                    + "    function adjustPosition_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("() {\n"
                    + "        var popupAnchor = document.getElementById(\"groupedPopupAnchor_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("\");\n"
                    + "        var popup = document.getElementById(\"groupedPopup_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("\");\n"
                    + "        // Find the screen position of the anchor\n"
                    + "        var popupAnchorLeft = 0;\n"
                    + "        var obj = popupAnchor;\n"
                    + "        if(obj.offsetParent) {\n"
                    + "            popupAnchorLeft = obj.offsetLeft\n"
                    + "            while (obj = obj.offsetParent) {\n"
                    + "                popupAnchorLeft += obj.offsetLeft\n"
                    + "            }\n"
                    + "        }\n"
                    + "        var popupAnchorRight = popupAnchorLeft + popupAnchor.offsetWidth;\n"
                    + "        // Find the width of the popup\n"
                    + "        var popupWidth = popup.offsetWidth;\n"
                    + "        // Find the width of the screen\n"
                    + "        var screenWidth = (document.compatMode && document.compatMode == \"CSS1Compat\") ? document.documentElement.clientWidth : document.body.clientWidth;\n"
                    + "        // Find the desired screen position of the popup\n"
                    + "        var popupScreenPosition = 0;\n"
                    + "        if(screenWidth<=(popupWidth+12)) {\n"
                    + "            popupScreenPosition = 0;\n"
                    + "        } else {\n"
                    + "            popupScreenPosition = screenWidth - popupWidth - 12;\n"
                    + "            if(popupAnchorRight < popupScreenPosition) popupScreenPosition = popupAnchorRight;\n"
                    + "        }\n"
                    + "        popup.style.left=(popupScreenPosition-popupAnchorLeft)+\"px\";\n"
                    + "    }\n"
                    + "    // Call once at parse time for when the popup is activated while page loading (before onload called)\n"
                    + "    adjustPosition_");
            out.print(groupId);
            out.print('_');
            out.print(popupId);
            out.print("();\n"
                    + "// --></SCRIPT>");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }
}
