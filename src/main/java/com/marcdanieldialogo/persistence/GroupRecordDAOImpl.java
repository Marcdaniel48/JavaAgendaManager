package com.marcdanieldialogo.persistence;

import com.marcdanieldialogo.entities.GroupRecord;
import com.marcdanieldialogo.jam_jdbc.Utilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the GroupRecordDAO interface
 * 
 * @author Marc-Daniel
 */
public class GroupRecordDAOImpl implements GroupRecordDAO{

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    
    // Has a method that can make and return a connection.
    private Utilities util = new Utilities();
    
     /**
     * Create a GroupRecord record
     *
     * @param groupRecord
     * @return number of records created, should be 0 or 1
     * @throws SQLException
     */
    @Override
    public int create(GroupRecord groupRecord) throws SQLException {
        int records;

        String sql = "INSERT INTO GROUP_RECORD(GROUP_NAME, COLOUR) values (?, ?)";
        try(Connection conn = util.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);)
        {
            stmt.setString(1, groupRecord.getGroupName());
            stmt.setString(2, groupRecord.getColour());
            
            records = stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            
            int recordNum = -1;
            
            if (rs.next()) {
                recordNum = rs.getInt(1);
            }
            
            groupRecord.setGroupNumber(recordNum);
            log.debug("New record ID is " + recordNum);
            
        }
        
        return records;
    }

    /**
     * Returns a List of GroupRecords objects holding the values of each record in the Group_Record table.
     *
     * @return rows The list of GroupRecord objects.
     * @throws SQLException
     */
    @Override
    public List<GroupRecord> findAll() throws SQLException {
        List<GroupRecord> rows = new ArrayList<>();

        String selectQuery = "SELECT * FROM GROUP_RECORD";
        try (Connection connection = util.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);
                ResultSet results = pStatement.executeQuery()) 
        {
            while (results.next()) {
                GroupRecord groupRecord = new GroupRecord();
                groupRecord.setGroupNumber(results.getInt("GROUP_NUMBER"));
                groupRecord.setGroupName(results.getString("GROUP_NAME"));
                groupRecord.setColour(results.getString("COLOUR"));
                
                rows.add(groupRecord);
            }
        }
        return rows;
    }

    /**
     * Takes in an int ID and uses it to query for a GROUP_RECORD record, then returns
     * a GroupRecord object that holds all of the values of the retrieved record.
     *
     * @param id The ID of the requested group record.
     * @return groupRecord A GroupRecord object that holds all of the information of a Group_Record record.
     * @throws SQLException
     */
    @Override
    public GroupRecord findID(int id) throws SQLException {
        GroupRecord groupRecord = new GroupRecord();
        
        String selectQuery = "SELECT * FROM GROUP_RECORD WHERE GROUP_NUMBER = ?";
        
        try (Connection connection = util.getConnection();
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) 
        {
            pStatement.setInt(1, id);

            try (ResultSet resultSet = pStatement.executeQuery()) 
            {
                if (resultSet.next()) 
                {
                    groupRecord.setGroupNumber(resultSet.getInt("GROUP_NUMBER"));
                    groupRecord.setGroupName(resultSet.getString("GROUP_NAME"));
                    groupRecord.setColour(resultSet.getString("COLOUR"));
                }
            }
        }
        
        return groupRecord;
    }

    
    /**
     * Takes in a GroupRecord object and uses its ID value in order to update a record in the
     * GROUP_RECORD table with the same ID. All of the object's fields will be used to update
     * the record.
     *
     * @param groupRecord A GroupRecord object that holds all of the updated values.
     * @return record The number of records that has been updated. Should be either 0 or 1.
     * @throws SQLException
     */
    @Override
    public int update(GroupRecord groupRecord) throws SQLException {
        int record = 0;
        String updateStatement = "UPDATE GROUP_RECORD SET GROUP_NAME = ?, COLOUR = ? WHERE GROUP_NUMBER = ?";
        
        try
        (Connection connection = util.getConnection(); PreparedStatement pStatement = connection.prepareStatement(updateStatement);)
        {
            pStatement.setString(1, groupRecord.getGroupName());
            pStatement.setString(2, groupRecord.getColour());
            pStatement.setInt(3, groupRecord.getGroupNumber());
            
            pStatement.executeUpdate();
            record = 1;
        }
        
        return record;
    }

     /**
     * Takes in an int ID and uses it to delete a record in the GROUP_RECORD table with the same
     * ID/group_number.
     *
     * @param ID Represents the ID(or group_number) of the record that's to be deleted.
     * @return The number of records that have been deleted. Should be either 0 or 1.
     * @throws SQLException
     */
    @Override
    public int deleteGroupRecord(int ID) throws SQLException {
        int record = 0;
        String sql = "DELETE FROM GROUP_RECORD WHERE GROUP_NUMBER = ?";
        
        try
        (Connection connection = util.getConnection(); PreparedStatement pStatement = connection.prepareStatement(sql);)
        {
            pStatement.setInt(1, ID);
            
            pStatement.executeUpdate();
            record = 1;
        }
        
        return record;
    }

     /**
     * Takes in a String representing a GROUP_RECORD's group name and uses it to select a record in the GROUP_RECORD table with the same
     * name.
     *
     * @param groupName The group name of the requested record.
     * @return A GroupRecord object that represents an equivalent record in the GROUP_RECORD table.
     * @throws SQLException
     */
    @Override
    public GroupRecord findByGroupName(String groupName) throws SQLException {
        GroupRecord groupRecord = new GroupRecord();
        
        String sql = "SELECT * FROM GROUP_RECORD WHERE GROUP_NAME = ?";
        
        try
        (Connection conn = util.getConnection();
                PreparedStatement pStatement = conn.prepareStatement(sql);)
        {
            pStatement.setString(1, groupName);
            
            ResultSet results = pStatement.executeQuery();
                        
            if (results.next()) 
            {
                    groupRecord.setGroupNumber(results.getInt("GROUP_NUMBER"));
                    groupRecord.setGroupName(results.getString("GROUP_NAME"));
                    groupRecord.setColour(results.getString("COLOUR"));
            }
        }
        
        return groupRecord;
    }
    
}
