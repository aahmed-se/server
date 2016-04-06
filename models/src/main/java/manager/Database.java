package manager;

import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import java.net.UnknownHostException;

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
             mongo = new MongoClient("10.31.4.1", 5151);
             db = mongo.getDB("test");
            Datastore ds = morphia.createDatastore(mongo,"test");
             } catch (UnknownHostException e) {
                 return null;
             }
         return db;
        }
}
