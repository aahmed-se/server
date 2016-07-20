package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import mongo.Database;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializers.ObjectIdSerializer;

/**
 * Created by thomas on 09/04/16
 */
@Entity(noClassnameStored = true)
public abstract class Model{

    private static final Logger log = LoggerFactory.getLogger(Model.class);
    public final static ObjectMapper MAPPER = new ObjectMapper();

    @Id
    protected ObjectId objectId;

    public Model(){this(null);}
    public Model(ObjectId objectId) {
        if(objectId != null) this.objectId = objectId;
        MAPPER.registerModule(new SimpleModule("serializer").addSerializer(new ObjectIdSerializer()));
    }

    public synchronized <T extends Model> ObjectId save(){
        String collection = this.getClass().getAnnotation(Entity.class).value();
        if(collection != null){
            if(objectId == null){
                objectId = this.find().getObjectId();
            }

            Key<T> key = (Key<T>) Database.get().getDatastore().save(this);
            if(objectId == null && key != null) objectId =  new ObjectId(key.getId().toString());

            return objectId;
        }
        return null;
    }

    public <T extends Model> T find(){
        if(this.getObjectId() == null) return (T)this;
        return Database.get().getDatastore()
                .get((T) this);
    }

    public ObjectId getObjectId() {
        return objectId;
    }
}