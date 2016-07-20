package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import models.Champion;
import mongo.Database;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpError;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by thomas on 28/04/16.
 */
@Path("/champions")
@Api(value = "Champion",description = "Champions information")
@Produces("application/json")
public class ChampionResource {
    private static final Logger log = LoggerFactory.getLogger(ChampionResource.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @GET
    @ApiOperation(value = "Get all champions.",notes = "Don't return any skins", response = Champion[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Can't retrieve champions. Internal error server.")
    })
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
    @Path("/{id}")
    @ApiOperation(value = "Get champion by id", notes = "Default don't return any skins", response = Champion.class)
    @ApiResponses( value = {
            @ApiResponse(code = 404, message = "Champion not found at this id."),
            @ApiResponse(code = 500, message = "Can't retrieve the champion.")
    })
    public Response getChampions(
            @PathParam("id") int id,
            @QueryParam("skins") boolean skins
            ){
        try {
            Query<Champion> queryChampion = Database.get().getDatastore().find(Champion.class).filter("id = ",id);
            Champion champion;

            if(!skins) queryChampion = queryChampion.retrievedFields(false,"skins");
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

