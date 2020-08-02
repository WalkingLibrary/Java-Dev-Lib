package com.jumbodinosaurs.devlib.email;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.jumbodinosaurs.devlib.util.GeneralUtil;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class GoogleAPIEmail extends Email
{
    private transient static final String APPLICATION_NAME = "Web Server Application";
    private transient static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private transient static final List<String> SCOPES = Arrays.asList(GmailScopes.MAIL_GOOGLE_COM);
    private transient Gmail service;
    private transient NetHttpTransport HTTP_TRANSPORT;
    private String jsonCredentials;
    private String parentDirPath;
    
    public GoogleAPIEmail(String username, String jsonCredentials, String parentDirPath)
    {
        super(username);
        this.jsonCredentials = jsonCredentials;
        this.parentDirPath = parentDirPath;
    }
    
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
    
    //https://developers.google.com/gmail/api/guides/sending
    @Override
    public void sendEmail(String to, String topic, String message)
            throws Exception
    {
       // Print the labels in the user's account.
        
        String user = "me";
        
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        MimeMessage mimeMessage = new MimeMessage(session);
        
        mimeMessage.setFrom(new InternetAddress(user));
        mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject(topic);
        mimeMessage.setText(message);
        
        Message emailMessage = createMessageWithEmail(mimeMessage);
        service.users().messages().send(user, emailMessage).execute();
    }
    
    public void activate()
            throws GeneralSecurityException, IOException
    {
        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        service = new Gmail.Builder(HTTP_TRANSPORT,
                                          JSON_FACTORY,
                                          getCredentials(HTTP_TRANSPORT)).setApplicationName(APPLICATION_NAME).build();
    
    }
    
    public String getJsonCredentials()
    {
        return jsonCredentials;
    }
    
    
    // Copyright 2018 Google LLC
    //
    // Licensed under the Apache License, Version 2.0 (the "License");
    // you may not use this file except in compliance with the License.
    // You may obtain a copy of the License at
    //
    //     http://www.apache.org/licenses/LICENSE-2.0
    //
    // Unless required by applicable law or agreed to in writing, software
    // distributed under the License is distributed on an "AS IS" BASIS,
    // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    // See the License for the specific language governing permissions and
    // limitations under the License.
    
    public void setJsonCredentials(String jsonCredentials)
    {
        this.jsonCredentials = jsonCredentials;
    }
    
    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException
    {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new StringReader(jsonCredentials));
        
        File googleAPITokens = GeneralUtil.checkFor(new File(parentDirPath), "Google API Tokens");
        
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
                                                                                   JSON_FACTORY,
                                                                                   clientSecrets,
                                                                                   SCOPES).setDataStoreFactory(new FileDataStoreFactory(
                googleAPITokens)).setAccessType("offline").build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
}
