package com.jumbodinosaurs.devlib.database.objectHolder;

import com.google.gson.Gson;
import com.jumbodinosaurs.devlib.database.DataBase;
import com.jumbodinosaurs.devlib.database.DataBaseUtil;
import com.jumbodinosaurs.devlib.database.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLDatabaseObjectUtil
{
    public static final String OBJECT_COLUMN_NAME = "objectJson";
    
    
    /*
     *
     *
     *
     *
     *
     * */
    
    public static ArrayList<SQLDataBaseObjectHolder> loadObjects(DataBase dataBase, Class clazz, SelectLimiter selectLimiter)
            throws SQLException
    {
        /*
         * Process for Getting Objects
         * Craft Query
         * Execute Query
         * Create Object Holders from Returned Information
         *   */
        
        //Craft Query
        String selectStatement = "SELECT * FROM %s %s;";
        selectStatement = String.format(selectStatement, clazz.getSimpleName(),  selectLimiter.getSelectLimiterStatement());
        
        Query selectQuery = new Query(selectStatement);
        
        
        //Execute Query
        DataBaseUtil.queryDataBase(selectQuery, dataBase);
        
        
        //Create Object Holders from Returned Information
        ArrayList<SQLDataBaseObjectHolder> selectedObjects = new ArrayList<SQLDataBaseObjectHolder>();
        ResultSet queryResult = selectQuery.getResultSet();
        while(queryResult.next())
        {
            String objectJson = queryResult.getString(OBJECT_COLUMN_NAME);
            int id = queryResult.getInt("id");
            selectedObjects.add(new SQLDataBaseObjectHolder(id, objectJson));
        }
        
        return selectedObjects;
    }
    
    public static <E> void putObject(DataBase dataBase, E object, int id) throws SQLException
    {
        /*
         * Process for Putting Objects
         * Craft Query
         * Execute Query
         *   */
        
        //Craft Query
        String updateStatement = "REPLACE INTO %s (id, objectJson) VALUES(%s, '%s');";
        String objectJson = new Gson().toJson(object);
        updateStatement = String.format(updateStatement, object.getClass().getSimpleName(), id + "", objectJson);
        Query updateQuery = new Query(updateStatement);
        
        //Execute Query
        DataBaseUtil.manipulateDataBase(updateQuery, dataBase);
    }
    
    
    public static void putObjectHolder(DataBase dataBase, SQLDataBaseObjectHolder objectHolder) throws SQLException
    {
        putObject(dataBase, objectHolder.getJsonObject(), objectHolder.getId());
    }
    
}
