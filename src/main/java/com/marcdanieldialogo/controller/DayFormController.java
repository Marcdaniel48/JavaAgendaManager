/**
 * Sample Skeleton for 'DayForm.fxml' Controller Class
 */

package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.Appointment;
import com.marcdanieldialogo.entities.TimestampBean;
import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
    private ComboBox<Integer> wholeDayComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="groupComboBox"
    private ComboBox<String> groupComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="alarmReminderComboBox"
    private ComboBox<Integer> alarmReminderComboBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="appFormStatusLabel"
    private Label appFormStatusLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader
    
    @FXML // fx:id="idTextField"
    private TextField idTextField; // Value injected by FXMLLoader
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private LocalDate day;
    private final TimestampBean tspStart = new TimestampBean();
    private final TimestampBean tspEnd = new TimestampBean();
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        Bindings.bindBidirectional(startDatePicker.valueProperty(), tspStart.dateFieldProperty());
        Bindings.bindBidirectional(endDatePicker.valueProperty(), tspEnd.dateFieldProperty());
        
        
    }
    
    public void setDay(LocalDate day)
    {
        this.day = day;
        tspStart.setDateField(day);
        tspEnd.setDateField(day);
        
        wholeDayComboBox.getItems().addAll(1, 0);
        groupComboBox.getItems().addAll("work/school", "events", "personal", "urgent", "other");
        alarmReminderComboBox.getItems().addAll(1, 0);
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
                wholeDayComboBox.setValue(0);
            if(groupComboBox.getValue() == null)
                groupComboBox.setValue("");
            if(alarmReminderComboBox.getValue() == null)
                alarmReminderComboBox.setValue(0);
            
            if(wholeDayComboBox.getValue() == 1)
                appointment.setWholeDay(true);
            else
                appointment.setWholeDay(false);

            if("work/school".equals(groupComboBox.getValue()))
                appointment.setAppointmentGroup(1);
            else if("events".equals(groupComboBox.getValue()))
                appointment.setAppointmentGroup(2);
            else if("personal".equals(groupComboBox.getValue()))
                appointment.setAppointmentGroup(3);
            else if("urgent".equals(groupComboBox.getValue()))
                appointment.setAppointmentGroup(4);
            else
                appointment.setAppointmentGroup(5);

            if(alarmReminderComboBox.getValue() == 1)
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
        
        wholeDayComboBox.setValue(0);
        groupComboBox.setValue("other");
        alarmReminderComboBox.setValue(0);
    }
    
    public void handleDelete()
    {
        
    }
    
    @FXML
    void handleUpdate(ActionEvent event) {

    }
}
