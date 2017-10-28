package com.marcdanieldialogo.entities;

import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class models an SMTP settings record.
 * 
 * @author Marc-Daniel
 */
public class SMTPSettings {
    private IntegerProperty smtp_id;
    private StringProperty username;
    private StringProperty email;
    private StringProperty email_password;
    private StringProperty smtp_url;
    private IntegerProperty smtp_port;
    private BooleanProperty default_SMTP;
    private IntegerProperty reminder_interval;
    
    public SMTPSettings(int smtp_id, String username, String email, String email_password, String smtp_url, int smtp_port, boolean default_SMTP,
            int reminder_interval)
    {
        this.smtp_id = new SimpleIntegerProperty(smtp_id);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.email_password = new SimpleStringProperty(email_password);
        this.smtp_url = new SimpleStringProperty(smtp_url);
        this.smtp_port = new SimpleIntegerProperty(smtp_port);
        this.default_SMTP = new SimpleBooleanProperty(default_SMTP);
        this.reminder_interval = new SimpleIntegerProperty(reminder_interval);
    }

    public SMTPSettings() 
    {
        this(-1, "", "", "", "", 465, false, -1);
    }
    
    public int getSMTPID()
    {
        return smtp_id.get();
    }
    
    public IntegerProperty smtpIDProperty()
    {
        return smtp_id;
    }
    
    public String getUsername()
    {
        return username.get();
    }
    
    public StringProperty usernameProperty()
    {
        return username;
    }
    
    public String getEmail()
    {
        return email.get();
    }
    
    public StringProperty emailProperty()
    {
        return email;
    }
    
    public String getEmailPassword()
    {
        return email_password.get();
    }
    
    public StringProperty emailPasswordProperty()
    {
        return email_password;
    }
    
    public String getSMTPURL()
    {
        return smtp_url.get();
    }
    
    public StringProperty smtpURLProperty()
    {
        return smtp_url;
    }
    
    public int getSMTPPort()
    {
        return smtp_port.get();
    }
    
    public IntegerProperty smtpPortProperty()
    {
        return smtp_port;
    }
    
    public boolean getDefaultSMTP()
    {
        return default_SMTP.get();
    }
    
    public BooleanProperty defaultSMTPProperty()
    {
        return default_SMTP;
    }
    
    public int getReminderInterval()
    {
        return reminder_interval.get();
    }
    
    public IntegerProperty reminderIntervalProperty()
    {
        return reminder_interval;
    }
    
    public void setSMTPID(int smtp_id) {
        this.smtp_id.set(smtp_id);
    }
    
    public void setUsername(String username)
    {
        this.username.set(username);
    }
    
    public void setEmail(String email)
    {
        this.email.set(email);
    }
    
    public void setEmailPassword(String email_password)
    {
        this.email_password.set(email_password);
    }
    
    public void setSMTPURL(String smtp_url)
    {
        this.smtp_url.set(smtp_url);
    }
    
    public void setSMTPPort(int smtp_port)
    {
        this.smtp_port.set(smtp_port);
    }
    
    public void setDefaultSMTP(boolean default_SMTP)
    {
        this.default_SMTP.set(default_SMTP);
    }
    
    public void setReminderInterval(int reminder_interval)
    {
        this.reminder_interval.set(reminder_interval);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.smtp_id.get();
        hash = 37 * hash + Objects.hashCode(this.username.get());
        hash = 37 * hash + Objects.hashCode(this.email.get());
        hash = 37 * hash + Objects.hashCode(this.email_password.get());
        hash = 37 * hash + Objects.hashCode(this.smtp_url.get());
        hash = 37 * hash + Objects.hashCode(this.smtp_port.get());
        hash = 37 * hash + Objects.hashCode(this.default_SMTP.get());
        hash = 37 * hash + Objects.hashCode(this.reminder_interval.get());
        
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
        
        final SMTPSettings other = (SMTPSettings) obj;
        
        if (this.smtp_id.get() != other.smtp_id.get()) {
            return false;
        }
        
        if (!Objects.equals(this.username.get(), other.username.get())) {
            return false;
        }
        
        if (!Objects.equals(this.email.get(), other.email.get())) {
            return false;
        }
        
        if (!Objects.equals(this.email_password.get(), other.email_password.get())) {
            return false;
        }
        
        if (!Objects.equals(this.smtp_url.get(), other.smtp_url.get())) {
            return false;
        }
        
        if (!Objects.equals(this.smtp_port.get(), other.smtp_port.get())) {
            return false;
        }
        
        if(!Objects.equals(this.default_SMTP.get(), other.default_SMTP.get()))
        {
            return false;
        }
        
        if(!Objects.equals(this.reminder_interval.get(), other.reminder_interval.get()))
        {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "SMTPSettings{" + "smtp_id=" + smtp_id.get() + ", username=" + username.get() + ", email=" + email.get() + ", email_password=" + email_password.get() 
                + ", smtp_url=" + smtp_url.get() + ", smtp_port=" + smtp_port.get() + ", default_SMTP=" + default_SMTP.get() + ", reminder_interval=" + reminder_interval.get() + '}';
    }

}
