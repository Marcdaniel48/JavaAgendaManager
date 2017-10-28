package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.ColorBean;
import com.marcdanieldialogo.entities.GroupRecord;
import com.marcdanieldialogo.persistence.GroupRecordDAO;
import com.marcdanieldialogo.persistence.GroupRecordDAOImpl;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupFormController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="groupNameTextField"
    private TextField groupNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="groupNumberTextField"
    private TextField groupNumberTextField; // Value injected by FXMLLoader
    
    @FXML // fx:id="colorPicker"
    private ColorPicker colorPicker; // Value injected by FXMLLoader
    
    // Does my logging
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    // Allows access and manipulation of the GroupRecord table of the JAM database
    private GroupRecordDAO groupRecordDAO;
    private GroupRecord groupRecord;
    
    private ColorBean colorBean;
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        groupNumberTextField.setEditable(false);
    }
    
    private void doBindings()
    {
        Bindings.bindBidirectional(groupNumberTextField.textProperty(), groupRecord.groupNumberProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(groupNameTextField.textProperty(), groupRecord.groupNameProperty());
        Bindings.bindBidirectional(colorPicker.valueProperty(), groupRecord.colourProperty());
    }
    
    /**
     * Creates a new GroupRecord record, based on the form's input values
     * @param event 
     */
    @FXML
    void handleCreate(ActionEvent event) {
        try
        {
            int records = groupRecordDAO.create(groupRecord);
            log.info("Records created: " + records);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    /**
     * Updates the GroupRecord in the database that has the same group number as the group number set
     * in the form's text field
     * @param event 
     */
    @FXML
    void handleUpdate(ActionEvent event) {
        try
        {
            int records = groupRecordDAO.update(groupRecord);
            log.info("Records updated: " + records);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    /**
     * Deletes the GroupRecord in the database that has the same group number as the group number set
     * in the form's text field
     * @param event 
     */
    @FXML
    void handleDelete(ActionEvent event) {
        try
        {
            int records = groupRecordDAO.deleteGroupRecord(groupRecord.getGroupNumber());
            log.info("Records deleted: " + records);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    /**
     * Clears the values of the form's input fields
     * @param event 
     */
    @FXML
    void handleClear(ActionEvent event) {
        groupRecord.setGroupNumber(-1);
        groupRecord.setGroupName("");
        groupRecord.setColour("");
    }
    
    /**
     * Sets the form's input fields to the next GroupRecord in the database, the record after this class' GroupRecord object
     * @param event 
     */
    @FXML
    void handleNext(ActionEvent event) {
        try
        {
            groupRecordDAO.findNextByID(groupRecord);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }

    /**
     * Makes the form's input fields to the previous GroupRecord in the database, the record before this class' GroupRecord object
     * @param event 
     */
    @FXML
    void handlePrevious(ActionEvent event) {
        try
        {
            groupRecordDAO.findPrevByID(groupRecord);
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
    
    public void setGroupRecord(GroupRecordDAO groupRecordDAO, GroupRecord groupRecord)
    {
        this.groupRecord = groupRecord;
        doBindings();
        
        try 
        {
            this.groupRecordDAO = groupRecordDAO;
            groupRecordDAO.findNextByID(groupRecord);
        } 
        catch (SQLException ex) 
        {
            log.error("SQL Error", ex);
        }
        
    }
}
