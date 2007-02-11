package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.LinuxAccountTable;
import com.aoindustries.website.Skin;
import com.aoindustries.website.SkinAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Generates a list of passwords, stores in request attribute "generatedPasswords", and forwards to "success".
 *
 * @author  AO Industries, Inc.
 */
public class PasswordGeneratorAction extends SkinAction {

    private static final int NUM_PASSWORDS = 10;

    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin
    ) throws Exception {
        // Generate the passwords
        List<String> generatedPasswords = new ArrayList<String>(NUM_PASSWORDS);
        for(int c=0;c<10;c++) generatedPasswords.add(LinuxAccountTable.generatePassword());

        // Set request values
        request.setAttribute("generatedPasswords", generatedPasswords);

        return mapping.findForward("success");
    }
}
