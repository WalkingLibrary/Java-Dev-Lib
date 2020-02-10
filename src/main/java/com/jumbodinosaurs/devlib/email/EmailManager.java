package com.jumbodinosaurs.devlib.email;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.devlib.util.GsonUtil;

import java.io.File;
import java.lang.reflect.Type;
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
        Type typeToken = new TypeToken<ArrayList<Email>>() {}.getType();
        String emailsJsonized = new Gson().toJson(emails, typeToken);
        GeneralUtil.writeContents(emailMemory, emailsJsonized, false);
    }
    
    
    private static ArrayList<Email> loadEmails()
    {
        ArrayList<Email> domains = new ArrayList<Email>();
        try
        {
            domains = GsonUtil.readList(emailMemory, Email.class, new TypeToken<ArrayList<Email>>() {}, false);
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
