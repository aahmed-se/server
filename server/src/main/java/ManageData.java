import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by CLOE on 10/02/2016.
 */
public class ManageData {

    Database db;
    com.mongodb.DB database = db.initBDD();

    public static void main(String[] args) {
        Field[] result = User.class.getDeclaredFields();
        for (Field f : result) {
            System.out.println("- " + f.getName());
        }
    }

}
