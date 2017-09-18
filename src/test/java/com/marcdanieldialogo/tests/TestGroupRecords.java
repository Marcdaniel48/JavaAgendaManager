package com.marcdanieldialogo.tests;

import com.marcdanieldialogo.entities.GroupRecord;
import com.marcdanieldialogo.persistence.GroupRecordDAO;
import com.marcdanieldialogo.persistence.GroupRecordDAOImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests the methods of the GroupRecordDAOImpl.java file
 * 
 * @author Marc-Daniel
 */
public class TestGroupRecords {
 
    // Information needed to connect to the JAM database.
    private final String url = "jdbc:mysql://localhost:3306/jamdb";
    private final String user = "MarcDaniel";
    private final String password = "dbsurfing";
    
    private final static Logger log = LoggerFactory.getLogger(TestGroupRecords.class);
    
    /**
     * Tests the "findAll" method.
     * 
     * Tests to see if the number of records within the GROUP_RECORD table is as expected (5).
     *
     * @throws SQLException
     */
    @Test(timeout = 1000)
    public void testFindAll() throws SQLException {
        GroupRecordDAO groupRecordDAO = new GroupRecordDAOImpl();
        List<GroupRecord> groupRecords = groupRecordDAO.findAll();

        assertEquals("# of group records", 5, groupRecords.size());
    }
    
    /**
     * Tests the "create" method.
     * 
     * This will insert a record into the GROUP_RECORD table, retrieve the just inserted record and compare
     * the inserted and retrieved objects to see if they are the exact same.
     *
     * @throws SQLException
     */
    @Test(timeout = 1000)
    public void testCreate() throws SQLException {
        
        GroupRecordDAO groupRecordDAO = new GroupRecordDAOImpl();

        GroupRecord groupRecord = new GroupRecord();
        groupRecord.setGroupName("Quests");
        groupRecord.setColour("#aaf442");
        
        int records = groupRecordDAO.create(groupRecord);
        
        GroupRecord groupRecordCopy = groupRecordDAO.findByGroupName("Quests");
        
        assertEquals("A record was not created", 1, records);
        assertEquals("A record was not created", groupRecord, groupRecordCopy);
    }
    
