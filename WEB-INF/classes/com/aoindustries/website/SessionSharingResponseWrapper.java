package com.aoindustries.website;

/*
 * Copyright 2007-2008 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author  AO Industries, Inc.
 */
public class SessionSharingResponseWrapper extends HttpServletResponseWrapper {

    final private HttpServletResponse response;
    final private String serverName;

    public SessionSharingResponseWrapper(HttpServletResponse response, String serverName) {
        super(response);
        this.response = response;
        this.serverName = serverName;
    }

    /**
     * @deprecated  Please use encodeURL.
     *
     * @see  #encodeURL(String)
     */
    public String encodeUrl(String url) {
        return encodeURL(url);
    }

    public String encodeURL(final String url) {
        //System.out.println("DEBUG: url="+url);
        // If starts with http:// or https:// parse out the first part of the URL, encode the path, and reassemble.
        String protocol;
        String remaining;
        if(url.length()>7 && (protocol=url.substring(0, 7)).equalsIgnoreCase("http://")) {
            remaining = url.substring(7);
        } else if(url.length()>8 && (protocol=url.substring(0, 8)).equalsIgnoreCase("https://")) {
            remaining = url.substring(8);
        } else {
            return response.encodeURL(url);
        }
        //System.out.println("DEBUG: protocol="+protocol);
        //System.out.println("DEBUG: remaining="+remaining);
        int slashPos = remaining.indexOf('/');
        if(slashPos==-1) return response.encodeURL(url);
        String hostPort = remaining.substring(0, slashPos);
        int colonPos = hostPort.indexOf(':');
        String host = colonPos==-1 ? hostPort : hostPort.substring(0, colonPos);
        //System.out.println("DEBUG: host="+host);
        String encoded;
        if(host.equalsIgnoreCase(serverName)) {
            encoded = protocol + hostPort + response.encodeURL(remaining.substring(slashPos));
        } else {
            encoded = response.encodeURL(url);
        }
        //System.out.println("DEBUG: encoded="+encoded);
        return encoded;
    }

    /**
     * @deprecated  Please use encodeRedirectURL.
     *
     * @see  #encodeRedirectURL(String)
     */
    public String encodeRedirectUrl(String url) {
        return encodeRedirectURL(url);
    }

    public String encodeRedirectURL(String url) {
        //System.out.println("DEBUG: url="+url);
        // If starts with http:// or https:// parse out the first part of the URL, encode the path, and reassemble.
        String protocol;
        String remaining;
        if(url.length()>7 && (protocol=url.substring(0, 7)).equalsIgnoreCase("http://")) {
            remaining = url.substring(7);
        } else if(url.length()>8 && (protocol=url.substring(0, 8)).equalsIgnoreCase("https://")) {
            remaining = url.substring(8);
        } else {
            return response.encodeRedirectURL(url);
        }
        //System.out.println("DEBUG: protocol="+protocol);
        //System.out.println("DEBUG: remaining="+remaining);
        int slashPos = remaining.indexOf('/');
        if(slashPos==-1) return response.encodeRedirectURL(url);
        String hostPort = remaining.substring(0, slashPos);
        int colonPos = hostPort.indexOf(':');
        String host = colonPos==-1 ? hostPort : hostPort.substring(0, colonPos);
        //System.out.println("DEBUG: host="+host);
        String encoded;
        if(host.equalsIgnoreCase(serverName)) {
            encoded = protocol + hostPort + response.encodeRedirectURL(remaining.substring(slashPos));
        } else {
            encoded = response.encodeRedirectURL(url);
        }
        //System.out.println("DEBUG: encoded="+encoded);
        return encoded;
    }

    /**
     * @deprecated
     */
    public void setStatus(int sc, String sm) {
        super.setStatus(sc, sm);
    }
}
