package responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import deserializers.TaskDeserializer;
import manager.CRUD;
import models.Priority;
import models.Region;


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
    public <T extends CRUD> T castToModel() {
        return null;
    }
}
