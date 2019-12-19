package com.jumbodinosaurs.devlib.util.objects;

public class HttpResponse
{
    private int status;
    private String response;
    
    public HttpResponse(int status, String response)
    {
        this.status = status;
        this.response = response;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public String getResponse()
    {
        return response;
    }
    
    public void setResponse(String response)
    {
        this.response = response;
    }
}
