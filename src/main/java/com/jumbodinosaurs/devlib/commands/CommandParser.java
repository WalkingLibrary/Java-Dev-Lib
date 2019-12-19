package com.jumbodinosaurs.devlib.commands;

import java.util.ArrayList;

;

public class CommandParser
{
    private ArrayList<Parameter> parameters = new ArrayList<Parameter>();
    private String command;
    
    public CommandParser(String message, boolean hasPrefix)
    {
        String[] commandParts = message.split(" ");
        //Remove Prefix
        if(hasPrefix)
        {
            commandParts[0] = commandParts[0].substring(1);
            
        }
        this.command = commandParts[0];
        for(int i = 1; i < commandParts.length; i++)
        {
            parameters.add(new Parameter(commandParts[i]));
        }
    }
    
    public ArrayList<Parameter> getParameters()
    {
        return parameters;
    }
    
    public void setParameters(ArrayList<Parameter> parameters)
    {
        this.parameters = parameters;
    }
    
    public String getCommand()
    {
        return command;
    }
    
    public void setCommand(String command)
    {
        this.command = command;
    }
}
