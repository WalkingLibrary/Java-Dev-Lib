package com.jumbodinosaurs.devlib.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceLoaderUtil
{
    public ClassLoader getLoader()
    {
        return getClass().getClassLoader();
    }
    
    public List<File> getFiles(String resourceDir)
    {
        ArrayList<File> files = new ArrayList<File>();
        try
        {
            for(String fileName : listFiles(resourceDir))
            {
                System.out.println(fileName);
                files.add(getResource(fileName));
            }
            return files;
        }
        catch(IOException e)
        {
            return null;
        }
    }
    
    //https://stackoverflow.com/questions/11012819/how-can-i-get-a-resource-folder-from-inside-my-jar-file
    public ArrayList<String> listFiles(String resourceDir) throws IOException
    {
        ArrayList<String> fileNames = new ArrayList<String>();
        File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        if(jarFile.isFile())
        {  // Run with JAR file
            JarFile jar = new JarFile(jarFile);
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            while(entries.hasMoreElements())
            {
                String name = entries.nextElement().getName();
                if(name.startsWith(resourceDir + "/"))
                { //filter according to the path
                    fileNames.add(resourceDir + "/" + name);
                }
            }
            jar.close();
        }
        else
        { // Run with IDE
            
            for(File file : getResource(resourceDir).listFiles())
            {
                fileNames.add(resourceDir + "/" + file.getName());
            }
        }
        
        for(String string : fileNames)
        {
            System.out.println("File Name: " + string);
        }
        return fileNames;
    }
    
    public File getResource(String resourcePath)
    {
        try
        {
            return new File(getLoader().getResource(resourcePath).toURI());
        }
        catch(URISyntaxException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public String getResourceAsStream(String resourcePath)
    {
        InputStream resourceStream = getLoader().getResourceAsStream(resourcePath);
        return GeneralUtil.scanStream(resourceStream);
    }
}
