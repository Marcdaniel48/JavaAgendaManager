package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.SMTPSettings;
import com.marcdanieldialogo.persistence.SMTPSettingsDAO;
import com.marcdanieldialogo.persistence.SMTPSettingsDAOImpl;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMTPSettingsFormController {
    
    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="defaultSmtpComboBox"
    private ComboBox<Integer> defaultSmtpComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="idTextField"
    private TextField idTextField; // Value injected by FXMLLoader

    @FXML // fx:id="usernameTextField"
    private TextField usernameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="emailTextField"
    private TextField emailTextField; // Value injected by FXMLLoader

    @FXML // fx:id="emailPasswordTextField"
    private TextField emailPasswordTextField; // Value injected by FXMLLoader

    @FXML // fx:id="smtpUrlTextField"
    private TextField smtpUrlTextField; // Value injected by FXMLLoader

    @FXML // fx:id="smtpPortTextField"
    private TextField smtpPortTextField; // Value injected by FXMLLoader

    @FXML // fx:id="reminderIntervalTextField"
    private TextField reminderIntervalTextField; // Value injected by FXMLLoader
    
    // Does my logging
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    // These are used to manipulate and access the SMTPSettings table of the JAM database
    private final SMTPSettingsDAO smtpDAO = new SMTPSettingsDAOImpl();
    private SMTPSettings currentSMTP;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        // Gives the default SMTP combo box values
        defaultSmtpComboBox.getItems().addAll(1, 0);
        try
        {
            currentSMTP = smtpDAO.findAll().get(0);
            
            displayCurrentSMTP();
        }
        catch(SQLException ex)
        {
            log.error("SQLException with AppointmentDAO.findAll probably", ex);
        }

    }
    
    /**
     * Sets the input fields to the values of this class' currentSMTP object
     */
    private void displayCurrentSMTP()
    {
        // Fills the combo box with the names of the group records
        try 
        {
            // Because idTextField takes in a String, the appointment id is casted into a such
            idTextField.setText(currentSMTP.getSMTPID()+"");
            usernameTextField.setText(currentSMTP.getUsername());
            emailTextField.setText(currentSMTP.getEmail());
            emailPasswordTextField.setText(currentSMTP.getEmailPassword());
            smtpUrlTextField.setText(currentSMTP.getSMTPURL());
            smtpPortTextField.setText(currentSMTP.getSMTPPort()+"");
            reminderIntervalTextField.setText(currentSMTP.getReminderInterval()+"");
            defaultSmtpComboBox.setValue(currentSMTP.getDefaultSMTP());
            
            smtpDAO.update(currentSMTP);           
        }
        catch(SQLException ex)
        {
            log.error("SQLException with AppointmentDAO.findAll probably", ex);
        }
    }

    /**
     * Takes in the values of the input fields, and inserts an SMTPSettings record into the database
     * @param event 
     */
    @FXML
    void handleCreate(ActionEvent event) {
        try{
            SMTPSettings newSMTP = new SMTPSettings();

            newSMTP.setUsername(usernameTextField.getText());
            newSMTP.setEmail(emailTextField.getText());
            newSMTP.setEmailPassword(emailPasswordTextField.getText());
            newSMTP.setSMTPURL(smtpUrlTextField.getText());
            newSMTP.setSMTPPort(Integer.parseInt(smtpPortTextField.getText()));
            newSMTP.setReminderInterval(Integer.parseInt(reminderIntervalTextField.getText()));;
            
            if(defaultSmtpComboBox.getValue() == 1)
                newSMTP.setDefaultSMTP(1);
            else
                newSMTP.setDefaultSMTP(0);

            smtpDAO.create(newSMTP);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    /**
     * Uses the values of the input fields to update the SMTPSettings record in the database with the same ID as the ID
     * set in the ID text field
     * @param event 
     */
    @FXML
    void handleUpdate(ActionEvent event) {
        try{
            SMTPSettings updatedSMTP = new SMTPSettings();
            updatedSMTP.setSMTPID(currentSMTP.getSMTPID());
            updatedSMTP.setUsername(usernameTextField.getText());
            updatedSMTP.setEmail(emailTextField.getText());
            updatedSMTP.setEmailPassword(emailPasswordTextField.getText());
            updatedSMTP.setSMTPURL(smtpUrlTextField.getText());
            updatedSMTP.setSMTPPort(Integer.parseInt(smtpPortTextField.getText()));
            updatedSMTP.setReminderInterval(Integer.parseInt(reminderIntervalTextField.getText()));
            
            if(defaultSmtpComboBox.getValue() == 1)
                updatedSMTP.setDefaultSMTP(1);
            else
                updatedSMTP.setDefaultSMTP(0);
            
            smtpDAO.update(updatedSMTP);
            this.currentSMTP = updatedSMTP;
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }

    /**
     * Deletes a record of the SMTPSettings table, depending on the ID value set in the ID text field
     * @param event 
     */
    @FXML
    void handleDelete(ActionEvent event) {

        try 
        {
            smtpDAO.delete(Integer.parseInt(idTextField.getText()));
        } 
        catch (SQLException ex) 
        {
            log.error("SQLException - Something went wrong. Was a correct ID entered?", ex);
        }
    }
    
    /**
     * Clears the input fields of the form
     * @param event 
     */
    @FXML
    void handleClear(ActionEvent event) {
        usernameTextField.setText("");
        emailTextField.setText("");
        emailPasswordTextField.setText("");
        smtpUrlTextField.setText("");
        smtpPortTextField.setText("");
        reminderIntervalTextField.setText("");
        defaultSmtpComboBox.setValue(0);
    }

    /**
     * Sets the input fields to the next SMTPSettings record in the database, relative to this class' currentSMTP object
     * @param event 
     */
    @FXML
    void handleNext(ActionEvent event) {
        try
        {
            if(!(idTextField.getText().isEmpty()))
            {
                this.currentSMTP = smtpDAO.findID(Integer.parseInt(idTextField.getText()) + 1);
                displayCurrentSMTP();
            }
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }

    /**
     * Sets the input fields to the previous SMTPSettings record in the database, relative to this class' currentSMTP object
     * @param event 
     */
    @FXML
    void handlePrevious(ActionEvent event) {
        try
        {
            if(!(idTextField.getText().isEmpty()))
            {
                this.currentSMTP = smtpDAO.findID(Integer.parseInt(idTextField.getText()) - 1);
                displayCurrentSMTP();
            }
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    /**
     * Closes the window
     * @param event 
     */
    @FXML
    void handleExit(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
