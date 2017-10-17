/**
 * Sample Skeleton for 'DayForm.fxml' Controller Class
 */

package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.Appointment;
import com.marcdanieldialogo.entities.GroupRecord;
import com.marcdanieldialogo.entities.TimestampBean;
import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import com.marcdanieldialogo.persistence.GroupRecordDAO;
import com.marcdanieldialogo.persistence.GroupRecordDAOImpl;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayFormController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="startDatePicker"
    private DatePicker startDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="endDatePicker"
    private DatePicker endDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="titleTextField"
    private TextField titleTextField; // Value injected by FXMLLoader

    @FXML // fx:id="locationTextField"
    private TextField locationTextField; // Value injected by FXMLLoader

    @FXML // fx:id="detailsTextField"
    private TextField detailsTextField; // Value injected by FXMLLoader

    @FXML // fx:id="wholeDayComboBox"
    private ComboBox<Boolean> wholeDayComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="groupComboBox"
    private ComboBox<String> groupComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="alarmReminderComboBox"
    private ComboBox<Boolean> alarmReminderComboBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="appFormStatusLabel"
    private Label appFormStatusLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader
    
    @FXML // fx:id="idTextField"
    private TextField idTextField; // Value injected by FXMLLoader
    
    // Does my logging
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    private final TimestampBean tspStart = new TimestampBean();
    private final TimestampBean tspEnd = new TimestampBean();
    private final GroupRecordDAO groupRecordDAO = new GroupRecordDAOImpl();
    private final AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private Appointment currentAppointment;
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        Bindings.bindBidirectional(startDatePicker.valueProperty(), tspStart.dateFieldProperty());
        Bindings.bindBidirectional(endDatePicker.valueProperty(), tspEnd.dateFieldProperty());
        
        // Gives the ComboBoxes values
        wholeDayComboBox.getItems().addAll(true, false);
        alarmReminderComboBox.getItems().addAll(true, false);
        try
        {
            for(GroupRecord group : groupRecordDAO.findAll())
            {
                groupComboBox.getItems().add(group.getGroupName());
            }
            
            // Initializes the currentAppointment field to the first Appointment found in the AppointmentDAO
            currentAppointment = appointmentDAO.findAll().get(0);
        }
        catch(SQLException ex)
        {
            log.error("SQLException with AppointmentDAO.findAll probably", ex);
        }
        
        displayCurrentAppointment();
    }
    
    private void displayCurrentAppointment()
    {
        // Fills the combo box with the names of the group records
        try 
        {
            // Because idTextField takes in a String, the appointment id is casted into a such
            idTextField.setText(currentAppointment.getAppointmentID()+"");
            
            titleTextField.setText(currentAppointment.getTitle());
            locationTextField.setText(currentAppointment.getLocation());
            tspStart.setDateField(currentAppointment.getStartTime().toLocalDateTime().toLocalDate());
            tspEnd.setDateField(currentAppointment.getEndTime().toLocalDateTime().toLocalDate());
            detailsTextField.setText(currentAppointment.getDetails());
            wholeDayComboBox.setValue(currentAppointment.getWholeDay());
            
            for(GroupRecord group : groupRecordDAO.findAll())
            {
                if(group.getGroupNumber() == currentAppointment.getAppointmentGroup())
                    groupComboBox.setValue(group.getGroupName());
            }
            
            alarmReminderComboBox.setValue(currentAppointment.getAlarmReminder());
           
        }
        catch(SQLException ex)
        {
            log.error("SQLException with AppointmentDAO.findAll probably", ex);
        }
    }
    
    // Used to set the DatePickers to the same value as the passed in LocalDate parameter
    public void setDay(LocalDate day)
    {
        tspStart.setDateField(day);
        tspEnd.setDateField(day);
    }
    
    public void handleCreate()
    {
        try{
            AppointmentDAO dao = new AppointmentDAOImpl();
            Appointment appointment = new Appointment();

            appointment.setTitle(titleTextField.getText());
            appointment.setLocation(locationTextField.getText());
            appointment.setStartTime(tspStart.getTimeStampValue());
            appointment.setEndTime(tspEnd.getTimeStampValue());
            appointment.setDetails(detailsTextField.getText());

            if(wholeDayComboBox.getValue() == null)
                wholeDayComboBox.setValue(false);
            if(groupComboBox.getValue() == null)
                groupComboBox.setValue("");
            if(alarmReminderComboBox.getValue() == null)
                alarmReminderComboBox.setValue(false);
            
            if(wholeDayComboBox.getValue())
                appointment.setWholeDay(true);
            else
                appointment.setWholeDay(false);

            if(alarmReminderComboBox.getValue())
                appointment.setAlarmReminder(true);
            else
                appointment.setAlarmReminder(false);

            dao.create(appointment);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    public void handleExit()
    {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
    
    public void handleClear()
    {
        titleTextField.setText("");
        locationTextField.setText("");
        detailsTextField.setText("");
        wholeDayComboBox.setValue(false);
        groupComboBox.setValue("other");
        alarmReminderComboBox.setValue(false);
    }
    
    public void handleDelete()
    {
        try 
        {
            appointmentDAO.delete(Integer.parseInt(idTextField.getText()));
        } 
        catch (SQLException ex) 
        {
            log.error("SQLException - Something went wrong. Was a correct ID entered?", ex);
        }
    }
    
    @FXML
    void handleUpdate(ActionEvent event) {
        try{
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(Integer.parseInt(idTextField.getText()));
            appointment.setTitle(titleTextField.getText());
            appointment.setLocation(locationTextField.getText());
            appointment.setStartTime(tspStart.getTimeStampValue());
            appointment.setEndTime(tspEnd.getTimeStampValue());
            appointment.setDetails(detailsTextField.getText());

            if(wholeDayComboBox.getValue() == null)
                wholeDayComboBox.setValue(false);
            if(groupComboBox.getValue() == null)
                groupComboBox.setValue("");
            if(alarmReminderComboBox.getValue() == null)
                alarmReminderComboBox.setValue(false);
            
            if(wholeDayComboBox.getValue())
                appointment.setWholeDay(true);
            else
                appointment.setWholeDay(false);

            if(alarmReminderComboBox.getValue())
                appointment.setAlarmReminder(true);
            else
                appointment.setAlarmReminder(false);

            appointmentDAO.update(appointment);
            this.currentAppointment = appointment;
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    @FXML
    void handleNext(ActionEvent event) 
    {
        try
        {
            if(!(idTextField.getText().isEmpty()))
            {
                this.currentAppointment = appointmentDAO.findID(Integer.parseInt(idTextField.getText()) + 1);
                displayCurrentAppointment();
                appFormStatusLabel.setText("");
            }
            else
                appFormStatusLabel.setText("PLEASE ENTER AN ID");
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }

    @FXML
    void handlePrevious(ActionEvent event) {
        try
        {
            if(!(idTextField.getText().isEmpty()))
            {
                this.currentAppointment = appointmentDAO.findID(Integer.parseInt(idTextField.getText()) - 1);
                displayCurrentAppointment();
                appFormStatusLabel.setText("");
            }
            else
                appFormStatusLabel.setText("PLEASE ENTER AN ID");
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
}
