package com.marcdanieldialogo.jam_jdbc;

import com.marcdanieldialogo.entities.Appointment;
import com.marcdanieldialogo.persistence.AppointmentDAO;
import com.marcdanieldialogo.persistence.AppointmentDAOImpl;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Marc-Daniel
 */
public class MainApp {
    public static void main(String[] args) throws SQLException
    {
        
        AppointmentDAO dao = new AppointmentDAOImpl();
        List<Appointment> appointmentList = dao.findAll();
        
        for(Appointment record : appointmentList)
        {
            System.out.println(record.toString());
        }
        
    }
}
