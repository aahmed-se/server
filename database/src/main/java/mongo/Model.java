package mongo;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.AnnotationFormatError;

/**
 * Created by thomas on 09/04/16.
 * TODO should be implemented by all model
 */
@Entity
public abstract class Model extends Document{

    private static final Logger log = LoggerFactory.getLogger(Model.class);

    @Id
    protected ObjectId _id;

    public Model(ObjectId _id) {
        this._id = _id;
    }

    public <T extends Model> ObjectId save(){
        String collection = this.getClass().getAnnotation(Entity.class).value();
        if(collection != null){
            if(_id == null){
                _id = new ObjectId();
            }
            Database.get().datastore.save(this);

            return _id;
        }
        throw new AnnotationFormatError("Missing annotation entity on the model " + this.getClass());
    }



}
