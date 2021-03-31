package com.jumbodinosaurs.devlib.discord;

public class DiscordWebHookAPIMessage
{
    private String username;
    private String avatar_url;
    private String content;
    
    public DiscordWebHookAPIMessage(String username, String avatar_url, String content)
    {
        this.username = username;
        this.avatar_url = avatar_url;
        this.content = content;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getAvatar_url()
    {
        return avatar_url;
    }
    
    public void setAvatar_url(String avatar_url)
    {
        this.avatar_url = avatar_url;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
}
