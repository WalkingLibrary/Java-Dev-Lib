package com.jumbodinosaurs.devlib.commands;

import com.jumbodinosaurs.devlib.commands.exceptions.WaveringParametersException;
import com.jumbodinosaurs.devlib.util.ReflectionUtil;

import java.util.ArrayList;

public class CommandManager
{
    public static MessageResponse filter(String input, boolean hasPrefix) throws WaveringParametersException
    {
        MessageResponse response = null;
        CommandParser parser = new CommandParser(input, hasPrefix);
        String command = parser.getCommand();
        ArrayList<Parameter> parameters = parser.getParameters();
        System.out.println(command);
        for(Command consoleCommand : getLoadedCommands())
        {
            System.out.println(consoleCommand.getCommand());
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
        return commands;
    }
}
