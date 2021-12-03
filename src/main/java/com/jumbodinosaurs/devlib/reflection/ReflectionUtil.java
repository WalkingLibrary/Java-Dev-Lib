package com.jumbodinosaurs.devlib.reflection;

import com.jumbodinosaurs.devlib.reflection.exceptions.NoJarFileException;
import com.jumbodinosaurs.devlib.reflection.exceptions.NoSuchJarAttribute;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.JarFile;


public class ReflectionUtil
{
    public static boolean set(Field field, Object object, String value, Class type)
    {
        try
        {
            if(type.equals(int.class))
            {
                field.setInt(object, Integer.parseInt(value));
            }
            else if(type.equals(double.class))
            {
                field.setDouble(object, Double.parseDouble(value));
            }
            else if(type.equals(char.class))
            {
                field.setChar(object, value.charAt(0));
            }
            else if(type.equals(boolean.class))
            {
                field.setBoolean(object, Boolean.parseBoolean(value));
            }
            else if(type.equals(float.class))
            {
                field.setFloat(object, Float.parseFloat(value));
            }
            else//can add more as i need em
            {
                field.set(object, value);
            }
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    public static ArrayList<Class> getSubClasses(Class classTypePattern)
    {
        return getSubClasses(classTypePattern, false, false);
    }
    
    public static ArrayList<Class> getSubClasses(Class classTypePattern, boolean includeInterfaces,
                                                 boolean includeAbstract)
    {
        /* Getting an ArrayList of the SubClass Type of a given Class
         * Scan the Runtime Environment
         * Get all SubClass Instances of the Given classTypePattern
         * Filter this list for Abstract and Local Instances
         */
        
        //Scan the Runtime Environment
        try(ScanResult scanResult = new ClassGraph().enableClassInfo().scan())
        {
            //Get all SubClass Instances of the Given classTypePattern
            ClassInfoList controlClasses = scanResult.getSubclasses(classTypePattern.getCanonicalName());
            ArrayList<Class<?>> subClasses = new ArrayList<Class<?>>();
            subClasses.addAll(controlClasses.loadClasses());
            
            if(includeInterfaces)
            {
                ClassInfoList interFaceClass = scanResult.getClassesImplementing(classTypePattern.getCanonicalName());
                subClasses.addAll(interFaceClass.loadClasses());
            }
            
            //Filter this list for Abstract and Local Instances
            ArrayList<Class> classes = new ArrayList<Class>();
            for(Class classType : subClasses)
            {
                try
                {
                    //Local Instances
                    if(classType.getCanonicalName() != null)
                    {
                        //Abstract Instances
                        if(!Modifier.isAbstract(classType.getModifiers()) || includeAbstract)
                        {
                            classes.add(Class.forName(classType.getCanonicalName()));
                        }
                    }
                }
                catch(ClassNotFoundException e)
                {
                    System.out.println("Could Not find the Class " + classType.getSimpleName());
                }
            }
            return classes;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<Class>();
    }
    
    
    public static String getCodeExePath()
    {
        ResourceLoaderUtil resourceLoaderUtil = new ResourceLoaderUtil();
        return resourceLoaderUtil.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    }
    
    public static String getCodeExePath(Object context)
    {
        return context.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    }
    
    public static JarFile reflectJarFile() throws IOException, NoJarFileException
    {
        ResourceLoaderUtil loaderUtil = new ResourceLoaderUtil();
        return loaderUtil.loadJarFile(getCodeExePath());
    }
    
    public static String getAttribute(String key) throws NoSuchJarAttribute
    {
        try
        {
            JarFile reflectedJar = reflectJarFile();
            Attributes attributes = reflectedJar.getManifest().getMainAttributes();
            if(attributes.getValue(key) != null)
            {
                return attributes.getValue(key);
            }
            throw new NoSuchJarAttribute("No Jar Attribute found matching " + key);
        }
        catch(Exception e)
        {
            throw new NoSuchJarAttribute(e.getMessage());
        }
        
    }
}
