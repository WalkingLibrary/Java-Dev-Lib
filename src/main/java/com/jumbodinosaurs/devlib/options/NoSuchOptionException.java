package com.jumbodinosaurs.devlib.options;

public class NoSuchOptionException extends Exception
{
    public NoSuchOptionException()
    {
    }
    
    public NoSuchOptionException(String message)
    {
        super(message);
    }
    
    public NoSuchOptionException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public NoSuchOptionException(Throwable cause)
    {
        super(cause);
    }
    
    public NoSuchOptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
