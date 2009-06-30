package com.aoindustries.website;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.util.ErrorPrinter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import org.apache.struts.Globals;

/**
 * @author  AO Industries, Inc.
 */
public class SessionResponseWrapper extends HttpServletResponseWrapper {

    final private HttpServletRequest request;
    final private HttpServletResponse response;

    public SessionResponseWrapper(HttpServletRequest request, HttpServletResponse response) {
        super(response);
        this.request = request;
        this.response = response;
    }

    /**
     * @deprecated  Please use encodeURL.
     *
     * @see  #encodeURL(String)
     */
    @Override
    public String encodeUrl(String url) {
        return encodeURL(url);
    }

    @Override
    public String encodeURL(final String url) {
        //System.err.println("DEBUG: encodeURL: "+url);
        try {
            // If starts with http:// or https:// parse out the first part of the URL, encode the path, and reassemble.
            String protocol;
            String remaining;
            if(url.length()>7 && (protocol=url.substring(0, 7)).equalsIgnoreCase("http://")) {
                remaining = url.substring(7);
            } else if(url.length()>8 && (protocol=url.substring(0, 8)).equalsIgnoreCase("https://")) {
                remaining = url.substring(8);
            } else {
                return addNoCookieParameters(url, false);
                //return response.encodeURL(url);
            }
            int slashPos = remaining.indexOf('/');
            if(slashPos==-1) {
                return addNoCookieParameters(url, false);
                //return response.encodeURL(url);
            }
            String hostPort = remaining.substring(0, slashPos);
            int colonPos = hostPort.indexOf(':');
            String host = colonPos==-1 ? hostPort : hostPort.substring(0, colonPos);
            String encoded;
            if(host.equalsIgnoreCase(request.getServerName())) {
                encoded = protocol + hostPort + addNoCookieParameters(remaining.substring(slashPos), false);
                //encoded = protocol + hostPort + response.encodeURL(remaining.substring(slashPos));
            } else {
                // Going to an different hostname, do not add request parameters
                //System.err.println("DEBUG: encodeURL: Not adding parameters to external link: "+url);
                encoded = url;
                //encoded = response.encodeURL(url);
            }
            return encoded;
        } catch(JspException err) {
            ErrorPrinter.printStackTraces(err);
            return url;
        } catch(IOException err) {
            ErrorPrinter.printStackTraces(err);
            return url;
        } catch(SQLException err) {
            ErrorPrinter.printStackTraces(err);
            return url;
        }
    }

    /**
     * @deprecated  Please use encodeRedirectURL.
     *
     * @see  #encodeRedirectURL(String)
     */
    @Override
    public String encodeRedirectUrl(String url) {
        return encodeRedirectURL(url);
    }

    @Override
    public String encodeRedirectURL(String url) {
        //System.err.println("DEBUG: encodeRedirectURL: "+url);
        try {
            // If starts with http:// or https:// parse out the first part of the URL, encode the path, and reassemble.
            String protocol;
            String remaining;
            if(url.length()>7 && (protocol=url.substring(0, 7)).equalsIgnoreCase("http://")) {
                remaining = url.substring(7);
            } else if(url.length()>8 && (protocol=url.substring(0, 8)).equalsIgnoreCase("https://")) {
                remaining = url.substring(8);
            } else {
                return addNoCookieParameters(url, true);
                //return response.encodeRedirectURL(url);
            }
            int slashPos = remaining.indexOf('/');
            if(slashPos==-1) {
                return addNoCookieParameters(url, true);
                //return response.encodeRedirectURL(url);
            }
            String hostPort = remaining.substring(0, slashPos);
            int colonPos = hostPort.indexOf(':');
            String host = colonPos==-1 ? hostPort : hostPort.substring(0, colonPos);
            String encoded;
            if(host.equalsIgnoreCase(request.getServerName())) {
                encoded = protocol + hostPort + addNoCookieParameters(remaining.substring(slashPos), true);
                //encoded = protocol + hostPort + response.encodeRedirectURL(remaining.substring(slashPos));
            } else {
                // Going to an different hostname, do not add request parameters
                //System.err.println("DEBUG: encodeRedirectURL: Not adding parameters to external link: "+url);
                encoded = url;
                //encoded = response.encodeRedirectURL(url);
            }
            return encoded;
        } catch(JspException err) {
            ErrorPrinter.printStackTraces(err);
            return url;
        } catch(IOException err) {
            ErrorPrinter.printStackTraces(err);
            return url;
        } catch(SQLException err) {
            ErrorPrinter.printStackTraces(err);
            return url;
        }
    }

