package models;

import mongo.Database;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by thomas on 09/04/16.
 */
@Entity(noClassnameStored = true)
public abstract class Model{

    private static final Logger log = LoggerFactory.getLogger(Model.class);

    @Id
    protected String _id;

    public Model(){}
    public Model(String _id) {
        this._id = _id;
    }

    public synchronized <T extends Model> String save(){
        String collection = this.getClass().getAnnotation(Entity.class).value();
        if(collection != null){
            if(_id == null){
                _id = this.find().get_id();
            }

            Key<T> key = (Key<T>) Database.get().getDatastore().save(this);
            if(_id == null && key != null) _id =(String) key.getId();

            return _id;
        }
        return null;
    }

    public <T extends Model> T find(){
        if(this.get_id() == null) return (T)this;
        return Database.get().getDatastore()
                .get((T) this);
    }

    public String get_id() {
        return _id;
    }
}