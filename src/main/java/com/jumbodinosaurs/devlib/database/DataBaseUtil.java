package com.jumbodinosaurs.devlib.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jumbodinosaurs.devlib.database.exceptions.WrongStorageFormatException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseUtil
{
    public static final String objectColumnName = "objectJson";
    
    public static void queryDataBase(Query query, DataBase dataBase)
            throws SQLException
    {
        Connection dataBaseConnection = dataBase.getConnection();
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(query.getStatement());
        for(int i = 0; query.getParameters() != null && i < query.getParameters().size(); i++)
        {
            preparedStatement.setString(i + 1, query.getParameters().get(i));
        }
        query.setResultSet(preparedStatement.executeQuery());
    }
    
    public static void manipulateDataBase(Query query, DataBase dataBase)
            throws SQLException
    {
        Connection dataBaseConnection = dataBase.getConnection();
        PreparedStatement preparedStatement = dataBaseConnection.prepareStatement(query.getStatement());
        for(int i = 0; query.getParameters() != null && i < query.getParameters().size(); i++)
        {
            preparedStatement.setString(i + 1, query.getParameters().get(i));
        }
        query.setResponseCode(preparedStatement.executeUpdate());
    }
    
    public static <E> ArrayList<E> getObjectsDataBase(Query query, DataBase dataBase, TypeToken<E> typeToken)
            throws SQLException, WrongStorageFormatException
    {
        queryDataBase(query, dataBase);
        ArrayList<E> objects = new ArrayList<E>();
        ResultSet queryResult = query.getResultSet();
        while(queryResult.next())
        {
            try
            {
                String objectJson = queryResult.getString(objectColumnName);
                objects.add(new Gson().fromJson(objectJson, typeToken.getType()));
            }
            catch(SQLException | JsonParseException e)
            {
                throw new WrongStorageFormatException(e.getMessage());
            }
        }
        return objects;
    }
    
    public static <E> Query getUpdateObjectQuery(String table, E objectOld, E objectNew)
    {
        String oldObjectJson = new Gson().toJson(objectOld);
        String newObjectJson = new Gson().toJson(objectNew);
        
        String statement = "UPDATE ? ";
        statement += " SET " + objectColumnName + " =?";
        statement += " WHERE " + objectColumnName + " = CAST(? AS JSON);";
        
        Query query = new Query(statement);
        
        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add(table);
        parameters.add(newObjectJson);
        parameters.add(oldObjectJson);
        query.setParameters(parameters);
        
        return query;
    }
    
    /* Warning don't allow un-sanitized user input for table names or you'll risk sql injection
     * */
    public static <E> Query getInsertQuery(String table, E object)
    {
        //https://github.com/google/gson/issues/203
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String objectJson = gson.toJson(object);
        String statement = "INSERT INTO " + table +" (" + objectColumnName + ") VALUES(?);";
        
        Query query = new Query(statement);
    
        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add(objectJson);
        query.setParameters(parameters);
        
        return query;
    }
    
    public static <E> Query getDeleteQuery(String table, int id)
    {
        String statement = "DELETE FROM ? WHERE id = " + id;
        Query deleteQuery = new Query(statement);
    
    
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        
        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add(table);
        deleteQuery.setParameters(parameters);
        
        return deleteQuery;
    }
    
    public static <E> Query getDeleteQuery(String table, E object)
    {
        String statement = "DELETE FROM ? WHERE " + objectColumnName + " = CAST(? AS JSON);";
        Query deleteQuery = new Query(statement);
        
        
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        
        String objectJson = gson.toJson(object);
        
        ArrayList<String> parameters = new ArrayList<String>();
        parameters.add(table);
        parameters.add(objectJson);
        deleteQuery.setParameters(parameters);
        
        return new Query(statement);
    }
}
