package com.jumbodinosaurs.devlib.database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Query
{
    private String statement;
    private ArrayList<String> parameters;
    private ResultSet resultSet;
    private Statement statementObject;
    private int responseCode;
    
    public Query(String query)
    {
        this.statement = query;
    }
    
    public String getStatement()
    {
        return statement;
    }
    
    public void setStatement(String statement)
    {
        this.statement = statement;
    }
    
    public ResultSet getResultSet()
    {
        return resultSet;
    }
    
    public void setResultSet(ResultSet resultSet)
    {
        this.resultSet = resultSet;
    }
    
    public int getResponseCode()
    {
        return responseCode;
    }
    
    public void setResponseCode(int responseCode)
    {
        this.responseCode = responseCode;
    }
    
    public ArrayList<String> getParameters()
    {
        return parameters;
    }
    
    public void setParameters(ArrayList<String> parameters)
    {
        this.parameters = parameters;
    }
    
    public Statement getStatementObject()
    {
        return statementObject;
    }
    
    public void setStatementObject(Statement statementObject)
    {
        this.statementObject = statementObject;
    }
}
