package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import models.Mastery;
import mongo.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpError;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by thomas on 21/05/16.
 */
@Path("/masteries")
@Api(value = "Mastery",description = "Mastery information")
@Produces("application/json")
public class MasteryResource {

    private static final Logger log = LoggerFactory.getLogger(MasteryResource.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();


    @GET
    @ApiOperation(value="Get all masteries", response = Mastery[].class)
    @ApiResponses( value = {
            @ApiResponse(code = 500, message = "Can't retrieve masteries.")
            })
    public Response getMasteries(){
        Response.ResponseBuilder responseBuilder = Response.ok();
        try {
            responseBuilder.entity(
                    MAPPER.writeValueAsString(
                            Database.get().getDatastore()
                            .find(Mastery.class)
                            .asList()
                    )).status(200);
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());

            responseBuilder.entity(new HttpError(500,"Can't retrieve masteries. Internal error server.")).status(500);
        }
        return responseBuilder.build();
    }

    @GET
    @Path("{id}")
    @ApiOperation(value = "Get mastery by id", response = Mastery.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not found !"),
            @ApiResponse(code = 500, message = "Can't retrieve mastery.")
    })
    public Response getMastery(@PathParam("id") Long id){
        Response.ResponseBuilder responseBuilder = Response.ok();
        try {
            Mastery mastery = Database.get().getDatastore().find(Mastery.class).filter("id = ", id).get();
            if(mastery == null) responseBuilder.entity(new HttpError(404,"Mastery not found")).status(404);
            else responseBuilder.entity(MAPPER.writeValueAsString(mastery));
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());

            responseBuilder.entity(new HttpError(500,"Can't retrieve mastery. Internal error server.")).status(500);
        }
        return responseBuilder.build();
    }

}
