package com.jumbodinosaurs.devlib.util;

import com.jumbodinosaurs.devlib.util.exceptions.NoJarFileException;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
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
        /* Getting an ArrayList of the SubClass Type of a given Class
         * Scan the Runtime Environment
         * Get all SubClass Instances of the Given classTypePattern
         * Filter this list for Abstract and Local Instances
         */
    
        //Scan the Runtime Environment
        try(ScanResult scanResult = new ClassGraph().enableAllInfo().scan())
        {
            //Get all SubClass Instances of the Given classTypePattern
            ClassInfoList controlClasses = scanResult.getSubclasses(classTypePattern.getCanonicalName());
            List<Class<?>> controlClassRefs = controlClasses.loadClasses();
    
            //Filter this list for Abstract and Local Instances
            ArrayList<Class> classes = new ArrayList<Class>();
            for(Class classType : controlClassRefs)
            {
                try
                {
                    //Local Instances
                    if(classType.getCanonicalName() != null)
                    {
                        //Abstract Instances
                        if(!Modifier.isAbstract(classType.getModifiers()))
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
        return ReflectionUtil.class.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    }
    
    public static JarFile reflectJarFile() throws IOException, NoJarFileException
    {
        ResourceLoaderUtil loaderUtil = new ResourceLoaderUtil();
        return loaderUtil.loadJarFile(getCodeExePath());
    }
}
