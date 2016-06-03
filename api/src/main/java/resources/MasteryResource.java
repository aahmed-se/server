package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Mastery;
import mongo.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static models.Model.MAPPER;

/**
 * Created by thomas on 21/05/16.
 */
@Path("/mastery")
@Produces("application/json")
public class MasteryResource {

    private static final Logger log = LoggerFactory.getLogger(MasteryResource.class);



    @GET
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

            responseBuilder.entity(new HttpResponse(500,"Can't retrieve masteries. Internal error server.")).status(500);
        }
        return responseBuilder.build();
    }

    @GET
    @Path("{id}")
    public Response getMastery(@PathParam("id") Long id){
        Response.ResponseBuilder responseBuilder = Response.ok();
        try {
            Mastery mastery = Database.get().getDatastore().find(Mastery.class).filter("id = ", id).get();
            if(mastery == null) responseBuilder.entity(new HttpResponse(404,"Mastery not found")).status(404);
            else responseBuilder.entity(MAPPER.writeValueAsString(mastery));
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());

            responseBuilder.entity(new HttpResponse(500,"Can't retrieve masteries. Internal error server.")).status(500);
        }
        return responseBuilder.build();
    }

}
