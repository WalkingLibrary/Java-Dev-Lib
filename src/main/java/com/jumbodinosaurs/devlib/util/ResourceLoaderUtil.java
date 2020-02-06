package com.jumbodinosaurs.devlib.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceLoaderUtil
{
    public ClassLoader getLoader()
    {
        return getClass().getClassLoader();
    }
    
    public ArrayList<URL> getURLS(String resourceDir) throws IOException
    {
        ArrayList<URL> urls = new ArrayList<>();
        try
        {
            for(String fileName : listResources(resourceDir))
            {
                System.out.println(fileName);
                URL url = getResource(fileName);
                if(url != null)
                {
                    urls.add(url);
                }
            }
            return urls;
        }
        catch(IOException e)
        {
            throw e;
        }
    }
    
    //https://stackoverflow.com/questions/11012819/how-can-i-get-a-resource-folder-from-inside-my-jar-file
    public ArrayList<String> listResources(String resourceDir) throws IOException
    {
        File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        ArrayList<String> fileNames = new ArrayList<String>();
        if(GeneralUtil.getType(jarFile).equals("jar") && jarFile.exists())
        {  // Run with JAR file
            JarFile jar = new JarFile(jarFile.getPath());
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            while(entries.hasMoreElements())
            {
                String name = entries.nextElement().getName();
                if(name.startsWith(resourceDir + "/") && !name.equals(resourceDir + "/"))
                { //filter according to the path
                    fileNames.add(name);
                }
            }
            jar.close();
        }
        else if(GeneralUtil.getType(jarFile).equals("jar"))
        {//weird bug with intellij??
            throw new IllegalStateException("Jar Exists but is not accessible");
        }
        else
        { // Run with IDE
        
            try
            {
                for(File file : new File(getLoader().getResource(resourceDir).toURI()).listFiles())
                {
                    fileNames.add(resourceDir + "/" + file.getName());
                }
            }
            catch(URISyntaxException e)
            {
                e.printStackTrace();
            }
        }
        return fileNames;
    }
    
    public URL getResource(String resourcePath)
    {
        return getLoader().getResource(resourcePath);
    }
    
    public InputStream getResourceAsStream(String resourcePath)
    {
        return getLoader().getResourceAsStream(resourcePath);
    }
    
    public String scanResource(String resourcePath)
    {
        return GeneralUtil.scanStream(getResourceAsStream(resourcePath));
    }
}
