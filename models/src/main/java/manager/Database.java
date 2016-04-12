package manager;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

/**
 * Created by Clo� on 29/10/2015.
 */

public class Database {

    private static final Logger log = LoggerFactory.getLogger(Database.class);
    private static Database ourInstance = new Database();

    public DB database;
    public Datastore datastore;

    public static Database get() {
        return ourInstance;
    }

    private Database() {
        Morphia morphia;
        MongoClient mongo;

        try {
            morphia = new Morphia();
            mongo = new MongoClient("192.168.1.17", 27017);
            database = mongo.getDB("dfp");
            datastore = morphia.createDatastore(mongo, "dfp");
        } catch (UnknownHostException e) {
            if (log.isDebugEnabled()) e.printStackTrace();
        }
    }


}