package resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import models.Champion;
import mongo.Database;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static models.Model.MAPPER;

/**
 * Created by thomas on 28/04/16.
 */
@Path("/champions")
@Api(value = "Champion",description = "Champions information")
@Produces("application/json")
public class ChampionResource {
    private static final Logger log = LoggerFactory.getLogger(ChampionResource.class);


    @GET
    @ApiOperation(value = "Get all champions.",notes = "Don't return any skins", response = Champion[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Can't retrieve champions. Internal error server.")
    })
    public Response getChampions() {
        log.debug("Get champions called");
        try {
            List<Champion> champions =  Database.get().getDatastore().find(Champion.class).order("key").retrievedFields(false,"skins").asList();
            return Response.ok(MAPPER.writeValueAsString(champions)).status(Response.Status.OK).build();
        } catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpResponse(500,"Can't retrieve champions. Internal error server.")).status(500).build();
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
                return Response.ok(new HttpResponse(404,"Champion not found !")).status(404).build();
            }
            return Response.ok(MAPPER.writeValueAsString(champion)).status(Response.Status.OK).build();
        } catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpResponse(500,"Can't retrieve the champion. Internal error server.")).status(500).build();
    }
}

