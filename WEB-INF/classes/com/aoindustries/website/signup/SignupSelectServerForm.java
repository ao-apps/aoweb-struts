package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.PackageCategory;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.util.WrappedException;
import com.aoindustries.website.SiteSettings;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionServlet;

/**
 * @author  AO Industries, Inc.
 */
abstract public class SignupSelectServerForm extends ActionForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private int packageDefinition;

    public SignupSelectServerForm() {
        setPackageDefinition(-1);
    }

    public int getPackageDefinition() {
        return packageDefinition;
    }
    
    public void setPackageDefinition(int packageDefinition) {
        this.packageDefinition = packageDefinition;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if(errors==null) errors = new ActionErrors();
        try {
            boolean found = false;

            // Must be one of the active package_definitions
            ActionServlet myServlet = getServlet();
            if(myServlet!=null) {
                AOServConnector rootConn = SiteSettings.getInstance(myServlet.getServletContext()).getRootAOServConnector();
                PackageCategory category = rootConn.getPackageCategories().get(getPackageCategory());
                Business rootBusiness = rootConn.getThisBusinessAdministrator().getUsername().getPackage().getBusiness();

                PackageDefinition pd = rootConn.getPackageDefinitions().get(packageDefinition);
                if(pd==null || !pd.getPackageCategory().equals(category) || !pd.getBrand().getBusiness().equals(rootBusiness)) {
                    errors.add("packageDefinition", new ActionMessage("signupSelectServerForm.packageDefinition.required"));
                }
            }
            return errors;
        } catch(IOException err) {
            throw new WrappedException(err);
        } catch(SQLException err) {
            throw new WrappedException(err);
        }
    }
    
    abstract protected String getPackageCategory();
}
