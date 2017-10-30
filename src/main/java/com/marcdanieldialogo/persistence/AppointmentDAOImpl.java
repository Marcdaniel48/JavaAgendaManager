package com.marcdanieldialogo.persistence;

import com.marcdanieldialogo.entities.Appointment;
import com.marcdanieldialogo.util.Utilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the AppointmentDAO interface
 * 
 * @author Marc-Daniel
 */
public class AppointmentDAOImpl implements AppointmentDAO{

    private final Logger log = LoggerFactory.getLogger(
            this.getClass().getName());
    
    // Has a method that can make and return a connection.
    private Utilities util = new Utilities();
    
    /**
     * Takes in an Appointment object and uses it's values to insert a record into the
     * APPOINTMENT table.
     * 
     * @param appointment
     * @return records  The number of records that have been created/inserted. Should be either 0 or 1.
     * @throws SQLException 
     */
    @Override
    public int create(Appointment appointment) throws SQLException {
        int records;

        String sql = "INSERT INTO APPOINTMENT(TITLE, LOCATION, START_TIME, END_TIME, DETAILS, WHOLE_DAY,"
                + "APPOINTMENT_GROUP, ALARM_REMINDER) values (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection conn = util.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);)
        {
            stmt.setString(1, appointment.getTitle());
            stmt.setString(2, appointment.getLocation());
            stmt.setTimestamp(3, appointment.getStartTime());
            stmt.setTimestamp(4, appointment.getEndTime());
            stmt.setString(5, appointment.getDetails());
            stmt.setBoolean(6, appointment.getWholeDay());
            stmt.setInt(7, appointment.getAppointmentGroup());
            stmt.setBoolean(8, appointment.getAlarmReminder());
            
            records = stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            
            int recordNum = -1;
            
            if (rs.next()) {
                recordNum = rs.getInt(1);
            }
            
            appointment.setAppointmentID(recordNum);
            
            log.debug("New record ID is " + recordNum);
            
        }
        
        return records;
    }

