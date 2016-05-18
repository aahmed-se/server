package models;

import org.mongodb.morphia.annotations.Entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static main.Bootstrap.CONFIG;
/**
 * Created by CLOE on 27/01/2016.
 */
@Entity("user")
public class User extends Model {

    private String login;
    private String password;
    private final String BEFORE = CONFIG.getString("user.salting.before");
    private final String AFTER = CONFIG.getString("user.salting.after");

    public User(String login, String password) {
        super();
        this.login = login;
        setPassword(password);
    }

    public User(String _id, String login, String password) {
        super(_id);
        this.login = login;
        this.password = encrypt(password);
    }

    /**
     *
     * @param string
     * @return encrypted string with before and after
     */
    private String encrypt(String string){
        String generatedPassword = "";
        try {
            string = BEFORE + string + AFTER;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(string.getBytes());
            byte byteData[] = md5.digest();

            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < byteData.length; i++)
                stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

            generatedPassword = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = encrypt(password);
    }

    @Override
    public User find() {
        return null;
    }
}