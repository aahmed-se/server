package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import models.Region;
import models.Summoner;
import mongo.Database;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpError;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by thomas on 29/04/16.
 */
@Path("summoners")
@Api(value = "Summoner", description = "Summoners informations")
@Produces({"application/json"})
public class SummonerResource {

    private static final Logger log = LoggerFactory.getLogger(SummonerResource.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();


    @GET
    @Path("/{_id}")
    @Produces("application/json")
    @ApiOperation(value = "Get a user by his object objectId", notes = "Notes !", response = Summoner.class)
    @ApiResponses( value = {
            @ApiResponse(code = 404, message = "Summoner not found !"),
            @ApiResponse(code = 400, message = "Bad objectId inserted, waited object id."),
            @ApiResponse(code = 500, message = "Can't retrieve summoner. Internal error server.")
    })
    public Response getSummoner(@ApiParam("Object id") @PathParam("_id") String _id){
        Response.ResponseBuilder responseBuilder = Response.ok();
        try {
            Summoner summoner = Database.get().getDatastore().get(Summoner.class,new ObjectId(_id));
            if(summoner == null) responseBuilder.entity(new HttpError(404,"Summoner not found !")).status(404);
            else responseBuilder.entity(MAPPER.writeValueAsString(summoner)).status(200);
        }catch (IllegalArgumentException e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.warn(e.getMessage());

            responseBuilder.entity(new HttpError(400,"Bad id inserted, waited object id.")).status(500);
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());

            responseBuilder.entity(new HttpError(500,"Can't retrieve summoner. Internal error server.")).status(500);
        }
        return responseBuilder.build();
    }

    @GET
    @Path("/{region}/{name}")
    @ApiOperation(value = "Get a user by his region and name", response = Summoner.class)
    @ApiResponses(value ={
            @ApiResponse(code = 404, message = "Summoner not found !"),
            @ApiResponse(code = 500, message = "Can't retrieve summoner !")
    })
    public Response getSummonerByName(
            @ApiParam(defaultValue = "euw") @PathParam("region") Region region,
            @PathParam("name") String name
            ){
        try {
            Summoner summoner = Database.get().getDatastore().find(Summoner.class)
                    .filter("region = ",region)
                    .field("name").equalIgnoreCase(name)
                    .get();
            if(summoner == null){

                return Response.ok(new HttpError(404,"Summoner not found !")).status(404).build();
            }
            return Response.ok(MAPPER.writeValueAsString(summoner)).status(200).build();
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpError(500,"Can't retrieve summoner. Internal error server.")).status(500).build();
    }
}
