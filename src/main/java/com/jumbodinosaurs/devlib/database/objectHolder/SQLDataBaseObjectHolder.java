package com.jumbodinosaurs.devlib.database.objectHolder;

public class SQLDataBaseObjectHolder
{
    private int id;
    private String jsonObject;
    
    public SQLDataBaseObjectHolder(int id, String jsonObject)
    {
        this.id = id;
        this.jsonObject = jsonObject;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getJsonObject()
    {
        return jsonObject;
    }
    
    public void setJsonObject(String jsonObject)
    {
        this.jsonObject = jsonObject;
    }
}
