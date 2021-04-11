package com.jumbodinosaurs.devlib.util;


import com.jumbodinosaurs.devlib.commands.CommandManager;
import com.jumbodinosaurs.devlib.commands.MessageResponse;
import com.jumbodinosaurs.devlib.commands.exceptions.WaveringParametersException;
import com.jumbodinosaurs.devlib.log.LogManager;

import java.util.Scanner;

public class OperatorConsole implements Runnable
{
    //https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    //BackGround
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    
    
    public OperatorConsole()
    {
        LogManager.consoleLogger.info(ANSI_YELLOW + "Console Online" + ANSI_RESET);
    }
    
    public static String getEnsuredAnswer()
    {
        String ensuredAnswer = null;
        Scanner userInputScanner = new Scanner(System.in);
        String userInput = "";
        do
        {
            if(ensuredAnswer != null)
            {
                System.out.println(ANSI_RED + "Re-Enter: " + ANSI_RESET);
            }
            ensuredAnswer = userInputScanner.nextLine();
            System.out.println(ANSI_GREEN + "Is this correct: \"" + ensuredAnswer + "\" (y/n)" + ANSI_RESET);
            userInput = userInputScanner.nextLine();
        }
        while(!userInput.toLowerCase().contains("y"));
        
        return ensuredAnswer;
    }
    
    public void run()
    {
        Scanner input = new Scanner(System.in);
        CommandManager.refreshCommands();
        while(true)
        {
            try
            {
                String userInput = "";
                userInput += input.nextLine();
                if(!userInput.equals(""))
                {
                    try
                    {
                        MessageResponse response = CommandManager.filter(userInput, true);
                        if(response == null)
                        {
                            System.out.println(ANSI_RED + "Unrecognized command /help or /? for more Help." + "" + ANSI_RESET);
                        }
                        else
                        {
                            LogManager.consoleLogger.info(ANSI_GREEN + response.getMessage() + ANSI_RESET);
                        }
                    }
                    catch(WaveringParametersException e)
                    {
                        LogManager.consoleLogger.warn(ANSI_RED + e.getMessage() + ANSI_RESET);
                    }
                }
            }
            catch(Exception e)
            {
                LogManager.consoleLogger.error("Un-caught Exception in Operator Console", e);
            }
        }
    }
}
