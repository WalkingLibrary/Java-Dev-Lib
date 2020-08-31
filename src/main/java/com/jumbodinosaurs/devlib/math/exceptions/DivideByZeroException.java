package com.jumbodinosaurs.devlib.math.exceptions;

public class DivideByZeroException extends Exception
{
    public DivideByZeroException()
    {
    }
    
    public DivideByZeroException(String message)
    {
        super(message);
    }
    
    public DivideByZeroException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public DivideByZeroException(Throwable cause)
    {
        super(cause);
    }
    
    public DivideByZeroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
