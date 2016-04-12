package deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import models.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import responses.TaskResponse;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 25/03/16.
 */
public class TaskDeserializer extends JsonDeserializer<TaskResponse> {

    private static final Logger log = LoggerFactory.getLogger(TaskDeserializer.class);

    @Override
    public TaskResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        TaskResponse response = new TaskResponse();
        try {
            response.target = Class.forName(node.get("target").asText());
            response.method = node.get("method").asText();

            //Create the array for parameters types
            ArrayList<Class> parametersTypes = new ArrayList<>();
            for(JsonNode nodeClass: node.get("parametersTypes")){
                parametersTypes.add(Class.forName(nodeClass.asText()));
            }
            response.parametersTypes = new Class[parametersTypes.size()];
            for (int i = 0; i < parametersTypes.size(); i++) {
                response.parametersTypes[i] = parametersTypes.get(i);
            }

//          God damn ! i have to check all different type to cast u_U
            ArrayList<Object> parameters = new ArrayList<>();
            int i = 0;
            for(JsonNode nodeClass: node.get("parameters")){
                parameters.add(convertJsonNode(parametersTypes.get(i),nodeClass));
                i++;
            }
            response.parameters = parameters.toArray();

            //response.region = Region.valueOf(node.get("region").asText());
            response.priority = Priority.valueOf(node.get("priority").asText());
            return response;
        } catch (ClassNotFoundException e) {
            log.error("Class not found. The JSON {}.\n Message error {}",node.asText(), e.getMessage());
        }
        throw new IOException("Cannot deserialize request. " + node.asText());
    }

    /**
     * WHY JAVA ? HOW CAN YOU EXPLAIN THAT SHIT ?
     * @param type
     * @param <T>
     * @return
     */
    private static <T> List<T> createListOfType(Class<T> type){
        return new ArrayList<>();
    }

    /**
     * Infinite spiral shit
     * @param type
     * @param node
     * @param <T>
     * @return
     */
    private static <T> T convertJsonNode(Class<T> type, JsonNode node){
        if(type.isEnum()){
            return (T) Enum.valueOf((Class<Enum>)type,node.asText());
        }else if(node.isArray()){
            Class<? super Object> itemsClass = (Class<? super Object>) type.getComponentType();
            List<? super Object> itemsList = createListOfType(itemsClass);
            for(JsonNode item : node){
                itemsList.add(itemsClass.cast(convertJsonNode(itemsClass, item)));
            }
            Object[] res =(Object[]) Array.newInstance(itemsClass,itemsList.size());
            for (int i = 0; i < res.length; i++) {
                res[i] = itemsList.get(i);
            }
            return (T) res;
        }else if(node.canConvertToInt()){
            return type.cast(node.asInt());
        }
        return type.cast(node.asText());
    }

    @Override
    public Class<?> handledType() {
        return TaskResponse.class;
    }
}
