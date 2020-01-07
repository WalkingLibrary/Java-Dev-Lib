package com.jumbodinosaurs.devlib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.google.typeadapters.gson.RuntimeTypeAdapterFactory;

import java.io.File;
import java.util.ArrayList;

public class GsonUtil
{
    
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
