package models;

import mongo.Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by CLOE on 27/01/2016.
 */
public class User extends Model {

    private String login;
    private String password;
    private String key = Long.toHexString(Double.doubleToLongBits(Math.random()));

    public User(String login, String password) throws Exception {
        super();
        this.login = login;
        this.password = encryptMdp(password);
    }

    public User(String _id, String login, String password) throws Exception {
        super(_id);
        this.login = login;
        this.password = encryptMdp(password);
    }

    public String encryptMdp(String password) throws Exception {
        String generatedPassword = null;
        System.out.println("AVANT : " + password);
        try {
            password = key + password + key;
            System.out.println("APRES 'SALAGE' : " + password);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("APRES HASH : " + generatedPassword);
        return generatedPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return password;
    }

    public void setMdp(String password) {
        this.password = password;
    }

    @Override
    public User find() {
        return null;
    }
}
