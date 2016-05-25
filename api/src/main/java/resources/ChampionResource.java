package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Champion;
import mongo.Database;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpError;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by thomas on 28/04/16.
 */
@Path("/champion")
@Produces("application/json")
public class ChampionResource {
    private static final Logger log = LoggerFactory.getLogger(ChampionResource.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @GET
    public Response getChampions() {
        log.debug("Get champions called");
        try {
            List<Champion> champions =  Database.get().getDatastore().find(Champion.class).order("key").retrievedFields(false,"skins").asList();
            return Response.ok(MAPPER.writeValueAsString(champions)).status(Response.Status.OK).header("Access-Control-Allow-Origin","*").header("Access-Control-Allow-Headers","Content-Type").build();
        } catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpError(500,"Can't retrieve champions. Internal error server.")).status(500).build();
    }

    @GET
    @Path("{id}{skins:(/skins)?}")
    public Response getChampions(
            @PathParam("id") int id,
            @PathParam("skins") String skins
            ){
        boolean addSkin = !skins.equals("");
        try {
            Query<Champion> queryChampion = Database.get().getDatastore().find(Champion.class).filter("id = ",id);
            Champion champion;

            if(!addSkin) queryChampion = queryChampion.retrievedFields(false,"skins");
            champion = queryChampion.get();

            if(champion == null){
                return Response.ok(new HttpError(404,"Champion not found !")).status(404).build();
            }
            return Response.ok(MAPPER.writeValueAsString(champion)).status(Response.Status.OK).header("Access-Control-Allow-Origin","*").header("Access-Control-Allow-Headers","Content-Type").build();
        } catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpError(500,"Can't retrieve the champion. Internal error server.")).status(500).build();
    }
}

