package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServer;
import com.aoindustries.aoserv.client.LinuxAccount;
import com.aoindustries.aoserv.client.PasswordChecker;
import com.aoindustries.aoserv.client.validator.UserId;
import com.aoindustries.aoserv.client.validator.ValidationException;
import com.aoindustries.util.AutoGrowArrayList;
import com.aoindustries.util.WrappedException;
import com.aoindustries.website.AuthenticatedAction;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author Dan Armstrong &lt;dan@aoindustries.com&gt;
 */
public class LinuxAccountPasswordSetterForm extends ActionForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> businesses;
    private List<String> usernames;
    private List<String> aoServers;
    private List<String> newPasswords;
    private List<String> confirmPasswords;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setBusinesses(new AutoGrowArrayList<String>());
        setUsernames(new AutoGrowArrayList<String>());
        setAoServers(new AutoGrowArrayList<String>());
        setNewPasswords(new AutoGrowArrayList<String>());
        setConfirmPasswords(new AutoGrowArrayList<String>());
    }

    public List<String> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<String> businesses) {
        this.businesses = businesses;
    }

    public List<String> getUsernames() {
        return usernames;
    }
    
    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
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
    
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        try {
            ActionErrors errors = super.validate(mapping, request);
            if(errors==null) errors = new ActionErrors();
            AOServConnector<?,?> aoConn = AuthenticatedAction.getAoConn(request, null);
            if(aoConn==null) throw new RuntimeException("aoConn is null");
            for(int c=0;c<usernames.size();c++) {
                String newPassword = newPasswords.get(c);
                String confirmPassword = confirmPasswords.get(c);
                if(!newPassword.equals(confirmPassword)) {
                    errors.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage("password.linuxAccountPasswordSetter.field.confirmPasswords.mismatch"));
                } else {
                    if(newPassword.length()>0) {
                        try {
                            String username = usernames.get(c);
                            LinuxAccount la = aoConn
                                .getAoServers()
                                .filterUnique(AOServer.COLUMN_HOSTNAME, aoServers.get(c))
                                .getLinuxAccount(UserId.valueOf(username));
                            // Check the password strength
                            PasswordChecker.Result[] results = la.getLinuxAccountType().checkPassword(username, newPassword);
                            if(PasswordChecker.hasResults(results)) {
                                errors.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage(PasswordChecker.getResultsHtml(results), false));
                            }
                        } catch(ValidationException err) {
                            errors.add("usernames[" + c + "].usernames", new ActionMessage(err.getLocalizedMessage(), false));
                        }
                    }
                }
            }
            return errors;
        } catch(IOException err) {
            throw new WrappedException(err);
        }
    }
}
