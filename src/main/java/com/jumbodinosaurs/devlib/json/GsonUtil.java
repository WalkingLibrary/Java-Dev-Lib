package com.jumbodinosaurs.devlib.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.google.typeadapters.gson.RuntimeTypeAdapterFactory;
import com.jumbodinosaurs.devlib.reflection.ReflectionUtil;
import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.devlib.util.objects.ObjectHolder;

import java.io.File;
import java.util.ArrayList;

public class GsonUtil
{
    
    public static <E> void saveObjectsToHolderList(File fileToSaveTo, ArrayList<E> objectsToSave, Class classType)
    {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting()
                                               .registerTypeAdapterFactory(getClassPathRuntimeTypeAdapterFactory(
                                                       classType));
        Gson gson = builder.create();
        ArrayList<ObjectHolder> objectHolders = new ArrayList<ObjectHolder>();
        for(E object : objectsToSave)
        {
            objectHolders.add(new ObjectHolder(object.getClass(), gson.toJson(object)));
        }
        GeneralUtil.writeContents(fileToSaveTo, new Gson().toJson(objectHolders), false);
    }
    
    public static <E> ArrayList<E> readObjectHoldersList(File fileToRead,
                                                         Class classType,
                                                         TypeToken<ArrayList<E>> typeToken)
    {
        /* When loading a list of polymorphic objects with Gson
         * The problem that all the subtypes aren't in the class path can arise
         * to get around this problem You can use an object holder and save the JSONized objects
         * in the object holders data field and then serialize the object holders and then read
         * the object holders back in and check whether the object they are holding is in the classpath
         * this allows you to save data and easily read in objects using the RuntimeTypeAdapterFactory
         * rather than throwing it all away
         *
         *  Process
         *  Read in the file assuming it's Object Holders
         *  Check each object holder to see if it's object is in the class path
         *  Add it to a JSON String for RuntimeTypeAdapter Context if it in the class path
         *  Load and return a list of the specified object from the context JSON String
         *  */
        try
        {
            //Read in the file assuming it's Object Holders
            ArrayList<ObjectHolder> objectHolders = readList(fileToRead,
                                                             ObjectHolder.class,
                                                             new TypeToken<ArrayList<ObjectHolder>>() {},
                                                             false);
            
            /* Check each object holder to see if it's object is in the class path
             * Add it to a JSON String for RuntimeTypeAdapter Context if it in the class path
             *
             * To make it easy to read and return a list of the specified object
             * we can create a JSON list from the Available subclasses
             * Format: ["JSON OBJECTS SEPARATED BY COMMAS HERE"]
             */
            if(objectHolders == null)
            {
                return null;
            }
            
            String classPathContext = "[";
            for(ObjectHolder objectHolder : objectHolders)
            {
                if(objectHolder.isInClassPath())
                {
                    classPathContext += objectHolder.getData() + ",";
                }
            }
            //Remove the last comma if any objects were added
            boolean objectsWereAdded = classPathContext.length() > 1;
            if(objectsWereAdded)
            {
                //Remove the last Comma
                classPathContext = classPathContext.substring(0, classPathContext.length() - 1);
            }
            //Add the last Bracket for the list
            classPathContext += "]";
            
            return readList(classPathContext, classType, typeToken, true);
        }
        catch(JsonParseException e)
        {
            throw e;
        }
    }
    
    
    public static <E> ArrayList<E> readList(File fileToRead,
                                            Class classType,
                                            TypeToken<ArrayList<E>> typeToken,
                                            boolean isPolymorphic) throws JsonParseException
    {
        String fileContents = GeneralUtil.scanFileContents(fileToRead);
        return readList(fileContents, classType, typeToken, isPolymorphic);
    }
    
    public static <E> ArrayList<E> readList(String fileContents,
                                            Class classType,
                                            TypeToken<ArrayList<E>> typeToken,
                                            boolean isPolymorphic) throws JsonParseException
    {
        if(isPolymorphic)
        {
            GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(getContextRuntimeTypeAdapterFactory(
                    fileContents,
                    classType));
            Gson gson = builder.create();
            return gson.fromJson(fileContents, typeToken.getType());
        }
        else
        {
            return new Gson().fromJson(fileContents, typeToken.getType());
        }
    }
    
    /*
     * RuntimeTypeAdapterFactory Serialization and Deserialization Note
     * Goal: new Gson().toJson(List<Object>) -> File
     *       File(With List<Object> in json Form) -> List<Object>
     *       List<Object> holding Polymorphic objects
     *
     *       Requirements For The Object are that they contain a non-transient Field Named type
     *       to aid in the creation of the RuntimeTypeAdapterFactory
     *
     *       Their Type being assigned the value of their getClass().getSimpleName()
     *
     *       The Retrieval of the RuntimeTypeAdapterFactory can be contextualized to the data being read
     *       or simply the runtime classpath.
     *
     */
    
    public static RuntimeTypeAdapterFactory<?> getClassPathRuntimeTypeAdapterFactory(Class classContext)
    {
        
        RuntimeTypeAdapterFactory<?> adapterFactory = RuntimeTypeAdapterFactory.of(classContext, "type");
        for(Class classType : ReflectionUtil.getSubClasses(classContext))
        {
            adapterFactory.registerSubtype(classType, classType.getSimpleName());
        }
        return adapterFactory;
    }
    
    
    public static RuntimeTypeAdapterFactory<?> getContextRuntimeTypeAdapterFactory(String context, Class classContext)
    {
        RuntimeTypeAdapterFactory<?> adapterFactory = RuntimeTypeAdapterFactory.of(classContext, "type");
        for(Class classType : ReflectionUtil.getSubClasses(classContext))
        {
            if(context.contains(classType.getSimpleName()))
            {
                adapterFactory.registerSubtype(classType, classType.getSimpleName());
            }
        }
        return adapterFactory;
    }
}
