package com.marcdanieldialogo.email;

import com.marcdanieldialogo.entities.Appointment;
import com.marcdanieldialogo.entities.SMTPSettings;
import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import com.marcdanieldialogo.persistence.SMTPSettingsDAO;
import com.marcdanieldialogo.persistence.SMTPSettingsDAOImpl;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jodd.mail.Email;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;

/**
 * A class used for sending emails.
 * 
 * @author Marc-Daniel
 */
public class JoddEmail {
    private String smtpServerURL;
    
    // The gmail account that's going to be used to send email notifications
    private String emailUser;
    private String emailPwd;
    SMTPSettings defaultSMTP;
    
    public JoddEmail()
    {
        try 
        {
            SMTPSettingsDAO smtpDAO = new SMTPSettingsDAOImpl();
            this.defaultSMTP = smtpDAO.findDefaultSMTP();
            
            this.emailUser = defaultSMTP.getEmail();
            this.emailPwd = defaultSMTP.getEmailPassword();
            this.smtpServerURL = defaultSMTP.getSMTPURL();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(JoddEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Finds the record in the SMTPSettings table with DEFAULT_SMTP set to true and sets the fields in this class
     * to the data stored in that record.
     */
    public void setDefaultSMTP()
    {
        try 
        {
            SMTPSettingsDAO smtpDAO = new SMTPSettingsDAOImpl();
            this.defaultSMTP = smtpDAO.findDefaultSMTP();
            
            this.emailUser = defaultSMTP.getEmail();
            this.emailPwd = defaultSMTP.getEmailPassword();
            this.smtpServerURL = defaultSMTP.getSMTPURL();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(JoddEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
    public void sendEmail(Appointment appointment) {

        // Create am SMTP server object
        SmtpServer<SmtpSslServer> smtpServer = SmtpSslServer.create(smtpServerURL).authenticateWith(emailUser, emailPwd);

        // Display Java Mail debug conversation with the server
        smtpServer.debug(true);

        // Using the fluent style of coding create a plain text message
        Email email = Email.create().from(emailUser).to(emailUser)
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
    
    /**
     * Will return any appointments that start X minutes from now & have their alarm reminder set to true.
     * X is equal to the reminder interval set in the default SMTP settings.
     * 
     * @return List<Appointment>
     * @throws SQLException 
     */
    public List<Appointment> findEmailsByInterval() throws SQLException
    {
        AppointmentDAO dao = new AppointmentDAOImpl();
        
        List<Appointment> appointments = dao.findByDate(LocalDateTime.now().plusMinutes(defaultSMTP.getReminderInterval()) );

        for(int i = 0; i < appointments.size(); i++)
        {
            if(appointments.get(i).getAlarmReminder() == false)
            {
                appointments.remove(i);
            }
        }
        
        // If no appointments are found, an empty list will be returned anyway.
        return appointments;
    }
    
    /**
     * Takes in a list of appointments and for each appointment, an email
     * describing the appointment will be sent.
     * 
     * @param mail 
     */
    public void sendEmailList(List<Appointment> mail)
    {
        // Nothing will be sent if the mail list is empty.
        if(!mail.isEmpty())
        {
            for(Appointment appointment : mail)
            {
                sendEmail(appointment);
            }
        }
    }
}
