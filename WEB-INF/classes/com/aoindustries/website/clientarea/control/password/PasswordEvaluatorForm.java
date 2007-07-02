package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/**
 * @author  AO Industries, Inc.
 */
public class PasswordEvaluatorForm extends ValidatorForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String password;

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setPassword("");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