    /**
     * Tests the "create" method for a String length failure.
     * 
     * This method will try to insert a record into the GROUP_RECORD table with a
     * group name that exceeds 30 characters (The max length of the GROUP_NAME field
     * is 30). An exception should be thrown.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000, expected = SQLException.class)
    public void testCreateFailureStringLength() throws SQLException {
        
        GroupRecordDAO groupRecordDAO = new GroupRecordDAOImpl();

        GroupRecord groupRecord = new GroupRecord();
        
        // The group_name column should have a character limit of 30.
        groupRecord.setGroupName("Questssssssssssssssssssssssssssssssssssssssssssssssssssss");
        groupRecord.setColour("#aaf442");
        
        groupRecordDAO.create(groupRecord);
        
        fail("The string that exceeded the length of 30 did not throw the expected exception");
    }
    
    /**
     * Tests the "update" method.
     * 
     * This will create a GroupRecord object with an ID (group_number) that's equivalent to
     * a record within the GROUP_RECORD table, then use the created object's values to update
     * that equivalent record.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testUpdate() throws SQLException {
        GroupRecordDAO groupRecordDAO = new GroupRecordDAOImpl();

        GroupRecord groupRecord = new GroupRecord();
        groupRecord.setGroupNumber(1);
        groupRecord.setGroupName("Club Activities");
        groupRecord.setColour("Some colour");
        
        int records = groupRecordDAO.update(groupRecord);
        
        GroupRecord groupRecord2 = groupRecordDAO.findID(1);
        
        assertEquals("No record was updated.", 1, records);
        assertEquals("No record was updated.", groupRecord2, groupRecord);
    }
    
    /**
     * Tests the "update" method for a String length failure.
     * 
     * This method will try to update a record of the GROUP_RECORD table with a
     * colour that exceeds 16 characters (The max length of the COLOUR field
     * is 16). An exception should be thrown.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000, expected = SQLException.class)
    public void testUpdateFailureStringLength() throws SQLException {
        
        GroupRecordDAO groupRecordDAO = new GroupRecordDAOImpl();

        GroupRecord groupRecord = new GroupRecord();
        groupRecord.setGroupNumber(1);
        groupRecord.setGroupName("Club Activities");
        // The COLOUR column should have a character limit of 16.
        groupRecord.setColour("Some colourrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
        
        groupRecordDAO.create(groupRecord);
        
        fail("The string that exceeded the length of 30 did not throw the expected exception");
    }
    
    /**
     * Tests the "findID" method.
     * 
     * Using an ID, this method will select the record within the GROUP_RECORD table with that same ID value,
     * and place the retrieved record's values into a new GroupRecord object. Finally, checks if the new object's
     * ID is as expected, to know if the findID method succeeded.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testFindID() throws SQLException
    {
        GroupRecordDAO groupRecordDAO = new GroupRecordDAOImpl();
        
        GroupRecord groupRecord = groupRecordDAO.findID(1);
        assertEquals("Couldn't find the requested record.", 1, groupRecord.getGroupNumber());
    }
    
    /**
     * Tests the "deleteGroupRecord" method.
     * 
     * Asserts that the record that's to be deleted exists, then deletes it assuming it does.
     * After, asserts that the record is no longer in the database.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testDeleteGroupRecord() throws SQLException
    {
        GroupRecordDAO groupRecordDAO = new GroupRecordDAOImpl();
        
        assertEquals("The record to be deleted never existed.", 1, groupRecordDAO.findID(1).getGroupNumber());
        
        groupRecordDAO.deleteGroupRecord(1);
        assertEquals("No record was deleted.", -1, groupRecordDAO.findID(1).getGroupNumber());
    }
    
    /**
     * Tests the "findByGroupName" method.
     * 
     * Tries to retrieve the record with the chosen group name, then places that record's values
     * into a new GroupRecord object. After, checks to see if the group name value of the new object
     * is equal to the expected group name.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testFindByGroupName() throws SQLException
    {
        GroupRecordDAO groupRecordDAO = new GroupRecordDAOImpl();
        
        GroupRecord groupRecord = groupRecordDAO.findByGroupName("Work/school");
        
        assertEquals("Couldn't find the requested record.", "work/school", groupRecord.getGroupName());
    }
    
     /**
     * The database is recreated before each test. If the last test is
     * destructive then the database is in an unstable state. @AfterClass is
     * called just once when the test class is finished with by the JUnit
     * framework. It is instantiating the test class anonymously so that it can
     * execute its non-static seedDatabase routine.
     */
    @AfterClass
    public static void seedAfterTestCompleted() {
        log.info("@AfterClass seeding");
        new TestGroupRecords().seedDatabase();
    }
    
     /**
     * This routine recreates the database before every test. This makes sure
     * that a destructive test will not interfere with any other test. Does not
     * support stored procedures.
     *
     * This routine is courtesy of Bartosz Majsak, the lead Arquillian developer
     * at JBoss
     */
    @Before
    public void seedDatabase() 
    {
        log.info("Seeding Database");
        
        final String seedDataScript = loadAsString("CreateJAMTables.sql");
        final String seedDataScript2 = loadAsString("CreateJAMMockData.sql");
        
        try (Connection connection = DriverManager.getConnection(url, user, password))
        {
            for (String statement : splitStatements(new StringReader(seedDataScript), ";")) 
            {
                connection.prepareStatement(statement).execute();
            }
            for (String statement : splitStatements(new StringReader(seedDataScript2), ";")) 
            {
                connection.prepareStatement(statement).execute();
            }
        } 
        catch (SQLException e) 
        {
            throw new RuntimeException("Failed seeding database", e);
        }
    }

    /**
     * The following methods support the seedDatabase method
     */
    private String loadAsString(final String path) 
    {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
                Scanner scanner = new Scanner(inputStream);) 
        {
            return scanner.useDelimiter("\\A").next();
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader, String statementDelimiter)
    {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        
        final StringBuilder sqlStatement = new StringBuilder();
        
        final List<String> statements = new LinkedList<>();
        
        try 
        {
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) 
                {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) 
                {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            
            return statements;
        } 
        catch (IOException e)
        {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) 
    {
        return line.startsWith("--") || line.startsWith("//") || line.startsWith("/*");
    }
}
