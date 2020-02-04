package com.jumbodinosaurs.devlib.options;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.devlib.util.GsonUtil;

import java.io.File;
import java.util.ArrayList;

public class OptionsManager
{
    private File optionsFile;
    private ArrayList<Option> options;
    
    public OptionsManager(File optionsFile)
    {
        this.optionsFile = optionsFile;
        this.options = GsonUtil.readList(optionsFile, Option.class, new TypeToken<ArrayList<Option>>() {}, false);
        if(this.options == null)
        {
            this.options = new ArrayList<Option>();
        }
    }
    
    public Option getOption(String identifier) throws NoSuchOptionException
    {
        for(Option option : options)
        {
            if(option.getIdentifier().equals(identifier))
            {
                return option;
            }
        }
        
        throw new NoSuchOptionException("No Option with " + identifier + " as identifier.");
    }
    
    private void saveOptions()
    {
        GeneralUtil.writeContents(optionsFile,
                                  new Gson().toJson(options, new TypeToken<ArrayList<Option>>() {}.getType()),
                                  false);
    }
    
    public void setOption(Option option)
    {
        try
        {
            Option oldOption = getOption(option.getIdentifier());
            oldOption.setOption(option.getOption());
        }
        catch(NoSuchOptionException e)
        {
            this.options.add(option);
        }
        saveOptions();
    }
}
