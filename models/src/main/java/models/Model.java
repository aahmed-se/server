package models;

import mongo.Database;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by thomas on 09/04/16
 */
@Entity(noClassnameStored = true)
public abstract class Model{

    private static final Logger log = LoggerFactory.getLogger(Model.class);

    @Id
    protected String objectId;

    public Model(){}
    public Model(String objectId) {
        this.objectId = objectId;
    }

    public synchronized <T extends Model> String save(){
        String collection = this.getClass().getAnnotation(Entity.class).value();
        if(collection != null){
            if(objectId == null){
                objectId = this.find().getObjectId();
            }

            Key<T> key = (Key<T>) Database.get().getDatastore().save(this);
            if(objectId == null && key != null) objectId =(String) key.getId();

            return objectId;
        }
        return null;
    }

    public <T extends Model> T find(){
        if(this.getObjectId() == null) return (T)this;
        return Database.get().getDatastore()
                .get((T) this);
    }

    public String getObjectId() {
        return objectId;
    }
}