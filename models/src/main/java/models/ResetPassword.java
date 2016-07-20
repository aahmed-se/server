package models;

import conf.Configuration;
import mongo.Database;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.*;

import static conf.Configuration.CONFIG;

/**
 * Created by to116676 on 01/06/2016.
 */
@Entity(value = "resetPassword", noClassnameStored = true)
public class ResetPassword extends Model{

    private static final Logger log = LoggerFactory.getLogger(ResetPassword.class);

    private String key;
    private Long date;
    @Reference(idOnly = true)
    private User user;

    public ResetPassword() {
    }

    public ResetPassword(User user) throws Exception {
        this(null, user);
    }

    public ResetPassword(ObjectId _id, User user) throws Exception {
        super(_id);

        Database.get().getDatastore().delete(
                Database.get().getDatastore().find(
                        ResetPassword.class)
                        .filter("user = ",user));

        this.key = generateKey();
        this.date = new Date().getTime()/1000;
        this.user = user;
        //sendMail();
    }

    public String getKey() {
        return key;
    }

    public Long getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }


    private static String generateKey(){
        String key = "";
        int length = Configuration.CONFIG.getInt("user.keyLength");
        String letters = "azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN1234567890";

        for (int i = 0; i < length; i++) {
            char letter = letters.charAt((int) (Math.random() * (letters.length())));
            key = key.concat(String.valueOf(letter));
        }

        ResetPassword resetPassword = Database.get().getDatastore().find(ResetPassword.class).filter("key = ", key).get();
        if(resetPassword != null) key = generateKey();

        return key;
    }

    private void sendMail()throws Exception{
        //parameters
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host",  CONFIG.getString("mail.smtp.host"));
        properties.setProperty("mail.smtp.port",  CONFIG.getString("mail.smtp.port "));
        properties.put("mail.smtp.socketFactory.port", CONFIG.getString("mail.smtp.port "));
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(CONFIG.getString("mail.smtp.login"), CONFIG.getString("mail.smtp.password"));
                    }
                });

        //Message
        String text = "Go to the " + CONFIG.getString("sent.resetPassword.uri");
        String subject = "Reset your Password";
        MimeMessage message = new MimeMessage(session);
        message.setText(text);
        message.setSubject(subject);
        message.setFrom(new InternetAddress(CONFIG.getString("mail.from")));
        message.addRecipients(Message.RecipientType.TO, this.getUser().getEmail());

        //Sending
        Transport.send(message);
    }

    public boolean isValid(){
        return this.getDate() + CONFIG.getLong("user.resetPassword.timeout") > new Date().getTime()/1000;
    }
}
