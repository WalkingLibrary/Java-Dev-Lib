package com.jumbodinosaurs.devlib.util;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class ResourceLoaderUtil
{
    public ClassLoader getLoader()
    {
        return getClass().getClassLoader();
    }
    
    public List<File> getFiles(String resourceDir)
    {
        File resourceDirFile = getResource(resourceDir);
        if(resourceDirFile == null)
        {
            return null;
        }
        List<File> resources = Arrays.asList(resourceDirFile.listFiles());
        return resources;
        
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
