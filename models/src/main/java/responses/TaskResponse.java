package responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import deserializers.TaskDeserializer;
import models.Priority;
import models.Region;
import models.Model;


/**
 * Created by Maxime on 16/12/2015.
 */
@JsonDeserialize(using = TaskDeserializer.class)
public class TaskResponse extends Response {

    public Class target;
    public String method;
    public Class[] parametersTypes;
    public Object[] parameters;
    public Region region;
    public Priority priority;

    @Override
    public <T extends Model> T castToModel() {
        return null;
    }
}
