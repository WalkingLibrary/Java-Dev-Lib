package com.jumbodinosaurs.devlib.reflection.exceptions;

public class NoSuchJarAttribute extends Exception
{
    public NoSuchJarAttribute()
    {
    }
    
    public NoSuchJarAttribute(String message)
    {
        super(message);
    }
    
    public NoSuchJarAttribute(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public NoSuchJarAttribute(Throwable cause)
    {
        super(cause);
    }
    
    public NoSuchJarAttribute(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
