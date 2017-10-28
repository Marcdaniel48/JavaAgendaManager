package com.marcdanieldialogo.tests;

import com.marcdanieldialogo.entities.Appointment;
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
import java.time.DateTimeException;
import java.time.LocalDateTime;
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
 * Tests the methods of the AppointmentDAOImpl.java file.
 * 
 * @author Marc-Daniel
 */
public class TestAppointments {
    
    // Information needed to connect to the JAM database.
    private final String url = "jdbc:mysql://localhost:3306/jamdb";
    private final String user = "MarcDaniel";
    private final String password = "dbsurfing";
    
    private final static Logger log = LoggerFactory.getLogger(TestGroupRecords.class);
    
    /**
     * Tests the "create" method.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testCreate() throws SQLException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

        Appointment appointment = new Appointment();
        appointment.setTitle("Go to a park");
        appointment.setLocation("Any park");
        
        LocalDateTime startTime = LocalDateTime.of(2017, 10, 21, 10, 15);
        LocalDateTime endTime = LocalDateTime.of(2017, 10, 21, 11, 0);
        Timestamp tsStarttime = Timestamp.valueOf(startTime);
        Timestamp tsEndTime = Timestamp.valueOf(endTime);
        
        appointment.setStartTime(tsStarttime);
        appointment.setEndTime(tsEndTime);
        appointment.setDetails("Because I can.");
        appointment.setWholeDay(false);
        appointment.setAppointmentGroup(5);
        appointment.setAlarmReminder(true);
        
        int records = appointmentDAO.create(appointment);
        
        Appointment appointment2 = appointmentDAO.findByTitle("Go to a park").get(0);
        
        assertEquals("A record was not created", records, 1);
        assertEquals("A record was not created", appointment, appointment2);
    }
    
    /**
     * Tests the "create" method for a String length failure.
     * 
     * Tries to insert an appointment record with a title that's exceeding 30 characters
     * (The max character length for a title is 30).
     *
     * @throws SQLException
     */
    @Test(timeout = 1000, expected = SQLException.class)
    public void testCreateFailureStringLength() throws SQLException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

        Appointment appointment = new Appointment();
        
        appointment.setTitle("Go to a parkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        appointment.setLocation("Any park");
        
        LocalDateTime startTime = LocalDateTime.of(2017, 10, 21, 10, 15);
        LocalDateTime endTime = LocalDateTime.of(2017, 10, 21, 11, 0);
        Timestamp tsStarttime = Timestamp.valueOf(startTime);
        Timestamp tsEndTime = Timestamp.valueOf(endTime);
        
        appointment.setStartTime(tsStarttime);
        appointment.setEndTime(tsEndTime);
        appointment.setDetails("Because I can.");
        appointment.setWholeDay(false);
        appointment.setAppointmentGroup(5);
        appointment.setAlarmReminder(true);
        
        int records = appointmentDAO.create(appointment);
        
        fail("The string that exceeded the length of 30 did not throw the expected exception");
    }
    
    /**
     * Tests the "findID" method.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testFindID() throws SQLException
    {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        
        Appointment appointment = appointmentDAO.findID(1);
        
        assertEquals("Couldn't find the requested record.", 1, appointment.getAppointmentID());
        assertEquals("Couldn't find the requested record.", "Dentist appointment", appointment.getTitle());
    }
    
    /**
     * Tests the "findAll" method.
     *
     * @throws SQLException
     */
    @Test(timeout = 1000)
    public void testFindAll() throws SQLException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        List<Appointment> appointments = appointmentDAO.findAll();

