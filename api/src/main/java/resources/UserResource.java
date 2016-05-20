package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.Token;
import models.User;
import mongo.Database;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.UserLoginRequest;
import utils.HttpError;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.Date;

import static conf.Configuration.CONFIG;

/**
 * Created by thomas on 19/05/16.
 */
@Path("/user")
@Produces("application/json")
public class UserResource {
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    @GET
    @Path("{id}")
    public Response getUser(@PathParam("id") String _id){
        try {
            User user = Database.get().getDatastore().get(User.class,new ObjectId(_id));
            if(user== null) return Response.ok(new HttpError(404,"User not found !")).status(404).build();
            return Response.ok(mapper.writeValueAsString(user)).status(200).build();
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpError(500,"Can't retrieve user on id " + _id + ". Internal error server.")).status(500).build();
    }

    @POST
    @Path("login")
    @Consumes("application/json")
    public Response login(UserLoginRequest userLoginRequest){
        try {
            User user = Database.get().getDatastore().find(User.class)
                    .filter("login =",userLoginRequest.login)
                    .filter("password =",User.encrypt(userLoginRequest.password)).get();

            if(user== null) return Response.ok(new HttpError(404,"User not found !")).status(404).build();

            String token = Jwts.builder()
                    .setSubject(mapper.writeValueAsString(user))
                    .setExpiration(new Date(new Date().getTime() + CONFIG.getLong("token.timeout")))
                    .signWith(SignatureAlgorithm.forName(CONFIG.getString("token.algorithm")), CONFIG.getString("token.secret"))
                    .compact();
            return Response.ok(mapper.writeValueAsString(new Token(token))).status(200).build();
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpError(500,"Can't retrieve user on id . Internal error server.")).status(500).build();
    }


    @GET
    @Path("token/{token}")
    public Response verifyToken(@PathParam("token") String token){
        try {

            Claims body = Jwts.parser().setSigningKey(CONFIG.getString("token.secret")).parseClaimsJws(token).getBody();
            return Response.ok(mapper.writeValueAsString(body)).status(200).build();
        } catch (Exception e) {

        }
        return Response.ok(new HttpError(500,"Token non valid")).status(500).build();
    }


}
