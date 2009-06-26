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
import com.aoindustries.io.ChainWriter;
import com.aoindustries.sql.SQLUtility;
import com.aoindustries.website.SiteSettings;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.util.MessageResources;

/**
 * ManagedAction and DedicatedAction both use this to setup the request attributes.  This is implemented
 * here because inheritence is not possible and neither one is logically above the other.
 *
 * @author  AO Industries, Inc.
 */
final public class SignupSelectServerActionHelper {

    /**
     * Make no instances.
     */
    private SignupSelectServerActionHelper() {}

    public static void setRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request,
        String packageCategoryName
    ) throws IOException, SQLException {
        List<Server> servers = getServers(servletContext, packageCategoryName);
        
        request.setAttribute("servers", servers);
    }

    /**
     * Gets the possible servers ordered by minimum monthly rate.
     */
    public static List<Server> getServers(ServletContext servletContext, String packageCategoryName) throws IOException, SQLException {
        AOServConnector rootConn = SiteSettings.getInstance(servletContext).getRootAOServConnector();
        PackageCategory category = rootConn.getPackageCategories().get(packageCategoryName);
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
        
        Collections.sort(servers, new ServerComparator());
        
        return servers;
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

    private static class ServerComparator implements Comparator<Server> {
        public int compare(Server s1, Server s2) {
            return s1.getMinimumConfiguration().getMonthly().compareTo(s2.getMinimumConfiguration().getMonthly());
        }
    }

    public static BigDecimal getSetup(PackageDefinition packageDefinition) {
        int setupFee = packageDefinition.getSetupFee();
        return setupFee==-1 ? null : new BigDecimal(SQLUtility.getDecimal(setupFee));
    }

    public static void setConfirmationRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request,
        SignupSelectServerForm signupSelectServerForm
    ) throws IOException, SQLException {
        // Lookup things needed by the view
        AOServConnector rootConn = SiteSettings.getInstance(servletContext).getRootAOServConnector();
        PackageDefinition packageDefinition = rootConn.getPackageDefinitions().get(signupSelectServerForm.getPackageDefinition());

        // Store as request attribute for the view
        request.setAttribute("setup", getSetup(packageDefinition));
    }
    
    public static void printConfirmation(ChainWriter emailOut, Locale contentLocale, PackageDefinition packageDefinition, MessageResources signupApplicationResources) {
        emailOut.print("    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(contentLocale, "signupSelectServerForm.packageDefinition.prompt")).print("</td>\n"
                     + "        <td>").writeHtml(packageDefinition.getDisplay()).print("</td>\n"
                     + "    </tr>\n");
    }
}
