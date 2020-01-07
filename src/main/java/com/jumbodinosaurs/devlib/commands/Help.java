package com.jumbodinosaurs.devlib.commands;


public class Help extends CommandWithParameters
{
    @Override
    public MessageResponse getExecutedMessage()
    {
        MessageResponse response = null;
        if(this.getParameters().size() > 0)
        {
            String identifier = this.getParameters().get(0).getParameter();
            
            
            for(Command command : CommandManager.getLoadedCommands())
            {
                if(command.getCommand().equals(identifier))
                {
                    response = new MessageResponse(command.getHelpMessage());
                }
            }
            
            if(response == null)
            {
                response = new MessageResponse("No Command Named: " + identifier);
            }
            
        }
        else
        {
            String commandsToList = "Commands:";
            for(Command command : CommandManager.getLoadedCommands())
            {
                commandsToList += "\n" + command.getCommand();
            }
            response = new MessageResponse(commandsToList);
        }
        return response;
    }
    
    
    @Override
    public String getHelpMessage()
    {
        return "Enter " + getCommand() + " followed by a command to see the help message for that command";
    }
}
