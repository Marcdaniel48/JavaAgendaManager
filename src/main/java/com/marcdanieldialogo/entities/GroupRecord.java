package com.marcdanieldialogo.entities;

import java.util.Objects;

/**
 * This class models a group record.
 * 
 * @author Marc-Daniel
 */
public class GroupRecord {
    private int group_number;
    private String group_name;
    private String colour;
    
    public GroupRecord(int group_number, String group_name, String colour)
    {
        this.group_number = group_number;
        this.group_name = group_name;
        this.colour = colour;
    }

    public GroupRecord() {
        this.group_number = -1;
        this.group_name = "";
        this.colour = "";
    }
    
    public int getGroupNumber()
    {
        return group_number;
    }
    
    public String getGroupName()
    {
        return group_name;
    }
    
    public String getColour()
    {
        return colour;
    }
    
    public void setGroupNumber(int group_number) {
        this.group_number = group_number;
    }
    
    public void setGroupName(String group_name)
    {
        this.group_name = group_name;
    }
    
    public void setColour(String colour)
    {
        this.colour = colour;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.group_number;
        hash = 37 * hash + Objects.hashCode(this.group_name);
        hash = 37 * hash + Objects.hashCode(this.colour);
        
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
        
        final GroupRecord other = (GroupRecord) obj;
        
        if (this.group_number != other.group_number) {
            return false;
        }
        
        if (!Objects.equals(this.group_name, other.group_name)) {
            return false;
        }
        
        if (!Objects.equals(this.colour, other.colour)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "GroupRecord{" + "group_number=" + group_number + ", group_name=" + group_name + ", colour=" + colour + '}';
    }
}