        assertEquals("# of group records", 50, appointments.size());
    }
    
    /**
     * Tests the "update" method.
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testUpdate() throws SQLException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        
        // Asserts the original values of the record to be updated.
        assertEquals("The record to be updated doesn't exist.", "Dentist appointment", appointmentDAO.findID(1).getTitle());
        assertEquals("The record to be updated doesn't exist.", "Gotta go to the dentist", appointmentDAO.findID(1).getDetails());
        assertEquals("The record to be updated doesn't exist.", "The Dentist's Office", appointmentDAO.findID(1).getLocation());
        
        Appointment appointment = new Appointment();
        
        appointment.setAppointmentID(1);
        appointment.setTitle("Cancel dentist appointment");
        appointment.setLocation(appointmentDAO.findID(1).getLocation());
        appointment.setStartTime(appointmentDAO.findID(1).getStartTime());
        appointment.setEndTime(appointmentDAO.findID(1).getEndTime());
        appointment.setDetails("I won't go today");
        appointment.setWholeDay(appointmentDAO.findID(1).getWholeDay());
        appointment.setAlarmReminder(appointmentDAO.findID(1).getAlarmReminder());
        
        int records = appointmentDAO.update(appointment);
        
        // Checks to see if the int record is at 1, because if it was at 0, then that should indicate that no updates have been made.
        assertEquals("The record wasn't updated", 1, records);
        
        assertEquals("The record wasn't updated", "Cancel dentist appointment", appointmentDAO.findID(1).getTitle());
        assertEquals("The record wasn't updated", "I won't go today", appointmentDAO.findID(1).getDetails());
        
        // Checks to see if the LOCATION field hasn't been changed.
        assertEquals("The record to be updated doesn't exist.", "The Dentist's Office", appointmentDAO.findID(1).getLocation());
    }
    
    /**
     * Tests the "update" method for a String length failure.
     * 
     * Tries to update an appointment record, with a location that's exceeding 30 characters
     * (The max character length for a location is 30).
     *
     * @throws SQLException
     */
    @Test(timeout = 1000, expected = SQLException.class)
    public void testUpdateFailureStringLength() throws SQLException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        
        // Asserts the original values of the record to be updated.
        assertEquals("The record to be updated doesn't exist.", "Dentist appointment", appointmentDAO.findID(1).getTitle());
        assertEquals("The record to be updated doesn't exist.", "Gotta go to the dentist", appointmentDAO.findID(1).getDetails());
        assertEquals("The record to be updated doesn't exist.", "The Dentist's Office", appointmentDAO.findID(1).getLocation());
        
        Appointment appointment = new Appointment();
        
        appointment.setAppointmentID(1);
        appointment.setTitle("Cancel dentist appointment");
        appointment.setLocation("Anywhere with a dentist............................................................");
        appointment.setStartTime(appointmentDAO.findID(1).getStartTime());
        appointment.setEndTime(appointmentDAO.findID(1).getEndTime());
        appointment.setDetails("I won't go today");
        appointment.setWholeDay(appointmentDAO.findID(1).getWholeDay());
        appointment.setAlarmReminder(appointmentDAO.findID(1).getAlarmReminder());
        
        int records = appointmentDAO.update(appointment);
        
        fail("The string that exceeded the length of 30 did not throw the expected exception");
    }
    
    /**
     * Tests the "delete" method.
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testDeleteGroupRecord() throws SQLException
    {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        
        assertEquals("The record to be deleted never existed.", 1, appointmentDAO.findID(1).getAppointmentID());
        
        appointmentDAO.delete(1);
        assertEquals("No record was deleted.", -1, appointmentDAO.findID(1).getAppointmentID());
    }
    
    /**
     * Tests the "findByTitle" method.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testFindByTitle() throws SQLException
    {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        
        Appointment appointment = appointmentDAO.findByTitle("Dentist appointment").get(0);
        
        assertEquals("Couldn't find the requested record.", "Dentist appointment", appointment.getTitle());
    }
    
    /**
     * Tests the "findByDate" method.
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testFindByDate() throws SQLException
    {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        
        LocalDateTime date = LocalDateTime.of(2018, 1, 20, 14, 0);
        
        List<Appointment> appointments = appointmentDAO.findByDate(date);
        
        assertEquals("Couldn't find any matching records", 1, appointments.size());
        
        // The expected title of the appointment with ID 1
        assertEquals("Couldn't find any matching records", "Dentist appointment", appointments.get(0).getTitle());
        
        // More test cases
        date = LocalDateTime.of(2018, 2, 11, 14, 30);
        appointments = appointmentDAO.findByDate(date);
        assertEquals("The number of records returned does not match expected", 1, appointments.size());
        
        date = LocalDateTime.of(2018, 3, 1, 12, 59);
        appointments = appointmentDAO.findByDate(date);
        // There is not appointment at time 12:59, but there is one at 13:00. The findByDate method will also return
        // appointments that are 1 minute ahead of time.
        assertEquals("The number of records returned does not match expected", 1, appointments.size());
    }
    
    /**
     * Tests the "findByDate" method for a failure.
     * 
     * Tries to create a LocalDateTime object with an illegal value (Sets the year to 2018, but with a month of -1).
     * More of a LocalDatetime failure than a findByDate failure.
     * 
     * @throws SQlException, DateTimeException
     */
    @Test(timeout = 1000, expected = DateTimeException.class)
    public void testFindByDateFailure() throws SQLException, DateTimeException {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        
        LocalDateTime date = LocalDateTime.of(2018, -1, 20, 14, 0);
        
        List<Appointment> appointments = appointmentDAO.findByDate(date);
        
        fail("An illegal value has been used for the LocalDateTime object.");
    }
    
    @Test(timeout = 1000)
    public void testFindByDay() throws SQLException
    {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        
        LocalDateTime date = LocalDateTime.of(2018, 2, 12, 0, 0);
        
        List<Appointment> appointments = appointmentDAO.findByDay(date);
        
        assertEquals("The number of records returned does not match expected", 3, appointments.size());
        assertEquals("Retrieved an unexpected record", 28, appointments.get(0).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 29, appointments.get(1).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 30, appointments.get(2).getAppointmentID());
        
        // More test cases
        date = LocalDateTime.of(2017, 9, 12, 1, 1);
        appointments = appointmentDAO.findByDay(date);
        assertEquals("The number of records returned does not match expected", 1, appointments.size());
        
        date = LocalDateTime.of(2017, 1, 18, 0, 0);
        appointments = appointmentDAO.findByDay(date);
        assertEquals("The number of records returned does not match expected", 0, appointments.size());
    }
    
    /**
     * Tests the "findByWeek" method.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testFindByWeek() throws SQLException
    {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        
        LocalDateTime date = LocalDateTime.of(2018, 1, 24, 8, 30);
        
        List<Appointment> appointments = appointmentDAO.findByWeek(date);
        
        assertEquals("Number of records returned do not match expected", 7, appointments.size());
        
        // The expected IDs of the expected returned records
        assertEquals("Retrieved an unexpected record", 2, appointments.get(0).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 3, appointments.get(1).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 4, appointments.get(2).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 5, appointments.get(3).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 6, appointments.get(4).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 7, appointments.get(5).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 8, appointments.get(6).getAppointmentID());
        
        // More test cases
        date = LocalDateTime.of(2018, 2, 2, 13, 0);
        appointments = appointmentDAO.findByWeek(date);
        assertEquals("The number of records returned does not match expected", 7, appointments.size());
        
        date = LocalDateTime.of(2017, 10, 22, 0, 0);
        appointments = appointmentDAO.findByWeek(date);
        assertEquals("The number of records returned does not match expected", 1, appointments.size());
    }
    
    /**
     * Tests the "findByMonth" method.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testFindByMonth() throws SQLException
    {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        
        LocalDateTime date = LocalDateTime.of(2018, 1, 1, 0, 0);
        
        List<Appointment> appointments = appointmentDAO.findByMonth(date);
        
        assertEquals("Number of records retrieved do not match expected", 11, appointments.size());
        
        // These are the expected IDs of the expected returned records
        assertEquals("Retrieved an unexpected record", 1, appointments.get(0).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 2, appointments.get(1).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 3, appointments.get(2).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 4, appointments.get(3).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 5, appointments.get(4).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 6, appointments.get(5).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 7, appointments.get(6).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 8, appointments.get(7).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 9, appointments.get(8).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 10, appointments.get(9).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 11, appointments.get(10).getAppointmentID());
        
        // More test cases
        date = LocalDateTime.of(2017, 9, 30, 23, 59);
        appointments = appointmentDAO.findByMonth(date);
        assertEquals("The number of records returned does not match expected", 3, appointments.size());
        
        date = LocalDateTime.of(2018, 2, 28, 23, 59);
        appointments = appointmentDAO.findByMonth(date);
        assertEquals("The number of records returned does not match expected", 34, appointments.size());
    }
    
    /**
     * Tests the "findBetweenDates" method.
     * 
     * @throws SQLException 
     */
    @Test(timeout = 1000)
    public void testFindBetweenDates() throws SQLException
    {
        AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
        
        LocalDateTime date1 = LocalDateTime.of(2018, 1, 29, 0, 0);
        LocalDateTime date2 = LocalDateTime.of(2018, 2, 3, 0, 0);
        
        List<Appointment> appointments = appointmentDAO.findBetweenDates(date1, date2);
        
        assertEquals("Number of records retrieved does not match expected", 7, appointments.size());
        
        // These are the expected IDs of the expected returned records
        assertEquals("Retrieved an unexpected record", 9, appointments.get(0).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 10, appointments.get(1).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 11, appointments.get(2).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 12, appointments.get(3).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 13, appointments.get(4).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 14, appointments.get(5).getAppointmentID());
        assertEquals("Retrieved an unexpected record", 19, appointments.get(6).getAppointmentID());
        
        // More test cases
        date1 = LocalDateTime.of(2017, 1, 1, 0, 0);
        date2 = LocalDateTime.of(2020, 4, 10, 6, 45);
        appointments = appointmentDAO.findBetweenDates(date1, date2);
        assertEquals("The number of records returned does not match expected", 50, appointments.size());
        
        // Note: The findBetweenDates method doesn't care about the time.
        date1 = LocalDateTime.of(2018, 2, 12, 9, 0);
        date2 = LocalDateTime.of(2018, 2, 12, 13, 0);
        appointments = appointmentDAO.findBetweenDates(date1, date2);
        assertEquals("The number of records returned does not match expected", 3, appointments.size());
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
        new TestAppointments().seedDatabase();
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
