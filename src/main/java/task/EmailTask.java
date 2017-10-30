package task;

import com.marcdanieldialogo.email.JoddEmail;
import com.marcdanieldialogo.entities.Appointment;
import java.sql.SQLException;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Marc-Daniel
 */
public class EmailTask implements Runnable
{
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final JoddEmail emailSender;
    
    public EmailTask()
    {
        emailSender = new JoddEmail();
    }

    @Override
    public void run() 
    {
        // Platform.runLater() allows this thread to interact with the main
        // JavaFX thread and therefore interact with controls.
        Platform.runLater(() -> {
            emailSender.setDefaultSMTP();
            try 
            {
                // Get a list of all appointments that are within the current default SMTP settings interval
                List<Appointment> appointmentList = emailSender.findEmailsByInterval();
                
                // Emails should only be sent if the appointment list is NOT empty.
                if(!appointmentList.isEmpty())
                {
                    emailSender.sendEmailList(emailSender.findEmailsByInterval());
                    
                    log.info("Sent an email");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Email Sender");
                    alert.setHeaderText("Attempting to send emails.");
                    alert.setContentText("Emails for any appointments that both meet the current reminder interval & have email reminder set to true have been sent.");
                    alert.showAndWait();
                }
            } 
            catch (SQLException ex) 
            {
                log.error("Error with sending email list in thread", ex);
            }
        });
    }
}
