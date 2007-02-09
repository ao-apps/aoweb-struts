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
     * Gets the prefix for URLs for the SSL server.  This should always end with a /.
     */
    public String getHttpsUrlBase(HttpServletRequest req) throws IOException {
        int port = req.getServerPort();
        if(port!=80 && port!=443) {
            // Non-ssl development area
            return "http://"+req.getServerName()+":"+port+"/";
        } else {
            return "https://"+req.getServerName()+"/";
        }
    }

    /**
     * Gets the prefix for URLs for the non-SSL server.  This should always end with a /.
     */
    public String getHttpUrlBase(HttpServletRequest req) throws IOException {
        int port = req.getServerPort();
        if(port!=80 && port!=443) {
            // Non-ssl development area
            return "http://"+req.getServerName()+":"+port+"/";
        } else {
            return "http://"+req.getServerName()+"/";
        }
    }

    /**
     * Print the logo for the top left part of the page.
     */
    public void printLogo(HttpServletRequest req, ChainWriter out, String urlBase) throws IOException {
        // Print no logo by default
    }

    /**
     * Gets the list of languages supported by this site.
     */
    public List<Language> getLanguages(HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession();
        Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        MessageResources applicationResources = (MessageResources)req.getAttribute("/ApplicationResources");
        if(applicationResources==null) throw new IOException("Unable to load resources: /ApplicationResources");
        List<Language> languages = new ArrayList<Language>(2);
        languages.add(new Language(Locale.ENGLISH.getLanguage(), applicationResources.getMessage(locale, "TextSkin.language.en")));
        languages.add(new Language(Locale.JAPANESE.getLanguage(), applicationResources.getMessage(locale, "TextSkin.language.ja")));
        return languages;
    }

    /**
     * Gets the layouts supported by this site.
     */
    public List<Layout> getLayouts(HttpServletRequest req) throws IOException {
        HttpSession session = req.getSession();
        Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        MessageResources applicationResources = (MessageResources)req.getAttribute("/ApplicationResources");
        if(applicationResources==null) throw new IOException("Unable to load resources: /ApplicationResources");
        List<Layout> layouts = new ArrayList<Layout>(2);
        layouts.add(new Layout("Text", applicationResources.getMessage(locale, "TextSkin.name")));
        return layouts;
    }

    /**
     * Prints the search form, if any exists.
     */
    public void printSearch(HttpServletRequest req, ChainWriter out) throws IOException {
    }

    /**
     * Prints the common pages area, which is at the top of the site.
     */
    public void printCommonPages(HttpServletRequest req, ChainWriter out) throws IOException {
    }

    /**
     * Prints the lines to include any CSS files.
     */
    public void printCssIncludes(ChainWriter out, String urlBase) {
    }

    /**
     * Prints the lines for any JavaScript sources.
     */
    public void printJavaScriptSources(ChainWriter out, String urlBase) {
    }

    public void startSkin(HttpServletRequest req, ChainWriter out, PageAttributes pageAttributes) throws IOException {
        HttpSession session = req.getSession();
        Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        MessageResources applicationResources = (MessageResources)req.getAttribute("/ApplicationResources");
        if(applicationResources==null) throw new IOException("Unable to load resources: /ApplicationResources");
        String httpsUrlBase = getHttpsUrlBase(req);
        String httpUrlBase = getHttpUrlBase(req);
        String urlBase = req.isSecure() ? httpsUrlBase : httpUrlBase;
        String path = pageAttributes.getPath();
        if(path.startsWith("/")) path=path.substring(1);
        String fullPath = (req.isSecure() ? httpsUrlBase : httpUrlBase) + path;

        out.print("  <HEAD>\n"
                + "    <META http-equiv='Content-Type' content='text/html; charset=").print(getCharacterSet(locale)).print("'>\n");
        String keywords = pageAttributes.getKeywords();
        if(keywords!=null && keywords.length()>0) {
            out.print("    <META name='keywords' content='").printEI(keywords).print("'>\n");
        }
        String description = pageAttributes.getDescription();
        if(description!=null && description.length()>0) {
            out.print("    <META name='description' content='").printEI(description).print("'>\n");
        }
        String author = pageAttributes.getAuthor();
        if(author!=null && author.length()>0) {
            out.print("    <META name='author' content='").printEI(author).print("'>\n");
        }
        printCssIncludes(out, urlBase);
        printJavaScriptSources(out, urlBase);
        out.print("    <TITLE>");
        List<Page> parents = pageAttributes.getParents();
        for(Page parent : parents) {
            out.printEH(parent.getTitle()).print(" - ");
        }
        out.printEH(pageAttributes.getTitle()).print("</TITLE>\n"
                + "    <SCRIPT type='text/javascript' language='Javascript1.1' src='").print(urlBase).print("commons-validator-1.3.0.js'></SCRIPT>\n"
                + "  </HEAD>\n"
                + "  <BODY");
        String onLoad = pageAttributes.getOnLoad();
        if(onLoad!=null && onLoad.length()>0) {
            out.print(" onLoad=\"").print(onLoad).print('"');
        }
        out.print(">\n"
                + "    <TABLE border=0 cellspacing=10 cellpadding=0>\n"
                + "      <TR>\n"
                + "        <TD valign='top'>\n");
        printLogo(req, out, urlBase);
        AOServConnector aoConn = AuthenticatedAction.getAoConn(req);
        if(aoConn!=null) {
            out.print("          <HR>\n"
                    + "          ").print(applicationResources.getMessage(locale, "TextSkin.logoutPrompt")).print("<FORM target='_top' style='display:inline;' name='logout_form' method='post' action='").print(urlBase).print("logout.do'><INPUT type='hidden' name='target' value='").printEI(fullPath).print("'><INPUT type='submit' value='").print(applicationResources.getMessage(locale, "TextSkin.logoutButtonLabel")).print("'></FORM>\n");
        } else {
            out.print("          <HR>\n"
                    + "          ").print(applicationResources.getMessage(locale, "TextSkin.loginPrompt")).print("<FORM target='_top' style='display:inline;' name='login_form' method='post' action='").print(httpsUrlBase).print("login.do'><INPUT type='hidden' name='target' value='").printEI(fullPath).print("'><INPUT type='submit' value='").print(applicationResources.getMessage(locale, "TextSkin.loginButtonLabel")).print("'></FORM>\n");
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
                    out.print("    if(layout=='").print(layout.getName()).print("') window.top.location.href='").printEI(fullPath).print("?layout=").print(layout.getName()).print("';\n");
                }
                out.print("  }\n");
            }
            if(languages.size()>1) {
                out.print("  function selectLanguage(language) {\n");
                for(Language language : languages) {
                    out.print("    if(language=='").print(language.getCode()).print("') window.top.location.href='").printEI(fullPath).print("?language=").print(language.getCode()).print("';\n");
                }
                out.print("  }\n");
            }
            out.print("// --></SCRIPT>\n");
            if(layouts.size()>1) {
                out.print("            <FORM style='display:inline;'>\n"
                        + "              ").print(applicationResources.getMessage(locale, "TextSkin.layoutPrompt")).print("<SELECT name='layout_selector' onChange='selectLayout(this.form.layout_selector.options[this.form.layout_selector.selectedIndex].value);'>\n");
                for(Layout layout : layouts) {
                    out.print("                <OPTION value='").print(layout.getName()).print('\'');
                    if("Text".equals(layout.getName())) out.print(" selected");
                    out.print('>').print(layout.getDisplay()).print("</OPTION>\n");
                }
                out.print("              </SELECT>\n"
                        + "            </FORM><BR>\n");
            }
            if(languages.size()>1) {
                out.print("            <FORM style='display:inline;'>\n"
                        + "              ").print(applicationResources.getMessage(locale, "TextSkin.languagePrompt")).print("<SELECT name='language_selector' onChange='selectLanguage(this.form.language_selector.options[this.form.language_selector.selectedIndex].value);'>\n");
                for(Language language : languages) {
                    out.print("                <OPTION value='").print(language.getCode()).print("'");
                    if(language.getCode().equals(locale.getLanguage())) out.print(" selected");
                    out.print('>').print(language.getDisplay()).print("</OPTION>\n");
                };
                out.print("              </SELECT>\n"
                        + "            </FORM><BR>\n");
            }
        }
        printSearch(req, out);
        out.print("          </SPAN>\n"
                + "          <HR>\n"
        // Display the parents
                + "          <B>").print(applicationResources.getMessage(locale, "TextSkin.currentLocation")).print("</B><BR>\n"
                + "          <SPAN style='white-space: nowrap'>\n");
        for(Page parent : parents) {
            String navAlt = parent.getNavImageAlt();
            boolean useEncryption = parent.getUseEncryption();
            String parentPath = parent.getPath();
            if(parentPath.startsWith("/")) parentPath=parentPath.substring(1);
            out.print("            <A href='").print(useEncryption ? httpsUrlBase : httpUrlBase).printEI(parentPath).print("'>").printEH(navAlt).print("</A><BR>\n");
        }
        // Always include the current page in the current location area
        out.print("            <A href='").printEI(fullPath).print("'>").printEH(pageAttributes.getNavImageAlt()).print("</A><BR>\n"
                + "          </SPAN>\n"
                + "          <HR>\n"
                + "          <B>").print(applicationResources.getMessage(locale, "TextSkin.relatedPages")).print("</B><BR>\n"
        // Display the siblings
                + "          <SPAN style='white-space: nowrap'>\n");
        List<Page> siblings = pageAttributes.getSiblings();
        for(Page sibling : siblings) {
            String navAlt=sibling.getNavImageAlt();
            boolean useEncryption = sibling.getUseEncryption();
            String siblingPath = sibling.getPath();
            if(siblingPath.startsWith("/")) siblingPath=siblingPath.substring(1);
            out.print("          <A href='").print(useEncryption ? httpsUrlBase : httpUrlBase).printEI(siblingPath).print("'>").printEH(navAlt).print("</A><BR>\n");
        }
        out.print("          </SPAN>\n"
                + "          <HR>\n"
                + "        </TD>\n"
                + "        <TD valign='top'>\n");
        printCommonPages(req, out);
        out.print("          <TABLE cellpadding=0 cellspacing=0 border=0 width='600'' align='left' valign='top'>\n"
                + "            <TR>\n"
                + "              <TD><HR></TD>\n"
                + "            </TR>\n"
                + "            <TR>\n"
                + "              <TD valign='top' align='center'><H1>").print(pageAttributes.getTitle()).print("</H1></TD>\n"
                + "            </TR>\n"
                + "            <TR>\n"
                + "              <TD><HR></TD>\n"
                + "            </TR>\n"
                + "            <TR>\n"
                + "              <TD valign='top'>");
    }

    public void endSkin(HttpServletRequest req, ChainWriter out, PageAttributes pageAttributes) throws IOException {
        out.print("</TD>\n"
                + "            </TR>\n"
                + "            <TR><TD><HR></TD></TR>\n");
        String copyright = pageAttributes.getCopyright();
        if(copyright!=null && copyright.length()>0) {
            out.print("            <TR><TD align='center'><FONT size=-2>"); out.print(copyright); out.print("</FONT></TD></TR>\n");
        }
        out.print("          </TABLE>\n"
                + "        </TD>\n"
                + "      </TR>\n"
                + "    </TABLE>\n"
                + "  </BODY>\n");
    }

    public void beginLightArea(ChainWriter out) {
        out.print("<TABLE border=5 cellpadding=0 cellspacing=0>\n"
                + "  <TR>\n"
                + "    <TD class='ao_light_row'>");
    }

    public void endLightArea(ChainWriter out) {
	out.print("</TD>\n"
                + "  </TR>\n"
                + "</TABLE>\n");
    }
    
    public static class Language {
        private String code;
        private String display;
        
        public Language(String code, String display) {
            this.code = code;
            this.display = display;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getDisplay() {
            return display;
        }
    }

    public static class Layout {
        private String name;
        private String display;
        
        public Layout(String name, String display) {
            this.name = name;
            this.display = display;
        }
        
        public String getName() {
            return name;
        }
        
        public String getDisplay() {
            return display;
        }
    }
}
