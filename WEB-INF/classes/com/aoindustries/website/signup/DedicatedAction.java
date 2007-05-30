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
import com.aoindustries.aoserv.client.PackageDefinitionLimit;
import com.aoindustries.sql.SQLUtility;
import com.aoindustries.website.RootAOServConnector;
import com.aoindustries.website.Skin;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class DedicatedAction extends DedicatedStepAction {

    public ActionForward executeDedicatedStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        DedicatedSignupSelectServerForm dedicatedSignupSelectServerForm,
        boolean dedicatedSignupSelectServerFormComplete,
        DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm,
        boolean dedicatedSignupCustomizeServerFormComplete,
        SignupBusinessForm signupBusinessForm,
        boolean signupBusinessFormComplete,
        SignupTechnicalForm signupTechnicalForm,
        boolean signupTechnicalFormComplete,
        SignupBillingInformationForm signupBillingInformationForm,
        boolean signupBillingInformationFormComplete
    ) throws Exception {
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(getServlet().getServletContext());
        PackageCategory category = rootConn.packageCategories.get(PackageCategory.DEDICATED);
        Business rootBusiness = rootConn.getThisBusinessAdministrator().getUsername().getPackage().getBusiness();
        List<PackageDefinition> packageDefinitions = rootBusiness.getPackageDefinitions(category);
        List<Server> servers = new ArrayList<Server>();
        
        for(PackageDefinition packageDefinition : packageDefinitions) {
            if(packageDefinition.isActive()) {
                servers.add(
                    new Server(
                        ServerConfiguration.getMinimumConfiguration(packageDefinition),
                        ServerConfiguration.getMaximumConfiguration(packageDefinition)
                    )
                );
            }
        }

        request.setAttribute("servers", servers);

        return mapping.findForward("input");
    }
    
    public static class Server {
        final private ServerConfiguration minimumConfiguration;
        final private ServerConfiguration maximumConfiguration;

        private Server(
            ServerConfiguration minimumConfiguration,
            ServerConfiguration maximumConfiguration
        ) {
            this.minimumConfiguration = minimumConfiguration;
            this.maximumConfiguration = maximumConfiguration;
        }

        public ServerConfiguration getMinimumConfiguration() {
            return minimumConfiguration;
        }

        public ServerConfiguration getMaximumConfiguration() {
            return maximumConfiguration;
        }
    }
}
