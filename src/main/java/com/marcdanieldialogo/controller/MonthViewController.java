/**
 * Sample Skeleton for 'MonthView.fxml' Controller Class
 */

package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.Week;
import com.marcdanieldialogo.jam_jdbc.MainApp;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MonthViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="monthTable"
    private TableView<Week> monthTable; // Value injected by FXMLLoader

    @FXML // fx:id="sundayCells"
    private TableColumn<Week, String> sundayCells; // Value injected by FXMLLoader

    @FXML // fx:id="mondayCells"
    private TableColumn<Week, String> mondayCells; // Value injected by FXMLLoader

    @FXML // fx:id="tuesdayCells"
    private TableColumn<Week, String> tuesdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="wednesdayCells"
    private TableColumn<Week, String> wednesdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="thursdayCells"
    private TableColumn<Week, String> thursdayCells; // Value injected by FXMLLoader

    @FXML // fx:id="fridayCells"
    private TableColumn<Week, String> fridayCells; // Value injected by FXMLLoader

    @FXML // fx:id="saturdayCells"
    private TableColumn<Week, String> saturdayCells; // Value injected by FXMLLoader
    
    @FXML // fx:id="currentMonthLabel"
    private Label currentMonthLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="exitButton"
    private Button exitButton; // Value injected by FXMLLoader
    
    private ObservableList<TablePosition> theCells;
    private int currentMonth = LocalDate.now().getMonthValue();
    private int currentYear = LocalDate.now().getYear();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        sundayCells.setCellValueFactory(cellData -> cellData.getValue().getWeek().get(0));
        mondayCells.setCellValueFactory(cellData -> cellData.getValue().getWeek().get(1));
        tuesdayCells.setCellValueFactory(cellData -> cellData.getValue().getWeek().get(2));
        wednesdayCells.setCellValueFactory(cellData -> cellData.getValue().getWeek().get(3));
        thursdayCells.setCellValueFactory(cellData -> cellData.getValue().getWeek().get(4));
        fridayCells.setCellValueFactory(cellData -> cellData.getValue().getWeek().get(5));
        saturdayCells.setCellValueFactory(cellData -> cellData.getValue().getWeek().get(6));
        
        display();
        monthTable.getSelectionModel().cellSelectionEnabledProperty().set(true);
        theCells = monthTable.getSelectionModel().getSelectedCells();
        theCells.addListener(this::showSingleCellDetails);
        adjustColumnWidths();
    }
    
    /*
        Turns the cells into squares, to make the table look more like a calendar
    */
    private void adjustColumnWidths() {
        double width = monthTable.getPrefWidth();
        mondayCells.setPrefWidth(width / 7.0);
        tuesdayCells.setPrefWidth(width / 7.0);
        wednesdayCells.setPrefWidth(width / 7.0);
        thursdayCells.setPrefWidth(width / 7.0);
        fridayCells.setPrefWidth(width / 7.0);
        saturdayCells.setPrefWidth(width / 7.0);
        sundayCells.setPrefWidth(width / 7.0);
        monthTable.setFixedCellSize(width / 7.0);
    }
    
    public void handleNewAppointment()
    {
        try
        {   
            Stage appointmentFormStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("DayFormText"));
            loader.setLocation(MainApp.class.getResource("/fxml/DayForm.fxml"));
            
            Parent appointmentFormPane = (BorderPane) loader.load();

            Scene appointmentFormScene = new Scene(appointmentFormPane);
            
            appointmentFormStage.setScene(appointmentFormScene);
            appointmentFormStage.initModality(Modality.APPLICATION_MODAL);
            appointmentFormStage.setTitle("Appointment Form");
            appointmentFormStage.show();
        }
        catch(IOException ioe)
        {
            Platform.exit();
        }
    }
        
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
     * Will display the days of the current month on a table
     */
    public void display()
    {
        ObservableList<Week> list = FXCollections.observableArrayList();
        
        LocalDate date = LocalDate.of(currentYear, currentMonth, 1);
        currentMonthLabel.setText(date.getMonth().toString() + " " + date.getYear());

        List<StringProperty> lastWeek = new ArrayList<StringProperty>();
        
        boolean dayInCalendarAlready = false;
        for(int i = 0; i < 6; i ++)
        {
            Week week = new Week(date);
            if(!dayInCalendarAlready)
                list.add(week);
            
            lastWeek = week.getWeek();
            date = date.plusDays(7);
            
            while(date.getMonthValue() != currentMonth)
            {
                date = date.minusDays(1);
            }
            
            // The last line will be skipped if the month doesn't enter a 6th week
            if(!lastWeek.isEmpty())
            {
                for(StringProperty item : lastWeek)
                {
                    if(!"".equals(item.get()))
                    {
                        if(date.getDayOfMonth() == Integer.parseInt(item.get()))
                        {
                            dayInCalendarAlready = true;
                            break;
                        }
                    }
                }
            }
        }
        
        monthTable.setItems(list);
    }
    
    private void showSingleCellDetails(ListChangeListener.Change<? extends TablePosition> change)
    {
        if(theCells.size() > 0)
        {
            TablePosition selectedCell = theCells.get(0);
            TableColumn column = selectedCell.getTableColumn();
            int rowIndex = selectedCell.getRow();
            Object data = column.getCellObservableValue(rowIndex).getValue();
        
            if(!((String)data).equals(""))
            {
                LocalDate selectedDay = LocalDate.of(currentYear, currentMonth, Integer.parseInt((String)data));
                
                handleOpen(selectedDay);
            }
        }
    }
    
    @FXML
    private void handleNext()
    {
        if(currentMonth == 12)
        {
            currentMonth = 1;
            currentYear += 1;
            
            display();
        }
        else
        {
            currentMonth += 1;
            
            display();
        }
    }
    
    @FXML
    private void handlePrevious()
    {
        if(currentMonth == 1)
        {
            currentMonth = 12;
            currentYear -= 1;
            
            display();
        }
        else
        {
            currentMonth -= 1;
            
            display();
        }
    }
    
    @FXML
    private void handleExit()
    {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void handleNewGroupRecord(ActionEvent event) 
    {
        try
        {   
            Stage groupFormStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("GroupFormText"));
            loader.setLocation(MainApp.class.getResource("/fxml/GroupForm.fxml"));
            
            Parent groupFormPane = (BorderPane) loader.load();

            Scene groupFormScene = new Scene(groupFormPane);
            
            groupFormStage.setScene(groupFormScene);
            groupFormStage.initModality(Modality.APPLICATION_MODAL);
            groupFormStage.setTitle("Group Record Form");
            groupFormStage.show();
        }
        catch(IOException ioe)
        {
            Platform.exit();
        }
    }
    
    @FXML
    void handleSettingsForm(ActionEvent event) 
    {

        try
        {   
            Stage groupFormStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle("SMTPSettingsFormText"));
            loader.setLocation(MainApp.class.getResource("/fxml/SMTPSettingsForm.fxml"));
            
            Parent groupFormPane = (BorderPane) loader.load();

            Scene groupFormScene = new Scene(groupFormPane);
            
            groupFormStage.setScene(groupFormScene);
            groupFormStage.initModality(Modality.APPLICATION_MODAL);
            groupFormStage.setTitle("SMTP Settings Form");
            groupFormStage.show();
        }
        catch(IOException ioe)
        {
            Platform.exit();
        }
    }
}
