package com.jumbodinosaurs.devlib.commands.exceptions;

public class WaveringParametersException extends Exception
{
    public WaveringParametersException()
    {
    }
    
    public WaveringParametersException(String message)
    {
        super(message);
    }
    
    public WaveringParametersException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public WaveringParametersException(Throwable cause)
    {
        super(cause);
    }
    
    public WaveringParametersException(String message,
                                       Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
