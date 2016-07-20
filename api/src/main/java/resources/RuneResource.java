package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import models.Rune;
import mongo.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by thomas on 21/05/16.
 */
@Path("/runes")
@Api(value = "Runes", description = "Runes information")
@Produces("application/json")
public class RuneResource {
    private static final Logger log = LoggerFactory.getLogger(RuneResource.class);

    @GET
    @ApiOperation(value = "Get all runes", response = Rune[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Can't retrieve runes.")
    })
    public Response getRunes(){
        try{
            return Response.ok()
                    .entity(
                            MAPPER.writeValueAsString(
                            Database.get().getDatastore()
                            .find(Rune.class)
                            .asList())
                    ).status(200)
                    .build();
        }catch (Exception e){
            if(log.isDebugEnabled()) e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok().entity(new HttpResponse(500,"Can't retrieve runes. Internal server Error.")).status(500).build();
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get a rune by his id.", response = Rune.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Get a run by his id")
    })
    public Response getRune(@PathParam("id") Integer id){
        Response.ResponseBuilder responseBuilder = Response.ok();
        try {
            Rune rune = Database.get().getDatastore().find(Rune.class).filter("id = ", id).get();
            if(rune == null) responseBuilder.entity(new HttpError(404,"Mastery not found")).status(404);
            else responseBuilder.entity(MAPPER.writeValueAsString(rune));
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());

            responseBuilder.entity(new HttpError(500,"Can't retrieve rune. Internal error server.")).status(500);
        }
        return responseBuilder.build();
    }
}
