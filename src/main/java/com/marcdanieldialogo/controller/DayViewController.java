package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.DayBean;
import com.marcdanieldialogo.entities.HalfHourOfDay;
import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import com.marcdanieldialogo.persistence.GroupRecordDAO;
import com.marcdanieldialogo.persistence.GroupRecordDAOImpl;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

/**
 * Controller class for DayView.fxml
 * @author Marc-Daniel
 */
public class DayViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="currentDayLabel"
    private Label currentDayLabel; // Value injected by FXMLLoader

    @FXML // fx:id="exitButton"
    private Button exitButton; // Value injected by FXMLLoader

    @FXML // fx:id="dayTable"
    private TableView<HalfHourOfDay> dayTable; // Value injected by FXMLLoader

    @FXML // fx:id="timeCells"
    private TableColumn<HalfHourOfDay, String> timeCells; // Value injected by FXMLLoader

    @FXML // fx:id="appointmentCells"
    private TableColumn<HalfHourOfDay, String> appointmentCells; // Value injected by FXMLLoader
    
    // The controller refers to this LocalDate object find and display 
    private LocalDate day;
    
    // Logger
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    // These DAO objects will be used to find certain appointments and group records.
    private AppointmentDAO appointmentDAO;
    private GroupRecordDAO groupRecordDAO;
    
    /**
     * Constructor that helps with initializing the DAO objects of this class.
     */
    public DayViewController()
    {
        super();
        appointmentDAO = new AppointmentDAOImpl();
        groupRecordDAO = new GroupRecordDAOImpl();
    }
    
    /**
     * Initializes the controller class
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
        timeCells.setCellValueFactory(cellData -> cellData.getValue().timeColProperty());
        appointmentCells.setCellValueFactory(cellData -> cellData.getValue().appointmentColProperty());
        
        // Balances the widths of each column
        adjustColumnWidths();
    }
    
    /**
     * Sets and displays the rows for the day table
     */
    public void displayTable()
    {
        ObservableList<HalfHourOfDay> dayList = FXCollections.observableArrayList();
        
        LocalDateTime ldt = day.atStartOfDay();
        DayBean dayBean = new DayBean(ldt.toLocalDate());
        for(HalfHourOfDay halfHour : dayBean.getHalfHourList())
        {
            dayList.add(halfHour);
        }
        currentDayLabel.setText(day.getDayOfMonth() + " " + day.getMonth().toString() + " " + day.getYear());
        
        dayTable.setItems(dayList);
    }
    
    /**
     * Evens out the width of each column of the table which will display all appointments for a particular day
     */
    private void adjustColumnWidths() {
        double width = dayTable.getPrefWidth();
        timeCells.setPrefWidth(width / 6.0);
        appointmentCells.setPrefWidth(width / 1.2);
    }
    
    /**
     * Makes the Day View go to the next day
     */
    @FXML
    public void handleNext()
    {
        this.day = day.plusDays(1);
        displayTable();
    }
    
    /**
     * Makes the Day View go to the previous day
     */
    @FXML
    public void handlePrevious()
    {
        this.day = day.minusDays(1);
        displayTable();
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
     * Sets the LocalDate object of this class, then calls the display method which will give column of the table a value
     * @param day 
     */
    public void setDay(LocalDate day)
    {
        this.day = day;
        
        displayTable();
    }
}
