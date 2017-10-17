package com.marcdanieldialogo;

import com.marcdanieldialogo.entities.Appointment;
import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class used for sending emails.
 * 
 * @author Marc-Daniel
 */
public class JoddEmail {
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    private final String smtpServerName = "smtp.gmail.com";
    private final String imapServerName = "imap.gmail.com";
    
    // The gmail account that's going to be used to send email notifications
    private final String emailSend = "JAMEmailReminder@gmail.com";
    private final String emailSendPwd = "thunderboltx";
    
    private final String emailReceive = "JAMEmailReminder@gmail.com";
    private final String emailReceivePwd = "thunderboltx";
    
    private final String emailCC1 = "";
    private final String emailCC2 = "";

    // You will need a folder with this name or change it to another
    // existing folder
    private final String attachmentFolder = "C:\\Temp\\Attach\\";
    

    /**
     * A method that takes in an Appointment and sends an email to the receiver email address
     * that describes the appointment.
     * 
     * Standard send routine using Jodd. Jodd knows about GMail so no need to
     * include port information.
     * 
     * 
     * @param appointment An appointment object that holds the information that's to be sent by email
     */
    public void sendAppointmentEmail(Appointment appointment) {

        // Create am SMTP server object
        SmtpServer<SmtpSslServer> smtpServer = SmtpSslServer
                .create(smtpServerName)
                .authenticateWith(emailSend, emailSendPwd);

        // Display Java Mail debug conversation with the server
        smtpServer.debug(true);

        // Using the fluent style of coding create a plain text message
        Email email = Email.create().from(emailSend).to(emailReceive)
                .subject("JAM reminder - " + appointment.getTitle())
                .addText("Hello, you have an upcoming appointment.\n"
                + "Appointment title: " + appointment.getTitle()+"\n"
                + "Location: " + appointment.getLocation()+"\n"
                + "Details: " + appointment.getDetails()+"\n"
                + "Start time: " + appointment.getStartTime().toString()+"\n");

        // A session is the object responsible for communicating with the server
        SendMailSession session = smtpServer.createSession();

        // Like a file we open the session, send the message and close the
        // session
        session.open();
        session.sendMail(email);
        session.close();

    }
   
}
