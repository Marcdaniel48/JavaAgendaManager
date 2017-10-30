package com.marcdanieldialogo.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import static java.nio.file.Files.newInputStream;
import java.nio.file.Path;
import static java.nio.file.Paths.get;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * @throws java.sql.SQLException 
     */
    public Connection getConnection() throws SQLException
    {
        Properties dbmsSettings = new Properties();
        Path propertiesPath = get("src/main/resources", "DBMSSettings.properties");

        if(Files.exists(propertiesPath))
        {
            try(InputStream propFileStream = newInputStream(propertiesPath);)
            {
                dbmsSettings.load(propFileStream);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        String url = dbmsSettings.getProperty("URL");
        String user = dbmsSettings.getProperty("USER");
        String password = dbmsSettings.getProperty("PASSWORD");

        try 
        {
            return DriverManager.getConnection(url, user, password);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        throw new SQLException("Error using Utilities.getConnection");
    }
}
