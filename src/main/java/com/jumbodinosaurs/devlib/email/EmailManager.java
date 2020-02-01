package com.jumbodinosaurs.devlib.email;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.devlib.util.GsonUtil;

import java.io.File;
import java.util.ArrayList;

public class EmailManager
{
    private static File emailDir;
    private static File emailMemory;
    private static ArrayList<Email> emails;
    
    private static void initializeEmails(File parentFile)
    {
        emailDir = GeneralUtil.checkFor(parentFile, "Emails");
        emailMemory = GeneralUtil.checkFor(emailDir, "emails.json");
        
    }
    
    public static void addEmail(Email email)
    {
        emails.add(email);
        saveEmails();
    }
    
    private static void saveEmails()
    {
        saveEmails(emails);
    }
    
    private static void saveEmails(ArrayList<Email> emails)
    {
        GsonUtil.saveObjectsToHolderList(emailMemory, emails, Email.class);
    }
    
    
    private static ArrayList<Email> loadDomains()
    {
        ArrayList<Email> domains = new ArrayList<Email>();
        try
        {
            domains = GsonUtil.readObjectHoldersList(emailMemory, Email.class, new TypeToken<ArrayList<Email>>() {});
        }
        catch(JsonParseException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Email Data is Not Loadable");
        }
        
        /*
        Polymorphism Type Setting
        for(Email email : emails)
        {
            email.setType(email.getClass().getSimpleName());
        }
        */
        return domains;
    }
    
    public static ArrayList<Email> getEmails()
    {
        return emails;
    }
    
    //Returns true if a domain was updated
    public boolean updateDomain(Email updatedEmails)
    {
        for(Email email : emails)
        {
            if(email.getUsername().equals(updatedEmails.getUsername()))
            {
                emails.remove(email);
                emails.add(updatedEmails);
                saveEmails();
                return true;
            }
        }
        return false;
    }
}
