package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Summoner;
import mongo.Database;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpError;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by thomas on 29/04/16.
 */
@Singleton
@Path("/summoner")
@Produces("application/json")
public class SummonerResource {

    private static final Logger log = LoggerFactory.getLogger(SummonerResource.class);

    private static final ObjectMapper mapper = new ObjectMapper();


    @GET
    @Path("{id}")
    public Response getSummoner(@PathParam("id") String _id){
        Response.ResponseBuilder responseBuilder = Response.ok();
        try {
            Summoner summoner = Database.get().getDatastore().get(Summoner.class,new ObjectId(_id));
            if(summoner == null) responseBuilder = responseBuilder.entity(new HttpError(404,"Summoner not found !")).status(404);
            else responseBuilder = responseBuilder.entity(mapper.writeValueAsString(summoner)).status(200);
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());

            responseBuilder = responseBuilder.entity(new HttpError(500,"Can't retrieve summoner. Internal error server.")).status(500);
        }
        return responseBuilder.build();
    }

    @GET
    @Path("{region}/{name}")
    public Response getSummonerByName(
            @PathParam("region") String region,
            @PathParam("name") String name
            ){
        try {
            Summoner summoner = Database.get().getDatastore().find(Summoner.class)
                    .filter("region = ",region)
                    .field("name").equalIgnoreCase(name)
                    .get();
            if(summoner == null) return Response.ok(new HttpError(404,"Summoner not found !")).status(404).build();
            return Response.ok(mapper.writeValueAsString(summoner)).status(200).build();
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpError(500,"Can't retrieve summoner. Internal error server.")).status(500).build();
    }
}
