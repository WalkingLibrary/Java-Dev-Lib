package com.jumbodinosaurs.devlib.util;


import com.jumbodinosaurs.devlib.log.LogManager;
import com.jumbodinosaurs.devlib.util.objects.ProcessOutput;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class GeneralUtil
{
    public static File userDir = new File(System.getProperty("user.dir"));

    private GeneralUtil()
    {
    }

    public static File checkFor(File file, String name, boolean forceDir)
    {
        boolean needToMakeFile = true;
        String[] contentsOfFile = file.list();
        for (String fileName : contentsOfFile)
        {
            if (fileName.equals(name))
            {
                needToMakeFile = false;
                break;
            }
        }

        File neededFile = new File(file.getPath() + File.pathSeparator + name);
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
                    if (!neededFile.createNewFile())
                    {
                        throw new IOException("Could not Create File: " + name);
                    }
                }
                catch(Exception e)
                {
                    LogManager.consoleLogger.error("Error Creating File");
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
        for (String fileName : contentsOfFile)
        {
            if (fileName.equals(name))
            {
                needToMakeFile = false;
                break;
            }
        }

        File neededFile = new File(file.getPath() + File.pathSeparator + name);
        if(needToMakeFile)
        {
            if(name.indexOf(".") >= 0)
            {
                try
                {
                    if (neededFile.createNewFile())
                    {
                        throw new IOException("Could not Create the File: " + name);
                    }
                }
                catch(Exception e)
                {
                    LogManager.consoleLogger.error("Error Creating File");
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

                LogManager.consoleLogger.error("Error Creating Local Path");
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

        StringBuilder pathToReturn = new StringBuilder();
        for(char character : charToChange)
        {
            pathToReturn.append(character);
        }
        return pathToReturn.toString();
        
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
            LogManager.consoleLogger.error("Error writing to file");
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
        StringBuilder fileRequestedContents = new StringBuilder();
        try
        {
            Scanner input = new Scanner(file);
            while(input.hasNextLine())
            {
                fileRequestedContents.append(input.nextLine());
                fileRequestedContents.append("\n");
            }
            input.close();
        }
        catch(Exception e)
        {
            LogManager.consoleLogger.error("Error Reading File Contents", e);
        }
        return fileRequestedContents.toString();
    }
    
    
    public static String scanStream(InputStream stream, String delimiter)
    {
        StringBuilder inputStreamContents = new StringBuilder();
        try
        {
            Scanner input = new Scanner(stream);
            input.useDelimiter(delimiter);
            while(input.hasNext())
            {
                inputStreamContents.append(input.next());
                inputStreamContents.append(delimiter);
            }
            input.close();
        }
        catch(Exception e)
        {
            LogManager.consoleLogger.error("Error Reading Stream Contents", e);
        }
        return inputStreamContents.toString();
    }
    
    public static String scanStream(InputStream stream)
    {
        return scanStream(stream, "\n");
    }
    
    public static File[] listFilesRecursive(File directory)
    {
        ArrayList<File> files = new ArrayList<File>();
        for (File file : Objects.requireNonNull(directory.listFiles()))
        {
            if (file.isDirectory())
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
    public static ProcessOutput execute(String command, List<String> arguments, File executionDir)
            throws IOException
    {

        StringBuilder executionString = new StringBuilder();
        executionString.append(command);

        if (arguments != null)
        {
            for (String argument : arguments)
            {
                executionString.append(" " + argument);
            }
        }

        String debugString = "Executing Command:\n %s %s %s\n";
        debugString = String.format(debugString, OperatorConsole.ANSI_YELLOW, executionString,
                OperatorConsole.ANSI_RESET);
        LogManager.consoleLogger.debug(debugString);

        Process process;
        if (executionDir == null)
        {
            process = Runtime.getRuntime().exec(executionString.toString());
        }
        else
        {
            process = Runtime.getRuntime().exec(executionString.toString(), null, executionDir);
        }


        String successOutput = GeneralUtil.scanStream(process.getInputStream(), "\n");
        String failureOutput = GeneralUtil.scanStream(process.getErrorStream(), "\n");

        return new ProcessOutput(successOutput, failureOutput);

    }
    
    public static StringBuffer replaceUnicodeCharacters(String data)
    {
        Pattern pattern = Pattern.compile("\\\\u(\\p{XDigit}{4})");
        Matcher matcher = pattern.matcher(data);
        StringBuffer buf = new StringBuffer(data.length());
        while(matcher.find())
        {
            String ch = String.valueOf((char) Integer.parseInt(matcher.group(1), 16));
            matcher.appendReplacement(buf, Matcher.quoteReplacement(ch));
        }
        matcher.appendTail(buf);
        return buf;
    }
    
    
    /**/
    public static void copyDir(File source, File destination)
            throws IOException
    {
        if(source.isDirectory())
        {
            if(!destination.exists())
            {
                destination.mkdirs();
            }
            
            String[] files = source.list();
            
            if(files == null)
            {
                return;
            }
            
            
            for(String file : files)
            {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);
                
                copyDir(srcFile, destFile);
            }
            return;
        }


        InputStream in = null;
        OutputStream out = null;
        in = new FileInputStream(source);
        out = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0)
        {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
    }
    
    public static void decompressGzip(File source, File target)
            throws IOException
    {
        
        GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(source));
        FileOutputStream fileOutputStream = new FileOutputStream(target);
        
        // copy GZIPInputStream to FileOutputStream
        byte[] byteBuffer = new byte[1024];
        int length;
        while((length = gzipInputStream.read(byteBuffer)) > 0)
        {
            fileOutputStream.write(byteBuffer, 0, length);
        }
        
        
    }
    
}




