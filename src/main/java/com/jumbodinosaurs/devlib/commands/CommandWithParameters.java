package com.jumbodinosaurs.devlib.commands;

import java.util.ArrayList;

public abstract class CommandWithParameters extends Command
{
    private ArrayList<Parameter> parameters = new ArrayList<Parameter>();
    
    public ArrayList<Parameter> getParameters()
    {
        return parameters;
    }
    
    //TODO Switch to IParamable
    public void setParameters(ArrayList<Parameter> parameters)
    {
        this.parameters = parameters;
    }
}
