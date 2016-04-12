package deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import responses.SummonerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 01/04/16.
 */
public class SummonersDeserializer extends JsonDeserializer<SummonerResponse[]>{

    private static Logger log = LoggerFactory.getLogger(SummonersDeserializer.class);

    @Override
    public SummonerResponse[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        List<SummonerResponse> responses = new ArrayList<>();

        if(node.get("Status") != null) {
            JsonNode error =node.get("Status");
            throw new IOException(error.get("status_code").asText() + " code thrown. Error : " + error.get("message").asText());
        }

        ObjectMapper mapper = new ObjectMapper();

        for(JsonNode summoner : node){
            responses.add(mapper.readValue(summoner.traverse(), SummonerResponse.class));
        }

        SummonerResponse[] result = new SummonerResponse[responses.size()];

        return responses.toArray(result);
    }

    @Override
    public Class<?> handledType() {
        return SummonerResponse[].class;
    }
}