    /**
     * Selects all the records of the APPOINTMENT table and uses its values to
     * create a list of Appointment objects which will be returned.
     * 
     * @return rows A list of Appointment objects which represents the individual records of 
     * the APPOINTMENT table
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findAll() throws SQLException {
        List<Appointment> rows = new ArrayList<>();

        String selectQuery = "SELECT * FROM APPOINTMENT";
        try (Connection connection = util.getConnection();
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);
                ResultSet results = pStatement.executeQuery()) 
        {
            while (results.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(results.getInt("APPOINTMENT_ID"));
                appointment.setTitle(results.getString("TITLE"));
                appointment.setLocation(results.getString("LOCATION"));
                appointment.setStartTime(results.getTimestamp("START_TIME"));
                appointment.setEndTime(results.getTimestamp("END_TIME"));
                appointment.setDetails(results.getString("DETAILS"));
                appointment.setWholeDay(results.getBoolean("WHOLE_DAY"));
                appointment.setAppointmentGroup(results.getInt("APPOINTMENT_GROUP"));
                appointment.setAlarmReminder(results.getBoolean("ALARM_REMINDER"));
                
                rows.add(appointment);
            }
        }
        return rows;
    }

    /**
     * Selects a record of the APPOINTMENT table where it's ID value is the same as the ID parameter.
     * The values of the retrieved record will be used to fill in an Appointment object which will
     * then be returned.
     * @param id    The ID of an appointment record.
     * @return  appointment An Appointment object with the same values as a record of the APPOINTMENT
     * table.
     * @throws SQLException 
     */
    @Override
    public Appointment findID(int id) throws SQLException {
        Appointment appointment = new Appointment();
        
        String selectQuery = "SELECT * FROM APPOINTMENT WHERE APPOINTMENT_ID = ?";
        
        try (Connection connection = util.getConnection();
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setInt(1, id);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    appointment.setAppointmentID(resultSet.getInt("APPOINTMENT_ID"));
                    appointment.setTitle(resultSet.getString("TITLE"));
                    appointment.setLocation(resultSet.getString("LOCATION"));
                    appointment.setStartTime(resultSet.getTimestamp("START_TIME"));
                    appointment.setEndTime(resultSet.getTimestamp("END_TIME"));
                    appointment.setDetails(resultSet.getString("DETAILS"));
                    appointment.setWholeDay(resultSet.getBoolean("WHOLE_DAY"));
                    appointment.setAppointmentGroup(resultSet.getInt("APPOINTMENT_GROUP"));
                    appointment.setAlarmReminder(resultSet.getBoolean("ALARM_REMINDER"));
                }
            }
        }
        return appointment;
    }

    /**
     * Takes in an Appointment object and uses it's values to update the record of the
     * APPOINTMENT table with the same ID value as the object.
     * 
     * @param appointment
     * @return record   The number of updated records. Should be either 0 or 1.
     * @throws SQLException 
     */
    @Override
    public int update(Appointment appointment) throws SQLException {
        int record = 0;
        String updateStatement = "UPDATE APPOINTMENT SET TITLE = ?, LOCATION = ?, START_TIME = ?, END_TIME = ?, DETAILS = ?, WHOLE_DAY = ?, APPOINTMENT_GROUP = ?,"
                + " ALARM_REMINDER = ? WHERE APPOINTMENT_ID = ?";
        
        try
        (Connection connection = util.getConnection(); PreparedStatement pStatement = connection.prepareStatement(updateStatement);)
        {
            pStatement.setString(1, appointment.getTitle());
            pStatement.setString(2, appointment.getLocation());
            pStatement.setTimestamp(3, appointment.getStartTime());
            pStatement.setTimestamp(4, appointment.getEndTime());
            pStatement.setString(5, appointment.getDetails());
            pStatement.setBoolean(6, appointment.getWholeDay());
            pStatement.setInt(7, appointment.getAppointmentGroup());
            pStatement.setBoolean(8, appointment.getAlarmReminder());
            pStatement.setInt(9, appointment.getAppointmentID());
            
            
            pStatement.executeUpdate();
            record = 1;
        }
        
        return record;
    }

    /**
     * Takes in an int ID and uses it to delete the record of the APPOINTMENT table with the same
     * ID.
     * 
     * @param ID
     * @return  record  The number of deleted records. Should be either 0 or 1.
     * @throws SQLException 
     */
    @Override
    public int delete(int ID) throws SQLException {
        int record = 0;
        String sql = "DELETE FROM APPOINTMENT WHERE APPOINTMENT_ID = ?";
        
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
     * Takes in a String representing the title of an appointment and uses it to retrieve any
     * records of the APPOINTMENT table with the same title.
     * 
     * @param title
     * @return appointments A list of Appointment objects with the same title as the parameter.
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findByTitle(String title) throws SQLException
    {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM APPOINTMENT WHERE TITLE = ?"; 
        
        try
        (Connection connection = util.getConnection(); PreparedStatement pStatement = connection.prepareStatement(sql);)
        {
            pStatement.setString(1, title);
            
            ResultSet resultSet = pStatement.executeQuery();
            
             if (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentID(resultSet.getInt("APPOINTMENT_ID"));
                    appointment.setTitle(resultSet.getString("TITLE"));
                    appointment.setLocation(resultSet.getString("LOCATION"));
                    appointment.setStartTime(resultSet.getTimestamp("START_TIME"));
                    appointment.setEndTime(resultSet.getTimestamp("END_TIME"));
                    appointment.setDetails(resultSet.getString("DETAILS"));
                    appointment.setWholeDay(resultSet.getBoolean("WHOLE_DAY"));
                    appointment.setAppointmentGroup(resultSet.getInt("APPOINTMENT_GROUP"));
                    appointment.setAlarmReminder(resultSet.getBoolean("ALARM_REMINDER"));
                    
                    appointments.add(appointment);
                }
        }
        
        return appointments;
    }
    
    /**
     * Takes in a LocalDateTime object and uses it to retrieve any records of the 
     * APPOINTMENT table that's currently active during the LocalDateTime's day and time
     * (day, hour, minutes).
     * 
     * @param date
     * @return appointments
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findByDate(LocalDateTime date) throws SQLException
    {
        List<Appointment> appointments = new ArrayList<>();
        
        date = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute());
        
        
        String sql = "SELECT * FROM APPOINTMENT WHERE START_TIME BETWEEN ? AND ?";
        
        try
        (Connection connection = util.getConnection(); PreparedStatement pStatement = connection.prepareStatement(sql);)
        {
            pStatement.setTimestamp(1, Timestamp.valueOf(date));
            pStatement.setTimestamp(2, Timestamp.valueOf(date.plusMinutes(1)));
            
            ResultSet resultSet = pStatement.executeQuery();
            
             while (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentID(resultSet.getInt("APPOINTMENT_ID"));
                    appointment.setTitle(resultSet.getString("TITLE"));
                    appointment.setLocation(resultSet.getString("LOCATION"));
                    appointment.setStartTime(resultSet.getTimestamp("START_TIME"));
                    appointment.setEndTime(resultSet.getTimestamp("END_TIME"));
                    appointment.setDetails(resultSet.getString("DETAILS"));
                    appointment.setWholeDay(resultSet.getBoolean("WHOLE_DAY"));
                    appointment.setAppointmentGroup(resultSet.getInt("APPOINTMENT_GROUP"));
                    appointment.setAlarmReminder(resultSet.getBoolean("ALARM_REMINDER"));
                    
                    appointments.add(appointment);
                }
        }
        
        return appointments;
    }

    /**
     * Takes in a LocalDateTime object and uses it to retrieve any records of the 
     * APPOINTMENT table that takes place during the same day as the LocalDateTime object.
     * 
     * @param date
     * @return appointments
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findByDay(LocalDateTime date) throws SQLException {
        date = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0);
        
        Timestamp day = Timestamp.valueOf(date);
        
        date = date.plusHours(23);
        date = date.plusMinutes(59);
        date = date.plusSeconds(59);
        
        Timestamp endOfDay = Timestamp.valueOf(date);
        
        List<Appointment> appointments = new ArrayList<>();
        
        String sql = "SELECT * FROM APPOINTMENT WHERE START_TIME BETWEEN ? AND ?";
        
        try
        (Connection connection = util.getConnection(); PreparedStatement pStatement = connection.prepareStatement(sql);)
        {
            pStatement.setTimestamp(1, day);
            pStatement.setTimestamp(2, endOfDay);
            
            ResultSet resultSet = pStatement.executeQuery();
            
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentID(resultSet.getInt("APPOINTMENT_ID"));
                appointment.setTitle(resultSet.getString("TITLE"));
                appointment.setLocation(resultSet.getString("LOCATION"));
                appointment.setStartTime(resultSet.getTimestamp("START_TIME"));
                appointment.setEndTime(resultSet.getTimestamp("END_TIME"));
                appointment.setDetails(resultSet.getString("DETAILS"));
                appointment.setWholeDay(resultSet.getBoolean("WHOLE_DAY"));
                appointment.setAppointmentGroup(resultSet.getInt("APPOINTMENT_GROUP"));
                appointment.setAlarmReminder(resultSet.getBoolean("ALARM_REMINDER"));

                appointments.add(appointment);
            }
        }
        
        return appointments;
    }
    
    /**
     * Takes in a LocalDateTime object and uses it to retrieve any records of the 
     * APPOINTMENT table that takes place during the same week as the LocalDateTime object.
     * 
     * @param date
     * @return appointments
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findByWeek(LocalDateTime date) throws SQLException
    {
        List<Appointment> appointments = new ArrayList<>();
        
        date = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0, 0);
        
        while(date.getDayOfWeek() != DayOfWeek.MONDAY)
        {
            date = date.minusDays(1);
        }
        Timestamp monday = Timestamp.valueOf(date);
        
        while(date.getDayOfWeek() != DayOfWeek.SUNDAY)
        {
            date = date.plusDays(1);
        }
        
        date = date.plusHours(23);
        date = date.plusMinutes(59);
        date = date.minusMinutes(59);
        
        Timestamp sunday = Timestamp.valueOf(date);
        

        String sql = "SELECT * FROM APPOINTMENT WHERE START_TIME BETWEEN ? AND ?";
        
        try
        (Connection connection = util.getConnection(); PreparedStatement pStatement = connection.prepareStatement(sql);)
        {
            pStatement.setTimestamp(1, monday);
            pStatement.setTimestamp(2, sunday);
            
            ResultSet resultSet = pStatement.executeQuery();
            
             while (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentID(resultSet.getInt("APPOINTMENT_ID"));
                    appointment.setTitle(resultSet.getString("TITLE"));
                    appointment.setLocation(resultSet.getString("LOCATION"));
                    appointment.setStartTime(resultSet.getTimestamp("START_TIME"));
                    appointment.setEndTime(resultSet.getTimestamp("END_TIME"));
                    appointment.setDetails(resultSet.getString("DETAILS"));
                    appointment.setWholeDay(resultSet.getBoolean("WHOLE_DAY"));
                    appointment.setAppointmentGroup(resultSet.getInt("APPOINTMENT_GROUP"));
                    appointment.setAlarmReminder(resultSet.getBoolean("ALARM_REMINDER"));
                    
                    appointments.add(appointment);
                }
        }
        
        return appointments;
    }

    /**
     * Takes in a LocalDateTime object and uses it to retrieve any records of the 
     * APPOINTMENT table that takes place during the same month as the LocalDateTime object.
     * 
     * @param date
     * @return appointments
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findByMonth(LocalDateTime date) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        
        
        String sql = "SELECT * FROM APPOINTMENT WHERE MONTH(START_TIME) = ? AND YEAR(START_TIME) = ?";
        
        int year = date.getYear();
        int month = date.getMonthValue();
        
        try
        (Connection connection = util.getConnection(); PreparedStatement pStatement = connection.prepareStatement(sql);)
        {
            pStatement.setInt(1, month);
            pStatement.setInt(2, year);
            
            ResultSet resultSet = pStatement.executeQuery();
            
             while (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentID(resultSet.getInt("APPOINTMENT_ID"));
                    appointment.setTitle(resultSet.getString("TITLE"));
                    appointment.setLocation(resultSet.getString("LOCATION"));
                    appointment.setStartTime(resultSet.getTimestamp("START_TIME"));
                    appointment.setEndTime(resultSet.getTimestamp("END_TIME"));
                    appointment.setDetails(resultSet.getString("DETAILS"));
                    appointment.setWholeDay(resultSet.getBoolean("WHOLE_DAY"));
                    appointment.setAppointmentGroup(resultSet.getInt("APPOINTMENT_GROUP"));
                    appointment.setAlarmReminder(resultSet.getBoolean("ALARM_REMINDER"));
                    
                    appointments.add(appointment);
                }
        }
        
        return appointments;
    }

    /**
     * Takes in two LocalDateTime objects and uses them to retrieve any records of the 
     * APPOINTMENT table that takes place during the same time between the two LocalDateTime objects.
     * 
     * @param date1, date2
     * @return appointments
     * @throws SQLException 
     */
    @Override
    public List<Appointment> findBetweenDates(LocalDateTime date1, LocalDateTime date2) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM APPOINTMENT WHERE START_TIME BETWEEN ? AND ?";
        
        date1 = LocalDateTime.of(date1.getYear(), date1.getMonthValue(), date1.getDayOfMonth(), 0, 0);
        date2 = LocalDateTime.of(date2.getYear(), date2.getMonthValue(), date2.getDayOfMonth(), 23, 59, 59);
        
        Timestamp tsDate1 = Timestamp.valueOf(date1);
        Timestamp tsDate2 = Timestamp.valueOf(date2);
        
        try
        (Connection connection = util.getConnection(); PreparedStatement pStatement = connection.prepareStatement(sql);)
        {
            pStatement.setTimestamp(1, tsDate1);
            pStatement.setTimestamp(2, tsDate2);
            
            ResultSet resultSet = pStatement.executeQuery();
            
             while (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentID(resultSet.getInt("APPOINTMENT_ID"));
                    appointment.setTitle(resultSet.getString("TITLE"));
                    appointment.setLocation(resultSet.getString("LOCATION"));
                    appointment.setStartTime(resultSet.getTimestamp("START_TIME"));
                    appointment.setEndTime(resultSet.getTimestamp("END_TIME"));
                    appointment.setDetails(resultSet.getString("DETAILS"));
                    appointment.setWholeDay(resultSet.getBoolean("WHOLE_DAY"));
                    appointment.setAppointmentGroup(resultSet.getInt("APPOINTMENT_GROUP"));
                    appointment.setAlarmReminder(resultSet.getBoolean("ALARM_REMINDER"));
                    
                    appointments.add(appointment);
                }
        }
        
        return appointments;
    }
    
    @Override
    public List<Appointment> findBetweenDateTimes(LocalDateTime date1, LocalDateTime date2) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM APPOINTMENT WHERE START_TIME BETWEEN ? AND ?";
        
        date1 = LocalDateTime.of(date1.getYear(), date1.getMonthValue(), date1.getDayOfMonth(), date1.getHour(), date1.getMinute());
        date2 = LocalDateTime.of(date2.getYear(), date2.getMonthValue(), date2.getDayOfMonth(), date2.getHour(), date2.getMinute());
        
        Timestamp tsDate1 = Timestamp.valueOf(date1);
        Timestamp tsDate2 = Timestamp.valueOf(date2);
        
        try
        (Connection connection = util.getConnection(); PreparedStatement pStatement = connection.prepareStatement(sql);)
        {
            pStatement.setTimestamp(1, tsDate1);
            pStatement.setTimestamp(2, tsDate2);
            
            ResultSet resultSet = pStatement.executeQuery();
            
             while (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentID(resultSet.getInt("APPOINTMENT_ID"));
                    appointment.setTitle(resultSet.getString("TITLE"));
                    appointment.setLocation(resultSet.getString("LOCATION"));
                    appointment.setStartTime(resultSet.getTimestamp("START_TIME"));
                    appointment.setEndTime(resultSet.getTimestamp("END_TIME"));
                    appointment.setDetails(resultSet.getString("DETAILS"));
                    appointment.setWholeDay(resultSet.getBoolean("WHOLE_DAY"));
                    appointment.setAppointmentGroup(resultSet.getInt("APPOINTMENT_GROUP"));
                    appointment.setAlarmReminder(resultSet.getBoolean("ALARM_REMINDER"));
                    
                    appointments.add(appointment);
                }
        }
        
        return appointments;
    }

    @Override
    public Appointment findNextByID(Appointment appointment) throws SQLException {
        String selectQuery = "SELECT * FROM APPOINTMENT WHERE APPOINTMENT_ID = (SELECT MIN(APPOINTMENT_ID) from APPOINTMENT WHERE APPOINTMENT_ID > ?)";

        try (Connection connection = util.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) 
        {
            
            pStatement.setInt(1, appointment.getAppointmentID());
            
            try (ResultSet resultSet = pStatement.executeQuery()) 
            {
                if (resultSet.next()) 
                {
                    createBoundAppointment(resultSet, appointment);
                }
            }
        }
        log.info("Found " + appointment.getAppointmentID());
        return appointment;
    }

    @Override
    public Appointment findPrevByID(Appointment appointment) throws SQLException {
                String selectQuery = "SELECT * FROM APPOINTMENT WHERE APPOINTMENT_ID = (SELECT MAX(APPOINTMENT_ID) from APPOINTMENT WHERE APPOINTMENT_ID < ?)";

        try (Connection connection = util.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(selectQuery);) 
        {
            
            pStatement.setInt(1, appointment.getAppointmentID());
            
            try (ResultSet resultSet = pStatement.executeQuery()) 
            {
                if (resultSet.next()) 
                {
                    createBoundAppointment(resultSet, appointment);
                }
            }
        }
        log.info("Found " + appointment.getAppointmentID());
        return appointment;
    }
    
     /**
     * Fill an existing bean that is bound to a form
     *
     * @param resultSet
     * @param smtpSettings
     * @return
     * @throws SQLException
     */
    private Appointment createBoundAppointment(ResultSet resultSet, Appointment appointment) throws SQLException 
    {
        appointment.setAppointmentID(resultSet.getInt("APPOINTMENT_ID"));
        appointment.setTitle(resultSet.getString("TITLE"));
        appointment.setLocation(resultSet.getString("LOCATION"));
        appointment.setStartTime(resultSet.getTimestamp("START_TIME"));
        appointment.setEndTime(resultSet.getTimestamp("END_TIME"));
        appointment.setDetails(resultSet.getString("DETAILS"));
        appointment.setWholeDay(resultSet.getBoolean("WHOLE_DAY"));
        appointment.setAppointmentGroup(resultSet.getInt("APPOINTMENT_GROUP"));
        appointment.setAlarmReminder(resultSet.getBoolean("ALARM_REMINDER"));
        
        return appointment;
    }
}
