package com.aoindustries.website;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.encoding.MediaType;
import com.aoindustries.util.i18n.ModifiableResourceBundle;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class SetResourceBundleMediaTypeAction extends SkinAction {

    @Override
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Locale userLocale,
        Skin skin
    ) throws Exception {
        // If disabled, return 404 status
        if(!siteSettings.getCanEditResources()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        String baseName = SetResourceBundleValueAction.getUTF8Parameter(request, "baseName");
        String key = SetResourceBundleValueAction.getUTF8Parameter(request, "key");
        String mediaTypeParam = SetResourceBundleValueAction.getUTF8Parameter(request, "mediaType");
        // Determine the MediaType and isBlockElement
        MediaType mediaType = null;
        Boolean isBlockElement = null;
        //System.out.println("DEBUG: mediaTypeParam="+mediaTypeParam);
        for(MediaType mt : MediaType.values()) {
            String mtVal = mt.getMediaType().replace('+', ' '); // Losing + sign from XMLHttpRequest call
            if(mt==MediaType.XHTML) {
                // Special treatment for isBlockElement
                if((mtVal+" (inline)").equals(mediaTypeParam)) {
                    mediaType = mt;
                    isBlockElement = false;
                    break;
                } else if((mtVal+" (block)").equals(mediaTypeParam)) {
                    mediaType = mt;
                    isBlockElement = true;
                    break;
                }
            } else {
                if(mtVal.equals(mediaTypeParam)) {
                    mediaType = mt;
                    // isBlockElement remains null
                    break;
                }
            }
        }
        //System.out.println("DEBUG: mediaType="+mediaType);
        if(mediaType==null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        // Find the bundle
        Locale locale = new Locale(""); // Locale.BASE in Java 1.6
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
        if(!resourceBundle.getLocale().equals(locale)) throw new AssertionError("resourceBundle.locale!=locale");
        if(!(resourceBundle instanceof ModifiableResourceBundle)) throw new AssertionError("resourceBundle is not a ModifiableResourceBundle");
        ((ModifiableResourceBundle)resourceBundle).setMediaType(key, mediaType, isBlockElement);

        // Set request parameters
        request.setAttribute("baseName", baseName);
        request.setAttribute("key", key);
        request.setAttribute("mediaType", mediaTypeParam);

        // Return success
        return mapping.findForward("success");
    }
}
