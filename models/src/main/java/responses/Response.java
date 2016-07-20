package responses;

import models.Model;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Kaylleur on 26/12/2015.
 */
@Entity
public abstract class Response {

    public Response() {
    }

    public abstract <T extends Model> T castToModel();
}
