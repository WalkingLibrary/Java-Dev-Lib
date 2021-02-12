package com.jumbodinosaurs.devlib.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MicrosoftSQLDataBase extends DataBase
{
    public MicrosoftSQLDataBase(String dataBaseName, String ip, String port, DataBaseUser user)
    {
        super(dataBaseName, ip, port, user);
        this.baseInfo = "jdbc:sqlserver://";
    }
    
    @Override
    public Connection getConnection()
            throws SQLException
    {
        String fullURL = getURL() +
                         String.format("user=%s;", user.getUsername()) +
                         String.format("password=%s;", user.getPassword());
        return DriverManager.getConnection(fullURL);
        
        
    }
    
    @Override
    public String getURL()
    {
        return baseInfo + ip + ":" + port + ";databaseName=" + dataBaseName + ";";
    }
}
