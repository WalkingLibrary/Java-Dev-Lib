package com.jumbodinosaurs.devlib.util.objects;


import java.util.List;
import java.util.Map;

public class HttpResponse
{
    private int status;
    private String response;
    private Map<String, List<String>> headers;

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

    @Override
    public String toString()
    {
        return "HttpResponse{" +
                "status=" + status +
                ", response='" + response + '\'' +
                ", headers=" + getHeadersAsString() +
                '}';
    }

    public Map<String, List<String>> getHeaders()
    {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers)
    {
        this.headers = headers;
    }

    public String getHeadersAsString()
    {
        if (headers == null)
        {
            return null;
        }

        String stringHeaders = "\n";
        for (Map.Entry<String, List<String>> headerEntry : headers.entrySet())
        {
            String key = headerEntry.getKey();
            String value = "";
            for (String headerValue : headerEntry.getValue())
            {
                value += headerValue;
            }
            stringHeaders += key + ":" + value + "\n";
        }
        return stringHeaders;
    }
}
