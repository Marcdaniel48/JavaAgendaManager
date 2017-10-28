package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.SMTPSettings;
import com.marcdanieldialogo.persistence.SMTPSettingsDAO;
import com.marcdanieldialogo.persistence.SMTPSettingsDAOImpl;
import java.sql.SQLException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMTPSettingsFormController {
    
    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

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
    
    @FXML // fx:id="defaultCheckBox"
    private CheckBox defaultCheckBox; // Value injected by FXMLLoader
    
    // Does my logging
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    // These are used to manipulate and access the SMTPSettings table of the JAM database
    private SMTPSettingsDAO smtpDAO;
    private SMTPSettings smtp;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        idTextField.setEditable(false);
    }
    
    private void doBindings()
    {
        Bindings.bindBidirectional(idTextField.textProperty(), smtp.smtpIDProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(usernameTextField.textProperty(), smtp.usernameProperty());
        Bindings.bindBidirectional(emailTextField.textProperty(), smtp.emailProperty());
        Bindings.bindBidirectional(emailPasswordTextField.textProperty(), smtp.emailPasswordProperty());
        Bindings.bindBidirectional(smtpUrlTextField.textProperty(), smtp.smtpURLProperty());
        Bindings.bindBidirectional(smtpPortTextField.textProperty(), smtp.smtpPortProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(reminderIntervalTextField.textProperty(), smtp.reminderIntervalProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(defaultCheckBox.selectedProperty(), smtp.defaultSMTPProperty());
    }

    /**
     * Takes in the values of the input fields, and inserts an SMTPSettings record into the database
     * @param event 
     */
    @FXML
    void handleCreate(ActionEvent event) 
    {
        try
        {
            int records = smtpDAO.create(smtp);
            log.info("Records created: " + records);
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
    void handleUpdate(ActionEvent event) 
    {
        try
        {
            int records = smtpDAO.update(smtp);
            log.info("Records updated: " + records);
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
           int records = smtpDAO.delete(smtp.getSMTPID());
           log.info("Records deleted: " + records);
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
        smtp.setSMTPID(-1);
        smtp.setUsername("");
        smtp.setEmail("");
        smtp.setEmailPassword("");
        smtp.setSMTPURL("");
        smtp.setSMTPPort(0);
        smtp.setReminderInterval(0);
        smtp.setDefaultSMTP(false);
    }

    /**
     * Sets the input fields to the next SMTPSettings record in the database, relative to this class' currentSMTP object
     * @param event 
     */
    @FXML
    void handleNext(ActionEvent event) {
        try
        {
            smtpDAO.findNextByID(smtp);
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
            smtpDAO.findPrevByID(smtp);
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
    
    public void setSMTPSettings(SMTPSettingsDAO smtpDAO, SMTPSettings smtp)
    {
        this.smtp = smtp;
        doBindings();
        try 
        {
            this.smtpDAO = smtpDAO;
            smtpDAO.findNextByID(smtp);
        } 
        catch (SQLException ex) 
        {
            log.error("SQL Error", ex);
        }
    }
}
