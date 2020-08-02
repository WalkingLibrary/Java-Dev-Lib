package com.jumbodinosaurs.devlib.email;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class DefaultEmail extends Email
{
    private String password;
    
    public DefaultEmail(String username, String password)
    {
        super(username);
        this.password = password;
    }
    
  
    
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    
    //https://dzone.com/articles/sending-mail-using-javamail-api-for-gmail-server
    //https://www.geeksforgeeks.org/sending-email-java-ssltls-authentication/
    @Override
    public void sendEmail(String to, String topic, String message)
            throws Exception
    {
        //Setting up configurations for the email connection to the Google SMTP server using TLS
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //Establishing a session with required user details
        javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(getUsername(), getPassword());
            }
        });
    
    
        //Creating a Message object to set the email content
        MimeMessage msg = new MimeMessage(session);
        /*Parsing the String with default delimiter as a comma by marking the boolean as true and storing the email
                addresses in an array of InternetAddress objects*/
        InternetAddress[] address = InternetAddress.parse(to, true);
        //Setting the recipients from the address variable
        msg.setRecipients(Message.RecipientType.TO, address);
    
        msg.setSubject(topic);
        msg.setSentDate(new Date());
        msg.setText(message);
        msg.setHeader("XPriority", "1");
        Transport.send(msg);
    }
}
