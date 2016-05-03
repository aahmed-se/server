package mongo;

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

    private final Morphia morphia;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private Datastore datastore;

    public static Database get() {
        return ourInstance;
    }

    private Database() {

        morphia = new Morphia();

        morphia.mapPackage("models.Summoner");
        mongoClient = new MongoClient("127.0.0.1");
        database = mongoClient.getDatabase("dfp");
        datastore = morphia.createDatastore(mongoClient, "dfp");
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public Datastore getDatastore() {
        return datastore;
    }
}