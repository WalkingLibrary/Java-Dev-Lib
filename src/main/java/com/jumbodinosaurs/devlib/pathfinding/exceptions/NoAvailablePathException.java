package com.jumbodinosaurs.devlib.pathfinding.exceptions;

public class NoAvailablePathException extends Exception
{
    public NoAvailablePathException()
    {
    }
    
    public NoAvailablePathException(String message)
    {
        super(message);
    }
    
    public NoAvailablePathException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public NoAvailablePathException(Throwable cause)
    {
        super(cause);
    }
    
    public NoAvailablePathException(String message,
                                    Throwable cause,
                                    boolean enableSuppression,
                                    boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
