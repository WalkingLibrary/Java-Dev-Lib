package com.jumbodinosaurs.devlib.util.objects;

public class ObjectHolder
{
    private String type;
    private String data;
    private String canonicalName;
    
    public ObjectHolder(Class classType, String data)
    {
        this.type = classType.getSimpleName();
        this.data = data;
        this.canonicalName = classType.getCanonicalName();
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getData()
    {
        return data;
    }
    
    public void setData(String data)
    {
        this.data = data;
    }
    
    public boolean isInClassPath()
    {
        try
        {
            Class.forName(this.canonicalName);
            return true;
        }
        catch(ClassNotFoundException e)
        {
            return false;
        }
    }
    
    public String getCanonicalName()
    {
        return canonicalName;
    }
    
    public void setCanonicalName(String canonicalName)
    {
        this.canonicalName = canonicalName;
    }
    
}
