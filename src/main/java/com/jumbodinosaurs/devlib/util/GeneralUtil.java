package com.jumbodinosaurs.devlib.util;


import com.jumbodinosaurs.devlib.reflection.ResourceLoaderUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class GeneralUtil
{
    public static File userDir = new File(System.getProperty("user.dir"));
    private static ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
    
    public static File checkFor(File file, String name, boolean forceDir)
    {
        boolean needToMakeFile = true;
        String[] contentsOfFile = file.list();
        for(int i = 0; i < contentsOfFile.length; i++)
        {
            if(contentsOfFile.equals(name))
            {
                needToMakeFile = false;
            }
        }
        
        File neededFile = new File(file.getPath() + "/" + name);
        if(needToMakeFile)
        {
            if(forceDir || name.indexOf(".") < 0)
            {
                neededFile.mkdir();
            }
            else
            {
                try
                {
                    neededFile.createNewFile();
                }
                catch(Exception e)
                {
                    System.out.println("Error Creating File");
                }
            }
        }
        return neededFile;
    }
    
    
    /* @Function: Checks for the String name in the given Dir of File file
     * returns it and makes it if not there.
     *
     * @Return: desired name within file
     * @param1: File file Dir to search for name in
     * @param2: String Name name to search for in file
     * @PreCondition: File must be a Dir and also Exist
     */
    public static File checkFor(File file, String name)
    {
        boolean needToMakeFile = true;
        String[] contentsOfFile = file.list();
        for(int i = 0; i < contentsOfFile.length; i++)
        {
            if(contentsOfFile.equals(name))
            {
                needToMakeFile = false;
            }
        }
        
        File neededFile = new File(file.getPath() + "/" + name);
        if(needToMakeFile)
        {
            if(name.indexOf(".") >= 0)
            {
                try
                {
                    neededFile.createNewFile();
                }
                catch(Exception e)
                {
                    System.out.println("Error Creating File");
                }
            }
            else
            {
                neededFile.mkdir();
            }
        }
        return neededFile;
    }
    
    
    /*
         tl;dr it checks the given file for the given local path and makes it if it's not there

         Example: /home/systemop -> File file
                  /stats/home.txt -> String localPath
                  checks for /home/systemop/stats/home.txt
                  if not there then it will check and make each file in the local path

     */
    public static File checkForLocalPath(File file, String localPath)
    {
        localPath = fixPathSeparator(localPath);
        
        
        ArrayList<String> levels = new ArrayList<String>();
        String temp = localPath;
        String level = "";
        if(temp.indexOf(File.separator) != 0)
        {
            temp = File.separator + temp;
        }
        
        if(temp.lastIndexOf(File.separator) != temp.length())
        {
            temp += File.separator;
        }
        
        char[] tempchars = temp.toCharArray();
        int indexOfLastSlash = 0;
        for(int i = 1; i < temp.length(); i++)
        {
            if(tempchars[i] == File.separatorChar)
            {
                level = temp.substring(indexOfLastSlash + 1, i);
                levels.add(level);
                indexOfLastSlash = i;
            }
        }
        
        File lastParent = file;
        for(String subPath : levels)
        {
            try
            {
                File fileToMake = checkFor(lastParent, subPath);
                lastParent = fileToMake;
            }
            catch(Exception e)
            {
                
                System.out.println("Error Creating Local Path");
                e.printStackTrace();
            }
            
        }
        return lastParent;
    }
    
    public static String fixPathSeparator(String path)
    {
        
        
        char[] charToChange = path.toCharArray();
        if(File.separator.equals("\\"))
        {
            for(int i = 0; i < charToChange.length; i++)
            {
                if(charToChange[i] == '/')
                {
                    charToChange[i] = '\\';
                }
            }
        }
        else
        {
            for(int i = 0; i < charToChange.length; i++)
            {
                if(charToChange[i] == '\\')
                {
                    charToChange[i] = '/';
                }
            }
        }
        
        String pathToReturn = "";
        for(char character : charToChange)
        {
            pathToReturn += character;
        }
        return pathToReturn;
        
    }
    
    
    public static void writeContents(File fileToWrite, String contents, boolean append)
    {
        try
        {
            PrintWriter output = new PrintWriter(new FileOutputStream(fileToWrite, append));
            output.write(contents);
            output.close();
        }
        catch(Exception e)
        {
            System.out.println("Error writing to file");
        }
    }
    
    
    public static String getType(File file)
    {
        String temp = file.getName();
        while(temp.indexOf(".") > -1)
        {
            temp = temp.substring(temp.indexOf(".") + 1);
        }
        return temp;
    }
    
    
    //For Reading any file on the system
    public static String scanFileContents(File file)
    {
        //Read File
        String fileRequestedContents = "";
        try
        {
            Scanner input = new Scanner(file);
            while(input.hasNextLine())
            {
                fileRequestedContents += input.nextLine();
                fileRequestedContents += "\n";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Error Reading File Contents");
        }
        return fileRequestedContents;
    }
    
    
    public static String scanStream(InputStream stream, String delimiter)
    {
        String inputStreamContents = "";
        try
        {
            Scanner input = new Scanner(stream);
            input.useDelimiter(delimiter);
            while(input.hasNext())
            {
                inputStreamContents += input.next();
                inputStreamContents += delimiter;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Error Reading Stream Contents");
        }
        return inputStreamContents;
    }
    
    public static String scanStream(InputStream stream)
    {
        return scanStream(stream, "\n");
    }
    
    public static File[] listFilesRecursive(File directory)
    {
        ArrayList<File> files = new ArrayList<File>();
        for(File file : directory.listFiles())
        {
            if(file.isDirectory())
            {
                files.addAll(Arrays.asList(listFilesRecursive(file)));
            }
            else
            {
                files.add(file);
            }
        }
        File[] filesToReturn = new File[files.size()];
        for(int i = 0; i < files.size(); i++)
        {
            filesToReturn[i] = files.get(i);
        }
        return filesToReturn;
    }
    
    ////https://www.codejava.net/java-se/file-io/execute-operating-system-commands-using-runtime-exec-methods
    // This Function is for running commands via the console/command prompt
    public static String execute(String command, ArrayList<String> arguments, File executionDir) throws IOException
    {
        if(arguments != null && arguments.size() != 0)
        {
            for(String argument : arguments)
            {
                command += " " + argument;
            }
        }
    
        System.out.println("Executing Command:\n" + command + "\n");
        
        Process process;
        if(executionDir == null)
        {
            process = Runtime.getRuntime().exec(command);
        }
        else
        {
            process = Runtime.getRuntime().exec(command, null, executionDir);
        }
        
        
        String processOutput = GeneralUtil.scanStream(process.getInputStream(), "\n");
        String processErrorOutput = GeneralUtil.scanStream(process.getErrorStream(), "\n");
    
    
        String returnOutput = "--------------------------------------\n";
        
        if(!processOutput.equals(""))
        {
            returnOutput += "Process Output:\n\n";
            returnOutput += processOutput + "\n\n";
        }
        
        if(!processErrorOutput.equals(""))
        {
            returnOutput += "Error Output:" + "\n\n";
            returnOutput += processErrorOutput;
        }
    
        returnOutput += "--------------------------------------\n\n";
        return returnOutput;
        
        
    }
    
}




