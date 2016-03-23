import com.mongodb.BasicDBObject;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.security.*;

/**
 * Created by CLOE on 27/01/2016.
 */
public class User implements DataProcessing<User> {

    private String login;
    private String password;
    private String key = Long.toHexString(Double.doubleToLongBits(Math.random()));

    public User(String login, String password) {
        this.login = login;
        this.password = password;
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

    public Field[] getDeclaredFields() {
        return this.getDeclaredFields();
    }

    public void insert() {
        BasicDBObject document = new BasicDBObject();
        Field[] fields = this.getDeclaredFields();
        for (Field f : fields) {
           // document.put(f.getName());
        }
        document.put("name", "lokesh");
        document.put("website", "howtodoinjava.com");

        BasicDBObject documentDetail = new BasicDBObject();
        documentDetail.put("addressLine1", "Sweet Home");
        documentDetail.put("addressLine2", "Karol Bagh");
        documentDetail.put("addressLine3", "New Delhi, India");

        document.put("address", documentDetail);

        //collection.insert(document);
    }

}
