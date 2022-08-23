package com.jumbodinosaurs.devlib.commands;

import com.jumbodinosaurs.devlib.commands.exceptions.WaveringParametersException;
import com.jumbodinosaurs.devlib.reflection.ReflectionUtil;

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
        loadedCommands = commands;
    }
    
    public static MessageResponse filter(String input, boolean hasPrefix) throws WaveringParametersException
    {
        MessageResponse response = null;
        Command command = filterCommand(input, hasPrefix);
        
        if(command != null)
        {
            response = command.getExecutedMessage();
        }
        return response;
    }
    
    public static Command filterCommand(String input, boolean hasPrefix) throws WaveringParametersException
    {
        CommandParser parser = new CommandParser(input, hasPrefix);
        String command = parser.getCommand();
        ArrayList<Parameter> parameters = parser.getParameters();

        ArrayList<Command> possibleCommands = new ArrayList<>();
        for (Command consoleCommand : getLoadedCommands())
        {
            if (consoleCommand.getCommand().toLowerCase().equals(command.toLowerCase().trim()))
            {
                if (consoleCommand instanceof CommandWithParameters)
                {
                    ((CommandWithParameters) consoleCommand).setParameters(parameters);

                }
                possibleCommands.add(consoleCommand);
            }
        }


        if (possibleCommands.size() <= 0)
        {
            return null;
        }

        Command latestVersionCommand = possibleCommands.get(0);

        for (Command possibleCommand : possibleCommands)
        {
            if (possibleCommand.getVersion() > latestVersionCommand.getVersion())
            {
                latestVersionCommand = possibleCommand;
            }
        }
        return latestVersionCommand;
    }
    
    public static ArrayList<String> getCategories()
    {
        ArrayList<String> commandCategories = new ArrayList<String>();
        for(Command command : getLoadedCommands())
        {
            if(!commandCategories.contains(command.getCategory()))
            {
                commandCategories.add(command.getCategory());
            }
        }
        return commandCategories;
    }
    
    public static ArrayList<Command> getLoadedCommands()
    {
        return loadedCommands;
    }
}
