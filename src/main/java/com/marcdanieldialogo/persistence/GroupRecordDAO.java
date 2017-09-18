package com.marcdanieldialogo.persistence;

import com.marcdanieldialogo.entities.GroupRecord;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface for GroupRecord CRUD methods
 * 
 * @author Marc-Daniel
 */
public interface GroupRecordDAO {
    // Create
    public int create(GroupRecord groupRecord) throws SQLException;

    // Read
    public List<GroupRecord> findAll() throws SQLException;

    public GroupRecord findID(int id) throws SQLException;
    // Update

    public int update(GroupRecord groupRecord) throws SQLException;

    // Delete
    public int deleteGroupRecord(int ID) throws SQLException;
    
    // Queries
    public GroupRecord findByGroupName(String groupName) throws SQLException;
}
