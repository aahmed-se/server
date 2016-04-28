package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Champion;
import mongo.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by thomas on 28/04/16.
 */
@Path("/champions")
public class ChampionResource {
    private static final Logger log = LoggerFactory.getLogger(ChampionResource.class);

    private static final ObjectMapper mapper = new ObjectMapper();
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces("application/json")
    public String getChampions() {
        try {
            List<Champion> champions =  Database.get().getDatastore().find(Champion.class).retrievedFields(false,"skins").asList();
            return mapper.writeValueAsString(champions);
        } catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return "error 500";
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getChampions(@PathParam("id") int id){
        try {
            Champion champion=  Database.get().getDatastore().find(Champion.class).filter("id = ",id).retrievedFields(false,"skins").get();
            return mapper.writeValueAsString(champion);
        } catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return "error 500";
    }
}

