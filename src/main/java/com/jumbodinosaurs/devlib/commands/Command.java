package com.jumbodinosaurs.devlib.commands;


import com.jumbodinosaurs.devlib.commands.exceptions.WaveringParametersException;

public abstract class Command
{
    
    public String getCommand()
    {
        return getClass().getSimpleName();
    }
    
    public abstract MessageResponse getExecutedMessage() throws WaveringParametersException;
    
    public abstract String getHelpMessage();
}
