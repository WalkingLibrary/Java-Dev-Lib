package com.jumbodinosaurs.devlib.commands;

import com.jumbodinosaurs.devlib.commands.exceptions.WaveringParametersException;
import com.jumbodinosaurs.devlib.util.ReflectionUtil;

import java.util.ArrayList;

public class CommandManager
{
    
    private static ArrayList<Command> loadedCommands = new ArrayList<Command>();
    
    /*This command needs to be called once to capture the commands in the classpath*/
    public static void refreshCommands()
    {
        ArrayList<Command> commands = new ArrayList<Command>();
        for(Class classType : ReflectionUtil.getSubClasses(Command.class))
        {
            try
            {
                commands.add((Command) classType.newInstance());
            }
            catch(ReflectiveOperationException e)
            {
                e.printStackTrace();
            }
            
        }
        System.out.println("Commands Size: " + commands.size());
        loadedCommands = commands;
    }
    
    public static MessageResponse filter(String input, boolean hasPrefix) throws WaveringParametersException
    {
        MessageResponse response = null;
        CommandParser parser = new CommandParser(input, hasPrefix);
        String command = parser.getCommand();
        ArrayList<Parameter> parameters = parser.getParameters();
        for(Command consoleCommand : getLoadedCommands())
        {
            if(consoleCommand.getCommand().toLowerCase().equals(command.toLowerCase().trim()))
            {
                if(consoleCommand instanceof CommandWithParameters)
                {
                    ((CommandWithParameters) consoleCommand).setParameters(parameters);
                    
                }
                response = consoleCommand.getExecutedMessage();
            }
        }
        return response;
    }
    
    public static ArrayList<Command> getLoadedCommands()
    {
        return loadedCommands;
    }
}
