package com.jumbodinosaurs.devlib.email;

public abstract class Email
{
    private String type;
    private String username;
    
    public Email(String username)
    {
        this.type = getClass().getSimpleName();
        this.username = username;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public abstract void sendEmail(String to, String topic, String message) throws Exception;
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
}
