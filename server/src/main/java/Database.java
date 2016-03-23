import com.mongodb.*;
import org.mongodb.morphia.Morphia;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

/**
 * Created by Clo� on 29/10/2015.
 */

public class Database {

     public com.mongodb.DB initBDD() {
         Morphia morphia;
         MongoClient mongo;
         com.mongodb.DB db;

         try {
             morphia = new Morphia();
             mongo = new MongoClient("10.31.2.7", 5151);
             db = mongo.getDB("test");
            //Datastore ds = morphia.createDatastore(mongo,"test");
             Set<String> collections = db.getCollectionNames();
             for (String s : collections) {
                 System.out.println(s);
             }
             } catch (UnknownHostException e) {
                 return null;
             }
         return db;
        }
}
