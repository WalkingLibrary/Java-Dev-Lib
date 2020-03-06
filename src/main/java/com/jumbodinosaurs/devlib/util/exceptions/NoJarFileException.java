package com.jumbodinosaurs.devlib.util.exceptions;

public class NoJarFileException extends Exception
{
    public NoJarFileException()
    {
    }
    
    public NoJarFileException(String message)
    {
        super(message);
    }
    
    public NoJarFileException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public NoJarFileException(Throwable cause)
    {
        super(cause);
    }
    
    public NoJarFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
