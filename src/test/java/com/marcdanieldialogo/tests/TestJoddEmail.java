package com.marcdanieldialogo.tests;

import com.marcdanieldialogo.util.Utilities;
import com.marcdanieldialogo.entities.Appointment;
import com.marcdanieldialogo.email.JoddEmail;
import com.marcdanieldialogo.entities.SMTPSettings;
import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import com.marcdanieldialogo.persistence.SMTPSettingsDAO;
import com.marcdanieldialogo.persistence.SMTPSettingsDAOImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Marc-Daniel
 */
public class TestJoddEmail {
    
    // Information needed to connect to the JAM database.
    Utilities util = new Utilities();
    
    private final static Logger log = LoggerFactory.getLogger(TestGroupRecords.class);
    
    
    @Test
    public void mailTest() throws SQLException
    {
        // Creates 4 appointments that start 120 minutes from now.
        createMail();
        
        // The email that's being used to send the emails is the one found in the default SMTP settings record.
        JoddEmail emailObj = new JoddEmail();
        
        // Will use the reminder interval of the default SMTP Settings record, which is 120 minutes.
        // This method makes sure to only find appointments with alarm reminder set to true.
        List<Appointment> appointmentsToSend = emailObj.findEmailsByInterval();
        
        // Sends an email for each appointment in the appointmentsToSend list
        emailObj.sendEmailList(appointmentsToSend);
        
        // Makes sure that there are only 3 appointments that have been found
        assertEquals(3, appointmentsToSend.size());
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
        new TestJoddEmail().seedDatabase();
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
        
        try (Connection connection = util.getConnection())
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
    
    /**
     * Creates 3 test appointments that start 120 minutes from the current time.
     * @throws SQLException 
     */
    private void createMail() throws SQLException
    {
        LocalDateTime twoHoursFromNow = LocalDateTime.now().plusMinutes(120);
        
        Appointment appointment1 = new Appointment();
        appointment1.setTitle("Test appointment #1");
        appointment1.setAlarmReminder(Boolean.TRUE);
        appointment1.setStartTime(Timestamp.valueOf(twoHoursFromNow));
        appointment1.setLocation(null);
        appointment1.setDetails(null);
        appointment1.setEndTime(Timestamp.valueOf(twoHoursFromNow.plusMinutes(30)));
        appointment1.setWholeDay(Boolean.FALSE);
        appointment1.setAppointmentGroup(0);
        
        Appointment appointment2 = new Appointment();
        appointment2.setTitle("Test appointment #2");
        appointment2.setLocation("Somewhere");
        appointment2.setAlarmReminder(Boolean.TRUE);
        appointment2.setStartTime(Timestamp.valueOf(twoHoursFromNow));
        appointment2.setDetails(null);
        appointment2.setEndTime(Timestamp.valueOf(twoHoursFromNow.plusMinutes(30)));
        appointment2.setWholeDay(Boolean.FALSE);
        appointment2.setAppointmentGroup(0);
        
        Appointment appointment3 = new Appointment();
        appointment3.setTitle("Test appointment #3");
        appointment3.setLocation("Someplace");
        appointment3.setDetails("Not really an appointment");
        appointment3.setAlarmReminder(Boolean.TRUE);
        appointment3.setStartTime(Timestamp.valueOf(twoHoursFromNow));
        appointment3.setEndTime(Timestamp.valueOf(twoHoursFromNow.plusMinutes(30)));
        appointment3.setWholeDay(Boolean.FALSE);
        appointment3.setAppointmentGroup(0);
        
        Appointment appointment4 = new Appointment();
        appointment4.setTitle("Test appointment #4");
        appointment4.setLocation("The Void");
        appointment4.setDetails("Alarm is set to false. This appointment shouldn't be sent.");
        appointment4.setAlarmReminder(Boolean.FALSE);
        appointment4.setStartTime(Timestamp.valueOf(twoHoursFromNow));
        appointment4.setEndTime(Timestamp.valueOf(twoHoursFromNow.plusMinutes(30)));
        appointment4.setWholeDay(Boolean.FALSE);
        appointment4.setAppointmentGroup(0);
        
        AppointmentDAO dao = new AppointmentDAOImpl();
        
        dao.create(appointment1);
        dao.create(appointment2);
        dao.create(appointment3);
        dao.create(appointment4);
    }
}
