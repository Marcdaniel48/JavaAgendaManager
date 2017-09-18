package com.marcdanieldialogo.entities;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * This class models an appointment record.
 * 
 * @author Marc-Daniel
 */
public class Appointment {
    private int appointment_id;
    private String title;
    private String location;
    private Timestamp start_time;
    private Timestamp end_time;
    private String details;
    private Boolean whole_day;
    private int appointment_group;
    private int reminder_interval;
    private Boolean alarm_reminder;
    
    public Appointment(int appointment_id, String title, String location, Timestamp start_time, Timestamp end_time, String details, Boolean whole_day,
            int appointment_group, int reminder_interval, Boolean alarm_reminder)
    {
        this.appointment_id = appointment_id;
        this.title = title;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.details = details;
        this.whole_day = whole_day;
        this.appointment_group = appointment_group;
        this.reminder_interval = reminder_interval;
        this.alarm_reminder = alarm_reminder;
    }

    public Appointment() {
        this.appointment_id = -1;
        this.title = "";
        this.location = "";
        this.start_time = null;
        this.end_time = null;
        this.details = "";
        this.whole_day = null;
        this.appointment_group = -1;
        this.reminder_interval = -1;
        this.alarm_reminder = null;
    }
    
    public int getAppointmentID()
    {
        return appointment_id;
    }
    public String getTitle()
    {
        return title;
    }
    public String getLocation()
    {
        return location;
    }
    public Timestamp getStartTime()
    {
        return start_time;
    }
    public Timestamp getEndTime()
    {
        return end_time;
    }
    public String getDetails()
    {
        return details;
    }
    public Boolean getWholeDay()
    {
        return whole_day;
    }
    public int getAppointmentGroup()
    {
        return appointment_group;
    }
    public int getReminderInterval()
    {
        return reminder_interval;
    }
    public Boolean getAlarmReminder()
    {
        return alarm_reminder;
    }
    
    public void setAppointmentID(int appointment_id)
    {
        this.appointment_id = appointment_id;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    public void setStartTime(Timestamp start_time)
    {
        this.start_time = start_time;
    }
    
    public void setEndTime(Timestamp end_time)
    {
        this.end_time = end_time;
    }
    
    public void setDetails(String details)
    {
        this.details = details;
    }
    
    public void setWholeDay(Boolean whole_day)
    {
        this.whole_day = whole_day;
    }
    
    public void setAppointmentGroup(int appointment_group)
    {
        this.appointment_group = appointment_group;
    }
    
    public void setReminderInterval(int reminder_interval)
    {
        this.reminder_interval = reminder_interval;
    }
    
    public void setAlarmReminder(Boolean alarm_reminder)
    {
        this.alarm_reminder = alarm_reminder;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.appointment_id;
        hash = 37 * hash + Objects.hashCode(this.title);
        hash = 37 * hash + Objects.hashCode(this.location);
        hash = 37 * hash + Objects.hashCode(this.start_time);
        hash = 37 * hash + Objects.hashCode(this.end_time);
        hash = 37 * hash + Objects.hashCode(this.details);
        hash = 37 * hash + Objects.hashCode(this.whole_day);
        hash = 37 * hash + Objects.hashCode(this.appointment_group);
        hash = 37 * hash + Objects.hashCode(this.reminder_interval);
        hash = 37 * hash + Objects.hashCode(this.alarm_reminder);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Appointment other = (Appointment) obj;
        
        if (this.appointment_id != other.appointment_id) {
            return false;
        }
        
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        
        if (!Objects.equals(this.start_time, other.start_time)) {
            return false;
        }
        
        if (!Objects.equals(this.end_time, other.end_time)) {
            return false;
        }
        
        if (!Objects.equals(this.details, other.details)) {
            return false;
        }
        
        if (!Objects.equals(this.whole_day, other.whole_day)) {
            return false;
        }
        
        if (!Objects.equals(this.appointment_group, other.appointment_group)) {
            return false;
        }
                
        if (!Objects.equals(this.reminder_interval, other.reminder_interval)) {
            return false;
        }
        
        if (!Objects.equals(this.alarm_reminder, other.alarm_reminder)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "Appointment{" + "appointment_id=" + appointment_id + ", title=" + title + ", location=" + location + ", start_time=" + start_time 
                + ", end_time=" + end_time + ", details=" + details + ", whole_day=" + whole_day + ", appointment_group=" + appointment_group
                + ", reminder_interval=" + reminder_interval + ", alarm_reminder=" + alarm_reminder + '}';
    }
    
    
}
