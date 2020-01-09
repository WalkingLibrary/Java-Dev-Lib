package com.jumbodinosaurs.devlib.util;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;

public class ResourceLoaderUtil
{
    public ClassLoader getLoader()
    {
        return getClass().getClassLoader();
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
