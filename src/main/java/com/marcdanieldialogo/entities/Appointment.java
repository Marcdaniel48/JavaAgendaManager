package com.marcdanieldialogo.entities;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class models an appointment record.
 * 
 * @author Marc-Daniel
 */
public class Appointment {
    private IntegerProperty appointment_id;
    private StringProperty title;
    private StringProperty location;
    private TimestampBean start_time;
    private TimestampBean end_time;
    private StringProperty details;
    private BooleanProperty whole_day;
    private IntegerProperty appointment_group;
    private BooleanProperty alarm_reminder;
    
    
    public Appointment(int appointment_id, String title, String location, Timestamp start_time, Timestamp end_time, String details, Boolean whole_day,
            int appointment_group, Boolean alarm_reminder)
    {
        this.appointment_id = new SimpleIntegerProperty(appointment_id);
        this.title = new SimpleStringProperty(title);
        this.location = new SimpleStringProperty(location);
        
        this.start_time = new TimestampBean();
        this.start_time.setTimeStampValue(start_time);
        this.end_time = new TimestampBean();
        this.end_time.setTimeStampValue(end_time);
        
        this.details = new SimpleStringProperty(details);
        this.whole_day = new SimpleBooleanProperty(whole_day);
        this.appointment_group = new SimpleIntegerProperty(appointment_group);
        this.alarm_reminder = new SimpleBooleanProperty(alarm_reminder);
    }

    public Appointment() {
        this(-1, "", "", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), "", false, 0, false);
    }
    
    public int getAppointmentID()
    {
        return appointment_id.get();
    }
    
    public IntegerProperty appointmentIDProperty()
    {
        return appointment_id;
    }
    
    public String getTitle()
    {
        return title.get();
    }
    
    public StringProperty titleProperty()
    {
        return title;
    }
    
    public String getLocation()
    {
        return location.get();
    }
    
    public StringProperty locationProperty()
    {
        return location;
    }
    
    public Timestamp getStartTime()
    {
        return start_time.getTimeStampValue();
    }
    
    public TimestampBean startTimeBean()
    {
        return start_time;
    }
    
    public Timestamp getEndTime()
    {
        return end_time.getTimeStampValue();
    }
    
    public TimestampBean endTimeBean()
    {
        return end_time;
    }
    
    public String getDetails()
    {
        return details.get();
    }
    
    public StringProperty detailsProperty()
    {
        return details;
    }
    
    public Boolean getWholeDay()
    {
        return whole_day.get();
    }
    
    public BooleanProperty wholeDayProperty()
    {
        return whole_day;
    }
    
    public int getAppointmentGroup()
    {
        return appointment_group.get();
    }
    
    public IntegerProperty appointmentGroupProperty()
    {
        return appointment_group;
    }

    public Boolean getAlarmReminder()
    {
        return alarm_reminder.get();
    }
    
    public BooleanProperty alarmReminderProperty()
    {
        return alarm_reminder;
    }
    
    public void setAppointmentID(int appointment_id)
    {
        this.appointment_id.set(appointment_id);
    }
    
    public void setTitle(String title)
    {
        this.title.set(title);
    }
    
    public void setLocation(String location)
    {
        this.location.set(location);
    }
    
    public void setStartTime(Timestamp start_time)
    {
        this.start_time.setTimeStampValue(start_time);
    }
    
    public void setEndTime(Timestamp end_time)
    {
        this.end_time.setTimeStampValue(end_time);
    }
    
    public void setDetails(String details)
    {
        this.details.set(details);
    }
    
    public void setWholeDay(Boolean whole_day)
    {
        this.whole_day.set(whole_day);
    }
    
    public void setAppointmentGroup(int appointment_group)
    {
        this.appointment_group.set(appointment_group);
    }
    
    public void setAlarmReminder(Boolean alarm_reminder)
    {
        this.alarm_reminder.set(alarm_reminder);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.appointment_id.get();
        hash = 37 * hash + Objects.hashCode(this.title.get());
        hash = 37 * hash + Objects.hashCode(this.location.get());
        hash = 37 * hash + Objects.hashCode(this.start_time.getTimeStampValue());
        hash = 37 * hash + Objects.hashCode(this.end_time.getTimeStampValue());
        hash = 37 * hash + Objects.hashCode(this.details.get());
        hash = 37 * hash + Objects.hashCode(this.whole_day.get());
        hash = 37 * hash + Objects.hashCode(this.appointment_group.get());
        hash = 37 * hash + Objects.hashCode(this.alarm_reminder.get());
        
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
        
        if (this.appointment_id.get() != other.appointment_id.get()) {
            return false;
        }
        
        if (!Objects.equals(this.title.get(), other.title.get())) {
            return false;
        }
        
        if (!Objects.equals(this.location.get(), other.location.get())) {
            return false;
        }
        
        if (!Objects.equals(this.start_time.getTimeStampValue(), other.start_time.getTimeStampValue())) {
            return false;
        }
        
        if (!Objects.equals(this.end_time.getTimeStampValue(), other.end_time.getTimeStampValue())) {
            return false;
        }
        
        if (!Objects.equals(this.details.get(), other.details.get())) {
            return false;
        }
        
        if (!Objects.equals(this.whole_day.get(), other.whole_day.get())) {
            return false;
        }
        
        if (!Objects.equals(this.appointment_group.get(), other.appointment_group.get())) {
            return false;
        }
        
        if (!Objects.equals(this.alarm_reminder.get(), other.alarm_reminder.get())) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "Appointment{" + "appointment_id=" + appointment_id.get() + ", title=" + title.get() + ", location=" + location.get() + ", start_time=" + start_time.getTimeStampValue()
                + ", end_time=" + end_time.getTimeStampValue() + ", details=" + details.get() + ", whole_day=" + whole_day.get() + ", appointment_group=" + appointment_group.get()
                + ", alarm_reminder=" + alarm_reminder.get() + '}';
    }
    
    
}
