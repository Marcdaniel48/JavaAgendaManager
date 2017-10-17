/**
 * Sample Skeleton for 'DayView.fxml' Controller Class
 */

package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.Appointment;
import com.marcdanieldialogo.MainApp;
import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

public class DayViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="appointmentTable"
    private TableView<Appointment> appointmentTable; // Value injected by FXMLLoader

    @FXML // fx:id="idCells"
    private TableColumn<Appointment, Number> idCells; // Value injected by FXMLLoader

    @FXML // fx:id="titleCells"
    private TableColumn<Appointment, String> titleCells; // Value injected by FXMLLoader

    @FXML // fx:id="locationCells"
    private TableColumn<Appointment, String> locationCells; // Value injected by FXMLLoader

    @FXML // fx:id="startTimeCells"
    private TableColumn<Appointment, String> startTimeCells; // Value injected by FXMLLoader

    @FXML // fx:id="endTimeCells"
    private TableColumn<Appointment, String> endTimeCells; // Value injected by FXMLLoader

    @FXML // fx:id="detailsCells"
    private TableColumn<Appointment, String> detailsCells; // Value injected by FXMLLoader

    @FXML // fx:id="wholeDayCells"
    private TableColumn<Appointment, String> wholeDayCells; // Value injected by FXMLLoader

    @FXML // fx:id="appointmentGroupCells"
    private TableColumn<Appointment, Number> appointmentGroupCells; // Value injected by FXMLLoader

    @FXML // fx:id="alarmReminderCells"
    private TableColumn<Appointment, String> alarmReminderCells; // Value injected by FXMLLoader
    
    @FXML // fx:id="currentDayLabel"
    private Label currentDayLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="exitButton"
    private Button exitButton; // Value injected by FXMLLoader
    
    private LocalDate day;
    
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        idCells.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentID()));
        titleCells.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        locationCells.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        startTimeCells.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStartTime().toString()));
        endTimeCells.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEndTime().toString()));
        detailsCells.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDetails()));
        wholeDayCells.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWholeDay().toString()));
        appointmentGroupCells.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentGroup()));
        alarmReminderCells.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAlarmReminder().toString()));
        
        adjustColumnWidths();
    }
    
    /**
     * Evens out the width of each column of the table which will display all appointments for a particular day
     */
    private void adjustColumnWidths() {
        double width = appointmentTable.getPrefWidth();
        idCells.setPrefWidth(width / 9.0);
        titleCells.setPrefWidth(width / 9.0);
        locationCells.setPrefWidth(width / 9.0);
        startTimeCells.setPrefWidth(width / 9.0);
        endTimeCells.setPrefWidth(width / 9.0);
        detailsCells.setPrefWidth(width / 9.0);
        wholeDayCells.setPrefWidth(width / 9.0);
        appointmentGroupCells.setPrefWidth(width / 9.0);
        alarmReminderCells.setPrefWidth(width / 9.0);
    }
    
    /**
     * Sets the LocalDate object of this class, then calls the display method which will give column of the table a value
     * @param day 
     */
    public void setDay(LocalDate day)
    {
        this.day = day;
        
        // Using the passed in day, this method will set the appointments into the view.
        display();
    }
    
    /**
     * This adds a list of appointments, for a particular day, to the table. This method is where we give the table its values.
     */
    public void display()
    {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        
        try 
        {
            AppointmentDAO dao = new AppointmentDAOImpl();
            
            LocalDateTime find = LocalDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 1, 1);

            currentDayLabel.setText(day.getDayOfMonth() + " " + day.getMonth() + " " + day.getYear());
            List<Appointment> todayAppointments = dao.findByDay(find);
            
            for(Appointment appointment : todayAppointments)
            {
                list.add(appointment);
            }
        } 
        catch (SQLException ex) 
        {
            log.error("Error, something could have gone wrong with the DAO", ex);
            Platform.exit();
        }
        
        
        appointmentTable.setItems(list);
    }
    
    /**
     * Opens up an appointment form to create a new appointment
     */
    @FXML
    public void handleNew()
    {
        try
        {
            Stage dayStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("AppointmentFormText"));
            loader.setLocation(MainApp.class.getResource("/fxml/AppointmentForm.fxml"));
            Parent dayPane = (BorderPane) loader.load();

            Scene dayScene = new Scene(dayPane);

            dayStage.setScene(dayScene);
            AppointmentFormController controller = loader.getController();
            controller.setDay(day);
            dayStage.setTitle("Appointment form");
            dayStage.initModality(Modality.APPLICATION_MODAL);
            dayStage.show();
        }
        catch(IOException ioe)
        {
            Platform.exit();
        }
    }
    
    /**
     * Makes the Day View go to the previous day
     */
    @FXML
    public void handlePrevious()
    {
        this.day = day.minusDays(1);
        display();
    }
    
    /**
     * Makes the Day View go to the next day
     */
    @FXML
    public void handleNext()
    {
        this.day = day.plusDays(1);
        display();
    }
    
    /**
     * Closes the window
     */
    public void handleExit()
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Will open the Week View and display the week that this controller's 'LocalDate day' is in.
     * @param event 
     */
    @FXML
    void handleOpenWeekView(ActionEvent event) 
    {
        try
        {
            Stage dayStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("WeekViewText"));
            loader.setLocation(MainApp.class.getResource("/fxml/WeekView.fxml"));
            Parent dayPane = (BorderPane) loader.load();

            Scene dayScene = new Scene(dayPane);

            dayStage.setScene(dayScene);
            WeekViewController controller = loader.getController();
            controller.setDay(day);
            dayStage.setTitle("Week View");
            dayStage.show();
            
            handleExit();
        }
        catch(IOException ioe)
        {
            Platform.exit();
        }

    }
}
