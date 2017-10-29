package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.DayBean;
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

public class DayViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="currentDayLabel"
    private Label currentDayLabel; // Value injected by FXMLLoader

    @FXML // fx:id="appFormStatusLabel"
    private Label appFormStatusLabel; // Value injected by FXMLLoader

    @FXML // fx:id="exitButton"
    private Button exitButton; // Value injected by FXMLLoader

    @FXML // fx:id="dayTable"
    private TableView<DayBean> dayTable; // Value injected by FXMLLoader

    @FXML // fx:id="timeCells"
    private TableColumn<DayBean, String> timeCells; // Value injected by FXMLLoader

    @FXML // fx:id="appointmentCells"
    private TableColumn<DayBean, String> appointmentCells; // Value injected by FXMLLoader
    
    private LocalDate day;
    
    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        timeCells.setCellValueFactory(cellData -> cellData.getValue().timeColProperty());
        appointmentCells.setCellValueFactory(cellData -> cellData.getValue().appointmentColProperty());
        
        adjustColumnWidths();
    }
    
    public void displayTable()
    {
        ObservableList<DayBean> dayList = FXCollections.observableArrayList();
        
        LocalDateTime ldt = day.atStartOfDay();
        for(int i = 0; i < 48; i++)
        {
            DayBean dayBean = new DayBean();
            dayBean.setDate(ldt);
            ldt = ldt.plusMinutes(30);
            
            dayList.add(dayBean);
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
