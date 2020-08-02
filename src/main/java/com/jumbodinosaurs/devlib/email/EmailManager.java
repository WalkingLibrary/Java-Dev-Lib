package com.jumbodinosaurs.devlib.email;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jumbodinosaurs.devlib.json.GsonUtil;
import com.jumbodinosaurs.devlib.util.GeneralUtil;

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
        emails = loadEmails();
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
        ArrayList<Email> emails = new ArrayList<Email>();
        try
        {
            emails = GsonUtil.readObjectHoldersList(emailMemory, Email.class, new TypeToken<ArrayList<Email>>() {});
        }
        catch(JsonParseException e)
        {
            throw new IllegalStateException("Email Data is Not Loadable");
        }
    
        if(emails != null)
        {
            for(Email domain: emails)
            {
                domain.setType(domain.getClass().getSimpleName());
            }
        }
        
        return emails;
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
    
    public static ArrayList<Email> getEmails()
    {
        return emails;
    }
    
    
    public static boolean removeEmail(Email emailToRemove)
    {
        for(Email email : emails)
        {
            if(email.getUsername().equals(emailToRemove.getUsername()))
            {
                emails.remove(email);
                saveEmails();
                return true;
            }
        }
        return false;
    }
    
    public static File getEmailMemory()
    {
        return emailMemory;
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
