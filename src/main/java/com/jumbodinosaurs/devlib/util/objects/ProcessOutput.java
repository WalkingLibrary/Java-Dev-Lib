package com.jumbodinosaurs.devlib.util.objects;

public class ProcessOutput
{
    private String successOutput;
    private String failureOutput;
    
    public ProcessOutput(String successOutput, String failureOutput)
    {
        this.successOutput = successOutput;
        this.failureOutput = failureOutput;
    }
    
    public String getSuccessOutput()
    {
        return successOutput;
    }
    
    public void setSuccessOutput(String successOutput)
    {
        this.successOutput = successOutput;
    }
    
    public String getFailureOutput()
    {
        return failureOutput;
    }
    
    public void setFailureOutput(String failureOutput)
    {
        this.failureOutput = failureOutput;
    }
}
