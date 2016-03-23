package manager;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class CRUD {

    private Database bdd;
    private com.mongodb.DB database;

    public CRUD() {
        this.bdd = new Database();
        this.database = bdd.initBDD();
    }

    public void saveObject(Object myObject) {
        BasicDBObject doc = new BasicDBObject();
        doc.put(Object.class.getName(),myObject);
        System.out.println(Object.class.getSimpleName());
        DBCollection items = bdd.initBDD().getCollection(Object.class.getSimpleName());
        items.insert(doc);
    }

    public void modifyObject(Object myObject) {

    }

    public void deleteObject(Object myObject) {

    }

    public Object findObject(Object myObject) {
    return null;
    }


}