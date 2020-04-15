package com.jumbodinosaurs.devlib.util.objects;

public class PostRequest
{
    private String username;
    private String password;
    private String email;
    private String token;
    private String path;
    private String command;
    private String content;
    private String captchaCode;
    
    public PostRequest()
    {
    
    }
    
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public String getPath()
    {
        return path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public String getCommand()
    {
        return command;
    }
    
    public void setCommand(String command)
    {
        this.command = command;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getCaptchaCode()
    {
        return captchaCode;
    }
    
    public void setCaptchaCode(String captchaCode)
    {
        this.captchaCode = captchaCode;
    }
    
    @Override
    public String toString()
    {
        return "PostRequest{" +
                       "username='" + username + '\'' +
                       ", password='" + password + '\'' +
                       ", email='" + email + '\'' +
                       ", token='" + token + '\'' +
                       ", path='" + path + '\'' +
                       ", command='" + command + '\'' +
                       ", content='" + content + '\'' +
                       ", captchaCode='" + captchaCode + '\'' +
                       '}';
    }
}
