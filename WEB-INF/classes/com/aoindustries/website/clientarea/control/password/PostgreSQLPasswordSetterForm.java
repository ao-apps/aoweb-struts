package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2008 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.PasswordChecker;
import com.aoindustries.aoserv.client.PostgresUser;
import com.aoindustries.util.AutoGrowArrayList;
import com.aoindustries.util.WrappedException;
import com.aoindustries.website.AuthenticatedAction;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author Dan Armstrong &lt;dan@aoindustries.com&gt;
 */
public class PostgreSQLPasswordSetterForm extends ActionForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> packages;
    private List<String> usernames;
    private List<String> postgreSQLServers;
    private List<String> aoServers;
    private List<String> newPasswords;
    private List<String> confirmPasswords;

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setPackages(new AutoGrowArrayList<String>());
        setUsernames(new AutoGrowArrayList<String>());
        setPostgreSQLServers(new AutoGrowArrayList<String>());
        setAoServers(new AutoGrowArrayList<String>());
        setNewPasswords(new AutoGrowArrayList<String>());
        setConfirmPasswords(new AutoGrowArrayList<String>());
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public List<String> getUsernames() {
        return usernames;
    }
    
    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    public List<String> getPostgreSQLServers() {
        return postgreSQLServers;
    }
    
    public void setPostgreSQLServers(List<String> postgreSQLServers) {
        this.postgreSQLServers = postgreSQLServers;
    }

    
    public List<String> getAoServers() {
        return aoServers;
    }
    
    public void setAoServers(List<String> aoServers) {
        this.aoServers = aoServers;
    }

    public List<String> getNewPasswords() {
        return newPasswords;
    }

    public void setNewPasswords(List<String> newPasswords) {
        this.newPasswords = newPasswords;
    }

    public List<String> getConfirmPasswords() {
        return confirmPasswords;
    }

    public void setConfirmPasswords(List<String> confirmPasswords) {
        this.confirmPasswords = confirmPasswords;
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if(errors==null) errors = new ActionErrors();
        AOServConnector aoConn = AuthenticatedAction.getAoConn(request, null);
        if(aoConn==null) throw new RuntimeException("aoConn is null");
        Locale locale = (Locale)request.getSession().getAttribute(Globals.LOCALE_KEY);

        for(int c=0;c<usernames.size();c++) {
            String newPassword = newPasswords.get(c);
            String confirmPassword = confirmPasswords.get(c);
            if(!newPassword.equals(confirmPassword)) {
                errors.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage("password.postgreSQLPasswordSetter.field.confirmPasswords.mismatch"));
            } else {
                if(newPassword.length()>0) {
                    String username = usernames.get(c);

                    // Check the password strength
                    PasswordChecker.Result[] results = PostgresUser.checkPassword(locale, username, newPassword);
                    if(PasswordChecker.hasResults(locale, results)) {
                        errors.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage(PasswordChecker.getResultsHtml(results), false));
                    }
                }
            }
        }
        return errors;
    }
}
