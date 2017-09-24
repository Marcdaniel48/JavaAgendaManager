package com.marcdanieldialogo.entities;

import java.util.Objects;

/**
 * This class models an SMTP settings record.
 * 
 * @author Marc-Daniel
 */
public class SMTPSettings {
    private int smtp_id;
    private String username;
    private String email;
    private String email_password;
    private String smtp_url;
    private int smtp_port;
    private int default_SMTP;
    private int reminder_interval;
    
    public SMTPSettings(int smtp_id, String username, String email, String email_password, String smtp_url, int smtp_port, int default_SMTP,
            int reminder_interval)
    {
        this.smtp_id = smtp_id;
        this.username = username;
        this.email = email;
        this.email_password = email_password;
        this.smtp_url = smtp_url;
        this.smtp_port = smtp_port;
        this.default_SMTP = default_SMTP;
        this.reminder_interval = reminder_interval;
    }

    public SMTPSettings() {
        this.smtp_id = -1;
        this.username = "";
        this.email = "";
        this.email_password = "";
        this.smtp_url = "";
        this.smtp_port = 465;
        this.default_SMTP = 0;
        this.reminder_interval = -1;
    }
    
    public int getSMTPID()
    {
        return smtp_id;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public String getEmailPassword()
    {
        return email_password;
    }
    
    public String getSMTPURL()
    {
        return smtp_url;
    }
    
    public int getSMTPPort()
    {
        return smtp_port;
    }
    
    public int getDefaultSMTP()
    {
        return default_SMTP;
    }
    public int getReminderInterval()
    {
        return reminder_interval;
    }
    
    public void setSMTPID(int smtp_id) {
        this.smtp_id = smtp_id;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public void setEmailPassword(String email_password)
    {
        this.email_password = email_password;
    }
    
    public void setSMTPURL(String smtp_url)
    {
        this.smtp_url = smtp_url;
    }
    
    public void setSMTPPort(int smtp_port)
    {
        this.smtp_port = smtp_port;
    }
    
    public void setDefaultSMTP(int default_SMTP)
    {
        this.default_SMTP = default_SMTP;
    }
    
    public void setReminderInterval(int reminder_interval)
    {
        this.reminder_interval = reminder_interval;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.smtp_id;
        hash = 37 * hash + Objects.hashCode(this.username);
        hash = 37 * hash + Objects.hashCode(this.email);
        hash = 37 * hash + Objects.hashCode(this.email_password);
        hash = 37 * hash + Objects.hashCode(this.smtp_url);
        hash = 37 * hash + Objects.hashCode(this.smtp_port);
        hash = 37 * hash + Objects.hashCode(this.default_SMTP);
        hash = 37 * hash + Objects.hashCode(this.reminder_interval);
        
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
        
        if (this.smtp_id != other.smtp_id) {
            return false;
        }
        
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        
        if (!Objects.equals(this.email_password, other.email_password)) {
            return false;
        }
        
        if (!Objects.equals(this.smtp_url, other.smtp_url)) {
            return false;
        }
        
        if (!Objects.equals(this.smtp_port, other.smtp_port)) {
            return false;
        }
        
        if(!Objects.equals(this.default_SMTP, other.default_SMTP))
        {
            return false;
        }
        
        if(!Objects.equals(this.reminder_interval, other.reminder_interval))
        {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "SMTPSettings{" + "smtp_id=" + smtp_id + ", username=" + username + ", email=" + email + ", email_password=" + email_password 
                + ", smtp_url=" + smtp_url + ", smtp_port=" + smtp_port + ", default_SMTP=" + default_SMTP + ", reminder_interval=" + reminder_interval + '}';
    }

}