    /**
     * @deprecated
     */
    @Override
    public void setStatus(int sc, String sm) {
        super.setStatus(sc, sm);
    }
    
    /**
     * Adds the no cookie parameters (language and layout) if needed and not already set.
     */
    private String addNoCookieParameters(String url, boolean isRedirect) throws JspException, UnsupportedEncodingException, IOException, SQLException {
        HttpSession session = request.getSession();
        if(session.isNew() || request.isRequestedSessionIdFromURL()) {
            // Don't add for certains file types
            int pos = url.indexOf('?');
            String lowerPath = (pos==-1 ? url : url.substring(0, pos)).toLowerCase(Locale.ENGLISH);
            //System.err.println("DEBUG: addNoCookieParameters: lowerPath="+lowerPath);
            if(
                !lowerPath.endsWith(".css")
                && !lowerPath.endsWith(".gif")
                && !lowerPath.endsWith(".ico")
                && !lowerPath.endsWith(".jpeg")
                && !lowerPath.endsWith(".jpg")
                && !lowerPath.endsWith(".js")
                && !lowerPath.endsWith(".png")
                && !lowerPath.endsWith(".txt")
            ) {
                // Use the default servlet container jsessionid when the user is authenticated
                if(session.getAttribute(Constants.AUTHENTICATED_AO_CONN)!=null) {
                    return isRedirect ? response.encodeRedirectURL(url) : response.encodeURL(url);
                }
                // Add the Constants.AUTHENTICATION_TARGET if needed
                String authenticationTarget = (String)session.getAttribute(Constants.AUTHENTICATION_TARGET);
                if(authenticationTarget==null) authenticationTarget = request.getParameter(Constants.AUTHENTICATION_TARGET);
                //System.err.println("DEBUG: addNoCookieParameters: authenticationTarget="+authenticationTarget);
                if(authenticationTarget!=null) url = addParamIfMissing(url, Constants.AUTHENTICATION_TARGET, authenticationTarget);

                // Only add the language if there is more than one possibility
                SiteSettings siteSettings = SiteSettings.getInstance(session.getServletContext());
                List<Skin.Language> languages = siteSettings.getLanguages(request);
                if(languages.size()>1) {
                    Locale locale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
                    if(locale!=null) {
                        String code = locale.getLanguage();
                        // Don't add if is the default language
                        Locale defaultLocale = LocaleAction.getDefaultLocale(siteSettings, request);
                        if(!code.equals(defaultLocale.getLanguage())) {
                            for(Skin.Language language : languages) {
                                if(language.getCode().equals(code)) {
                                    url = addParamIfMissing(url, "language", code);
                                    break;
                                }
                            }
                        }
                    }
                }
                // Only add the layout if there is more than one possibility
                List<Skin> skins = siteSettings.getSkins();
                if(skins.size()>1) {
                    String layout = (String)session.getAttribute(Constants.LAYOUT);
                    if(layout!=null) {
                        // Don't add if is the default layout
                        Skin defaultSkin = SkinAction.getDefaultSkin(skins, request);
                        if(!layout.equals(defaultSkin.getName())) {
                            // Make sure it is one of the allowed skins
                            for(Skin skin : skins) {
                                if(skin.getName().equals(layout)) {
                                    url = addParamIfMissing(url, "layout", layout);
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                //System.err.println("DEBUG: encodeRedirectURL: Not adding parameters to skipped type: "+url);
            }
        }
        return url;
    }
    
    /**
     * Adds a parameter if missing.
     */
    private static String addParamIfMissing(String url, String name, String value) throws UnsupportedEncodingException {
        String encodedName = URLEncoder.encode(name, "UTF-8");
        if(url.indexOf('?')==-1) {
            // No params exist, just add it
            return url+'?'+encodedName+'='+URLEncoder.encode(value, "UTF-8");
        }
        if(
            url.indexOf("?"+encodedName+'=')==-1
            && url.indexOf("&"+encodedName+'=')==-1
        ) {
            return url+"&amp;"+encodedName+'='+URLEncoder.encode(value, "UTF-8");
        }
        return url;
    }
}
