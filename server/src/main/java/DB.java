import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.net.UnknownHostException;

/**
 * Created by Cloé on 29/10/2015.
 */

public class DB {

     public static Datastore initBDD() throws UnknownHostException {
         Morphia morphia = new Morphia();
         MongoClient mongo = new MongoClient("127.0.0.1");
         Datastore ds = morphia.createDatastore(mongo,"test");
         return ds;
    }
}
