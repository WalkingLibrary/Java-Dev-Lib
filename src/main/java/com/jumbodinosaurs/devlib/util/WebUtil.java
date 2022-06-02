package com.jumbodinosaurs.devlib.util;

import com.google.gson.Gson;
import com.jumbodinosaurs.devlib.discord.DiscordWebHookAPIMessage;
import com.jumbodinosaurs.devlib.reflection.ResourceLoaderUtil;
import com.jumbodinosaurs.devlib.util.objects.HttpResponse;
import com.jumbodinosaurs.devlib.util.objects.PostRequest;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebUtil
{
    
    public static HttpResponse getResponse(HttpURLConnection connection)
            throws IOException
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
        
        String response = "";
        
        if(connectionIn != null)
        {
            response = GeneralUtil.scanStream(connectionIn);
        }
        urlResponse = new HttpResponse(returnCode, response);
        urlResponse.setHeaders(connection.getHeaderFields());
        return urlResponse;
    }
    
    
    public static HttpResponse getResponse(HttpsURLConnection connection)
            throws IOException
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
        String response = "";
        
        if(connectionIn != null)
        {
            response = GeneralUtil.scanStream(connectionIn);
        }
        urlResponse = new HttpResponse(returnCode, response);
        urlResponse.setHeaders(connection.getHeaderFields());
        return urlResponse;
    }
    
    public static HttpURLConnection buildConnection(String url, boolean secure)
            throws IOException
    {
        URL address = new URL(url);
        if(secure)
        {
            HttpsURLConnection connection = (HttpsURLConnection) address.openConnection();
            connection.addRequestProperty("User-Agent", "Dev-Lib-Client");
            return connection;
        }
        
        HttpURLConnection connection = (HttpURLConnection) address.openConnection();
        connection.addRequestProperty("User-Agent", "Dev-Lib-Client");
        return connection;
    }
    
    
    public static HttpResponse sendPostRequestToJumboDinosaurs(PostRequest request)
            throws Exception
    {
        /*
         * Process for Sending A Post Request To JumboDinosaurs
         * Create an SSL Context
         * Send the Post Request over HTTPs
         *
         *
         *  */
        
        
        /* Process for Creating a SSL Context
         * Read the Root Cert from the Resources
         * Create a Certificate Object
         * Create/Combine the Keystore with the Certificate
         * Create a TrustManager that trusts the CAs in our KeyStore
         * Create an SSLContext that uses our TrustManager
         *
         */
        
        //Read the Root Cert from the Resources
        ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
        String response = "";
        int status = 400;
        String certificatePath = "certificates/DSTRootCAx3.txt";
        String certificate;
        certificate = resourceLoader.scanResource(certificatePath);
        
        // Create a Certificate Object
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
        
        //Create/Combine the Keystore with the Certificate
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
        
        
        // Send the Post Request over HTTPs
        String url = "https://jumbodinosaurs.com/";
        HttpsURLConnection connection = (HttpsURLConnection) buildConnection(url, true);
        connection.setSSLSocketFactory(context.getSocketFactory());
        
        
        //Send the Request
        String message = new Gson().toJson(request);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        byte[] bytesToSend = message.getBytes();
        connection.setRequestProperty("Content-Length", "" + bytesToSend.length);
        BufferedOutputStream writer = new BufferedOutputStream(connection.getOutputStream());
        writer.write(bytesToSend);
        writer.flush();
        writer.close();
        
        //Read the Response
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
    
    
    public static HttpResponse sendLocalHostPostRequest(PostRequest postRequest)
            throws IOException
    {
        
        String url = "http://localhost/";
        
        String response = "";
        int status = 400;
        HttpURLConnection connection = buildConnection(url, false);
        
        String message = new Gson().toJson(postRequest);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        byte[] bytesToSend = message.getBytes();
        connection.setRequestProperty("Content-Length", "" + bytesToSend.length);
        BufferedOutputStream writer = new BufferedOutputStream(connection.getOutputStream());
        writer.write(bytesToSend);
        writer.flush();
        writer.close();
        
        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        status = connection.getResponseCode();
        InputStream input;
        
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
    
    
    public static HttpResponse sendMessageToWebHook(String webHook, DiscordWebHookAPIMessage discordAPIMessage)
            throws IOException
    {
        HttpsURLConnection connection = (HttpsURLConnection) buildConnection(webHook, true);
        String messageToSend = new Gson().toJson(discordAPIMessage);
        byte[] bytesToSend = messageToSend.getBytes();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", "" + bytesToSend.length);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        BufferedOutputStream writer = new BufferedOutputStream(connection.getOutputStream());
        writer.write(bytesToSend);
        writer.flush();
        writer.close();
        
        return WebUtil.getResponse(connection);
    }
    
    
    /**
     * Returns a list with all links contained in the input
     */
    public static ArrayList<String> extractUrls(String text)
    {
        ArrayList<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);
        
        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                                             urlMatcher.end(0)));
        }
        
        return containedUrls;
    }
}
