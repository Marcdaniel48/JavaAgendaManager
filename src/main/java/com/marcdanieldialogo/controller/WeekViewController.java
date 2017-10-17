/**
 * Sample Skeleton for 'WeekView.fxml' Controller Class
 */

package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.MainApp;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WeekViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="mondayLbl"
    private Label mondayLbl; // Value injected by FXMLLoader

    @FXML // fx:id="tuesdayLbl"
    private Label tuesdayLbl; // Value injected by FXMLLoader

    @FXML // fx:id="wednesdayLbl"
    private Label wednesdayLbl; // Value injected by FXMLLoader

    @FXML // fx:id="fridayLbl"
    private Label fridayLbl; // Value injected by FXMLLoader

    @FXML // fx:id="saturdayLbl"
    private Label saturdayLbl; // Value injected by FXMLLoader

    @FXML // fx:id="sundayLbl"
    private Label sundayLbl; // Value injected by FXMLLoader
    
    @FXML // fx:id="thursdayLbl"
    private Label thursdayLbl; // Value injected by FXMLLoader

    @FXML // fx:id="exitButton"
    private Button exitButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="weekLbl"
    private Label weekLbl; // Value injected by FXMLLoader
    
    private LocalDate day;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    }

    /**
     * Will open the day view for this week's Sunday
     * @param event 
     */
    @FXML
    void handleSunday(ActionEvent event) {
        LocalDate sunday = day;
        while(sunday.getDayOfWeek() != DayOfWeek.SUNDAY)
        {
            sunday = sunday.minusDays(1);
        }

        sundayLbl.setText(sunday.getDayOfMonth() + " " + sunday.getMonth());
        handleOpen(sunday);
        handleExit();
    }
    
    /**
     * Will open the day view for this week's Monday
     * @param event 
     */
    @FXML
    void handleMonday(ActionEvent event) {
        LocalDate monday = day;
        if(monday.getDayOfWeek() != DayOfWeek.MONDAY)
        {
            while(monday.getDayOfWeek() == DayOfWeek.SUNDAY)
            {
                monday = monday.plusDays(1);
            }
            while(monday.getDayOfWeek() == DayOfWeek.TUESDAY || monday.getDayOfWeek() == DayOfWeek.WEDNESDAY || monday.getDayOfWeek() == DayOfWeek.THURSDAY
                    || monday.getDayOfWeek() == DayOfWeek.FRIDAY || monday.getDayOfWeek() == DayOfWeek.SATURDAY)
            {
                monday = monday.minusDays(1);
            }
        }

        mondayLbl.setText(monday.getDayOfMonth() + " " + monday.getMonth());
        handleOpen(monday);
        handleExit();
    }

    /**
     * Will open the day view for this week's Tuesday
     * @param event 
     */
    @FXML
    void handleTuesday(ActionEvent event) {
        LocalDate tuesday = day;
        
        if(tuesday.getDayOfWeek() != DayOfWeek.TUESDAY)
        {
            while(tuesday.getDayOfWeek() == DayOfWeek.WEDNESDAY || tuesday.getDayOfWeek() == DayOfWeek.THURSDAY || tuesday.getDayOfWeek() == DayOfWeek.FRIDAY
                    || tuesday.getDayOfWeek() == DayOfWeek.SATURDAY)
            {
                tuesday = tuesday.minusDays(1);
            }
            while(tuesday.getDayOfWeek() == DayOfWeek.MONDAY || tuesday.getDayOfWeek() == DayOfWeek.SUNDAY)
            {
                tuesday = tuesday.plusDays(1);
            }
        }
        
        tuesdayLbl.setText(tuesday.getDayOfMonth() + " " + tuesday.getMonth());
        handleOpen(tuesday);
        handleExit();
    }

    /**
     * Will open the day view for this week's Wednesday
     * @param event 
     */
    @FXML
    void handleWednesday(ActionEvent event) {
        LocalDate wednesday = day;
        
        if(wednesday.getDayOfWeek() != DayOfWeek.WEDNESDAY)
        {
            while(wednesday.getDayOfWeek() == DayOfWeek.THURSDAY || wednesday.getDayOfWeek() == DayOfWeek.FRIDAY || wednesday.getDayOfWeek() == DayOfWeek.SATURDAY)
            {
                wednesday = wednesday.minusDays(1);
            }
            while(wednesday.getDayOfWeek() == DayOfWeek.MONDAY || wednesday.getDayOfWeek() == DayOfWeek.TUESDAY || wednesday.getDayOfWeek() == DayOfWeek.SUNDAY)
            {
                wednesday = wednesday.plusDays(1);
            }
        }
        
        wednesdayLbl.setText(wednesday.getDayOfMonth() + " " + wednesday.getMonth());
        handleOpen(wednesday);
        handleExit();
    }
    
    /**
     * Will open the day view for this week's Thursday
     * @param event 
     */
    @FXML
    void handleThursday(ActionEvent event) {
        LocalDate thursday = day;
        
        if(thursday.getDayOfWeek() != DayOfWeek.WEDNESDAY)
        {
            while(thursday.getDayOfWeek() == DayOfWeek.FRIDAY || thursday.getDayOfWeek() == DayOfWeek.SATURDAY || thursday.getDayOfWeek() == DayOfWeek.SUNDAY)
            {
                thursday = thursday.minusDays(1);
            }
            while(thursday.getDayOfWeek() == DayOfWeek.MONDAY || thursday.getDayOfWeek() == DayOfWeek.TUESDAY || thursday.getDayOfWeek() == DayOfWeek.WEDNESDAY)
            {
                thursday = thursday.plusDays(1);
            }
        }
        
        thursdayLbl.setText(thursday.getDayOfMonth() + " " + thursday.getMonth());
        handleOpen(thursday);
        handleExit();
    }
    
    /**
     * Will open the day view for this week's Friday
     * @param event 
     */
    @FXML
    void handleFriday(ActionEvent event) {
        LocalDate friday = day;
        if(friday.getDayOfWeek() != DayOfWeek.FRIDAY)
        {
            while(friday.getDayOfWeek() == DayOfWeek.MONDAY || friday.getDayOfWeek() == DayOfWeek.TUESDAY || friday.getDayOfWeek() == DayOfWeek.WEDNESDAY
                    || friday.getDayOfWeek() == DayOfWeek.THURSDAY || friday.getDayOfWeek() == DayOfWeek.SUNDAY)
            {
                friday = friday.plusDays(1);
            }
            while(friday.getDayOfWeek() == DayOfWeek.SATURDAY)
            {
                friday = friday.minusDays(1);
            }
        }

        fridayLbl.setText(friday.getDayOfMonth() + " " + friday.getMonth());
        handleOpen(friday);
        handleExit();
    }
    
    /**
     * Will open the day view for this week's Saturday
     * @param event 
     */
    @FXML
    void handleSaturday(ActionEvent event) {
        LocalDate saturday = day;
        
        while(saturday.getDayOfWeek() != DayOfWeek.SATURDAY)
        {
            saturday = saturday.plusDays(1);
        }
        
        saturdayLbl.setText(saturday.getDayOfMonth() + " " + saturday.getMonth());
        handleOpen(saturday);
        handleExit();
    }
    
    /**
     * Takes in a LocalDate object and opens the day for the date specified in that LocalDate object
     * @param selectedDay 
     */
    public void handleOpen(LocalDate selectedDay)
    {
        try
        {   
            Stage dayStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("DayViewText"));
            loader.setLocation(MainApp.class.getResource("/fxml/DayView.fxml"));
            
            Parent dayPane = (BorderPane) loader.load();

            Scene dayScene = new Scene(dayPane);
            
            dayStage.setScene(dayScene);
            DayViewController controller = loader.getController();
            controller.setDay(selectedDay);
            dayStage.setTitle("Day View");
            dayStage.show();
        }
        catch(IOException ioe)
        {
            Platform.exit();
        }
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
        
        weekLbl.setText("Week of " + sunday + " to " + saturday);
    }
    
    /**
     * Moves the week view to the next week
     * @param event 
     */
    @FXML
    void handleNext(ActionEvent event) {
        day = day.plusWeeks(1);
        setWeekLabel();
    }

    /**
     * Moves the week view to the previous week
     * @param event 
     */
    @FXML
    void handlePrevious(ActionEvent event) {
        day = day.minusWeeks(1);
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
    }
}
