package com.jumbodinosaurs.devlib.options;

public class Option<E>
{
    private E option;
    private String identifier;
    
    public Option(E option, String identifier)
    {
        this.option = option;
        this.identifier = identifier;
    }
    
    public E getOption()
    {
        return option;
    }
    
    public void setOption(E option)
    {
        this.option = option;
    }
    
    public String getIdentifier()
    {
        return identifier;
    }
    
    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }
}
