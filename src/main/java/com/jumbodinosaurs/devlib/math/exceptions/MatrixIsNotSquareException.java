package com.jumbodinosaurs.devlib.math.exceptions;

public class MatrixIsNotSquareException extends Exception
{
    public MatrixIsNotSquareException()
    {
    }
    
    public MatrixIsNotSquareException(String message)
    {
        super(message);
    }
    
    public MatrixIsNotSquareException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public MatrixIsNotSquareException(Throwable cause)
    {
        super(cause);
    }
    
    public MatrixIsNotSquareException(String message,
                                      Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
