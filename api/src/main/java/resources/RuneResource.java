package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Rune;
import mongo.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by thomas on 21/05/16.
 */
@Path("/rune")
@Produces("application/json")
public class RuneResource {
    private static final Logger log = LoggerFactory.getLogger(RuneResource.class);

    @GET
    public Response getRunes(){
        try{
            return Response.ok()
                    .entity(
                            Database.get().getDatastore()
                            .find(Rune.class)
                            .asList()
                    ).status(200)
                    .build();
        }catch (Exception e){
            if(log.isDebugEnabled()) e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok().entity(new HttpResponse(500,"Can't retrieve runes. Internal server Error.")).status(500).build();
    }
}
