/**
 * Sample Skeleton for 'GroupForm.fxml' Controller Class
 */

package com.marcdanieldialogo.controller;

import com.marcdanieldialogo.entities.GroupRecord;
import com.marcdanieldialogo.persistence.GroupRecordDAO;
import com.marcdanieldialogo.persistence.GroupRecordDAOImpl;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

    @FXML // fx:id="colourTextField"
    private TextField colourTextField; // Value injected by FXMLLoader
    
    @FXML // fx:id="groupNumberTextField"
    private TextField groupNumberTextField; // Value injected by FXMLLoader
    
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final GroupRecordDAO groupRecordDAO = new GroupRecordDAOImpl();
    private GroupRecord currentGroup;

    /**
     * Creates a new GroupRecord record, based on the form's input values
     * @param event 
     */
    @FXML
    void handleCreate(ActionEvent event) {
        try{
            
        currentGroup = new GroupRecord();
        
        currentGroup.setGroupName(groupNameTextField.getText());
        currentGroup.setColour(colourTextField.getText());
        
        groupRecordDAO.create(currentGroup);
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
    
    /**
     * Will set the form's input values, based on the values of this class' GroupRecord currentGroup object
     */
    private void displayCurrentGroupRecord()
    {
        groupNumberTextField.setText(currentGroup.getGroupNumber()+"");
        groupNameTextField.setText(currentGroup.getGroupName());
        colourTextField.setText(currentGroup.getColour());
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

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        try
        {
            currentGroup = groupRecordDAO.findAll().get(0);
            displayCurrentGroupRecord();
        }
        catch(SQLException ex)
        {
            log.error("SQLException with GroupRecordDAO.findAll probably", ex);
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
            GroupRecord updatedRecord = new GroupRecord();

            updatedRecord.setGroupNumber(currentGroup.getGroupNumber());
            updatedRecord.setGroupName(currentGroup.getGroupName());
            updatedRecord.setColour(currentGroup.getColour());

            groupRecordDAO.update(updatedRecord);
            this.currentGroup = updatedRecord;
        }
        catch(SQLException ex)
        {
            log.error("SQLException trying to update a GroupRecord", ex);
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
            groupRecordDAO.deleteGroupRecord(Integer.parseInt(groupNumberTextField.getText()));
        } 
        catch (SQLException ex) 
        {
            log.error("SQLException - Something went wrong. Was a correct ID entered?", ex);
        }
    }
    
    /**
     * Clears the values of the form's input fields
     * @param event 
     */
    @FXML
    void handleClear(ActionEvent event) {
        groupNameTextField.setText("");
        colourTextField.setText("");
    }
    
    /**
     * Sets the form's input fields to the next GroupRecord in the database, the record after this class' GroupRecord object
     * @param event 
     */
    @FXML
    void handleNext(ActionEvent event) {
        try
        {
            if(!(groupNumberTextField.getText().isEmpty()))
            {
                this.currentGroup = groupRecordDAO.findID(Integer.parseInt(groupNumberTextField.getText()) + 1);
                displayCurrentGroupRecord();
            }
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
            if(!(groupNumberTextField.getText().isEmpty()))
            {
                this.currentGroup = groupRecordDAO.findID(Integer.parseInt(groupNumberTextField.getText()) - 1);
                displayCurrentGroupRecord();
            }
        }
        catch(SQLException sqle)
        {
            log.error("SQLException - Something went wrong", sqle);
        }
    }
}
