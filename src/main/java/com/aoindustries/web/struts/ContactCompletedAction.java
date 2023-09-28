/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2000-2009, 2015, 2016, 2018, 2021, 2022, 2023  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.aoindustries.web.struts;

import com.aoapps.lang.Strings;
import com.aoapps.net.Email;
import com.aoapps.web.resources.registry.Registry;
import com.aoindustries.aoserv.client.AoservConnector;
import com.aoindustries.aoserv.client.ticket.Language;
import com.aoindustries.aoserv.client.ticket.Priority;
import com.aoindustries.aoserv.client.ticket.Status;
import com.aoindustries.aoserv.client.ticket.Ticket;
import com.aoindustries.aoserv.client.ticket.TicketTable;
import com.aoindustries.aoserv.client.ticket.TicketType;
import com.google.cloud.recaptchaenterprise.v1.RecaptchaEnterpriseServiceClient;
import com.google.recaptchaenterprise.v1.Assessment;
import com.google.recaptchaenterprise.v1.CreateAssessmentRequest;
import com.google.recaptchaenterprise.v1.Event;
import com.google.recaptchaenterprise.v1.RiskAnalysis.ClassificationReason;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Locale;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class ContactCompletedAction extends PageAction {

  private static final Logger logger = Logger.getLogger(ContactCompletedAction.class.getName());

  /**
   * The action value used for reCAPTCHA.
   */
  public static final String RECAPTCHA_ACTION = "CONTACT";

  /**
   * The score at or below which requests will be reject. See
   * <a href="https://cloud.google.com/recaptcha-enterprise/docs/interpret-assessment">Interpreting an assessment</a>.
   */
  private static final float RECAPTCHA_FORBID_SCORE = 0.11F; // A bit higher than 0.1 for floating point precision

  /**
   * The score at or below which requests will be added, but immediately marked as {@link Status#JUNK}.  See
   * <a href="https://cloud.google.com/recaptcha-enterprise/docs/interpret-assessment">Interpreting an assessment</a>.
   */
  private static final float RECAPTCHA_JUNK_SCORE = 0.51F; // A bit higher than 0.5 for floating point precision

  static {
    assert RECAPTCHA_JUNK_SCORE > RECAPTCHA_FORBID_SCORE;
  }

  @Override
  public ActionForward execute(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response,
      Registry pageRegistry
  ) throws Exception {
    ContactForm contactForm = (ContactForm) form;

    // Check if logged-in
    AoservConnector authenticatedAoConn = AuthenticatedAction.getAuthenticatedAoConn(request, response);

    // Check reCAPTCHA first when not logged-in
    boolean isJunk;
    float recaptchaScore;
    if (authenticatedAoConn != null) {
      isJunk = false;
      recaptchaScore = Float.NaN;
    } else {
      // See https://cloud.google.com/recaptcha-enterprise/docs/create-assessment#java
      String token = Strings.nullIfEmpty(request.getParameter("g-recaptcha-response"));
      if (token == null) {
        logger.info("no reCAPTCHA token");
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return null;
      }
      ServletContext servletContext = getServlet().getServletContext();
      // Initialize client that will be used to send requests.
      try (RecaptchaEnterpriseServiceClient recaptchaEnterpriseServiceClient =
          RecaptchaEnterpriseServiceClient.create(ReCaptcha.createSettings(servletContext))) {
        // Set the properties of the event to be tracked.
        Event event = Event.newBuilder()
            .setSiteKey(ReCaptcha.getSitekey(servletContext))
            .setExpectedAction(RECAPTCHA_ACTION)
            .setToken(token)
            .build();
        // Build the assessment request.
        CreateAssessmentRequest createAssessmentRequest =
            CreateAssessmentRequest.newBuilder()
                .setParent(ReCaptcha.getProjectName(servletContext).toString())
                .setAssessment(Assessment.newBuilder().setEvent(event).build())
                .build();
        Assessment assessment = recaptchaEnterpriseServiceClient.createAssessment(createAssessmentRequest);
        // Check if the token is valid.
        if (!assessment.getTokenProperties().getValid()) {
          logger.info(() -> "The CreateAssessment call failed because the token was: "
              + assessment.getTokenProperties().getInvalidReason().name());
          response.sendError(HttpServletResponse.SC_FORBIDDEN);
          return null;
        }
        // Check if the expected action was executed.
        if (!RECAPTCHA_ACTION.equals(assessment.getTokenProperties().getAction())) {
          logger.info(() -> "The action attribute in reCAPTCHA tag is: "
              + assessment.getTokenProperties().getAction() + System.lineSeparator()
              + "The action attribute in the reCAPTCHA tag "
              + "does not match the action ("
              + RECAPTCHA_ACTION
              + ") you are expecting to score");
          response.sendError(HttpServletResponse.SC_FORBIDDEN);
          return null;
        }
        // Get the reason(s) and the risk score.
        // For more information on interpreting the assessment,
        // see: https://cloud.google.com/recaptcha-enterprise/docs/interpret-assessment
        for (ClassificationReason reason : assessment.getRiskAnalysis().getReasonsList()) {
          logger.info(reason::toString);
        }
        recaptchaScore = assessment.getRiskAnalysis().getScore();
        logger.info(() -> "The reCAPTCHA score is: " + recaptchaScore);
        // Get the assessment name (id). Use this to annotate the assessment.
        // TODO: Store assessment id then give feedback back into reCAPTCHA when change ticket status?
        // This would require aoserv-master being able to make API calls on behalf of each brand.
        // Only do once we get false negative/positives and need to correct.
        String assessmentName = assessment.getName();
        logger.info(() -> "Assessment name: " + assessmentName.substring(assessmentName.lastIndexOf('/') + 1));
        if (recaptchaScore <= RECAPTCHA_FORBID_SCORE) {
          logger.info(() -> "recaptchaScore <= RECAPTCHA_FORBID_SCORE, forbidding request: "
              + recaptchaScore + " <= " + RECAPTCHA_FORBID_SCORE);
          response.sendError(HttpServletResponse.SC_FORBIDDEN);
          return null;
        }
        isJunk = recaptchaScore <= RECAPTCHA_JUNK_SCORE;
        if (isJunk) {
          logger.info(() -> "recaptchaScore <= RECAPTCHA_JUNK_SCORE, will mark new ticket as " + Status.JUNK + ": "
              + recaptchaScore + " <= " + RECAPTCHA_JUNK_SCORE);
        }
      }
    }

    // Validation
    ActionMessages errors = contactForm.validate(mapping, request);
    if (errors != null && !errors.isEmpty()) {
      saveErrors(request, errors);
      return mapping.findForward("input");
    }

    SiteSettings siteSettings = SiteSettings.getInstance(getServlet().getServletContext());
    AoservConnector rootConn = siteSettings.getRootAoservConnector();
    Locale locale = response.getLocale();
    Language language = rootConn.getTicket().getLanguage().get(locale.getLanguage());
    if (language == null) {
      language = rootConn.getTicket().getLanguage().get(Language.EN);
      if (language == null) {
        throw new SQLException("Unable to find Language: " + Language.EN);
      }
    }
    TicketType ticketType = rootConn.getTicket().getTicketType().get(TicketType.CONTACT);
    if (ticketType == null) {
      throw new SQLException("Unable to find TicketType: " + TicketType.CONTACT);
    }
    Priority clientPriority = rootConn.getTicket().getPriority().get(Priority.NORMAL);
    if (clientPriority == null) {
      throw new SQLException("Unable to find Priority: " + Priority.NORMAL);
    }
    Email from = Email.valueOf(contactForm.getFrom());
    TicketTable tickets = rootConn.getTicket().getTicket();
    String subject = contactForm.getSubject();
    if (isJunk) {
      subject = "[reCAPTCHA score " + recaptchaScore + "] " + subject;
    }
    // TODO: Way to set status while creating ticket, instead of creating ticket and setting status in separate step
    int ticketId = tickets.addTicket(
        siteSettings.getBrand(),
        null, // account
        language,
        null, // category
        ticketType,
        from,
        subject,
        contactForm.getMessage(),
        clientPriority,
        Collections.singleton(from),
        ""
    );
    if (isJunk) {
      Ticket ticket = tickets.get(ticketId);
      if (ticket == null) {
        throw new SQLException("Unable to find Ticket: " + ticketId);
      }
      Status junkStatus = rootConn.getTicket().getStatus().get(Status.JUNK);
      if (junkStatus == null) {
        throw new SQLException("Unable to find Status: " + Status.JUNK);
      }
      ticket.setStatus(ticket.getStatus(), junkStatus, -1);
    }

    return mapping.findForward("success");
  }
}
