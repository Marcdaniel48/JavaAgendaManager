package com.marcdanieldialogo.tests;

import com.marcdanieldialogo.entities.SMTPSettings;
import com.marcdanieldialogo.persistence.SMTPSettingsDAO;
import com.marcdanieldialogo.persistence.SMTPSettingsDAOImpl;
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
 * Tests the methods of the SMTPSettingsDAOImpl.java file.
 * 
 * @author Marc-Daniel
 */
public class TestSMTPSettings {
    
    // Information needed to connect to the JAM database.
    private final String url = "jdbc:mysql://localhost:3306/jamdb?autoReconnect=true&useSSL=false";
    private final String user = "MarcDaniel";
    private final String password = "dbsurfing";
    
    private final static Logger log = LoggerFactory.getLogger(TestGroupRecords.class);
    
    
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
        SMTPSettingsDAO smtpDAO = new SMTPSettingsDAOImpl();

        SMTPSettings smtp = new SMTPSettings();
        smtp.setUsername("TheKING");
        smtp.setEmail("ImSoCool@TheBestGuyEver.king");
        smtp.setEmailPassword("bro");
        smtp.setSMTPURL("The Best SMTP URL Ever");
        smtp.setSMTPPort(1337);
        smtp.setDefaultSMTP(false);
        smtp.setReminderInterval(0);
        
        int records = smtpDAO.create(smtp);
        
        SMTPSettings smtp2 = smtpDAO.findByEmail("ImSoCool@TheBestGuyEver.king");
        
        assertEquals("A record was not created", records, 1);
        assertEquals("A record was not created", smtp, smtp2);
    }
    
    /**
     * Tests the "create" method for a String length failure.
     * 
     * This method will attempt to insert a record into the SMTP_SETTINGS table with
     * a user name longer than 60 characters (The character limit of a username is 60).
     *
     * @throws SQLException
     */
    @Test(timeout = 1000, expected = SQLException.class)
    public void testCreateFailureStringLength() throws SQLException {
        SMTPSettingsDAO smtpDAO = new SMTPSettingsDAOImpl();

        SMTPSettings smtp = new SMTPSettings();
        
        // The character limit of USERNAME should be 60.
        smtp.setUsername("TheKINGgggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");
        smtp.setEmail("ImSoCool@TheBestGuyEver.king");
        smtp.setEmailPassword("bro");
        smtp.setSMTPURL("The Best SMTP URL Ever");
        smtp.setSMTPPort(1337);
        smtp.setDefaultSMTP(false);
        smtp.setReminderInterval(0);
        
        smtpDAO.create(smtp);
        
        fail("A string that should have exceeded it's limit did not throw an exception");
    }
    
     /**
     * Tests the "findAll" method.
     * 
     * Tests to see if the number of records within the GROUP_RECORD table is as expected (2).
     *
     * @throws SQLException
     */
    @Test(timeout = 1000)
    public void testFindAll() throws SQLException {
        SMTPSettingsDAO smtpDAO = new SMTPSettingsDAOImpl();
        List<SMTPSettings> smtpList = smtpDAO.findAll();

        assertEquals("# of group records", 2, smtpList.size());
    }
    
     /**
     * Tests the "findID" method.
     * 
     * Attempts to retrieve a record within the table with a specified ID, then checks if the
     * retrieved record's ID is the same as the specified and expected ID.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testFindID() throws SQLException
    {
        SMTPSettingsDAO smtpDAO = new SMTPSettingsDAOImpl();
        
        SMTPSettings smtp = smtpDAO.findID(1);
        assertEquals("Couldn't find the requested record.", 1, smtp.getSMTPID());
    }
    
    /**
     * Tests the "update" method.
     * 
     * Attempts to update the record within the database that has the same ID
     * as the parameter (a specified ID), then checks to see if the record's
     * values have been changed.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testUpdate() throws SQLException {
        SMTPSettingsDAO smtpDAO = new SMTPSettingsDAOImpl();

        SMTPSettings smtp = new SMTPSettings();
        smtp.setSMTPID(1);
        smtp.setUsername("BOBBY");
        smtp.setEmail("BOBBY@BOBZ.BOB");
        smtp.setEmailPassword("bobby123");
        smtp.setSMTPURL("smtp.bobz.bob");
        smtp.setSMTPPort(808);
        smtp.setDefaultSMTP(false);
        smtp.setReminderInterval(smtpDAO.findID(1).getReminderInterval());
        
        int records = smtpDAO.update(smtp);
        
        SMTPSettings smtp2 = smtpDAO.findID(1);
        
        assertEquals("No record was updated.", 1, records);
        assertEquals("No record was updated.", smtp2, smtp);
    }
    
    /**
     * Tests the "update" method for a String length failure.
     * 
     * This method will attempt to update a record of the SMTP_SETTINGS table with
     * a password longer than 100 characters (The character limit of a password is 100 characters).
     *
     * @throws SQLException
     */
    @Test(timeout = 1000, expected = SQLException.class)
    public void testUpdateFailureStringLength() throws SQLException {
        SMTPSettingsDAO smtpDAO = new SMTPSettingsDAOImpl();

        SMTPSettings smtp = new SMTPSettings();
        smtp.setSMTPID(1);
        smtp.setUsername("BOBBY");
        smtp.setEmail("BOBBY@BOBZ.BOB");
        
        // The character limit of EMAIL_PASSWORD should be 100.
        smtp.setEmailPassword("bobby1233333333333333333333333333333333333333333333333333333333333333333333333333333333333333"
                + "3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
        
        smtp.setSMTPURL("smtp.bobz.bob");
        smtp.setSMTPPort(808);
        
        smtpDAO.create(smtp);
        
        fail("A string that should have exceeded it's limit did not throw an exception");
    }
    
    /**
     * Tests the "delete" method.
     * 
     * Tests to see if the record up for deletion exists within the database, and if it does, check if it no longer does,
     * after the delete method has been run.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testDelete() throws SQLException
    {
        SMTPSettingsDAO smtpDAO = new SMTPSettingsDAOImpl();
        
        assertEquals("The record to be deleted never existed.", 1, smtpDAO.findID(1).getSMTPID());
        
        smtpDAO.delete(1);
        
        assertEquals("No record was deleted.", -1, smtpDAO.findID(1).getSMTPID());
    }
    
    /**
     * Tests the "findByEmail" method works.
     * 
     * Tests to see if, given a String email, the record within the database with the same email value
     * can be retrieved. The test is a success if the values of the retrieved object's fields are as
     * expected.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testFindByEmail() throws SQLException
    {
        SMTPSettingsDAO smtpDAO = new SMTPSettingsDAOImpl();
        
        SMTPSettings smtp = smtpDAO.findByEmail("JAMEmailReminder@gmail.com");
        
        assertEquals("Couldn't find the requested record.", 1, smtp.getSMTPID());
        assertEquals("Couldn't find the requested record.", "JAMEmailReminder@gmail.com", smtp.getEmail());
        assertEquals("Couldn't find the requested record.", "thunderboltx", smtp.getEmailPassword());
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
        new TestSMTPSettings().seedDatabase();
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
