package mongoClient;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Clo� on 29/10/2015.
 */

public class Database {

    private static final Logger log = LoggerFactory.getLogger(Database.class);
    private static Database ourInstance = new Database();

    public final Morphia morphia;
    public MongoClient mongoClient;
    public MongoDatabase database;
    public Datastore datastore;

    public static Database get() {
        return ourInstance;
    }

    private Database() {

        morphia = new Morphia();
        morphia.mapPackage("models.Summoner");
        mongoClient = new MongoClient("192.168.1.17");
        database = mongoClient.getDatabase("dfp");
        datastore = morphia.createDatastore(mongoClient, "dfp");
    }


}