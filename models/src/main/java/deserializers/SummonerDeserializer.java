package deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Summoner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 01/04/16.
 */
public class SummonerDeserializer extends JsonDeserializer<Summoner[]>{

    private static Logger log = LoggerFactory.getLogger(SummonerDeserializer.class);

    @Override
    public Summoner[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        List<Summoner> responses = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        for(JsonNode summoner : node){
            responses.add(mapper.readValue(summoner.traverse(), Summoner.class));
        }

        Summoner[] result = new Summoner[responses.size()];

        return responses.toArray(result);
    }

    @Override
    public Class<?> handledType() {
        return Summoner[].class;
    }
}
