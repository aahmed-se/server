package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import mongo.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import responses.Response;

/**
 * Created by Thomas on 23/11/2015.
 */
public class Harvester {

    private static final Logger log = LoggerFactory.getLogger(Harvester.class);

    public static <T extends Response> void basicReceipt(byte[] obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readValue(obj, ObjectNode.class);
            Class<T> objectClass = (Class<T>) Class.forName(node.get("class").asText());
            T object = mapper.readValue(node.get("object").asText(), objectClass);
            log.debug("Object receive : {} from {}", object, objectClass);
            Model objectModel = object.castToModel();
            log.debug("Object saved from {} into id {}",objectModel.getClass(),objectModel.save());
        } catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}

