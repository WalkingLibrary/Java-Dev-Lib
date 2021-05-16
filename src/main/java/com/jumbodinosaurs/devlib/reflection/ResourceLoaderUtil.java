package com.jumbodinosaurs.devlib.reflection;

import com.jumbodinosaurs.devlib.reflection.exceptions.NoJarFileException;
import com.jumbodinosaurs.devlib.util.GeneralUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceLoaderUtil
{
    public ClassLoader getLoader()
    {
        return getClass().getClassLoader();
    }
    
    public ArrayList<URL> getURLS(String resourceDir)
            throws IOException
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
    
    public JarFile loadJarFile(String filePath)
            throws IOException, NoJarFileException
    {
        File jarFile = new File(filePath);
        if(GeneralUtil.getType(jarFile).equals("jar") && jarFile.exists())
        {
            try
            {
                return new JarFile(jarFile.getPath());
            }
            catch(IOException e)
            {
                throw e;
            }
        }
        throw new NoJarFileException("No Jar File found (Dev Environment?)");
    }
    
    //https://stackoverflow.com/questions/11012819/how-can-i-get-a-resource-folder-from-inside-my-jar-file
    public ArrayList<String> listResources(String resourceDir)
            throws IOException
    {
        return listResources(resourceDir, this);
    }
    
    //https://stackoverflow.com/questions/11012819/how-can-i-get-a-resource-folder-from-inside-my-jar-file
    public ArrayList<String> listResources(String resourceDir, Object context)
            throws IOException
    {
        ArrayList<String> fileNames = new ArrayList<String>();
        String codePath = ReflectionUtil.getCodeExePath(context);
        System.out.println("Code Path: " + codePath);
        if(codePath.contains("!"))
        {
            codePath = codePath.split("!")[0];
        }
        try
        {
            JarFile jarFile = loadJarFile(codePath);
            Enumeration<JarEntry> entries = jarFile.entries(); //gives ALL entries in jar
            while(entries.hasMoreElements())
            {
                String name = entries.nextElement().getName();
                if(name.startsWith(resourceDir + "/") && !name.equals(resourceDir + "/"))
                { //filter according to the path
                    fileNames.add(name);
                }
            }
            jarFile.close();
        }
        catch(NoJarFileException e)
        {
            File codeFile = new File(codePath);
            if(GeneralUtil.getType(codeFile).equals("jar"))
            {//weird bug with intellij??
                throw new IllegalStateException("Jar Exists but is not accessible");
            }
            else
            { // Run with IDE
                
                try
                {
                    for(File file : Objects.requireNonNull(new File(Objects.requireNonNull(getLoader().getResource(
                            resourceDir)).toURI()).listFiles()))
                    {
                        fileNames.add(resourceDir + "/" + file.getName());
                    }
                }
                catch(URISyntaxException ex)
                {
                    ex.printStackTrace();
                }
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
