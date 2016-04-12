package models;

import com.mongodb.DBObject;
import manager.Database;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.lang.annotation.AnnotationFormatError;

/**
 * Created by thomas on 09/04/16.
 * TODO should be implemented by all model
 */
public abstract class Model {

    @Id
    protected ObjectId _id;

    public Model(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId save(){
        Morphia morphia = new Morphia();
        String collection = this.getClass().getAnnotation(Entity.class).value();
        if(collection != null){
            boolean insert = true;
            if(_id == null){
                _id = new ObjectId();
                insert = true;
            }

            if(insert){
                DBObject doc = morphia.toDBObject(this);
                return (ObjectId) Database.get().database.getCollection(collection).save(doc).getField("_id");
            }
        }
        throw new AnnotationFormatError("Missing annotation entity on the model " + this.getClass());
    }
}
