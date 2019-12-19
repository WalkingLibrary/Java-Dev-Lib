package com.jumbodinosaurs.devlib.commands;

import java.io.File;
import java.util.ArrayList;

public class MessageResponse
{
    private String message;
    private ArrayList<File> attachments;
    
    public MessageResponse(String message, ArrayList<File> attachments)
    {
        this.message = message;
        this.attachments = attachments;
    }
    
    public MessageResponse(String message)
    {
        this.message = message;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public ArrayList<File> getAttachments()
    {
        return attachments;
    }
    
    public void setAttachments(ArrayList<File> attachments)
    {
        this.attachments = attachments;
    }
}
