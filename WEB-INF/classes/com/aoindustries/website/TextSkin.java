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
    public void printLogo(HttpServletRequest req, JspWriter out, String urlBase) throws JspException {
        // Print no logo by default
    }

    /**
     * Prints the search form, if any exists.
     */
    public void printSearch(HttpServletRequest req, JspWriter out) throws JspException {
    }

    /**
     * Prints the common pages area, which is at the top of the site.
     */
    public void printCommonPages(HttpServletRequest req, JspWriter out) throws JspException {
    }

    /**
     * Prints the lines to include any CSS files.
     */
    public void printCssIncludes(JspWriter out, String urlBase) throws JspException {
    }

    /**
     * Prints the lines for any JavaScript sources.
     */
    public void printJavaScriptSources(JspWriter out, String urlBase) throws JspException {
    }

    /**
     * Prints the line for the favicon.
     */
    public void printFavIcon(JspWriter out, String urlBase) throws JspException {
    }

    public void startSkin(HttpServletRequest req, HttpServletResponse resp, JspWriter out, PageAttributes pageAttributes) throws JspException {
        try {
            boolean isSecure = req.isSecure();
            HttpSession session = req.getSession();
            Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
            MessageResources applicationResources = (MessageResources)req.getAttribute("/ApplicationResources");
            if(applicationResources==null) throw new JspException("Unable to load resources: /ApplicationResources");
            String httpsUrlBase = getHttpsUrlBase(req);
            String httpUrlBase = getHttpUrlBase(req);
            String urlBase = isSecure ? httpsUrlBase : httpUrlBase;
            String path = pageAttributes.getPath();
            if(path.startsWith("/")) path=path.substring(1);
            String fullPath = (isSecure ? httpsUrlBase : httpUrlBase) + path;

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
            printCssIncludes(out, urlBase);
            printJavaScriptSources(out, urlBase);
            out.print("    <TITLE>");
            List<Page> parents = pageAttributes.getParents();
            for(Page parent : parents) {
                ChainWriter.writeHtml(parent.getTitle(), out);
                out.print(" - ");
            }
            ChainWriter.writeHtml(pageAttributes.getTitle(), out);
            out.print("</TITLE>\n"
                    + "    <SCRIPT type='text/javascript' language='Javascript1.1' src='"); out.print(urlBase); out.print("commons-validator-1.3.0.js'></SCRIPT>\n");
            printFavIcon(out, urlBase);
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
            printLogo(req, out, urlBase);
            AOServConnector aoConn = AuthenticatedAction.getAoConn(req, resp);
            if(aoConn!=null) {
                out.print("          <HR>\n"
                        + "          ");
                out.print(applicationResources.getMessage(locale, "TextSkin.logoutPrompt"));
                out.print("<FORM style='display:inline;' name='logout_form' method='post' action='");
                out.print(urlBase);
                out.print("logout.do'><INPUT type='hidden' name='target' value='");
                ChainWriter.writeHtmlAttribute(fullPath, out);
                out.print("'><INPUT type='submit' value='");
                out.print(applicationResources.getMessage(locale, "TextSkin.logoutButtonLabel"));
                out.print("'></FORM>\n");
            } else {
                out.print("          <HR>\n"
                        + "          ");
                out.print(applicationResources.getMessage(locale, "TextSkin.loginPrompt"));
                out.print("<FORM style='display:inline;' name='login_form' method='post' action='");
                out.print(httpsUrlBase);
                out.print("login.do'><INPUT type='hidden' name='target' value='");
                ChainWriter.writeHtmlAttribute(fullPath, out);
                out.print("'><INPUT type='submit' value='");
                out.print(applicationResources.getMessage(locale, "TextSkin.loginButtonLabel"));
                out.print("'></FORM>\n");
            }
            out.print("          <HR>\n"
                    + "          <SPAN style='white-space: nowrap'>\n");
            List<Language> languages = getLanguages(req);
            List<Layout> layouts = getLayouts(req);
            if(languages.size()>1 || layouts.size()>1) {
                out.print("<SCRIPT language='JavaScript1.2'><!--\n");
                if(layouts.size()>1) {
                    out.print("  function selectLayout(layout) {\n");
                    for(Layout layout : layouts) {
                        out.print("    if(layout=='");
                        out.print(layout.getName());
                        out.print("') window.top.location.href='");
                        ChainWriter.writeHtmlAttribute(fullPath, out);
                        out.print("?layout=");
                        out.print(layout.getName());
                        out.print("';\n");
                    }
                    out.print("  }\n");
                }
                if(languages.size()>1) {
                    out.print("  function selectLanguage(language) {\n");
                    for(Language language : languages) {
                        out.print("    if(language=='");
                        out.print(language.getCode());
                        out.print("') window.top.location.href='");
                        ChainWriter.writeHtmlAttribute(fullPath,out);
                        out.print("?language=");
                        out.print(language.getCode());
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
                if(languages.size()>1) {
                    out.print("            <FORM style='display:inline;'>\n"
                            + "              ");
                    out.print(applicationResources.getMessage(locale, "TextSkin.languagePrompt"));
                    out.print("<SELECT name='language_selector' onChange='selectLanguage(this.form.language_selector.options[this.form.language_selector.selectedIndex].value);'>\n");
                    for(Language language : languages) {
                        out.print("                <OPTION value='");
                        out.print(language.getCode());
                        out.print('\'');
                        if(language.getCode().equals(locale.getLanguage())) out.print(" selected");
                        out.print('>');
                        out.print(language.getDisplay());
                        out.print("</OPTION>\n");
                    };
                    out.print("              </SELECT>\n"
                            + "            </FORM><BR>\n");
                }
            }
            printSearch(req, out);
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
                out.print(useEncryption ? httpsUrlBase : httpUrlBase);
                ChainWriter.writeHtmlAttribute(parentPath, out);
                out.print("'>");
                ChainWriter.writeHtml(navAlt, out);
                out.print("</A><BR>\n");
            }
            // Always include the current page in the current location area
            out.print("            <A href='");
            ChainWriter.writeHtmlAttribute(fullPath, out);
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
                out.print(useEncryption ? httpsUrlBase : httpUrlBase);
                ChainWriter.writeHtmlAttribute(siblingPath, out);
                out.print("'>");
                ChainWriter.writeHtml(navAlt, out);
                out.print("</A><BR>\n");
            }
            out.print("          </SPAN>\n"
                    + "          <HR>\n");
            printBelowRelatedPages(req, out);
            out.print("        </TD>\n"
                    + "        <TD valign='top'>\n");
            printCommonPages(req, out);
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void startContent(HttpServletRequest req, JspWriter out, PageAttributes pageAttributes, int[] colspans, String width) throws JspException {
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

    public void printContentTitle(HttpServletRequest req, JspWriter out, String title, int colspan) throws JspException {
        try {
            startContentLine(req, out, colspan, "center");
            out.print("<H1>");
            out.print(title);
            out.print("</H1>\n");
            endContentLine(req, out, 1, false);
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void startContentLine(HttpServletRequest req, JspWriter out, int colspan, String align) throws JspException {
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

    public void printContentVerticalDivider(HttpServletRequest req, JspWriter out, boolean visible, int colspan, int rowspan, String align) throws JspException {
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

    public void endContentLine(HttpServletRequest req, JspWriter out, int rowspan, boolean endsInternal) throws JspException {
        try {
            out.print("              </TD>\n"
                    + "            </TR>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void printContentHorizontalDivider(HttpServletRequest req, JspWriter out, int[] colspansAndDirections, boolean endsInternal) throws JspException {
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

    public void endContent(HttpServletRequest req, JspWriter out, PageAttributes pageAttributes, int[] colspans) throws JspException {
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

    public void beginLightArea(HttpServletRequest req, JspWriter out, String width, boolean nowrap) throws JspException {
        try {
            out.print("<TABLE border='5' cellpadding='0' cellspacing='0'>\n"
                    + "  <TR>\n"
                    + "    <TD class='ao_light_row'");
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

    public void endLightArea(HttpServletRequest req, JspWriter out) throws JspException {
        try {
            out.print("</TD>\n"
                    + "  </TR>\n"
                    + "</TABLE>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }
    
    public void beginWhiteArea(HttpServletRequest req, JspWriter out, String width, boolean nowrap) throws JspException {
        try {
            out.print("<TABLE border='5' cellpadding='0' cellspacing='0'>\n"
                    + "  <TR>\n"
                    + "    <TD class='ao_light_row'");
            if(width!=null && (width=width.trim()).length()>0) {
                out.print(" width='");
                out.print(width);
                out.print('\'');
            }
            if(nowrap) out.print(" nowrap");
            out.print(" bgcolor='white'>");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void endWhiteArea(HttpServletRequest req, JspWriter out) throws JspException {
        try {
            out.print("</TD>\n"
                    + "  </TR>\n"
                    + "</TABLE>\n");
        } catch(IOException err) {
            throw new JspException(err);
        }
    }

    public void printAutoIndex(HttpServletRequest req, JspWriter out, PageAttributes pageAttributes) throws JspException {
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
                out.print(useEncryption ? httpsUrlBase : httpUrlBase);
                ChainWriter.writeHtmlAttribute(siblingPath, out);
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
}
