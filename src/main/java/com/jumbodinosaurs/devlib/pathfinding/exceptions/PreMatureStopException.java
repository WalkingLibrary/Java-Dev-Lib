package com.jumbodinosaurs.devlib.pathfinding.exceptions;

public class PreMatureStopException extends Exception
{
    public PreMatureStopException()
    {
    }
    
    public PreMatureStopException(String message)
    {
        super(message);
    }
    
    public PreMatureStopException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public PreMatureStopException(Throwable cause)
    {
        super(cause);
    }
    
    public PreMatureStopException(String message,
                                  Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
