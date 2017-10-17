package com.marcdanieldialogo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides some utility.
 * 
 * @author Marc-Daniel
 */
public class Utilities 
{
    /**
     * Connects to my JAM database and returns that connection.
     * 
     * @return 
     */
    public Connection getConnection() throws SQLException
    {
        try
        {  
            String url = "jdbc:mysql://localhost:3306/jamdb";
            String user = "MarcDaniel";
            String password = "dbsurfing";
            
            return DriverManager.getConnection(url, user, password);
        }
        catch(SQLException sqlex)
        {
            sqlex.printStackTrace();
        }
        
        throw new SQLException("Error using Utilities.getConnection");
    }
}
