/**
 * Sample Skeleton for 'WeekView.fxml' Controller Class
 */

package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.DayBean;
import com.marcdanieldialogo.entities.HalfHourBean;
import com.marcdanieldialogo.entities.HalfHourOfWeek;
import com.marcdanieldialogo.entities.WeekBean;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class WeekViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="weekTable"
    private TableView<HalfHourOfWeek> weekTable; // Value injected by FXMLLoader

    @FXML // fx:id="timeCells"
    private TableColumn<HalfHourOfWeek, String> timeCells; // Value injected by FXMLLoader

    @FXML // fx:id="sundayCells"
    private TableColumn<HalfHourOfWeek, String> sundayCells; // Value injected by FXMLLoader

    @FXML // fx:id="mondayCells"
    private TableColumn<HalfHourOfWeek, String> mondayCells; // Value injected by FXMLLoader

    @FXML // fx:id="tuesdayCells"
    private TableColumn<HalfHourOfWeek, String> tuesdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="wednesdayCells"
    private TableColumn<HalfHourOfWeek, String> wednesdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="thursdayCells"
    private TableColumn<HalfHourOfWeek, String> thursdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="fridayCells"
    private TableColumn<HalfHourOfWeek, String> fridayCells; // Value injected by FXMLLoader

    @FXML // fx:id="saturdayCells"
    private TableColumn<HalfHourOfWeek, String> saturdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="weekLbl"
    private Label weekLbl; // Value injected by FXMLLoader

    @FXML // fx:id="exitButton"
    private Button exitButton; // Value injected by FXMLLoader
    
    private LocalDate day;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
        timeCells.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        sundayCells.setCellValueFactory(cellData -> cellData.getValue().sundayProperty());
        mondayCells.setCellValueFactory(cellData -> cellData.getValue().mondayProperty());
        tuesdayCells.setCellValueFactory(cellData -> cellData.getValue().tuesdayProperty());
        wednesdayCells.setCellValueFactory(cellData -> cellData.getValue().wednesdayProperty());
        thursdayCells.setCellValueFactory(cellData -> cellData.getValue().thursdayProperty());
        fridayCells.setCellValueFactory(cellData -> cellData.getValue().fridayProperty());
        saturdayCells.setCellValueFactory(cellData -> cellData.getValue().saturdayProperty());
        
        adjustColumnWidths();
    }
    
    public void displayTable()
    {
        ObservableList<HalfHourOfWeek> dayList = FXCollections.observableArrayList();
        LocalDateTime ldt = LocalDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), 0, 0);
        for(int i = 0; i < 48; i++)
        {
            HalfHourOfWeek halfHour = new HalfHourOfWeek();
            halfHour.setDate(ldt);
            dayList.add(halfHour);
            ldt = ldt.plusMinutes(30);
        }
        
        weekTable.setItems(dayList);
    }
    
    private void adjustColumnWidths() 
    {
        double width = weekTable.getPrefWidth();
        timeCells.setPrefWidth(width / 8.0);
        sundayCells.setPrefWidth(width / 8.0);
        mondayCells.setPrefWidth(width / 8.0);
        tuesdayCells.setPrefWidth(width / 8.0);
        wednesdayCells.setPrefWidth(width / 8.0);
        thursdayCells.setPrefWidth(width / 8.0);
        fridayCells.setPrefWidth(width / 8.0);
        saturdayCells.setPrefWidth(width / 8.0);
    }
    
    /**
     * Sets the label responsible for displaying the current week
     */
    private void setWeekLabel()
    {
        LocalDate weekRange = day;
        String sunday = "";
        String saturday = "";
        
        while(weekRange.getDayOfWeek() != DayOfWeek.SUNDAY)
        {
            weekRange = weekRange.minusDays(1);
        }
        sunday = weekRange.getDayOfWeek() + " " + weekRange.getDayOfMonth() + " " + weekRange.getMonth()+ " " + weekRange.getYear();
        while(weekRange.getDayOfWeek() != DayOfWeek.SATURDAY)
        {
            weekRange = weekRange.plusDays(1);
        }
        saturday = weekRange.getDayOfWeek() + " " + weekRange.getDayOfMonth() + " " + weekRange.getMonth()+ " " + weekRange.getYear();
        
        weekLbl.setText(sunday + " — " + saturday);
    }
    
    /**
     * Moves the week view to the next week
     * @param event 
     */
    @FXML
    void handleNext(ActionEvent event) {
        day = day.plusWeeks(1);
        displayTable();
        setWeekLabel();
    }

    /**
     * Moves the week view to the previous week
     * @param event 
     */
    @FXML
    void handlePrevious(ActionEvent event) {
        day = day.minusWeeks(1);
        displayTable();
        setWeekLabel();
    }
    
    /**
     * Closes the window
     */
    public void handleExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Sets the LocalDate object of this class
     * @param day 
     */
    public void setDay(LocalDate day)
    {
        this.day = day;
        setWeekLabel();
        displayTable();
    }
}
