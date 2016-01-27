import com.mongodb.*;
import org.mongodb.morphia.Morphia;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

/**
 * Created by Clo� on 29/10/2015.
 */

public class Database {

     public static com.mongodb.DB initBDD() throws UnknownHostException {
         Morphia morphia = new Morphia();
         MongoClient mongo = new MongoClient("10.31.2.7", 5151);
         com.mongodb.DB db = mongo.getDB("test");
         //Datastore ds = morphia.createDatastore(mongo,"test");
         Set<String> collections = db.getCollectionNames();
         for (String s : collections) {
             System.out.println(s);
         }

         return db;
    }
}
