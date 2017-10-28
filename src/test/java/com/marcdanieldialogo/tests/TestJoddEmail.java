package com.marcdanieldialogo.tests;

import com.marcdanieldialogo.entities.Appointment;
import com.marcdanieldialogo.JoddEmail;
import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
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
    private final String url = "jdbc:mysql://localhost:3306/jamdb?autoReconnect=true&useSSL=false";
    private final String user = "MarcDaniel";
    private final String password = "dbsurfing";
    
    private final static Logger log = LoggerFactory.getLogger(TestGroupRecords.class);
    
    
    @Test
    public void mailTest() throws SQLException
    {
        createMail();
        List<Appointment> mail = findEmails();
        sendEmails(mail);
        
        // Makes sure that there are only 3 appointments that have been found
        assertEquals(3, mail.size());
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
        
        AppointmentDAO dao = new AppointmentDAOImpl();
        
        dao.create(appointment1);
        dao.create(appointment2);
        dao.create(appointment3);
    }
    
    /**
     * Will return any appointments that start 120 minutes from now & have their alarm reminder set to true.
     * 
     * @return List<Appointment>
     * @throws SQLException 
     */
    private List<Appointment> findEmails() throws SQLException
    {
        AppointmentDAO dao = new AppointmentDAOImpl();
        
        List<Appointment> appointments = dao.findByDate(LocalDateTime.now().plusMinutes(120));
        
        for(int i = 0; i < appointments.size(); i++)
        {
            if(appointments.get(i).getAlarmReminder() == false)
            {
                appointments.remove(i);
            }
        }
        
        return appointments;
    }
    
    /**
     * Takes in a list of appointments and for each appointment, an email
     * describing the appointment will be sent.
     * 
     * @param mail 
     */
    private void sendEmails(List<Appointment> mail)
    {
        JoddEmail jodd = new JoddEmail();
        
        for(Appointment appointment : mail)
        {
            jodd.sendAppointmentEmail(appointment);
        }
    }
}
