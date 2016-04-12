package manager;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class CRUD {

    private Database bdd;
    private com.mongodb.DB database;

    public CRUD() {
        this.bdd = Database.get();
        this.database = bdd.database;
    }

    public void save() {
        BasicDBObject doc = new BasicDBObject();
        doc.put(Object.class.getName(),this);
        System.out.println(Object.class.getSimpleName());
        DBCollection items = database.getCollection(Object.class.getSimpleName());
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