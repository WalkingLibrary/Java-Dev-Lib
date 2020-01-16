package com.jumbodinosaurs.devlib.util;


import com.google.gson.Gson;
import com.jumbodinosaurs.devlib.util.objects.HttpResponse;
import com.jumbodinosaurs.devlib.util.objects.PostRequest;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class GeneralUtil
{
    
    private static ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
    
    
    public static HttpResponse sendPostRequestToJumboDinosaurs(PostRequest request)
    {
        String response = "";
        int status = 400;
        try
        {
            String certificatePath = "certificates/DSTRootCAx3.txt";
            File rootCA = resourceLoader.getResource("certificates/DSTRootCAx3.txt");
            String certificate;
            if(rootCA != null)
            {
                certificate = scanFileContents(rootCA);
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
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return new HttpResponse(status, response);
    }
    
    
    public static File checkFor(File file, String name, boolean forceDir)
    {
        boolean needToMakeFile = true;
        String[] contentsOfFile = file.list();
        for(int i = 0; i < contentsOfFile.length; i++)
        {
            if(contentsOfFile.equals(name))
            {
                needToMakeFile = false;
            }
        }
        
        File neededFile = new File(file.getPath() + "/" + name);
        if(needToMakeFile)
        {
            if(forceDir || name.indexOf(".") < 0)
            {
                neededFile.mkdir();
            }
            else
            {
                try
                {
                    neededFile.createNewFile();
                }
                catch(Exception e)
                {
                    System.out.println("Error Creating File");
                }
            }
        }
        return neededFile;
    }
    
    
    /* @Function: Checks for the String name in the given Dir of File file
     * returns it and makes it if not there.
     *
     * @Return: desired name within file
     * @param1: File file Dir to search for name in
     * @param2: String Name name to search for in file
     * @PreCondition: File must be a Dir and also Exist
     */
    public static File checkFor(File file, String name)
    {
        boolean needToMakeFile = true;
        String[] contentsOfFile = file.list();
        for(int i = 0; i < contentsOfFile.length; i++)
        {
            if(contentsOfFile.equals(name))
            {
                needToMakeFile = false;
            }
        }
        
        File neededFile = new File(file.getPath() + "/" + name);
        if(needToMakeFile)
        {
            if(name.indexOf(".") >= 0)
            {
                try
                {
                    neededFile.createNewFile();
                }
                catch(Exception e)
                {
                    System.out.println("Error Creating File");
                }
            }
            else
            {
                neededFile.mkdir();
            }
        }
        return neededFile;
    }
    
    
    /*
         tl;dr it checks the given file for the given local path and makes it if it's not there

         Example: /home/systemop -> File file
                  /stats/home.txt -> String localPath
                  checks for /home/systemop/stats/home.txt
                  if not there then it will check and make each file in the local path

     */
    public static File checkForLocalPath(File file, String localPath)
    {
        localPath = fixPathSeparator(localPath);
        
        
        ArrayList<String> levels = new ArrayList<String>();
        String temp = localPath;
        String level = "";
        if(temp.indexOf(File.separator) != 0)
        {
            temp = File.separator + temp;
        }
        
        if(temp.lastIndexOf(File.separator) != temp.length())
        {
            temp += File.separator;
        }
        
        char[] tempchars = temp.toCharArray();
        int indexOfLastSlash = 0;
        for(int i = 1; i < temp.length(); i++)
        {
            if(tempchars[i] == File.separatorChar)
            {
                level = temp.substring(indexOfLastSlash + 1, i);
                levels.add(level);
                indexOfLastSlash = i;
            }
        }
        
        File lastParent = file;
        for(String subPath : levels)
        {
            try
            {
                File fileToMake = checkFor(lastParent, subPath);
                lastParent = fileToMake;
            }
            catch(Exception e)
            {
                
                System.out.println("Error Creating Local Path");
                e.printStackTrace();
            }
            
        }
        return lastParent;
    }
    
    public static String fixPathSeparator(String path)
    {
        
        
        char[] charToChange = path.toCharArray();
        if(File.separator.equals("\\"))
        {
            for(int i = 0; i < charToChange.length; i++)
            {
                if(charToChange[i] == '/')
                {
                    charToChange[i] = '\\';
                }
            }
        }
        else
        {
            for(int i = 0; i < charToChange.length; i++)
            {
                if(charToChange[i] == '\\')
                {
                    charToChange[i] = '/';
                }
            }
        }
        
        String pathToReturn = "";
        for(char character : charToChange)
        {
            pathToReturn += character;
        }
        return pathToReturn;
        
    }
    
    
    public static void writeContents(File fileToWrite, String contents, boolean append)
    {
        try
        {
            PrintWriter output = new PrintWriter(new FileOutputStream(fileToWrite, append));
            output.write(contents);
            output.close();
        }
        catch(Exception e)
        {
            System.out.println("Error writing to file");
        }
    }
    
    
    public static String getType(File file)
    {
        String temp = file.getName();
        while(temp.indexOf(".") > -1)
        {
            temp = temp.substring(temp.indexOf(".") + 1);
        }
        return temp;
    }
    
    
    //For Reading any file on the system
    public static String scanFileContents(File file)
    {
        //Read File
        String fileRequestedContents = "";
        try
        {
            Scanner input = new Scanner(file);
            while(input.hasNextLine())
            {
                fileRequestedContents += input.nextLine();
                fileRequestedContents += "\n";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Error Reading File Contents");
        }
        return fileRequestedContents;
    }
    
    //For Reading any file on the system
    public static String scanStream(InputStream stream)
    {
        //Read File
        String fileRequestedContents = "";
        try
        {
            Scanner input = new Scanner(stream);
            while(input.hasNextLine())
            {
                fileRequestedContents += input.nextLine();
                fileRequestedContents += "\n";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Error Reading File Contents");
        }
        return fileRequestedContents;
    }
    
    public static File[] listFilesRecursive(File directory)
    {
        ArrayList<File> files = new ArrayList<File>();
        for(File file : directory.listFiles())
        {
            if(file.isDirectory())
            {
                files.addAll(Arrays.asList(listFilesRecursive(file)));
            }
            else
            {
                files.add(file);
            }
        }
        File[] filesToReturn = new File[files.size()];
        for(int i = 0; i < files.size(); i++)
        {
            filesToReturn[i] = files.get(i);
        }
        return filesToReturn;
    }
    
}




