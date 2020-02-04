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
    
    public static void initializeEmails(File parentFile)
    {
        emailDir = GeneralUtil.checkFor(parentFile, "Emails");
        emailMemory = GeneralUtil.checkFor(emailDir, "emails.json");
        loadEmails();
        if(emails == null)
        {
            emails = new ArrayList<Email>();
        }
        saveEmails();
        
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
    
    
    private static ArrayList<Email> loadEmails()
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
    
    public static Email getEmail(String username) throws NoSuchEmailException
    {
        for(Email email : emails)
        {
            if(email.getUsername().equals(username))
            {
                return email;
            }
        }
        throw new NoSuchEmailException("No Email with the Username of " + username);
    }
    
    
    //Returns true if a email was updated
    public boolean updateEmail(Email updatedEmail)
    {
        for(Email email : emails)
        {
            if(email.getUsername().equals(updatedEmail.getUsername()))
            {
                emails.remove(email);
                emails.add(updatedEmail);
                saveEmails();
                return true;
            }
        }
        return false;
    }
}
