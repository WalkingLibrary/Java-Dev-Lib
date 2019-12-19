package com.jumbodinosaurs.devlib.commands;

public class Parameter
{
    private String parameter;
    private int order;
    
    public Parameter(String parameter, int order)
    {
        this.parameter = parameter;
        this.order = order;
    }
    
    public Parameter(String parameter)
    {
        this.parameter = parameter;
    }
    
    
    public String getParameter()
    {
        return parameter;
    }
    
    public void setParameter(String parameter)
    {
        this.parameter = parameter;
    }
    
    public int getOrder()
    {
        return order;
    }
    
    public void setOrder(int order)
    {
        this.order = order;
    }
}
