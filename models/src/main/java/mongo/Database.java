package mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static conf.Configuration.CONFIG;
/**
 * Created by Thomas 29/10/2015.
 */

public class Database {

    private static final Logger log = LoggerFactory.getLogger(Database.class);
    private static Database ourInstance = new Database();

    private final Morphia morphia;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private Datastore datastore;

    private Database() {

        morphia = new Morphia();

        morphia.mapPackage("models.Summoner");
        mongoClient = new MongoClient(CONFIG.getString("database.address"));
        database = mongoClient.getDatabase(CONFIG.getString("database.name"));
        datastore = morphia.createDatastore(mongoClient, CONFIG.getString("database.name"));
        datastore.ensureIndexes();
    }

    public static Database get() {
        return ourInstance;
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