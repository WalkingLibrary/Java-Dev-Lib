package com.jumbodinosaurs.devlib.util;

import com.google.gson.Gson;
import com.jumbodinosaurs.devlib.email.Email;
import com.jumbodinosaurs.devlib.util.objects.HttpResponse;
import com.jumbodinosaurs.devlib.util.objects.PostRequest;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Date;
import java.util.Properties;

public class WebUtil
{
    
    public static HttpResponse getResponse(HttpURLConnection connection) throws IOException
    {
        HttpResponse urlResponse = null;
        
        int returnCode = connection.getResponseCode();
        InputStream connectionIn = null;
        if(returnCode == 200)
        {
            connectionIn = connection.getInputStream();
        }
        else
        {
            connectionIn = connection.getErrorStream();
        }
        
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connectionIn));
        String response = "";
        while(bufferedReader.ready())
        {
            response += bufferedReader.readLine();
        }
        
        urlResponse = new HttpResponse(returnCode, response);
        
        return urlResponse;
    }
    
    
    public static HttpResponse getResponse(HttpsURLConnection connection) throws IOException
    {
        HttpResponse urlResponse = null;
        
        int returnCode = connection.getResponseCode();
        InputStream connectionIn = null;
        if(returnCode == 200)
        {
            connectionIn = connection.getInputStream();
        }
        else
        {
            connectionIn = connection.getErrorStream();
        }
        
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connectionIn));
        String response = "";
        while(bufferedReader.ready())
        {
            response += bufferedReader.readLine();
        }
        
        urlResponse = new HttpResponse(returnCode, response);
        
        return urlResponse;
    }
    
    public static HttpResponse sendPostRequestToJumboDinosaurs(PostRequest request) throws Exception
    {
        ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
        String response = "";
        int status = 400;
        String certificatePath = "certificates/DSTRootCAx3.txt";
        File rootCA = resourceLoader.getResource("certificates/DSTRootCAx3.txt");
        String certificate;
        if(rootCA != null)
        {
            certificate = GeneralUtil.scanFileContents(rootCA);
        }
        else
        {
            certificate = resourceLoader.getResourceAsStream(certificatePath);
        }
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        ByteArrayInputStream bytes = new ByteArrayInputStream(certificate.getBytes());
        Certificate ca;
        try
        {
            ca = cf.generateCertificate(bytes);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new HttpResponse(status, response);
        }
        
        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);
        
        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        
        // Create an SSLContext that uses our TrustManager
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);
        
        String url = "https://www.jumbodinosaurs.com/" + new Gson().toJson(request);
        URL address = new URL(url);
        HttpsURLConnection connection = (HttpsURLConnection) address.openConnection();
        connection.setSSLSocketFactory(context.getSocketFactory());
        connection.setRequestMethod("POST");
        InputStream input;
        status = connection.getResponseCode();
        if(status == 200)
        {
            input = connection.getInputStream();
        }
        else
        {
            input = connection.getErrorStream();
        }
        
        while(input.available() > 0)
        {
            response += (char) input.read();
        }
        return new HttpResponse(status, response);
    }
    
    
    //https://dzone.com/articles/sending-mail-using-javamail-api-for-gmail-server
    public static void sendEmail(Email email,
                                 String userEmailAddress,
                                 String topic,
                                 String message) throws MessagingException
    {
        //Setting up configurations for the email connection to the Google SMTP server using TLS
        Properties props = new Properties();
        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //Establishing a session with required user details
        javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(email.getUsername(), email.getPassword());
            }
        });
        
        
        //Creating a Message object to set the email content
        MimeMessage msg = new MimeMessage(session);
        //Storing the comma separated values to email addresses
        String to = userEmailAddress;
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
