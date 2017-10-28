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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppointmentFormController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="titleTextField"
    private TextField titleTextField; // Value injected by FXMLLoader

    @FXML // fx:id="locationTextField"
    private TextField locationTextField; // Value injected by FXMLLoader

    @FXML // fx:id="detailsTextField"
    private TextField detailsTextField; // Value injected by FXMLLoader

    @FXML // fx:id="groupComboBox"
    private ComboBox<String> groupComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="idTextField"
    private TextField idTextField; // Value injected by FXMLLoader

    @FXML // fx:id="wholeDayCheckBox"
    private CheckBox wholeDayCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="alarmReminderCheckBox"
    private CheckBox alarmReminderCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="startDatePicker"
    private DatePicker startDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="startHourComboBox"
    private ComboBox<String> startHourComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="startMinuteComboBox"
    private ComboBox<String> startMinuteComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="endDatePicker"
    private DatePicker endDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="endHourComboBox"
    private ComboBox<String> endHourComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="endMinuteComboBox"
    private ComboBox<String> endMinuteComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader
    
    // Does my logging
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    // Useful when working with DatePickers
    private final TimestampBean tspStart = new TimestampBean();
    private final TimestampBean tspEnd = new TimestampBean();
    
    // These will be used to access and manipulate the JAM database
    private AppointmentDAO appointmentDAO;
    private Appointment appointment;
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        idTextField.setEditable(false);
    }
    
    private void doBindings()
    {
        try 
        {
            for(GroupRecord group : new GroupRecordDAOImpl().findAll())
            {
                groupComboBox.getItems().add(String.valueOf(group.getGroupNumber()));
            }
        }
        catch (SQLException ex)
        {
            log.error("Something wrong when trying to use GroupRecordDAOImpl.findAll()", ex);
        }
        
        for(int i = 0; i < 24; i++)
        {
            startHourComboBox.getItems().add(String.valueOf(i));
            endMinuteComboBox.getItems().add(String.valueOf(i));
        }
        for(int i = 0; i < 60; i++)
        {
            startMinuteComboBox.getItems().add(String.valueOf(i));
            endMinuteComboBox.getItems().add(String.valueOf(i));
        }
                
        Bindings.bindBidirectional(idTextField.textProperty(), appointment.appointmentIDProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(titleTextField.textProperty(), appointment.titleProperty());
        Bindings.bindBidirectional(locationTextField.textProperty(), appointment.locationProperty());
        
        
        Bindings.bindBidirectional(startDatePicker.valueProperty(), appointment.startTimeBean().dateFieldProperty());
        Bindings.bindBidirectional(startHourComboBox.valueProperty(), appointment.startTimeBean().hourFieldProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(startMinuteComboBox.valueProperty(), appointment.startTimeBean().minuteFieldProperty(), new NumberStringConverter());
        
        
        Bindings.bindBidirectional(endDatePicker.valueProperty(), appointment.endTimeBean().dateFieldProperty());
        Bindings.bindBidirectional(endHourComboBox.valueProperty(), appointment.endTimeBean().hourFieldProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(endMinuteComboBox.valueProperty(), appointment.endTimeBean().minuteFieldProperty(), new NumberStringConverter());
        
        
        
        Bindings.bindBidirectional(detailsTextField.textProperty(), appointment.detailsProperty());
        Bindings.bindBidirectional(wholeDayCheckBox.selectedProperty(), appointment.wholeDayProperty());
        Bindings.bindBidirectional(groupComboBox.valueProperty(), appointment.appointmentGroupProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(alarmReminderCheckBox.selectedProperty(), appointment.alarmReminderProperty());
    }
    
    public void handleCreate()
    {
       try
        {
            int records = appointmentDAO.create(appointment);
            log.info("Records created: " + records);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    @FXML
    void handleUpdate(ActionEvent event) {
       try
        {
            int records = appointmentDAO.update(appointment);
            log.info("Records updated: " + records);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    public void handleDelete()
    {
       try
        {
            int records = appointmentDAO.delete(appointment.getAppointmentID());
            log.info("Records deleted: " + records);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    public void handleClear()
    {
        appointment.setAppointmentID(-1);
        appointment.setTitle("");
        appointment.setLocation("");
        appointment.setDetails("");
        appointment.setStartTime(Timestamp.valueOf(LocalDateTime.now()));
        appointment.setEndTime(Timestamp.valueOf(LocalDateTime.now()));
        appointment.setDetails("");
        appointment.setWholeDay(false);
        appointment.setAppointmentGroup(0);
        appointment.setAlarmReminder(false);
    }
    
    @FXML
    void handleNext(ActionEvent event) 
    {
        try
        {
            appointmentDAO.findNextByID(appointment);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }

    /**
     * Will set the form's field to the previous appointment record of the appointment table.
     * @param event 
     */
    @FXML
    void handlePrevious(ActionEvent event) {
        try
        {
            appointmentDAO.findPrevByID(appointment);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    /**
     * This method will close the window for the appointment form.
     */
    public void handleExit()
    {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
    
    public void setAppointment(AppointmentDAO appointmentDAO, Appointment appointment)
    {
        this.appointment = appointment;
        doBindings();
        try 
        {
            this.appointmentDAO = appointmentDAO;
            appointmentDAO.findNextByID(appointment);
        } 
        catch (SQLException ex) 
        {
            log.error("SQL Error", ex);
        }
    }
}
