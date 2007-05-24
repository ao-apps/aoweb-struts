package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.PackageCategory;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.util.WrappedException;
import com.aoindustries.website.RootAOServConnector;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionServlet;

/**
 * @author  AO Industries, Inc.
 */
public class DedicatedSignupSelectServerForm extends ActionForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private int packageDefinition;

    public DedicatedSignupSelectServerForm() {
        setPackageDefinition(-1);
    }

    public int getPackageDefinition() {
        return packageDefinition;
    }
    
    public void setPackageDefinition(int packageDefinition) {
        this.packageDefinition = packageDefinition;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        try {
            ActionErrors errors = new ActionErrors();
            boolean found = false;

            // Must be one of the active package_definitions
            ActionServlet servlet = getServlet();
            if(servlet!=null) {
                AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(servlet.getServletContext());
                PackageCategory category = rootConn.packageCategories.get(PackageCategory.DEDICATED);
                Business rootBusiness = rootConn.getThisBusinessAdministrator().getUsername().getPackage().getBusiness();

                PackageDefinition pd = rootConn.packageDefinitions.get(packageDefinition);
                if(pd==null || !pd.getPackageCategory().equals(category) || !pd.getBusiness().equals(rootBusiness)) {
                    errors.add("packageDefinition", new ActionMessage("dedicatedSignupSelectServerForm.packageDefinition.required"));
                }
            }
            return errors;
        } catch(IOException err) {
            throw new WrappedException(err);
        }
    }
}
