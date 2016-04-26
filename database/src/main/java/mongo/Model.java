package mongo;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by thomas on 09/04/16.
 * TODO should be implemented by all model
 */
@Entity(noClassnameStored = true)
public abstract class Model extends Document{

    private static final Logger log = LoggerFactory.getLogger(Model.class);

    @Id
    protected ObjectId _id;

    public Model(){

    };
    public Model(ObjectId _id) {
        this._id = _id;
    }

    public synchronized <T extends Model> ObjectId save(){
        String collection = this.getClass().getAnnotation(Entity.class).value();
        if(collection != null){
            _id = this.find();
            if(_id == null){
                _id = new ObjectId();
            }
            Database.get().datastore.save(this);

            return _id;
        }
        return null;
    }

    public abstract ObjectId find();

    public ObjectId get_id() {
        return _id;
    }
}