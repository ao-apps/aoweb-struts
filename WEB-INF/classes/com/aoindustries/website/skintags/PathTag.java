package com.aoindustries.website.skintags;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.encoding.MediaType;
import com.aoindustries.io.StringBuilderWriter;
import com.aoindustries.taglib.AutoEncodingBufferedTag;
import com.aoindustries.taglib.ParamsAttribute;
import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspTag;

/**
 * Sets the path for a page or its PathAttribute parent.
 *
 * @author  AO Industries, Inc.
 */
public class PathTag extends AutoEncodingBufferedTag implements ParamsAttribute {

    private static final long serialVersionUID = 1L;

    private SortedMap<String,List<String>> params;

    public MediaType getContentType() {
        return MediaType.URL;
    }

    public MediaType getOutputType() {
        return null;
    }

    public Map<String,List<String>> getParams() {
        if(params==null) return Collections.emptyMap();
        return params;
    }

    public void addParam(String name, String value) {
        if(params==null) params = new TreeMap<String,List<String>>();
        List<String> values = params.get(name);
        if(values==null) params.put(name, values = new ArrayList<String>());
        values.add(value);
    }

    protected void doTag(StringBuilderWriter capturedBody, Writer out) throws IOException {
        String path = capturedBody.toString().trim();
        if(params!=null) {
            boolean hasQuestion = path.indexOf('?')!=-1;
            StringBuilder sb = new StringBuilder(path);
            for(Map.Entry<String,List<String>> entry : params.entrySet()) {
                String encodedName = URLEncoder.encode(entry.getKey(), "UTF-8");
                for(String value : entry.getValue()) {
                    if(hasQuestion) sb.append('&');
                    else {
                        sb.append('?');
                        hasQuestion = true;
                    }
                    sb.append(encodedName).append('=').append(URLEncoder.encode(value, "UTF-8"));
                }
            }
            path = sb.toString();
        }
        JspTag parent = findAncestorWithClass(this, PathAttribute.class);
        if(parent==null) {
            PageAttributesBodyTag.getPageAttributes((PageContext)getJspContext()).setPath(path);
        } else {
            PathAttribute pathAttribute = (PathAttribute)parent;
            pathAttribute.setPath(path);
        }
    }
}
